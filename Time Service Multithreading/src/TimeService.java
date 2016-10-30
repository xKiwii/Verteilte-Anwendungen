import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
	 * Uses ThreadPool to handle multiple connections at once. Working Threads: HandleConnection.
	 * 
	 * @param args Command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		
		ServerSocket serverSocket = new ServerSocket(PORT);
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		
		while(true) {
			Socket client = null;
			
			try {
				client = serverSocket.accept();
				
				HandleConnection connection = new HandleConnection(client);
				executor.execute(connection);
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}
