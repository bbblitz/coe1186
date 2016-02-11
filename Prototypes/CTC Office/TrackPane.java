import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TrackPane extends JPanel{
  ArrayList<DummyLine> lines;
  public TrackPane(ArrayList<DummyLine> l){
    super();
    lines = l;
  }
  public TrackPane(boolean db){
    super(db);
  }
  public TrackPane(LayoutManager lm){
    super(lm);
  }
  public TrackPane(LayoutManager lm, boolean db){
    super(lm,db);
  }
  public void tick(){
    System.out.println("I've ticked!");
  }
  /**
   * @override
   */
  public void paint(Graphics g){
    g.setColor(Color.black);
    Dimension d = getSize();
    g.fillRect(0,0,d.width,d.height);
    for(DummyLine dl : lines){
      if(true){//Check if this line should be visible
        drawLine(g,dl);
      }
    }
  }

  public void drawLine(Graphics g, DummyLine dl){
    for(DummyTrackInterface dti : dl.line){
      setColorState(g,dti);
      if(dti instanceof DummyTrackCurved){
        DummyTrackCurved dtc = (DummyTrackCurved)dti;
        g.drawArc(dtc.x,dtc.y,dtc.radius,dtc.radius,dtc.startang,dtc.endang);

        //System.out.println("Found curved track");
      }
      else if(dti instanceof DummyTrackStraight){
        //System.out.println("Found straight track");
      }
      else if(dti instanceof DummySwitch){


        DummySwitch ds = (DummySwitch)dti;

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

        /*
        System.out.printf("Switch origin at (%d,%d)\n",ds.x,ds.y);
        System.out.printf("\tHead from (%d,%d) to (%d,%d)\n",headstartoffx,headstartoffy,headendoffx,headendoffy);
        System.out.printf("\tSwitch from (%d,%d) to (%d,%d)\n",switchstartoffx,switchstartoffy,switchendoffx,switchendoffy);
        System.out.printf("\tDivergent from (%d,%d) to (%d,%d)\n",divstartoffx,divstartoffy,divendoffx,divendoffy);
        */
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
        /*
        System.out.printf("With offsets:\n");
        System.out.printf("\tHead from (%d,%d) to (%d,%d)\n",hsx,hsy,hex,hey);
        System.out.printf("\tSwitch from (%d,%d) to (%d,%d)\n",ssx,ssy,sex,sey);
        System.out.printf("\tDivergent from (%d,%d) to (%d,%d)\n",dsx,dsy,dex,dey);
        */
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
      }
    }
  }

  public void setColorState(Graphics g, DummyTrackInterface track){
    if(track.getFailState() == null){
      g.setColor(Color.white);
      return;
    }
    switch(track.getFailState()){
      case FS_NORMAL:
        g.setColor(Color.green);
        break;
      case FS_BROKEN_RAIL:
        g.setColor(Color.gray);
        break;
      case FS_POWER_FAILURE:
        g.setColor(Color.yellow);
        break;
      case FS_TRACK_CIRCUIT_FAILURE:
        g.setColor(Color.red);
        break;
    }
  }
}
