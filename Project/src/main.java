
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

public class main{

  //TODO:Finish this method
  static TrackModel createTrackModel(){

    return null;
  }

  //TODO:Finish this method
  static TrackControllerManager createTrackControllers(TrackModel tm){

    return null;
  }

  //TODO:Finish this method
  static CTCWindow createCTC(TrackControllerManager tcm, TrackModel tm){

    return null;
  }

  public static void main(String[] args){
    //Order is important!
    TrackModel tm = createTrackModel();
    TrackControllerManager tcm = createTrackControllers(tm);
    createCTC(tcm,tm);

    //Run the simulation
    System.out.println("About to start running");
    for(int i = 0; i < 1; i++){
      Timer timer = new Timer();
      timer.schedule(new RemindTask(), 5000);
    }
    System.out.println("Done");
  }

  static class RemindTask extends TimerTask {
    public void run() {
      System.out.println("Time's up!");
      //System.exit(0); //Stops the AWT thread (and everything else)
      Timer timer = new Timer();
      timer.schedule(new RemindTask(), 5000);
    }
  }
}
