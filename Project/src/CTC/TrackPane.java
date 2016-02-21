/**The black panel that draws the track on the left of the CTC interface*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TrackPane extends JPanel implements MouseListener {
  ArrayList<DummyLine> lines;
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
    System.out.printf("Looking for switches at (%d,%d)\n", e.getX(), e.getY());
    System.out.println("config.trackpane is: " + c.trackpane);
    for(int i = 0; i < lines.size(); i++){
      if(vislines.get(i)){
        for(DummyTrackInterface dti : lines.get(i).line){
          if(dti instanceof DummySwitch){
            DummySwitch dts = (DummySwitch)dti;
            System.out.printf("Found switch at (%d,%d)\n",dts.x,dts.y);
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
    for(Boolean b : vislines){
      System.out.print(b + "\t");
    }
    g.setColor(Color.black);
    Dimension d = getSize();
    g.fillRect(0,0,d.width,d.height);
    for(DummyLine dl : lines){
      if(vislines.get(dl.lineid)){//Check if this line should be visible
        drawLine(g,dl);
      }
    }
  }

  public void drawLine(Graphics g, DummyLine dl){
    for(DummyTrackInterface dti : dl.line){
      drawSegment(g,dti);
    }
  }

  public void drawSegment(Graphics g, DummyTrackInterface dti){
    Color c = getColorState(g,dti);
    drawSegment(g,dti,c);
  }

  public void drawInfrastructure(Graphics g, DummyTrackStraight track, Color c){

  }

  public void drawStraightTrack(Graphics g, DummyTrackStraight track, Color c){
    DummyTrackStraight ts = track;
    int sx = ts.x;
    int sy = ts.y;
    System.out.println("Cos of " + ts.direction + " is " + Math.cos(Math.toRadians(ts.direction)));
    int exoff = (int)(Math.cos(Math.toRadians(ts.direction))*ts.length);
    int eyoff = (int)(Math.sin(Math.toRadians(ts.direction))*ts.length);
    int ex = exoff+sx;
    int ey = eyoff+sy;
    System.out.printf("Line from (%d,%d)+(%d,%d) to (%d,%d)\n",sx,sy,exoff,eyoff,ex,ey);
    g.drawLine(sx,sy,ex,ey);
    ArrayList<Infrastructure> allinfra = track.getInfrastructure();
    if(allinfra == null) return;
    for(Infrastructure f : allinfra){

    }
  }

  public void drawSegment(Graphics g, DummyTrackInterface dti, Color c){
    g.setColor(c);
    if(dti instanceof DummyTrackCurved){
      DummyTrackCurved dtc = (DummyTrackCurved)dti;
      g.drawArc(dtc.x,dtc.y,dtc.radius,dtc.radius,dtc.startang,dtc.endang);

      //System.out.println("Found curved track");
    }
    else if(dti instanceof DummyTrackStraight){
      //System.out.println("Found straight track");
      drawStraightTrack(g,(DummyTrackStraight)dti,c);
    }
    else if(dti instanceof DummySwitch){


      DummySwitch ds = (DummySwitch)dti;
      Color cn = c.darker();
      if(ds.fliped){
        drawSegment(g,ds.head,c);
        drawSegment(g,ds.divergent,cn);
      }else{
        drawSegment(g,ds.head,cn);
        drawSegment(g,ds.divergent,c);
      }
      drawSegment(g,ds.tail,c);
    }
  }

  public Color getColorState(Graphics g, DummyTrackInterface track){
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
