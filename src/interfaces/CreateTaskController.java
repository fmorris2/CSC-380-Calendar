package interfaces;

import java.io.IOException;

/**
 * Class handles create new task button.
 * @author noybo_000
 */

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import task.Priority;
import task.Task;
import user.User;

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
	ChoiceBox<Priority> priorityFieldTask;
	@FXML
	ChoiceBox<String> timeHoursFieldTask;
	@FXML
	ChoiceBox<String> timeMinutesFieldTask;
	@FXML
	ChoiceBox<String> timeFieldTask;
	User user;
	MainController parent;
	
	@FXML
	private void handleTaskSubmitListener(ActionEvent event) throws IOException
	{
		try
		{
			String taskName = taskNameFieldTask.getText();
			String category = categoryFieldTask.getText();
			String durationString = durationFieldTask.getText();
			Duration duration = Duration.ofMinutes(Integer.parseInt(durationString));
			LocalDateTime dueDate = makeLocalDateTime(datePicker.getValue(), timeHoursFieldTask.getValue(),
					timeMinutesFieldTask.getValue(), timeFieldTask.getValue());
			String taskDescription = taskDescriptionFieldTask.getText();
			Priority priority = Priority.HIGH;
			// Priority priority = priorityFieldTask.getValue();
			Task task = new Task(dueDate, duration, taskName, taskDescription, category, priority);
			user.addNewTask(task);
			parent.refreshList();
			Stage stage = (Stage) taskNameFieldTask.getScene().getWindow();
			stage.close();
		}
		catch (NullPointerException e)
		{
			// System message
		}
		catch (NumberFormatException e)
		{
			// System message
		}
	}
	
	private LocalDateTime makeLocalDateTime(LocalDate date, String hour, String minutes, String time)
	{
		String stringDate;
		if (time.equalsIgnoreCase("AM"))
		{
			stringDate = date + "T" + hour + ":" + minutes + ":00.000";
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
