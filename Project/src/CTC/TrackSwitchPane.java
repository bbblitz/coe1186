/*The pane used to close sections of track*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrackSwitchPane extends JPanel implements ActionListener {
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
    closebutton.addActionListener(this);
    openbutton.addActionListener(this);
    int i = 0;
    for(BlockInterface bi : c.aldl.get(0).blocks){
      trackselector.addItem(new Integer(bi.getID()));
    }
    /*
    for(BlockInterface bi : c.aldl.get(1).blocks){
      trackselector.addItem(new JLabel("" + i++));
    }
    */

    JLabel l = new JLabel("Track Closeing:");
    add(l);
    add(trackselector);
    add(closebutton);
    add(openbutton);
    this.c = c;
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == closebutton){
      int block = (Integer)trackselector.getSelectedItem();
      c.lineController.setClosed(block,true);
    }
    if(e.getSource() == openbutton){
      int block = (Integer)trackselector.getSelectedItem();
      c.lineController.setClosed(block,false);
    }
  }
}
