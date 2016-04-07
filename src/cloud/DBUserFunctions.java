package cloud;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void save(User user)
	{
		try
		{
			Statement st = CloudManager.getConnection().createStatement();
			
			st.executeUpdate("UPDATE users SET firstName='"+user.getFirstName()+"',lastName='"+user.getLastName()
					+ "',password='"+user.getPassword()+"',email='"+user.getEmail()+"',securityQuestion='"+user.getSecurityQuestion()
					+ "',securityAnswer='"+user.getSecurityAnswer()+"' WHERE username='"+user.getUsername()+"'");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("User saved");
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
		catch(Exception e)
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
		catch(Exception e)
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
		test.setFirstName("Fred");
		System.out.println("Loaded account: " + test);
		save(test);
	}
}
