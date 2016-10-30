import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Worker Thread to handle an incoming connection.
 * 
 * @author Jenny
 *
 */
public class HandleConnection implements Runnable {

	/**
	 * Socket that the client is connected to
	 */
	private Socket client;
	
	/**
	 * Creates a new HandleConnection object and initializes variables.
	 * 
	 * @param clientSocket Socket that the client is connected to
	 */
	public HandleConnection(Socket clientSocket){
		this.client = clientSocket;
		
	}
	
	/**
	 * Calls HandleConnection#{@link HandleConnection}.
	 * 
	 *
	 * @see		Thread#run()
	 */
	@Override
	public void run() {
		
		try {
			handleConnection(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

	/**
	 * Is hearing for line commands and handles them.
	 * 
	 * @param client The socket that the client is connected to.
	 * @throws IOException
	 */
	private void handleConnection(Socket client) throws IOException{
		
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
		
		writer.close();
		
		
		if(client != null) {
			try { 
				System.out.println(client.getRemoteSocketAddress() + ": Verbindung getrennt!");
				client.close();
			} 
			catch (IOException e) {}
		} 
		
	}

}
