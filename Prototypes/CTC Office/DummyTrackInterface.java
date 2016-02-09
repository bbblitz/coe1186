
import java.util.*;


public abstract class DummyTrackInterface{
  private Infrastructure infra;
  private int blocknum;
  private float blockgrade;
  private float blockele;
  private int speedlimit;


  public abstract DummyTrackInterface goesto(DummyTrackInterface from);
  public Infrastructure getInfrastructure(){
    return infra;
  }
  public void setInfrastructure(Infrastructure i){
    infra = i;
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
