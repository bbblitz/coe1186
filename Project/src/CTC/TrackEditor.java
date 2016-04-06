/*Track editor, should not be in final project
 *TODO: Remove me!
 *I was going mad doing this by hand
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class TrackEditor implements ActionListener{
  static JPanel tp;
  static Config config;
  static JTextArea ta;
  static JButton rb;
  static PrintWriter out;
  public static void main(String[] args){
    JFrame window = new JFrame("Track Editor");
    config = new Config();
    config.windowDim = new Dimension(1200,600);
    window.setSize(config.windowDim.width,config.windowDim.height);
    JPanel holder = new JPanel();
    holder.setLayout(new BoxLayout(holder,BoxLayout.X_AXIS));
    JPanel rs = new JPanel();
    rs.setLayout(new BoxLayout(rs,BoxLayout.Y_AXIS));
    LineParser lp = new LineParser("tmp.txt",config);
    tp = new TrackPane(config);
    ta = new JTextArea();
    rb = new JButton("Refresh");
    config.aldl = new ArrayList<Line>();
    try{
      out = new PrintWriter(new BufferedWriter(new FileWriter("tmp.txt")));
    }catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    rs.add(ta);
    rs.add(rb);
    holder.add(tp);
    holder.add(rs);
    window.add(holder);
    window.pack();
    window.setVisible(true);
  }
  public static void refresh(){
    LineParser lp = new LineParser("tmp.txt",config);
    tp.repaint();
  }
  public void actionPerformed(ActionEvent e){
    if(e.getSource() == rb){
      refresh();
    }
  }
}
