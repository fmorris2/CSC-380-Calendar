package filters.impl;

import filters.Filter;
import task.Task;

public class CategoryFilter extends Filter
{
	private String category;
	
	public CategoryFilter(String category)
	{
		this.category = category;
	}
	
	@Override
	public boolean accept(Task t)
	{
		return t.getCategory().equalsIgnoreCase(category);
	}
	
}
