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
  private ArrayList infrastructure;
  private int ID;
  private float gradeTailToHead;
  private float elevation;
  private int speedLimit;
 // private TrackFailState failState;
  private double length;
	private boolean isoccupied;



  /**
   * Getters and setters
   */

	public boolean getOccupied(){
		return this.isoccupied;
	}
	public void setOccupied(boolean o){
		this.isoccupied = o;
	}

 
  public int getID(){
    return this.ID;
  }
  public void setID(int id){
    this.ID = id;
  }
  public float getGradeTailToHead(){
    return this.gradeTailToHead;
  }
  public void setGradeTailToHead(float gradeTailToHead){
    this.gradeTailToHead = gradeTailToHead;
  }
  public float getElevation(){
    return this.elevation;
  }
  public void setElevation(float elevation){
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

	/*Overload this in each type of track*/
	public String toString(){
		return String.format("Block interface string should not be used!");
	}
}