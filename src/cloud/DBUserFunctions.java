package cloud;

import group.Group;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import task.Task;
import user.Permissions;
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
			loadGroups(resultSet, user);
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
	
	private static void loadCompletedTasks(ResultSet resultSet, User user)
	{
		
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
	
	@SuppressWarnings("unchecked")
	private static void loadGroups(ResultSet resultSet, User user)
	{
		try
		{
			Object unchecked;
			Blob groupIdentifiers = resultSet.getBlob("groups");
			unchecked = BlobHandler.deserialize(groupIdentifiers.getBinaryStream());
			List<String> groupNames = unchecked == null ? new ArrayList<>() : (List<String>)unchecked;
			System.out.println("groupNames size: " + groupNames.size());
			
			for(String name : groupNames)
			{
				String query = "SELECT * FROM groups WHERE groupName='"+name+"'";
				ResultSet results = CloudManager.getConnection().createStatement().executeQuery(query);
				
				if(results.next()) //Group found in groups table
				{
					Group g = new Group();
					//basic strings
					g.setGroupName(name);
					g.setGroupDescription(results.getString("groupDescription"));
					
					//blobs
					//group tasks
					unchecked = BlobHandler.deserialize(results.getBlob("groupTasks").getBinaryStream());
					List<Task> groupTasks = unchecked == null ? new ArrayList<>() : (List<Task>)unchecked;
					g.setGroupTasks(groupTasks);
					
					//groupCompletedTasks
					unchecked = BlobHandler.deserialize(results.getBlob("groupCompletedTasks").getBinaryStream());
					List<Task> groupCompletedTasks = unchecked == null ? new ArrayList<>() : (List<Task>)unchecked;
					g.setGroupCompletedTasks(groupCompletedTasks);
					
					//groupMembers
					unchecked = BlobHandler.deserialize(results.getBlob("groupMembers").getBinaryStream());
					List<User> groupMembers = unchecked == null ? new ArrayList<>() : (List<User>)unchecked;
					g.setGroupMembers(groupMembers);
					
					//groupLeader
					unchecked = BlobHandler.deserialize(results.getBlob("groupLeader").getBinaryStream());
					User groupLeader = unchecked == null ? new User() : (User)unchecked;
					g.setGroupLeader(groupLeader);
					
					//permissions
					unchecked = BlobHandler.deserialize(results.getBlob("permissions").getBinaryStream());
					Map<User, Permissions> permissions = unchecked == null ? new HashMap<>() : (HashMap<User, Permissions>)unchecked;
					g.setPermissions(permissions);
					
					//add the group to the user
					user.addGroup(g);
				}
			}
			
			System.out.println("Groups loaded");
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
	}
}
