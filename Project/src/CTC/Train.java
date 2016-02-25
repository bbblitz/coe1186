public class Train {
	public BlockInterface currentBlock;
	public Route route;

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
	
	public BlockInterface getBlock() {
		return this.currentBlock;
	}
}
