import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class LineController
{
	private TrackController controllers[];
	private TrackModel model;
	private boolean[] routeBit;
	private boolean[] closed;
	private ArrayList<Integer[]> blockConversion; //for config files
	
	public LineController()
	{
		this.model = new TrackModel();
	}
	
	public LineController(TrackModel model)
	{
		this.model = model;
		loadConfigFile();
		//use GUI to load all files for TrackController
		//load config file to get defaults
	}
	
	public LineController(TrackController[] controllers, TrackModel model)
	{
		this.controllers = controllers;
		this.model = model;
	}
	
	public boolean loadConfigFile()
	{
		try
		{			
			File config = new File("LineControllerConfig.txt");
			Scanner configReader = new Scanner(config);
			Scanner lineConfig = new Scanner(new File(configReader.nextLine()));
			configReader.close();
			int fileCount = lineConfig.nextInt();
			File controllerFiles[] = new File[fileCount];
			lineConfig.nextLine();
			for(int i=0;i<fileCount;i++)
			{
				controllerFiles[i] = new File(lineConfig.nextLine());
			}
			TrackController[] controllers = new TrackController[fileCount];
			int index=0;
			for(File current : controllerFiles)
			{
				controllers[index++] = new TrackController(current);
			}
			this.controllers = controllers;
			return true;
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("config file(s) invalid");
			return false;
		}
	}
	
	public int getBlockCount()
	{
		int blockCount = 0;
		for(TrackController current : controllers)
		{
			blockCount += current.getBlockCount();
		}
		return blockCount;
	}
	
	public int getSwitchCount()
	{
		int totalSwitchCount = 0;
		for(TrackController current : controllers)
		{
			totalSwitchCount += current.getSwitchCount();
		}
		return totalSwitchCount;
	}
	
	public boolean[] getBlockOccupancies()
	{
		boolean[] out = model.getBlockOccupancies();
		return out;
	}
	
	public boolean[] getSwitchStates()
	{
		boolean[] lineSwitches = new boolean[getSwitchCount()];
		
		for(int i=0;i<controllers.length;i++)
		{
			boolean[] trackSwitches = controllers[i].getSwitchStates();
			for(int j=0;i<lineSwitches.length;j++)
			{
				lineSwitches[convetToLineSwitch(i,j)] = trackSwitches[i];
			}
		}
		return lineSwitches;
	}
	
	public boolean updateInputs(boolean[] inputs)
	{
		int maxInputLength = 0;
		boolean safe = true;
		for(TrackController current : controllers)
		{
			if(current.getInputCount()>maxInputLength) maxInputLength = current.getInputCount();
		}
		//System.out.println("max Inputs = "+maxInputLength);
		boolean[][] controllerInputs = new boolean[controllers.length][maxInputLength];
		for(int i=0;i<inputs.length;i++)
		{
			int[] conversion = convertToTrackInput(i);
			controllerInputs[conversion[0]][conversion[1]] = inputs[i];
		}
		for(int i=0;i<controllers.length;i++)
		{
			safe &= controllers[i].updateInputs(controllerInputs[i], routeBit);
		}
		return safe;
	}
	
	public void zeroAuthority(int blockID)
	{
		relayAuthority(0, blockID);
	}
		
	public void relayAuthority(int authority, int blockID)
	{
		model.relayAuthority(authority, blockID);
	}
	
	public void relaySpeed(int speed, int blockID)
	{
		model.relaySpeed(speed, blockID);
	}
	
	public TrackFailState[] getFailStates()
	{
		return model.getFailStates();
	}
	
	public int[] convertToTrackInput(int blockID)
	{
		int[] out = {-1, -1};
		for(int i=0;i<controllers.length;i++)
		{
			for(int j=0;j<controllers[i].getInputCount();j++)
			{
				if(controllers[i].inputConversion[j]==blockID)
				{
					out[0] = i;
					out[1] = j;
				}
			}
		}
		return out;
	}
	public int[] convertToTrackOutput(int blockID)
	{
		int[] out = {-1, -1};
		for(int i=0;i<controllers.length;i++)
		{
			for(int j=0;j<controllers[i].getBlockCount();j++)
			{
				if(controllers[i].outputConversion[j]==blockID)
				{
					out[0] = i;
					out[1] = j;
				}
			}
		}
		return out;
	}
	public int convertToLineInput(int controllerID, int blockID)
	{
		return controllers[controllerID].inputConversion[blockID];
	}
	public int convertToLineOutput(int controllerID, int blockID)
	{
		return controllers[controllerID].outputConversion[blockID];
	}
	
	public int[] convertToTrackSwitch(int switchID)
	{
		int[] out = {-1,-1};
		for(int i=0;i<controllers.length;i++)
		{
			for(int j=0;j<controllers[i].getSwitchCount();j++)
			{
				if (controllers[i].switchConversion[j] == switchID)
				{
					out[0] = i;
					out[1] = j;
				}
				return out;
			}
		}
		return out;
	}
	public int convetToLineSwitch(int controllerID, int switchID)
	{
		return controllers[controllerID].switchConversion[switchID];
	}
	
	public void nextTrainToYard(boolean toYard, int lineNumber)
	{
		//gets a bit from CTC for whether the next train should go into the yard or not
		routeBit[lineNumber] = toYard;
	}
	
	public void createTrain(int line)
	{
		model.createTrain(line);
	}
	
	public void closeBlock(int blockID, boolean value)
	{
		closed[blockID] = value;
	}
	
	public void tick(double deltaT)
	{
		boolean[] inputs = model.getBlockOccupancies();
		TrackFailState[] tfs = model.getFailStates();
		for(int i = 0;i<inputs.length;i++)
		{
			inputs[i] |= (tfs[i] != TrackFailState.FS_NORMAL);	//treats failstates and closed blocks as occupied blocks
			inputs[i] |= closed[i];
		}
		updateInputs(inputs);
		for(int i=0;i<controllers.length;i++)
		{
			boolean[] currentOutputs = controllers[i].getOutputs();
			for(int j=0;j<controllers[i].getBlockCount();j++)
			{
				if(currentOutputs[j])
				{
					zeroAuthority(convertToLineOutput(i, j));
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		//System.out.print("Enter the config file: ");
		LineController LC = new LineController();
		LC.loadConfigFile();
		/*TrackController[] LCControllers = new TrackController[controllerCount];
		for(int i=0;i<LCControllers.length;i++)
		{
			System.out.println("Creating TrackController "+i);
			LCControllers[i] = new TrackController();
		}*/
		TrackModel model = new TrackModel();
		model.blockCount = LC.getBlockCount();
		LC.tick(0);
	}
}
