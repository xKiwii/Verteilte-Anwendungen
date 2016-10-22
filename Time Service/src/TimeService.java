import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main class taking care of program execution. 
 * Creates a server socket and handles connections.
 * 
 * @author Jenny
 *
 */
public class TimeService {
	
	/**
	 * Server port this service is listening on.
	 */
	public static final int PORT = 75;

	/**
	 * Entrance point for the program execution.
	 * Creates a sever socket which is listening on port (constant #PORT) 
	 * Blocks until a connection is established and only handles one connection at once.
	 * 
	 * @param args Command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		
		ServerSocket serverSocket = new ServerSocket(PORT);
		
		while(true) {
			Socket client = null;
			
			try {
				client = serverSocket.accept();
				handleConnection(client);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				 if(client != null) {
					try { 
						client.close(); 
						System.out.println("Verbindung getrennt!");
					} 
					catch (IOException e) {}
				} 
					
			}
		}
		
	}
	
	
	/**
	 * Is hearing for line commands and handles them.
	 * 
	 * @param client The server that the client is connected to.
	 * @throws IOException
	 */
	private static void handleConnection(Socket client) throws IOException{
		
		OutputStream output = client.getOutputStream();
		BufferedWriter writer= new BufferedWriter(new OutputStreamWriter(output));
		
		InputStream input = client.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
		writer.write("time service");
		writer.newLine();
		writer.flush();
		
		boolean connection = true;
		String userInput;
		
		
		while(connection && (userInput = reader.readLine()) != null){
			
			switch(userInput) {
			case "date":
				writer.write(Clock.date());
				break;
				
			case "time":
				writer.write(Clock.time());
				break;
				
			case "end":
				connection = false;
				break;
				
			default:
				writer.write("Invalid command. Connection closed.");
				connection = false;
				break;
			}
			
			writer.newLine();
			writer.flush();
		}
	}
}
