
public class BlockCurved extends BlockInterface{
  public BlockInterface head;
  public BlockInterface tail;
  public BlockPart headto;
  public BlockPart tailto;

  public int x;
  public int y;

  //Start and angle length for the curve
  public int startang;
  public int endang;

  //The radius of the curve
  public int radius;

  //All curved track peices have heads at the clockwise-most edge, and tails at the counter-clockwise-most edge

  public BlockCurved(int tx, int ty, int sa, int ea, int r){
    x = tx;
    y = ty;
    startang = sa;
    endang = ea;
    radius = r;
    super.setFailState(TrackFailState.FS_NORMAL);
  }
  public BlockInterface goesto(BlockInterface from){
    if(from == head){
      return tail;
    }else{
      return head;
    }
  }
  
  public BlockInterface getHead() {
	  return this.head;
  }
  
  public BlockInterface getTail() {
	  return this.tail;
  }
}
