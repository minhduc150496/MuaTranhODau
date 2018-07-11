/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import sample.dao.PaintingDAO;
import sample.jaxb.Painting;
import sample.jaxb.Paintings;
import sample.utils.FOPUtils;
import sample.utils.JAXBUtils;

/**
 *
 * @author MinhDuc
 */
public class PrintPDFServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        try {
            // Step 1: get param
            String input = request.getParameter("pdfInput");
            String[] codes = {};
            if (input != null) {
                codes = input.split(",");
            }
            // Step 2: Query, get JAXB Object
            PaintingDAO dao = new PaintingDAO();
            Paintings paintings = new Paintings();
            for (String code : codes) {
                try {
                    Painting p = dao.getByCode(code);
                    System.out.println(p.getName());
                    paintings.getPainting().add(p);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PrintPDFServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(PrintPDFServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            paintings.getPainting().sort((o1, o2) -> {
                Painting p1 = (Painting)o1;
                Painting p2 = (Painting)o2;
                return p1.getPrice().compareTo(p2.getPrice()); 
            });
            // Step 3: marshall to XML
            String xml = null;
            try {
                StringWriter sw = JAXBUtils.marshallObject(paintings, Paintings.class);
                xml = sw.toString();
            } catch (JAXBException ex) {
                Logger.getLogger(PrintPDFServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Step 4: convert to PDF
            String path = this.getServletContext().getRealPath("/");
            String pathXSL = path + "WEB-INF\\catalogFO.xsl";
            String sFO = null;
            try {
                sFO = FOPUtils.convertXmlAndXslToFO(xml, pathXSL);
            } catch (TransformerException ex) {
                Logger.getLogger(PrintPDFServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PrintPDFServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            String pathFopConf = path + "WEB-INF\\fop.xconf";
            ByteArrayOutputStream ousPDF = new ByteArrayOutputStream();
            try {
                FOPUtils.convertFOToPDF(pathFopConf, sFO, ousPDF);
                response.setContentType("application/pdf");
                byte[] content = ousPDF.toByteArray();
                response.setContentLength(content.length);
                response.getOutputStream().write(content);
                response.getOutputStream().flush();
            } catch (SAXException ex) {
                Logger.getLogger(PrintPDFServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(PrintPDFServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ousPDF != null) {
                    ousPDF.close();
                }
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
