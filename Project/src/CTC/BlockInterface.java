
import java.util.*;

/**
 * The overarching class for all blocks. All block types extend this class.
 */
public abstract class BlockInterface{
	/**
	 * List of the types of infrastructure this block is
	 * @see Infrastructure 
	 */
  private ArrayList<Infrastructure> infrastructure;
  private int ID;
  private float grade;
  private float elevation;
  private int speedLimit;

  private TrackFailState failState;
  
  private ArrayList<BlockInterface> adjacentBlocks;

  /**
   * Given a Block `from`, return the Block after this one
   * @param from
   * @return
   */
  public abstract BlockInterface goesto(BlockInterface from);
  
  public ArrayList<Infrastructure> getInfrastructure(){
    return this.infrastructure;
  }
  public void addInfrastructure(Infrastructure infrastructure){
    this.infrastructure.add(infrastructure);
  }
  public TrackFailState getFailState(){
    return this.failState;
  }
  public void setFailState(TrackFailState failState){
    this.failState = failState;
  }
  public int getID(){
    return this.ID;
  }
  public void setID(int id){
    this.ID = id;
  }
  public float getGrade(){
    return this.grade;
  }
  public void setGrade(float grade){
    this.grade = grade;
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
  public ArrayList<BlockInterface> getAdjacentBlocks() {
	  return this.adjacentBlocks;
  }
  public void setAdjacentBlocks(ArrayList<BlockInterface> adjacentBlocks) {
	  this.adjacentBlocks = adjacentBlocks;
  }
}
