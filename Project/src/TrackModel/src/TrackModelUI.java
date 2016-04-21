//package trackmodelui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;


public class TrackModelUI{
	private TrackModelParser track;
	//private int num1 = 0;
	private String color;
	private String section;     //string
	private String blocknumber; //integer
	private String blocklength;  //double
	private String blockgrade;   //-ve double
	private String speedlimit;   //integer
	private String infrastructure;
	private String failState;
	private String crossing;
	private String[] failArray = new String[229];
	/* 
	 station: shadyside
     station: steel plaza, underground
     switch: to yard/from yard
     switch
     switch: underground
     underbround
     railway crossing
	 */
	private String elevation; //-ve double
	private String stationName;
	private String commulativeelevation; //-ve double
	private String switchblock;  //Switch 6
	private String arrowdirection; //head, tail
	
	private final int HEIGHT = 300;
	private final int WIDTH = 800;
	
	private JFrame frame = new JFrame();
	private JPanel[] panel = new JPanel[16];
	
	private JLabel colorID;
	private JLabel sectionID;
	private JLabel blocknumberID;
	private JLabel blocklengthID;
	private JLabel blockgradeID;
	private JLabel speedlimitID;
	private JLabel stationNameID;
	private JLabel infrastructureID;
	private JLabel elevationID;
	private JLabel commulativeelevationID;
	private JLabel switchblockID;
	private JLabel crossingID;
	private JLabel arrowdirectionID;
	private JLabel failStateID;
	public JComboBox[] failstate;
	
	//String colour = "RED";
	//private JButton colorred = new JButton(colour);
	//private JButton colorgreen = new JButton("GREEN");
	
	
 
	
	public TrackModelUI(int num1){   //constructor
		this.track = track;
		
		//track = readFile().track ;
		
		TrackModelParser tmp = new TrackModelParser("dummyTrack.txt");
		ArrayList<BlockInterface> out = tmp.readFile();
		System.out.println("I am here!!  " + out.get(7).getBlockType());
		infrastructure = out.get(num1).getBlockType();
		stationName = out.get(num1).getStation();
		speedlimit = Integer.toString(out.get(num1).getSpeedLimit()); //String strI = Integer.toString(i);
		elevation = Double.toString(out.get(num1).getElevation()); //String strI = Integer.toString(i);
		blocklength = Double.toString(out.get(num1).getLength());
		blockgrade =  Double.toString(out.get(num1).getGrade());
		crossing = out.get(num1).getCrossing();
		
		
		
		frame.setSize(WIDTH, HEIGHT);
		frame.setLayout(new GridLayout(8,2));
		
		String[] failstates = new String[] {"FS_NORMAL","FS_BROKEN_RAIL","FS_POWER_FAILURE","FS_TRACK_CIRCUIT_FAILURE","FS_RAIL_AND_POWER","FS_CIRCUIT_AND_RAIL","FS_CIRCUIT_AND_POWER","FS_CIRCUIT_RAIL_POWER"};
		
		//Create JLabel
		JLabel wordColor = new JLabel("Please Enter a Block ID: ");
		JLabel wordSection = new JLabel("Section: ");
		JLabel wordBlkNum = new JLabel("Block ID: ");
		JLabel wordBlkLength = new JLabel("Block Length: ");
		JLabel wordBlkGrade = new JLabel("Block Grade: ");
		JLabel wordSpeedLmt = new JLabel("Speed Limit:");
		JLabel wordInfra = new JLabel("Infrastructure: ");
		JLabel wordStation = new JLabel("Station Name: ");
		JLabel wordEleva = new JLabel("Elevation: ");
		JLabel wordCrossing = new JLabel("Crossing: ");
		JLabel wordCumuEleva = new JLabel("Commulative Elevation: ");
		JLabel wordSwitchBlk = new JLabel("Switch Block: ");
		JLabel wordArrowDir = new JLabel("Arrow Direction: ");
		JLabel wordFailState = new JLabel("Fail State: ");
		JComboBox<String> failstate = new JComboBox<>(failstates);
		
		
		
		String sectio = "A";
		JLabel seection = new JLabel(sectio);
		
		int blknum = num1;
		String s2 = blknum + "";
		JLabel blocknum = new JLabel(s2);
		
		
		//Units
		JLabel unitLength = new JLabel("m");
		JLabel unitSpeed = new JLabel("km/hr");
		JLabel unitGrade = new JLabel("%");
		JTextField textblockID = new JTextField(5);
		
		JLabel unitVel = new JLabel("mph");
		
		
		
		//System.out.println("Output Num 2" + num2);
		
		colorID = new JLabel(color);
	    sectionID = new JLabel(section);
	    blocknumberID = new JLabel(blocknumber);
	    blocklengthID = new JLabel(blocklength);
	    blockgradeID = new JLabel(blockgrade);
	    speedlimitID = new JLabel(speedlimit);
	    stationNameID = new JLabel(stationName);
	    infrastructureID = new JLabel(infrastructure);
	    elevationID = new JLabel(elevation);
	    commulativeelevationID = new JLabel(commulativeelevation);
	    switchblockID = new JLabel(switchblock);
	    arrowdirectionID = new JLabel(arrowdirection);
	    crossingID = new JLabel(crossing);
	    
	    
    	
	    //Failstate Method
	    failArray = new String[229];
    	for (int i = 0; i < 229; i++)
    	{
    		failArray[i] = "FS_Normal";
    	}
	    
	    failstate.addActionListener(new ActionListener(){
	  
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			JComboBox<String> combo = (JComboBox<String>) e.getSource();
			String selectedFail = (String) failstate.getSelectedItem();

			if (selectedFail.equals("FS_NORMAL")){
				failArray[num1] = "FS_NORMAL";
				System.out.println("This is good");
			}
			else {
				failArray[num1] = "FS_ERROR";
				System.out.println("This is bad");
			}
			}
	    });
	    
