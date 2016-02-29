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
}
