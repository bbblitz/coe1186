

public class TrackPane extends JPanel{

  public TrackPane(){
    super();
  }
  public TrackPane(boolean db){
    super(db);
  }
  public TrackPane(LayoutManager lm){
    super(lm);
  }
  public TrackPane(LayoutManager lm, boolean db){
    super(lm,db);
  }
  public void tick(){
    System.out.println("I've ticked!");
  }
}
