import java.util.ArrayList;


public class CTCWindow {
	
	private Config config;
	
	public CTCWindow() {
		
	}
	
	public void tick(double deltaT) {
		ArrayList<Train> allTrains = config.getAllTrains();
		ArrayList<TrackController> allTrackControllers = config.getAllTrackControllers();

		// flip all switches to correct position
		for (TrackController trackController : allTrackControllers) {
			// get occupancy+switchstate arrays
			// process arrays
			ArrayList<Boolean> switchStates = trackController.getSwitchStates();
			for (int i = 0; i < switchStates.size(); i++) {
				// get BlockSwitch from i
				// set this BlockSwitch to switched or unswitched
				BlockSwitch blockSwitch = config.trackControllerManager.getBlockFromTrackControllerSwitchArray(trackController, i);
				boolean flipped = switchStates.get(i);
				blockSwitch.setFlipped(flipped);
			}
		}
		
		
		
		
		
		// move trains forward if their current block is no longer occupied
		for (Train train : allTrains) {
			// if train.getCurrentBlock() is not occupied according to track controller(s)
			// then move train forward one block
			BlockInterface trainCurrentBlock = train.getCurrentBlock();
			boolean trainBlockOccupied = config.trackControllerManager.isBlockOccupied(trainCurrentBlock);
			if (!trainBlockOccupied) {
				// this train must have moved forward one block since its last known block is not occupied
				train.moveForwardOneBlock();
			}
		}
		
		
		
		
		
	}
	
}
