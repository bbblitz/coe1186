public class TrackModel
{
	int blockCount;
	int switchCount;
	TestTrackModelUI ttmui;
	
	
	public TrackModel()
	{
		ttmui = new TestTrackModelUI(blockCount, switchCount);
	}
	
	public TrackModel(int blockCount, int switchCount)
	{
		ttmui = new TestTrackModelUI(blockCount, switchCount);
		this.blockCount = blockCount;
	}
	
	public void relayAuthority(int authority, int blockID)
	{
		ttmui.log.append("Sent Authority of "+authority+" to block "+blockID+"\n");
	}
	
	public boolean[] getBlockOccupancies()
	{
		return ttmui.occupancy;
	}
	
	public void recieveSwitchStates(boolean[] switches)
	{
		ttmui.updateSwitches(switches);
	}
	
	public void relaySpeed(int speed, int blockID)
	{
		
		ttmui.log.append("Sent speed of "+speed+" to block "+blockID+"\n");
	}
	
	public TrackFailState[] getFailStates()
	{
		return ttmui.tfs;
	} 
	
	public void createTrain(int line)
	{
		System.out.println("Created Train on Line "+line);
	}
	
	public static void main(String[] args)
	{
		TrackModel tm = new TrackModel(100, 3);
		tm.recieveSwitchStates(new boolean[3]);
	}
}
