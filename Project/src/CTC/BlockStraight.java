import java.awt.*;
import java.util.*;

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

  public BlockStraight(int tx, int ty, int dir, int length){
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
    //System.out.println("Calling goesto on straight block");
    if(from == head){
      //System.out.println("Returning " + tail.toString());
      return tail;
    }else{
      //System.out.println("Returning " + head.toString());
      return head;
    }
  }

  public BlockInterface getHead() {
	  return this.head;
  }

  public BlockInterface getTail() {
	  return this.tail;
  }

  public double getLength(){
    return length;
  }

  /*Prints out a string representation of this block.*/
  public String toString(){
    return String.format("id:%d x:%3d y:%3d length:%3d head:%3d tail:%3d direction:%3d",getID(), x, y, length, head != null?head.getID():-1, tail != null?tail.getID():-1, direction);
  }

  public void drawBlock(Graphics g){
    System.out.println("In BlockStraight's drawBlock()");
    //Figure out what colors to draw in {}
    int drawnnum = 0;
    boolean drawgray = false, drawred = false, drawyellow = false;
    if(getFailState() == TrackFailState.FS_TRACK_CIRCUIT_FAILURE || getFailState() == TrackFailState.FS_CIRCUIT_AND_RAIL || getFailState() == TrackFailState.FS_CIRCUIT_AND_POWER || getFailState() == TrackFailState.FS_CIRCUIT_RAIL_POWER){
      drawred = true;
      drawnnum++;
    }
    if(getFailState() == TrackFailState.FS_BROKEN_RAIL || getFailState() == TrackFailState.FS_CIRCUIT_AND_RAIL || getFailState() == TrackFailState.FS_RAIL_AND_POWER || getFailState() == TrackFailState.FS_CIRCUIT_RAIL_POWER){
      drawgray = true;
      drawnnum++;
    }
    if(getFailState() == TrackFailState.FS_POWER_FAILURE || getFailState() == TrackFailState.FS_CIRCUIT_AND_POWER || getFailState() == TrackFailState.FS_RAIL_AND_POWER || getFailState() == TrackFailState.FS_CIRCUIT_RAIL_POWER){
      drawyellow = true;
      drawnnum++;
    }

    Color[] allcol = new Color[Math.max(1,drawnnum)];
    int count = 0;
    if(drawred){
      allcol[count++] = Color.RED;
    }
    if(drawgray){
      allcol[count++] = Color.GRAY;
    }
    if(drawyellow){
      allcol[count++] = Color.YELLOW;
    }
    if(!drawred && !drawgray && !drawyellow){
      allcol[count++] = Color.GREEN;
    }
    //Actually draw the thing
    drawBySegments(allcol, g);


    ArrayList<Infrastructure> allinfra = this.getInfrastructure();
    if(allinfra == null) return;
    for(Infrastructure f : allinfra){

    }
  }

  public void drawBySegments(Color[] colors, Graphics g){
    System.out.println("In BlockStraight's drawBySegments()");
    float segmentlength =(float) ((length*super.TRACK_SCALE) / (colors.length * 5));
    System.out.println("Finished calculations segmentlength");
    double exoff =(Math.cos(Math.toRadians(direction))*(length*super.TRACK_SCALE));
    double eyoff =(Math.sin(Math.toRadians(direction))*(length*super.TRACK_SCALE));
    System.out.println("Finished calculations offsets");
    double ex = exoff+x;
    double ey = eyoff+y;
    System.out.println("Finished calculations real position");
    double xadd = (exoff/((float)length))*segmentlength;
    double yadd = (eyoff/((float)length))*segmentlength;
    System.out.println("Finished calculations delta positions");
    System.out.println("Finished calculations");
    if(xadd == 0 && yadd == 0){
      System.out.println("Error 201: deltax and deltay is 0");
      System.exit(-1);
    }
    System.out.printf("\texoff:%f\teyoff:%f\n\tex:%f\tey:%f\n\txadd:%f\tyadd:%f\n\tlength:%d\tsegmentlength:%f\n",exoff,eyoff,ex,ey,xadd,yadd,length,segmentlength);
    //Print some debug stuff
    /*
    System.out.printf("Drawing straight block:\n\toff:(%d,%d)\n\tstart:(%d,%d)\n\tend:(%d,%d)\n\tadd:(%d,%d)\n",exoff,eyoff,x,y,ex,ey,xadd,yadd);
    System.out.printf("With colors:\n");
    for(int i = 0; i < colors.length; i++){
      System.out.printf("\t%s\n",colors[i].toString());
    }
    */
    /*
    int gx = x;
    int gy = y;
    int colind = 0;
    while((gx != ex) || (gy != ey)){
      System.out.printf("Drawing a line from (%d,%d) to (%d,%d)",gx,gy,gx+xadd,gy+yadd);

      g.setColor(colors[colind%colors.length]);
      g.drawLine(gx,gy,gx+xadd,gy+yadd);

      colind++;
      gx += xadd;
      gy += yadd;
    }
    */
    float gx = x;
    float gy = y;
    int col = 0;

    for(; (gx - (exoff+x) > 2) || (gy - (eyoff+y) > 2); gx+= xadd, gy += yadd, col++){
      System.out.println("Setting color and drawing segment...");
      g.setColor(colors[col%colors.length]);
      //System.out.printf("Drawing a line from (%d,%d) to (%d,%d)",gx,gy,gx+xadd,gy+yadd);
      g.drawLine((int)gx,(int)gy,(int)(gx+xadd),(int)(gy+yadd));
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
