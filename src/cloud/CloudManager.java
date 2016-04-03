package cloud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class CloudManager
{
	private static final String DB_URL = "jdbc:mysql://fcscripting.com:3306/csc380";
	private static final String USER = "380admin";
	private static final String PASS = "nintendo";
	
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
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
