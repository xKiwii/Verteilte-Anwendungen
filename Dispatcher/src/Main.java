import java.util.Arrays;

/**
 * Main class taking care of program execution.
 * 
 * @author Jenny
 *
 */
public class Main {
	
	

	/**
	 * Entrance point for the program execution.
	 * Creates function object and calls #execute(F, int) method
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {

		Function f = new Function();
		int[] calculation = execute(f,5);
		
		System.out.println("Result: " + Arrays.toString(calculation));
		
	}
	
	
	/**
	 * Creates threads for calculation and collects and returns all calculation results.
	 * 
	 * @param f Function object
	 * @param n Amount of calculation runs.
	 * @return Array of all calculation results.
	 */
	public static int[] execute(Function f, int n){
		
		ResultCollector resultCollector = new ResultCollector(n);
		
		for(int i=0; i<n; i++)
		{
			Calculator calculatorThread = new Calculator(i,f,resultCollector);
			calculatorThread.start();
		}
	
		int[] results = resultCollector.getResult();
		
		return results;
	}

}
