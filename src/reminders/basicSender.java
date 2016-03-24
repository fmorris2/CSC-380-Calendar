package reminders;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import task.Task;
import user.User;

import javax.activation.*;

/*
 * 
 * @author Noah Pierce
 */

public class basicSender {

	public static void main(String[] args) {
		User user = new User();
		Task task = new Task();
		sendReminder(user, task);
		
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
		String host = "localhost";
				
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
			Date date = new Date();
			message.setContent("<h1>Your task: " + task.getTaskName() + " needs to be done by " + task.getDueDate() + "</h1> \n" + task.getTaskDescription(), "text/html" );
			
					
			//Send message
			Transport.send(message);
			System.out.println("Messsage was successfully sent...");
				
		} catch (MessagingException mex) {
					mex.printStackTrace();
		}
	}

}
