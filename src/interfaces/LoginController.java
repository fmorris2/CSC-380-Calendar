package interfaces;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class LoginController implements Initializable
{
	@FXML Label SystemMessageLabelLogin;
	@FXML TextField usernameFieldLogin;
	@FXML PasswordField passwordFieldLogin;
	
	/**
	 * Action for create account button
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
			accountStage.setTitle("Create Account");
			accountStage.setScene(accountScene);
			accountStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	/**
	 * Action for Login Button
	 * @param event
	 */
	private void handleLoginAction(ActionEvent event)
	{
		if (correctLogin(usernameFieldLogin.getText(), passwordFieldLogin.getText())){
			SystemMessageLabelLogin.setText("Login successful");
			usernameFieldLogin.setText("");
			passwordFieldLogin.setText("");
			//Pull user data from database
			Stage stage = (Stage) SystemMessageLabelLogin.getScene().getWindow();
			stage.close();
		} else {
			SystemMessageLabelLogin.setText("Incorrect Username or Password");
			passwordFieldLogin.setText("");
		}
		
	}
	private boolean correctLogin(String username, String password) {
		// Filler until Fred is done with database
		if (!username.equalsIgnoreCase("username") || !password.equalsIgnoreCase("password")){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}
	
}
