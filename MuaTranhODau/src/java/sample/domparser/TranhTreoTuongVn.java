/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.domparser;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sample.jaxb.Painting;
import sample.utils.HTMLPreprocessor;
import sample.utils.XMLUtils;

/**
 *
 * @author MinhDuc
 */
public class TranhTreoTuongVn implements MyParser {

    private final String domainCode = "TTT"; // TTT: TranhTreoTuong 
    private List<Painting> results = null;

    @Override
    public List<Painting> getResults() {
        return results;
    }

    @Override
    public void scrap() {
        results = null;
        String[] pageList = {
            "http://tranhtreotuong.vn/tranh-trang-tri.html"
        };
        // Step 1: Get HTML
        List<String> htmlList = new ArrayList<>();
        for (String pageURL : pageList) {
            try {
                String html = HTMLPreprocessor.getHTMLFromURL(pageURL);
                htmlList.add(html);
            } catch (IOException ex) {
                Logger.getLogger(TranhTreoTuongVn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Step 2: Pre-process
        List<String> xmlList = new ArrayList<>();
        for (String html : htmlList) {
            int start = html.indexOf("<ul", html.indexOf("category-products"));
            int end = start;
            for (int i = 0; i < 10; i++) {
                int tmp = html.indexOf("<li class=\"item", end+1);
                if (tmp != -1) {
                    end = tmp;
                }
            }
            html = html.substring(start,end) + "</ul>";
            String xml = HTMLPreprocessor.wellForm(html);
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;            
            xmlList.add(xml);
        }
        
        // Step 3: Parse
        for (String xml : xmlList) {
            try {
                parse(xml);
            } catch (IOException ex) {
                Logger.getLogger(TranhTreoTuongVn.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(TranhTreoTuongVn.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(TranhTreoTuongVn.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(TranhTreoTuongVn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }// end for    
    }// end method
    
    private void parse(String xml) throws
            IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        Document doc = XMLUtils.parseDOMFromString(xml);
        String exp = "//*[contains(@class,'col-item')]";
        XPath xpath = XMLUtils.createXPath();
        NodeList result = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
        for (int i = 0; i < result.getLength(); i++) {
            try {
                Node node = result.item(i);

                exp = ".//h3/a/text()";
                String name = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                name = name.trim();
                
                exp = ".//button/@data-id";
                String code = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                code = code.trim();
                code = domainCode + "-" + code;

                exp = ".//h3/a/@href";
                String pageURL = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                pageURL = "http://tranhtreotuong.vn/" + pageURL.trim();

                exp = ".//img[1]/@src";
                String imageURL = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                imageURL = "http://tranhtreotuong.vn" + imageURL.trim();

                exp = ".//span[@class='price']/text()";
                String sPrice = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                sPrice = sPrice.replace(".", "");
                sPrice = sPrice.replace("đ", "");
                sPrice = sPrice.trim();
                BigInteger price = new BigInteger("-1");
                try {
                    price = new BigInteger(sPrice);
                } catch (NumberFormatException ex) {
                    Logger.getLogger(TranhTreoTuongVn.class.getName()).log(Level.SEVERE, null, ex);
                }

                Painting p = new Painting(name, code, pageURL, price, imageURL, "");

                if (results == null) {
                    results = new ArrayList<>();
                }
                results.add(p);

            } catch (XPathExpressionException ex) {
                // Log lỗi lại và xử lí tiếp
                Logger.getLogger(TranhTreoTuongVn.class.getName()).log(Level.SEVERE, null, ex);
            }
        } // end for    
    }
}
