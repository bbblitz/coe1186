public class Track implements TrackInterface
{
	public String name;
	public TrackInterface next;
	public TrackInterface prev;
	public int speedLimit;
	public int CTCspeed;
	public boolean light;
	public boolean train;
	
	public Track()
	{
		name = "";
		next = null;
		prev = null;
		light = false;
		train = false;
	}
	public Track(String name)
	{
		this.name = name;
		next = null;
		prev = null;
		light = false;
		train = false;
	}
	
	public Track(String name, Track next, Track prev)
	{
		this.name = name;
		this.next = next;
		this.prev = prev;
		this.light = light;
		this.train = train;
	}
	
	public void connectNext(Track next)
	{
		this.next = next;
		next.next = this;
	}
	
	public void connectPrev(Track prev)
	{
		this.prev = prev;
		prev.prev = this;
	}
	
	public void checkSpeed()
	{
		if(CTCspeed > speedLimit)CTCspeed = speedLimit;
	}
	
	public String toString()
	{
		return name;
	}
}
