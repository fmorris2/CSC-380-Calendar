package task;

import java.time.LocalDateTime;

public class Comment
{
	private String author;
	private LocalDateTime date;
	private String comment;
	
	public Comment(String a, LocalDateTime d, String c)
	{
		author = a;
		date = d;
		comment = c;
	}
	
	public String getComment()
	{
		return comment;
	}
	
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public LocalDateTime getDate()
	{
		return date;
	}
	
	public void setDate(LocalDateTime date)
	{
		this.date = date;
	}
}
