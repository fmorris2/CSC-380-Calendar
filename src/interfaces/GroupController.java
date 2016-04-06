package interfaces;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import user.User;

public class GroupController implements Initializable
{
	@FXML groupNameFieldGroup;
	@FXML groupDescriptionFieldGroup;
	User user;
	
	@FXML
	private void handleGroupSubmitListener(ActionEvent event)
	{
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.user = new User();
	}
	
}
