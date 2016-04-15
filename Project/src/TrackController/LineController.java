import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class LineController
{
	private TrackController controllers[];
	private TrackModel model;
	private boolean[] routeBit;
	//private ArrayList<Integer[]> blockConversion; //for config files
	
	public LineController()
	{
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
	
	public LineController(TrackController[] controllers)
	{
		this.controllers = controllers;
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
	
	public boolean[] getSwitchStates()
	{
		int totalSwitchCount = 0;
		for(TrackController current : controllers)
		{
			totalSwitchCount += current.getSwitchCount();
		}
		boolean[] switches = new boolean[totalSwitchCount];
		
		int k=0;
		for(int i=0;i<controllers.length;i++)
		{
			boolean[] newSwitches = new boolean[controllers[i].getSwitchCount()];
			for(int j=0;i<controllers[i].getSwitchCount();j++)
			{
				switches[k++] = newSwitches[j];
			}
		}
		return switches;
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
		updateInputs(model.getBlockOccupancies());
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
			File[] controllerFiles = new File[config.nextInt()];
			config.nextLine();
			for(int i=0;i<controllerFiles.length;i++)
			{
				controllerFiles[i] = new File(config.nextLine());
			}
			return true;
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("config file(s) invalid");
			return false;
		}
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
			LCControllers[i].main(new String[0]);
		}
		
		LineController LC = new LineController(LCControllers);
	}
}
