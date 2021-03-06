/*Dummy track model so we can start working on the main
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;

public class TrackModel{
	

	/*private TrainModel train;

  public TrackModel(){
  	this.train = new TrainModel("IAmAFakeTrain", this);
  }

  public void tick(long deltaT) {
  	this.train.tick(deltaT);
  }

  public void relayAuthority(int authority, int blockID) {
  	this.train.receiveAuthority(authority);
  }

  public void relaySpeed(int speed, int blockID) {
  	this.train.receiveSpeed(speed);
    System.out.println(speed);
  }
*/
  public String line;
  public char   section;
  public int    block_number;
  public double block_length;
  public double block_grade;
  public int    speed_limit;
  public String infrastruct;
  public double elevation;
  public double cummulative_el;
  public String switch_block;
  public String direction;
  
  public TrackModel next;       //a reference pointing to next node

  public TrackModel(String line, char section, int block_number, 
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
  }

  public void display() {
		 System.out.println(line + ":" + section + ":" + block_number + ":" +
	             block_length + ":" + block_grade + ":" + 
	             speed_limit + ":" + infrastruct + ":" +
	             elevation + ":" + cummulative_el + ":" +
	             switch_block + ":" + direction + ":");
	 }
	 public String toString(){
		 return line;
	 }
	//because all variables are public, you do not need any get or set
	 //public ListNode getNext() {return next;}
	 //public void setNext(ListNode newNext) {next = newNext;}

	  public static void main(String[] args) throws NumberFormatException, IOException{
		  MyLinkList theLinkedList = new MyLinkList();
		  
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
		  	theLinkedList.display();
		  	System.out.println("HI");
		 
		 // Print out Specific Block info.
		  		//System.out.println(theLinkedList.find(2).block_length);
		  
	  }
  public void receiveDistance(double deltaX) {
	  

  }

  public boolean[] getBlockOccupancies() {
  	boolean[] test = new boolean[14];
  	return test;
  }
}