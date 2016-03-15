package filters.combination.impl;

import filters.Filter;
import filters.combination.ComboFilter;
import task.Task;

/**
 * This is a ComboFilter that combines two separate filters with an "or"
 * relationship.
 * 
 * @author Freddy
 *
 */
public class OrFilter extends ComboFilter
{
	public OrFilter(Filter one, Filter two)
	{
		super(one, two);
	}
	
	@Override
	public boolean accept(Task t)
	{
		return one.accept(t) || two.accept(t);
	}
	
}
