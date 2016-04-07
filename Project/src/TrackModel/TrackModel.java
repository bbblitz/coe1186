/*Dummy track model so we can start working on the main
*/

public class TrackModel{

	private TrainModel train;

  public TrackModel(){
  	TrainModel dummyTrain = new TrainModel("IAmAFakeTrain", this);
  }

  public void tick(long deltaT) {
  	this.train.tick(deltaT);
  }

  public void relayAuthority(int authority, int blockID) {
  	this.train.receiveAuthority(authority);
  }

  public void receiveSpeed(int speed, int blockID) {
  	this.train.receiveSpeed(speed);
  }

  public void receiveDistance(double deltaX) {

  }

  public boolean[] getBlockOccupancies() {
  	boolean[] test = new boolean[2];
  	return test;
  }
}
