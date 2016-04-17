import java.util.BitSet;


public class TrainController {
	private TrainModel trainModel;
	private PowerController powerController;
    private PowerController powerController2;
	private TrainControllerUI ui;
	
	private final double MAX_TRAIN_POWER = 120000;	// 120kW
	private final double DISTANCE_BUFFER = 10;	// give some buffer to make sure train will never exceed target distances
	private final int STATION_DWELL_TIME = 60000;	// 60s
	
	/* signal pickups */
	private double velocityFromTrainOperator = 20;
	private double velocityFromCTC;
	private double authorityFromCTC;

	/* intermediate calculations to display on UI */
	private double targetVelocity;
	private double powerCommand;
	
	/* station stuff */
	private String nextStation;
	private int nextStationId;
	private int distanceToStationEnd;
	private enum StationApproachStatus {
		NONE,
		DISTANCE_SET,
		BRAKING_FOR_APPROACH,
		DWELLING
	}
	private StationApproachStatus stationApproachStatus = StationApproachStatus.NONE;
	private int dwellTimeRemaining; 

	/* train status */
	private double velocitySI;
	private boolean doorsOpenLeft;
	private boolean doorsOpenRight;
	private boolean lightsOn;
	
	private double odometer;
	double _lastPowerCommand;

	/* internal logging and event tracking */
	boolean outOfAuthorityBraking = false;
	
	/**
	 * Create the TrainController
	 * @param trainModel TrainModel The train model that this controller is installed on
	 */
	public TrainController(TrainModel trainModel) {
		this.trainModel = trainModel;
		this.powerController = new PowerController(trainModel.getMass());
        this.powerController2 = new PowerController(trainModel.getMass());
		this.ui = new TrainControllerUI();
		this.ui.initialize(this);
		
		this.nextStation = "";
		this.nextStationId = -1;
		this.distanceToStationEnd = -1;

		// train status
		this.velocitySI = this.trainModel.getCurrentVelocitySI();
		this.doorsOpenLeft = false;
		this.doorsOpenRight = false;
		this.lightsOn = false;
		
		this.odometer = 0;
	}
	
	/**
	 * Update train's odometer and authority, and send a new power command to train
	 * @param deltaT double Milliseconds since last update
	 */
	public void tick(double deltaT) {
		// update odometer
		// deltaX = 0.5 * (v + v0) * deltaT
		double oldVelocity = this.velocitySI;
		double newVelocity = trainModel.getCurrentVelocitySI();
		double deltaX = 0.5 * (oldVelocity + newVelocity) * millisToSeconds(deltaT);
		
		this.velocitySI = newVelocity;
		this.odometer += deltaX;
		this.authorityFromCTC -= deltaX;
		this.authorityFromCTC = Math.max(0, this.authorityFromCTC);	// no negative authorities

		// if we know the distance to station end, update that
		if (this.stationApproachStatus == StationApproachStatus.DISTANCE_SET || this.stationApproachStatus == StationApproachStatus.BRAKING_FOR_APPROACH) {
			this.distanceToStationEnd -= deltaX;
			this.distanceToStationEnd = Math.max(0, this.distanceToStationEnd);	// no negative distances
		}

		// if we were dwelling, update that
		if (this.stationApproachStatus == StationApproachStatus.DWELLING) {
			this.dwellTimeRemaining -= deltaT;
			// have we been dwelling long enough?
			if (this.dwellTimeRemaining <= 0) {
				this.stationApproachStatus = StationApproachStatus.NONE;
				this.trainModel.deactivateServiceBrake();

				this.trainModel.turnOnLights();
				this.trainModel.closeDoors();
				this.ui.log("Leaving station.");
			}
		}

		// check if we braked all the way to a stop at the station
		if (this.stationApproachStatus == StationApproachStatus.BRAKING_FOR_APPROACH && this.velocitySI == 0) {
			this.stationApproachStatus = StationApproachStatus.DWELLING;
			this.dwellTimeRemaining = STATION_DWELL_TIME;
			this.ui.log("Stopped completely at station. Dwelling for " + (STATION_DWELL_TIME / 1000) + "s.");

			this.trainModel.notifyAtStation();	// so he can figure out passengers exiting
			this.trainModel.turnOnLights();
			this.trainModel.openDoors();
		}

		// calculate power if we're not at or braking for a station
		if (this.stationApproachStatus == StationApproachStatus.NONE || this.stationApproachStatus == StationApproachStatus.DISTANCE_SET) {
			// 0 power if no authority or emergency brake
			if (this.authorityFromCTC > 0 && !this.trainModel.isEmergencyBrakeActivated()) {
				// calculate power (power <= 0 means brake should be applied)
				double power = calculatePower(deltaT);
				if (power <= 0) {
					// best action is to slow down
					power = 0;
					this.trainModel.activateServiceBrake();
				} else if (power > 0) {
					// best action is to speed up
					this.trainModel.deactivateServiceBrake();
				}
				this.powerCommand = power;
				this.trainModel.receivePowerCommand(power);
			} else {
				// either out of authority or ebrake applied
				this.powerCommand = 0;
				this.trainModel.receivePowerCommand(0);
			}
		} else {
			// we're braking for a station approach or stopped at the station
			this.trainModel.activateServiceBrake();	// if it wasn't already
			this.trainModel.receivePowerCommand(0);
		}

		// check the temperature for AC control
		int temperature = this.trainModel.getTemperature();
		if (temperature > 75) {
			this.trainModel.setTemperature(75);
		} else if (temperature < 65) {
			this.trainModel.setTemperature(65);
		}

        this.ui.refresh();
	}
	
