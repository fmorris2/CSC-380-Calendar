package interfaces;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mike Mekker
 */
public class InterfaceLauncher extends Application
{
	
	@Override
	public void start(Stage stage) throws Exception
	{
		Parent login = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
		Parent main = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
		
		Scene mainScene = new Scene(main);
		stage.setScene(mainScene);
		stage.show();
		
		Stage loginStage = new Stage();
		Scene loginScene = new Scene(login);
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
	
}
