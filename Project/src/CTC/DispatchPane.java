import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DispatchPane extends JPanel implements ActionListener{
  Config config;
  JButton dispatchbutton;
  ArrayList<BlockStation> stations = new ArrayList<BlockStation>();
  ArrayList<Integer> trains = new ArrayList<Integer>();
  public DispatchPane(Config c) {
    config = c;
    JComboBox trainlist = new JComboBox();
    for(Integer i : trains){
      trainlist.addItem("Train #" + i);
    }
    JComboBox blocklist = new JComboBox();
    for(Line l : config.aldl){
      System.out.println("Found a line: " + l.name);
    }
    for(BlockInterface bi : config.aldl.get(0).blocks){
      if(bi != null){
        blocklist.addItem("Block #" + bi.getID());
      }
    }
    JLabel l = new JLabel("Dispatch:");
    JLabel trainname = new JLabel("Train:");
    JTextField station = new JTextField();
    dispatchbutton = new JButton("Set");
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    add(l);
    add(trainname);
    add(trainlist);
    add(station);
    add(dispatchbutton);

    // dummy values
    /*
    int id = 15;
    BlockInterface currentBlock = config.aldl.get(0).blocks.get(0);    // the first block in the red line is right outside the yard
    BlockInterface destinationBlock = config.aldl.get(0).blocks.get(7);    // go to the 7th block in the red line
    long targetTime = System.currentTimeMillis() + 240000;  // 4 minutes from now

    // dispatch a dummy train
    Train newTrain = new Train(config, id, currentBlock, destinationBlock, targetTime);
    */
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == dispatchbutton){
      //Do the dispatch pane
    }
  }


}
