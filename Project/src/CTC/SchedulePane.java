
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SchedulePane extends JPanel{

  //A 2 by x arraylist, formated Time,Station
  ArrayList<ArrayList<String>> schedule = new ArrayList<ArrayList<String>>();
  Config c;
  public SchedulePane(Config config){
    super();
    c = config;
    JLabel l = new JLabel("Schedule:");
    add(l);
    System.out.println("Createing Schedule");
    bogusSchedule();
    String[][] data = new String[schedule.size()][2];
    for(int i = 0; i < schedule.size(); i++){
      data[i][0] = schedule.get(i).get(0);
      data[i][1] = schedule.get(i).get(1);
    }

    for(int i = 0; i < data.length; i++){
      for(int j = 0; j < data[i].length; j++){
        System.out.print("" + data[i][j] + "\t");
      }
      System.out.println();
    }

    String[] columnnames = {"Time","Station"};
    JTable table = new JTable(data,columnnames);
    JScrollPane scrollPane = new JScrollPane(table);
    JPanel butpanel = new JPanel();
    JButton addbut = new JButton("Add");
    JButton editbut = new JButton("Edit");
    JButton delbut = new JButton("Delete");
    //table.setPreferredScrollableViewportSize(new Dimension(500, 70));
    table.setFillsViewportHeight(true);
    //JScrollPane scrollPane = new JScrollPane(table);
    //JScrollPane scrollPane = new JScrollPane(table);
    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    butpanel.setLayout(new BoxLayout(butpanel,BoxLayout.X_AXIS));
    add(scrollPane);
    butpanel.add(addbut);
    butpanel.add(editbut);
    butpanel.add(delbut);
    add(butpanel);

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
      addItem(time,station);
    }
  }

  public void addItem(String time, String stationName){
    ArrayList<String> newitem = new ArrayList<String>();
    newitem.add(time);
    newitem.add(stationName);
    schedule.add(newitem);
  }
}
