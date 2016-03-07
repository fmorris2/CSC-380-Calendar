package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Stack;

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
