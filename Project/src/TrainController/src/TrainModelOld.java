//package system;
/**
 * Temporary class to simulate interaction between train controller and model.  Will be replaced with real model in the future.
 */
public class TrainModelOld {
	private final double MASS = 37103;	// kg
	private final double EMERGENCY_BRAKE_ACCELERATION_RATE_SI = -2.73;
	private final double SERVICE_BRAKE_ACCELERATION_RATE_SI = -1.2;

	private double power;			// W
	private double positionSI;		// m
	private double position;		// ft
	private double velocitySI;		// m/s
	private double velocity;		// ft/s
	private double accelerationSI;	// m/s^2
	private double acceleration;	// ft/s^2
	
	private boolean engineFailure = false;
	private boolean brakeFailure = false;
	private boolean signalPickupFailure = false;
	
	private boolean serviceBrakeActive;
	private boolean emergencyBrakeActive;
	
	public TrainModel(String id) {
		this.velocitySI = 1.0;
		this.positionSI = 1.0;
		this.serviceBrakeActive = false;
		this.emergencyBrakeActive = false;
	}
	
	/**
	 * Update acceleration based on power; velocity and position based on new acceleration and deltaT
	 * @param deltaT Time in millis since last tick
	 */
	public void tick(double deltaT) {
		double oldAccelerationSI = this.accelerationSI;
		double newAccelerationSI;
		double oldVelocitySI = this.velocitySI;
		double newVelocitySI;
		double oldPositionSI = this.positionSI;
		double newPositionSI;
		
		
		if (this.emergencyBrakeActive || this.serviceBrakeActive) {	// brake applied - acceleration is a constant
			if (oldVelocitySI > 0) {
				newAccelerationSI = this.emergencyBrakeActive ? this.EMERGENCY_BRAKE_ACCELERATION_RATE_SI : this.SERVICE_BRAKE_ACCELERATION_RATE_SI;
				newVelocitySI = calculateNewVelocitySI(oldVelocitySI, newAccelerationSI, deltaT);
				newPositionSI = calculateNewPositionSI(oldPositionSI, newVelocitySI, oldVelocitySI, deltaT);
			} else { // train is stopped - keep it stopped
				newAccelerationSI = 0.0;
				newVelocitySI = 0.0;
				newPositionSI = oldPositionSI;
			}
		} else {	// no brake applied, so calculate forward acceleration based on power
			if (this.power > 0) {
				newAccelerationSI = calculateNewAccelerationSI(this.power, oldVelocitySI, this.MASS);
				newVelocitySI = calculateNewVelocitySI(oldVelocitySI, newAccelerationSI, deltaT);
				newPositionSI = calculateNewPositionSI(oldPositionSI, newVelocitySI, oldVelocitySI, deltaT);
			} else {	// no brake applied, no power commanded - either coast along or stay stopped (a=0 either way)
				newAccelerationSI = 0.0;
				if (oldVelocitySI > 0) {	// coasting
					newVelocitySI = calculateNewVelocitySI(oldVelocitySI, newAccelerationSI, deltaT);
					newPositionSI = calculateNewPositionSI(oldPositionSI, newVelocitySI, oldVelocitySI, deltaT);
				} else {	// stopped
					newVelocitySI = 0;
					newPositionSI = oldPositionSI;
				} 
			}
		}		
		
		// save new values
		this.accelerationSI = newAccelerationSI;
		this.velocitySI = newVelocitySI;
		this.positionSI = newPositionSI;
		
		this.acceleration = metersToFeet(newAccelerationSI);
		this.velocity = metersToFeet(newVelocitySI);
		this.position = metersToFeet(newPositionSI);
	}
	
	/**
	 * Calculate new acceleration from power and force relationships:
	 * <pre>		power = force * velocity
	 * 		force = mass * acceleration
	 * 		...
	 * 		acceleration = power / velocity / mass</pre>
	 * Fake a small value for zero velocity to avoid division by zero.
	 * @param power
	 * @param oldVelocitySI
	 * @param mass
	 * @return
	 */
	public double calculateNewAccelerationSI(double power, double oldVelocitySI, double mass) {
		oldVelocitySI = Math.max(oldVelocitySI, 0.1);	// no division by zero - can't accelerate from stopped, must fake small velocity
		double newAccelerationSI = power / oldVelocitySI / mass;
		return newAccelerationSI;
	}
	
	/**
	 * Calculate new velocity from kinematic equation v = v0 + a*t
	 * @param oldVelocitySI Previous velocity in m/s
	 * @param newAccelerationSI New acceleration in m/s^2
	 * @param deltaT Time elapsed in milliseconds
	 * @return New velocity in m/s
	 */
	public double calculateNewVelocitySI(double oldVelocitySI, double newAccelerationSI, double deltaT) {
		double newVelocitySI = oldVelocitySI + newAccelerationSI * millisToSeconds(deltaT);
		newVelocitySI = Math.max(newVelocitySI, 0);	// no negative velocities
		return newVelocitySI;
	}
	
	/**
	 * Calculate new position from the average velocity kinematic equation deltaX = (1/2) (v0 + v) * t
	 * @param oldPositionSI Old position in m
	 * @param newVelocitySI New velocity in m/s
	 * @param oldVelocitySI Old velocity in m/s
	 * @param deltaT Time elapsed in milliseconds
	 * @return New position in m
	 */
	public double calculateNewPositionSI(double oldPositionSI, double newVelocitySI, double oldVelocitySI, double deltaT) {
		double deltaX = 0.5 * (oldVelocitySI + newVelocitySI) * millisToSeconds(deltaT);
		double newPositionSI = oldPositionSI + deltaX;
		return newPositionSI;
	}
	
	/**
	 * Receive a power command from the train controller. Physics are updated on next tick()
	 * @param power Power in Watts, always non-negative. To command a deceleration, controller will send zero power and activate service brake.
	 */
	public void receivePowerCommand(double power) {
		this.power = power;
	}
	
	public double getCurrentVelocitySI() {
		return this.velocitySI;
	}
	
	public void setCurrentVelocitySI(double newVelocitySI) {
		this.velocitySI = newVelocitySI;
	}
	
	public double getMass() {
		return this.MASS;
	}
	
	public boolean getEngineFailure() {
		return this.engineFailure;
	}
	
	public void setEngineFailure(boolean fail) {
		this.engineFailure = fail;
	}
	
	public boolean getBrakeFailure() {
		return this.brakeFailure;
	}
	
	public void setBrakeFailure(boolean fail) {
		this.brakeFailure = fail;
	}
	
	public boolean getSignalPickupFailure() {
		return this.signalPickupFailure;
	}
	
	public void setSignalPickupFailure(boolean fail) {
		this.signalPickupFailure = fail;
	}
	
	public void activateEmergencyBrake() {
		this.emergencyBrakeActive = true;
	}
	
	public void deactivateEmergencyBrake() {
		this.emergencyBrakeActive = false;
	}
	
	public void activateServiceBrake() {
		this.serviceBrakeActive = true;
	}
	
	public void deactivateServiceBrake() {
		this.serviceBrakeActive = false;
	}
	
	
	
	public double millisToSeconds(double millis) {
		return millis / 1000.0;
	}
	
	public double secondsToMillis(double seconds) {
		return seconds * 1000.0;
	}
	
	public double metersToFeet(double meters) {
		return meters * 3.28084;
	}
	
	public double feetToMeters(double feet) {
		return feet * 0.3048;
	}
	
	
	
}
