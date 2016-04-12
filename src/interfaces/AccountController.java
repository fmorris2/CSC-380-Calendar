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
import javafx.stage.Stage;
import user.User;

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
	
	@FXML
	public void onEnter(ActionEvent event)
	{
		handleCreateAccountSubmitListener(event);
	}
	
	@FXML
	private void handleCreateAccountSubmitListener(ActionEvent event)
	{
		if (validFields())
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
				}
			}
			else
			{
				SystemMessage.setText("Passwords don't match.");
			}
		}
		else
		{
			SystemMessage.setText("Fields are filled out incorrectly.");
		}
	}
	
	private boolean validFields()
	{
		if (!firstName.getText().equals("") && !lastName.getText().equals("") && !username.getText().equals("")
				&& !email.getText().equals("") && !password.getText().equals("")
				&& !passwordConfirmation.getText().equals("") && !secQ.getText().equals("")
				&& !secA.getText().equals(""))
		{
			return true;
		}
		return false;
	}
	
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
