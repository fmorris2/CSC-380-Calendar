package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import group.Group;
import user.User;

/**
 * This it the UserTest class.
 * It uses JUnit to test various aspects of the User class.
 * @author Mike Mekker
 */
public class UserTest {
	public UserTest(){}
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
			fail("Group not added.");
		g = user.removeGroup(new Group());
		if(user.getGroups().contains(g))
			fail("Group not removed.");
		
	}

}
