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
public class MainController implements Initializable
{
	//TODO: Make multiple FXML Controllers for each screen
	//TODO: Continue adding functionality to buttons
	
	
	
	@FXML
	/**
	 * Action for File->New Task
	 * @param event
	 */
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
	/**
	 * Action for File->New Group
	 * @param event
	 */
	private void handleFileNewGroupAction(ActionEvent event)
	{
		Parent group;
		try
		{
			group = FXMLLoader.load(getClass().getResource("CreateEditGroupScreen.fxml"));
			Stage groupStage = new Stage();
			Scene groupScene = new Scene(group);
			groupStage.setScene(groupScene);
			groupStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
