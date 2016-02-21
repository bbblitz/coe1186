import java.util.*;

public class Line{
  public ArrayList<BlockInterface> blocks = null;
  public String name = "Line";
  public int lineid;
  public Line(){
    line = new ArrayList<BlockInterface>();
  }

  public void add(BlockInterface trackpart){
    line.add(trackpart);
  }
}
