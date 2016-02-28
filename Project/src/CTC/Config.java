import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Config{
  public Dimension windowDim;
  //The red line is the first line in aldl, the green line is the second.
  public ArrayList<Line> aldl;
  public ArrayList<Boolean> vislines;
  public JFrame window;
  public JPanel trackpane;
  public JPanel switchpane;
  //Use log.append to add stuff to the log
  public JTextArea log;
  public Object selected;
  
  public ArrayList<Train> greenLineTrains;
  public ArrayList<Train> redLineTrains;
  
  public Config(){
    //vislines = new ArrayList<Boolean>();
  }

  // given a station name, get the block it's on
  public BlockInterface getBlockFromStationName(String stationName) {
    for (Line line : this.aldl) {
      for (BlockInterface block : line.blocks) {
    	  if (block instanceof BlockStation) {
    		  BlockStation station = (BlockStation) block;
    		  if (station.getStationName() == stationName) {
    			  return block;
    		  }
    	  }
      }
    }
    // station not found :(
    return null;
  }
  
  public Line getRedLine() {
	  return aldl.get(0);
  }
  
  public Line getGreenLine() {
	  return aldl.get(1);
  }
  
}
