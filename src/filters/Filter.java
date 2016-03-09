package filters;

import filters.combination.impl.AndFilter;
import filters.combination.impl.OrFilter;
import task.Task;

public abstract class Filter
{
	private Task task;
	
	public Filter()
	{}
	
	public Filter(Task t)
	{
		this.task = t;
	}
	
	public abstract boolean accept(Task t);

	public Filter and(Filter other)
	{
		return new AndFilter(this, other);
	}
	
	public Filter or(Filter other)
	{
		return new OrFilter(this, other);
	}
	
	protected Task getTask()
	{
		return task;
	}
}
