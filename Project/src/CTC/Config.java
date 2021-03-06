import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Config{
  //Why dosen't java have #define? this is so inconvinent.
  public static final boolean DEBUG_PARSER = false;
  public static final boolean DEBUG_COMMUNICATION = false;
  public static final boolean DEBUG_SCHEDUAL = false;
  public static final boolean DEBUG_DRAWING = false;
  public static final int CONSTANT_SPEED = 40;
  public static final int SEGMENTATION_LENGTH = 10;


  //The red line is the first line in aldl, the green line is the second.
  public ArrayList<Line> aldl;
  public ArrayList<Train> alltrains = new ArrayList<Train>();
  public ArrayList<Train> greenLineTrains = new ArrayList<Train>();
  public ArrayList<Train> redLineTrains = new ArrayList<Train>();
  public static int trainid = 0;
  //TODO:Delete this
  public ArrayList<Train> pinkLineTrains = new ArrayList<Train>();
  public LineController lineController;

  public long time;

  /*
   * UI STUFF
   */
  public Dimension windowDim;
  public ArrayList<Boolean> vislines;
  public JFrame window;
  public JPanel trackpane;
  public JPanel switchpane;
  public JPanel schedulepane;
  public JPanel dispatchpane;
  //Use log.append to add stuff to the log
  public JTextArea log;
  public Object selected;

  public Config(){
    vislines = new ArrayList<Boolean>();
    aldl = new ArrayList<Line>();
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
