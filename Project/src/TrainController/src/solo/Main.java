import java.util.Timer;
import java.util.TimerTask;

public final class Main extends TimerTask {

	static long TIME_INTERVAL_MILLIS = 100;
	static int speedup = 1;

	static TrainModel trainModel;
	static TrainController trainController;

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		trainModel = new TrainModel("");

		
		// fake some speed+auth commands
		double TARGET_SPEED = 7.0;
		double AUTH = 3000.0;
		trainModel.trainController.hackVelocityFromCTC(TARGET_SPEED);
		trainModel.trainController.setVelocityFromTrainOperator(TARGET_SPEED);
		trainModel.trainController.hackAuthorityFromCTC(AUTH);
		
		// set up the ticking
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Main(), 0, TIME_INTERVAL_MILLIS);
	}

	@Override
	public void run() {
		trainModel.tick(TIME_INTERVAL_MILLIS * speedup);
	}

}
