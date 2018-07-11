/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.fakeservlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import sample.jaxb.Painting;
import sample.dao.PaintingDAO;
import sample.domparser.TuongXinhComVn;
import sample.domparser.MyParser;
import sample.domparser.TranhTreoTuongVn;
import sample.servlet.PrintPDFServlet;
import sample.staxparser.TranhDepCom;
import sample.utils.FOPUtils;
import sample.utils.StringUtils;

/**
 *
 * @author MinhDuc
 */
public class ScrapServlet {

    // for testing without Junit (like shit)
    public static void main(String[] args) {
        try {
            InputStream insXML = new FileInputStream(new File("web\\WEB-INF\\sample_paintings.xml"));
            String pathXSL = "web\\WEB-INF\\catalogFO.xsl";
            String sFO = null;
            try {
                sFO = FOPUtils.convertXmlAndXslToFO(insXML, pathXSL);
            } catch (TransformerException ex) {
                Logger.getLogger(PrintPDFServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println(sFO);

            String pathFopConf = "web\\WEB-INF\\fop.xconf";
            InputStream insFO = new ByteArrayInputStream(sFO.getBytes());
            OutputStream ousPDF = null;
            try {
                File f = new File("web\\xxx.pdf");                
                ousPDF = new FileOutputStream(f);
                FOPUtils.convertFOToPDF(pathFopConf, insFO, ousPDF);
            } catch (SAXException ex) {
                Logger.getLogger(PrintPDFServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(PrintPDFServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ousPDF != null) {
                    try {
                        ousPDF.close();
                    } catch (IOException ex) {
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ScrapServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
