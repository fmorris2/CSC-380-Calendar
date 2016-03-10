package filters;

import java.util.ArrayList;
import java.util.List;

import filters.combination.impl.AndFilter;
import filters.combination.impl.OrFilter;
import task.Task;

/**
 * This is the base abstract Filter class that serves as the
 * foundation of the filtering system. Filters will allow users
 * to narrow down a list of tasks based on specific criteria.
 * 
 * Users will select certain criteria in the UI and a Filter object
 * will automatically be constructed, and used to narrow down applicable
 * tasks.
 * 
 * @author Freddy
 */
public abstract class Filter
{	
	/**
	 * Primary function of a filter - To accept or reject
	 * a task based on the filter's criteria.
	 * 
	 * The sub-classes will implement this method, and will each
	 * have a unique implementation depending on the type of filter
	 * it is.
	 * 
	 * @param t The task to pass through the filter
	 * @return If the task meets the filter criteria
	 */
	public abstract boolean accept(Task t);

	/**
	 * This method filters a list of tasks and returns
	 * a new, filtered list.
	 * 
	 * @param tasks The task list to filter
	 * @return A new, filtered list based off of this filter's criteria
	 */
	public List<Task> filter(List<Task> tasks)
	{
		List<Task> copy = new ArrayList<>(tasks);
		
		for(Task t : tasks)
			if(!accept(t))
				copy.remove(t);
		
		return copy;
	}
	
	/**
	 * This method constructs an "and" relationship between
	 * two filters.
	 * 
	 * @param other The other filter to merge with this object
	 * @return A new filter object, created by merging this filter and the other with an "and" relationship
	 */
	public Filter and(Filter other)
	{
		return new AndFilter(this, other);
	}
	
	/**
	 * This method constructs an "or" relationship between two filters.
	 * 
	 * @param other The other filter to merge with this object
	 * @return A new filter object, created by merging this filter and the other with an "or" relationship
	 */
	public Filter or(Filter other)
	{
		return new OrFilter(this, other);
	}
}
