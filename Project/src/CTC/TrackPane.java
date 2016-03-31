/**The black panel that draws the track on the left of the CTC interface*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TrackPane extends JPanel implements MouseListener {
  ArrayList<Line> lines;
  ArrayList<Boolean> vislines;
  Config c;
  public TrackPane(Config c){
    super();
    this.c = c;
    vislines = c.vislines;
    lines = c.aldl;
    addMouseListener(this);
  }

  public void tick(){
    System.out.println("I've ticked!");
  }

  public void mousePressed(MouseEvent e) {
   System.out.println("Mouse pressed; # of clicks: " + e.getClickCount());
  }

  public void mouseReleased(MouseEvent e) {
    //System.out.println("Mouse released; # of clicks: " + e.getClickCount());
  }

  public void mouseEntered(MouseEvent e) {
    //System.out.println("Mouse entered");
  }

  public void mouseExited(MouseEvent e) {
    //System.out.println("Mouse exited");
  }

  public void mouseClicked(MouseEvent e) {
    //System.out.printf("Looking for switches at (%d,%d)\n", e.getX(), e.getY());
    //System.out.println("config.trackpane is: " + c.trackpane);
    for(int i = 0; i < lines.size(); i++){
      if(vislines.get(i)){
        for(BlockInterface dti : lines.get(i).blocks){
          if(dti instanceof BlockSwitch){
            BlockSwitch dts = (BlockSwitch)dti;
            //System.out.printf("Found switch at (%d,%d)\n",dts.x,dts.y);
            c.selected = dti;
            c.switchpane.repaint();
            return;
          }
        }
      }
    }
  }

  /**
   * @override
   */
  public void paint(Graphics g){
    if(lines == null){
      System.out.println("TrackPane.java No lines found!");
      System.exit(0);
    }
    /*
    for(Boolean b : vislines){
      System.out.print(b + "\t");
    }
    */
    g.setColor(Color.black);
    Dimension d = getSize();
    g.fillRect(0,0,d.width,d.height);
    for(Line dl : lines){
      if(vislines.get(dl.lineid)){//Check if this line should be visible
        drawLine(g,dl);
      }
    }
  }

  public void drawLine(Graphics g, Line dl){
    //System.out.println("Drawing line:");
    //System.out.println(dl.toString());
    for(BlockInterface dti : dl.blocks){
      drawSegment(g,dti);
    }
  }

  public void drawSegment(Graphics g, BlockInterface dti){
    //System.out.println("Drawing segment:" + (dti!=null?dti.toString():"Null dti"));
    if(dti == null) return;
    Color c = getColorState(g,dti);
    drawSegment(g,dti,c);
  }

  public void drawInfrastructure(Graphics g, BlockStraight track, Color c){

  }

  public void drawStraightTrack(Graphics g, BlockStraight track, Color c){
    BlockStraight ts = track;
    int sx = ts.x;
    int sy = ts.y;
    //System.out.println("Cos of " + ts.direction + " is " + Math.cos(Math.toRadians(ts.direction)));
    int exoff = (int)(Math.cos(Math.toRadians(ts.direction))*ts.length);
    int eyoff = (int)(Math.sin(Math.toRadians(ts.direction))*ts.length);
    int ex = exoff+sx;
    int ey = eyoff+sy;
    //System.out.printf("Line from (%d,%d)+(%d,%d) to (%d,%d)\n",sx,sy,exoff,eyoff,ex,ey);
    g.drawLine(sx,sy,ex,ey);
    ArrayList<Infrastructure> allinfra = track.getInfrastructure();
    if(allinfra == null) return;
    for(Infrastructure f : allinfra){

    }
  }

  public void drawCrossingTrack(Graphics g, BlockCrossing dti, Color c){
    BlockCrossing bs = dti;
    int starts[][] = new int[2][2];
    int ends[][] = new int[2][2];
    int xvar  = (int)(Math.sin(Math.toRadians(bs.direction))*4);
    int yvar = (int)(Math.cos(Math.toRadians(bs.direction))*4);
    starts[0][0] = bs.x + xvar;
    starts[1][0] = bs.x;
    starts[0][1] = bs.y + yvar;
    starts[1][1] = bs.y;

    int endx = bs.x + (int)(Math.cos(Math.toRadians(bs.direction))*bs.length);
    int endy = bs.y + (int)(Math.sin(Math.toRadians(bs.direction))*bs.length);
    ends[0][0] = endx + xvar;
    ends[1][0] = endx;
    ends[0][1] = endy + yvar;
    ends[1][1] = endy;

    int sx = bs.x;
    int sx2 = bs.x + 4;
    int sx3 = bs.x - 4;
    int sy = bs.y;
    int exoff = (int)(Math.cos(Math.toRadians(bs.direction))*bs.length);
    for(int i = 0; i < 2; i++){
      g.drawLine(starts[i][0],starts[i][1],ends[i][0],ends[i][1]);
    }
  }

  public void drawStationTrack(Graphics g, BlockStation dti, Color c){
    BlockStation bs = dti;
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

  public void drawSegment(Graphics g, BlockInterface dti, Color c){
    g.setColor(c);
    if(dti instanceof BlockCurved){
      BlockCurved dtc = (BlockCurved)dti;
      g.drawArc(dtc.x,dtc.y,dtc.radius,dtc.radius,dtc.startang,dtc.endang);

      //System.out.println("Found curved track");
    }
    else if(dti instanceof BlockStraight){
      //System.out.println("Found straight track");
      drawStraightTrack(g,(BlockStraight)dti,c);
    }
    else if(dti instanceof BlockSwitch){


      BlockSwitch ds = (BlockSwitch)dti;
      Color cn = c.darker();
      if(ds.flipped){
        drawSegment(g,ds.head,c);
        drawSegment(g,ds.divergent,cn);
      }else{
        drawSegment(g,ds.head,cn);
        drawSegment(g,ds.divergent,c);
      }
      drawSegment(g,ds.tail,c);
    }
    else if(dti instanceof BlockStation){
      drawStationTrack(g,(BlockStation)dti,c);
    }
    else{
      System.out.println("Did not understand kind of track!");
    }
  }

  public Color getColorState(Graphics g, BlockInterface track){
    if(track.getFailState() == null){
      //g.setColor(Color.white);
      System.out.println("Found a track with null failstate:");
      return Color.white;
      //System.out.println("Got to null TrackPane.java:151");
    }
    switch(track.getFailState()){
      case FS_NORMAL:
        return Color.green;
      case FS_BROKEN_RAIL:
        return Color.gray;
      case FS_POWER_FAILURE:
        return Color.yellow;
      case FS_TRACK_CIRCUIT_FAILURE:
        return Color.red;
      default:
        return Color.blue;
    }
  }
}
