import java.awt.*;

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


  public BlockStation(int tx, int ty, int dir, int length, String stationname){
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
    System.out.println("Calling goesto on station block" + this.toString());
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

  public String getStationName() {
	  return this.stationName;
  }

  public String toString(){
    return String.format("x:%3d y:%3d length:%3d head:%3d tail:%3d direction:%3d name:%s", x, y, length, head != null?head.getID():-1, tail != null?tail.getID():-1, direction, this.stationName);
  }

  public void drawBlock(Graphics g){
    BlockStation bs = this;
    int starts[][] = new int[3][2];
    int ends[][] = new int[3][2];
    int xvar  = (int)(Math.sin(Math.toRadians(bs.direction))*4);
    int yvar = (int)(Math.cos(Math.toRadians(bs.direction))*4);
    starts[0][0] = bs.x + xvar;
    starts[1][0] = bs.x;
    starts[2][0] = bs.x - xvar;
    starts[0][1] = bs.y + yvar;
    starts[1][1] = bs.y;
    starts[2][1] = bs.y - yvar;

    int endx = bs.x + (int)(Math.cos(Math.toRadians(bs.direction))*bs.length);
    int endy = bs.y + (int)(Math.sin(Math.toRadians(bs.direction))*bs.length);
    ends[0][0] = endx + xvar;
    ends[1][0] = endx;
    ends[2][0] = endx - xvar;
    ends[0][1] = endy + yvar;
    ends[1][1] = endy;
    ends[2][1] = endy - yvar;

    int sx = bs.x;
    int sx2 = bs.x + 4;
    int sx3 = bs.x - 4;
    int sy = bs.y;
    int exoff = (int)(Math.cos(Math.toRadians(bs.direction))*bs.length);
    for(int i = 0; i < 3; i++){
      g.drawLine(starts[i][0],starts[i][1],ends[i][0],ends[i][1]);
    }
  }
  public void drawTrainOn(Graphics g, boolean on){
    drawBlock(g);
    //Find the middle of the track
    int exoff = (int)(Math.cos(Math.toRadians(direction))*length);
    int eyoff = (int)(Math.sin(Math.toRadians(direction))*length);
    int ex = (exoff/2)+x;
    int ey = (eyoff/2)+y;
    g.setColor(Color.WHITE);
    g.drawOval(ex-5,ey-5,10,10);
  }
}
