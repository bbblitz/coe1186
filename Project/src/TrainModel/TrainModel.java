import java.util.BitSet;
import java.util.Random;

public class TrainModel{
	private double mass;
	private double weight;
	private final double trainMass = 37103.86;
	private final double avgPassengerWeight = 80.7;
	private final double eBrakeRate = -2.73;
	private final double sBrakeRate = -1.2;
	
	private final int maxPassengers = 222;
	
	private double power;
	
	private boolean eBrake;
	private boolean sBrake;
	private boolean engineFailure;
	private boolean brakeFailure;
	private boolean signalFailure;
	private boolean lights;
	private boolean doors;
	
	private int ID;
	private String nextStation;
	
	private double acceleration;
	private double accelerationSI;
	private double oldVelocity;
	private double velocity;
	private double velocitySI;
	private double position;
	private double positionSI;
	private double distanceOnLastTick;
	private double blockGrade;
	
	private int passengerCount;
	private int passAndCrew;
	private int temperature;
	
	private TrainController trainController;
	private TrackModel trackModel;
	private TrainModelUI uI;
	
	private BitSet railSignal;
	private BitSet beaconSignal;
	
	private Random random;
	
	public TrainModel(int ID, TrackModel trackModel){
		this.mass = this.trainMass;
		this.weight = 81628.492;
		
		this.power = 0;
		
		this.eBrake = false;
		this.sBrake = false;
		this.engineFailure = false;
		this.brakeFailure = false;
		this.signalFailure = false;
		
		this.ID = ID;
		
		this.acceleration = 0;
		this.accelerationSI = 0;
		this.oldVelocity = 0;
		this.velocity = 0;
		this.velocitySI = 0;
		this.positionSI = 0;
		this.blockGrade = 0;
		
		this.passengerCount = 0;
		this.passAndCrew = 0;
		this.temperature = 60;
		
		this.trainController = new TrainController(this);
		this.trackModel = trackModel;
		this.uI = new TrainModelUI(this, this.trainController);
		
		railSignal = new BitSet(32);
		beaconSignal = new BitSet(32);
		
		this.random = new Random();
	}
	
	public void tick(double deltaT){
		this.trainController.tick(deltaT);
		//this.trainController.receiveSignalFromRail(this.railSignal);
		
		if(this.velocitySI == 0 && this.power > 0){
			this.oldVelocity = 1;
		} else{
			this.oldVelocity = this.velocitySI;
		}
		double distanceOnTick = calculateSpeed(deltaT);
		
		trackModel.receiveDistance(distanceOnTick, this.ID);
		
		this.distanceOnLastTick = distanceOnTick;
		convertMass();
		convertVelocity();
		convertAcceleration();
		uI.tick();
	}
	
	private double calculateSpeed(double deltaT){
		
		double appForce = 0;
		if(sBrake){
			appForce = this.sBrakeRate * this.mass;
		} else if(eBrake){
			appForce = this.eBrakeRate * this.mass;
		} else{
			if (this.power == 0 && this.oldVelocity == 0) {
				// avoid division by 0
				appForce = 0;
			} else {
				appForce = this.power / this.oldVelocity;
			}
		}
		double gravForce = gravitationalForce();
		double totalForce = gravForce + appForce;
		this.accelerationSI = totalForce / this.mass;
		
		this.velocitySI = this.oldVelocity + this.accelerationSI * deltaT/1000.0;
		if(this.velocitySI < 0){
			this.velocitySI = 0;
		}
		if(this.velocitySI > 70){
			this.velocitySI = 70;
		}
		
		double distanceOnTick = ((this.oldVelocity + this.velocitySI)/2) * deltaT/1000.0;
		return distanceOnTick;
	}
	
	private double gravitationalForce(){
		double grade = this.blockGrade;
		
		double theta = Math.atan(grade/100.0);
		double force = 9.8 * this.mass * Math.sin(theta) * -1.0;
		
		return force;
	}
	
