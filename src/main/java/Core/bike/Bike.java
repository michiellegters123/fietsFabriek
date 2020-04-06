package Core.bike;

public class Bike
{
	private Frame frame;
	private BikeType bikeType;
	private Brand brand;
	private SaddleType saddleType;

	public Bike(Frame frame, BikeType bikeType, Brand brand, SaddleType saddleType)
	{
		this.frame = frame;
		this.bikeType = bikeType;
		this.brand = brand;
		this.saddleType = saddleType;
	}

	public Frame getFrame()
	{
		return frame;
	}

	public BikeType getBikeType()
	{
		return bikeType;
	}

	public void setBikeType(BikeType bikeType)
	{
		this.bikeType = bikeType;
	}

	public Brand getBrand()
	{
		return brand;
	}

	public SaddleType getSaddleType()
	{
		return saddleType;
	}

	public void setSaddleType(SaddleType saddleType)
	{
		this.saddleType = saddleType;
	}
}
