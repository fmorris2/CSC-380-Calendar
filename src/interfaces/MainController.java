package interfaces;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.CheckMenuItem;
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
	
	ObservableList<Task> tasks;
	
	MainController cont = this;
	
	@FXML
	/**
	 * Action for File->New Task
	 * 
	 * @param event
	 */
	private void handleFileNewTaskAction(ActionEvent event)
	{
		Stage taskStage = new Stage();
		Parent task;
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateTaskScreen.fxml"));
			task = (Parent) fxmlLoader.load();
			CreateTaskController controller = fxmlLoader.<CreateTaskController> getController();
			controller.setParent(cont);
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
			accountStage.setTitle("Edit Account");
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
			aboutStage.setTitle("About");
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
		cont = this;
		User user = InterfaceLauncher.CurrentUser;
		tasks = FXCollections.observableArrayList(user.getTasks());
		tasks.addAll(InterfaceLauncher.CurrentUser.getCompletedTasks());
		
		CompletedColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("completed"));
		NameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("taskName"));
		CategoryColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("category"));
		PriorityColumn.setCellValueFactory(new PropertyValueFactory<Task, Priority>("priority"));
		DateColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("dueDate"));
		
		/**********************Category Context Menu**********************/
		final ContextMenu categoryMenu = new ContextMenu();
		/*List of items to put into the list*/
		ArrayList<CheckMenuItem> categoryItems = new ArrayList<CheckMenuItem>();
		ArrayList<String> categories = getCategories();
		/*Add menu items*/
		for(String s: categories)
		{
			categoryItems.add(new CheckMenuItem(s));
		}
		/*Add handler to all menu items*/
		for(CheckMenuItem i: categoryItems)
		{
			i.setSelected(true);
			i.setOnAction(new EventHandler<ActionEvent>() {
			    public void handle(ActionEvent e) {
			    	/*Handler for all menu items*/
			    	/*To get name of clicked item use: ((CheckMenuItem) (e.getSource())).getText()*/
			        System.out.println(((CheckMenuItem) (e.getSource())).getText() + " Enabled: " + ((CheckMenuItem) (e.getSource())).isSelected());
			    }
			});
		}
		/*Add items to the menu*/
		for(CheckMenuItem i: categoryItems)
		{
			categoryMenu.getItems().add(i);
		}
		/*Set menu to column*/
		CategoryColumn.setContextMenu(categoryMenu);
		/**********************End Category menu**********************/
		
		/**********************Priority Context Menu**********************/
		final ContextMenu priorityMenu = new ContextMenu();
		/*List of items to put into the list*/
		ArrayList<CheckMenuItem> priorityItems = new ArrayList<CheckMenuItem>();
		Priority[] priorities = Priority.values();
		/*Add menu items*/
		for(Priority s: priorities)
		{
			priorityItems.add(new CheckMenuItem(s.name()));
		}
		/*Add handler to all menu items*/
		for(CheckMenuItem i: priorityItems)
		{
			i.setSelected(true);
			i.setOnAction(new EventHandler<ActionEvent>() {
			    public void handle(ActionEvent e) {
			    	/*Handler for all menu items*/
			    	/*To get name of clicked item use: ((CheckMenuItem) (e.getSource())).getText()*/
			        System.out.println(((CheckMenuItem) (e.getSource())).getText() + " Enabled: " + ((CheckMenuItem) (e.getSource())).isSelected());
			    }
			});
		}
		/*Add items to the menu*/
		for(CheckMenuItem i: priorityItems)
		{
			priorityMenu.getItems().add(i);
		}
		/*Set menu to column*/
		PriorityColumn.setContextMenu(priorityMenu);
		/**********************End Priority menu**********************/
		
		/**********************Date Context Menu**********************/
		final ContextMenu dateMenu = new ContextMenu();
		/*List of items to put into the list*/
		ArrayList<CheckMenuItem> dateItems = new ArrayList<CheckMenuItem>();
		ArrayList<String> dates = getDates();
		/*Add menu items*/
		for(String s: dates)
		{
			dateItems.add(new CheckMenuItem(s));
		}
		/*Add handler to all menu items*/
		for(CheckMenuItem i: dateItems)
		{
			i.setSelected(true);
			i.setOnAction(new EventHandler<ActionEvent>() {
			    public void handle(ActionEvent e) {
			    	/*Handler for all menu items*/
			    	/*To get name of clicked item use: ((CheckMenuItem) (e.getSource())).getText()*/
			        System.out.println(((CheckMenuItem) (e.getSource())).getText() + " Enabled: " + ((CheckMenuItem) (e.getSource())).isSelected());
			    }
			});
		}
		/*Add items to the menu*/
		for(CheckMenuItem i: dateItems)
		{
			dateMenu.getItems().add(i);
		}
		/*Set menu to column*/
		DateColumn.setContextMenu(dateMenu);
		/**********************End Date menu**********************/
		
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
						if (selectedTask.getCompleted().equals(""))
						{
							Stage taskStage = new Stage();
							Parent task;
							try
							{
								FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditTaskScreen.fxml"));
								task = (Parent) fxmlLoader.load();
								EditTaskController controller = fxmlLoader.<EditTaskController> getController();
								controller.setTask(selectedTask);
								controller.setParent(cont);
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
						if (row.getItem().getCompleted().equals(""))
						{
							InterfaceLauncher.CurrentUser.removeTask(row.getItem());
						}
						else if (row.getItem().getCompleted().equals("C"))
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
	
	public void refreshList()
	{
		User user = InterfaceLauncher.CurrentUser;
		tasks = FXCollections.observableArrayList(user.getTasks());
		tasks.addAll(InterfaceLauncher.CurrentUser.getCompletedTasks());
		TaskTable.setItems(tasks);
	}
	
	private ArrayList<String> getCategories()
	{
		ArrayList<String> arr = new ArrayList<String>();
		User user = InterfaceLauncher.CurrentUser;
		for(Task t: user.getTasks())
		{
			String cat = t.getCategory();
			if(!arr.contains(cat))
			{
				arr.add(cat);
			}
		}
		for(Task t: user.getCompletedTasks())
		{
			String cat = t.getCategory();
			if(!arr.contains(cat))
			{
				arr.add(cat);
			}
		}
		return arr;
	}
	
	private ArrayList<String> getDates()
	{
		ArrayList<String> arr = new ArrayList<String>();
		User user = InterfaceLauncher.CurrentUser;
		for(Task t: user.getTasks())
		{
			String date = t.getDueDate().toString();
			if(!arr.contains(date))
			{
				arr.add(date);
			}
		}
		for(Task t: user.getCompletedTasks())
		{
			String date = t.getDueDate().toString();
			if(!arr.contains(date))
			{
				arr.add(date);
			}
		}
		return arr;
	}
	
}
