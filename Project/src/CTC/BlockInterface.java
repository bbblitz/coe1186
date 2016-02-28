
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
  private float gradeTailToHead;
  private float elevation;
  private int speedLimit;
  private TrackFailState failState;
  private Line line;
  private double length;

  /**
   * Given a Block `from`, return the Block after this one
   * @param from
   * @return
   */
  public abstract BlockInterface goesto(BlockInterface from);
  
  public abstract BlockInterface getHead();
  public abstract BlockInterface getTail();
  
  /**
   * Get the next block. Returns the first block in its adjacentBlocks ArrayList, so only use if you're certain it has only 1 connected block (e.g. from the yard)
   * @return
   */
  /*public BlockInterface getNext() {
	  for (BlockInterface block : this.adjacentBlocks) {
		  if (adjacentBlockCanBeAccessed(block)) {
			  return block;
		  }
	  }
	  // no adjacent blocks that are accessible - did we end up in a corner?
	  return null;
  }*/
  
  /**
   * Get the next block (or blocks, if this is a switch), given a previous block.
   * @param exclude ArrayList<BlockInterface> Blocks to exclude from the search (e.g. the previous block)
   * @return ArrayList<BlockInterface> The next block(s) minus excluded blocks
   */
 /* public ArrayList<BlockInterface> getNext(ArrayList<BlockInterface> exclude) {
	  ArrayList<BlockInterface> nextBlocks = new ArrayList<BlockInterface>();
	  for (BlockInterface block : this.adjacentBlocks) {
		  if (!exclude.contains(block)) {
			  if (adjacentBlockCanBeAccessed(block)) {
				  nextBlocks.add(block);
			  }
		  }
	  }
	  return nextBlocks;
  }*/
  
  /**
   * From this block, is it possible to go to nextBlock? For example, switches are not bidirectional - an adjacent block might be inbound only
   * @param nextBlock The block we're trying to go to
   * @return boolean Is it possible to go to this block?
   * 
   * @todo IMPLEMENT THIS
   */
  /*private boolean adjacentBlockCanBeAccessed(BlockInterface nextBlock) {
	  return true;
  }*/
  
  
  /**
   * Getters and setters
   */
  
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
  public Line getLine() {
	  return this.line;
  }
  public void setLine(Line line) {
	  this.line = line;
  }
  public double getLength() {
	  return this.length;
  }
  public void setLength(double length) {
	  this.length = length;
  }
}
