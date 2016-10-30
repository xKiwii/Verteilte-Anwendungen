import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clock class providing the formatted current date and time.
 * 
 * @author Jenny
 *
 */
public class Clock {

	/**
	 * Time format: kk:mm:ss
	 */
	private static SimpleDateFormat timeFormatter = new SimpleDateFormat("kk:mm:ss");
	
	/**
	 * Date format: dd.MM.yyyy
	 */
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
	
	/**
	 * Date Object
	 */
	private static Date d = new Date();
	
	
	/**
	 * Returns the formatted current date. Format is specified by {@link #dateFormatter}
	 * 
	 * @return Formatted date
	 */
	public static String date() {
		d.setTime(System.currentTimeMillis());
		return dateFormatter.format(d);
	}
	
	/**
	 * Returns the formatted current time. Format is specified by {@link #timeFormatter}
	 * 
	 * @return Formatted time
	 */
	public static String time() {
		d.setTime(System.currentTimeMillis());
		return timeFormatter.format(d);
	}
}
