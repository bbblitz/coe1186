import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TrackPane extends JPanel{
  ArrayList<DummyLine> lines;
  ArrayList<Boolean> vislines;
  public TrackPane(Config c){
    super();
    vislines = c.vislines;
    lines = c.aldl;
  }

  public void tick(){
    System.out.println("I've ticked!");
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

  public void drawSegment(Graphics g, DummyTrackInterface dti, Color c){
    g.setColor(c);
    if(dti instanceof DummyTrackCurved){
      DummyTrackCurved dtc = (DummyTrackCurved)dti;
      g.drawArc(dtc.x,dtc.y,dtc.radius,dtc.radius,dtc.startang,dtc.endang);

      //System.out.println("Found curved track");
    }
    else if(dti instanceof DummyTrackStraight){
      //System.out.println("Found straight track");
      DummyTrackStraight ts = (DummyTrackStraight)dti;
      int sx = ts.x;
      int sy = ts.y;
      System.out.println("Cos of " + ts.direction + " is " + Math.cos(Math.toRadians(ts.direction)));
      int exoff = (int)(Math.cos(Math.toRadians(ts.direction))*ts.length);
      int eyoff = (int)(Math.sin(Math.toRadians(ts.direction))*ts.length);
      int ex = exoff+sx;
      int ey = eyoff+sy;
      System.out.printf("Line from (%d,%d)+(%d,%d) to (%d,%d)\n",sx,sy,exoff,eyoff,ex,ey);
      g.drawLine(sx,sy,ex,ey);
    }
    else if(dti instanceof DummySwitch){


      DummySwitch ds = (DummySwitch)dti;
      Color ct = getColorState(g,dti);
      Color cn = c.darker();
      if(ds.fliped){
        drawSegment(g,ds.head,ct);
        drawSegment(g,ds.divergent,cn);
      }else{
        drawSegment(g,ds.head,cn);
        drawSegment(g,ds.divergent,ct);
      }
      /*
      int headstartoffx = 0;
      int headendoffx = 0;
      int headstartoffy = 0;
      int headendoffy = 0;
      headstartoffx = (int) (ds.length*0*Math.cos(ds.dir));
      headendoffx = (int) (ds.length*0.33*Math.cos(ds.dir));

      int switchstartoffx = 0;
      int switchstartoffy = 0;
      int switchendoffx = 0;
      int switchendoffy = 0;
      switchstartoffx = headendoffx;
      switchstartoffy = headendoffy;
      switchendoffx = (int)(ds.length*0.66*Math.cos(ds.dir));
      switchendoffy = (int)(10);

      int divstartoffx = 0;
      int divstartoffy = 0;
      int divendoffx = 0;
      int divendoffy = 0;
      divstartoffx = headendoffx;
      divstartoffy = headendoffy;
      divendoffx = (int)(ds.length*0.66*Math.cos(ds.dir));
      divendoffy = -(int)(10);
      */
      /*
      System.out.printf("Switch origin at (%d,%d)\n",ds.x,ds.y);
      System.out.printf("\tHead from (%d,%d) to (%d,%d)\n",headstartoffx,headstartoffy,headendoffx,headendoffy);
      System.out.printf("\tSwitch from (%d,%d) to (%d,%d)\n",switchstartoffx,switchstartoffy,switchendoffx,switchendoffy);
      System.out.printf("\tDivergent from (%d,%d) to (%d,%d)\n",divstartoffx,divstartoffy,divendoffx,divendoffy);
      */
      /*
      int hsx = ds.x + headstartoffx;
      int hex = ds.x + headendoffx;
      int hsy = ds.y + headstartoffy;
      int hey = ds.y + headendoffy;

      int ssx = ds.x + switchstartoffx;
      int sex = ds.x + switchendoffx;
      int ssy = ds.y + switchstartoffy;
      int sey = ds.y + switchendoffy;

      int dsx = ds.x + divstartoffx;
      int dex = ds.y + divstartoffy;
      int dsy = ds.x + divendoffx;
      int dey = ds.y + divendoffy;
      */
      /*
      System.out.printf("With offsets:\n");
      System.out.printf("\tHead from (%d,%d) to (%d,%d)\n",hsx,hsy,hex,hey);
      System.out.printf("\tSwitch from (%d,%d) to (%d,%d)\n",ssx,ssy,sex,sey);
      System.out.printf("\tDivergent from (%d,%d) to (%d,%d)\n",dsx,dsy,dex,dey);
      */
      /*
      g.drawLine(hsx, hsy, hex, hey);
      if(ds.fliped){
        g.setColor(Color.pink);
      }else{
        setColorState(g,ds);
      }
      g.drawLine(ssx, ssy, sex, sey);
      if(!ds.fliped){
        g.setColor(Color.pink);
      }else{
        setColorState(g,ds);
      }
      g.drawLine(dsx, dex, dsy, dey);
      //System.out.println("Found switch");
      */
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
