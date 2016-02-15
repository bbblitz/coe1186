package system;

public class PIDController {
	private double Kp;
	private double Ki;
	private double Kd;
	
	private double ep;	// current error
	private double ep_prev;
	private double ei;	// time-weighted past error
	
	public PIDController(double trainMass) {
		Kp = trainMass / 1000.0;
		Ki = 0;
		Kd = 0;
		
		ep = 0;
		ep_prev = 0;
		ei = 0;
	}
	
	/***
	 * Calculate the power that should be used to achieve target velocity
	 * @param currentVelocity The current speed of the train
	 * @param targetVelocity The target speed of the train
	 * @param deltaT The amount of time that has passed since last calculation
	 * @return The optimal power in watts. Might be more than max train power
	 */
	public double calculatePower(double currentVelocity, double targetVelocity, double deltaT) {
		double power;
		
		// new errors
		ep_prev = ep;
		ep = targetVelocity - currentVelocity;
		ei += ep * deltaT;
		
		power = Kp * ep + Ki * ei + Kd * _getDerivativeError(deltaT);
		
		return power;
	}
	
	private double _getDerivativeError(double deltaT) {
		if (deltaT == 0) {
			return 0.0;
		} else {
			return (ep - ep_prev) / deltaT;
		}
	}
	
}