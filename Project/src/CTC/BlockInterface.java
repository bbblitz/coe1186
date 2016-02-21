
import java.util.*;

public abstract class BlockInterface{
  private ArrayList<Infrastructure> infra;
  private int blocknum;
  private float blockgrade;
  private float blockele;
  private int speedlimit;

  private TrackFailState fs;

  public abstract BlockInterface goesto(BlockInterface from);
  public ArrayList<Infrastructure> getInfrastructure(){
    return infra;
  }
  public void addInfrastructure(Infrastructure i){
    infra.add(i);
  }
  public TrackFailState getFailState(){
    return fs;
  }
  public void setFailState(TrackFailState f){
    fs = f;
  }
  public int getBlockNum(){
    return blocknum;
  }
  public void setBlockNum(int n){
    blocknum = n;
  }
  public float getBlockGrade(){
    return blockgrade;
  }
  public void setBlockGrade(float f){
    blockgrade = f;
  }
  public float getBlockEle(){
    return blockele;
  }
  public void setBlockEle(float f){
    blockele = f;
  }
  public int getSpeedLimit(){
    return speedlimit;
  }
  public void setSpeedLimit(int i){
    speedlimit = i;
  }
}
