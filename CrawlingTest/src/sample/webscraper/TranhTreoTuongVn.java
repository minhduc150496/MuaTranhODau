/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.webscraper;

import java.io.IOException;
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
public class TranhTreoTuongVn {

    public List<Painting> scrap() throws IOException {
        String html = HTMLPreprocessor.getHTMLFromURL("http://tranhtreotuong.vn/tranh-trang-tri.html");
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

        List<Painting> results = null;
        
        // DOM Parsing
        
        return results;
    }
}
