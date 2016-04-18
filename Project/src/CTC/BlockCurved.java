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
    //System.out.println("Calling goesto on curved block");
    if(head == null){
      //System.out.println("Head on curved track is null");
      //System.out.println(this.toString());
      System.exit(-1);
    }
    if(tail == null){
      //System.out.println("Tail on curved track is null");
      //System.out.println(this.toString());
      System.exit(-1);
    }
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

  public void drawBlock(Graphics g){
    BlockCurved dtc = this;
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
    Color[] allcol = new Color[drawnnum==0?1:drawnnum];
    int ccount = 0;
    if(drawred){
      allcol[ccount++] = Color.RED;
    }
    if(drawgray){
      allcol[ccount++] = Color.GRAY;
    }
    if(drawyellow){
      allcol[ccount++] = Color.YELLOW;
    }
    if(ccount == 0){
      allcol[ccount++] = Color.GREEN;
    }

    drawBySegments(allcol,g);
    //g.drawArc(dtc.x,dtc.y,dtc.radius,dtc.radius,dtc.startang,dtc.endang);
  }

  public double getLength(){
    double tpr = 2*Math.PI*radius;
    double perc = endang/360;
    return tpr*perc;
  }

  public void drawBySegments(Color[] col, Graphics g){
    int segmentlength = ((startang+endang)-startang)/5;
    //System.out.println("Segment length is:" + segmentlength);
    int j = 0;
    for(int i = startang; i != startang+endang; i+= segmentlength){
      //System.out.printf("Drawing a bit from %d to %d\n",i,i+segmentlength);
      g.setColor(col[(j++)%col.length]);
      //System.out.println("Color:");
      //System.out.println(col[j%col.length]);
      g.drawArc(x,y,radius,radius,i,segmentlength);
      if(i > 360)
        return;
    }
  }

  public void drawTrainOn(Graphics g, boolean on){
    //System.out.println("Dawing a train on " + toString());
    drawBlock(g);
    int ang = startang + (endang/2);
    //System.out.println("Angle to draw at is " + ang);
    int xoff = (int)(Math.cos(Math.toRadians(ang)) * (radius/2));
    int yoff = -(int)(Math.sin(Math.toRadians(ang)) * (radius/2));
    //System.out.printf("Offsets:(%d,%d)\n",xoff,yoff);
    //Quick hack to make it work
    if(xoff > 0)
      xoff=90;
    else
      xoff=10;
    if(yoff > 0)
      yoff=80;
    else
      yoff=20;


    int ex = x+xoff;
    int ey = y+yoff;
    //System.out.printf("fanals:(%d,%d)\n",ex,ey);
    g.setColor(Color.WHITE);
    g.drawOval(ex-5,ey-5,10,10);
  }

  public String toString(){
    return String.format("id:%d x:%d y:%d startang:%d anglength:%d radius:%d, head:%d tail:%d",getID(), x,y,startang,endang,radius,head!=null?head.getID():-1,tail!=null?tail.getID():-1);
  }
}
