

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

public class main{

  static final long deltaT = 100;
  static final int speedupFactor = 1;


  static TrackModel trackModel;
  static CTCWindow ctc;
  static LineController tcm;

  //TODO:Finish this method
  static TrackModel createTrackModel(){

    return new TrackModel();
  }

  //TODO:Finish this method
  static LineController createTrackControllers(TrackModel tm){
	TrackController tc1 = new TrackController();
	TrackController tc2 = new TrackController();
	tc1.loadFile();
	tc2.loadFile();
    return new LineController(tc1, tc2, tm);
  }

  //TODO:Finish this method
  static CTCWindow createCTC(LineController tcm){

    return new CTCWindow(tcm);
  }

  public static void main(String[] args){
    //Order is important!
    trackModel = createTrackModel();
    tcm = createTrackControllers(trackModel);
    ctc = createCTC(tcm);


    // initialize a train on the track somewhere

    // create a route for the train?

    // give the train some authority and speed

    // go train go


    // Run the simulation
    System.out.println("Starting the program, ticking every " + String.valueOf(deltaT) + " ms, and telling modules that " + String.valueOf(deltaT * speedupFactor) + "ms have passed each tick.");

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new Ticker(), 0, deltaT);
  }

  static class Ticker extends TimerTask {
	public void run() {
		//System.out.println("tick");

		// tick everything once if we're going normal speed, or more than once if we're going fast
		for(int i = 0; i < speedupFactor; i++) {
			//trackModel.tick(deltaT);
			//ctc.tick(deltaT);
      	}


	}
  }
}
