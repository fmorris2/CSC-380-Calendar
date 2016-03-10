package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import group.Group;
import task.Task;
import user.Permissions;
import user.User;


public class GroupTest {

	@Test
	public void test() {

		
		//Create test group
		Group test = new Group();
		
		//Assert that test group was created properly
		if(!"Test Group".equals(test.getGroupName()))
			fail("Test Group failed creation (Group())");
		
		//Check if new members can be added to the group
		User userTest = new User("Bob", "Greg", "Username", "Password", "Email");
		test.addUser(userTest, true, true, true);
		if(!test.getGroupMembers().contains(userTest))
			fail("User was not added to group list (addUser)");
		if(!test.getUserPermissions().containsKey(userTest))
			fail("New user was not added to permissions list (addUser)");
		
		//Check the permissions of the newly added user
		assertTrue("The permissions of a new user did not match up (setPermissions)", test.getUserPermissions().get(userTest).getCanChangeTasks());
		assertTrue("The permissions of a new user did not match up (setPermissions)", test.getUserPermissions().get(userTest).getCanCompleteTasks());
		assertTrue("The permissions of a new user did not match up (setPermissions)", test.getUserPermissions().get(userTest).getCanCreateTasks());
		
		//Add a new task
		Task task = new Task();
		test.addGroupTask(userTest, task);
		if (!test.getGroupTasks().contains(task))
			fail("Failed to add task (addGroupTask");
		
		//Change permissions of a user
		Permissions change = new Permissions(true, false, true);
		test.setPermissions(userTest, true, false, true);
		if(test.getUserPermissions().get(userTest).equals(change))
			fail("Failed to set permissions of a users (setPermissions)");
		
		//Remove user from group
		test.removeUser(userTest);
		
		//Check to make sure user was taken out of group
		if(test.getGroupMembers().contains(userTest))
			fail("User was not removed (removeUser)");
		
		//Check to make sure user has lost their permissions
		if(test.getUserPermissions().containsKey(userTest))
			fail("Failed to revoke user permisssions (removeUser)");
		
		
		
		//Remove a task
		test.removeGroupTask(task);
		if (test.getGroupTasks().contains(task))
			fail ("Task remove failed (removeGroupTask");
		
		
	}

}
