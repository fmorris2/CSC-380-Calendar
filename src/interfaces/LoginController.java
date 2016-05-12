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
 * Controller for the login screen
 * 
 * @author Noah Pierce
 */
public class LoginController implements Initializable
{
	@FXML
	Label SystemMessageLabelLogin;
	@FXML
	TextField usernameFieldLogin;
	@FXML
	PasswordField passwordFieldLogin;
	
	/**
	 * Action for create account button
	 * 
	 * @param event
	 */
	@FXML
	private void handleCreateAccountAction(ActionEvent event)
	{
		Parent account;
		try
		{
			account = FXMLLoader.load(getClass().getResource("CreateEditAccountScreen.fxml"));
			Stage accountStage = new Stage();
			Scene accountScene = new Scene(account);
			accountScene.getStylesheets().add(InterfaceLauncher.class.getResource("MainStyle.css").toExternalForm());
			accountStage.getIcons().add(new Image("TOIcon.png"));
			accountStage.setTitle("Create Account");
			accountStage.setScene(accountScene);
			accountStage.show();
			Stage stage = (Stage) SystemMessageLabelLogin.getScene().getWindow();
			stage.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Listener for the account recovery button
	 * 
	 * @param event
	 */
	@FXML
	private void recoverHandle(ActionEvent event)
	{
		Parent recover;
		try
		{
			recover = FXMLLoader.load(getClass().getResource("AccountRecoveryScreen.fxml"));
			Stage recoverStage = new Stage();
			Scene recoverScene = new Scene(recover);
			recoverScene.getStylesheets().add(InterfaceLauncher.class.getResource("MainStyle.css").toExternalForm());
			recoverStage.getIcons().add(new Image("TOIcon.png"));
			recoverStage.setTitle("Recovery");
			recoverStage.setScene(recoverScene);
			recoverStage.show();
			Stage stage = (Stage) SystemMessageLabelLogin.getScene().getWindow();
			stage.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Redirects the ActionEvent param to the login submit button
	 * 
	 * @param event
	 */
	@FXML
	public void onEnter(ActionEvent event)
	{
		handleLoginAction(event);
	}
	
	@FXML
	/**
	 * Action for Login Button
	 * 
	 * @param event
	 */
	private void handleLoginAction(ActionEvent event)
	{
		User currentUser = correctLogin(usernameFieldLogin.getText(), passwordFieldLogin.getText());
		if (currentUser != null)
		{
			SystemMessageLabelLogin.setText("Login successful");
			usernameFieldLogin.setText("");
			passwordFieldLogin.setText("");
			InterfaceLauncher.setCurrentUser(currentUser);
			// Pull user data from database
			Stage stage = (Stage) SystemMessageLabelLogin.getScene().getWindow();
			stage.close();
			Parent main;
			try
			{
				main = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
				Scene mainScene = new Scene(main);
				mainScene.getStylesheets().add(InterfaceLauncher.class.getResource("MainStyle.css").toExternalForm());
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
		else
		{
			SystemMessageLabelLogin.setText("Incorrect Username or Password");
			passwordFieldLogin.setText("");
		}
		
	}
	
	/**
	 * Takes in a username and password and checks that there exists a user with
	 * that username and that its password is the same and the one given.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private User correctLogin(String username, String password)
	{
		User user = new User(username, password);
		return DBUserFunctions.login(user) ? user : null;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}
	
}
