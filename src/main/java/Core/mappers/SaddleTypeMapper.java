package Core.mappers;

import Core.bike.SaddleType;

public class SaddleTypeMapper
{
	public SaddleType map(String name)
	{
		if(name.equals("Man"))
			return SaddleType.Man;
		else if(name.equals("Woman"))
			return SaddleType.Woman;

		throw new IllegalArgumentException("Unknown saddle type");
	}

	public String map(SaddleType type)
	{
		switch (type)
		{
			case Woman:
				return "Woman";
			case Man:
				return "Man";
		}

		throw new IllegalArgumentException("Unknown saddle type");
	}
}
