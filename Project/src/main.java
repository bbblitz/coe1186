
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
    createCTC(tcm);
  }
}
