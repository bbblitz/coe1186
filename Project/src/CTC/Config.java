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
  public Object selected;
  public Config(){
    //vislines = new ArrayList<Boolean>();
  }
 
  // given a station name, get the block it's on
  public BlockInterface getBlockFromStationName(String stationName) {
    for (Line line : this.aldl) {
      for (BlockInterface block : line.blocks) {
        if (block.infra.contains(Infrastructure.INFR_STATION)) {
          if (block.stationName == stationName) {
            return block;
          }
        }
      }
    }
    return null;
  }
}
