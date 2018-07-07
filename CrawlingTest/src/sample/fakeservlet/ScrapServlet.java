/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.fakeservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import sample.jaxb.Painting;
import sample.dao.PaintingDAO;
import sample.webscraper.TuongXinhComVN;
import sample.webscraper.WebScraper;

/**
 *
 * @author MinhDuc
 */
public class ScrapServlet {

    public void process() {
        WebScraper ws = new TuongXinhComVN();
        ws.scrap();
        List<Painting> list = ws.getResults();
        System.out.println("list: "+list.size());
        
        PaintingDAO dao = new PaintingDAO();

        int nSuccess = 0;
        if (list != null) {
            for (Painting p : list) {
                try {
                    dao.insert(p.getName(), p.getCode(), p.getPageURL(), p.getPrice().intValue(), "");
                    nSuccess++;
                } catch (SQLException ex) {
                    Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NamingException ex) {
                    Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("Scrap done: " + nSuccess + " new painting(s).");

    }
}
