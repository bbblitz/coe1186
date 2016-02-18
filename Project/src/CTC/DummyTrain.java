/*A train that goes on a DummyLine*/


public class DummyTrain{
  /** A direction in degrees that the train is faceing
   *  This should be updated after curved track peices*/
  public int direction;

  /** The speed that this train is going*/
  public int speed;

  /** The track segment that this train is currently on*/
  public DummyTrackInterface trackon;
}
