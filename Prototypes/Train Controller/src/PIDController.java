//package system;

public class PIDController {
	private double Kp;
	private double Ki;
	
	private double ep;	// current error
	private double ep_prev;
	private double ei;	// time-weighted past error
	private double ei_prev;
	
	public double _lastPowerCalculation;
	
	public PIDController(double trainMass) {
		//this.Kp = trainMass / 10.0;
		this.Kp = 10000.0;
		this.Ki = 10.0;
		
		this.ep = 0;
		this.ep_prev = 0;
		this.ei = 0;
		this.ei_prev = 0;
	}
	
	/***
	 * Calculate the power that should be used to achieve target velocity
	 * @param currentVelocity The current speed of the train
	 * @param targetVelocity The target speed of the train
	 * @param deltaT Millis since last calculation
	 * @return The optimal power in watts. Might be more than max train power
	 */
	public double calculatePower(double currentVelocity, double targetVelocity, double deltaT) {
		double power;
		double deltaTSec = deltaT / 1000.0;
		
		// new errors
		this.ep_prev = this.ep;
		this.ei_prev = this.ei;
		this.ep = targetVelocity - currentVelocity;
		this.ei = this.ei_prev + (deltaTSec / 2.0) * (this.ep + this.ep_prev);
		//this.ei += this.ep * (deltaT / 1000.0);
		
		power = this.Kp * this.ep + this.Ki * this.ei;
		
		this._lastPowerCalculation = power;
		return power;
	}
	
}
