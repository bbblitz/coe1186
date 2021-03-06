/*
 * A prototype for the CTC office module
 * Creates the CTC office window
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class main{
  static CTCWindow ctc;
  public static void main(String[] args){
    LineController lc = makeDummyTrackController();
    ctc = new CTCWindow(lc);

    java.util.Timer timer = new java.util.Timer();
    timer.scheduleAtFixedRate(new Ticker(), 0, 100);
  }

  public static LineController makeDummyTrackController(){
    return new LineController(null);
  }

  static class Ticker extends TimerTask {
    public void run() {
      for(int i = 0; i < 1; i++) {
        ctc.tick(100);
      }
    }
  }
}
