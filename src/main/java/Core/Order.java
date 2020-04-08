package Core;

import Core.bike.Bike;

import java.util.ArrayList;

public class Order
{
	private ArrayList<Bike> bikes = new ArrayList<Bike>();

	public ArrayList<Bike> getBikes()
	{
		return bikes;
	}

	public void addBike(Bike bike)
	{
		bikes.add(bike);
	}
}
