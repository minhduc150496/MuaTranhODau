/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tranhdayroi.listener;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author MinhDuc
 */
public class MyContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 3);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        // Every night at 3AM
        Timer timer = new Timer();
        timer.schedule(new ScheduledParsingTask(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
