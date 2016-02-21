import java.util.BitSet;

//package system;

public class TrainController {
	private TrainModel trainModel;
	public PowerController powerController;
	
	private final double MAX_TRAIN_POWER = 120000;	// 120kW
	private final double AUTHORITY_BUFFER = 10;	// give some buffer to make sure train will never exceed authority
	
	/* signal pickups */
	private double velocityFromTrainOperator;
	private double velocityFromCTC;
	private double authorityFromCTC;
	
	/* train status */
	private double velocitySI;
	private boolean doorsOpenLeft;
	private boolean doorsOpenRight;
	private boolean lightsOn;
	private String nextStation;
	
	private double odometer;
	double _lastPowerCommand;
	
	/**
	 * Create the TrainController
	 * @param trainModel TrainModel The train model that this controller is installed on
	 */
	public TrainController(TrainModel trainModel) {
		this.trainModel = trainModel;
		this.powerController = new PowerController(trainModel.getMass());
		
		// train status
		this.velocitySI = this.trainModel.getCurrentVelocitySI();
		this.doorsOpenLeft = false;
		this.doorsOpenRight = false;
		this.lightsOn = false;
		this.nextStation = "";
		
		this.odometer = 0;
	}
	
	/**
	 * Update train's odometer and authority, and send a new power command to train
	 * @param deltaT double Milliseconds since last update
	 */
	public void tick(double deltaT) {
		// update odometer
		// deltaX = 0.5 * (v + v0) * deltaT
		double oldVelocity = this.velocitySI;
		double newVelocity = trainModel.getCurrentVelocitySI();
		double deltaX = 0.5 * (oldVelocity + newVelocity) * millisToSeconds(deltaT);
		
		this.velocitySI = newVelocity;
		this.odometer += deltaX;
		this.authorityFromCTC -= deltaX;
		this.authorityFromCTC = Math.max(0, this.authorityFromCTC);	// no negative authorities
		
		double power = calculatePower(deltaT);
		if (power < 0) {
			// best action is to slow down
			power = 0;
			this.trainModel.activateServiceBrake();
		} else if (power > 0) {
			// best action is to speed up
			this.trainModel.deactivateServiceBrake();
		}
		this._lastPowerCommand = power;
		this.trainModel.receivePowerCommand(power);
	}
	
	/**
	 * Calculate the best power to send to the train based on velocity and authority
	 * @param deltaT double Milliseconds since last calculation
	 * @return double Power in watts
	 */
	public double calculatePower(double deltaT) {
		double power;
		
		boolean engineFailure = this.trainModel.getEngineFailure();
		boolean brakeFailure = this.trainModel.getBrakeFailure();
		boolean signalPickupFailure = this.trainModel.getSignalPickupFailure();
		
		if (engineFailure || brakeFailure || signalPickupFailure) {
			// emergency!!!
			this.trainModel.activateEmergencyBrake();
			power = 0;
		} else {
			// no emergency, continue with power calculation
			if (haveEnoughAuthority()) {
				// authority is fine, turn off service brake if applied and let the train continue
				this.trainModel.deactivateServiceBrake();
				
				// decide best velocity to target
				double targetVelocity = Math.min(this.velocityFromTrainOperator, this.velocityFromCTC);
				
				// calculate new power
				double calculatedPower = this.powerController.calculatePower(this.velocitySI, targetVelocity, deltaT);
				
				// abs(power) should never be more than max train power
				power = calculatedPower > 0 ? Math.min(calculatedPower, this.MAX_TRAIN_POWER) : Math.max(calculatedPower, -1 * this.MAX_TRAIN_POWER);
			} else {
				// out of authority, brake the train to a stop (or until more authority is received)
				this.trainModel.activateServiceBrake();
				power = 0;
			}
		}
		
		return power;
	}
	
