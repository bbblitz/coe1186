//package system;

public class MainPrototype {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("*********************************************");
		System.out.println("         TRAIN CONTROLLER PROTOTYPE          ");
		System.out.println("*********************************************");
		
		TrainModel trainModel = new TrainModel();	// train mass
		TrainController trainController = new TrainController(trainModel);

		long TIME_INTERVAL_MILLIS = 100;
		
		// fake the train already moving
		double CURRENT_SPEED = 1.0;
		trainModel.setCurrentVelocitySI(CURRENT_SPEED);
		
		// fake some speed+auth commands
		double TARGET_SPEED = 10.0;
		double AUTH = 300000.0;
		trainController.hackVelocityFromCTC(TARGET_SPEED);
		trainController.hackVelocityFromTrainOperator(TARGET_SPEED);
		trainController.hackAuthorityFromCTC(AUTH);
		
		
		System.out.println("Train is moving at " + CURRENT_SPEED + "m/s, and received a command to accelerate to " + TARGET_SPEED + "m/s with authority of " + AUTH + "m.");
		System.out.println();
		
		// simulate 100 iterations
		for (int t = 0; t < 100; t++) {
			trainModel.tick(TIME_INTERVAL_MILLIS);
			trainController.tick(TIME_INTERVAL_MILLIS);
						
			//System.out.println("t=" + t + ": velocity = " + (Math.round(trainModel.getCurrentVelocitySI() * 10000.0) / 10000.0) + "m/s, powerCommand = " + (Math.round(trainController._getLastPowerCommand() * 10000.0) / 10000.0) + ", authority = " + (Math.round(trainController.getAuthorityFromCTC() * 100.0) / 100.0) + "m.");
			//Thread.sleep(TIME_INTERVAL_MILLIS);
		}
	}

}