	public void notifyAtStation(int station){
		int passengersOff = this.random.nextInt(this.passengerCount);
		this.passengerCount -= passengersOff;
		int passengersOn = trackModel.getPassengers();
		if(this.passengerCount + passengersOn > this.maxPassengers){
			this.passengerCount = this.maxPassengers;
		} else{
			this.passengerCount += passengersOn;
		}
		this.passAndCrew = this.passengerCount + 1;
		
		this.mass = this.trainMass + (this.passAndCrew * this.avgPassengerWeight);
	}
	
	private void convertVelocity(){
		this.velocity = this.velocitySI * 2.236942;
	}
	
	private void convertAcceleration(){
		this.acceleration = this.accelerationSI * 3.281;
	}
	
	private void convertMass(){
		this.weight = this.mass * 2.2;
	}
	
	public double getMass(){
		return mass;
	}
	
	public void receivePowerCommand(double power){
		if(power > 120000){
			power = 120000.0;
		}else{
			this.power = power;
		}
	}
	
	public double getCurrentVelocitySI(){
		return velocitySI;
	}
	
	public void activateEmergencyBrake(){
		this.eBrake = true;
	}
	
	public void deactivateEmergencyBrake(){
		this.eBrake = false;
	}
	
	public void activateServiceBrake(){
		this.sBrake = true;
	}
	
	public void deactivateServiceBrake(){
		this.sBrake = false;
	}
	
	public boolean getEngineFailure(){
		return engineFailure;
	}
	
	public boolean getBrakeFailure(){
		return brakeFailure;
	}
	
	public boolean getSignalPickupFailure(){
		return signalFailure;
	}
	
	public void receiveBeacon(BitSet beacon){
		this.trainController.receiveBeacon(beacon);
	}
	
	public void receiveSignalFromRail(BitSet railSignal){
		this.railSignal = railSignal;
	}
	
	public void receiveAuthority(int authority){
		this.trainController.receiveAuthority(authority);
	}
	
	public void receiveSpeed(int speed){
		this.trainController.receiveSpeed(speed);
	}
	
	public int getTrainID(){
		return this.ID;
	}
	
	public double getVelocityUS(){
		return this.velocity;
	}
	
	public double getAccelerationUS(){
		return this.acceleration;
	}
	
	public double getPower(){
		return this.power/1000;
	}
	
	public int getPassengerCount(){
		return this.passAndCrew;
	}
	
	public double getWeight(){
		return this.weight;
	}
	
	public String getStationID(){
		return this.nextStation;
	}
	
	public void displayNextStation(String StationID){
		this.nextStation = StationID;
	}
	
	public boolean getLightsStatus(){
		return lights;
	}
	
	public void turnOnLights(){
		this.lights = true;
	}
	
	public void turnOffLights(){
		this.lights = false;
	}
	
	public void openDoors(){
		this.doors = true;
	}
	
	public void closeDoors(){
		this.doors = false;
	}
	
	public boolean doorsAreOpen(){
		return doors;
	}
	
	public void setTemperature(int temp){
		this.temperature = temp;
	}
	
	public int getTemperature(){
		return this.temperature;
	}
	
	public void forceFailure(String source){
		if(source.equals("e")){
			this.engineFailure = true;
		}else if(source.equals("s")){
			this.signalFailure = true;
		}else if(source.equals("b")){
			this.brakeFailure = true;
		}
	}
	
	public void fixFailure(String source){
		if(source.equals("e")){
			this.engineFailure = false;
		}else if(source.equals("s")){
			this.signalFailure = false;
		}else if(source.equals("b")){
			this.brakeFailure = false;
		}
	}
	
	public boolean isEmergencyBreakActivated(){
		return eBrake;
	}
	
	public double getDistance(){
		return distanceOnLastTick;
	}
	
	public void incTemp(){
		this.temperature++;
	}
	
	public void decTemp(){
		this.temperature--;
	}
	
	public void receiveBlockGrade(double grade){
		this.blockGrade = grade;
	}
}