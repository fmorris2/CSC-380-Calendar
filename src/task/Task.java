package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Stack;

import reminders.Interval;
import reminders.Reminder;



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
	private ArrayList<Reminder> reminders;
	private String completed;
	
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
		this.reminders = new ArrayList<Reminder>();
		this.completed = "";

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
		this.reminders = new ArrayList<Reminder>();
		this.completed = "";
		
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

	public Reminder addReminder(Interval interval){
		LocalDateTime dueDate = getDueDate();
		Reminder reminder = new Reminder(dueDate, interval);
		reminders.add(reminder);
		return reminder;
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
	
	public ArrayList<Reminder> getReminders()
	{
		return reminders;
	}
	
	public void setCreationDate(LocalDateTime creationDate)
	{
		this.creationDate = creationDate;
	}

	public void setDueDate(LocalDateTime dueDate)
	{
		this.dueDate = dueDate;
	}

	public void setDuration(Duration duration)
	{
		this.duration = duration;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	public void setTaskDescription(String taskDescription)
	{
		this.taskDescription = taskDescription;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public void setPriority(Priority priority)
	{
		this.priority = priority;
	}

	public void setComments(Stack<Comment> comments)
	{
		this.comments = comments;
	}

	public void setReminders(ArrayList<Reminder> reminders)
	{
		this.reminders = reminders;
	}

	public void setCompleted(String string)
	{
		this.completed = string;
	}
	
	public String getCompleted()
	{
		return this.completed;
	}
}
