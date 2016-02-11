package system;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TrainModel trainModel = new TrainModel(37103.86);	// train mass
		TrainController trainController = new TrainController(1, trainModel);

		// controlled by timed loop in future
		double TIME_INTERVAL = 1.0;
		
		// fake the train already moving
		double CURRENT_SPEED = 10.0;
		trainModel.setCurrentSpeed(CURRENT_SPEED);
		
		// fake some speed commands
		double TARGET_SPEED = 25.0;
		trainController.forceVelocityFromCTC(TARGET_SPEED);
		trainController.forceVelocityFromTrainOperator(TARGET_SPEED);
		
		// an informed user is a happy user
		System.out.println("Train is moving at " + CURRENT_SPEED + "mph, and received a command to accelerate to " + TARGET_SPEED + "mph.");
		System.out.println();
		
		// simulate 30 seconds
		for (int t = 0; t < 30; t++) {
			double powerCommand = trainController.calculatePower(TIME_INTERVAL);
			double currentSpeed = trainModel.getCurrentSpeed();
			
			// update the speed of the train with some phony physics
			if (powerCommand > 0) {
				trainModel.setCurrentSpeed(currentSpeed + 1);
			} else {
				trainModel.setCurrentSpeed(currentSpeed - 1);
			}
			
			System.out.println("t=" + t + ": currentSpeed = " + currentSpeed + "mph, powerCommand = " + powerCommand);
		}
	}

}
