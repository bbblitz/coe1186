
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;

public class TrackModel {
	
	ArrayList<BlockInterface> track; 
	ArrayList<Train> Trains;
	  
	  public TrackModel(String filename){
		  TrackModelParser Parser = new TrackModelParser(filename);
		  track = Parser.readFile();
		  track.get(3).getLength();
	  }
	  
	  
  public void tick(double deltaT){
		 
	    receiveBlockGrade(deltaT);
			  
  }
  
  //Send Block grade to Train
  public void receiveBlockGrade(int temp)
  {
		  int tblockid = Trains.get(temp).block;
		  double traingrade = track.get(tblockid).getGrade();
  }
  
  // Get Occupanies method to output boolean array with occupancies
  public boolean[] getBlockOccupancies(){ 
	  boolean[] occupancy = new boolean[track.size()];
	  for(int i=0; i < Trains.size(); i++){ 
		  occupancy[Trains.get(i).block] = true;			 
	  }
	  return occupancy;
  }
  
  //Receives distance traveled by train and increments total distance
  public void receiveDistance(double deltaX, int trainid) {
	 Trains.get(trainid).distance += deltaX;
	 trainLocation(trainid);
  }
  
  public void trainLocation(int trainID){
	  //check the type of block
  int currentBlock = Trains.get(trainID).block;
  if (Trains.get(trainID).distance > track.get(currentBlock).getLength()){
	  Trains.get(trainID).distance -= track.get(currentBlock).getLength();
	  
	  if(track.get(currentBlock) instanceof BlockSwitch){
		  //Write case senario for Switches on the track.
		  int head = ((BlockSwitch)track.get(currentBlock)).getHead();
		  int tail = ((BlockSwitch)track.get(currentBlock)).getTail();
		  int divergent = ((BlockSwitch)track.get(currentBlock)).getDivergent();
		  
		   if (Trains.get(trainID).previous == head || Trains.get(trainID).previous == divergent){
			   
			   Trains.get(trainID).previous = Trains.get(trainID).block;
			   Trains.get(trainID).block = tail;
		   }
		   else {
			   if(true){
			   Trains.get(trainID).previous = Trains.get(trainID).block;
			   Trains.get(trainID).block = divergent;
			   }
			   else {
				   Trains.get(trainID).previous = Trains.get(trainID).block;
				   Trains.get(trainID).block = divergent;
			   }
		   }
		   
			  
		  }
	  		//Else statement to handle straight blocks
			  else if(track.get(currentBlock) instanceof Block){
		  //for(int i = 0; Trains.size(); i++){
			  
				  int head = ((Block)track.get(currentBlock)).getHead();
				  int tail = ((Block)track.get(currentBlock)).getTail();
				  
				  if (head == Trains.get(trainID).previous)
				  {
					  Trains.get(trainID).previous = Trains.get(trainID).block;
					  Trains.get(trainID).block = tail;
				  }
				  else {
					  Trains.get(trainID).previous = Trains.get(trainID).block;
					  Trains.get(trainID).block = head;
				  } 
		 }
  }
}
  
	  //Generate random number between 1 and 222 for 
  public int getpassengers(){
	  Random rand = new Random();
	  int  n = rand.nextInt(222);
	  return n;
  }
  
  //Output the track that failure occurs on
  public int getFailTrack(){
	  int trackid=0;
	return trackid;
  }
  
  //Output the type of failure that occured.
  public enum getFailState {
	  BrokenRail, TrackCircuitFailure, PowerFailure
  }
  
  //Track heater method, heat the track if temp goes under 69 degrees.
	  public void getTrackHeater(){
		  int tracktemperature=0;
		  if (tracktemperature < 69){
			  tracktemperature += 1.3;
		  }
	  }
	  
}
