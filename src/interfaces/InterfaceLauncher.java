package interfaces;

import java.time.Duration;
import java.time.LocalDateTime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import task.Priority;
import task.Task;
import user.User;

/**
 *
 * @author Mike Mekker
 */
public class InterfaceLauncher extends Application
{
	public static User CurrentUser;
	@Override
	public void start(Stage stage) throws Exception
	{
		//Parent login = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
		Parent main = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
		
		Scene mainScene = new Scene(main);
		stage.setScene(mainScene);
		stage.show();
		
		/*Stage loginStage = new Stage();
		Scene loginScene = new Scene(login);
		loginStage.setScene(loginScene);
		loginStage.show();*/
	}
	
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args)
	{
		launch(args);
	}

	public static void setCurrentUser()
	{
		CurrentUser = new User("FirstName", "LastName", "username", "password", "email@email.com");
		Task testTask = new Task(LocalDateTime.of(2016, 4, 6, 17, 10, 0), Duration.ofSeconds(60), "Task 0", "This is task 0.", "Category 0", Priority.HIGH);
		CurrentUser.addNewTask(testTask);
		CurrentUser.addNewTask(new Task(LocalDateTime.of(2016, 5, 6, 15, 20, 0), Duration.ofSeconds(400), "Task 1", "This is task 1.", "Category 1", Priority.LOW));
		CurrentUser.addNewTask(new Task(LocalDateTime.of(2016, 7, 11, 6, 30, 0), Duration.ofSeconds(600), "Task 2", "This is task 2.", "Category 2", Priority.MEDIUM));
		CurrentUser.addNewTask(new Task(LocalDateTime.of(2016, 9, 25, 11, 40, 0), Duration.ofSeconds(220), "Task 3", "This is task 3.", "Category 3", Priority.MEDIUM));
		CurrentUser.addNewTask(new Task(LocalDateTime.of(2017, 1, 30, 13, 50, 0), Duration.ofSeconds(180), "Task 4", "This is task 4.", "Category 4", Priority.LOW));
		CurrentUser.completeTask(testTask);
	}
	
}
