public class Train {
	public BlockInterface currentBlock;
	public Route route;

	public Train(int id, BlockInterface currentBlock, Route route) {
		this.currentBlock = currentBlock;
		this.route = route;

		// create the actual train
		TrainModel newTrain = new TrainModel(/*id*/);
	}
}
