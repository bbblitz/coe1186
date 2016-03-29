
public class main{

  //TODO:Finish this method
  TrackModel createTrackModel(){

  }

  //TODO:Finish this method
  TrackControllerManager createTrackControllers(TrackModel tm){

  }

  //TODO:Finish this method
  CTCWindow createCTC(TrackControllerManager tcm){

  }

  public static void main(String[] args){
    //Order is important!
    TrackModel tm = createTrackModel();
    TrackControllerManager tcm = createTrackControllers(tm);
    createCTC(tm,tcm);

    System.out.println("About to start running");
    for(int i = 0; i < 100; i++)
      new timertest((long)5000);
    System.out.println("Done");
  }

  class RemindTask extends TimerTask {
    public void run() {
      System.out.println("Time's up!");
      toolkit.beep();
      timer.cancel(); //Not necessary because we call System.exit
      //System.exit(0); //Stops the AWT thread (and everything else)
    }
  }
}
