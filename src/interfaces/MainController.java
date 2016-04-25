package interfaces;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import filters.Filter;
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
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
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
	
	List<CheckMenuItem> categoryItems, dateItems, priorityItems;
	MenuItem datePickerMenuItem;
	
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
		
		/********************** Category Context Menu **********************/
		final ContextMenu categoryMenu = new ContextMenu();
		/* List of items to put into the list */
		categoryItems = new ArrayList<>();
		ArrayList<String> categories = getCategories(tasks);
		/* Add menu items */
		for (String s : categories)
		{
			categoryItems.add(new CheckMenuItem(s));
		}
		/* Add handler to all menu items */
		for (CheckMenuItem i : categoryItems)
		{
			i.setSelected(true);
			i.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					/* Handler for all menu items */
					/*
					 * To get name of clicked item use: ((CheckMenuItem)
					 * (e.getSource())).getText()
					 */
					// CATEGORY FILTER
					updateTaskDisplay(filter());
					System.out.println(((CheckMenuItem) (e.getSource())).getText() + " Enabled: "
							+ ((CheckMenuItem) (e.getSource())).isSelected());
				}
			});
		}
		/* Add items to the menu */
		for (CheckMenuItem i : categoryItems)
		{
			categoryMenu.getItems().add(i);
		}
		/* Set menu to column */
		CategoryColumn.setContextMenu(categoryMenu);
		/********************** End Category menu **********************/
		
		/********************** Priority Context Menu **********************/
		final ContextMenu priorityMenu = new ContextMenu();
		/* List of items to put into the list */
		priorityItems = new ArrayList<>();
		Priority[] priorities = Priority.values();
		/* Add menu items */
		for (Priority s : priorities)
		{
			priorityItems.add(new CheckMenuItem(s.name()));
		}
		/* Add handler to all menu items */
		for (CheckMenuItem i : priorityItems)
		{
			i.setSelected(true);
			i.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					/* Handler for all menu items */
					/*
					 * To get name of clicked item use: ((CheckMenuItem)
					 * (e.getSource())).getText()
					 */
					// PRIORITY FILTER
					updateTaskDisplay(filter());
					System.out.println(((CheckMenuItem) (e.getSource())).getText() + " Enabled: "
							+ ((CheckMenuItem) (e.getSource())).isSelected());
				}
			});
		}
		/* Add items to the menu */
		for (CheckMenuItem i : priorityItems)
		{
			priorityMenu.getItems().add(i);
		}
		/* Set menu to column */
		PriorityColumn.setContextMenu(priorityMenu);
		/********************** End Priority menu **********************/
		
		/********************** Date Context Menu **********************/
		final ContextMenu dateMenu = new ContextMenu();
		/* List of items to put into the list */
		dateItems = new ArrayList<>();
		/* Add menu items */
		dateItems.add(new CheckMenuItem("BEFORE"));
		dateItems.add(new CheckMenuItem("EQUAL"));
		dateItems.add(new CheckMenuItem("AFTER"));
		// Add handler to all menu items
		for (CheckMenuItem i : dateItems)
		{
			i.setSelected(true);
			i.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					// Handler for all menu items
					// To get name of clicked item use: ((CheckMenuItem)
					// (e.getSource())).getText()
					// ORDER FILTER
					updateTaskDisplay(filter());
					System.out.println(((CheckMenuItem) (e.getSource())).getText() + " Enabled: "
							+ ((CheckMenuItem) (e.getSource())).isSelected());
				}
			});
		}
		datePickerMenuItem = new CalendarMenuItem();
		datePickerMenuItem.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				// DATE
				updateTaskDisplay(filter());
				System.out.println(((CalendarMenuItem) (e.getSource())).getDate().toString());
			}
		});
		dateMenu.getItems().add(datePickerMenuItem);
		/* Add items to the menu */
		for (CheckMenuItem i : dateItems)
		{
			dateMenu.getItems().add(i);
		}
		/* Set menu to column */
		DateColumn.setContextMenu(dateMenu);
		/********************** End Date menu **********************/
		
		TaskTable.setItems(tasks);
		
		TaskTable.getSelectionModel().selectedItemProperty().addListener(extracted());
		
		TaskTable.setRowFactory(new Callback<TableView<Task>, TableRow<Task>>()
		{
			@Override
			public TableRow<Task> call(TableView<Task> tableView)
			{
				final TableRow<Task> row = new TableRow<>();
				final ContextMenu contextMenu = new ContextMenu();
				final MenuItem completeTaskItem = new MenuItem("Complete Task");
				final MenuItem editTaskItem = new MenuItem("Edit Task");
				final MenuItem removeTaskItem = new MenuItem("Remove Task");
				final MenuItem addReminderItem = new MenuItem("Add Reminder");
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
						updateTaskDisplay(filter());
						if (row.getItem().getCompleted().equals(""))
							InterfaceLauncher.CurrentUser.removeTask(row.getItem());
						else if (row.getItem().getCompleted().equals("C"))
							InterfaceLauncher.CurrentUser.removeCompletedTask(row.getItem());
						
					}
				});
				// Complete task right click option
				completeTaskItem.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						if (row.getItem().getCompleted().equals(""))
							InterfaceLauncher.CurrentUser.completeTask(row.getItem());
						
						updateTaskDisplay(filter());
					}
				});
				addReminderItem.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						Stage reminderStage = new Stage();
						Parent reminder;
						try
						{
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddNewReminderWindow.fxml"));
							reminder = (Parent) fxmlLoader.load();
							AddReminderController controller = fxmlLoader.<AddReminderController> getController();
							controller.setTask(row.getItem());
							controller.setParent(cont);
							Scene reminderScene = new Scene(reminder);
							reminderStage.setTitle("Add New Reminder");
							reminderStage.setScene(reminderScene);
							reminderStage.show();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				});
				contextMenu.getItems().add(addReminderItem);
				contextMenu.getItems().add(editTaskItem);
				contextMenu.getItems().add(completeTaskItem);
				contextMenu.getItems().add(removeTaskItem);
				row.contextMenuProperty()
						.bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
				return row;
			}
		});
	}
	
	public List<Task> filter()
	{
		System.out.println("Filter");
		CalendarMenuItem cM = (CalendarMenuItem) datePickerMenuItem;
		Filter f = Filter.create(categoryItems, priorityItems, dateItems.subList(0, 3), cM);
		System.out.println("Normal list size: " + InterfaceLauncher.CurrentUser.getTasks().size());
		List<Task> toFilter = new ArrayList<>(InterfaceLauncher.CurrentUser.getTasks());
		toFilter.addAll(InterfaceLauncher.CurrentUser.getCompletedTasks());
		
		List<Task> filtered = f.filter(toFilter);
		System.out.println("Filtered list size: " + filtered.size());
		return filtered;
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
	
	public void updateTaskDisplay(List<Task> taskList)
	{
		//User user = InterfaceLauncher.CurrentUser;
		tasks = FXCollections.observableArrayList(taskList);
		//tasks.addAll(InterfaceLauncher.CurrentUser.getCompletedTasks());
		TaskTable.setItems(tasks);
	}
	
	public void refreshMenuItems(List<Task> taskList)
	{		
		/* Refresh Categories */
		categoryItems = new ArrayList<>();
		final ContextMenu categoryMenu = new ContextMenu();
		ArrayList<String> categories = getCategories(taskList);
		/* Add menu items */
		for (String s : categories)
		{
			categoryItems.add(new CheckMenuItem(s));
		}
		/* Add handler to all menu items */
		for (CheckMenuItem i : categoryItems)
		{
			i.setSelected(true);
			i.setOnAction(new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent e)
				{
					/* Handler for all menu items */
					/*
					 * To get name of clicked item use: ((CheckMenuItem)
					 * (e.getSource())).getText()
					 */
					// CATEGORY FILTERS
					System.out.println("Refresh list");
					System.out.println(((CheckMenuItem) (e.getSource())).getText() + " Enabled: "
							+ ((CheckMenuItem) (e.getSource())).isSelected());
					updateTaskDisplay(filter());
				}
			});
		}
		/* Add items to the menu */
		for (CheckMenuItem i : categoryItems)
		{
			categoryMenu.getItems().add(i);
		}
		/* Set menu to column */
		CategoryColumn.setContextMenu(categoryMenu);
	}
	
	private ArrayList<String> getCategories(List<Task> taskList)
	{
		ArrayList<String> arr = new ArrayList<String>();
		//User user = InterfaceLauncher.CurrentUser;
		for (Task t : taskList)
		{
			String cat = t.getCategory();
			if (!arr.contains(cat))
			{
				arr.add(cat);
			}
		}
		/*
		for (Task t : user.getCompletedTasks())
		{
			String cat = t.getCategory();
			if (!arr.contains(cat))
			{
				arr.add(cat);
			}
		}
		*/
		return arr;
	}
	
	public static class CalendarMenuItem extends CustomMenuItem
	{
		static DatePicker datePicker = new DatePicker(LocalDate.now());
		
		public CalendarMenuItem()
		{
			super(datePicker);
			setHideOnClick(false);
		}
		
		public LocalDate getDate()
		{
			return datePicker.getValue();
		}
	}
	
}
