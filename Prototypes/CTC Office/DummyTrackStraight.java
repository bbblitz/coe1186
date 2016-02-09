
public class DummyTrackStraight extends DummyTrackInterface{
  public DummyTrackInterface[] connected = new DummyTrackInterface[2];
  public DummySection[] connectedto = new DummySection[2];

  //How long the track is
  public int tracklength;

  //The x offset from some origin
  public int x;

  //The y offset from some origin
  public int y;

  //The direction the track is faceing (in degrees)
  public int direction;

  public DummyTrackStraight(int length, int tx, int ty, int dir){
    tracklength = length;
    x = tx;
    y = ty;
    direction = dir;
  }

  /**
   *@override
   */
  public DummyTrackInterface goesto(DummyTrackInterface from){
    if(from == connectedtracks[0]){
      return connectedtracks[1];
    }else{
      return connectedtracks[0];
    }
  }
}
