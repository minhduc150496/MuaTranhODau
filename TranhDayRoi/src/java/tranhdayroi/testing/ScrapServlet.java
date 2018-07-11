/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tranhdayroi.testing;

import tranhdayroi.listener.ScheduledParsingTask;

/**
 *
 * @author MinhDuc
 */
public class ScrapServlet {

    // for testing without Junit (like shit)
    public static void main(String[] args) {
        ScheduledParsingTask t = new ScheduledParsingTask();
        t.run();
    }
}
