package cloud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Properties;

public class CloudManager
{
	private static final String DB_URL = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3113873";
	private static final String USER = "sql3113873";
	private static final String PASS = "yxVJdsdt5F";
	
	private static Connection connection;
	private static Properties connectionProps;
	
	static
	{
		connectionProps = new Properties();
		connectionProps.put("user", USER);
		connectionProps.put("password", PASS);
	}
	
	private static void connect()
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
	
	public static Connection getConnection()
	{
		try
		{
			if(connection == null || !connection.createStatement().execute("SELECT * FROM users LIMIT 1"))
				connect();
		}
		catch(SQLTimeoutException e)
		{
			System.out.println("DB connection timed out... refreshing");
			connect();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return connection;
	}
}
