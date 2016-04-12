package cloud;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class BlobHandler
{
	public static Object deserialize(InputStream stream)
	{	
		try(ObjectInputStream ois = new ObjectInputStream(stream))
		{
			return ois.readObject();
		}
		catch(EOFException e)
		{
			System.out.println("No object to read, returning null");
			//e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
