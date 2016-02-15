import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class LineVisPanel extends JPanel implements ItemListener{
  public LineVisPanel(Config config){
    super();
    JLabel lm = new JLabel("Line Visibility:");
    add(lm);
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    for(DummyLine l : config.aldl){
      add(new Checkbox(l.name,true));
    }
  }

  public void itemStateChanged(ItemEvent e) {
    int index = 0;
    char c = '-';
    Object source = e.getItemSelectable();
    System.out.println("Checkbox ticked");
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
