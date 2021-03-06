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
 * Controller for the Edit task screen.
 * 
 * @author Mike Mekker
 */
public class EditTaskController implements Initializable
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
	Task currentTask;
	MainController parent;
	
	/**
	 * Redirects the ActionEvent param to the submit button listener
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void onEnter(ActionEvent event) throws IOException
	{
		handleTaskSubmitListener(event);
	}
	
	/**
	 * Takes in user input and validates field values. Creates and overwrites
	 * existing task if fields are correct.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handleTaskSubmitListener(ActionEvent event) throws IOException
	{
		if (validFields())
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
			if (!currentTask.equals(task))
			{
				user.addNewTask(task);
				user.removeTask(currentTask);
				List<Task> filtered = parent.filter();
				parent.updateTaskDisplay(filtered);
				parent.refreshMenuItems(filtered);
			}
			Stage stage = (Stage) taskNameFieldTask.getScene().getWindow();
			stage.close();
		}
		else
		{
			SystemMessage.setText("One or more fields are incorrectly filled out.");
		}
	}
	
	/**
	 * Checks that all fields are valid
	 * 
	 * @return
	 */
	private boolean validFields()
	{
		if (!taskNameFieldTask.getText().equals("") && !categoryFieldTask.getText().equals("")
				&& !durationFieldTask.getText().equals("") && durationFieldTask.getText().matches("\\d+")
				&& !datePicker.getValue().equals(null) && !timeHoursFieldTask.getValue().equals(null)
				&& !timeMinutesFieldTask.getValue().equals(null) && !timeFieldTask.getValue().equals(null)
				&& !taskDescriptionFieldTask.getText().equals("") && !priorityFieldTask.getValue().equals(null))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Takes in strings for various parts of a date and concatonates them into a
	 * date string. Returns the LocalDateTime object
	 * 
	 * @param date
	 * @param hour
	 * @param minutes
	 * @param time
	 * @return
	 */
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
	
	/**
	 * Run every time a new create task screen is made
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.user = InterfaceLauncher.CurrentUser;
	}
	
	/**
	 * Sets the target task to be edited
	 * 
	 * @param t
	 *            - task to be edited
	 */
	public void setTask(Task t)
	{
		currentTask = t;
		taskNameFieldTask.setText(currentTask.getTaskName());
		categoryFieldTask.setText(currentTask.getCategory());
		durationFieldTask.setText("" + currentTask.getDuration().toMinutes());
		datePicker.setValue(LocalDate.of(currentTask.getDueDate().getYear(), currentTask.getDueDate().getMonth(),
				currentTask.getDueDate().getDayOfMonth()));
		taskDescriptionFieldTask.setText(currentTask.getTaskDescription());
		priorityFieldTask.getSelectionModel().select(t.getPriority().toString());
		;
		if ((currentTask.getDueDate().getHour() % 12) < 10)
			timeHoursFieldTask.setValue("0" + (currentTask.getDueDate().getHour() % 12));
		else
			timeHoursFieldTask.setValue("" + (currentTask.getDueDate().getHour() % 12));
		if (roundToFives(currentTask.getDueDate().getMinute()) < 10)
			timeMinutesFieldTask.setValue("0" + roundToFives(currentTask.getDueDate().getMinute()));
		else
			timeMinutesFieldTask.setValue("" + roundToFives(currentTask.getDueDate().getMinute()));
		if (currentTask.getDueDate().getHour() > 12)
			timeFieldTask.setValue("PM");
		else
			timeFieldTask.setValue("AM");
	}
	
	/**
	 * Sets parent controller
	 * 
	 * @param p
	 */
	public void setParent(MainController p)
	{
		parent = p;
	}
	
	/**
	 * Rounds a given number to the nearest 5
	 * 
	 * @param num
	 *            - number to round
	 * @return - rounded number
	 */
	public int roundToFives(int num)
	{
		int t = num % 10;
		int num2 = (num - t);
		int num3 = (num - (t - 5));
		if (num % 10 > 5)
			num = num3;
		else if (num % 10 < 5)
			num = num2;
		
		return num;
	}
}
