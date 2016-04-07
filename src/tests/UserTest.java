package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import task.Task;
import user.User;

/**
 * This it the UserTest class. It uses JUnit to test various aspects of the User
 * class.
 * 
 * @author Mike Mekker
 */
public class UserTest
{
	// Create User, Group, and Task to test with
	User user = new User();
	Task t = new Task();
	
	@Test
	public void testAddNewTask()
	{
		/** Add new task to user (addNewTask) */
		user.addNewTask(t);
		ArrayList<Task> tArr = new ArrayList<Task>();
		tArr.add(t);
		// Check if task was added
		assertEquals(tArr, user.getTasks());
	}
	
	@Test
	public void testCompleteTask()
	{
		/** Complete task (completeTask) */
		user.completeTask(t);
		ArrayList<Task> tArr = new ArrayList<Task>();
		// Check if task was removed from tasks
		assertEquals(tArr, user.getTasks());
		// Check if task was added to completed tasks
		tArr.add(t);
		assertEquals(tArr, user.getCompletedTasks());
	}
	
	@Test
	public void testRemoveTask()
	{
		/** Remove task */
		user.addNewTask(t);
		user.removeTask(t);
		// Check if task was removed
		ArrayList<Task> tArr = new ArrayList<Task>();
		assertEquals(tArr, user.getTasks());
		// Remove task from completed tasks
		user.completeTask(t);
		user.removeCompletedTask(t);
		// Check that it was removed
		assertEquals(tArr, user.getCompletedTasks());
	}
	
}
