import java.awt.*;

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
    System.out.println("Calling goesto on curved block");
    if(head == null){
      System.out.println("Head on curved track is null");
      System.out.println(this.toString());
      System.exit(-1);
    }
    if(tail == null){
      System.out.println("Tail on curved track is null");
      System.out.println(this.toString());
      System.exit(-1);
    }
    if(from == head){
      System.out.println("Returning " + tail.toString());
      return tail;
    }else{
      System.out.println("Returning " + head.toString());
      return head;
    }
  }

  public BlockInterface getHead() {
	  return this.head;
  }

  public BlockInterface getTail() {
	  return this.tail;
  }

  public void drawBlock(Graphics g){

  }
  public void drawTrainOn(Graphics g, boolean on){

  }

  public String toString(){
    return String.format("id:%d x:%d y:%d startang:%d anglength:%d radius:%d, head:%d tail:%d",getID(), x,y,startang,endang,radius,head!=null?head.getID():-1,tail!=null?tail.getID():-1);
  }
}
