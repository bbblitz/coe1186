public class Train {
	private BlockInterface currentBlock;
	private BlockInterface previousBlock;
	private Route route;

	public Train(int id, BlockInterface currentBlock) {
		this.currentBlock = currentBlock;

		// create the actual train
		TrainModel newTrain = new TrainModel(/*id*/);
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
	
	public BlockInterface getPreviousBlock() {
		return this.previousBlock;
	}
}
