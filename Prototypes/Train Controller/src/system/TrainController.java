package system;

public class TrainController {
	private int trainID;
	private TrainModel trainModel;
	private PIDController pidController;
	
	private double MAX_TRAIN_POWER = 120000;	// 120kW
	
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
		this.pidController = new PIDController(trainModel.getMass());
		
		// train status
		currentVelocity = 0;
		doorsOpenLeft = false;
		doorsOpenRight = false;
		lightsOn = false;
		nextStation = "";
	}
	
	public double calculatePower(double deltaT) {
		double power;
		
		boolean engineFailure = trainModel.getEngineFailure();
		boolean brakeFailure = trainModel.getBrakeFailure();
		boolean signalPickupFailure = trainModel.getSignalPickupFailure();
		
		if (engineFailure || brakeFailure || signalPickupFailure) {
			// stop the train!!!
			trainModel.activateEmergencyBrake();
			power = 0;
		} else {
			currentVelocity = trainModel.getCurrentSpeed();
			// decide best velocity to target
			double targetVelocity = Math.min(velocityFromTrainOperator, velocityFromCTC);
			// never deliver a power more than the max train power
			power = Math.min(pidController.calculatePower(currentVelocity, targetVelocity, deltaT), MAX_TRAIN_POWER); 
		}
		return power;
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
	
	public void activateEmergencyBrake() {
		trainModel.activateEmergencyBrake();
	}
	
	public void deactivateEmergencyBrake() {
		trainModel.deactivateEmergencyBrake();
	}
	
	public double getAuthorityFromCTC() {
		return authorityFromCTC;
	}
	
	public double getAuthorityFromTrainOperator() {
		return authorityFromTrainOperator;
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
	
	
	/**
	 * Forced values
	 */
	
	public void forceVelocityFromTrainOperator(double newVelocity) {
		velocityFromTrainOperator = newVelocity;
	}
	
	public void forceVelocityFromCTC(double newVelocity) {
		velocityFromCTC = newVelocity;
	}
	
	public void forceAuthorityFromTrainOperator(double newAuthority) {
		authorityFromTrainOperator = newAuthority;
	}
	
	public void forceAuthorityFromCTC(double newAuthority) {
		authorityFromCTC = newAuthority;
	}
	
}