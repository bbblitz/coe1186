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

	public static int calculateTimeTo(Train t, BlockInterface end){
		BlockInterface sourceBlock = t.getCurrentBlock();
		BlockInterface currentBlock = sourceBlock;
		BlockInterface previousBlock = t.getPreviousBlock();

		int trainspeed = 20;
		int totaldistance = 0;

		//If trains are stopping just short or just long of their destination, then this code is the problem.

		totaldistance += currentBlock.getLength();
		currentBlock = currentBlock.goesto(previousBlock);
		while (currentBlock != end) {
			totaldistance += currentBlock.getLength();
			BlockInterface nextBlock = currentBlock.goesto(previousBlock);
			previousBlock = currentBlock;
			currentBlock = nextBlock;
		}

		return (totaldistance/trainspeed)*1000;
	}

	public static int calculateAuthority(Train t, int blockid){
    if(blockid == 228){
      return 0;
    }
    int totallength = 0;
    Train train = t;
    BlockInterface currentblock = train.getCurrentBlock();
    BlockInterface previousblock = train.getPreviousBlock();
    BlockInterface tmpblock = currentblock;
    while(tmpblock.getID() != blockid){
      BlockInterface tmp2 = tmpblock;
      tmpblock = tmpblock.goesto(previousblock);
      previousblock = tmp2;
      totallength += tmpblock.getLength();
    }
    return totallength;
  }

	public ArrayList<BlockInterface> calculateRoute(Train train, BlockInterface destinationBlock) {
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
