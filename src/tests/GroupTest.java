package tests;

import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Test;

import filters.Filter;
import filters.Order;
import filters.impl.CategoryFilter;
import filters.impl.DueDateFilter;

public class GroupTest {

	@Test
	public void test() {
		Filter f = new CategoryFilter("homework").and(
				new DueDateFilter(LocalDateTime.now(), Order.EQUAL, Order.BEFORE));
		
		fail("Not yet implemented");
	}

}
