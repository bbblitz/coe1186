
public class DummyTrackCurved extends DummyTrackInterface{
  public DummyTrackInterface[] connected = new DummyTrackInterface[2];
  public DummySection[] connectedto = new DummySection[2];

  public int x;
  public int y;

  //Start and ending angle for the curve
  public int startang;
  public int endang;

  //The radius of the curve
  public int radius;

  //All curved track peices have heads at the clockwise-most edge, and tails at the counter-clockwise-most edge

  public DummyTrackCurved(int tx, int ty, int sa, int ea, int r){
    x = tx;
    y = ty;
    startang = sa;
    endang = ea;
    radius = r;
    super.setFailState(FS_NORMAL);
  }

  public DummyTrackInterface goesto(DummyTrackInterface from){
    if(from == connected[0]){
      return connected[1];
    }else{
      return connected[0];
    }
  }
}
