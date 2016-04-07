package interfaces;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import task.Priority;
import task.Task;
import user.User;

/**
 *
 * @author Mike Mekker
 */
public class MainController implements Initializable
{
	@FXML
	TableView<Task> TaskTable;
	@FXML
	TableColumn<Task, String> CompletedColumn;
	@FXML
	TableColumn<Task, String> NameColumn;
	@FXML
	TableColumn<Task, String> CategoryColumn;
	@FXML
	TableColumn<Task, Priority> PriorityColumn;
	@FXML
	TableColumn<Task, String> DateColumn;
	@FXML
	TextField TaskNameField;
	@FXML
	TextArea TaskDescriptionField;
	@FXML
	TextField DueDateField;
	@FXML
	TextField CreationDateField;
	@FXML
	TextField CategoryField;
	@FXML
	TextField PriorityField;
	@FXML
	TextArea CommentsField;
	
	@FXML
	/**
	 * Action for File->New Task
	 * 
	 * @param event
	 */
	private void handleFileNewTaskAction(ActionEvent event)
	{
		Parent task;
		try
		{
			task = FXMLLoader.load(getClass().getResource("CreateTaskScreen.fxml"));
			Stage taskStage = new Stage();
			Scene taskScene = new Scene(task);
			taskStage.setTitle("Create Task");
			taskStage.setScene(taskScene);
			taskStage.show();
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
		InterfaceLauncher.setCurrentUser();
		User user = InterfaceLauncher.CurrentUser;
		ObservableList<Task> tasks = FXCollections.observableArrayList(user.getTasks());
		tasks.addAll(InterfaceLauncher.CurrentUser.getCompletedTasks());
		
		CompletedColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("completed"));
		NameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("taskName"));
		CategoryColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("category"));
		PriorityColumn.setCellValueFactory(new PropertyValueFactory<Task, Priority>("priority"));
		DateColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		
		TaskTable.setItems(tasks);
		
		TaskTable.getSelectionModel().selectedItemProperty().addListener(extracted());
		
		TaskTable.setRowFactory(new Callback<TableView<Task>, TableRow<Task>>()
		{
			@Override
			public TableRow<Task> call(TableView<Task> tableView)
			{
				final TableRow<Task> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem editTaskItem = new MenuItem("Edit Task");
				final MenuItem removeTaskItem = new MenuItem("Remove Task");
				// Edit task right click option
				editTaskItem.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						Task selectedTask = TaskTable.getSelectionModel().getSelectedItem();
						if(selectedTask.getCompleted().equals(""))
						{
							Stage taskStage = new Stage();
							Parent task;
							try
							{
								FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditTaskScreen.fxml"));
								task = (Parent) fxmlLoader.load();
								EditTaskController controller = fxmlLoader.<EditTaskController> getController();
								controller.setTask(selectedTask);
								Scene taskScene = new Scene(task);
								taskStage.setTitle("Edit Task");
								taskStage.setScene(taskScene);
								taskStage.show();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
				});
				// Remove task right click option
				removeTaskItem.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						TaskTable.getItems().remove(row.getItem());
						if (row.getItem().getCompleted() == "")
							InterfaceLauncher.CurrentUser.removeTask(row.getItem());
						else if (row.getItem().getCompleted() == "C")
							InterfaceLauncher.CurrentUser.removeCompletedTask(row.getItem());
						
					}
				});
				contextMenu.getItems().add(editTaskItem);
				contextMenu.getItems().add(removeTaskItem);
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
	}
	
	private ChangeListener<? super Task> extracted()
	{
		return (ChangeListener<? super Task>) (observableValue, oldValue, newValue) -> {
			if (TaskTable.getSelectionModel().getSelectedItem() != null)
			{
				Task t = TaskTable.getSelectionModel().getSelectedItem();
				TaskNameField.setText(t.getTaskName());
				TaskDescriptionField.setText(t.getTaskDescription());
				DueDateField.setText(t.getDueDate().toString());
				CreationDateField.setText(t.getCreationDate().toString());
				CategoryField.setText(t.getCategory());
				PriorityField.setText(t.getPriority().toString());
			}
		};
	}
	
}
