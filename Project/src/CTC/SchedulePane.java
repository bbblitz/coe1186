
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class SchedulePane extends JPanel implements ActionListener{

  //A 2 by x arraylist, formated Time,Station
  Map<String, Integer> schedule = new TreeMap<String, Integer>();
  Config c;
  JComboBox trainnum;
  JButton addbutton;
  JTable table;
  Object[][] tdata;
  JButton redbutton;
  JButton greenbutton;
  public SchedulePane(Config config){
    super();
    c = config;
    JLabel l = new JLabel("Schedule:");
    add(l);
    if(c.DEBUG_SCHEDUAL)
      System.out.println("Createing Schedule");
    //bogusSchedule();
    /*
    String[][] data = new String[schedule.size()][2];
    for(int i = 0; i < schedule.size(); i++){
      data[i][0] = schedule.get(i).get(0);
      data[i][1] = schedule.get(i).get(1);
    }
    */
    /*
    for(int i = 0; i < data.length; i++){
      for(int j = 0; j < data[i].length; j++){
        System.out.print("" + data[i][j] + "\t");
      }
      System.out.println();
    }
    */
    trainnum = new JComboBox();

    String[] columnnames = {"Time","Station"};
    tdata = new Object[10][2];
    for(int i = 0; i < 10; i++){
      for(int j = 0; j < 2; j++){
        tdata[i][j] = "";
      }
    }


    Integer[] comboints = new Integer[c.pinkLineTrains.size()];
    for(int i = 0; i < c.pinkLineTrains.size(); i++){
      comboints[i] = c.pinkLineTrains.get(i).getID();
    }
    trainnum = new JComboBox(comboints);
    trainnum.addActionListener(this);
    table = new JTable(tdata,columnnames);
    JScrollPane scrollPane = new JScrollPane(table);
    JPanel butpanel = new JPanel();
    addbutton = new JButton("Add Stop");
    addbutton.addActionListener(this);
    redbutton = new JButton("Red");
    redbutton.addActionListener(this);
    greenbutton = new JButton("Green");
    greenbutton.addActionListener(this);
    //table.setPreferredScrollableViewportSize(new Dimension(500, 70));
    table.setFillsViewportHeight(true);
    //JScrollPane scrollPane = new JScrollPane(table);
    //JScrollPane scrollPane = new JScrollPane(table);
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    butpanel.setLayout(new BoxLayout(butpanel,BoxLayout.X_AXIS));
    JLabel trainlabel = new JLabel("Train number:");
    add(trainlabel);
    add(trainnum);
    add(scrollPane);
    butpanel.add(addbutton);
    butpanel.add(redbutton);
    butpanel.add(greenbutton);
    add(butpanel);
    reloadSchedual();
  }

  public String getStation(){
    Object[] stationlist = new Object[100];
    int i = 0;
    for(BlockInterface bi : c.aldl.get(0).blocks){
      if(bi instanceof BlockStation){
        String tname = ((BlockStation)bi).getStationName();
        stationlist[i++] = tname;
      }
    }

    return (String) JOptionPane.showInputDialog(null,"Station","Input",JOptionPane.INFORMATION_MESSAGE,null,stationlist,stationlist[0]);
  }
  public int getTime(){
    String time = JOptionPane.showInputDialog("Target time:");
    int atime = -1;
    try{
      atime = Integer.parseInt(time);
    }catch(Exception e){
      JOptionPane.showConfirmDialog(null,"Input was not a time!");
    }
    return atime;
  }

  public void reloadSchedual(){
    Integer tnum = (Integer) trainnum.getSelectedItem();
    System.out.println("Reloading schedule with train:" + tnum);
    Train ttrain = null;
    for(Train ts : c.alltrains){
      if(ts.getID() == tnum){
        ttrain = ts;
      }
    }
    if(ttrain == null) return;
    Object[][] ndata = new Object[10][2];
    int k = 0;
    for(Map.Entry<String, Integer> entry : ttrain.schedule.entrySet()) {
      ndata[k][0] = entry.getKey();
      ndata[k][1] = entry.getValue();
      k++;
    }
    System.out.println("Parsed schedule:");
    for(int i = 0; i < 10; i++){
      for(int j = 0; j < 2; j++){
        table.getModel().setValueAt(ndata[i][j],i,j);
        System.out.print(ndata[i][j] + " ");
      }
      System.out.println();
    }
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == trainnum){
      reloadSchedual();
    }
    if(e.getSource() == addbutton){
      String station = getStation();
      Integer time = getTime();
      if(time == -1) return;
      Integer train = (Integer) trainnum.getSelectedItem();
      Train t = null;
      for(Train ts : c.alltrains){
        if(ts.getID() == train){
          t = ts;
          break;
        }
      }
      if(t == null){
        System.out.println("Null train!");
        return;
      }
      System.out.printf("adding to schedule:%s,%d\n",station,time);
      t.schedule.put(station,time);
      reloadSchedual();
    }
    if(e.getSource() == redbutton){
      System.out.println("Loading red schedule");
      File f = new File("red.txt");
      Scanner s = null;
      try{
        s = new Scanner(f);
      }catch(Exception ex){
        System.out.println(ex);
        return;
      }
      int btime = 0;
      Integer train = (Integer) trainnum.getSelectedItem();
      Train t = null;
      for(Train ts : c.alltrains){
        if(ts.getID() == train){
          t = ts;
          break;
        }
      }
      if(t == null){
        System.out.println("Null train!");
        return;
      }
      while(s.hasNextLine()){
        String blockstring = s.nextLine();
        String[] vs = blockstring.split(",");
        for(int i = 0; i < vs.length; i++){
          vs[i] = vs[i].trim();
        }
        String name = vs[0];
        Integer time = Integer.parseInt(vs[1]);
        btime+= time;
        t.schedule.put(name,btime);
      }
      reloadSchedual();
    }
    if(e.getSource() == greenbutton){
      System.out.println("Loading red schedule");
      File f = new File("green.txt");
      Scanner s = null;
      try{
        s = new Scanner(f);
      }catch(Exception ex){
        System.out.println(ex);
        return;
      }
      int btime = 0;
      Integer train = (Integer) trainnum.getSelectedItem();
      Train t = null;
      for(Train ts : c.alltrains){
        if(ts.getID() == train){
          t = ts;
          break;
        }
      }
      if(t == null){
        System.out.println("Null train!");
        return;
      }
      while(s.hasNextLine()){
        String blockstring = s.nextLine();
        String[] vs = blockstring.split(",");
        for(int i = 0; i < vs.length; i++){
          vs[i] = vs[i].trim();
        }
        String name = vs[0];
        Integer time = Integer.parseInt(vs[1]);
        btime+= time;
        t.schedule.put(name,btime);
      }
      reloadSchedual();
    }
  }

  public void bogusSchedule(){
    Random r = new Random();
    for(int i = 0; i < 5; i++){
      String time = String.format("%02d:%02d",r.nextInt(24),r.nextInt(60));
      String station = "Kingdom Come";
      /*
      station += (char)('A' + r.nextInt(24));
      for(int j = 0; j < 10; j++){
        //Generate a random station name
        station += (char)('a' + r.nextInt(24));
      }
      */
      //addItem(time,station);
    }
  }

  public void addItem(String time, String stationName){
    ArrayList<String> newitem = new ArrayList<String>();
    newitem.add(time);
    newitem.add(stationName);
    schedule.put(stationName,Integer.parseInt(time));
  }
}
