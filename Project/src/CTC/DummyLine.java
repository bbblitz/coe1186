import java.util.*;

public class DummyLine{
  public ArrayList<DummyTrackInterface> line = null;
  public String name = "DummyLine";
  public int lineid;
  public DummyLine(){
    line = new ArrayList<DummyTrackInterface>();
  }

  public void add(DummyTrackInterface trackpart){
    line.add(trackpart);
  }
}
