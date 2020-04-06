package Core;

import java.util.ArrayList;

public class OrderQueue
{
	private ArrayList<Order> orders = new ArrayList<Order>();

	public void addOrder(Order order)
	{
		this.orders.add(order);
	}

	public int getSize()
	{
		return orders.size();
	}

	public Order getOrder(int index)
	{
		return orders.get(index);
	}

	public Order consumeOrder(int index)
	{
		return this.orders.remove(index);
	}
}
