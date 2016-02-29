import java.util.*;

public class Line{
  public ArrayList<BlockInterface> blocks = null;
  public String name = "Line";
  public int lineid;
  public Line(){
    blocks = new ArrayList<BlockInterface>(512);
    for(int i = 0; i < 512; i++){
      blocks.add(i, null);
    }
  }

  public void add(BlockInterface block){
    blocks.add(block);
  }

  public String toString(){
    String output = "";
    for(int i = 0; i < blocks.size()-1;i++){
      if(blocks.get(i) != null){
        output += blocks.get(i).toString();
      }
    }
    return output;
  }
}
