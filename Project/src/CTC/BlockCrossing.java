
public class BlockStraight extends BlockInterface{
  public BlockInterface head;
  public BlockInterface tail;
  public BlockPart headto;
  public BlockPart tailto;

  //How long the track is
  public int length;

  //The x offset from some origin
  public int x;

  //The y offset from some origin
  public int y;

  //The direction the track is faceing (in degrees)
  public int direction;

  public BlockCrossing(int tx, int ty, int dir, int length){
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

  public BlockInterface getHead() {
	  return this.head;
  }

  public BlockInterface getTail() {
	  return this.tail;
  }

  /*Prints out a string representation of this block.*/
  public String toString(){
    return String.format("x:%3d y:%3d length:%3d head:%3d tail:%3d direction:%3d", x, y, length, head != null?head.getID():-1, tail != null?tail.getID():-1, direction);
  }
}
