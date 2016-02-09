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
  public void paint(Graphics2D g){
    g.fillRect(0,0,200,200);
  }
}
