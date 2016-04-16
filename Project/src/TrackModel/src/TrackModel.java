
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
		  for(int i = 0; Trains.size; i++){
			  if (Train.get(trainid).distance > BlockInterface.get(ID).distance){
				  Train.get(trainid).distance -= BlockInterface.get(ID).distance;
				  track = BlockInterface[ID].head;
			  }  
		  }  
	  }
	  
	  
	  
	  
	  
	  
	  public void getFailState(){
		  
	  }
	  
	  
	  
	  
	  
}
