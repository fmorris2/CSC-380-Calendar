package filters.date.impl;

import java.time.LocalDateTime;

import filters.Order;
import filters.date.DateFilter;
import task.Task;

public class DueDateFilter extends DateFilter
{
	public DueDateFilter(LocalDateTime date, Order... order)
	{
		super(date, order);
	}

	@Override
	public boolean accept(Task t)
	{
		for(Order o : order)
		{
			if(o == Order.BEFORE && date.isBefore(t.getDueDate()))
				continue;
			else if(o == Order.AFTER && date.isAfter(t.getDueDate()))
				continue;
			else if(o == Order.EQUAL && date.equals(t.getDueDate()))
				continue;
			
			return false;
		}
		
		return true;
	}
}
