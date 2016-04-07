package task;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment implements Serializable
{
	private static final long serialVersionUID = 7800271263460167807L;
	
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
