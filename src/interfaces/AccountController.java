package interfaces;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cloud.DBUserFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import user.User;

/**
 * Account creation and editing screen. Takes input from the user and either
 * edits or creates the account.
 * 
 * @author Mike Mekker
 *
 */
public class AccountController implements Initializable
{
	@FXML
	TextField firstName;
	@FXML
	TextField lastName;
	@FXML
	TextField username;
	@FXML
	TextField email;
	@FXML
	PasswordField password;
	@FXML
	PasswordField passwordConfirmation;
	@FXML
	TextField secQ;
	@FXML
	TextField secA;
	@FXML
	Label SystemMessage;
	
	/**
	 * onEnter(ActionEvent) - Redirects the event to the normal submit listener.
	 * 
	 * @param event
	 */
	@FXML
	public void onEnter(ActionEvent event)
	{
		handleCreateAccountSubmitListener(event);
	}
	
	/**
	 * - Takes values from all fields - Checks that they are all valid - Checks
	 * if the window is for creating an account or editing one - Either creates
	 * or edits an account - Saves account to server
	 * 
	 * @param event
	 */
	@FXML
	private void handleCreateAccountSubmitListener(ActionEvent event)
	{
		try
		{
			// Take in values
			String first = firstName.getText();
			String last = lastName.getText();
			String nUsername = username.getText();
			String nEmail = email.getText();
			String pass = password.getText();
			String pass2 = passwordConfirmation.getText();
			String sQ = secQ.getText();
			String sA = secA.getText();
			if (pass.equals(pass2))
			{
				Stage stage = (Stage) firstName.getScene().getWindow();
				if (stage.getTitle().equals("Create Account"))
				{
					User u = new User(first, last, nUsername, pass, nEmail);
					u.setSecurityQuestion(sQ);
					u.setSecurityAnswer(sA);
					DBUserFunctions.register(u);
					InterfaceLauncher.CurrentUser = u;
					stage.close();
					Parent main;
					try
					{
						main = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
						Scene mainScene = new Scene(main);
						mainScene.getStylesheets()
								.add(InterfaceLauncher.class.getResource("MainStyle.css").toExternalForm());
						stage.getIcons().add(new Image("TOIcon.png"));
						stage.setTitle("Task Organizer");
						stage.setScene(mainScene);
						stage.show();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				else if (stage.getTitle().equals("Edit Account"))
				{
					InterfaceLauncher.CurrentUser.setFirstName(first);
					InterfaceLauncher.CurrentUser.setLastName(last);
					InterfaceLauncher.CurrentUser.setEmail(nEmail);
					InterfaceLauncher.CurrentUser.setPassword(pass);
					InterfaceLauncher.CurrentUser.setUsername(nUsername);
					InterfaceLauncher.CurrentUser.setSecurityQuestion(sQ);
					InterfaceLauncher.CurrentUser.setSecurityAnswer(sA);
					DBUserFunctions.saveUserStrings(InterfaceLauncher.CurrentUser);
					stage.close();
				}
			}
			else
			{
				SystemMessage.setText("Passwords don't match.");
			}
		}
		catch (Exception e)
		{
			SystemMessage.setText("Fields are filled out incorrectly.");
		}
	}
	
	/**
	 * Initialize is run every time a new instance of this screen is opened.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		User u = InterfaceLauncher.CurrentUser;
		if (u != null)
		{
			firstName.setText(u.getFirstName());
			lastName.setText(u.getLastName());
			username.setText(u.getUsername());
			email.setText(u.getEmail());
			password.setText(u.getPassword());
			passwordConfirmation.setText(u.getPassword());
			secQ.setText(u.getSecurityQuestion());
			secA.setText(u.getSecurityAnswer());
		}
	}
	
}
