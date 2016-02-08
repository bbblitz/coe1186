public class Crossing extends Track
{	
	public boolean closed;
	public boolean train;
	
	public Crossing()
	{
		closed = false;
	}
	
	public Crossing(Track next, Track previous)
	{
		this.next = next;
		this.prev = prev;
		light = false;
		train = false;
		closed = false;
	}
	
	public void close()
	{
		closed = true;
	}
	
	public void open()
	{
		closed = false;
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
}
