package interfaces;

import java.net.URL;
import java.util.ResourceBundle;

import group.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import user.User;

public class GroupController implements Initializable
{
	@FXML TextField groupNameFieldGroup;
	@FXML TextArea groupDescriptionFieldGroup;
	User user;
	
	@FXML
	private void handleGroupSubmitListener(ActionEvent event)
	{
		Group group = new Group(this.user, groupNameFieldGroup.getText(), groupDescriptionFieldGroup.getText());
	}
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.user = new User();
	}
	
}
