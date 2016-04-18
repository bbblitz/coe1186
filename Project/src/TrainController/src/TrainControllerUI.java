import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nickfaughey on 4/10/16.
 */
public class TrainControllerUI {
    private JPanel mainPanel;
    private JButton hackAuthorityFromCTCButton;
    private JSlider throttleSlider;
    private JButton activateServiceBrakeButton;
    private JButton deactivateServiceBrakeButton;
    private JButton hackSpeedFromCTCButton;
    private JCheckBox lightsOnCheckBox;
    private JCheckBox doorsOpenCheckBox;
    private JList messageList;
    private DefaultListModel<String> messageModel = new DefaultListModel<>();
    private JButton pullEmergencyBrakeButton;
    private JButton resetEmergencyBrakeButton;
    private JLabel authorityFromCTCLabel;
    private JLabel speedFromCTCLabel;
    private JLabel targetSpeedLabel;
    private JLabel currentSpeedLabel;
    private JLabel calculatedPowerLabel;
    private JLabel temperatureLabel;
    private JLabel nextStationLabel;
    private JLabel engineFailureLabel;
    private JLabel brakeFailureLabel;
    private JLabel signalPickupFailureLabel;

    private TrainController trainController;

    public TrainControllerUI() {

    }

    public void initialize(TrainController trainController) {
        this.trainController = trainController;

        JFrame frame = new JFrame("Train Controller");

        prepareGUI();

        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        this.hackAuthorityFromCTCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // show hack dialog
                HackAuthorityFromCTCDialog hackDialog = new HackAuthorityFromCTCDialog();
                int newAuthority = hackDialog.showDialog();
                if (newAuthority > -1) {
                    double newAuthorityDouble = (double)newAuthority;
                    newAuthorityDouble = feetToMeters(newAuthorityDouble);
                    TrainControllerUI.this.trainController.hackAuthorityFromCTC(newAuthorityDouble);
                }
            }
        });

        this.hackSpeedFromCTCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // show hack dialog
                HackSpeedFromCTCDialog hackDialog = new HackSpeedFromCTCDialog();
                int newSpeed = hackDialog.showDialog();
                if (newSpeed > -1) {
                    double newSpeedDouble = (double)newSpeed;
                    newSpeedDouble = mphToMps(newSpeedDouble);
                    TrainControllerUI.this.trainController.hackVelocityFromCTC(newSpeedDouble);
                }
            }
        });

        this.pullEmergencyBrakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrainControllerUI.this.trainController.activateEmergencyBrake();
                log("Emergency brake pulled!");
            }
        });

        this.resetEmergencyBrakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrainControllerUI.this.trainController.deactivateEmergencyBrake();
                log("Emergency brake reset.");
            }
        });

        this.activateServiceBrakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrainControllerUI.this.trainController.activateServiceBrake();
            }
        });

        this.deactivateServiceBrakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TrainControllerUI.this.trainController.deactivateServiceBrake();
            }
        });

        throttleSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double velocityFromTrainOperator = mphToMps(throttleSlider.getValue());
                TrainControllerUI.this.trainController.setVelocityFromTrainOperator(velocityFromTrainOperator);
            }
        });

        doorsOpenCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean open = doorsOpenCheckBox.isSelected();
                TrainControllerUI.this.trainController.setDoorsOpen(open);
            }
        });

        lightsOnCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean on = lightsOnCheckBox.isSelected();
                TrainControllerUI.this.trainController.setLightsOn(on);
            }
        });

    }

    public void refresh() {
        double authorityFromCTC = trainController.getAuthorityFromCTC();
        authorityFromCTC = round2(metersToFeet(authorityFromCTC));
        authorityFromCTCLabel.setText(String.valueOf(authorityFromCTC));

        double speedFromCTC = trainController.getVelocityFromCTC();
        speedFromCTC = round2(mpsToMph(speedFromCTC));
        this.speedFromCTCLabel.setText(String.valueOf(speedFromCTC));

        double speedFromDriver = trainController.getVelocityFromTrainOperator();
        speedFromDriver = Math.round(mpsToMph(speedFromDriver));
        this.throttleSlider.setValue((int) speedFromDriver);

        double targetSpeed = trainController.getTargetVelocity();
        targetSpeed = round2(mpsToMph(targetSpeed));
        this.targetSpeedLabel.setText(String.valueOf(targetSpeed));

        double currentSpeed = trainController.getCurrentVelocitySI();
        currentSpeed = round2(mpsToMph(currentSpeed));
        this.currentSpeedLabel.setText(String.valueOf(currentSpeed));

        double calculatedPower = trainController.getPowerCommand();
        calculatedPower = Math.round(calculatedPower / 1000.0);
        this.calculatedPowerLabel.setText(String.valueOf(calculatedPower));

        boolean doorsOpen = trainController.doorsAreOpen();
        this.doorsOpenCheckBox.setSelected(doorsOpen);

        boolean lightsOn = trainController.lightsAreOn();
        this.lightsOnCheckBox.setSelected(lightsOn);

        int temperature = trainController.getTemperature();
        this.temperatureLabel.setText(String.valueOf(temperature));

        String nextStation = trainController.getNextStation();
        this.nextStationLabel.setText(nextStation);

        boolean engineFailure = trainController.getEngineFailure();
        String engineFailureText = engineFailure ? "FAIL" : "OK";
        engineFailureLabel.setText(engineFailureText);

        boolean brakeFailure = trainController.getBrakeFailure();
        String brakeFailureText = brakeFailure ? "FAIL" : "OK";
        brakeFailureLabel.setText(brakeFailureText);

        boolean signalPickupFailure = trainController.getSignalPickupFailure();
        String signalPickupFailureText = signalPickupFailure ? "FAIL" : "OK";
        signalPickupFailureLabel.setText(signalPickupFailureText);
    }

    public void log(String message) {
        this.messageModel.addElement(message);
        int size = this.messageModel.size();
        this.messageList.ensureIndexIsVisible(size - 1);
    }

    public double metersToFeet(double meters) {
        return meters * 3.28084;
    }

    public double feetToMeters(double feet) {
        return feet / 3.28084;
    }

    public double mpsToMph(double mps) {
        return mps * 2.2369;
    }

    public double mphToMps(double mph) {
        return mph / 2.2369;
    }

    private double round2(double highPrecision) {
        return Math.round(highPrecision * 100.0) / 100.0;
    }

    private void prepareGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(panel1, gbc);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Signal pickups from CTC"));
        final JLabel label1 = new JLabel();
        label1.setText("Speed");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label1, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);
        speedFromCTCLabel = new JLabel();
        speedFromCTCLabel.setText("15");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(speedFromCTCLabel, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("mph");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label2, gbc);
        hackSpeedFromCTCButton = new JButton();
        hackSpeedFromCTCButton.setText("Hack");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(hackSpeedFromCTCButton, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Authority");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label3, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer3, gbc);
        authorityFromCTCLabel = new JLabel();
        authorityFromCTCLabel.setText("1500");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(authorityFromCTCLabel, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("ft");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label4, gbc);
        hackAuthorityFromCTCButton = new JButton();
        hackAuthorityFromCTCButton.setText("Hack");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(hackAuthorityFromCTCButton, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer4, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(panel2, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Target speed");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(label5, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel2.add(spacer6, gbc);
        targetSpeedLabel = new JLabel();
        targetSpeedLabel.setText("15");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(targetSpeedLabel, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("mph");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(label6, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("Current speed");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(label7, gbc);
        currentSpeedLabel = new JLabel();
        currentSpeedLabel.setText("12");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(currentSpeedLabel, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("mph");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(label8, gbc);
        final JLabel label9 = new JLabel();
        label9.setText("Calculated power");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(label9, gbc);
        calculatedPowerLabel = new JLabel();
        calculatedPowerLabel.setText("75");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(calculatedPowerLabel, gbc);
        final JLabel label10 = new JLabel();
        label10.setText("kW");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(label10, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel2.add(spacer7, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(panel3, gbc);
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Throttle"));
        throttleSlider = new JSlider();
        throttleSlider.setMajorTickSpacing(10);
        throttleSlider.setMaximum(43);
        throttleSlider.setMinimumSize(new Dimension(200, 42));
        throttleSlider.setMinorTickSpacing(1);
        throttleSlider.setPaintLabels(true);
        throttleSlider.setPaintTicks(true);
        throttleSlider.setValue(15);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(throttleSlider, gbc);

        activateServiceBrakeButton = new JButton();
        activateServiceBrakeButton.setText("Activate Service Brake");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(activateServiceBrakeButton, gbc);

        deactivateServiceBrakeButton = new JButton();
        deactivateServiceBrakeButton.setText("Deactivate Service Brake");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(deactivateServiceBrakeButton, gbc);

        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(spacer8, gbc);
        final JLabel label11 = new JLabel();
        label11.setText("mph");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label11, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(spacer9, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(panel4, gbc);
        doorsOpenCheckBox = new JCheckBox();
        doorsOpenCheckBox.setText("Doors open");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(doorsOpenCheckBox, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(spacer10, gbc);
        lightsOnCheckBox = new JCheckBox();
        lightsOnCheckBox.setText("Lights on");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(lightsOnCheckBox, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(panel5, gbc);
        final JLabel label12 = new JLabel();
        label12.setText("Next station:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel5.add(label12, gbc);
        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(spacer11, gbc);
        nextStationLabel = new JLabel();
        nextStationLabel.setText("Steel Plaza");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel5.add(nextStationLabel, gbc);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(panel6, gbc);
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null));
        final JLabel label13 = new JLabel();
        label13.setText("Temperature");
        panel6.add(label13);
        temperatureLabel = new JLabel();
        temperatureLabel.setText("72");
        panel6.add(temperatureLabel);
        final JLabel label14 = new JLabel();
        label14.setText("°F");
        panel6.add(label14);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(false);
        scrollPane1.setMinimumSize(new Dimension(17, 180));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane1, gbc);
        scrollPane1.setBorder(BorderFactory.createTitledBorder("Message log"));
        messageList = new JList();
        messageList.setModel(messageModel);
        scrollPane1.setViewportView(messageList);
        final JPanel spacer12 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer12, gbc);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(panel7, gbc);
        panel7.setBorder(BorderFactory.createTitledBorder("Failures"));
        final JLabel label15 = new JLabel();
        label15.setText("Engine");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel7.add(label15, gbc);
        engineFailureLabel = new JLabel();
        engineFailureLabel.setText("OK");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel7.add(engineFailureLabel, gbc);
        final JLabel label16 = new JLabel();
        label16.setText("Brake");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel7.add(label16, gbc);
        brakeFailureLabel = new JLabel();
        brakeFailureLabel.setText("OK");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel7.add(brakeFailureLabel, gbc);
        final JLabel label17 = new JLabel();
        label17.setText("Signal pickup");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel7.add(label17, gbc);
        signalPickupFailureLabel = new JLabel();
        signalPickupFailureLabel.setText("OK");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel7.add(signalPickupFailureLabel, gbc);
        pullEmergencyBrakeButton = new JButton();
        pullEmergencyBrakeButton.setText("Pull Emergency Brake");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(pullEmergencyBrakeButton, gbc);

        resetEmergencyBrakeButton = new JButton();
        resetEmergencyBrakeButton.setText("Reset Emergency Brake");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(resetEmergencyBrakeButton, gbc);
    }

}
