package user;

import java.util.ArrayList;
import group.Group;
import task.Task;

/**
 * This is the User class. It holds all the information
 * about a specific user such as:
 * 		- First and last name
 * 		- Username and password
 * 		- Email
 * 		- Security Question and Answer
 * 		- Groups that the user is a part of
 * 		- On going and completed tasks
 * 		- Custom categories the user has made
 * @author Mike Mekker
 *
 */
public class User
{
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String securityQuestion;
	private String securityAnswer;
	private ArrayList<Group> groups;
	private ArrayList<Task> tasks;
	private ArrayList<Task> completedTasks;
	private ArrayList<String> categories;
	
	
	public User(String firstName, String lastName, String username, String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.securityQuestion = "";
		this.securityAnswer = "";
		this.groups = new ArrayList<Group>();
		this.tasks = new ArrayList<Task>();
		this.completedTasks = new ArrayList<Task>();
		this.categories = new ArrayList<String>();
	}
	
	/**
	 * This method adds a Group object to the list
	 * of groups that the user is a member of.
	 * @param group - Group that the user is joining
	 * @return - Returns the group the user joined
	 */
	public Group addGroup(Group group)
	{
		this.groups.add(group);
		return group;
	}
	/**
	 * This method removes a Group object from the list
	 * of groups that the user is a member of.
	 * @param group - Group that the user is leaving
	 * @return - Returns the group the user left
	 */
	public Group removeGroup(Group group)
	{
		this.groups.remove(group);
		return group;
	}
	
	/**
	 * This method adds a new task object to the list of tasks.
	 * It also checks if the category of the new task is in the
	 * list of custom categories. If not then it adds the new category.
	 * @param task - Task to be added
	 * @return - Copy of added task
	 */
	public Task addNewTask(Task task)
	{
		this.tasks.add(task);
		if(!this.categories.contains(task.getCategory()))
			categories.add(task.getCategory());
		return task;
	}
	
	/**
	 * This method removes the completed task from the list
	 * of tasks and adds it to the list of completed tasks.
	 * If you are looking to just remove the task see removeTask(Task)
	 * @param task - task to be completed
	 * @return - returns completed task
	 */
	public Task completeTask(Task task)
	{
		this.tasks.remove(task);
		this.completedTasks.add(task);
		return task;
	}
	
	/**
	 * This method removes the given task from the tasks
	 * list. Does not complete task. For task completion see completeTask(Task).
	 * @param task - Task to be removed
	 * @return - returns the removed task.
	 */
	public Task removeTask(Task task)
	{
		this.tasks.remove(task);
		return task;
	}
	
	/**
	 * This method removes a task from the completed tasks list.
	 * This is used for if the user no longer wants a certain task
	 * to appear in their completed tasks section.
	 * @param task - task to be removed
	 * @return - returns the removed task
	 */
	public Task removeCompletedTask(Task task)
	{
		this.completedTasks.remove(task);
		return task;
	}
	
	/*Getters and Setters*/
	public String getFirstName() {return firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}
	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public String getSecurityQuestion() {return securityQuestion;}
	public void setSecurityQuestion(String securityQuestion) {this.securityQuestion = securityQuestion;}
	public String getSecurityAnswer() {return securityAnswer;}
	public void setSecurityAnswer(String securityAnswer) {this.securityAnswer = securityAnswer;}
	public ArrayList<Group> getGroups() {return groups;}
	public void setGroups(ArrayList<Group> groups) {this.groups = groups;}
	public ArrayList<Task> getTasks() {return tasks;}
	public void setTasks(ArrayList<Task> tasks) {this.tasks = tasks;}
	public ArrayList<Task> getCompletedTasks() {return completedTasks;}
	public void setCompletedTasks(ArrayList<Task> completedTasks) {this.completedTasks = completedTasks;}
	public ArrayList<String> getCategories() {return categories;}
	public void setCategories(ArrayList<String> categories) {this.categories = categories;}
}