	/**
	 * Calculate the best power to send to the train based on velocity and authority
	 * @param deltaT double Milliseconds since last calculation
	 * @return double Power in watts. Negative power means brake.
	 */
	public double calculatePower(double deltaT) {
		double power;
		if (this.stationApproachStatus == StationApproachStatus.DISTANCE_SET) {
			// distance to station is set but we're not braking yet - should we start braking?
			double stoppingDistance = this.calculateServiceBrakeStoppingDistance();
			if (stoppingDistance + DISTANCE_BUFFER >= this.distanceToStationEnd) {
				double feetToEnd = Math.round(this.metersToFeet(this.distanceToStationEnd) * 100.0) / 100.0;
				this.ui.log("Starting to brake for station " + String.valueOf(feetToEnd) + " ft away.");
				// give 0 power to start braking and begin station approach
				this.stationApproachStatus = StationApproachStatus.BRAKING_FOR_APPROACH;
				return 0;
			}
		}
		if (haveEnoughAuthority()) {
			// authority is fine, turn off service brake if applied and let the train continue
			this.outOfAuthorityBraking = false;
			this.trainModel.deactivateServiceBrake();
			
			// decide best velocity to target
			this.targetVelocity = Math.min(this.velocityFromTrainOperator, this.velocityFromCTC);
			
			// calculate new power redundantly
			double calculatedPower = this.powerController.calculatePower(this.velocitySI, this.targetVelocity, deltaT);
			double calculatedPower2 = this.powerController2.calculatePower(this.velocitySI, this.targetVelocity, deltaT);

            if (calculatedPower != calculatedPower2) {
                // uh oh
                this.trainModel.activateEmergencyBrake();
                return 0;
            }

			// abs(power) should never be more than max train power
			power = calculatedPower > 0 ? Math.min(calculatedPower, this.MAX_TRAIN_POWER) : Math.max(calculatedPower, -1 * this.MAX_TRAIN_POWER);
			return power;
		} else {
			// out of authority, brake the train to a stop (or until more authority is received)
			if (!this.outOfAuthorityBraking) {
				this.ui.log("Authority running out, starting to brake.");
				this.outOfAuthorityBraking = true;
			}
			this.trainModel.activateServiceBrake();
			return 0;
		}
	}
	
