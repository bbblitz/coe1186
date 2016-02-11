package system;

public class TrainController {
	private int trainID;
	private TrainModel trainModel;
	
	/* train control law */
	private final double MAX_TRAIN_POWER = 120000.0;	// 120kW, from train spec sheet
	private final double K_P = 80000.0;		// proportional gain
	private final double K_I = 300.0;		// integral gain
	private final double T = 0.1;			// sampling interval
	private double e_k;	// proportional error
	private double e_k_prev;
	private double u_k;	// integral error
	private double u_k_prev;
	
	
	/* signal pickups */
	private double velocityFromTrainOperator;
	private double velocityFromCTC;
	private double authorityFromTrainOperator;
	private double authorityFromCTC;
	
	/* train status */
	private double currentVelocity;
	private boolean doorsOpenLeft;
	private boolean doorsOpenRight;
	private boolean lightsOn;
	private String nextStation;
	
	public TrainController(int trainID, TrainModel trainModel) {
		this.trainID = trainID;
		this.trainModel = trainModel;
		
		// train status
		currentVelocity = 0;
		doorsOpenLeft = false;
		doorsOpenRight = false;
		lightsOn = false;
		nextStation = "";
	}
	
	public double calculatePower() {
		boolean engineFailure = trainModel.getEngineFailure();
		boolean brakeFailure = trainModel.getBrakeFailure();
		boolean signalPickupFailure = trainModel.getSignalPickupFailure();
		
		if (engineFailure || brakeFailure || signalPickupFailure) {
			// stop the train!!!
			return 0;
		} else {
			// go to next iteration
			u_k_prev = u_k;
			e_k_prev = e_k;

			// decide best velocity to target
			double targetVelocity = Math.min(velocityFromTrainOperator, velocityFromCTC);
			e_k = targetVelocity - currentVelocity;
			
			u_k = u_k_prev + (T / 2) * (e_k + e_k_prev);
			double calculatedPower = K_P * e_k + K_I * u_k;
			if (calculatedPower > MAX_TRAIN_POWER) {
				// too much power! use the last u_k to calculate a power that's acceptable
				u_k = u_k_prev;
				calculatedPower = K_P * e_k + K_I * u_k;
			}
			return calculatedPower;
		}
	}
	
	public void openDoorsLeft() {
		doorsOpenLeft = true;
	}
	
	public void openDoorsRight() {
		doorsOpenRight = true;
	}
	
	public void closeDoorsLeft() {
		doorsOpenLeft = false;
	}
	
	public void closeDoorsRight() {
		doorsOpenRight = false;
	}
	
	public void turnOnLights() {
		lightsOn = true;
	}
	
	public void turnOffLights() {
		lightsOn = false;
	}
	
	
	
	/**
	 * Forced failures
	 */
	
	public void forceEngineFailure() {
		trainModel.setEngineFailure(true);
	}
	
	public void fixEngineFailure() {
		trainModel.setEngineFailure(false);
	}
	public void forceBrakeFailure() {
		trainModel.setBrakeFailure(true);
	}
	
	public void fixBrakeFailure() {
		trainModel.setBrakeFailure(false);
	}
	public void forceSignalPickupFailure() {
		trainModel.setSignalPickupFailure(true);
	}
	
	public void fixSignalPickupFailure() {
		trainModel.setSignalPickupFailure(false);
	}
	
}