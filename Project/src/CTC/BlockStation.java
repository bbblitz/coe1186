
public class BlockStation extends BlockInterface{
  public BlockInterface head;
  public BlockInterface tail;
  public BlockPart headto;
  public BlockPart tailto;
  public String stationName;

  //How long the track is
  public int length;

  //The x offset from some origin
  public int x;

  //The y offset from some origin
  public int y;

  //The direction the track is faceing (in degrees)
  public int direction;

  public BlockStation(int tx, int ty, int dir, int length){
    this.length = length;
    x = tx;
    y = ty;
    direction = dir;
    super.setFailState(TrackFailState.FS_NORMAL);
  }

  /**
   *@override
   */
  public BlockInterface goesto(BlockInterface from){
    if(from == head){
      return tail;
    }else{
      return head;
    }
  }
}
