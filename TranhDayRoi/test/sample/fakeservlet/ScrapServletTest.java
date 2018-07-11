/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.fakeservlet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author MinhDuc
 */
public class ScrapServletTest {

    public ScrapServletTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class ScrapServlet.
     */
    @Test
    public void testProcess() {
        System.out.println("process");
        ScrapServlet instance = new ScrapServlet();
        instance.process();

//        String[] pageList = {"https://tuongxinh.com.vn/tranh-dong-ho/",
//            "https://tuongxinh.com.vn/tranh-scandinavia-bac-au",
//            "https://tuongxinh.com.vn/tranh-phu-dieu-3d/"
//        };
//        for (String pageURL : pageList) {
//            try {
//                URL url = new URL(pageURL);
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(ScrapServletTest.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

}
