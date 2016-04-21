BitsPlease
Nick Faughey

Train Controller Module
-----------------------

Folder structure


├── readme.txt	// this file
└── src
    ├── Beacon.java							///////////////////////////////////
    ├── BeaconSimulatorDialog.java			// 								 //
    ├── HackAuthorityFromCTCDialog.java		// Main files to be used/copied  //
    ├── HackSpeedFromCTCDialog.java			// into global folder for entire //
    ├── PowerController.java				// system use.					 //
    ├── TrainController.java				//								 //
    ├── TrainControllerUI.java				///////////////////////////////////
    └── solo	// For demoing the train controller only. Uses TrainModel and its dependencies to run.
        ├── Beacon.java
        ├── BeaconSimulatorDialog.java
        ├── HackAuthorityFromCTCDialog.java
        ├── HackSpeedFromCTCDialog.java
        ├── Main.java
        ├── PowerController.java
        ├── TrainController.java
        ├── TrainControllerUI.java
        ├── TrainModel.java
        ├── TrainModelUI.java
        ├── solo.zip
        └── bin
     		├── Main.class	// run this with `java Main` to demo Train Controller by itself
            └── *.class		// all binary versions of solo demo files