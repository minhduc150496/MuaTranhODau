/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.webscraper;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import sample.jaxb.Painting;

/**
 *
 * @author MinhDuc
 */
public interface WebScraper {
    public List<Painting> getResults();
    public void scrap();
}
