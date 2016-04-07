package reminders;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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

import task.Task;
import user.User;
import cloud.BlobHandler;
import cloud.CloudManager;


/*
 * 
 * @author Noah Pierce
 */

public class BasicSender {

	public static void main(String[] args) 
	{
		Map<Task, List<Reminder>> reminders = getAllReminders();
		
		for(Task t : reminders.keySet())
			System.out.println(reminders.get(t).size());
		/*
		User user = new User();
		Task task = new Task();
		sendReminder(user, task);
		*/
		
	}
	
	@SuppressWarnings("unchecked")
	private static Map<Task, List<Reminder>> getAllReminders()
	{
		Map<Task, List<Reminder>> reminders = new HashMap<>();
		
		try
		{
			Statement st = CloudManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM users");
			
			while(rs.next())
			{
				Blob tasksBlob = rs.getBlob("tasks");
				Object unchecked = BlobHandler.deserialize(tasksBlob.getBinaryStream());
				List<Task> tasks = unchecked == null ? new ArrayList<>() : (List<Task>)unchecked;
				for(Task t : tasks)
				{
					reminders.putIfAbsent(t, new ArrayList<Reminder>());
					List<Reminder> entry = reminders.get(t);
					
					for(Reminder r : t.getReminders())
						entry.add(r);
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return reminders;
	}
	
	/*
	 * 
	 */
	private static void sendReminder(User user, Task task) {
		//Recipient's email id
		String to = user.getEmail();
				
		//Sender's email id
		final String from = "noyboy125@gmail.com";
		final String password = "gameboy125";
				
		//Determine host
		//String host = "localhost";
				
		//Get system properties
		Properties props = System.getProperties();
				
		//Setup mail server
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
				
				
				
		try{
			//Create a message
			MimeMessage message = new MimeMessage(session);
					
			//Set sender
			message.setFrom(new InternetAddress(from));
					
			//Set receiver
			message.setRecipients(Message.RecipientType.TO, to);
			
					
			//Set subject
			message.setSubject(task.getTaskName() + " due at " + task.getDueDate());
					
			//Set the message
			//Date date = new Date();
			message.setContent("<h1>Your task: " + task.getTaskName() + " needs to be done by " + task.getDueDate() + "</h1> \n" + task.getTaskDescription(), "text/html" );
			
					
			//Send message
			Transport.send(message);
			System.out.println("Messsage was successfully sent...");
				
		} catch (MessagingException mex) {
					mex.printStackTrace();
		}
	}

}
