import java.util.*;

public class Line{
  public ArrayList<BlockInterface> line = null;
  public String name = "Line";
  public int lineid;
  public Line(){
    line = new ArrayList<BlockInterface>();
  }

  public void add(BlockInterface trackpart){
    line.add(trackpart);
  }
}
