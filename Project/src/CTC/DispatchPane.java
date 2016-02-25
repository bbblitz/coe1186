import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DispatchPane extends JPanel implements ActionListener{
  Config config;
  JButton dispatchbutton;
  public DispatchPane(Config c) {
    JLabel l = new JLabel("Dispatch:");
    JLabel trainname = new JLabel("Train:");
    JTextField station = new JTextField();
    dispatchbutton = new JButton("Set");
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    add(l);
    add(trainname);
    add(station);
    add(dispatchbutton);
    config = c;

    // dummy values
    int id = 15;
    BlockInterface currentBlock = config.aldl.get(0).blocks.get(0);    // the first block in the red line is right outside the yard
    BlockInterface destinationBlock = config.aldl.get(0).blocks.get(7);    // go to the 7th block in the red line
    long targetTime = System.currentTimeMillis() + 240000;  // 4 minutes from now
    
    // dispatch a dummy train
    Train newTrain = new Train(id, currentBlock);
    BlockInterface to = destinationBlock;
    Route newRoute = new Route(config, newTrain, to, targetTime);
    newTrain.setRoute(newRoute);
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == dispatchbutton){
      //Do the dispatch pane
    }
  }


}
