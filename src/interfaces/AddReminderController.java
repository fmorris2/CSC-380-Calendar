package interfaces;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import reminders.Interval;
import task.Task;
import user.User;

/**
 * Controller for the reminder creation class
 * 
 * @author Noah Pierce
 *
 */
public class AddReminderController
{
	
	@FXML
	DatePicker datePickerReminders;
	@FXML
	ChoiceBox<String> intervalPickerReminder;
	@FXML
	Label SystemMessage;
	User user;
	Task currentTask;
	MainController parent;
	
	/**
	 * Redirects the ActionEvent param to the submit listener
	 * 
	 * @param event
	 */
	@FXML
	public void onEnter(ActionEvent event)
	{
		try
		{
			handleAddReminderListener(event);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Listener for the submit button. Checks fields and creates new reminder.
	 * Saves new reminder to the server.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handleAddReminderListener(ActionEvent event) throws IOException
	{
		if (validFields())
		{
			currentTask.addReminder(InterfaceLauncher.CurrentUser, Interval.valueOf(intervalPickerReminder.getValue()));
			List<Task> filtered = parent.filter();
			parent.updateTaskDisplay(filtered);
			parent.refreshMenuItems(filtered);
			Stage stage = (Stage) intervalPickerReminder.getScene().getWindow();
			stage.close();
		}
		else
		{
			SystemMessage.setText("One or more fields are incorrectly filled out.");
		}
	}
	
	/**
	 * Checks that all fields are correctly filled out.
	 * 
	 * @return - Whether or not the fields are correctly filled out
	 */
	private boolean validFields()
	{
		if (!intervalPickerReminder.getValue().toString().equals(""))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Ran every time this screen is opened
	 * 
	 * @param location
	 * @param resources
	 */
	public void initialize(URL location, ResourceBundle resources)
	{
		this.user = InterfaceLauncher.CurrentUser;
		
	}
	
	/**
	 * Sets the parent variable
	 * 
	 * @param p
	 *            - parent controller
	 */
	public void setParent(MainController p)
	{
		parent = p;
	}
	
	/**
	 * Set current task that the reminder is being added to
	 * 
	 * @param t
	 */
	public void setTask(Task t)
	{
		currentTask = t;
	}
	
}
