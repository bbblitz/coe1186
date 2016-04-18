

public class BlockYard extends BlockInterface{
  public BlockInterface head;
  public BlockInterface tail;
  

  //How long the track is
  public int length;



  /**
   *@override
   */
  public BlockInterface goesto(BlockInterface from){
    //System.out.println("Calling goesto on station block" + this.toString());
    if(from == head){
      //System.out.println("Returning " + tail.toString());
      return tail;
    }else{
      //System.out.println("Returning " + head.toString());
      return head;
    }
  }

  public BlockInterface getHead() {
	  return this.head;
  }

  public BlockInterface getTail() {
	  return this.tail;
  }


}