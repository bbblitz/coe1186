public class TrackModel
{
	int blockCount;
	TestTrackModelUI ttmui;
	
	
	public TrackModel()
	{
		ttmui = new TestTrackModelUI(blockCount);
	}
	
	public TrackModel(int blockCount)
	{
		ttmui = new TestTrackModelUI(blockCount);
		this.blockCount = blockCount;
	}
	
	public void relayAuthority(int authority, int blockID)
	{
		System.out.println("Sent Authority of "+authority+" to block "+blockID);
	}
	
	public boolean[] getBlockOccupancies()
	{
		return ttmui.occupancy;
	}
	
	public void recieveSwitchStates(boolean[] switches)
	{
		System.out.println("Track Model Recieved Switches");
		for(int i=0;i<switches.length;i++)
		{
			System.out.println("Switch "+i+" is "+(switches[i]?"flipped ":"not flipped"));
		}
	}
	
	public void relaySpeed(int speed, int blockID)
	{
		System.out.println("Sent speed of "+speed+" to block "+blockID);
	}
	
	public TrackFailState[] getFailStates()
	{
		TrackFailState[] tfs = new TrackFailState[blockCount];
		/*int fail1 = (int)(Math.random()*(blockCount-1));
		int fail2 = (int)(Math.random()*(blockCount-1));
		int fail3 = (int)(Math.random()*(blockCount-1));
		tfs[fail1] = TrackFailState.FS_TRACK_CIRCUIT_FAILURE;
		tfs[fail2] = TrackFailState.FS_BROKEN_RAIL;
		tfs[fail3] = TrackFailState.FS_POWER_FAILURE;*/
		return tfs;		
	} 
	
	public void createTrain(int line)
	{
		System.out.println("Created Train on Line "+line);
	}
	
	public static void main(String[] args)
	{
		TrackModel tm = new TrackModel(100);
	}
}
