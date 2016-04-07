import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.BitSet;

public class TrainControllerUI implements ActionListener {

	public TrainController trainController;
	public JFrame mainFrame;
	public JPanel contentPane;
	public JLabel targetSpeedLabel;
	public JLabel currentSpeedLabel;
	public JLabel currentPowerLabel;
	public JLabel currentAuthorityLabel;
	public DefaultListModel messages = new DefaultListModel();
	public JList messagesList;
	
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
      	this.contentPane.setLayout(new GridLayout(0, 1));

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

      	JScrollPane scrollPane = new JScrollPane();
      	this.messagesList = new JList(this.messages);
      	scrollPane.setViewportView(messagesList);
      	this.contentPane.add(scrollPane);

      	JButton simulateBeaconButton = new JButton("Simulate Beacon");
      	simulateBeaconButton.addActionListener(this);
      	simulateBeaconButton.setActionCommand("beacon");
      	this.contentPane.add(simulateBeaconButton);

		this.mainFrame.setContentPane(this.contentPane);
		this.mainFrame.setSize(400, 400);
		this.mainFrame.setLocationByPlatform(true);
		this.mainFrame.setVisible(true);
	}

	public void log(String message) {
		this.messages.addElement(message);
		int lastIndex = this.messages.getSize() - 1;
		this.messagesList.ensureIndexIsVisible(lastIndex);
	}

	public void actionPerformed(ActionEvent e) {
		if ("beacon".equals(e.getActionCommand())) {
			// simulate beacon drop
			BitSet beacon = new BitSet(32);
			beacon.clear();
			beacon.set(0);
			beacon.set(2);

			beacon.set(12);

			this.trainController.receiveBeacon(beacon);
		}
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
		double targetSpeed = trainController.mpsToMph(trainController.getTargetVelocity());
		targetSpeed = Math.round(targetSpeed * 100.0) / 100.0;
		String targetSpeedText = "Target speed: " + String.valueOf(targetSpeed) + " mph";
		trainControllerUI.targetSpeedLabel.setText(targetSpeedText);

		double currentSpeed = trainController.mpsToMph(trainController.getCurrentVelocitySI());
		currentSpeed = Math.round(currentSpeed * 100.0) / 100.0;
		String currentSpeedText = "Current speed: " + String.valueOf(currentSpeed) + " mph";
		trainControllerUI.currentSpeedLabel.setText(currentSpeedText);

		double currentPower = trainController.getPowerCommand() / 1000.0;
		currentPower = Math.round(currentPower * 100.0) / 100.0;
		String currentPowerText = "Calculated power command: " + String.valueOf(currentPower) + " kW";
		trainControllerUI.currentPowerLabel.setText(currentPowerText);

		double currentAuthority = trainController.metersToFeet(trainController.getAuthorityFromCTC());
		currentAuthority = Math.round(currentAuthority * 100.0) / 100.0;
		String currentAuthorityText = "Current authority: " + String.valueOf(currentAuthority) + " ft";
		trainControllerUI.currentAuthorityLabel.setText(currentAuthorityText);
	}
}