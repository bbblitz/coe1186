/*The pane used to flip the direction of a switch on the track*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrackSwitchPane extends JPanel implements MouseListener {
  public Config c;
  public JComboBox trackselector;
  public JButton closebutton;
  public JButton openbutton;
  public TrackSwitchPane(Config c){
    super();
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    trackselector = new JComboBox();
    closebutton = new JButton("Close");
    openbutton = new JButton("Open");
    int i = 0;
    for(BlockInterface bi : c.aldl.get(0).blocks){
      trackselector.add(new JLabel("Something" + i++));
    }
    /*
    for(BlockInterface bi : c.aldl.get(1).blocks){
      trackselector.add(new JLabel("" + i++));
    }
    */
    JLabel l = new JLabel("Track Closeing:");
    add(l);
    add(trackselector);
    add(closebutton);
    add(openbutton);
    addMouseListener(this);
    this.c = c;
  }

  public void mousePressed(MouseEvent e) {
   System.out.println("Mouse pressed; # of clicks: " + e.getClickCount());
  }

  public void mouseReleased(MouseEvent e) {
    System.out.println("Mouse released; # of clicks: " + e.getClickCount());
  }

  public void mouseEntered(MouseEvent e) {
    System.out.println("Mouse entered");
  }

  public void mouseExited(MouseEvent e) {
    System.out.println("Mouse exited");
  }

  public void mouseClicked(MouseEvent e) {
     System.out.println("Mouse clicked (# of clicks: " + e.getClickCount() + ")");
  }
}
