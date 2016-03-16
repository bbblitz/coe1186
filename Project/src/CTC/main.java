/*
 * A prototype for the CTC office module
 * Creates the CTC office window
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class main{
  public static Config config;
  public static void main(String[] args){
    config = new Config();
    config.windowDim = new Dimension(1200,600);

    JFrame window = new JFrame("CTC Office");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setJMenuBar(makeRibbon());
    window.setSize(config.windowDim.width,config.windowDim.height);
    config.window = window;

    ArrayList<Line> aldl = new ArrayList<Line>();
    config.aldl = aldl;
    LineParser parser = new LineParser("track.txt",config);
    /*
    LineOne dlo = new LineOne();
    ArrayList<Line> aldl = new ArrayList<Line>();
    aldl.add(dlo);
    config.aldl = aldl;
    System.out.println("LineOneID:" + dlo.lineid);
    config.vislines = new ArrayList<Boolean>(10);
    config.vislines.add(dlo.lineid,true);
    */

    JPanel holder = new JPanel();
    holder.setLayout(new BoxLayout(holder,BoxLayout.X_AXIS));
    JPanel tp = makeTrack(config);
    config.trackpane = tp;
    System.out.println("config.trackpane is: " + config.trackpane);
    JPanel dp = makeDetails(config);
    Dimension o = window.getSize();
    Dimension d = new Dimension((int)(o.width*0.7),o.height);
    Dimension d2 = new Dimension((int)(o.width*0.3),o.height);
    tp.setPreferredSize(d);
    dp.setPreferredSize(d2);
    holder.add(tp);
    holder.add(dp);

    window.getContentPane().add(holder);
    ((DetailPane)dp).createComponent();
    //JLabel label = new JLabel("Hello, world!");
    //window.getContentPane().add(label);
    window.pack();
    window.setVisible(true);
  }

  public static JPanel makeDetails(Config config){
    DetailPane dp = new DetailPane(config);
    return dp;
  }

  public static JPanel makeTrack(Config config){
    TrackPane tp = new TrackPane(config);
    Dimension d = tp.getSize();
    System.out.printf("d is: %d,%d\n",d.width,d.height);
    Dimension d2 = new Dimension();
    d2.width =(int) (d.width*0.6);
    d2.height = d.height;
    tp.setSize(d2);
    System.out.printf("d2 is: %d,%d\n",d2.width,d2.height);
    return tp;
  }

  public static JMenuBar makeRibbon(){
    return new TopRibbon();
    /*
    JMenuBar menubar = new JMenuBar();

    JMenu filemenu = new JMenu("File");
    filemenu.setMnemonic(KeyEvent.VK_F);
    filemenu.getAccessibleContext().setAccessibleDescription("Items dealing with files");
    JMenuItem exititem = new JMenuItem("Exit",KeyEvent.VK_E);
    exititem.getAccessibleContext().setAccessibleDescription("Exits the CTC window");
    filemenu.add(exititem);

    JMenu editmenu = new JMenu("Edit");
    editmenu.setMnemonic(KeyEvent.VK_E);
    editmenu.getAccessibleContext().setAccessibleDescription("Anything to do with editing");
    JMenuItem copyitem = new JMenuItem("Copy",KeyEvent.VK_O);
    copyitem.getAccessibleContext().setAccessibleDescription("Copy a section of text to the clipboard");
    JMenuItem cutitem = new JMenuItem("Cut",KeyEvent.VK_U);
    cutitem.getAccessibleContext().setAccessibleDescription("Copy a section of text to clip board, and delete the text");
    JMenuItem pasteitem = new JMenuItem("Paste",KeyEvent.VK_P);
    pasteitem.getAccessibleContext().setAccessibleDescription("Enters the text held in the clipboard");
    editmenu.add(copyitem);
    editmenu.add(cutitem);
    editmenu.add(pasteitem);

    JMenu windowmenu = new JMenu("Window");
    windowmenu.setMnemonic(KeyEvent.VK_W);
    windowmenu.getAccessibleContext().setAccessibleDescription("Commands dealing with windows");
    JMenuItem schedualitem = new JMenuItem("Schedual",KeyEvent.VK_S);
    schedualitem.getAccessibleContext().setAccessibleDescription("Open the Schedual pane in a seperate window");
    JMenuItem linevisitem = new JMenuItem("Line Vis.",KeyEvent.VK_L);
    linevisitem.getAccessibleContext().setAccessibleDescription("Opens the Line Visibility pane in a seperate window");
    JMenuItem detailsitem = new JMenuItem("Details",KeyEvent.VK_E);
    detailsitem.getAccessibleContext().setAccessibleDescription("Opens the Train details pane in a seperate window");
    JMenuItem dispatchitem = new JMenuItem("Dispatch",KeyEvent.VK_I);
    dispatchitem.getAccessibleContext().setAccessibleDescription("Opens the Train dispatch pane in a seperate window");
    JMenuItem messageitem = new JMenuItem("Message",KeyEvent.VK_M);
    messageitem.getAccessibleContext().setAccessibleDescription("Opens the messages pane in a seperate window");
    JMenuItem switchitem = new JMenuItem("Switch",KeyEvent.VK_W);
    switchitem.getAccessibleContext().setAccessibleDescription("Opensd the switch pane in a seperate window");
    windowmenu.add(schedualitem);
    windowmenu.add(linevisitem);
    windowmenu.add(detailsitem);
    windowmenu.add(dispatchitem);
    windowmenu.add(messageitem);
    windowmenu.add(switchitem);

    JMenu helpmenu = new JMenu("Help");
    helpmenu.setMnemonic(KeyEvent.VK_H);
    helpmenu.getAccessibleContext().setAccessibleDescription("Oh No! I need help!");
    JMenuItem aboutitem = new JMenuItem("About",KeyEvent.VK_A);
    aboutitem.getAccessibleContext().setAccessibleDescription("About this peice of software");
    JMenuItem helpitem = new JMenuItem("Help",KeyEvent.VK_H);
    helpitem.getAccessibleContext().setAccessibleDescription("Open the User's Manual");
    helpmenu.add(aboutitem);
    helpmenu.add(helpitem);

    menubar.add(filemenu);
    menubar.add(editmenu);
    menubar.add(windowmenu);
    menubar.add(helpmenu);

    return menubar;
    */
  }
}
