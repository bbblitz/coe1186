

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;

//line:         red
//section :     A
//block number: 3
//block length: 50m
//block grade:  1.5%
//speed limit:  40 km/hr
//infrastruct: STATION: SHADYSIDE
//elevation:    0.75
//cummlatvie el:1.50
//switch block: Switch 12
//arrow direct: head

public class ListNode   
{
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
 
 public ListNode next;       //a reference pointing to next node

 public ListNode(String line, char section, int block_number, 
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
		//System.out.println(linecolor + ":" + section + ":" + block_number + ":" +
	            // block_length + ":" + block_grade + ":" + 
	             //speed_limit + ":" + infrastruct + ":" +
	             //elevation + ":" + cummulative_el + ":" +
	            // switch_block + ":" + direction + ":");
	  }
	  
	 // theLinkedList.display();
	 // System.out.println("Speed Limit is "+speed_limit);
	  //System.out.println(theLinkedList.find(2).block_number);
	  int distance = 0, x = 1;
	  
	  //Calculate distance
	  while(theLinkedList.find(x).block_length != 0){
		  System.out.println(theLinkedList.find(x).block_length);
		  distance = (int) (theLinkedList.find(x).block_length + distance);
		  System.out.println(distance+"cccccccc");
		  x++;
	  }
  }
	   //outputing on reverse order, ORANGE is the first link
	//  theLinkedList.removeFirst(); //ORANGE is removed
	//  System.out.println(theLinkedList.find(3).block_number + "  was found");
	//  theLinkedList.removeLink(3);
	//  theLinkedList.display();
  }
