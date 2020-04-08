package Core;

import Core.bike.Bike;

import java.util.ArrayList;

public class Order
{
	private int id;
	private ArrayList<Bike> bikes = new ArrayList<>();

	public ArrayList<Bike> getBikes()
	{
		return bikes;
	}

	public void addBike(Bike bike)
	{
		bikes.add(bike);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
