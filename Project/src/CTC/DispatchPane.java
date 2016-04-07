import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DispatchPane extends JPanel implements ActionListener{
  Config config;
  JButton dispatchbutton;
  ArrayList<BlockStation> stations = new ArrayList<BlockStation>();
  ArrayList<Integer> trains = new ArrayList<Integer>();
  JComboBox trainlist;
  JComboBox blocklist;
  public DispatchPane(Config c) {
    config = c;
    for(Train t : config.pinkLineTrains){
      trains.add(t.getID());
    }

    trainlist = new JComboBox();
    for(Integer i : trains){
      trainlist.addItem(new Integer(i));
    }
    blocklist = new JComboBox();
    for(BlockInterface bi : config.aldl.get(0).blocks){
      if(bi != null){
        blocklist.addItem(new Integer(bi.getID()));
      }
    }
    JLabel l = new JLabel("Dispatch:");
    JLabel trainname = new JLabel("Train:");
    JLabel blockname = new JLabel("Block:");
    JTextField station = new JTextField();
    dispatchbutton = new JButton("Dispatch");
    dispatchbutton.addActionListener(this);
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    add(l);
    add(trainname);
    add(trainlist);
    add(blockname);
    add(blocklist);
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

  public int calculateAuthority(int trainid, int blockid){
    int totallength = 0;
    Train train = config.pinkLineTrains.get(trainid);
    BlockInterface currentblock = train.getCurrentBlock();
    BlockInterface previousblock = train.getPreviousBlock();
    BlockInterface tmpblock = currentblock;
    while(tmpblock.getID() != blockid){
      BlockInterface tmp2 = tmpblock;
      tmpblock = tmpblock.goesto(previousblock);
      previousblock = tmp2;
      totallength += 50;
    }
    System.out.println("Calculated length to be:" + totallength);
    return totallength;
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == dispatchbutton){
      int blockid = (Integer)blocklist.getSelectedItem();
      int trainid = (Integer)trainlist.getSelectedItem();
      int authority = calculateAuthority(trainid, blockid);
      config.lineController.relayAuthority(authority,blockid);
      config.lineController.relaySpeed(10, blockid);
    }
  }


}
