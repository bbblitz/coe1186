package system;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TrainModel trainModel = new TrainModel();
		TrainController trainController = new TrainController(1, trainModel);
		
		double TEST_SPEED = 30.0;
		
		trainModel.setCurrentSpeed(TEST_SPEED);
		double power = trainController.calculatePower();
		System.out.println("TEST_SPEED=" + TEST_SPEED + ", power=" + power);
	}

}
