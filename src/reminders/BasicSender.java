package reminders;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cloud.CloudManager;
import cloud.DBUserFunctions;
import task.Task;
import user.User;

/**
 * 
 * @author Noah Pierce
 */

public class BasicSender
{
	
	public static void main(String[] args) throws InterruptedException
	{
		while (true)
		{
			Map<User, List<Task>> reminders = getAllReminders();
			
			for (User u : reminders.keySet())
			{
				System.out.println(reminders.get(u).size());
				for (Task t : u.getTasks())
				{
					for (int i = 0; i < t.getReminders().size(); i++)
					{
						
						 Reminder r = t.getReminders().get(i);
						 
						 //IF REMINDER NEEDS TO BE SENT 
						 if(r.remindTime())
							 sendReminder(u, t); t.removeReminder(u, i);
						 
					}
				}
			}
			Thread.sleep(30000);
		}
	}
	
	private static Map<User, List<Task>> getAllReminders()
	{
		Map<User, List<Task>> reminders = new HashMap<>();
		
		try
		{
			Statement st = CloudManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM users");
			
			while (rs.next())
			{
				User user = new User();
				
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				DBUserFunctions.login(user);
				
				reminders.putIfAbsent(user, user.getTasks());
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return reminders;
	}
	
	/*
	 * 
	 */
	private static void sendReminder(User user, Task task)
	{
		// Recipient's email id
		String to = user.getEmail();
		
		// Sender's email id
		final String from = "taskorganizer.remindersender@gmail.com";
		final String password = "sunyoswego";
		
		// Determine host
		// String host = "localhost";
		
		// Get system properties
		Properties props = System.getProperties();
		
		// Setup mail server
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(from, password);
			}
		});
		
		try
		{
			// Create a message
			MimeMessage message = new MimeMessage(session);
			
			// Set sender
			message.setFrom(new InternetAddress(from));
			
			// Set receiver
			message.setRecipients(Message.RecipientType.TO, to);
			
			// Set subject
			message.setSubject(task.getTaskName() + " due at " + task.getDueDate());
			
			// Set the message
			// LocalDateTime date = task.getDueDate();
			
			message.setContent("<h1>Your task: " + task.getTaskName() + " needs to be done by " + task.getDueDate()
					+ "</h1> \n" + task.getTaskDescription(), "text/html");
			
			// Send message
			Transport.send(message);
			System.out.println("Messsage was successfully sent...");
			
		}
		catch (MessagingException mex)
		{
			mex.printStackTrace();
		}
	}
	
}