	    System.out.println("I am here.." + failArray[num1]);
	    
		for(int i=0; i<16; i++){
			panel[i] = new JPanel();
			frame.add(panel[i]);
		}
		
		String failArray1 = failArray[num1];
		System.out.println(failArray1);
		failStateID = new JLabel(failArray1);
		
		panel[0].add(wordColor);
		panel[0].add(textblockID);
		//panel[0].add(colorred);
		//panel[0].add(colorgreen);
		//panel[1].add(wordSection);
		//panel[1].add(sectionID);
		//panel[1].add(seection);
		panel[1].add(wordBlkNum);
		//panel[2].add(blocknumberID);
		panel[1].add(blocknum);
		panel[2].add(wordBlkLength);
		panel[2].add(blocklengthID);
		panel[2].add(unitLength);
		panel[3].add(wordBlkGrade);
		panel[3].add(blockgradeID);
		panel[3].add(unitGrade);	
		panel[4].add(wordSpeedLmt);
		panel[4].add(speedlimitID);
		panel[4].add(unitSpeed);
		panel[5].add(wordInfra);
		panel[5].add(infrastructureID);
		panel[6].add(wordEleva);
		panel[6].add(elevationID);
		panel[6].add(unitLength);
		//panel[8].add(wordCumuEleva);
		//panel[8].add(commulativeelevationID);
		//panel[9].add(wordSwitchBlk);
		//panel[9].add(switchblockID);
		//panel[7].add(wordArrowDir);
		//panel[7].add(arrowdirectionID);
		panel[7].add(wordFailState);
		panel[7].add(failstate);
		panel[8].add(wordFailState);
		panel[8].add(failStateID);
		panel[9].add(wordStation);
		panel[9].add(stationNameID);
		panel[10].add(wordCrossing);
		panel[10].add(crossingID);
		
		
		
		//ActionListener buttonListener = new ButtonListener();
		//engineForce.addActionListener(buttonListener);
		frame.setVisible(true);
	
	}
	
	public void tick(){
		
		colorID.setText(color);
		sectionID.setText(section);
		blocknumberID.setText(blocknumber);
		blocklengthID.setText(blocklength);
		blockgradeID.setText(blockgrade);
		speedlimitID.setText(speedlimit);
		infrastructureID.setText(infrastructure);
		elevationID.setText(elevation);
		commulativeelevationID.setText(commulativeelevation);
		switchblockID.setText(switchblock);
		arrowdirectionID.setText(arrowdirection);
	}
	
	
	public static void main(String[] args){
		String fn = JOptionPane.showInputDialog("Enter block number");
		int num1 = Integer.parseInt(fn);
		new TrackModelUI(num1);  //instantiate constructor
		
	}
}
