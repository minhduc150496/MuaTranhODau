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
import sample.staxparser.TranhDepCom;
import sample.domparser.TuongXinhComVN;
import sample.domparser.MyParser;

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
        MyParser ws = new TranhDepCom();
        ws.scrap();
        List<Painting> list = ws.getResults();
//        System.out.println("list: "+list.size());
        
        PaintingDAO dao = new PaintingDAO();

        int nSuccess = 0;
        int nDuplicated = 0;
        if (list != null) {
            for (Painting p : list) {
                try {
                    dao.insert(p.getName(), p.getCode(), p.getPageURL(), p.getPrice(), p.getImageURL());
                    nSuccess++;
                } catch (SQLException ex) {
//                    Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
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