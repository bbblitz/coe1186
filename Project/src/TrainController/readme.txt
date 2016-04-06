/**************************************************/
              Communication Guidelines
         Train Controller <===> Train Model
/**************************************************/



Train Controller
------------------------
constructor: public TrainController(TrainModel trainModel);

TrainController's public TrainModel-facing methods:

TrainController.receiveVelocityCommandFromTrainOperator(double velocity);
TrainController.forceEngineFailure();
TrainController.fixEngineFailure();
TrainController.forceBrakeFailure();
TrainController.fixBrakeFailure();
TrainController.forceSignalPickupFailure();
TrainController.fixSignalPickupFailure();
TrainController.receiveSignalFromRail(byte[] signalPackage);
TrainController.receiveBeacon(byte[] beaconPackage);

Each TrainModel should initialize a TrainController with its constructor

	public TrainController(TrainModel trainModel);

passing a reference to itself as the trainModel.



The TrainController class will expose the following methods for use by the train model:

-TrainController.receiveVelocityCommandFromTrainOperator(double velocity);
-TrainController.forceEngineFailure();
-TrainController.fixEngineFailure();
-TrainController.forceBrakeFailure();
-TrainController.fixBrakeFailure();
-TrainController.forceSignalPickupFailure();
-TrainController.fixSignalPickupFailure();
-TrainController.receiveSignalFromRail(byte[] signalPackage);
-TrainController.receiveBeacon(byte[] beaconPackage);


The TrainController class will expect the following methods to be exposed by its train model:

-TrainModel.getMass();	// maybe - are we accounting for variable mass???...
-TrainModel.receivePowerCommand(double power);
-TrainModel.getCurrentVelocity();
-TrainModel.activateEmergencyBrake();
-TrainModel.deactivateEmergencyBrake();
-TrainModel.activateServiceBrake();
-TrainModel.deactivateServiceBrake();
