package tests;

import org.junit.Test;

import reminders.Interval;
import reminders.Reminder;
import task.Task;
import user.User;

/**
 * This it the TaskTest class. It uses JUnit to test various aspects of the Test
 * class.
 * 
 * @author Mike Mekker
 */
public class TaskTest
{
	/**
	 * Creates a task object to test. Task object is tested by: - Adding a
	 * comment
	 */
	@Test
	public void testAddReminder()
	{
		// Create new task to test
		Task t = new Task();
		// Add a new comment to that task
		Reminder r = t.addReminder(new User(), Interval.FIFTEEN_MINUTES);
		assert (t.getReminders().contains(r));
	}
}
