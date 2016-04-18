import java.util.*;

public class Train {
	private int trainid;
	private BlockInterface currentBlock;
	private BlockInterface previousBlock;
	private Route route;
	public Map<String, Integer> schedule;
	public Config c;
	public boolean dispatched;

	public Train(Config config, Integer id, BlockInterface currentBlock, BlockInterface previousBlock) {
		this.dispatched = false;
		this.c = config;
		this.currentBlock = currentBlock;
		this.previousBlock = previousBlock;
		//this.route = new Route(this, destinationBlock, targetTime);
		this.trainid = id;
		// create the actual train TODO:Uncomment?
		//TrainModel newTrain = new TrainModel(id);
		schedule = new TreeMap<String, Integer>();
	}

	public int getID(){
		return trainid;
	}

	public void setID(int n){
		trainid = n;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Route getRoute() {
		return this.route;
	}

	public BlockInterface getCurrentBlock() {
		return this.currentBlock;
	}

	public void moveForwardOneBlock() {
		//System.out.println("Train gong forward 1 block...");
		BlockInterface nextBlock = this.currentBlock.goesto(previousBlock);
		this.previousBlock = this.currentBlock;
		this.currentBlock = nextBlock;
	}

	public BlockInterface getPreviousBlock() {
		return this.previousBlock;
	}

	public BlockInterface getAnticipatedNextBlock() {
		return currentBlock.goesto(previousBlock);
	}

	public boolean isFacingHead() {
		return (currentBlock.goesto(previousBlock) != currentBlock.getTail());
	}

	public double getGrade() {
		if (isFacingHead()) {
			return currentBlock.getGradeTailToHead();
		} else {
			return (-1 * currentBlock.getGradeTailToHead());
		}
	}

	public double distanceToNextStation() {
		return route.distanceToNextStation();
	}

	public double distanceToNextStationOrSwitch() {
		return route.distanceToNextStationOrSwitch();
	}
}
