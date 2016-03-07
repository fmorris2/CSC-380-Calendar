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
		//Create new user to test
		User user = new User();
		//Add group to user
		Group g = user.addGroup(new Group());
		//Check if new group has been added
		if(!user.getGroups().contains(g))
			fail("Group not added. (addGroup)");
		//Remove group from user
		g = user.removeGroup(new Group());
		//Check if group was removed
		if(user.getGroups().contains(g))
			fail("Group not removed. (removeGroup)");
		//Add new task to user
		Task t = user.addNewTask(new Task(LocalDateTime.now(), Duration.ofHours(1), "Test Task", "This is a test task.", "Test category", Priority.HIGH));
		//Check if task was added
		if(!user.getTasks().contains(t))
			fail("Task not added. (addNewTask)");
		//Check if it added the new category
		if(!user.getCategories().contains("Test category"))
			fail("Category not added. (addNewTask)");
		//Complete task
		t = user.completeTask(t);
		//Check if task was removed from tasks
		if(user.getTasks().contains(t))
			fail("Task not removed from list. (completeTask)");
		//Check if task was added to completed tasks
		if(!user.getCompletedTasks().contains(t))
			fail("Task not added to completed tasks. (completeTask)");
		//Add another task
		t = user.addNewTask(new Task(LocalDateTime.now(), Duration.ofHours(1), "Test Task", "This is a test task.", "Test category", Priority.HIGH));
		//Check if duplicate category was added
		if(user.getCategories().size() > 1)
			fail("Added duplicate category. (addNewTask)");
		//Remove task
		user.removeTask(t);
		//Check if task was removed
		if(user.getTasks().contains(t))
			fail("Task not removed. (removeTask)");
		//Remove task from completed tasks
		user.removeCompletedTask(t);
		//Check that it was removed
		if(user.getCompletedTasks().contains(t))
			fail("Task not removed. (removeCompletedTask)");
	}

}
