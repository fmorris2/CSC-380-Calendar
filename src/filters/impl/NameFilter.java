package filters.impl;

import filters.Filter;
import task.Task;

public class NameFilter extends Filter
{
	private String name;
	
	public NameFilter(String name)
	{
		this.name = name;
	}
	
	@Override
	public boolean accept(Task t)
	{
		return t.getTaskName().equalsIgnoreCase(name);
	}

}
