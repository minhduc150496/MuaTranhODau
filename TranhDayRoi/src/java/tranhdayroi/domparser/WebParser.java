/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tranhdayroi.domparser;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import tranhdayroi.dao.PaintingDAO;
import tranhdayroi.jaxb.Painting;
import tranhdayroi.testing.ScrapServlet;

/**
 *
 * @author MinhDuc
 */
public class WebParser {
    
    private int nSuccess = 0;
    private int nFail = 0;

    public int getnSuccess() {
        return nSuccess;
    }

    public int getnFail() {
        return nFail;
    }

    
    public void parseFromDomain(Parser mp) {
        mp.parse();
        List<Painting> list = mp.getResults();

        PaintingDAO dao = new PaintingDAO();

        if (list != null) {
            for (Painting p : list) {
                try {
                    dao.insert(p.getName(), p.getCode(), p.getPageURL(), p.getPrice(), p.getImageURL(), p.getKeywords());
                    nSuccess++;
                } catch (SQLException ex) {
                    Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
                    nFail++;
                } catch (NamingException ex) {
                    Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("Finish. " + nSuccess + " new painting(s), " + nFail + " duplicated (skipped).");
    }
}
