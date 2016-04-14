import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LineController{
  Config c;
  JTextArea log;
  JCheckBox occupied[];
  JComboBox failstate[];
  static final int numblocks = 14;
  public LineController(Object trackmodelshouldbehere){

    JFrame frame = new JFrame("Dummy TrackController window");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    c = new Config();
    LineParser parser = new LineParser("track.txt",c);
    String[] columnnames = {"Block ID","Occupied","Fail States","Switched?"};
    JPanel allrows = new JPanel();
    allrows.setLayout(new BoxLayout(allrows,BoxLayout.Y_AXIS));
    occupied = new JCheckBox[numblocks];
    failstate = new JComboBox[numblocks];
    for(BlockInterface bi : c.aldl.get(0).blocks){
      if(bi == null){continue;}
      JPanel l = new JPanel();
      l.setLayout(new BoxLayout(l,BoxLayout.X_AXIS));
      JLabel id = new JLabel("BlockID:" + bi.getID());
      occupied[bi.getID()] = new JCheckBox("Occupied");
      String[] failstates = {"FS_NORMAL","FS_BROKEN_RAIL","FS_POWER_FAILURE","FS_TRACK_CIRCUIT_FAILURE"};
      failstate[bi.getID()] = new JComboBox(failstates);
      l.add(id);
      l.add(occupied[bi.getID()]);
      l.add(failstate[bi.getID()]);
      //JLabel l = new JLabel(bi.toString());
      allrows.add(l);
      System.out.println("LC block:");
      System.out.println(bi.toString());
    }
    log = new JTextArea();
    log.append("Message log started\n");
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
    boolean[] output = new boolean[numblocks];
    for(BlockInterface bi : c.aldl.get(0).blocks){
      if(bi == null) continue;
      output[bi.getID()] = occupied[bi.getID()].isSelected();
    }
    //output[10] = true;
    return output;
  }

  public TrackFailState getFailStateFromString(String fsstr){
    if(fsstr.equals("FS_NORMAL")){
      return TrackFailState.FS_NORMAL;
    }else if(fsstr.equals("FS_BROKEN_RAIL")){
      return TrackFailState.FS_BROKEN_RAIL;
    }else if(fsstr.equals("FS_POWER_FAILURE")){
      return TrackFailState.FS_POWER_FAILURE;
    }else if(fsstr.equals("FS_TRACK_CIRCUIT_FAILURE")){
      return TrackFailState.FS_TRACK_CIRCUIT_FAILURE;
    }
    return TrackFailState.FS_NORMAL;
  }

  public TrackFailState[] getBlockFailStates(){
    TrackFailState[] output = new TrackFailState[numblocks];
    for(BlockInterface bi : c.aldl.get(0).blocks){
      if(bi == null) continue;
      String fsstr = (String) failstate[bi.getID()].getSelectedItem();
      TrackFailState f = getFailStateFromString(fsstr);


      output[bi.getID()] = f;
    }
    return output;
  }

  public void relayAuthority(int authority, int blockID){
    //System.out.printf("Got authority:%d meters to be sent to blockid:%d",authority, blockID);
    log.append(String.format("Got authority:%d to be sent to blockid:%d\n",authority,blockID));
  }

  public void relaySpeed(int speed, int blockID){
    log.append(String.format("Got speed:%d to be sent to blockid:%d\n",speed,blockID));
    //System.out.printf("Got speed:%d m/s to be sent to blockid:%d",speed,blockID);
  }

  //True if next train to pass the yard needs to go into the yard
  //Blockid should be either the green line block or the red line block.
  public void routeToYard(boolean in, int redline){
    log.append(String.format("Route next train into yard?:%s, which line?:%d\n",in?"true":"false",redline));
  }

  public void createTrain(int line){
    log.append(String.format("A train should be created on line:%d",line==0?"red":"green"));
  }

}
