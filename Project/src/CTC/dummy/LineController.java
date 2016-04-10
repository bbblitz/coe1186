import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LineController{
  Config c;
  JTextArea log;
  public LineController(Object trackmodelshouldbehere){

    JFrame frame = new JFrame("Dummy TrackController window");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    c = new Config();
    LineParser parser = new LineParser("track.txt",c);
    String[] columnnames = {"Block ID","Occupied","Fail States","Switched?"};
    JPanel allrows = new JPanel();
    allrows.setLayout(new BoxLayout(allrows,BoxLayout.Y_AXIS));
    for(BlockInterface bi : c.aldl.get(0).blocks){
      if(bi == null){continue;}
      JPanel l = new JPanel();
      l.setLayout(new BoxLayout(l,BoxLayout.X_AXIS));
      JLabel id = new JLabel("BlockID:" + bi.getID());
      JCheckBox occupied = new JCheckBox("Occupied");
      String[] failstates = {"FS_NORMAL","FS_BROKEN_RAIL","FS_POWER_FAILURE","FS_TRACK_CIRCUIT_FAILURE"};
      JComboBox failstate = new JComboBox(failstates);
      l.add(id);
      l.add(occupied);
      l.add(failstate);
      //JLabel l = new JLabel(bi.toString());
      allrows.add(l);
      System.out.println("LC block:");
      System.out.println(bi.toString());
    }
    log = new JTextArea();
    log.append("Test");
    allrows.add(log);
    JScrollPane scrollPane = new JScrollPane(allrows);
    frame.add(scrollPane);
    //frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);

  }

  public void tick(long delta){

  }

  public boolean[] getBlockOccupancies(){
    boolean[] output = new boolean[14];
    for(BlockInterface bi : c.aldl.get(0).blocks){
      if(bi == null) continue;
      output[bi.getID()] = bi.getOccupied();
    }
    //output[10] = true;
    return output;
  }

  public TrackFailState[] getBlockFailStates(){
    TrackFailState[] output = new TrackFailState[14];
    for(BlockInterface bi : c.aldl.get(0).blocks){
      if(bi == null) continue;
      output[bi.getID()] = bi.getFailState();
    }
    return output;
  }

  public void relayAuthority(int authority, int blockID){
    //System.out.printf("Got authority:%d meters to be sent to blockid:%d",authority, blockID);
    log.append(String.format("Got authority:%d to be sent to blockid:%d"));
  }

  public void relaySpeed(int speed, int blockID){
    log.append(String.format("Got speed:%d to be sent to blockid:%d"));
    //System.out.printf("Got speed:%d m/s to be sent to blockid:%d",speed,blockID);
  }

  //True if next train to pass the yard
  public void routeToYard(boolean in, int blockID){
    


  }

}
