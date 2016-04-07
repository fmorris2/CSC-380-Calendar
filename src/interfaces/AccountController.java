package interfaces;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccountController implements Initializable
{
	@FXML TextField firstName;
	@FXML TextField lastName;
	@FXML TextField username;
	@FXML TextField email;
	@FXML PasswordField password;
	@FXML PasswordField passwordConfirmation;
	@FXML TextField secQ;
	@FXML TextField secA;
	@FXML Label SystemMessage;
	
	@FXML
	private void handleCreateAccountSubmitListener(ActionEvent event)
	{
		Stage stage = (Stage) firstName.getScene().getWindow();
		if(stage.getTitle().equals("Create Account"))
		{
			//Take in values
			
			
			
			//InterfaceLauncher.CurrentUser = new User();
		}
		else if(stage.getTitle().equals("Edit Account"))
		{
			//Take in values
			
			
			
			////InterfaceLauncher.CurrentUser = new User();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
	
}
