import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TrainDetailPane extends JPanel{

  public TrainDetailPane(){
    super();
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    JLabel trainname = new JLabel("Name:");
    JLabel throughput = new JLabel("Throughput:");
    JLabel passengers = new JLabel("Passengers:");
    JLabel speed = new JLabel("Speed:");
    JLabel authority = new JLabel("Authority:");
    /*
    add(trainname);
    add(throughput);
    add(passengers);
    add(speed);
    add(authority);
    */
  }
}
