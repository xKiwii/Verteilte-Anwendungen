package webapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class KartenverkaufServletContextListener implements ServletContextListener{

	public KartenverkaufServletContextListener(){
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		Kartenverkauf kartenverkauf = new Kartenverkauf(100);
		
		ServletContext context = event.getServletContext();  
		context.setAttribute("kartenverkauf", kartenverkauf);  
	}
}

//TODO: @WebListener