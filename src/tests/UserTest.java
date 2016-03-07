package tests;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.Test;

import group.Group;
import task.Priority;
import task.Task;
import user.User;

/**
 * This it the UserTest class.
 * It uses JUnit to test various aspects of the User class.
 * @author Mike Mekker
 */
public class UserTest {
	/**
	 * Creates a user object.
	 * The User object is then tested by:
	 * 		- Adding a group to the User's list of group
	 * 		- Removing a group from the User's list of groups
	 * 		- Add a new task
	 * 		- Complete a task
	 * 		- Remove task
	 * 		- Remove completed task
	 */
	@Test
	public void test() {
		User user = new User("TestFirstName", "TestLastName", "TestUsername", "TestEmail@TestEmail.com");
		//Add group
		Group g = user.addGroup(new Group());
		if(!user.getGroups().contains(g))
			fail("Group not added. (addGroup)");
		g = user.removeGroup(new Group());
		if(user.getGroups().contains(g))
			fail("Group not removed. (removeGroup)");
		Task t = user.addNewTask(new Task(LocalDateTime.now(), Duration.ofHours(1), "Test Task", "This is a test task.", "Test category", Priority.HIGH));
		if(!user.getTasks().contains(t))
			fail("Task not added. (addNewTask)");
		if(!user.getCategories().contains("Test category"))
			fail("Category not added. (addNewTask)");
		t = user.completeTask(t);
		if(user.getTasks().contains(t))
			fail("Task not removed from list. (completeTask)");
		if(!user.getCompletedTasks().contains(t))
			fail("Task not added to completed tasks. (completeTask)");
		t = user.addNewTask(new Task(LocalDateTime.now(), Duration.ofHours(1), "Test Task", "This is a test task.", "Test category", Priority.HIGH));
		user.removeTask(t);
		if(user.getTasks().contains(t))
			fail("Task not removed. (removeTask)");
		t = user.addNewTask(new Task(LocalDateTime.now(), Duration.ofHours(1), "Test Task", "This is a test task.", "Test category", Priority.HIGH));
		user.removeCompletedTask(t);
		if(user.getCompletedTasks().contains(t))
			fail("Task not removed. (removeCompletedTask)");
	}

}
