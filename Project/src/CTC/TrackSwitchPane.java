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

  public int findoffx(BlockInterface ds){
    if(ds instanceof BlockStraight){
      BlockStraight dts = (BlockStraight)ds;
      return (int)((Math.cos(dts.direction)*dts.length) + dts.x);
    }else if(ds instanceof BlockCurved){
      BlockCurved dtc = (BlockCurved)ds;
      int startang = dtc.startang;
      int endang = startang + dtc.endang;
      boolean startisleftmost = true;
      if(Math.cos(endang) < Math.cos(startang)){
        startisleftmost = false;
      }
      int output = (int)(Math.cos(startisleftmost?startang:endang)*dtc.radius);
      System.out.println("Returning " + output);
      return output;
    }else if(ds instanceof BlockSwitch){
      BlockSwitch dds = (BlockSwitch)ds;
      return (int) Math.min(
        Math.min(
          findoffx(dds.head),
          findoffx(dds.tail)
        ),
        findoffx(dds.divergent)
      );
    }
    return 0;
  }

  public void findoffy(BlockInterface ds){

  }

  /**
   * @override
   */
  public void paint(Graphics g){
    System.out.println("Selected from TSP: " + c.selected);
    g.setColor(Color.black);
    Dimension d = getSize();
    g.fillRect(0,0,d.width,d.height);
    if(this.c.selected instanceof BlockSwitch){
      //Find the top-left most corner
    }
  }
}
