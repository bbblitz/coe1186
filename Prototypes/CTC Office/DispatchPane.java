import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DispatchPane extends JPanel{

  public DispatchPane(){
    JLabel l = new JLabel("Dispatch:");
    JLabel trainname = new JLabel("Train:");
    JTextField station = new JTextField();
    JButton dispatchbutton = new JButton("Set");
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    add(l);
    add(trainname);
    add(station);
    add(dispatchbutton);
  }
}
