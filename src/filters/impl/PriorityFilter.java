package filters.impl;

import filters.Filter;
import task.Priority;
import task.Task;

public class PriorityFilter extends Filter
{
	private Priority priority;
	
	public PriorityFilter(Priority priority)
	{
		this.priority = priority;
	}
	
	@Override
	public boolean accept(Task t)
	{
		return t.getPriority() == priority;
	}

}
