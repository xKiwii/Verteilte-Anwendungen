/**
 * Holds all collected results. Works thread-safe.
 * 
 * @author Jenny
 *
 */
public class ResultCollector {
	
	/**
	 * Array to save results.
	 */
	private int[] results;
	
	/**
	 * Amount of results to be saved.
	 */
	private int n;
		
	/**
	 * Counts down when a new result is collected. Reaches 0 if n results are collected.
	 */
	private int counter;
	
	
	
	/**
	 * Creates a new ResultCollector objects and initializes fields.
	 * 
	 * @param n Amount of results to be saved
	 */
	public ResultCollector(int n){
		this.n = n;
		this.results = new int[n];
		this.counter = n;
	}
	
	/**
	 * Saves a given value on position i in the result Array. For every value saved the counter goes down.
	 * If counter hits 0, there is a notify.
	 * This method is thread safe
	 * 
	 * @param i Position in array.
	 * @param result Value to be saved as result.
	 */
	public synchronized void addResult(int i, int result){
		
		if( (i > -1) && (i < this.n)){
			this.results[i] = result;
			
			if(this.counter > 0) {
				this.counter--;
			}
			
			if(this.counter == 0){
				notify();
			}
		}
	}
	
	/**
	 * Returns the array which saves the results. If not all results are collected if waits for a notify.
	 * This method is thread-safe.
	 * 
	 * @return Complete result array.
	 */
	public synchronized int[] getResult(){
		if(this.counter > 0) {
			try {
				wait();
			} catch (InterruptedException e) {

			}
		}
		
		return this.results;
	}
	
}
