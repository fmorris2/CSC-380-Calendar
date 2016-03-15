package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import group.Group;
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
	Group g = new Group();
	Task t = new Task();
	
	/**
	 * Creates a user object. The User object is then tested by: - Adding a
	 * group to the User's list of group - Removing a group from the User's list
	 * of groups - Add a new task - Complete a task - Remove task - Remove
	 * completed task
	 */
	@Test
	public void testAddGroup()
	{
		user.addGroup(g);
		// Check if new group has been added
		ArrayList<Group> gArr = new ArrayList<Group>();
		gArr.add(g);
		assertEquals(gArr, user.getGroups());
	}
	
	@Test
	public void testRemoveGroup()
	{
		/** Remove group from user (removeGroup) */
		user.addGroup(g);
		user.removeGroup(g);
		// Check if group was removed
		ArrayList<Group> gArr = new ArrayList<Group>();
		assertEquals(gArr, user.getGroups());
	}
	
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
