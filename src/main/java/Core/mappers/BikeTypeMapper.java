package Core.mappers;

import Core.bike.BikeType;
import Core.bike.SaddleType;

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

	public String map(BikeType type)
	{
		switch (type)
		{
			case Woman:
				return "Woman";
			case Man:
				return "Man";
		}

		throw new IllegalArgumentException("Unknown bike type");
	}
}
