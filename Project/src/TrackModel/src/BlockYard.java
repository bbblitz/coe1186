

public class BlockYard extends BlockInterface{
  public int head;
  public int tail;
  

  //How long the track is
  public int length;


  /**
   *@override
   */
/*  public BlockInterface goesto(BlockInterface from){
    //System.out.println("Calling goesto on station block" + this.toString());
    if(from == head){
      //System.out.println("Returning " + tail.toString());
      return tail;
    }else{
      //System.out.println("Returning " + head.toString());
      return head;
    }
  }
*/
  public int getHead(){
	    return this.head;
	  }

public int getTail(){
	    return this.tail;
	  }

  public int setHead(int head) {
	  this.head = head;
	  return head;
  }

  public int setTail(int tail) {
	  this.tail = tail;
	  return tail;
  }


}