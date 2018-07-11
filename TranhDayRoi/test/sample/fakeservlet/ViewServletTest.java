/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.fakeservlet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MinhDuc
 */
public class ViewServletTest {
    
    public ViewServletTest() {
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
     * Test of process method, of class ViewServlet.
     */
    @Test
    public void testProcess() {
        System.out.println("process");
        ViewServlet instance = new ViewServlet();
        instance.process();
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
