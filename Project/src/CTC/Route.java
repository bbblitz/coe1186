import java.util.*;

public class Route {
	public ArrayList<BlockInterface> route;
	public long targetTime;
	public Config config;

	public Route(Config config, BlockInterface destination, long targetTime) {
		this.targetTime = targetTime;
		this.route = calculateRoute(destination);
		this.config = config;
	}

	// calculate a list of blocks to travel through, given a destination
	private ArrayList<BlockInterface> calculateRoute(BlockInterface destinationBlock) {
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
}
