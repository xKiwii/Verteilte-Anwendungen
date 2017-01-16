package webapp;

import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class KartenverkaufServletContextListener implements ServletContextListener{
	
	public static Logger logger = Logger.getLogger("Kartenverkauf");

	public KartenverkaufServletContextListener(){
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		
		try {
			Context initialContext = new InitialContext();
			Context context = (Context)initialContext.lookup("java:comp/env");
			DataSource datasource = (DataSource) context.lookup("jdbc/Kartenverkauf");
			
			Connection connection = datasource.getConnection();

			Statement statement = connection.createStatement();
			logger.info("Datasource erfolgreich getestet");
			statement.executeQuery("select * from sitzplaetze");
			logger.info("Datasource erfolgreich getestet");
			
			connection.close();
			
			
			Kartenverkauf kartenverkauf = new Kartenverkauf(100, datasource);
			
			ServletContext servletContext = event.getServletContext();  
			servletContext.setAttribute("kartenverkauf", kartenverkauf);  

			servletContext.setAttribute("datasource",datasource);	
			logger.info("Datasource im app scope gespeichert");
		} catch (Exception e) {
			logger.severe("Problem beim Zugriff auf Datasource, start von Kartenverkauf gescheitert:\n" + e);
			throw new RuntimeException(e);
		}
	}
}