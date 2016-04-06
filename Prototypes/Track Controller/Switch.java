public class Switch extends Track
{
	public Track divergent;
	public boolean switchPosition;
	
	public Switch()
	{
		name = "";
		next = null;
		prev = null;
		divergent = null;
		light = false;
		train = false;	
		switchPosition = false;
	}
	
	public Switch(String name)
	{
		this.name = name;
		next = null;
		prev = null;
		divergent = null;
		light = false;
		train = false;	
		switchPosition = false;
	}
	
	public Switch(String name, Track next, Track previous, Track divergent)
	{
		this.name = name;
		this.next = next;
		this.prev = prev;
		this.divergent = divergent;
		light = false;
		train = false;
	}
	
	public void toggle()
	{
		switchPosition = !switchPosition;
	}
	
	public void diverge()
	{
		switchPosition = true;
	}
	
	public void straight()
	{
		switchPosition = false;
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
	
	public void connectDivergent(Track divergent)
	{
		this.divergent = divergent;
		divergent.prev = this;
	}
	
}
