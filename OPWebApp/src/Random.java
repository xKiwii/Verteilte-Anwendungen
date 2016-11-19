

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Random
 */
@WebServlet("/Rand")
public class Random extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String lowerInt = request.getParameter("a");
		String upperInt = request.getParameter("b");
		
		int a;
		int b;
		
		if((lowerInt == null) || (upperInt == null)){
			response.sendRedirect("/OPWebApp/invalidvalues.html");
			// throw new IllegalArgumentException("Missing input"); TODO: Eigene Expection udn in web.xml error handling
		}
		
		try {
			a = Integer.parseInt(lowerInt, 10);
			b = Integer.parseInt(upperInt, 10);
			
			if (a >= b) {
				response.sendRedirect("/OPWebApp/invalidvalues.html");
				//throw new IllegalArgumentException("Invalid input, upper must be larger than lower.");
			}
			
			response.setContentType("text/html");
			response.getWriter().println("Zufallszahl: " + (a + Math.round((Math.random() * (b - a)))) + "<br />");
			
		} catch (NumberFormatException e) {
			response.sendRedirect("/OPWebApp/invalidvalues.html");
			//throw new IllegalArgumentException("Invalid input", e);
		}
		
		
	}
}
