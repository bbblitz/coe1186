/*The pane used to flip the direction of a switch on the track*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrackSwitchPane extends TrackPane implements ActionListener{

  public TrackSwitchPane(Config c){
    super(c);
    JLabel l = new JLabel("Track Switch:");
    add(l);
  }

  public void actionPerformed(ActionEvent e){
    System.out.println("Action!");
  }
}
