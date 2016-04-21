import java.util.*;
import java.awt.*;

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
  public void drawBlock(Graphics g){
    //System.out.println("In BlockSwitch's drawBlock()");
    //System.out.println(toString());
    if(flipped){
      if(head instanceof BlockStraight){
        ((BlockStraight)head).drawBlock(g,Color.BLUE);
      }else{
        head.drawBlock(g);
      }
    }else{
      if(head instanceof BlockStraight){
        ((BlockStraight)head).drawBlock(g,Color.BLACK);
      }else{
        head.drawBlock(g);
      }
    }
    tail.drawBlock(g);
    if(flipped){
      if(divergent instanceof BlockStraight){
        ((BlockStraight)divergent).drawBlock(g,Color.BLACK);
      }else{
        divergent.drawBlock(g);
      }
    }
    else{
      if(divergent instanceof BlockStraight){
        ((BlockStraight)divergent).drawBlock(g,Color.BLUE);
      }else{
        divergent.drawBlock(g);
      }
    }
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
  }

  public void drawBySegments(Color[] colors, Graphics g){
    //System.out.println("In BlockStraight's drawBySegments()");
    float segmentlength =(float) ((length*super.TRACK_SCALE) / (colors.length * 5));
    //System.out.println("Finished calculations segmentlength");
    double exoff =(Math.cos(Math.toRadians(dir))*(length*super.TRACK_SCALE));
    double eyoff =(Math.sin(Math.toRadians(dir))*(length*super.TRACK_SCALE));
    //System.out.println("Finished calculations offsets");
    double ex = exoff+x;
    double ey = eyoff+y;
    //System.out.println("Finished calculations real position");
    double xadd = (exoff/((float)length))*segmentlength;
    double yadd = (eyoff/((float)length))*segmentlength;
    //System.out.println("Finished calculations delta positions");
    //System.out.println("Finished calculations");
    if(xadd == 0 && yadd == 0){
      System.out.println("Error 201: deltax and deltay is 0");
      System.exit(-1);
    }
    //System.out.printf("\texoff:%f\teyoff:%f\n\tex:%f\tey:%f\n\txadd:%f\tyadd:%f\n\tlength:%d\tsegmentlength:%f\n",exoff,eyoff,ex,ey,xadd,yadd,length,segmentlength);
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

    //System.out.printf("gx:%f\tgy:%f\n\t(gx - (exoff+x)):%f\t(gy - (eyoff+y)):%f\n",gx,gy,(gx - (exoff+x)),(gy - (eyoff+y)));
    for(; Math.abs(gx - (exoff+x)) > 1 || Math.abs(gy - (eyoff+y)) > 1; gx+= xadd, gy += yadd, col++){
      //System.out.println("Setting color and drawing segment...");
      g.setColor(colors[col%colors.length]);
      int grx = (int)gx;
      int gry = (int)gy;
      int egrx = (int)(gx+xadd);
      int egry = (int)(gy+yadd);
      //System.out.printf("Drawing a line from (%d,%d) to (%d,%d)",grx,gry,egrx,egry);
      g.drawLine((int)gx,(int)gy,(int)(gx+xadd),(int)(gy+yadd));
    }
  }

  public void drawTrainOn(Graphics g, boolean on){

    drawBlock(g);
    //Find the middle of the track
    int exoff = (int)(Math.cos(Math.toRadians(dir))*(length*super.TRACK_SCALE));
    int eyoff = (int)(Math.sin(Math.toRadians(dir))*(length*super.TRACK_SCALE));
    int ex = (exoff/2)+x;
    int ey = (eyoff/2)+y;
    g.setColor(Color.WHITE);
    //System.out.println("Drawing train...");
    g.drawOval(ex-5,ey-5,10,10);
  }

  public String toString(){
    String s = String.format("(switch)x:%d y:%d dir:%d len:%d flipped:%s id:%d\n",x,y,dir,length,flipped?"true":"false", getID());
    if(head!=null){
      s+= String.format("\t%s\n",head.toString());
    }
    if(tail!=null){
      s+= String.format("\t%s\n",tail.toString());
    }
    if(divergent!=null){
      s+= String.format("\t%s\n",divergent.toString());
    }
    return s;
  }
}
