import java.util.ArrayList;


public class CTCWindow {
	
	private Config config;
	
	public CTCWindow() {
		
	}
	
	public void tick(double deltaT) {
		for (TrackController trackController : config.greenLineTrackControllers) {
			// get occupancy+switchstate array
			// process arrays
			ArrayList<Boolean> occupancies = trackController.getBlockOccupancies();
			ArrayList<Boolean> switchStates = trackController.getSwitchStates();
		}
		
		for (TrackController trackController : config.redLineTrackControllers) {
			// get occupancy+switchstate array
			// process arrays
			ArrayList<Boolean> occupancies = trackController.getBlockOccupancies();
			ArrayList<Boolean> switchStates = trackController.getSwitchStates();
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
