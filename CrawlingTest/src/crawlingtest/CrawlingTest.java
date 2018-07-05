/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlingtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.omg.PortableInterceptor.INACTIVE;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sample.painting.PaintingDAO;
import sample.utils.*;

/**
 *
 * @author MinhDuc
 */
public class CrawlingTest {

    public static String crawl() {
        String s = "";
        try {
            // THIS SITE NOT RECOMMENDED.
//                s = XMLUtils.convertHTMLToString("http://tranhtreotuong.vn/tranh-trang-tri.html");
//                int start = s.indexOf("<ul", s.indexOf("category-products"));
//                int end = start;
//                for (int i = 0; i < 10; i++) {
//                    int tmp = s.indexOf("<li class=\"item", end+1);
//                    if (tmp != -1) {
//                        end = tmp;
//                    }
//                }
//                System.out.println("pos: "+start + "-" + end);
//                s = s.substring(start,end) + "</ul>";

            s = XMLUtils.convertHTMLToString("https://tuongxinh.com.vn/tranh-phu-dieu-3d/");
            int start = s.indexOf("<ul class=\"products\">");
            int end = s.indexOf("<nav class=\"woocommerce-pagination\">");
            s = s.substring(start, end);

//                s = XMLUtils.convertHTMLToString("http://www.tranh-dep.com/tranh-trang-tri-phong-khach-dep");
//                int start = s.indexOf("<div id=\"bd_results\">");
//                int end = s.indexOf("<div class=\"ot-bottom-content\">");
//                s = s.substring(start, end);
            System.out.println("len: " + s.length());

            String s2 = HTMLPreprocessor.wellForm(s);
//            System.out.println(s2);

        } catch (IOException ex) {
            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s = crawl();
        
        PaintingDAO dao = new PaintingDAO();
        
        try {
            Document doc = XMLUtils.parseDOMFromString(s);
            String exp = "//li";
            XPath xpath = XMLUtils.createXPath();
            NodeList result = (NodeList) xpath.evaluate(exp, doc, XPathConstants.NODESET); 
            for (int i = 0; i < result.getLength(); i++) {
                Node node = result.item(i);
                
                exp = ".//h3";
                String namecode = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                System.out.println(namecode);
                
                exp = ".//a/@href";
                String pageURL = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                System.out.println(pageURL);
                
                exp = ".//img/@src";
                String imageURL = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                System.out.println(imageURL);
                
                exp = ".//span[contains(@class,'amount')]/text()";
                String sPrice = (String) xpath.evaluate(exp, node, XPathConstants.STRING);
                sPrice = sPrice.replaceAll(",", "");
                double price = Double.parseDouble(sPrice);
                System.out.println(price);
                
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
