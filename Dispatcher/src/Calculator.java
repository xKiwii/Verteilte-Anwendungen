/**
 * Wraps the execution of a given function implementing the F interface in a new thread.
 * 
 * @author Jenny
 *
 */
public class Calculator extends Thread {

	/**
	 * X value parameter for calculation
	 */
	private int x;
	
	/**
	 * Function object
	 */
	private Function f;
	
	/**
	 * ResultCollector that saves the result
	 */
	private ResultCollector resultCollector;
	
	
	/**
	 * Creates a new Calculator object and initializes fields.
	 * 
	 * @param x X value parameter for calculation
	 * @param f Function object
	 * @param resultCollector ResultCollector object
	 */
	public Calculator(int x, Function f, ResultCollector resultCollector){
		this.x = x;
		this.f = f;
		this.resultCollector = resultCollector;
	}
	
	
	
	/**
	 * Calls Function#f(int), prints result of calculation and gives it to the ResultCollector.
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

		int result = f.f(x);
		System.out.println("f("+x+") = " + result);
		resultCollector.addResult(x, result);
		
	}
	
}
