package filters.date.impl;

import java.time.LocalDateTime;

import filters.Order;
import filters.date.DateFilter;
import task.Task;

public class CreationDateFilter extends DateFilter
{
	public CreationDateFilter(LocalDateTime date, Order... order)
	{
		super(date, order);
	}

	@Override
	public boolean accept(Task t)
	{
		for(Order o : order)
		{
			if(o == Order.BEFORE && date.isBefore(t.getCreationDate()))
				continue;
			else if(o == Order.AFTER && date.isAfter(t.getCreationDate()))
				continue;
			else if(o == Order.EQUAL && date.equals(t.getCreationDate()))
				continue;
			
			return false;
		}
		
		return true;
	}
	
}
