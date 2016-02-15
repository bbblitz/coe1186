import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrackSwitchPane extends JPanel{

  public TrackSwitchPane(){
    super();
    JLabel l = new JLabel("Track Switch:");
    add(l);
  }

  public void paint(Graphics g){
    g.setColor(Color.black);
    Dimension d = getSize();
    g.fillRect(0,0,d.width,d.height);
    System.out.printf("Size is (%d,%d)",d.width,d.height);
  }
}
