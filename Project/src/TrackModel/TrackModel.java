/*Dummy track model so we can start working on the main
*/

public class TrackModel{

	private TrainModel train;

  public TrackModel(){
  	this.train = new TrainModel("IAmAFakeTrain", this);
  }

  public void tick(long deltaT) {
    System.out.println("TrackModel tick");
  	this.train.tick(deltaT);
  }

  public void relayAuthority(int authority, int blockID) {
  	this.train.receiveAuthority(authority);
  }

  public void relaySpeed(int speed, int blockID) {
  	this.train.receiveSpeed(speed);
    System.out.println(speed);
  }

  public void receiveDistance(double deltaX) {

  }

  public boolean[] getBlockOccupancies() {
  	boolean[] test = new boolean[14];
  	return test;
  }
}
