package filters.combination;

import filters.Filter;

public abstract class ComboFilter extends Filter
{
	protected Filter one;
	protected Filter two;
	
	public ComboFilter(Filter one, Filter two)
	{
		this.one = one;
		this.two = two;
	}
}
