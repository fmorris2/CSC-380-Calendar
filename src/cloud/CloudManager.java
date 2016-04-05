package cloud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class CloudManager
{
	private static final String DB_URL = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3113873";
	private static final String USER = "sql3113873";
	private static final String PASS = "yxVJdsdt5F";
	
	private Connection connection;
	private Properties connectionProps;
	
	public CloudManager()
	{
		connectionProps = new Properties();
		connectionProps.put("user", USER);
		connectionProps.put("password", PASS);
	}
	
	private void connect()
	{
		try
		{
			connection = DriverManager.getConnection(DB_URL, connectionProps);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		if(connection == null)
			connect();
		
		return connection;
	}
	
	public static void main(String[] args)
	{
		CloudManager manager = new CloudManager();
		try
		{
			System.out.println("Connection is alive: " + manager.getConnection().isValid(5));
			Statement test = manager.getConnection().createStatement();
			ResultSet result = test.executeQuery("SELECT * FROM users");
			if(!result.next())
				System.out.println("Result set is empty");
			else
				System.out.println(result.getString("firstName"));
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
