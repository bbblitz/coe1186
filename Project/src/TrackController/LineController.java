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
	
	public LineController(TrackModel model)
	{
		this.model = model;
		loadConfigFile();
		//use GUI to load all files for TrackController
		//load config file to get defaults
	}
	
	//Constructor for Pink Line with 2 controllers
	public LineController(TrackController controller1, TrackController controller2, TrackModel model)
	{
		controllers = new TrackController[2];
		this.controllers[0] = controller1;
		this.controllers[1] = controller2;
		this.model = model;
	}
	
	public LineController(TrackController[] controllers, TrackModel model)
	{
		this.controllers = controllers;
		int blockCount = getBlockCount();
		this.model = model;
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
		
	public void updateInputs(boolean[] inputs)
	{
		int maxInputLength = 0;
		for(TrackController current : controllers)
		{
			if(current.getInputCount()>maxInputLength) maxInputLength = current.getInputCount();
		}
		System.out.println("max Inputs = "+maxInputLength);
		boolean[][] controllerInputs = new boolean[controllers.length][maxInputLength];
		for(int i=0;i<inputs.length;i++)
		{
			int[] conversion = convertToTrackBlock(i);
			controllerInputs[conversion[0]][conversion[i]] = inputs[i];
		}
		for(int i=0;i<controllers.length;i++)
		{
			controllers[i].updateInputs(controllerInputs[i]);
		}
	}
	
	public boolean[] getBlockOccupancies()
	{
		boolean[] out = model.getBlockOccupancies();
		return out;
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
	
	public void tick(double deltaT)
	{
		boolean[] inputs = model.getBlockOccupancies();
		TrackFailState[] tfs = model.getFailStates();
		for(int i = 0;i<inputs.length;i++)
		{
			inputs[i] |= (tfs[i] != TrackFailState.FS_NORMAL);	//treats failstates as occupied blocks
		}
		updateInputs(inputs);
		for(int i=0;i<controllers.length;i++)
		{
			boolean[] currentOutputs = controllers[i].getOutputs();
			for(int j=0;j<controllers[i].getBlockCount();j++)
			{
				if(currentOutputs[j])
				{
					zeroAuthority(convertToLineBlock(i, j));
				}
			}
		}
	}
	
	public int[] convertToTrackBlock(int blockID)
	{
		//hard code blockID (Pink Line) eventually use config file to get array?
		int[] out = new int[2];
		out[0] = blockID/7;
		if(out[0]==0)
		{
			out[1] = blockID;
		}
		else if(out[0]==1)
		{
			out[1] = blockID-7;
		}
		return out;
	}
	
	public int convertToLineBlock(int controllerID, int blockID)
	{
		int lineBlockID = 7*controllerID + blockID;
		return lineBlockID;
	}
	
	public int[] convertToTrackSwitch(int switchID)
	{
		int[] out = {0,0};
		return out;
	}
	public int convetToLineSwitch(int controllerID, int SwitchID)
	{
		return 0;
	}
	
	public void nextTrainToYard(boolean toYard, int lineNumber)
	{
		//gets a bit from CTC for whether the next train should go into the yard or not
		routeBit[lineNumber] = toYard;
	}
	
	public TrackFailState[] getFailStates()
	{
		return model.getFailStates();
	}
	
	public void createTrain(int line)
	{
		model.createTrain(line);
	}
	
	public boolean loadConfigFile()
	{
		try
		{			
			File config = new File("LineControllerConfig.txt");
			Scanner configReader = new Scanner(config);
			File lineConfig = new File(configReader.nextLine());
			File[] controllerFiles = new File[configReader.nextInt()];
			configReader.nextLine();
			for(int i=0;i<controllerFiles.length;i++)
			{
				controllerFiles[i] = new File(configReader.nextLine());
			}
			return true;
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("config file(s) invalid");
			return false;
		}
	}
	
	public void closeBlock(int blockID, boolean value)
	{
		closed[blockID] = value;
	}
	
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the amount of controllers: ");
		int controllerCount = keyboard.nextInt();
		TrackController[] LCControllers = new TrackController[controllerCount];
		for(int i=0;i<LCControllers.length;i++)
		{
			System.out.println("Creating TrackController "+i);
			LCControllers[i] = new TrackController();
		}
		LineController LC = new LineController(LCControllers, new TrackModel(LC.getBlockCount()));
		LC.tick(0);
	}
}
