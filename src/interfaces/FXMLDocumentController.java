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
import javafx.stage.Stage;

/**
 *
 * @author Mike Mekker
 */
public class FXMLDocumentController implements Initializable
{
	
	@FXML
	private void handleFileNewTaskAction(ActionEvent event)
	{
		Parent task;
		try
		{
			task = FXMLLoader.load(getClass().getResource("CreateEditTaskScreen.fxml"));
			Stage taskStage = new Stage();
			Scene taskScene = new Scene(task);
			taskStage.setScene(taskScene);
			taskStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleFileNewGroupAction(ActionEvent event)
	{
		
	}
	
	@FXML
	private void handleFileEditAccountAction(ActionEvent event)
	{
		Parent account;
		try
		{
			account = FXMLLoader.load(getClass().getResource("CreateEditAccountScreen.fxml"));
			Stage accountStage = new Stage();
			Scene accountScene = new Scene(account);
			accountStage.setScene(accountScene);
			accountStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleAboutAction(ActionEvent event)
	{
		Parent about;
		try
		{
			about = FXMLLoader.load(getClass().getResource("AboutScreen.fxml"));
			Stage aboutStage = new Stage();
			Scene aboutScene = new Scene(about);
			aboutStage.setScene(aboutScene);
			aboutStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		// TODO
	}
	
}
