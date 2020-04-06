package Core.bike;

public class Frame
{
	private int size;
	private int wheelSize;
	private FrameType frameType;

	public Frame(int size, int wheelSize, FrameType frameType)
	{
		this.frameType = frameType;
		setWheelSize(wheelSize);
		setSize(size);
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		validateSizes(wheelSize, size);

		if ((size > 66 || size < 46) || size % 2 != 0)
		{
			throw new IllegalArgumentException("invalid size");
		}

		this.size = size;
	}

	public int getWheelSize()
	{
		return wheelSize;
	}

	public void setWheelSize(int wheelSize)
	{
		validateSizes(wheelSize, size);

		switch (wheelSize)
		{
			case 24:
			case 26:
			case 28:
				this.wheelSize = wheelSize;
				break;
			default:
				throw new IllegalArgumentException("invalid size");
		}
	}

	private void validateSizes(int wheelSize, int size)
	{
		if(size > 46 && size < 50 && wheelSize == 28)
			throw new IllegalArgumentException("invalid size");
	}

	public FrameType getFrameType()
	{
		return frameType;
	}

	public void setFrameType(FrameType frameType)
	{
		this.frameType = frameType;
	}
}
