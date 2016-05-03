package task;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cloud.DBUserFunctions;
import reminders.Interval;
import reminders.Reminder;
import user.User;

/**
 * This is the Task class that is used to store the various tasks the user would
 * like to save.
 * 
 * @author Mike Mekker
 */
public class Task implements Serializable
{
	private static final long serialVersionUID = 1181418367685770531L;
	
	private LocalDateTime creationDate;
	private LocalDateTime dueDate;
	private Duration duration;
	private String taskName;
	private String taskDescription;
	private String category;
	private Priority priority;
	private List<Reminder> reminders;
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
		this.reminders = new ArrayList<Reminder>();
		this.completed = "";
		
	}
	
	public Reminder addReminder(User user, Interval interval)
	{
		LocalDateTime dueDate = getDueDate();
		Reminder reminder = new Reminder(dueDate, interval);
		reminders.add(reminder);
		DBUserFunctions.saveBlob(user, "tasks", user.getTasks());
		return reminder;
	}
	
	public void removeReminder(User user, Reminder r)
	{
		reminders.remove(r);
		DBUserFunctions.saveBlob(user, "tasks", user.getTasks());
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
	
	public List<Reminder> getReminders()
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
