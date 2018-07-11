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
import sample.utils.StringUtils;
import sample.utils.XMLUtils;

/**
 *
 * @author MinhDuc
 */
public class TuongXinhComVn implements MyParser {

    private final String domainCode = "TX"; // TD: TuongXinh
    private List<Painting> results = null;

    @Override
    public List<Painting> getResults() {
        return results;
    }

    @Override
    public void scrap() {
        results = null;
        String[] pageList = {
            "https://tuongxinh.com.vn/tranh-dong-ho/",
            "https://tuongxinh.com.vn/tranh-scandinavia-bac-au",
            "https://tuongxinh.com.vn/tranh-phu-dieu-3d/"
        };
        // Step 1: Get HTML
        List<String> htmlList = new ArrayList<>();
        for (String pageURL : pageList) {
            try {
                String html = HTMLPreprocessor.getHTMLFromURL(pageURL);
                htmlList.add(html);
            } catch (IOException ex) {
                Logger.getLogger(TuongXinhComVn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Step 2: Pre-process
        List<String> xmlList = new ArrayList<>();
        for (String html : htmlList) {
            int start = html.indexOf("<ul class=\"products\">");
            int end = html.indexOf("<nav class=\"woocommerce-pagination\">");
            html = html.substring(start, end);
            String xml = HTMLPreprocessor.wellForm(html);
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;            
            xmlList.add(xml);
        }
        // Step 3: Parse
        for (String xml : xmlList) {
            try {
                parse(xml);
            } catch (IOException ex) {
                Logger.getLogger(TuongXinhComVn.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(TuongXinhComVn.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(TuongXinhComVn.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(TuongXinhComVn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void parse(String xml) throws
            IOException, ParserConfigurationException, SAXException, XPathExpressionException {

        Document doc = XMLUtils.parseDOMFromString(xml);
        String exp = "//li";
        XPath xpath = XMLUtils.createXPath();
        NodeList result = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
        for (int i = 0; i < result.getLength(); i++) {
            try {
                Node node = result.item(i);

                exp = ".//h3";
                String namecode = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                namecode = namecode.trim();
                int j = namecode.length() - 1;
                while (j >= 0 && Character.isSpaceChar(namecode.charAt(j)) == false) {
                    j--;
                }
                String name = namecode.substring(0, j);
                String code = namecode.substring(j + 1);

                exp = ".//a/@href";
                String pageURL = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                pageURL = pageURL.trim();

                exp = ".//img/@src";
                String imageURL = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                imageURL = imageURL.trim();

                exp = ".//span[contains(@class,'amount')]/text()";
                String sPrice = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                sPrice = sPrice.replace(",", "");
                sPrice = sPrice.trim();
                BigInteger price = new BigInteger("-1");
                try {
                    price = new BigInteger(sPrice);
                } catch (NumberFormatException ex) {
                    Logger.getLogger(TuongXinhComVn.class.getName()).log(Level.SEVERE, null, ex);
                }                
                String keywords = StringUtils.toRawString(name);
                Painting p = new Painting(name, code, pageURL, price, imageURL, keywords);

                if (results == null) {
                    results = new ArrayList<>();
                }
                results.add(p);

            } catch (XPathExpressionException ex) {
                // Log lỗi lại và xử lí tiếp
                Logger.getLogger(TuongXinhComVn.class.getName()).log(Level.SEVERE, null, ex);
            }

        } // end for
    }
}
