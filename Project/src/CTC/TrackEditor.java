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
  public TrackEditor(){
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
    File f = new File("tmp.txt");
    Scanner s = null;
    try{
      s = new Scanner(f);
    }catch(Exception e){
      System.out.println(e);
    }
    String initstring = "";
    while(s.hasNextLine()){
      initstring += s.nextLine() + "\n";
    }
    ta = new JTextArea(initstring);
    rb = new JButton("Refresh");
    rb.addActionListener(this);
    config.aldl = new ArrayList<Line>();
    try{
      out = new PrintWriter(new BufferedWriter(new FileWriter("tmp.txt")));
    }catch(Exception e){
      System.out.println(e);
      System.exit(0);
    }
    Dimension tracksize = new Dimension(800,600);
    Dimension textsize = new Dimension(400,600);
    ta.setPreferredSize(textsize);
    JScrollPane sta = new JScrollPane(ta);
    tp.setPreferredSize(tracksize);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    rs.add(sta);
    rs.add(rb);
    holder.add(tp);
    holder.add(rs);
    window.add(holder);
    window.pack();
    window.setVisible(true);
  }

  public static void main(String[] args){
    TrackEditor te = new TrackEditor();
  }
  public static void refresh(){
    System.out.println("Refresh pressed");
    String tracktext = ta.getText();
    try{
      PrintWriter writer = new PrintWriter("tmp.txt", "UTF-8");
      writer.print(tracktext);
      writer.close();
    }catch(Exception e){
      System.out.println("Failed to create printwriter");
    }
    ArrayList<Line> arl = new ArrayList<Line>();
    LineParser lp = new LineParser("tmp.txt",config);
    if(config.aldl.size() >= 2){
      Line readline = config.aldl.get(1);
      if(readline == null){
        System.out.println("readline was null!");
        System.exit(-1);
      }
      arl.add(readline);
      for(int i = 1; i < config.aldl.size(); i++){
        config.aldl.remove(i);
      }
    }
    for(int i = 0; i < config.vislines.size(); i++){
      System.out.println("visline(" + i + "):" + config.vislines.get(i));
    }
    System.out.println("ALDL size is now: " + config.aldl.size());
    ((TrackPane)tp).lines = arl;
    tp.repaint();
  }
  public void actionPerformed(ActionEvent e){
    if(e.getSource() == rb){
      refresh();
    }
  }
}
