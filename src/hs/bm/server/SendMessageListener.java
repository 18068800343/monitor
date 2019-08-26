package hs.bm.server;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class SendMessageListener implements ServletContextListener {
	
	public static ScheduledThreadPoolExecutor schedule;
    public SendMessageListener() {
    	 schedule = new ScheduledThreadPoolExecutor(5);
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
    	
    }

    public void contextInitialized(ServletContextEvent arg0)  {
	    schedule.scheduleWithFixedDelay(new AutoSetCdStatus(), 5, 1800, TimeUnit.SECONDS);
    }
    public static void main(String[] args) {
    	
	}
	
}
