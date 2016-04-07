import java.util.BitSet;

public class TrainModel{
	private double mass;
	private final double eBrakeRate = -2.73;
	private final double sBrakeRate = -1.2;
	
	private double power;
	
	private boolean eBrake;
	private boolean sBrake;
	private boolean engineFailure;
	private boolean brakeFailure;
	private boolean signalFailure;
	
	private String ID;
	private double acceleration;
	private double accelerationSI;
	private double oldVelocity;
	private double velocity;
	private double velocitySI;
	private double position;
	private double positionSI;
	private int authority;
	
	//private Block currentBlock;   (add back when block class is finished)
	
	private TrainController trainController;
	private TrackModel trackModel;
	
	private BitSet railSignal;
	private BitSet beaconSignal;
	
	public TrainModel(String ID, TrackModel trackModel){
		this.mass = 37103.86;
		
		this.power = 0;
		
		this.eBrake = false;
		this.sBrake = false;
		this.engineFailure = false;
		this.brakeFailure = false;
		this.signalFailure = false;
		
		this.ID = ID;
		//this.currentBlock = trackModel.getBlock();
		this.acceleration = 0;
		this.accelerationSI = 0;
		this.oldVelocity = 0;
		this.velocitySI = 0;
		this.positionSI = 0;
		this.authority = 0;
		
		this.trainController = new TrainController(this);
		this.trackModel = trackModel;
		
		railSignal = new BitSet(32);
		beaconSignal = new BitSet(32);
	}
	
	public void tick(double deltaT){
		this.trainController.tick(deltaT);
		//this.trainController.receiveSignalFromRail(this.railSignal);
		this.trainController.recieveAuthority(this.authority);
		
		if(this.velocitySI == 0 && this.power > 0){
			this.oldVelocity = 1;
		} else{
			this.oldVelocity = this.velocitySI;
		}
		double distanceOnTick = calculateSpeed(deltaT);
		
		this.trackModel.recieveDistance(distanceOnTick);
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
		System.out.println(String.valueOf(appForce));
		this.accelerationSI = totalForce / this.mass;
		
		this.velocitySI = this.oldVelocity + this.accelerationSI * deltaT/1000.0;
		if(this.velocitySI < 0){
			this.velocitySI = 0;
		}
		
		double distanceOnTick = ((this.oldVelocity + this.velocitySI)/2) * deltaT/1000.0;
		return distanceOnTick;
	}
	
	private double gravitationalForce(){
		double grade = /* currentBlock.getGrade(); */ 0;		//set to 0 for now, need get method from Block
		
		double theta = Math.atan(grade/100.0);
		double force = 9.8 * this.mass * Math.sin(theta) * -1.0;
		
		return force;
	}
	
	public void notifyAtStation(int stationID){
		/*
		
		
		
		
		
		handle adding passengers
		
		
		
		
		*/
	}
	
	public double getMass(){
		return mass;
	}
	
	public void receivePowerCommand(double power){
		this.power = power;
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
	
	public void setEngineFailure(boolean failure){
		this.engineFailure = failure;
	}
	
	public void setBrakeFailure(boolean failure){
		this.brakeFailure = failure;
	}
	
	public void setSignalPickupFailure(boolean failure){
		this.signalFailure = failure;
	}
	
	public void receiveBeacon(BitSet beacon){
		this.trainController.receiveBeacon(beacon);
	}
	
	public void receiveSignalFromRail(BitSet railSignal){
		this.railSignal = railSignal;
	}
	
	public void recieveAuthority(int authority){
		this.authority = authority;
	}
}