public class TrackModel
{
	private LineController controller;
	
	public TrackModel(LineController controller)
	{
		this.controller = controller;
	}
	
	public void relayAuthority(int authority, int blockID)
	{
		System.out.println("Sent Authority of "+authority+" to block "+blockID);
	}
	
	public boolean[] getBlockOccupancies()
	{
		boolean[] out = new boolean[14];
		for(int i=0;i<14;i++)
		{
			out[i] = Math.random()>=0.7;
		}
		return out;
	}
	
	public void relaySpeed(int speed, int blockID)
	{
		System.out.println("Sent speed of "+speed+" to block "+blockID);
	}
	
	public TrackFailState[] getFailStates()
	{
		TrackFailState[] tfs = new TrackFailState[controller.getBlockCount()];
		int fail1 = (int)Math.random()*controller.getBlockCount();
		int fail2 = (int)Math.random()*controller.getBlockCount();
		int fail3 = (int)Math.random()*controller.getBlockCount();
		tfs[fail1] = TrackFailState.FS_TRACK_CIRCUIT_FAILURE;
		tfs[fail2] = TrackFailState.FS_BROKEN_RAIL;
		tfs[fail3] = TrackFailState.FS_POWER_FAILURE;
		return tfs;		
	} 
	
	public static void main(String[] args)
	{
		
	}
}
