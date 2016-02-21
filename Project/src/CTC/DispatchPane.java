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

    // dispatch a dummy train
    int id = 15;
    BlockInterface currentBlock = config.aldl.get(0).line.get(0);    // the first block in the red line is right outside the yard
    BlockInterface destination = config.aldl.get(0).line.get(7);    // go to the 7th block in the red line
    long targetTime = System.currentTimeMillis() + 240000;  // 4 minutes from now
    Route newRoute = new Route(config, destination, targetTime);
    Train newTrain = new Train(id, currentBlock, newRoute);
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == dispatchbutton){
      //Do the dispatch pane
    }
  }


}
