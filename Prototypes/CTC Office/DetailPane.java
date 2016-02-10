
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DetailPane extends JPanel{

  public DetailPane(){
    super();
  }
  public void paint(Graphics g){
    Dimension size = getSize();
    g.setColor(Color.green);
    Dimension d = getSize();
    g.fillRect(0,0,d.width,d.height);
  }
}
