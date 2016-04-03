
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

    //return new TrackControllerManager();
    return null;
  }

  //TODO:Finish this method
  static CTCWindow createCTC(TrackControllerManager tcm, TrackModel tm){

    //return new CTCWindow(tm, tcm);
    return null;
  }

  public static void main(String[] args){
    //Order is important!
    TrackModel tm = createTrackModel();
    TrackControllerManager tcm = createTrackControllers(tm);
    createCTC(tcm,tm);

    //Run the simulation
    System.out.println("Starting the program, ticking every " + String.valueOf(deltaT) + " milliseconds...");

    Timer timer = new Timer();
    timer.schedule(new Ticker(), deltaT);
  }

  static class Ticker extends TimerTask {
    public void run() {
      System.out.println("tick");

      //trackModel.tick(deltaT);
      //ctc.tick(deltaT);


      //System.exit(0); //Stops the AWT thread (and everything else)
      Timer timer = new Timer();
      timer.schedule(new Ticker(), deltaT);
    }
  }
}
