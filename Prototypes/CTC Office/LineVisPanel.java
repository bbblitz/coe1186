import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class LineVisPanel extends JPanel{
  public LineVisPanel(ArrayList<DummyLine> aldl){
    super();
    for(DummyLine l : aldl){
      add(new Checkbox(l.name,true));
    }
  }
}
