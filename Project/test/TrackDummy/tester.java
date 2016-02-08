/*
 * Tests the TrackDummy class
 */

public class tester{
  private static final int NUM_TO_CREATE = 10000;
  public static void main(String[] args){

    System.out.println("Testing TrackDummy.java");
    TrackDummy[] arr = new TrackDummy[NUM_TO_CREATE];
    long startTime = System.nanoTime();
    for(int i=0; i < arr.length; i++){
      arr[i] = new TrackDummy();
    }
    long endTime = System.nanoTime();
    float duration = (endTime-startTime)/1000000;
    System.out.printf("It took %f seconds to spawn %d TrackDummys",duration,NUM_TO_CREATE);
    
  }
}
