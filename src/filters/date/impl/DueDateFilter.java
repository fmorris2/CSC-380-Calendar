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
		if(order.length == 0)
			return false;
		
		int dueDay = t.getDueDate().getYear() + t.getDueDate().getDayOfYear();
		int day = date.getYear() + date.getDayOfYear();
		
		for (Order o : order)
		{
			if (o == Order.BEFORE && dueDay < day
					|| o == Order.AFTER && dueDay > day
					|| o == Order.EQUAL && dueDay == day)
				return true;
		}
		
		return false;
	}
}
