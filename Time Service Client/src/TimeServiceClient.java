import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Main class taking care of program execution. 
 * ClientProgram which asks for current time and date from a TimeService.
 * 
 * @author Jenny
 *
 */
public class TimeServiceClient {
	
	/**
	 * Entrance point for program execution.
	 * Calls and prints {@link #dateFromServer(String)} and {@link #timeFromServer(String)}
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		
		System.out.println(dateFromServer("127.0.0.1"));
		System.out.println(timeFromServer("127.0.0.1"));
		
	}
	
	/**
	 * Calls {@link #getMessageFromServer(String, String)} and returns current Date from the TimeService
	 * 
	 * @param serverAddress IP Address or hostname of the TimeService Server
	 * @return Result of {@link #getMessageFromServer(String, String)}: Current Date from the TimeService
	 */
	public static String dateFromServer(String serverAddress){
			
		return getMessageFromServer(serverAddress, "date");
	}
	
	
	/**
	 * Calls {@link #getMessageFromServer(String, String)} and returns current Time from the TimeService
	 * 
	 * @param serverAddress IP Address or hostname of the TimeService Server
	 * @return Result of {@link #getMessageFromServer(String, String)}: Current Time from the TimeService
	 */
	public static String timeFromServer(String serverAddress){
		
		return getMessageFromServer(serverAddress, "time");
	}
	
	
	/**
	 * Connects to the server and sends a message/request to it and receive the respond
	 * 
	 * @param serverAddress IP Address or hostname of the TimeService Server
	 * @param command Message to the server/ Request
	 * @return Respond of the TimeService
	 */
	private static String getMessageFromServer(String serverAddress, String command){
		
		String serverMessage = "";
		
		try {
			Socket socket = new Socket(serverAddress, 75);
			
			BufferedWriter writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			serverMessage = reader.readLine();
			
			if(!serverMessage.equals("time service")){
				System.out.println("Wrong Service");
				throw new IOException();
			}
			
			writer.write(command);
			writer.newLine();
			writer.flush();
			
			serverMessage = reader.readLine();
			
			writer.write("end");
			writer.close();
			
			socket.close();
			
			
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + serverAddress);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + serverAddress);
		    System.exit(1);
		}
		
		
		return serverMessage;
	}

}

//TODO: evtl. CommandLine nutzen

