
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
    super.setFailState(FS_NORMAL);
    System.out.println("Super failstate is:" + super.getFailState());
  }

  /**
   *@override
   */
  public DummyTrackInterface goesto(DummyTrackInterface from){
    if(from == connected[0]){
      return connected[1];
    }else{
      return connected[0];
    }
  }
}
