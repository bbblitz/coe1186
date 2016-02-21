/*The pane used to flip the direction of a switch on the track*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrackSwitchPane extends JPanel implements MouseListener {
  public Config c;
  public TrackSwitchPane(Config c){
    super();
    JLabel l = new JLabel("Track Switch:");
    add(l);
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

  public int findoffx(DummyTrackInterface ds){
    if(ds instanceof DummyTrackStraight){
      return (Math.cos(ds.direction)*ds.length) + ds.x;
    }else if(ds instanceof DummyTrackCurved){
      
    }
  }

  public void findoffy(DummyTrackInterface ds){

  }

  /**
   * @override
   */
  public void paint(Graphics g){
    System.out.println("Selected from TSP: " + c.selected);
    g.setColor(Color.black);
    Dimension d = getSize();
    g.fillRect(0,0,d.width,d.height);
    if(this.c.selected instanceof DummySwitch){
      //Find the top-left most corner
    }
  }
}
