package filters.impl;

import java.time.LocalDateTime;

import filters.DateFilter;
import filters.Order;
import task.Task;

public class DueDateFilter extends DateFilter
{
	public DueDateFilter(LocalDateTime date, Order order)
	{
		super(date, order);
	}

	@Override
	public boolean accept(Task t)
	{
		if(order == Order.BEFORE)
			return date.isBefore(t.getDueDate());
		else if(order == Order.AFTER)
			return date.isAfter(t.getDueDate());
		
		return date.equals(t.getDueDate());
	}
}
