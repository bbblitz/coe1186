import java.util.*;

public class Route {
	public ArrayList<BlockInterface> route;
	public long targetTime;
	public Train train;

	public Route(Train train, BlockInterface to, long targetTime) {
		this.targetTime = targetTime;
		this.route = calculateRoute(train, to);
		this.train = train;
	}

	private ArrayList<BlockInterface> calculateRoute(Train train, BlockInterface destinationBlock) {
		ArrayList<BlockInterface> route = new ArrayList<BlockInterface>();

		BlockInterface sourceBlock = train.getCurrentBlock();

		BlockInterface currentBlock = sourceBlock;
		BlockInterface previousBlock = train.getPreviousBlock();

		route.add(currentBlock);

		currentBlock = currentBlock.goesto(previousBlock);
		while (currentBlock != destinationBlock) {
			BlockInterface nextBlock = currentBlock.goesto(previousBlock);

			route.add(nextBlock);
			previousBlock = currentBlock;
			currentBlock = nextBlock;
		}



		return route;
	}

	public double distanceToBlockAlongRoute(BlockInterface to) {
		double distance = 0;

		BlockInterface previousBlock = train.getPreviousBlock();
		BlockInterface currentBlock = train.getCurrentBlock();
		while (currentBlock != to) {
			distance += currentBlock.getLength();
			previousBlock = currentBlock;
			currentBlock = currentBlock.goesto(previousBlock);
		}
		return distance;
	}

	public BlockInterface getNextStationBlock() {
		for (int i = 0; i < route.size(); i++) {
			if (route.get(i) instanceof BlockStation) {
				return route.get(i);
			}
		}
		return null;
	}

	public double distanceToNextStation() {
		double distance = 0;
		for (int i = 0; i < route.size(); i++) {
			if (!(route.get(i) instanceof BlockStation)) {
				distance += route.get(i).getLength();
			}
		}
		return distance;
	}

	public double distanceToNextStationOrSwitch() {
		double distance = 0;
		for (int i = 0; i < route.size(); i++) {
			if (!(route.get(i) instanceof BlockStation) && !(route.get(i) instanceof BlockSwitch)) {
				distance += route.get(i).getLength();
			}
		}
		return distance;
	}

	/*
	// calculate a list of blocks to travel through, given a destination
	private ArrayList<BlockInterface> calculateRoute2(BlockInterface destinationBlock) {
		// do some magic to figure out a route from the yard to `destination`
		ArrayList<BlockInterface> route = new ArrayList<BlockInterface>();

		Line line = destinationBlock.getLine();
		BlockInterface currentBlock = line.blocks.get(0);	// start at the first block (yard?)

		Stack<BlockInterface> searcher = new Stack<BlockInterface>();

		// push the first block (yard block)
		searcher.push(currentBlock);
		currentBlock = currentBlock.getNext();

		// traverse the line until we find the destination
		while (currentBlock != destinationBlock) {
			searcher.push(currentBlock);
			currentBlock = currentBlock.getNext();
		}



		return route;
	}
	*/
}
