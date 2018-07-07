/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlingtest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sample.fakeservlet.ScrapServlet;
import sample.utils.HTMLPreprocessor;

/**
 *
 * @author MinhDuc
 */
public class CrawlingTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            String html = HTMLPreprocessor.getHTMLFromURL("http://www.tranh-dep.com/tranh-trang-tri-phong-khach-dep");
            int start = html.indexOf("<div id=\"bd_results\">");
            int end = html.indexOf("<div class=\"ot-bottom-content\">");
            html = html.substring(start, end);
            
            String xml = HTMLPreprocessor.wellForm(html);
            
            System.out.println(xml);
        } catch (IOException ex) {
            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
