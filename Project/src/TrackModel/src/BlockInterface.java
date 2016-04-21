import java.util.*;
import java.awt.*;
/**
 * The overarching class for all blocks. All block types extend this class.
 */
public abstract class BlockInterface{
	/**
 * List of the types of infrastructure this block is
 * @see Infrastructure
 */
  //private ArrayList infrastructure;
  private int ID;
  private double gradeTailToHead;
  private double elevation;
  private double grade;
  private int speedLimit;
 // private TrackFailState failState;
  private double length;
	private boolean isoccupied;
	private int infrastructure;
	private int direction;
	private String BlockType;
	private String station;
	private String underground;
	private String crossing;
	



  /**
   * Getters and setters
   */
	
	public String getBlockType(){
	    return this.BlockType;
	  }
  
	  public void setBlockType(String BlockType){
	    this.BlockType = BlockType;
	  }
	  
	  public String getStation(){
		    return this.station;
		  }
		  public void setStation(String station){
		    this.station = station;
		  }
		  public String getUnderground(){
			    return this.underground;
			  }
			  public void setUnderground(String underground){
			    this.underground = underground;
			  }
			  
			  public String getCrossing(){
				    return this.crossing;
				  }
				  public void setCrossing(String crossing){
				    this.crossing = crossing;
				  }
		  
	  public double getInfrastructure(){
		    return this.infrastructure;
		  }
		  public void setInfrastructure(int infrastructure){
		    this.infrastructure = infrastructure;
		  }

	public boolean getOccupied(){
		return this.isoccupied;
	}
	public void setOccupied(boolean o){
		this.isoccupied = o;
	}
	
	public double getGrade(){
	    return this.grade;
	  }
	  public void setGrade(double grade){
	    this.grade = grade;
	  }

  public int getID(){
    return this.ID;
  }
  public void setID(int id){
    this.ID = id;
  }
  public double getGradeTailToHead(){
    return this.gradeTailToHead;
  }
  public void setGradeTailToHead(double gradeTailToHead){
    this.gradeTailToHead = gradeTailToHead;
  }
  public double getElevation(){
    return this.elevation;
  }
  public void setElevation(double elevation){
    this.elevation = elevation;
  }
  public int getSpeedLimit(){
    return this.speedLimit;
  }
  public void setSpeedLimit(int speedLimit){
    this.speedLimit = speedLimit;
  }
  
  public double getLength() {
	  return this.length;
  }
  public void setLength(double length) {
	  this.length = length;
  }

	/*Overload this in each type of track
public String toString(){
	//return String.format("Block interface string should not be used!");
}
*/
}