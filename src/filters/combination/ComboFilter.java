package filters.combination;

import filters.Filter;

/**
 * A ComboFilter is a type of Filter that is composed
 * of two separate filters. This allows for flexibility
 * since you can combine multiple different Filter objects
 * into one in an elegant manner, instead of having to hard code
 * each one.
 * 
 * This is an abstract class because there can be various
 * types of ComboFilters, each with a different relationship
 * that binds the two separate filters together.
 * 
 * Each child class will implement it's own accept method,
 * however all ComboFilters are alike in that they all have
 * two separate Filter objects that they are combining.
 *
 * @author Freddy
 *
 */
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
