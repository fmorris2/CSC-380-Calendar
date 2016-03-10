package group;

import java.util.ArrayList;
import java.util.HashMap;

import task.Task;
import user.Permissions;
import user.User;

public class Group
{
	private String groupName;
	private String groupDescription;
	private ArrayList<Task> groupTasks;
	private ArrayList<Task> groupCompletedTasks;
	private ArrayList<User> groupMembers;
	private User groupLeader;
	private HashMap<User, Permissions> permissions;
	
	/**
	 * The parameterized constructor for the group class.
	 * 
	 * @param leader
	 * @param name
	 * @param description
	 */
	public Group(User leader, String name, String description)
	{
		this.groupDescription = description;
		this.groupName = name;
		this.groupLeader = leader;
		this.groupMembers = new ArrayList<>();
		this.permissions = new HashMap<User, Permissions>();
		addUser(this.groupLeader, new Permissions(true, true, true));
		this.groupTasks = new ArrayList<Task>();
		this.groupCompletedTasks = new ArrayList<Task>();
		
	}
	
	/**
	 * A default constructor for testing the group class.
	 */
	public Group()
	{
		this.groupDescription = "Group Description";
		this.groupName = "Test Group";
		this.groupTasks = new ArrayList<Task>();
		this.groupCompletedTasks = new ArrayList<Task>();
		this.groupMembers = new ArrayList<User>();
		this.groupLeader = new User();
		this.permissions = new HashMap<User, Permissions>();
	}
	
	/**
	 * Adds a new task to the group task list
	 * 
	 * @param task
	 */
	public void addGroupTask(User user, Task task)
	{
		if (permissions.get(user).getCanCreateTasks())
		{
			this.groupTasks.add(task);
		}
	}
	
	public void completeGroupTask(User user, Task task)
	{
		if(permissions.get(user).getCanCompleteTasks())
		{
			this.groupTasks.remove(task);
			this.groupCompletedTasks.add(task);
		}
	}
	
	/**
	 * Removes a from the group task list
	 * 
	 * @param task
	 */
	public void removeGroupTask(Task task)
	{
		this.groupTasks.remove(task);
	}
	
	/**
	 * Method adds a new user to the group.
	 * 
	 * @param user
	 * @param change
	 * @param create
	 * @param complete
	 */
	public void addUser(User user, Permissions perm)
	{
		this.groupMembers.add(user);
		setPermissions(user, perm);
		user.addGroup(this);
	}
	
	private void setPermissions(User user, Permissions perm)
	{
		permissions.put(user, perm);
	}
	
	public void removeUser(User user)
	{
		this.groupMembers.remove(user);
		this.permissions.remove(user);
		user.removeGroup(this);
	}
	
	/**
	 * Changes permissions, and returns a new copy of the permissions list with
	 * changes.
	 * 
	 * @param user
	 * @param change
	 * @param create
	 * @param complete
	 * @return
	 */
	public void setPermissions(User user, boolean change, boolean create, boolean complete)
	{
		permissions.get(user).setCanChangeTasks(change);
		permissions.get(user).setCanCompleteTasks(complete);
		permissions.get(user).setCanCreateTasks(create);
	}
	
	// Other getters and setters
	
	public String getGroupName()
	{
		return groupName;
	}
	
	public void setGroupName(String groupName)
	{
		groupName = this.groupName;
	}
	
	public String getGroupDescription()
	{
		return groupDescription;
	}
	
	public void setGroupDescription(String groupDescription)
	{
		groupDescription = this.groupDescription;
	}
	
	public ArrayList<Task> getGroupTasks()
	{
		return groupTasks;
	}
	
	public void setGroupTasks(ArrayList<Task> groupTasks)
	{
		groupTasks = this.groupTasks;
	}
	
	public ArrayList<User> getGroupMembers()
	{
		return groupMembers;
	}
	
	public void setGroupMembers(ArrayList<User> groupMembers)
	{
		groupMembers = this.groupMembers;
	}
	
	public User getGroupLeader()
	{
		return groupLeader;
	}
	
	public void setGroupLeader(User groupLeader)
	{
		groupLeader = this.groupLeader;
	}
	
	public HashMap<User, Permissions> getUserPermissions()
	{
		return permissions;
	}
	
	public void setUserPermissions(HashMap<User, Permissions> permissions)
	{
		permissions = this.permissions;
	}
	
}