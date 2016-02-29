import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Config{
  //The red line is the first line in aldl, the green line is the second.
  public ArrayList<Line> aldl;
  public ArrayList<Train> greenLineTrains;
  public ArrayList<Train> redLineTrains;

  public TrackControllerManager trackControllerManager;


  /*
   * UI STUFF
   */
  public Dimension windowDim;
  public ArrayList<Boolean> vislines;
  public JFrame window;
  public JPanel trackpane;
  public JPanel switchpane;
  //Use log.append to add stuff to the log
  public JTextArea log;
  public Object selected;


  public Config(){
    vislines = new ArrayList<Boolean>();
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

  public ArrayList<Train> getAllTrains() {
	  ArrayList<Train> allTrains = new ArrayList<Train>();

	  for (Train train : this.greenLineTrains) {
		  allTrains.add(train);

	  }

	  for (Train train : this.redLineTrains) {
		  allTrains.add(train);
	  }

	  return allTrains;
  }

  // TODO: implement this
  public BlockInterface getBlockFromTrackControllerOccupancyArray(int i) {
	  BlockInterface block = null;

	  return block;
  }

  // TODO: implement this
  public BlockSwitch getBlockFromTrackControllerSwitchArray(int i) {
	  BlockSwitch block = null;

	  return block;
  }

}
