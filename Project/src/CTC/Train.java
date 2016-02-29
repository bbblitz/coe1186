public class Train {
	private BlockInterface currentBlock;
	private BlockInterface previousBlock;
	private Route route;

	public Train(Config config, int id, BlockInterface currentBlock, BlockInterface destinationBlock, long targetTime) {
		this.currentBlock = currentBlock;
		this.route = new Route(config, this, destinationBlock, targetTime);
		
		// create the actual train
		TrainModel newTrain = new TrainModel(id);
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
