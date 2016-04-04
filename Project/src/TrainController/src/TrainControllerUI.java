import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class TrainControllerUI {

	public TrainController trainController;
	public JFrame mainFrame;
	public JPanel contentPane;
	public JLabel targetSpeedLabel;
	public JLabel currentSpeedLabel;
	public JLabel currentPowerLabel;
	public JLabel currentAuthorityLabel;
	
	public TrainControllerUI(TrainController trainController) {
		this.trainController = trainController;
		this.prepareGUI();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new RefreshTask(this), 0, 100);
	}

	private void prepareGUI() {
		this.mainFrame = new JFrame("Train Controller");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


      	this.contentPane = new JPanel();
      	this.contentPane.setOpaque(true);
      	this.contentPane.setBackground(Color.WHITE);
      	this.contentPane.setLayout(null);

      	this.targetSpeedLabel = new JLabel("Target speed: ");
      	this.targetSpeedLabel.setSize(300, 30);
      	this.targetSpeedLabel.setLocation(5, 5);
      	this.contentPane.add(this.targetSpeedLabel);

      	this.currentSpeedLabel = new JLabel("Current speed: ");
      	this.currentSpeedLabel.setSize(300, 30);
      	this.currentSpeedLabel.setLocation(5, 25);
      	this.contentPane.add(this.currentSpeedLabel);

      	this.currentPowerLabel = new JLabel("Calculated power command: ");
      	this.currentPowerLabel.setSize(300, 30);
      	this.currentPowerLabel.setLocation(5, 45);
      	this.contentPane.add(this.currentPowerLabel);

      	this.currentAuthorityLabel = new JLabel("Current authority: ");
      	this.currentAuthorityLabel.setSize(300, 30);
      	this.currentAuthorityLabel.setLocation(5, 65);
      	this.contentPane.add(this.currentAuthorityLabel);



		this.mainFrame.setContentPane(this.contentPane);
		this.mainFrame.setSize(400, 400);
		this.mainFrame.setLocationByPlatform(true);
		this.mainFrame.setVisible(true);
	}

	public void log(String message) {
		
	}

}

class RefreshTask extends TimerTask {

	private TrainControllerUI trainControllerUI;
	private TrainController trainController;

	public RefreshTask(TrainControllerUI trainControllerUI) {
		this.trainControllerUI = trainControllerUI;
		this.trainController = trainControllerUI.trainController;
	}

	@Override
	public void run() {
		double targetSpeed = Math.round(trainController.getTargetVelocity() * 100.0) / 100.0;
		String targetSpeedText = "Target speed: " + String.valueOf(targetSpeed);
		trainControllerUI.targetSpeedLabel.setText(targetSpeedText);

		double currentSpeed = Math.round(trainController.getCurrentVelocitySI() * 100.0) / 100.0;
		String currentSpeedText = "Current speed: " + String.valueOf(currentSpeed);
		trainControllerUI.currentSpeedLabel.setText(currentSpeedText);

		double currentPower = Math.round(trainController.getPowerCommand() * 100.0) / 100.0;
		String currentPowerText = "Calculated power command: " + String.valueOf(currentPower);
		trainControllerUI.currentPowerLabel.setText(currentPowerText);

		double currentAuthority = Math.round(trainController.getAuthorityFromCTC() * 100.0) / 100.0;
		String currentAuthorityText = "Current authority: " + String.valueOf(currentAuthority);
		trainControllerUI.currentAuthorityLabel.setText(currentAuthorityText);
	}
}