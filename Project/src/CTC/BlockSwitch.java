import java.util.*;

public class BlockSwitch extends BlockInterface{
  //.
  //(0,0)         /---------- Head
  //Tail--------/     Direction->
  //               --------- Divergent

  public int x;
  public int y;
  public int dir;
  public int length;
  public BlockInterface tail;
  public BlockPart tailto;
  public boolean flipped;

  public BlockInterface head;
  public BlockPart headto;
  public BlockInterface divergent;
  public BlockPart divergentto;

  public BlockSwitch(int tx, int ty, int d, int l){
    x = tx;
    y = ty;
    dir = d;
    length = l;
    super.setFailState(TrackFailState.FS_NORMAL);
  }

  public BlockInterface goesto(BlockInterface from){
    if(from == head || from == divergent){
      return tail;
    }else{
      if(flipped){
        return divergent;
      }else{
        return head;
      }
    }
  }
  
  public BlockInterface getHead() {
	  return this.head;
  }
  
  public BlockInterface getTail() {
	  return this.tail;
  }
  
  public void setFlipped(boolean flipped) {
	  this.flipped = flipped;
  }
  
  public boolean getFlipped() {
	  return this.flipped;
  }
}
