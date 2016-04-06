//====ReadFromFile.java
import java.util.*;
import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFromFile {
	public static void main(String[] args) throws IOException {
	  String linecolor;
	  char   section;
	  int    block_number;
	  double block_length;
      double block_grade;
	  int    speed_limit;
	  String infrastruct;
	  double elevation;
	  double cummulative_el;
	  String switch_block;
	  String direction;
	 

	  String nextLine;
	  String[] info;
	  String s = "p";
	
	  BufferedReader inFile = new BufferedReader(new FileReader("C:\\eclipse\\workspace\\readfromfile\\t1.txt"));

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
		System.out.println(linecolor + ":" + section + ":" + block_number + ":" +
	             block_length + ":" + block_grade + ":" + 
	             speed_limit + ":" + infrastruct + ":" +
	             elevation + ":" + cummulative_el + ":" +
	             switch_block + ":" + direction + ":");
	  }

	}
}