	/**
	 * If the service brake is applied now and the train is slowed at 1.2 m/s^2, will it stop within authority limits?
	 * @return boolean
	 */
	private boolean haveEnoughAuthority() {
		double stoppingDistance = this.calculateServiceBrakeStoppingDistance();
		if ((stoppingDistance + this.DISTANCE_BUFFER) >= this.authorityFromCTC) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * If the service brake is applied now, how far will it be until we completely stop?
	 */
	private double calculateServiceBrakeStoppingDistance() {
		// v^2 = 0 = v0^2 + 2*a*deltaX
		// deltaX = -(v0^2) / (2*a)
		double stoppingDistance = -1 * Math.pow(this.velocitySI, 2) / (2.0 * -1.2);	// service brake decceleration = 1.2m/s^2
		return stoppingDistance;
	}

	// temporary - combined into one bit package later
	public void receiveAuthority(int authority) {
		double authFeet = Math.round(this.metersToFeet(authority) * 100.0) / 100.0;
		this.ui.log("Received authority command: " + String.valueOf(authFeet) + " ft");
		this.authorityFromCTC = authority;
	}

	// temporary - combined into one bit package later
	public void receiveSpeed(int speed) {
		double speedMph = Math.round(this.mpsToMph(speed) * 100.0) / 100.0;
		this.ui.log("Received speed command from CTC: " + String.valueOf(speedMph) + " mph");
		this.velocityFromCTC = speed;
	}
	
	/**
	 * First 5 bits are speed, the rest is authority
	 * @param signalPackage
	 */
	public void receiveSignalFromRail(BitSet signalPackage) {
		BitSet bitSpeed = signalPackage.get(0, 5);	// bits 0-4 are speed
		BitSet bitAuthority = signalPackage.get(5, signalPackage.length());	// the rest is authority
		int speed = this.bitsetToInt(bitSpeed);
		int authority = this.bitsetToInt(bitAuthority);
		
		this.velocityFromCTC = speed;
		this.authorityFromCTC = authority;
	}
	
	/**
	 * Bit 0-5: station id
	 * Bit 6-end: distance to end of station
	 * Note: bits 6-end are irrelevant if station id is new (leaving station)
	 */
	public void receiveBeacon(BitSet beaconPackage) {
		BitSet bitStationId = beaconPackage.get(0, 5);
		BitSet bitDistance = beaconPackage.get(5, beaconPackage.length());
		int stationId = this.bitsetToInt(bitStationId);
		int distance = this.bitsetToInt(bitDistance);


		if (stationId == this.nextStationId || this.nextStationId == -1) {
			// we are entering a station
			this.distanceToStationEnd = distance;
			this.stationApproachStatus = StationApproachStatus.DISTANCE_SET;
		} else {
			// we are leaving a station, don't care about distance back to station
			String stationName = this.stationIdToStationName(stationId);
			this.nextStation = stationName;
			this.nextStationId = stationId;
			this.trainModel.displayNextStation(stationName);
		}
		this.ui.log("Received beacon: stationId=" + String.valueOf(stationId) + " distance=" + String.valueOf(distance));
	}

	private String stationIdToStationName(int stationId) {

		return "Steel Plaza";
	}



	/**
	 * Methods to change physical train properties
	 */
	
	private void openDoorsLeft() {
		this.doorsOpenLeft = true;
	}
	
	private void openDoorsRight() {
		this.doorsOpenRight = true;
	}
	
	private void closeDoorsLeft() {
		this.doorsOpenLeft = false;
	}
	
	private void closeDoorsRight() {
		this.doorsOpenRight = false;
	}
	
	public boolean lightsAreOn() {
		return this.trainModel.getLightsStatus();
	}

	public boolean doorsAreOpen() {
		return this.trainModel.doorsAreOpen();
	}
	
	
	/**
	 * Forced values
	 */

	public void hackVelocityFromCTC(double newVelocity) {
		this.velocityFromCTC = newVelocity;
	}

	public void hackAuthorityFromCTC(double newAuthority) {
		this.authorityFromCTC = newAuthority;
	}

	/**
	 * Interactions with UI
	 */
	public double getTargetVelocity() {
		return this.targetVelocity;
	}

	public double getCurrentVelocitySI() {
		return this.velocitySI;
	}

	public double getPowerCommand() {
		return this.powerCommand;
	}

	public double getAuthorityFromCTC() {
		return this.authorityFromCTC;
	}

	public double getVelocityFromCTC() {
		return this.velocityFromCTC;
	}

	public double getVelocityFromTrainOperator() {
		return this.velocityFromTrainOperator;
	}

	public int getTemperature() {
		return this.trainModel.getTemperature();
	}

	public String getNextStation() {
		return this.nextStation;
	}

    public void activateEmergencyBrake() {
        this.trainModel.activateEmergencyBrake();
    }

    public void setVelocityFromTrainOperator(double velocityFromTrainOperator) {
        this.velocityFromTrainOperator = velocityFromTrainOperator;
    }

    public void setLightsOn(boolean on) {
        if (on) {
            this.trainModel.turnOnLights();
        } else {
            this.trainModel.turnOffLights();
        }
    }

    public void setDoorsOpen(boolean open) {
        if (open) {
            this.trainModel.openDoors();
        } else {
            this.trainModel.closeDoors();
        }
    }

    public boolean getEngineFailure() {
        return this.trainModel.getEngineFailure();
    }

    public boolean getBrakeFailure() {
        return this.trainModel.getBrakeFailure();
    }

    public boolean getSignalPickupFailure() {
        return this.trainModel.getSignalPickupFailure();
    }

	/**
	 * Utility functions
	 */

	public double millisToSeconds(double millis) {
		return millis / 1000.0;
	}
	
	public double secondsToMillis(double seconds) {
		return seconds * 1000.0;
	}
	
	public double metersToFeet(double meters) {
		return meters * 3.28084;
	}
	
	public double feetToMeters(double feet) {
		return feet * 0.3048;
	}

	public double mpsToMph(double mps) {
		return mps * 2.2369;
	}
	
	private int bitsetToInt(BitSet bitset) {
		int value = 0;
		for (int i = 0; i < 64; i++) {	// max 64 bits
			if (bitset.get(i)) {
				value |= (1 << i);
			}
		}
		return value;
	}
	
	
	
}
