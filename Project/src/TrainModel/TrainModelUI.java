import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TrainModelUI{
	private TrainModel model;
	private TrainController controller;
	
	private String train;
	private String vel;
	private String acc;
	private String pow;
	private String temp;
	private String pass;
	private String mass;
	private String lights;
	private String doors;
	private String station;
	
	private final int HEIGHT = 600;
	private final int WIDTH = 800;
	
	private JFrame frame = new JFrame();
	private JPanel[] panel = new JPanel[16];
	
	private JLabel trainID;
	private JLabel velocity;
	private JLabel acceleration;
	private JLabel power;
	private JLabel temperature;
	private JLabel passengers;
	private JLabel totalMass;
	private JLabel lightDisplay;
	private JLabel doorDisplay;
	private JLabel stationID;
	
	private JButton lightsOn = new JButton("ON");
	private JButton lightsOff = new JButton("OFF");
	private JButton eBrakeOn = new JButton("Pull");
	private JButton eBrakeOff = new JButton("Reset");
	private JButton engineForce = new JButton("Force");
	private JButton engineFix = new JButton("Fix");
	private JButton brakeForce = new JButton("Force");
	private JButton brakeFix = new JButton("Fix");
	private JButton signalForce = new JButton("Force");
	private JButton signalFix = new JButton("Fix");
	private JButton tempInc = new JButton(" + ");
	private JButton tempDec = new JButton(" - ");
	
	public TrainModelUI(TrainModel model, TrainController controller){
		this.model = model;
		this.controller = controller;
		
		frame.setSize(WIDTH, HEIGHT);
		frame.setLayout(new GridLayout(8,2));
		
		JLabel wordTrain = new JLabel("TRAIN ID: ");
		JLabel wordVel = new JLabel("Velocity: ");
		JLabel wordAcc = new JLabel("Acceleration: ");
		JLabel wordPower = new JLabel("Power Input: ");
		JLabel wordTemp = new JLabel("Temperature: ");
		JLabel wordPass = new JLabel("Passenger Count: ");
		JLabel wordMass = new JLabel("Total Mass: ");
		JLabel wordLights = new JLabel("Lights: ");
		JLabel wordDoors = new JLabel("Doors: ");
		JLabel wordStation = new JLabel("Next Station: ");
		JLabel wordLightsInput = new JLabel("Turn Lights: ");
		JLabel wordEbrake = new JLabel("Pull eBrake: ");
		JLabel wordEngine = new JLabel("Force Engine Failure: ");
		JLabel wordBrake = new JLabel("Force Brake Failure: ");
		JLabel wordSignal = new JLabel("Force Signal Failure: ");
		JLabel wordTempInput = new JLabel("Adjust Temperature: ");
		
		JLabel unitVel = new JLabel("mph");
		JLabel unitAcc = new JLabel("ft/s^2");
		JLabel unitPower = new JLabel("kW");
		JLabel unitTemp = new JLabel("degrees F");
		JLabel unitMass = new JLabel("lbs");
		
		train = model.getTrainID();
		vel = new Double(model.getVelocityUS()).toString();
		acc = new Double(model.getAccelerationUS()).toString();
		pow = new Double(model.getPower()).toString();
		pass = new Integer(model.getPassengerCount()).toString();
		mass = new Double(model.getWeight()).toString();
		if(model.getLightsStatus()){
			lights = "On";
		}else{
			lights = "Off";
		}
		if(model.doorsAreOpen()){
			doors = "Open";
		}else{
			doors = "Closed";
		}
		station = model.getStationID();
		
		trainID = new JLabel(train);
		velocity = new JLabel(vel);
		acceleration = new JLabel(acc);
		power = new JLabel(pow);
		temperature = new JLabel(temp);
		passengers = new JLabel(pass);
		totalMass = new JLabel(mass);
		lightDisplay = new JLabel(lights);
		doorDisplay = new JLabel(doors);
		stationID = new JLabel(station);
		
		for(int i=0; i<16; i++){
			panel[i] = new JPanel();
			frame.add(panel[i]);
		}
		
		panel[0].add(wordTrain);
		panel[0].add(trainID);
		panel[1].add(wordStation);
		panel[1].add(stationID);
		panel[2].add(wordVel);
		panel[2].add(velocity);
		panel[2].add(unitVel);
		panel[7].add(wordEngine);
		panel[7].add(engineForce);
		panel[7].add(engineFix);
		panel[4].add(wordAcc);
		panel[4].add(acceleration);
		panel[4].add(unitAcc);
		panel[9].add(wordBrake);
		panel[9].add(brakeForce);
		panel[9].add(brakeFix);
		panel[6].add(wordPower);
		panel[6].add(power);
		panel[6].add(unitPower);
		panel[11].add(wordSignal);
		panel[11].add(signalForce);
		panel[11].add(signalFix);
		panel[8].add(wordMass);
		panel[8].add(totalMass);
		panel[8].add(unitMass);
		panel[5].add(wordEbrake);
		panel[5].add(eBrakeOn);
		panel[5].add(eBrakeOff);
		panel[10].add(wordPass);
		panel[10].add(passengers);
		panel[13].add(wordDoors);
		panel[13].add(doorDisplay);
		panel[12].add(wordTemp);
		panel[12].add(temperature);
		panel[12].add(unitTemp);
		panel[3].add(wordTempInput);
		panel[3].add(tempInc);
		panel[3].add(tempDec);
		panel[14].add(wordLights);
		panel[14].add(lightDisplay);
		panel[15].add(wordLightsInput);
		panel[15].add(lightsOn);
		panel[15].add(lightsOff);
		
		ActionListener buttonListener = new ButtonListener();
		engineForce.addActionListener(buttonListener);
		engineFix.addActionListener(buttonListener);
		signalForce.addActionListener(buttonListener);
		signalFix.addActionListener(buttonListener);
		brakeForce.addActionListener(buttonListener);
		brakeFix.addActionListener(buttonListener);
		eBrakeOn.addActionListener(buttonListener);
		eBrakeOff.addActionListener(buttonListener);
		lightsOn.addActionListener(buttonListener);
		lightsOff.addActionListener(buttonListener);
		
		frame.setVisible(true);
	}
	
	public void tick(){
		train = model.getTrainID();
		vel = new Double(model.getVelocityUS()).toString();
		acc = new Double(model.getAccelerationUS()).toString();
		pow = new Double(model.getPower()).toString();
		pass = new Integer(model.getPassengerCount()).toString();
		mass = new Double(model.getWeight()).toString();
		if(model.getLightsStatus()){
			lights = "On";
		}else{
			lights = "Off";
		}
		if(model.doorsAreOpen()){
			doors = "Open";
		}else{
			doors = "Closed";
		}
		station = model.getStationID();
		
		trainID.setText(train);
		velocity.setText(vel);
		acceleration.setText(acc);
		power.setText(pow);
		passengers.setText(pass);
		totalMass.setText(mass);
		lightDisplay.setText(lights);
		doorDisplay.setText(doors);
		stationID.setText(station);
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JButton source = (JButton) e.getSource();
			
			if(source.equals(engineForce)){
				model.forceFailure("e");
			}else if(source.equals(signalForce)){
				model.forceFailure("s");
			}else if(source.equals(brakeForce)){
				model.forceFailure("b");
			}else if(source.equals(brakeFix)){
				model.fixFailure("b");
			}else if(source.equals(signalFix)){
				model.fixFailure("s");
			}else if(source.equals(engineFix)){
				model.fixFailure("e");
			}else if(source.equals(eBrakeOn)){
				model.activateEmergencyBrake();
			}else if(source.equals(eBrakeOff)){
				model.deactivateEmergencyBrake();
			}else if(source.equals(lightsOn)){
				model.turnOnLights();
			}else if(source.equals(lightsOff)){
				model.turnOffLights();
			}
		}
	}
}