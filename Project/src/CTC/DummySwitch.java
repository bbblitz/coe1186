import java.util.*;

public class DummySwitch extends DummyTrackInterface{
  //.
  //(0,0)         /---------- Head
  //Tail--------/     Direction->
  //               --------- Divergent

  public int x;
  public int y;
  public int dir;
  public int length;
  public DummyTrackInterface tail;
  public DummySection tailto;
  public boolean fliped;

  public DummyTrackInterface head;
  public DummySection headto;
  public DummyTrackInterface divergent;
  public DummySection divergentto;

  public DummySwitch(int tx, int ty, int d, int l){
    x = tx;
    y = ty;
    dir = d;
    length = l;
    super.setFailState(TrackFailState.FS_NORMAL);
  }

  public DummyTrackInterface goesto(DummyTrackInterface from){
    if(from == head || from == divergent){
      return tail;
    }else{
      if(fliped){
        return divergent;
      }else{
        return head;
      }
    }
  }
}
