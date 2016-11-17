import java.util.Date;
import java.util.HashMap;

/**
 * @author Will Booker
 * A class used to time a process. Use Clock.in(object) to 
 * start the timer and Clock.out(object) to get the time.
 */
public class Clock {
	
	public static HashMap<Object, Date> datemap = new HashMap<Object, Date>();
	
	/**
	 * Starts the object timer.
	 * @param object -- The key for the clock-in, use this object to clock out
	 */
	public static void in(Object object) {
		datemap.put(object, new Date());
	}
	
	/**
	 * Ends the timer and returns the number of seconds elapsed.
	 * @param object -- They key from the clock-in. Closes the given timer.
	 * @return the total time elapsed from the clock.in()
	 */
	public static long out(Object object) {
		Date now = new Date();
		Date then = datemap.get(object);
		datemap.remove(object);
		if(then == null) return -1;
		return now.getTime() - then.getTime();
	}
	
	/**
	 * Overloads the in() method. See in(null).
	 */
	public static void in() {Clock.in(null);}
	
	/**
	 * Overloads the out() method. See out(null).
	 * @return the total time elapsed from the clock.in()
	 */
	public static long out() {return Clock.out(null);}

}
