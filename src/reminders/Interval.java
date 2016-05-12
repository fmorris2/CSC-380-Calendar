package reminders;

/**
 * @author Noah Pierce
 */
public enum Interval
{
	ONE_WEEK(10080), ONE_HOUR(60), TWO_HOURS(120), TWO_WEEKS(20160), HALF_HOUR(30), FIFTEEN_MINUTES(15), TEN_MINUTES(
			10), FIVE_MINUTES(5);
	
	private int interval;
	
	Interval(int interval)
	{
		this.interval = interval;
	}
	
	public int getInterval()
	{
		return interval;
	}
	
}
