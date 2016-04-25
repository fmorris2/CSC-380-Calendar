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

public class AddReminderController {
	
	@FXML
	DatePicker datePickerReminders;
	@FXML
	ChoiceBox<String> intervalPickerReminder;
	@FXML
	Label SystemMessage;
	User user;
	Task currentTask;
	MainController parent;
	
	
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
	
	@FXML
	private void handleAddReminderListener(ActionEvent event) throws IOException
	{
		if(validFields())
		{
			currentTask.addReminder(Interval.valueOf(intervalPickerReminder.getValue()));
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

	private boolean validFields() {
		if (!intervalPickerReminder.getValue().toString().equals("")){
			return true;
		}
		return false;
	}
	
	public void initialize(URL location, ResourceBundle resources)
	{
		this.user = InterfaceLauncher.CurrentUser;
		
	}
	
	public void setParent(MainController p)
	{
		parent = p;
	}
	
	public void setTask(Task t)
	{
		currentTask = t;
	}
	
}
