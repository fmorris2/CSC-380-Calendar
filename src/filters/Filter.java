package filters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import filters.combination.impl.AndFilter;
import filters.combination.impl.OrFilter;
import filters.date.impl.DueDateFilter;
import filters.impl.CategoryFilter;
import filters.impl.PriorityFilter;
import interfaces.MainController.CalendarMenuItem;
import javafx.scene.control.CheckMenuItem;
import task.Priority;
import task.Task;

/**
 * This is the base abstract Filter class that serves as the foundation of the
 * filtering system. Filters will allow users to narrow down a list of tasks
 * based on specific criteria.
 * 
 * Users will select certain criteria in the UI and a Filter object will
 * automatically be constructed, and used to narrow down applicable tasks.
 * 
 * @author Freddy
 */
public abstract class Filter
{
	/**
	 * Primary function of a filter - To accept or reject a task based on the
	 * filter's criteria.
	 * 
	 * The sub-classes will implement this method, and will each have a unique
	 * implementation depending on the type of filter it is.
	 * 
	 * @param t
	 *            The task to pass through the filter
	 * @return If the task meets the filter criteria
	 */
	public abstract boolean accept(Task t);
	
	public static Filter create(List<CheckMenuItem> categories, List<CheckMenuItem> priorities, 
			List<CheckMenuItem> orders, CalendarMenuItem date)
	{
		Filter categoryFilter = null;
		Filter priorityFilter = null;
		Filter dateFilter = null;
		
		//start with categories
		for(CheckMenuItem category : categories)
		{
			if(category.isSelected())
			{
				System.out.println("Adding category " + category.getText() + " to filter");
				CategoryFilter cF = new CategoryFilter(category.getText());
				categoryFilter = categoryFilter == null ? cF : categoryFilter.or(cF);
			}
		}
		
		//next, priority
		for(CheckMenuItem priority : priorities)
		{
			if(priority.isSelected())
			{
				System.out.println("Adding priority " + Priority.valueOf(priority.getText()) + " to filter");
				PriorityFilter pF = new PriorityFilter(Priority.valueOf(priority.getText()));
				priorityFilter = priorityFilter == null ? pF : priorityFilter.or(pF);
			}
		}
		
		//dates last
		Order[] orderArr = new Order[orders.size()];
		for(int i = 0; i < orderArr.length; i++)
		{
			Order o = Order.valueOf(orders.get(i).getText());
			System.out.println("Adding order " + o + " to filter");
			orderArr[i] = o;
		}
		
		LocalDate d = date.getDate();
		LocalDateTime ldt = LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), 0, 0);
		
		System.out.println("Adding date " + ldt + " to filter");
		dateFilter = new DueDateFilter(ldt, orderArr);
		
		return categoryFilter.and(priorityFilter).and(dateFilter);
	}
	
	/**
	 * This method filters a list of tasks and returns a new, filtered list.
	 * 
	 * @param tasks
	 *            The task list to filter
	 * @return A new, filtered list based off of this filter's criteria
	 */
	public List<Task> filter(List<Task> tasks)
	{
		List<Task> copy = new ArrayList<>(tasks);
		
		for (Task t : tasks)
			if (!accept(t))
				copy.remove(t);
		
		return copy;
	}
	
	/**
	 * This method constructs an "and" relationship between two filters.
	 * 
	 * @param other
	 *            The other filter to merge with this object
	 * @return A new filter object, created by merging this filter and the other
	 *         with an "and" relationship
	 */
	public Filter and(Filter other)
	{
		return new AndFilter(this, other);
	}
	
	/**
	 * This method constructs an "or" relationship between two filters.
	 * 
	 * @param other
	 *            The other filter to merge with this object
	 * @return A new filter object, created by merging this filter and the other
	 *         with an "or" relationship
	 */
	public Filter or(Filter other)
	{
		return new OrFilter(this, other);
	}
}
