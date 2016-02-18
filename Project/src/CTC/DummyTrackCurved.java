
public class DummyTrackCurved extends DummyTrackInterface{
  public DummyTrackInterface head;
  public DummyTrackInterface tail;
  public DummySection headto;
  public DummySection tailto;

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
    super.setFailState(TrackFailState.FS_NORMAL);
  }
  public DummyTrackInterface goesto(DummyTrackInterface from){
    if(from == head){
      return tail;
    }else{
      return head;
    }
  }
}
