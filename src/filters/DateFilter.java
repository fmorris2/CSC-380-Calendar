package filters;

import java.time.LocalDateTime;

public abstract class DateFilter extends Filter
{
	protected LocalDateTime date;
	protected Order order;
	
	public DateFilter(LocalDateTime date, Order order)
	{
		this.date = date;
		this.order = order;
	}
}
