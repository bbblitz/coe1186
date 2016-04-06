/*
 * Tests the TrackDummy class
 */

public class tester{
  private static final int NUM_TO_CREATE = 10000;
  public static void main(String[] args){

    System.out.println("Testing TrackType.java");
    for (TrackType t : TrackType.values()){
      System.out.println("" + t);
    }
  }
}
