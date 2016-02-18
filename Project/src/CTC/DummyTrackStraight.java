
public class DummyTrackStraight extends DummyTrackInterface{
  public DummyTrackInterface head;
  public DummyTrackInterface tail;
  public DummySection headto;
  public DummySection tailto;

  //How long the track is
  public int length;

  //The x offset from some origin
  public int x;

  //The y offset from some origin
  public int y;

  //The direction the track is faceing (in degrees)
  public int direction;

  public DummyTrackStraight(int tx, int ty, int dir, int length){
    this.length = length;
    x = tx;
    y = ty;
    direction = dir;
    super.setFailState(TrackFailState.FS_NORMAL);
  }

  /**
   *@override
   */
  public DummyTrackInterface goesto(DummyTrackInterface from){
    if(from == head){
      return tail;
    }else{
      return head;
    }
  }
}
