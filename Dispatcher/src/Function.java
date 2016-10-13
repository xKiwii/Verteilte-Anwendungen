/**
 * Implements the F interface and add the parameters to itself.
 * 
 * @author Jenny
 *
 */
public class Function implements F{
	
	/**
	 * Adds the parameter to itselfs.
	 * 
	 * @param x Parameter
	 * @return Result of addition
	 * @see F#f(int)
	 */
	public int f(int x){
		return x+x;
	}

}
