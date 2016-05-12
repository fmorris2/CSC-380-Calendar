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
import javafx.scene.control.CheckMenuItem;
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

/**
 * Controller for the create new task window.
 * 
 * @author Noah Pierce
 *
 */
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
	
	/**
	 * Redirects the ActionEvent param to the submit button listener
	 * 
	 * @param event
	 */
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
	
	/**
	 * Takes in user input and checks that all fields are correctly filled out.
	 * It then creates and saves the new task.
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
			boolean containsCategory = parent.getCategories(user.getTasks()).contains(category);
			user.addNewTask(task);
			if (!containsCategory)
			{
				System.out.println("Doesnt contain category... adding it to categoryItems");
				CheckMenuItem cM = new CheckMenuItem(category);
				cM.setSelected(true);
				parent.categoryItems.add(cM);
			}
			System.out.println("User tasks size: " + user.getTasks().size());
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
	
	/**
	 * Checks that all fields are correctly filled out
	 * 
	 * @return - Whether or not the fields are correctly filled out
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
		if (time.equalsIgnoreCase("AM") && Integer.parseInt(hour) != 12)
		{
			stringDate = date + "T" + hour + ":" + minutes + ":00.000";
		}
		else if (Integer.parseInt(hour) == 12)
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
	
	/**
	 * Run every time a new create task screen is made
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.user = InterfaceLauncher.CurrentUser;
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
}
