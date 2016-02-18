/*Holds are the stuff on the right side of the window*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DetailPane extends JPanel{

  public DetailPane(Config config){
    super();
    JPanel lh = new JPanel();
    JPanel rh = new JPanel();
    Dimension d = getSize();
    Dimension paneDim = new Dimension((int)(1200*0.2),(int)(1200*0.2));

    lh.setLayout(new BoxLayout(lh,BoxLayout.Y_AXIS));
    rh.setLayout(new BoxLayout(rh,BoxLayout.Y_AXIS));
    setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
    SchedulePane sp = new SchedulePane();
    LineVisPanel lp = new LineVisPanel(config);
    TrainDetailPane tp = new TrainDetailPane();
    DispatchPane dp = new DispatchPane();
    MessageLogPane mp = new MessageLogPane();
    TrackSwitchPane wp = new TrackSwitchPane(config);

    ((JPanel)wp).setPreferredSize(paneDim);
    ((JPanel)dp).setPreferredSize(paneDim);
    ((JPanel)mp).setPreferredSize(paneDim);
    ((JPanel)lp).setPreferredSize(paneDim);
    ((JPanel)tp).setPreferredSize(paneDim);
    ((JPanel)sp).setPreferredSize(paneDim);

    Dimension hsize = new Dimension((int)(1200/2),600);
    lh.setPreferredSize(hsize);
    rh.setPreferredSize(hsize);
    System.out.printf("Attempting to set prefered size to (%d,%d)\n",d.width,d.height);

    JLabel n1 = new JLabel("Test1");
    JLabel n2 = new JLabel("Test2");

    add(lh);
    add(rh);

    lh.add(n1);
    rh.add(n2);

    lh.add(sp);
    lh.add(lp);
    lh.add(tp);
    rh.add(dp);
    rh.add(mp);
    rh.add(wp);


    //add(wp);
  }

  public void createComponent(){
    Dimension d = getSize();
    System.out.printf("After prefered size to (%d,%d)\n",d.width,d.height);
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
