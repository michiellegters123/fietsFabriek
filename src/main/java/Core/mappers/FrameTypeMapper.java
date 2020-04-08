package Core.mappers;

import Core.bike.FrameType;

public class FrameTypeMapper
{
	public FrameType map(String name)
	{
		if (name.equals("Type-A"))
			return FrameType.TypeA;
		else if(name.equals("Type-B"))
			return FrameType.TypeB;
		else if(name.equals("Type-C"))
			return FrameType.TypeC;

		throw new IllegalArgumentException("Unknown frame type");
	}

	public String map(FrameType type)
	{
		switch (type)
		{
			case TypeB:
				return "Type-B";
			case TypeA:
				return "Type-A";
			case TypeC:
				return "Type-C";
		}

		throw new IllegalArgumentException("Unknown frame type");
	}
}
