package filters.impl;

import java.time.LocalDateTime;

import filters.DateFilter;
import filters.Order;
import task.Task;

public class CreationDateFilter extends DateFilter
{
	public CreationDateFilter(LocalDateTime date, Order order)
	{
		super(date, order);
	}

	@Override
	public boolean accept(Task t)
	{
		if(order == Order.BEFORE)
			return date.isBefore(t.getCreationDate());
		else if(order == Order.AFTER)
			return date.isAfter(t.getCreationDate());
		
		return date.equals(t.getCreationDate());
	}
	
}
