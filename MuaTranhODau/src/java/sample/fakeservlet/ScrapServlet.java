/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.fakeservlet;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import sample.jaxb.Painting;
import sample.dao.PaintingDAO;
import sample.domparser.TuongXinhComVn;
import sample.domparser.MyParser;
import sample.domparser.TranhTreoTuongVn;
import sample.staxparser.TranhDepCom;
import sample.utils.StringUtils;

/**
 *
 * @author MinhDuc
 */
public class ScrapServlet {
    
    // for testing without Junit (like shit)
    public static void main(String[] args) {
        (new ScrapServlet()).process();
    }

    public void process() {
        MyParser ws = new TranhTreoTuongVn();
        ws.scrap();
        List<Painting> list = ws.getResults();
        
        PaintingDAO dao = new PaintingDAO();

        int nSuccess = 0;
        int nDuplicated = 0;
        if (list != null) {
            for (Painting p : list) {
                try {
                    dao.insert(p.getName(), p.getCode(), p.getPageURL(), p.getPrice(), p.getImageURL(), p.getKeywords());
                    nSuccess++;
                } catch (SQLException ex) {
                    Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
                    nDuplicated++;
                } catch (NamingException ex) {
                    Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("Scrap done: " + nSuccess + " new painting(s), " + nDuplicated + " duplicated (skipped).");

    }
}
