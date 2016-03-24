package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Stack;



/**
 * This is the Task class that is used to store the various tasks the user would
 * like to save.
 * 
 * @author Mike Mekker
 */
public class Task
{
	private LocalDateTime creationDate;
	private LocalDateTime dueDate;
	private Duration duration;
	private String taskName;
	private String taskDescription;
	private String category;
	private Priority priority;
	private Stack<Comment> comments;
	
	/**
	 * Default Constructor for testing Task class
	 */
	public Task()
	{
		this.creationDate = LocalDateTime.now();
		this.dueDate = LocalDateTime.now();
		this.duration = Duration.ofHours(1);
		this.taskName = "Test Task";
		this.taskDescription = "This is a test task used for testing";
		this.category = "TEST";
		this.priority = Priority.HIGH;
		this.comments = new Stack<Comment>();
		
	}
	
	/**
	 * This is a parameterized constructor for Task
	 */
	public Task(LocalDateTime dueDate, Duration duration, String taskName, String taskDescription, String category,
			Priority priority)
	{
		this.dueDate = dueDate;
		this.duration = duration;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.category = category;
		this.priority = priority;
		this.creationDate = LocalDateTime.now();
		this.comments = new Stack<Comment>();
		
	}
	
	/**
	 * This method adds a comment object to the comment stack
	 * 
	 * @param author
	 *            - Author of the comment
	 * @param date
	 *            - The time of which the comment was made
	 * @param comment
	 *            - The comment to be stored
	 * @return - Copy of the comment
	 */
	public Comment addComment(String author, LocalDateTime date, String comment)
	{
		Comment c = new Comment(author, date, comment);
		comments.push(c);
		return c;
	}
	
	/* Getters & Setters */
	public LocalDateTime getCreationDate()
	{
		return creationDate;
	}
	
	public LocalDateTime getDueDate()
	{
		return dueDate;
	}
	
	public String getTaskName()
	{
		return taskName;
	}
	
	public String getTaskDescription()
	{
		return taskDescription;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public Priority getPriority()
	{
		return priority;
	}
	
	public Duration getDuration()
	{
		return duration;
	}
	
	public Stack<Comment> getComments()
	{
		return comments;
	}
}