	/**
	 * If the service brake is applied now and the train is slowed at 1.2 m/s^2, will it stop within authority limits?
	 * @return boolean
	 */
	private boolean haveEnoughAuthority() {
		// v^2 = 0 = v0^2 + 2*a*deltaX
		// deltaX = -(v0^2) / (2*a)
		double stoppingDistance = -1 * Math.pow(this.velocitySI, 2) / (2.0 * -1.2);	// service brake decceleration = 1.2m/s^2
		if ((stoppingDistance + this.AUTHORITY_BUFFER) >= this.authorityFromCTC) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * First 5 bits are speed, the rest is authority
	 * @see http://docs.oracle.com/javase/7/docs/api/java/util/BitSet.html
	 * @param signalPackage
	 */
	public void receiveSignalFromRail(BitSet signalPackage) {
		BitSet bitSpeed = signalPackage.get(0, 5);	// bits 0-4 are speed
		BitSet bitAuthority = signalPackage.get(5, signalPackage.length());	// the rest is authority
		int speed = this.bitsetToInt(bitSpeed);
		int authority = this.bitsetToInt(bitAuthority);
		
		this.velocityFromCTC = speed;
		this.authorityFromCTC = authority;
	}
	
	public void receiveBeacon(byte[] beaconPackage) {
		
	}
	
	private void openDoorsLeft() {
		this.doorsOpenLeft = true;
	}
	
	private void openDoorsRight() {
		this.doorsOpenRight = true;
	}
	
	private void closeDoorsLeft() {
		this.doorsOpenLeft = false;
	}
	
	private void closeDoorsRight() {
		this.doorsOpenRight = false;
	}
	
	private void turnOnLights() {
		this.lightsOn = true;
	}
	
	private void turnOffLights() {
		this.lightsOn = false;
	}
	
	private void activateEmergencyBrake() {
		this.trainModel.activateEmergencyBrake();
	}
	
	private void deactivateEmergencyBrake() {
		this.trainModel.deactivateEmergencyBrake();
	}
	
	private void activateServiceBrake() {
		this.trainModel.activateServiceBrake();
	}
	
	private void deactivateServiceBrake() {
		this.trainModel.deactivateServiceBrake();
	}
	
	private double getAuthorityFromCTC() {
		return this.authorityFromCTC;
	}
		
	private void announceStation() {
		System.out.println("NEXT STATION: " + this.nextStation);
	}
	
	
	/**
	 * Forced failures
	 */
	
	public void forceEngineFailure() {
		this.trainModel.setEngineFailure(true);
	}
	
	public void fixEngineFailure() {
		this.trainModel.setEngineFailure(false);
	}
	public void forceBrakeFailure() {
		this.trainModel.setBrakeFailure(true);
	}
	
	public void fixBrakeFailure() {
		this.trainModel.setBrakeFailure(false);
	}
	public void forceSignalPickupFailure() {
		this.trainModel.setSignalPickupFailure(true);
	}
	
	public void fixSignalPickupFailure() {
		this.trainModel.setSignalPickupFailure(false);
	}
	
	
	public double _getLastPowerCommand() {
		return this._lastPowerCommand;
	}
	
	
	/**
	 * Forced values
	 */
	
	public void hackVelocityFromTrainOperator(double newVelocity) {
		this.velocityFromTrainOperator = newVelocity;
	}
	
	public void hackVelocityFromCTC(double newVelocity) {
		this.velocityFromCTC = newVelocity;
	}
	
	public void hackAuthorityFromCTC(double newAuthority) {
		this.authorityFromCTC = newAuthority;
	}
	
	

	private double millisToSeconds(double millis) {
		return millis / 1000.0;
	}
	
	private double secondsToMillis(double seconds) {
		return seconds * 1000.0;
	}
	
	private double metersToFeet(double meters) {
		return meters * 3.28084;
	}
	
	private double feetToMeters(double feet) {
		return feet * 0.3048;
	}
	
	private int bitsetToInt(BitSet bitset) {
		int value = 0;
		for (int i = 0; i < 64; i++) {	// max 64 bits
			if (bitset.get(i)) {
				value |= (1 << i);
			}
		}
		return value;
	}
	
	
	
}
