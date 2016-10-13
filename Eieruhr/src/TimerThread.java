/**
 * TimerThread class which displays a message after a given amount of time. 
 * While counting down, the remaining time in seconds will be displayed too.
 * The sleeping is non-blocking as the sleeps runs in its own thread.
 * 
 * @author Jenny
 *
 */

public class TimerThread extends Thread{
	
	/**
	 * Name of the TimerThread object for better identification.
	 */
	private String name = "";
	
	/**
	 * Time in seconds to wait.
	 */
	private int timeInSeconds = 0;
	
	/**
	 * Message displayed when time is up.
	 */
	private String message = "";
	
	
	
	/**
	 * Creates a new TimerThread object and initializes variables.
	 * 
	 * @param sec Time in seconds to wait.
	 * @param message Message displayed when time is up.
	 * @param name Name of the TimerThread object for better identification.
	 */
	public TimerThread(int sec, String message, String name){
		this.name = name;
		this.timeInSeconds = sec;
		this.message = message;
		
	}	
	
	/**
	 * Calls TimerThread#schlafen(int) to wait and displays the message.
	 * Print name of the object and seconds left every second before displaying the message.
	 *
	 * @see		Thread#run()
	 */
	@Override
	public void run() {
		for(int i=0; i<timeInSeconds; i++)
		{
			System.out.println(name + ": " + Integer.toString(timeInSeconds - i)  +" seconds left.");
			schlafen(1);
		}
		
		System.out.println(name + " done. Message: "+ message);
	}
	
	/**
	 * Sleeps given seconds and catches all InterruptedExceptions.
	 *
	 * @param	m		Time in seconds to wait.
	 */
	public static void schlafen(int m) {
		try {
			Thread.sleep(1000*m);
		} catch (InterruptedException t) {
			
		}
	}
	
	
	

}
