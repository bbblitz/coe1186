import java.util.*;

public class Route {
	public ArrayList<BlockInterface> route;
	public long targetTime;

	public Route(Config config, BlockInterface destination, long targetTime) {
		this.targetTime = targetTime;
		this.route = calculateRoute(destination);
	}

	// calculate a list of blocks to travel through, given a destination
	private ArrayList<BlockInterface> calculateRoute(BlockInterface destination) {
		// do some magic to figure out a route from the yard to `destination`
		
		return null;
	}
}
