
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DetailPane extends JPanel{

  public DetailPane(ArrayList<DummyLine> aldl){
    super();
    JPanel lh = new JPanel();
    JPanel rh = new JPanel();
    lh.setLayout(new BoxLayout(lh,BoxLayout.Y_AXIS));
    rh.setLayout(new BoxLayout(rh,BoxLayout.Y_AXIS));
    setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
    SchedulePane sp = new SchedulePane();
    LineVisPanel lp = new LineVisPanel(aldl);
    TrainDetailPane tp = new TrainDetailPane();
    add(lh);
    add(rh);
    lh.add(sp);
    lh.add(lp);
    lh.add(tp);
  }
  /*
  public void paint(Graphics g){
    Dimension size = getSize();
    g.setColor(Color.green);
    Dimension d = getSize();
    //g.fillRect(0,0,d.width,d.height);
  }
  */
}
