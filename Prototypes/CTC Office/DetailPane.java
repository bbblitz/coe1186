
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DetailPane extends JPanel{

  public DetailPane(ArrayList<DummyLine> aldl){
    super();
  }
  public void paint(Graphics g){
    Dimension size = getSize();
    g.setColor(Color.green);
    Dimension d = getSize();
    g.fillRect(0,0,d.width,d.height);
  }
}
