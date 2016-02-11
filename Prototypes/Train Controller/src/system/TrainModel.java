package system;
/**
 * Temporary class to simulate interaction between train controller and model.  Will be replaced with real model in the future.
 */
public class TrainModel {
	private boolean engineFailure = false;
	private boolean brakeFailure = false;
	private boolean signalPickupFailure = false;
	private double currentSpeed;
	
	public TrainModel() {
		
	}
	
	public void setCurrentSpeed(double newSpeed) {
		currentSpeed = newSpeed;
	}
	
	public boolean getEngineFailure() {
		return engineFailure;
	}
	
	public void setEngineFailure(boolean fail) {
		this.engineFailure = fail;
	}
	
	public boolean getBrakeFailure() {
		return brakeFailure;
	}
	
	public void setBrakeFailure(boolean fail) {
		this.brakeFailure = fail;
	}
	
	public boolean getSignalPickupFailure() {
		return signalPickupFailure;
	}
	
	public void setSignalPickupFailure(boolean fail) {
		this.signalPickupFailure = fail;
	}
}
