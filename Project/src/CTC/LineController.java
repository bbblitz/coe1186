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
    boolean[] output = new boolean[14];
    //output[10] = true;
    return output;
  }

  public void relayAuthority(int authority, int blockID){
    System.out.printf("Got authority:%d meters to be sent to blockid:%d",authority, blockID);
  }
  public void relaySpeed(int speed, int blockID){
    System.out.printf("Got speed:%d m/s to be sent to blockid:%d",speed,blockID);
  }

}
