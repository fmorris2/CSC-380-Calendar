package utils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils
{
	/**
	 * Generate a random integer within the designated range, inclusive
	 * 
	 * @param min The minimum bound of the range
	 * @param max The maximum bound of the range
	 * 
	 * @author Freddy
	 */
	public static int randInt(int min, int max)
	{
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}
