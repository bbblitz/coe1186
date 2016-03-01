/*
 * @author Alexander Pickering <amp215@pitt.edu>
 * @version Prototype
 * @since 2016/02/08
 * The top ribbon panel
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TopRibbon extends JMenuBar implements MenuListener{
  //File
  JMenuItem exititem;
  //Edit
  JMenuItem copyitem;
  JMenuItem cutitem;
  JMenuItem pasteitem;
  //Window
  JMenuItem schedualitem;
  JMenuItem linevisitem;
  JMenuItem detailsitem;
  JMenuItem dispatchitem;
  JMenuItem messageitem;
  JMenuItem switchitem;
  //Help
  JMenuItem aboutitem;
  JMenuItem helpitem;
  /*
   * Initalizes JPanel's constructor
   * @override
   */
  public TopRibbon(/*LayoutManager layout, boolean isDoubleBuffered*/){
    //super(layout,isDoubleBuffered);

    JMenu filemenu = new JMenu("File");
    filemenu.setMnemonic(KeyEvent.VK_F);
    filemenu.getAccessibleContext().setAccessibleDescription("Items dealing with files");
    exititem = new JMenuItem("Exit",KeyEvent.VK_E);
    exititem.getAccessibleContext().setAccessibleDescription("Exits the CTC window");
    filemenu.add(exititem);

    JMenu editmenu = new JMenu("Edit");
    editmenu.setMnemonic(KeyEvent.VK_E);
    editmenu.getAccessibleContext().setAccessibleDescription("Anything to do with editing");
    copyitem = new JMenuItem("Copy",KeyEvent.VK_O);
    copyitem.getAccessibleContext().setAccessibleDescription("Copy a section of text to the clipboard");
    cutitem = new JMenuItem("Cut",KeyEvent.VK_U);
    cutitem.getAccessibleContext().setAccessibleDescription("Copy a section of text to clip board, and delete the text");
    pasteitem = new JMenuItem("Paste",KeyEvent.VK_P);
    pasteitem.getAccessibleContext().setAccessibleDescription("Enters the text held in the clipboard");
    editmenu.add(copyitem);
    editmenu.add(cutitem);
    editmenu.add(pasteitem);

    JMenu windowmenu = new JMenu("Window");
    windowmenu.setMnemonic(KeyEvent.VK_W);
    windowmenu.getAccessibleContext().setAccessibleDescription("Commands dealing with windows");
    schedualitem = new JMenuItem("Schedual",KeyEvent.VK_S);
    schedualitem.getAccessibleContext().setAccessibleDescription("Open the Schedual pane in a seperate window");
    linevisitem = new JMenuItem("Line Vis.",KeyEvent.VK_L);
    linevisitem.getAccessibleContext().setAccessibleDescription("Opens the Line Visibility pane in a seperate window");
    detailsitem = new JMenuItem("Details",KeyEvent.VK_E);
    detailsitem.getAccessibleContext().setAccessibleDescription("Opens the Train details pane in a seperate window");
    dispatchitem = new JMenuItem("Dispatch",KeyEvent.VK_I);
    dispatchitem.getAccessibleContext().setAccessibleDescription("Opens the Train dispatch pane in a seperate window");
    messageitem = new JMenuItem("Message",KeyEvent.VK_M);
    messageitem.getAccessibleContext().setAccessibleDescription("Opens the messages pane in a seperate window");
    switchitem = new JMenuItem("Switch",KeyEvent.VK_W);
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
    aboutitem = new JMenuItem("About",KeyEvent.VK_A);
    aboutitem.getAccessibleContext().setAccessibleDescription("About this peice of software");
    helpitem = new JMenuItem("Help",KeyEvent.VK_H);
    helpitem.getAccessibleContext().setAccessibleDescription("Open the User's Manual");
    helpmenu.add(aboutitem);
    helpmenu.add(helpitem);

    this.add(filemenu);
    this.add(editmenu);
    this.add(windowmenu);
    this.add(helpmenu);

    filemenu.addMenuListener(this);
  }

  @Override
   public void menuSelected(MenuEvent e) {
       System.out.println("menuSelected");
   }

   @Override
   public void menuDeselected(MenuEvent e) {
       System.out.println("menuDeselected");
   }

   @Override
   public void menuCanceled(MenuEvent e) {
       System.out.println("menuCanceled");
   }

/*
  public void menuSelected(MenuEvent  e){
    if(e.getSource() == exititem){
      System.exit(0);
    }
  }
*/
}
