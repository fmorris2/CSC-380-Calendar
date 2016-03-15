package user;

public class Permissions
{
	// Variables
	private boolean canChangeTasks;
	private boolean canCreateTasks;
	private boolean canCompleteTasks;
	
	// Constructors
	public Permissions(Boolean change, Boolean create, Boolean complete)
	{
		canChangeTasks = change;
		canCreateTasks = create;
		canCompleteTasks = complete;
	}
	
	public boolean getCanChangeTasks()
	{
		return canChangeTasks;
	}
	
	public void setCanChangeTasks(boolean canChangeTasks)
	{
		this.canChangeTasks = canChangeTasks;
	}
	
	public boolean getCanCreateTasks()
	{
		return canCreateTasks;
	}
	
	public void setCanCreateTasks(boolean canCreateTasks)
	{
		this.canCreateTasks = canCreateTasks;
	}
	
	public boolean getCanCompleteTasks()
	{
		return canCompleteTasks;
	}
	
	public void setCanCompleteTasks(boolean canCompleteTasks)
	{
		this.canCompleteTasks = canCompleteTasks;
	}
}