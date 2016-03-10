package filters;

import java.util.ArrayList;
import java.util.List;

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

	public List<Task> filter(List<Task> tasks)
	{
		List<Task> copy = new ArrayList<>(tasks);
		
		for(Task t : tasks)
			if(!accept(task))
				copy.remove(t);
		
		return copy;
	}
	
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
