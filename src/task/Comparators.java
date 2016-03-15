package task;

import java.util.Comparator;

/**
 * This class contains the various Comparator objects that the system will use
 * to compare tasks.
 * 
 * This is an easy and efficient way to sort tasks. With these comparators, we
 * will be able to use Collections.sort to sort collections of Tasks quickly and
 * conveniently.
 * 
 * @author Freddy
 */

public class Comparators
{
	// constants to avoid instantiating the comparator every time
	public static final Comparator<Task> CREATION_DATE = creationDateComparator();
	public static final Comparator<Task> DUE_DATE = dueDateComparator();
	public static final Comparator<Task> NAME = nameComparator();
	public static final Comparator<Task> CATEGORY = categoryComparator();
	public static final Comparator<Task> DURATION = durationComparator();
	public static final Comparator<Task> PRIORITY = priorityComparator();
	
	private static Comparator<Task> creationDateComparator()
	{
		return (Task one, Task two) -> one.getCreationDate().compareTo(two.getCreationDate());
	}
	
	private static Comparator<Task> dueDateComparator()
	{
		return (Task one, Task two) -> one.getDueDate().compareTo(two.getDueDate());
	}
	
	private static Comparator<Task> nameComparator()
	{
		return (Task one, Task two) -> one.getTaskName().compareTo(two.getTaskName());
	}
	
	private static Comparator<Task> categoryComparator()
	{
		return (Task one, Task two) -> one.getCategory().compareTo(two.getCategory());
	}
	
	private static Comparator<Task> durationComparator()
	{
		return (Task one, Task two) -> one.getDuration().compareTo(two.getDuration());
	}
	
	private static Comparator<Task> priorityComparator()
	{
		return (Task one, Task two) -> one.getPriority().compareTo(two.getPriority());
	}
}
