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
}
