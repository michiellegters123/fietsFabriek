package Core.bike;

public class Bike
{
	private int id;
	private Frame frame;
	private int BrandId;



	private Brand brand;
	private BikeType bikeType;
	private SaddleType saddleType;



	public Bike(Frame frame, BikeType bikeType, SaddleType saddleType)
	{
		this.frame = frame;
		this.bikeType = bikeType;
		this.saddleType = saddleType;
	}

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

	public SaddleType getSaddleType()
	{
		return saddleType;
	}

	public void addBrandId(int id)
	{
		this.BrandId = id;
	}

	public int getBrandId()
	{
		return BrandId;
	}

	public Brand getBrand()
	{
		return brand;
	}

	public void setBrand(Brand brand)
	{
		this.brand = brand;
	}

	public void setSaddleType(SaddleType saddleType)
	{
		this.saddleType = saddleType;
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
