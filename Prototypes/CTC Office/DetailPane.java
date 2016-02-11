
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DetailPane extends JPanel{

  public DetailPane(ArrayList<DummyLine> aldl){
    super();
    JPanel lh = new JPanel();
    JPanel rh = new JPanel();
    Dimension d = getSize();
    Dimension paneDim = new Dimension((int)(d.width*0.5),(int)(d.width*0.5));

    lh.setLayout(new BoxLayout(lh,BoxLayout.Y_AXIS));
    rh.setLayout(new BoxLayout(rh,BoxLayout.Y_AXIS));
    setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
    SchedulePane sp = new SchedulePane();
    LineVisPanel lp = new LineVisPanel(aldl);
    TrainDetailPane tp = new TrainDetailPane();
    DispatchPane dp = new DispatchPane();
    MessageLogPane mp = new MessageLogPane();
    TrackSwitchPane wp = new TrackSwitchPane();
    /*
    ((JPanel)wp).setPreferredSize(paneDim);
    ((JPanel)dp).setPreferredSize(paneDim);
    ((JPanel)mp).setPreferredSize(paneDim);
    ((JPanel)lp).setPreferredSize(paneDim);
    ((JPanel)tp).setPreferredSize(paneDim);
    ((JPanel)sp).setPreferredSize(paneDim);
    lh.setPreferredSize(new Dimension((int)(d.width*0.5),d.height));
    rh.setPreferredSize(new Dimension((int)(d.width*0.5),d.height));
    */
    add(lh);
    add(rh);
    lh.add(sp);
    lh.add(lp);
    lh.add(tp);
    rh.add(dp);
    rh.add(mp);
    rh.add(wp);

    //add(wp);
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
