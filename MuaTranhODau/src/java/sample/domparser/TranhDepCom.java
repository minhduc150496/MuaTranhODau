/*
 * To change this license header, choose License Headers in Project Propertiehtml.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.domparser;

import java.io.IOException;
import java.util.List;
import sample.jaxb.Painting;
import sample.utils.HTMLPreprocessor;

/**
 *
 * @author MinhDuc
 */
public class TranhDepCom{
    
    
    public List<Painting> scrap() throws IOException {
        String html = HTMLPreprocessor.getHTMLFromURL("http://www.tranh-dep.com/tranh-trang-tri-phong-khach-dep");
        int start = html.indexOf("<div id=\"bd_results\">");
        int end = html.indexOf("<div class=\"ot-bottom-content\">");
        html = html.substring(start, end);

        String xml = HTMLPreprocessor.wellForm(html);

        List<Painting> results = null;
        
        // DOM Parsing
        
        return results;
    }
}
