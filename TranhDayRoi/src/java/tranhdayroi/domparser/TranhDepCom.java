/*
 * To change this license header, choose License Headers in Project Propertiehtml.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tranhdayroi.domparser;

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
import tranhdayroi.jaxb.Painting;
import tranhdayroi.utils.HTMLPreprocessor;
import tranhdayroi.utils.StringUtils;
import tranhdayroi.utils.XMLUtils;

/**
 *
 * @author MinhDuc
 */
public class TranhDepCom extends WebParser implements Parser {

    private final String domainCode = "TD"; // TD: TranhDep 
    private List<Painting> results = null;

    @Override
    public List<Painting> getResults() {
        return results;
    }

    @Override
    public void parse() {
        results = null;
        String[] pageList = {
            "http://www.tranh-dep.com/tranh-tranh-tri-noi-that.html",
            "http://www.tranh-dep.com/tranh-trang-tri-phong-ngu",
            "http://www.tranh-dep.com/tranh-trang-tri-phong-khach-dep",
            "http://www.tranh-dep.com/tranh-trang-tri-phong-an",
            "http://www.tranh-dep.com/tranh-tang-tan-gia",
            "http://www.tranh-dep.com/tranh-dong-ho-hoa-qua.html",
            "http://www.tranh-dep.com/tranh-dong-ho-phong-canh.html",
            "http://www.tranh-dep.com/tranh-dong-ho-dong-vat-con-trung.html",
            "http://www.tranh-dep.com/tranh-dong-ho-hoat-hinh.html",
            "http://www.tranh-dep.com/tranh-dong-ho-trang-tri.html",
            "http://www.tranh-dep.com/tranh-dong-ho-de-ban.html",
            "http://www.tranh-dep.com/tranh-hoa-si-noi-tieng.html",
            "http://www.tranh-dep.com/tranh-hoa-treo-tuong",
            "http://www.tranh-dep.com/tranh-phong-canh-treo-tuong",
            "http://www.tranh-dep.com/tranh-treo-tuong-phong-thuy",
            "http://www.tranh-dep.com/tranh-treo-tuong-vintage",
            "http://www.tranh-dep.com/tranh-bo-hien-dai"
        };
        // Step 1: Get HTML
        System.out.println("Scraping tranhdep.com...");
        System.out.println("Step 1/3: GetHTML");
        List<String> htmlList = new ArrayList<>();
        for (String pageURL : pageList) {
            try {
                String html = HTMLPreprocessor.getHTMLFromURL(pageURL);
                htmlList.add(html);
            } catch (IOException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Step 2: Pre-process
        System.out.println("Step 2/3: Pre-process HTML");
        List<String> xmlList = new ArrayList<>();
        for (String html : htmlList) {
            try {
                int start = html.indexOf("<div id=\"bd_results\"");
                int end = html.indexOf("<div class=\"ot-bottom-content\"");
                html = html.substring(start, end);
                String xml = HTMLPreprocessor.wellForm(html);
                xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;
                xmlList.add(xml);
            } catch (StringIndexOutOfBoundsException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Step 3: Parse
        System.out.println("Step 3/3: Parse");
        for (String xml : xmlList) {
            try {
                parse(xml);
            } catch (IOException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }// end for    
    }// end method

    private void parse(String xml) throws
            IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        Document doc = XMLUtils.parseDOMFromString(xml);
        String exp = "//*[contains(@class,'ot-custom-product')]";
        XPath xpath = XMLUtils.createXPath();
        NodeList result = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET);
        for (int i = 0; i < result.getLength(); i++) {
            try {
                Node node = result.item(i);

                exp = ".//*[contains(@class,'product-name')]//a/text()";
                String name = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                name = name.trim();

                exp = ".//*[contains(@class,'product-sku')]/text()";
                String code = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                code = code.trim();
                code = domainCode + "-" + code;

                exp = ".//*[contains(@class,'product-name')]//a/@href";
                String pageURL = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                pageURL = "http://www.tranh-dep.com" + pageURL.trim();

                exp = ".//*[contains(@class,'product-image')]//img/@src";
                String imageURL = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                imageURL = "http://www.tranh-dep.com" + imageURL.trim();

                exp = ".//span[contains(@class,'PricesalesPrice')]/text()";
                String sPrice = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                sPrice = sPrice.replace(".", "");
                sPrice = sPrice.replace("đ", "");
                sPrice = sPrice.trim();
                BigInteger price = new BigInteger("-1");
                try {
                    price = new BigInteger(sPrice);
                } catch (NumberFormatException ex) {
                    Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
                }
                String keywords = StringUtils.toRawString(name);
                Painting p = new Painting(name, code, pageURL, price, imageURL, keywords);

                if (results == null) {
                    results = new ArrayList<>();
                }
                results.add(p);

            } catch (XPathExpressionException ex) {
                // Log lỗi lại và xử lí tiếp
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            }
        } // end for    
    }
}
