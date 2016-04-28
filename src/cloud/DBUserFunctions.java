package cloud;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import reminders.Interval;
import task.Task;
import user.User;

public class DBUserFunctions
{
	public static boolean login(User user)
	{
		try
		{
			ResultSet resultSet;
			
			//first, verify user
			if((resultSet = verify(user)) == null || !resultSet.getString("password").equals(user.getPassword()))
				return false;
			
			//second, load user
			
			//basic strings first
			user.setFirstName(resultSet.getString("firstName"));
			user.setLastName(resultSet.getString("lastName"));
			user.setEmail(resultSet.getString("email"));
			user.setSecurityQuestion(resultSet.getString("securityQuestion"));
			user.setSecurityAnswer(resultSet.getString("securityAnswer"));
			
			//blobs next
			loadTasks(resultSet, user);
			loadCompletedTasks(resultSet, user);
			
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static User getUser(String username)
	{
		User user = new User(username, "");
		try
		{
			ResultSet resultSet;
			if((resultSet = verify(user)) != null)
			{
				//basic strings first
				user.setFirstName(resultSet.getString("firstName"));
				user.setLastName(resultSet.getString("lastName"));
				user.setPassword(resultSet.getString("password"));
				user.setEmail(resultSet.getString("email"));
				user.setSecurityQuestion(resultSet.getString("securityQuestion"));
				user.setSecurityAnswer(resultSet.getString("securityAnswer"));
				
				//blobs next
				loadTasks(resultSet, user);
				loadCompletedTasks(resultSet, user);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		
		
		return user;
	}
	
	public static void register(User user)
	{
		try
		{
			Statement st = CloudManager.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM users WHERE username='"+user.getUsername()+"'");
			if(!rs.next())
			{
				st.executeUpdate("INSERT INTO users (username) VALUES ('"+user.getUsername()+"')");
				saveUserStrings(user);
			}
			else
			{
				System.out.println("User already exists!");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void save(User user)
	{
		saveUserStrings(user);
		
		//blobs now
		saveBlob(user, "tasks", user.getTasks());
		saveBlob(user, "completedTasks", user.getCompletedTasks());
		
		System.out.println("User saved");
	}
	
	public static void saveUserStrings(User user)
	{
		try
		{
			Statement st = CloudManager.getConnection().createStatement();
			
			st.executeUpdate("UPDATE users SET firstName='"+user.getFirstName()+"',lastName='"+user.getLastName()
					+ "',password='"+user.getPassword()+"',email='"+user.getEmail()+"',securityQuestion='"+user.getSecurityQuestion()
					+ "',securityAnswer='"+user.getSecurityAnswer()+"' WHERE username='"+user.getUsername()+"'");
			
			st.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void saveBlob(User user, String columnName, Object toSave)
	{
		try
		{
			PreparedStatement ps = CloudManager.getConnection().prepareStatement("UPDATE users SET "+columnName+"=? WHERE username='"+user.getUsername()+"'");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			new ObjectOutputStream(baos).writeObject(toSave);
			ps.setObject(1, baos.toByteArray());
			ps.executeUpdate();
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void loadCompletedTasks(ResultSet resultSet, User user)
	{
		try
		{
			Blob tasksBlob = resultSet.getBlob("completedTasks");
			Object unchecked = BlobHandler.deserialize(tasksBlob.getBinaryStream());
			List<Task> tasks = unchecked == null ? new ArrayList<>() : (List<Task>)unchecked;
			user.setCompletedTasks(tasks);
			System.out.println("Completed tasks loaded");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void loadTasks(ResultSet resultSet, User user)
	{
		try
		{
			Blob tasksBlob = resultSet.getBlob("tasks");
			Object unchecked = BlobHandler.deserialize(tasksBlob.getBinaryStream());
			List<Task> tasks = unchecked == null ? new ArrayList<>() : (List<Task>)unchecked;
			user.setTasks(tasks);
			System.out.println("Tasks loaded");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private static ResultSet verify(User user) throws SQLException
	{
		String query = "SELECT * FROM users WHERE username='"+user.getUsername() + "'";
		ResultSet results = CloudManager.getConnection().createStatement().executeQuery(query);
		
		return results.next() ? results : null;
	}
	
	public static void main(String[] args)
	{
		User test = new User("fred", "morrison", "testUsername", "testPassword", "Email@email.com");
		System.out.println("Login: " + login(test));
		System.out.println("Loaded account: " + test);
		Task testTask = test.addNewTask(new Task());
		testTask.addReminder(test, Interval.FIFTEEN_MINUTES);
		System.out.println("Task list size: " + test.getTasks().size());
		save(test);
	}
}
