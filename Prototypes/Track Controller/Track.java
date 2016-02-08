public class Track implements TrackInterface
{
	public TrackInterface next;
	public TrackInterface prev;
	public int speedLimit;
	public int CTCspeed;
	public boolean light;
	public boolean train;
	
	public Track()
	{
		next = null;
		prev = null;
		light = false;
		train = false;
	}
	
	public Track(Track next, Track prev)
	{
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
}
