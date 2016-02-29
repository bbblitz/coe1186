import java.util.ArrayList;


public class TrackControllerManager {

	  
	  public ArrayList<TrackController> greenLineTrackControllers;
	  public ArrayList<TrackController> redLineTrackControllers;
	  

	  public ArrayList<TrackController> getAllTrackControllers() {
		  ArrayList<TrackController> allTrackControllers = new ArrayList<TrackController>();
		  
		  for (TrackController train : this.greenLineTrackControllers) {
			  allTrackControllers.add(train);
		  
		  }
		  
		  for (TrackController train : this.redLineTrackControllers) {
			  allTrackControllers.add(train);
		  }
		  
		  return allTrackControllers;
	  }
	  
	  // TODO: implement this
	  private TrackController getTrackControllerFromBlock(BlockInterface block) {
		  TrackController trackController = null;
		  
		  return trackController;
	  }
	  
	  public ArrayList<BlockInterface> getBlocksInSection(TrackController trackController) {
		  ArrayList<BlockInterface> blocks = new ArrayList<BlockInterface>();
		  
		  return blocks;
	  }
	  
	  // TODO: implement this
	  public BlockInterface getBlockFromOccupancyArray(TrackController trackController, int i) {
		  BlockInterface block = null;
		  
		  return block;
	  }
	  
	  // TODO: implement this
	  public BlockSwitch getBlockFromSwitchArray(TrackController trackController, int i) {
		  BlockSwitch block = null;
		  
		  return block;
	  }
	  
	  // TODO: implement this
	  private int getOccupancyArrayIndexFromBlock(TrackController trackController, BlockInterface block) {
		  
		  return 0;
	  }
	  
	  // TODO: this has lots of nested searches :(
	  public boolean isBlockOccupied(BlockInterface block) {
		  TrackController trackController = getTrackControllerFromBlock(block);
		  ArrayList<Boolean> occupancies = trackController.getBlockOccupancies();
		  
		  int blockIndex = getOccupancyArrayIndexFromBlock(trackController, block);
		  return occupancies.get(blockIndex);
	  }
}
