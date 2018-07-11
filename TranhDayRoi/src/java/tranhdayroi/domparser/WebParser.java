/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tranhdayroi.domparser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import tranhdayroi.dao.PaintingDAO;
import tranhdayroi.jaxb.Painting;
import tranhdayroi.jaxb.Paintings;
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

//    class MyErrHandler implements ErrorHandler {
//
//        @Override
//        public void warning(SAXParseException exception) throws SAXException {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public void error(SAXParseException exception) throws SAXException {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public void fatalError(SAXParseException exception) throws SAXException {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//        
//    }
//    public void validate(Paintings p) {
//        JAXBContext jc;
//        try {
//            jc = JAXBContext.newInstance(Paintings.class);
//            JAXBSource source = new JAXBSource(jc, p);
//            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//            Schema schema = sf.newSchema(new File("web\\WEB-INF\\results.xsd"));
//            Validator validator = schema.newValidator();
//            validator.setErrorHandler();
//            validator.validate(source);
//        } catch (JAXBException ex) {
//            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SAXException ex) {
//            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(WebParser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
}
