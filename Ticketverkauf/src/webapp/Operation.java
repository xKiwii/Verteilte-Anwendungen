package webapp;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Operation
 */
@WebServlet("/Operation")
public class Operation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Operation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Kartenverkauf kartenverkauf = (Kartenverkauf) request.getServletContext().getAttribute("kartenverkauf");
			
			String operation = (String) request.getParameter("operation");
			
			
			int sitzplatznummer;
			String name;
			
			String operationCompleteHTML = "/Operation_erfolgreich_ausgefuehrt.html";
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(operationCompleteHTML);
			
			switch(operation){
				case "verkaufFreiesTicket":
					sitzplatznummer = Integer.parseInt(request.getParameter("sitzplatznummer")) - 1;
					kartenverkauf.verkaufEinesTicket(sitzplatznummer);
					break;
				case "reservierungTicket":
					sitzplatznummer = Integer.parseInt(request.getParameter("sitzplatznummer")) - 1;
					name = (String) request.getParameter("name");
					kartenverkauf.reservierungEinesTickets(sitzplatznummer, name);
					break;
				case "verkaufReserviertesTicket":
					sitzplatznummer = Integer.parseInt(request.getParameter("sitzplatznummer")) - 1;
					name = (String) request.getParameter("name");
					kartenverkauf.verkaufEinesReserviertenTickets(sitzplatznummer, name);
					break;
				case "stornierungTicket":
					sitzplatznummer = Integer.parseInt(request.getParameter("sitzplatznummer")) - 1;
					kartenverkauf.stornierungEinesTickets(sitzplatznummer);
					break;
				case "reservierungAufheben":
					kartenverkauf.reservierungenAufheben();
					break;
				default:
					throw new OperationErrorException(Errorcodes.OPERATION_NICHT_GUELTIG);
			}
			
			dispatcher.forward(request,response);
		} catch (NumberFormatException e) {
		    throw new OperationErrorException(Errorcodes.OPERATION_NICHT_GUELTIG, e);
		}
		
	}

}
