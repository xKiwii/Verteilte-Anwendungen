/**
 * Main class taking care of program execution.
 * 
 * @author Jenny
 *
 */

public class Main {
	
	/**
	 * Entrance point for the program execution. 
	 *
	 * @param	args		Command line arguments
	 */
	public static void main(String[] args) {

		eieruhr(10,"Funktioniert", "Eieruhr 1");
		eieruhr(3,"Funktioniert auch!", "Eieruhr 2");
			
	}
	
	
	/**
	 * Create and starts a new TimerThread.
	 * 
	 * @param sec Time in seconds to wait
	 * @param message Message displayed when time is up.
	 * @param name Name of the TimerThread for better identification.
	 */
	public static void eieruhr(int sec, String message, String name){
		
		TimerThread t = new TimerThread(sec, message, name);
		t.start();
		
	}

}
