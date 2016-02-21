import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class LineVisPanel extends JPanel implements ItemListener{
  ArrayList<Checkbox> alc = new ArrayList<Checkbox>();
  Config config;
  public LineVisPanel(Config config){
    super();
    this.config = config;
    JLabel lm = new JLabel("Line Visibility:");
    add(lm);
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    for(Line l : config.aldl){
      Checkbox c = new Checkbox(l.name,true);
      c.addItemListener(this);
      alc.add(l.lineid,c);
      add(c);
    }
  }

  public void itemStateChanged(ItemEvent e) {
    Object source = e.getItemSelectable();
    System.out.println("Checkbox ticked");
    for(int i = 0; i < alc.size(); i++){
      if(source == alc.get(i)){
        System.out.println("Checkbox:" + config.aldl.get(i).lineid);
        config.vislines.set(i,((java.awt.Checkbox)source).getState());
        config.trackpane.repaint();
      }
    }
    /*
    if (source == chinButton) {
        index = 0;
        c = 'c';
    } else if (source == glassesButton) {
        index = 1;
        c = 'g';
    } else if (source == hairButton) {
        index = 2;
        c = 'h';
    } else if (source == teethButton) {
        index = 3;
        c = 't';
    }

    //Now that we know which button was pushed, find out
    //whether it was selected or deselected.
    if (e.getStateChange() == ItemEvent.DESELECTED) {
        c = '-';
    }

    //Apply the change to the string.
    choices.setCharAt(index, c);

    updatePicture();
    */
  }
}
