import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LineController{

  public LineController(Object trackmodelshouldbehere){

    JFrame frame = new JFrame("Dummy TrackController window");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
  }

  public void tick(long delta){

  }

  public boolean[] getBlockOccupancies(){
    return new boolean[14];
  }

}
