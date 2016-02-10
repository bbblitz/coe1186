import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrackPane extends JPanel{
  DummyLine line;
  public TrackPane(DummyLine l){
    super();
    line = l;
  }
  public TrackPane(boolean db){
    super(db);
  }
  public TrackPane(LayoutManager lm){
    super(lm);
  }
  public TrackPane(LayoutManager lm, boolean db){
    super(lm,db);
  }
  public void tick(){
    System.out.println("I've ticked!");
  }
  /**
   * @override
   */
  public void paint(Graphics g){
    g.setColor(Color.black);
    Dimension d = getSize();
    g.fillRect(0,0,d.width,d.height);
  }
}
