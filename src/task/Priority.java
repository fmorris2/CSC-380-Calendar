package task;

import java.awt.Color;

/**
 * This enum represents task priorities and the
 * colors associated with each.
 * 
 * @author Freddy
 */
public enum Priority
{
	LOW(Color.GREEN),
	MEDIUM(Color.YELLOW),
	HIGH(Color.RED);
	
	private Color color;
	
	Priority(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
}
