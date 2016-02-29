import java.util.ArrayList;


public class CTCWindow {
	
	private Config config;
	
	public CTCWindow() {
		
	}
	
	public void tick(double deltaT) {
		ArrayList<Train> allTrains = config.getAllTrains();
		
		for (TrackController trackController : config.greenLineTrackControllers) {
			// get occupancy+switchstate arrays
			// process arrays
			ArrayList<Boolean> switchStates = trackController.getSwitchStates();
			for (int i = 0; i < switchStates.size(); i++) {
				// get BlockSwitch from i
				// set this BlockSwitch to switched or unswitched
				BlockSwitch blockSwitch = config.getBlockFromTrackControllerSwitchArray(i);
				boolean flipped = switchStates.get(i);
				blockSwitch.setFlipped(flipped);
			}
			
			ArrayList<Boolean> blockOccupancies = trackController.getBlockOccupancies();
			for (int i = 0; i < blockOccupancies.size(); i++) {
				// get BlockInterface from i
				
			}
		}
		
		for (TrackController trackController : config.redLineTrackControllers) {
			// get occupancy+switchstate arrays
			// process arrays
			ArrayList<Boolean> switchStates = trackController.getSwitchStates();
			for (int i = 0; i < switchStates.size(); i++) {
				// get BlockSwitch from i
				// set this BlockSwitch to switched or unswitched
				BlockSwitch blockSwitch = config.getBlockFromTrackControllerSwitchArray(i);
				boolean flipped = switchStates.get(i);
				blockSwitch.setFlipped(flipped);
			}
			
			ArrayList<Boolean> blockOccupancies = trackController.getBlockOccupancies();
		}
		
		
		for (Train train : config.greenLineTrains) {
			// figure out if train has changed blocks
			// maybe calculate new authority and speed for train
		}
		
		for (Train train : config.redLineTrains) {
			// figure out if train has changed blocks
			// maybe calculate new authority and speed for train
		}
	}
	
}
