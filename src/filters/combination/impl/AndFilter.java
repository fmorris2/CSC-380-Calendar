package filters.combination.impl;

import filters.Filter;
import filters.combination.ComboFilter;
import task.Task;

public class AndFilter extends ComboFilter
{
	public AndFilter(Filter one, Filter two)
	{
		super(one, two);
	}

	@Override
	public boolean accept(Task t)
	{
		return one.accept(t) && two.accept(t);
	}

}
