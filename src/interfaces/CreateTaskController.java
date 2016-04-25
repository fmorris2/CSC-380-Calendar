package interfaces;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import task.Priority;
import task.Task;
import user.User;
/**
 * Class handles create new task button.
 * @author noybo_000
 */
import java.net.URL;

public class CreateTaskController implements Initializable
{
	@FXML
	TextField taskNameFieldTask;
	@FXML
	TextField categoryFieldTask;
	@FXML
	TextField durationFieldTask;
	@FXML
	DatePicker datePicker;
	@FXML
	TextArea taskDescriptionFieldTask;
	@FXML
	ChoiceBox<String> priorityFieldTask;
	@FXML
	ChoiceBox<String> timeHoursFieldTask;
	@FXML
	ChoiceBox<String> timeMinutesFieldTask;
	@FXML
	ChoiceBox<String> timeFieldTask;
	@FXML
	Label SystemMessage;
	User user;
	MainController parent;
	
	@FXML
	public void onEnter(ActionEvent event)
	{
		try
		{
			handleTaskSubmitListener(event);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleTaskSubmitListener(ActionEvent event) throws IOException
	{
		if(validFields())
		{
			String taskName = taskNameFieldTask.getText();
			String category = categoryFieldTask.getText();
			String durationString = durationFieldTask.getText();
			Duration duration = Duration.ofMinutes(Integer.parseInt(durationString));
			LocalDateTime dueDate = makeLocalDateTime(datePicker.getValue(), timeHoursFieldTask.getValue(),
					timeMinutesFieldTask.getValue(), timeFieldTask.getValue());
			String taskDescription = taskDescriptionFieldTask.getText();
			Priority priority = Priority.valueOf(priorityFieldTask.getValue());
			Task task = new Task(dueDate, duration, taskName, taskDescription, category, priority);
			user.addNewTask(task);
			List<Task> filtered = parent.filter();
			parent.updateTaskDisplay(filtered);
			parent.refreshMenuItems(filtered);
			Stage stage = (Stage) taskNameFieldTask.getScene().getWindow();
			stage.close();
		}
		else
		{
			SystemMessage.setText("One or more fields are incorrectly filled out.");
		}
	}
	
	private boolean validFields()
	{
		if(!taskNameFieldTask.getText().equals("")
				&& !categoryFieldTask.getText().equals("")
				&& !durationFieldTask.getText().equals("")
				&& durationFieldTask.getText().matches("\\d+")
				&& !datePicker.getValue().equals(null)
				&& !timeHoursFieldTask.getValue().equals(null)
				&& !timeMinutesFieldTask.getValue().equals(null)
				&& !timeFieldTask.getValue().equals(null)
				&& !taskDescriptionFieldTask.getText().equals("")
				&& !priorityFieldTask.getValue().equals(null))
		{
			return true;
		}
		return false;
	}
	
	private LocalDateTime makeLocalDateTime(LocalDate date, String hour, String minutes, String time)
	{
		String stringDate;
		if (time.equalsIgnoreCase("AM") && Integer.parseInt(hour) != 12)
		{
			stringDate = date + "T" + hour + ":" + minutes + ":00.000";
		}
		else if(Integer.parseInt(hour) == 12)
		{
			String hours;
			if (time.equalsIgnoreCase("AM"))
				hours = "00";
			else
				hours = "12";
			stringDate = date + "T" + hours + ":" + minutes + ":00.000";
		}
		else
		{
			int hours = Integer.parseInt(hour);
			hours = hours + 12;
			stringDate = date + "T" + hours + ":" + minutes + ":00.000";
		}
		LocalDateTime dateTime = LocalDateTime.parse(stringDate);
		return dateTime;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.user = InterfaceLauncher.CurrentUser;
	}
	
	public void setParent(MainController p)
	{
		parent = p;
	}
}
