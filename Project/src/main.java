
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

public class main{

  static TrackModel trackModel;
  static CTCWindow ctc;

  static long deltaT = 100;

  //TODO:Finish this method
  static TrackModel createTrackModel(){

    return new TrackModel();
  }

  //TODO:Finish this method
  static TrackControllerManager createTrackControllers(TrackModel tm){

    return new TrackControllerManager(tm);
  }

  //TODO:Finish this method
  static CTCWindow createCTC(TrackControllerManager tcm, TrackModel tm){

    return new CTCWindow(tm, tcm);
  }

  public static void main(String[] args){
    //Order is important!
    trackModel = createTrackModel();
    TrackControllerManager tcm = createTrackControllers(trackModel);
    ctc = createCTC(tcm,trackModel);


    // initialize a train on the track somewhere

    // create a route for the train?

    // give the train some authority and speed

    // go train go


    // Run the simulation
    System.out.println("Starting the program, ticking every " + String.valueOf(deltaT) + " milliseconds...");

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new Ticker(), 0, deltaT);
  }

  static class Ticker extends TimerTask {
    public void run() {
      System.out.println("tick");

      //trackModel.tick(deltaT);
      //ctc.tick(deltaT);
    }
  }
}
