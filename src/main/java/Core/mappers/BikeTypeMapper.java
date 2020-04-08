package Core.mappers;

import Core.bike.BikeType;

public class BikeTypeMapper
{
	public BikeType map(String name)
	{
		if(name.equals("Man"))
			return BikeType.Man;
		else if(name.equals("Woman"))
			return BikeType.Woman;

		throw new IllegalArgumentException("Unknown bike type");
	}
}
