import java.util.Timer;
import java.util.TimerTask;

public final class Main extends TimerTask {

	static long TIME_INTERVAL_MILLIS = 100;

	static TrainModel trainModel;
	static TrainController trainController;

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("*********************************************");
		System.out.println("            TRAIN CONTROLLER TEST            ");
		System.out.println("*********************************************");
		
		trainModel = new TrainModel();	// train mass
		trainController = new TrainController(trainModel);
		
		// fake the train already moving
		double CURRENT_SPEED = 1.0;
		trainModel.setCurrentVelocitySI(CURRENT_SPEED);
		
		// fake some speed+auth commands
		double TARGET_SPEED = 6.0;
		double AUTH = 300.0;
		trainController.hackVelocityFromCTC(TARGET_SPEED);
		trainController.hackVelocityFromTrainOperator(TARGET_SPEED);
		trainController.hackAuthorityFromCTC(AUTH);
		
		
		System.out.println("Train is moving at " + CURRENT_SPEED + "m/s, and received a command to accelerate to " + TARGET_SPEED + "m/s with authority of " + AUTH + "m.");
		System.out.println();
		
		// set up the ticking
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Main(), 0, TIME_INTERVAL_MILLIS);
	}

	@Override
	public void run() {
		trainModel.tick(TIME_INTERVAL_MILLIS);
		trainController.tick(TIME_INTERVAL_MILLIS);
	}

}
