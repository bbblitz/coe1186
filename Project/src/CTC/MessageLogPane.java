
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.*;

public class MessageLogPane extends JPanel{
  JTextArea ml;

  public MessageLogPane(Config c){
    super();
    JLabel l = new JLabel("Message log:");
    ml = new JTextArea(10,10);
    ml.setEditable(false);
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    add(l);
    add(ml);
    log("Message Log Started");
    c.log = ml;
  }

  public void log(String message){
    ml.append(message /*+ " (" + LocalDateTime.now() + ")"*/);
  }
}
