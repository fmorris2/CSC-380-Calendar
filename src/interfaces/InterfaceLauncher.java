package interfaces;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
		Parent login = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
		Stage loginStage = new Stage();
		Scene loginScene = new Scene(login);
		/*login.setStyle("-fx-background-image: url('background.jpg'); " +
		           "-fx-background-position: center center; " +
		           "-fx-background-repeat: stretch;");
		loginStage.getIcons().add(new Image("TOIcon.png"));*/
		loginStage.setTitle("Login");
		loginStage.setScene(loginScene);
		loginStage.show();
		 
	}
	
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public static void setCurrentUser(User u)
	{
		CurrentUser = u;
	}
	
}
