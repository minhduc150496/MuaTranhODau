/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlingtest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.omg.PortableInterceptor.INACTIVE;
import sample.painting.PaintingDAO;
import sample.utils.*;

/**
 *
 * @author MinhDuc
 */
public class CrawlingTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PaintingDAO dao = new PaintingDAO();
        String s = "";
        try {
            if (dao==null || true) {
                // THIS SITE NOT RECOMMENDED.
//                s = XMLUtils.convertHTMLToString("http://tranhtreotuong.vn/tranh-trang-tri.html");
//                int start = s.indexOf("<ul", s.indexOf("category-products"));
//                int end = start;
//                for (int i = 0; i < 10; i++) {
//                    int tmp = s.indexOf("<li class=\"item", end+1);
//                    if (tmp != -1) {
//                        end = tmp;
//                    }
//                }
//                System.out.println("pos: "+start + "-" + end);
//                s = s.substring(start,end) + "</ul>";
                
                s = XMLUtils.convertHTMLToString("https://tuongxinh.com.vn/tranh-phu-dieu-3d/");
                int start = s.indexOf("<ul class=\"products\">");
                int end = s.indexOf("<nav class=\"woocommerce-pagination\">");
                s = s.substring(start,end);
                
//                s = XMLUtils.convertHTMLToString("http://www.tranh-dep.com/tranh-trang-tri-phong-khach-dep");
//                int start = s.indexOf("<div id=\"bd_results\">");
//                int end = s.indexOf("<div class=\"ot-bottom-content\">");
//                s = s.substring(start, end);
            }
            
//            File file = new File("src/crawlingtest/sample.txt");
//            FileInputStream fis = new FileInputStream(file);
//            InputStreamReader reader = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(reader);
//            String line = "";
//            s="";
//            while((line=br.readLine()) != null) {
//                s += line + "\n";
//            }
            
            
            System.out.println("len: "+s.length());
            
//            System.out.println(s);
//            s  = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en-gb\" lang=\"en-gb\" />\n" +
//"   \n" +
//"   <body>\n" +
//"      <img  /   > // 1. nếu gặp img, br: nếu chưa đóng, thì đóng ngay, rồi pop\n" +
//"      <h1>\n" +
//"         <span>\n" +
//"            AAA         \n" +
//"            <i>B\n" +
//"            // 2. mở mà chưa đóng: đóng giùm\n" +
//"      </h1> \n" +
//"      <div>\n" +
//"         <h1>\n" +
//"            <div>\n" +
//"               BBB\n" +
//"            </div>\n" +
//"            </div> // 3. chưa mở mà đóng: bỏ\n" +
//"         </h1>\n" +
//"      </div>\n" +
//"   </body>\n" +
//"</html>";
            String s2 = HTMLPreprocessor.wellForm(s);
            System.out.println(s2);
            
//            dao.insert("Tranh dong ho M01", "M01", "some-URL", 100000, "Tranh đồng hồ mạ vàng chất lượng cao.");
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NamingException ex) {
//            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
