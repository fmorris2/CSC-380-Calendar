package interfaces;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cloud.DBUserFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import user.User;

public class RecoveryController implements Initializable
{
	@FXML
	TextField usernameField;
	@FXML
	TextField questionField;
	@FXML
	TextField answerField;
	@FXML
	Label SystemMessage;
	User current;
	
	@FXML
	private void submitHandle(ActionEvent event)
	{
		if(current != null)
		{
			if (current.getSecurityAnswer().equals(answerField.getText()))
			{
				sendRecoveryMessage();
				SystemMessage.setText("Recovery message sent.");
			}
			else
			{
				SystemMessage.setText("Sorry, that answer is incorrect.");
			}
		}
		else
			SystemMessage.setText("Please enter your username and click \"Get Question\"");
	}
	
	@FXML
	private void questionHandle(ActionEvent event)
	{
		String username = usernameField.getText();
		if(!username.equals(""))
		{
			current = DBUserFunctions.getUser(username);
			String question = current.getSecurityQuestion();
			questionField.setText(question);
		}
		else
			SystemMessage.setText("Please enter your username.");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}
	
	private boolean sendRecoveryMessage()
	{
		// Recipient's email id
		String to = current.getEmail();
		
		// Sender's email id
		final String from = "taskorganizer.recoverysender@gmail.com";
		final String password = "sunyoswego";
		
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
			message.setSubject("Task Organizer Recovery");
			
			message.setContent("<h1>Your Password is: " + current.getPassword() + "</h1>", "text/html");
			
			// Send message
			Transport.send(message);
			System.out.println("Messsage was successfully sent...");
			return true;
			
		}
		catch (MessagingException mex)
		{
			return false;
		}
	}
}
