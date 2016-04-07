/*Dummy track model so we can start working on the main
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;

public class TrackModel{
	

	private TrainModel train;

  public TrackModel(){
  	this.train = new TrainModel("IAmAFakeTrain", this);
  }

  public void tick(long deltaT) {
    System.out.println("TrackModel tick");
  	this.train.tick(deltaT);
  }

  public void relayAuthority(int authority, int blockID) {
  	this.train.receiveAuthority(authority);
  }

  public void relaySpeed(int speed, int blockID) {
  	this.train.receiveSpeed(speed);
    System.out.println(speed);
  }
  
 /* public String line;
  public char   section;
  public int    block_number;
  public double block_length;
  public double block_grade;
  public int    speed_limit;
  public String infrastruct;
  public double elevation;
  public double cummulative_el;
  public String switch_block;
  public String direction;*/
  public double recvdistance = 401;
  public double totaldistance = 0;
  
  public MyLinkList theLinkedList;
  
  //public TrackModel next;       //a reference pointing to next node

  	String filename = "t1.txt";

	private String line;

	/*private char section;

	private int block_number;

	private double block_length;

	private double block_grade;

	private int speed_limit;

	private String infrastruct;

	private double elevation;

	private double cummulative_el;

	private String switch_block;

	private String direction; */
	
  public TrackModel(String filename) throws NumberFormatException, IOException
  {
	  Readfile();
  }
  
  public static void main(String[] args) throws NumberFormatException, IOException{

	  TrackModel trackmodel = new TrackModel();
	  trackmodel.Readfile();
	  System.out.println("I am here.");
	  trackmodel.theLinkedList.display();
  }
  
  public TrackModel(){
	  
  }
  
  /*public TrackModel(/*String line, char section, int block_number, 
                  double block_length, double block_grade, 
                  int speed_limit, String infrastruct,
                  double elevation, double cummulative_el,
                  String switch_block, String direction) //constructor
  {
    this.line         	= line;
    this.section			= section;
    this.block_number	= block_number;
    this.block_length	= block_length;
    this.block_grade		= block_grade;
    this.speed_limit		= speed_limit;
    this.infrastruct		= infrastruct;
    this.elevation		= elevation;
    this.cummulative_el	= cummulative_el;
    this.switch_block	= switch_block;
    this.direction	    = direction;
  }*/

  /*public void display() {
		 System.out.println(line + ":" + section + ":" + block_number + ":" +
	             block_length + ":" + block_grade + ":" + 
	             speed_limit + ":" + infrastruct + ":" +
	             elevation + ":" + cummulative_el + ":" +
	             switch_block + ":" + direction + ":");
	 }*/
	 public String toString(){
		 return line;
	 }
	//because all variables are public, you do not need any get or set
	 //public ListNode getNext() {return next;}
	 //public void setNext(ListNode newNext) {next = newNext;}

	  public void Readfile() throws NumberFormatException, IOException{
		  theLinkedList = new MyLinkList();
		  
		  String linecolor;
		  char   section;
		  int    block_number;
		  double block_length;
	      double block_grade;
		  int    speed_limit = 0;
		  String infrastruct;
		  double elevation;
		  double cummulative_el;
		  String switch_block;
		  String direction;	 

		  String nextLine;
		  String[] info;
		  String s = "p";
		
		  BufferedReader inFile = new BufferedReader(new FileReader("t1.txt"));

		  while ((nextLine = inFile.readLine()) != null)
		  {
		    info = nextLine.split("\t");
		    linecolor 	    = info[0];
			section         = info[1].charAt(0);
			block_number    = Integer.parseInt(info[2]);
			block_length    = Double.parseDouble(info[3]);
		    block_grade     = Double.parseDouble(info[4]);
			speed_limit     = Integer.parseInt(info[5]);
			infrastruct     = info[6];
			elevation       = Double.parseDouble(info[7]);
			cummulative_el  = Double.parseDouble(info[8]);
			switch_block    = info[9];
			direction       = info[10]; 
			theLinkedList.insertFristLink(linecolor,  section,	block_number,	block_length,	block_grade, speed_limit, infrastruct ,  elevation ,	cummulative_el,	switch_block,	direction);
		  }
	  
		  
		  
		 // Output all of the items in the Linked List
		  //	theLinkedList.display();
		 
		 // Print out Specific Block info.
		  		//System.out.println(theLinkedList.find(2).block_length);	
		  
	  }
	  
  public void receiveDistance(double deltaX) {
	  recvdistance = recvdistance + deltaX ;

  }

  public boolean[] getBlockOccupancies() {
	  boolean [] test = new boolean[14];
	  int i=1;
	  while (recvdistance > totaldistance) 
	  {
		totaldistance =  totaldistance + theLinkedList.find(i).block_length;  
		i = i+1;
		System.out.println("i=" + (i-1));
		test = new boolean[14];
		test[convertBlock(i-2)] = true;
		System.out.println(Arrays.toString(test));
	  
  }
	  return test;
}
  
  public int convertBlock(int i)
  {
	  int[] blocks = {11,10,9,8,6,0,1,2,3,4,5,7,13,12};
	  return blocks[i];
	  
  }
}