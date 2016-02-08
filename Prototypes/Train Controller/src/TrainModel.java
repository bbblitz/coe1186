
public class TrainModel {
	private boolean engineFailure = false;
	private boolean brakeFailure = false;
	private boolean signalPickupFailure = false;
	
	public TrainModel() {
		
	}
	
	public boolean getEngineFailure() {
		return engineFailure;
	}
	
	public boolean getBrakeFailure() {
		return brakeFailure;
	}
	
	public boolean getSignalPickupFailure() {
		return signalPickupFailure;
	}
}
