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
	  
	  public ArrayList<BlockInterface> getBlocksInTrackControllerSection(TrackController trackController) {
		  ArrayList<BlockInterface> blocks = new ArrayList<BlockInterface>();
		  
		  return blocks;
	  }
	  
	  // TODO: implement this
	  public BlockInterface getBlockFromTrackControllerOccupancyArray(TrackController trackController, int i) {
		  BlockInterface block = null;
		  
		  return block;
	  }
	  
	  // TODO: implement this
	  public BlockSwitch getBlockFromTrackControllerSwitchArray(TrackController trackController, int i) {
		  BlockSwitch block = null;
		  
		  return block;
	  }
	  
	  /*
	  public ArrayList<Train> getTrainsInTrackControllerSection(TrackController trackController) {
		  ArrayList<Train> allTrains = getAllTrains();
		  ArrayList<Train> trainsInSection = new ArrayList<Train>();
		  ArrayList<BlockInterface> blocksInSection = getBlocksInTrackControllerSection(trackController);
		  
		  for (Train train : allTrains) {
			  if (blocksInSection.contains(train.getCurrentBlock())) {
				  trainsInSection.add(train);
			  }
		  }
		  
		  return trainsInSection;
	  }
	  */
	  
	  public boolean isBlockOccupied(BlockInterface block) {
		  TrackController trackController = getTrackControllerFromBlock(block);
		  
		  return false;
	  }
}
