public class GreenLineController	//doesn't have to only be green line
{
	private TrackController topLoop;
	private TrackController bottomLoop;
	private boolean[] blocks;
	private boolean[] switches;
	
	public static void main(String[] args)
	{
		
	}
	
	public boolean[] getBlockOccupancies()
	{
		//update blocks array from track controllers
		boolean[] topBlocks = topLoop.getBlockOccupancies();
		boolean[] bottomBlocks = bottomLoop.getBlockOccupancies();
		//merge into one array
		return blocks;
	}
	
	public boolean[] getSwtichStates()
	{
		//update switches
		return switches;
	}
}
