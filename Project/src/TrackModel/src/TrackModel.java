
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;

public class TrackModel {
	
	ArrayList<BlockInterface> track; 
	Arraylist<Train> Trains;
	  
	  
	  public void tick(double deltaT){
		  
	  }
	  
	  // Get Occupanies method to output boolean array with occupancies
  public boolean[] getBlockOccupancies(){ 
	  Boolean occupancy[];
	  for(int i=0; track.size(); i++){ 
		  occupancy[i] = track.get(i).getOccupied();			 
	  }
  }
  
  //Receives distance traveled by train and increments total distance
  public void receiveDistance(double deltaX, int trainid) {
	 Trains.get(trainid).distance += deltaX;
  }
  
  public void trainLocation(){
	  //check the type of block
	  if(track[ID] instanceof BlockSwitch){
		  //Write case senario for Switches on the track.
			  
		  }
			  else{
		  for(int i = 0; Trains.size; i++){
			  if (Train.get(trainid).distance > BlockInterface.get(ID).distance){
				  Train.get(trainid).distance -= BlockInterface.get(ID).distance;
				  track[ID] = BlockInterface[ID].head;
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
		  int trackid;
		return trackid;
	  }
	  
	  //Output the type of failure that occured.
	  public enum getFailState {
		  BrokenRail, TrackCircuitFailure, PowerFailure
	  }
	  
	  //Track heater method, heat the track if temp goes under 69 degrees.
	  public void getTrackHeater(){
		  int tracktemperature;
		  if (tracktemperature < 69){
			  tracktemperature += 1.3;
		  }
	  }
}
