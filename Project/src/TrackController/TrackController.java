import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TrackController
{
	private File PLCFile;
	private boolean[] inputs;
	private boolean[] outputs;
	private boolean[] switches;
	private boolean[] crossings;
	private int inputCount;
	private int blockCount;
	private int switchCount;
	private int crossingCount;
	
	public TrackController()
	{
		loadFile();
	}
	
	public TrackController(File PLCFile)
	{
		loadFile(PLCFile);
	}
	
	private void setInputCount(int inputCount)
	{
		this.inputCount = inputCount;
	}
	private void setBlockCount(int blockCount)
	{
		this.blockCount = blockCount;
	}
	private void setSwitchCount(int switchCount)
	{
		this.switchCount = switchCount;
	}
	private void setCrossingCount(int crossingCount)
	{
		this.crossingCount = crossingCount;
	}
	public int getInputCount()
	{
		return inputCount;
	}
	public int getBlockCount()
	{
		return blockCount;
	}
	public int getSwitchCount()
	{
		return switchCount;
	}
	public int getCrossingCount()
	{
		return crossingCount;
	}
	public boolean[] getOutputs()
	{
		return outputs;
	}
	
	
	public boolean decode()
	{
		boolean[] allOutputs = PLCDecoder.decode(inputs, PLCFile);
		boolean[] outputsRedundant = PLCDecoder.decode(inputs, PLCFile);
		for(int i=0;i<outputs.length;i++)
		{
			if(allOutputs[i] != outputsRedundant[i])
			{
				System.out.println("Critical Error With PLC Decoder File: "+PLCFile);
				return false;
			}
		}
		for(int i=0;i<blockCount;i++)
		{
			outputs[i] = allOutputs[i];
		}
		for(int i=0;i<switchCount;i++)
		{
			switches[i] = allOutputs[i+blockCount];
		}
		for(int i=0;i<crossingCount;i++)
		{
			crossings[i] = allOutputs[i+blockCount+switchCount];
		}
		return true;
	}
	
	
	/*public void tick(double deltaT)
	{
		//get inputs from track model
		//send switch positions to track model
		//
		//zero authority on all true tracks?
		for(int i=0;i<blockCount;i++)
		{
			if(outputs[i]) zeroAuthority(i);
		}
		//send speed to all blocks where speed changes?
		//relay occupancies to CTC
	}*/
	
	public boolean updateInputs(boolean[] inputs)
	{
		this.inputs = inputs;
		return decode();
	}
	
	public boolean[] getBlockOccupancies()
	{
		return inputs;
	}
	
	public boolean[] getSwitchStates()
	{
		boolean[] out = new boolean[switchCount];
		for(int i=0;i<switchCount;i++)
		{
			out[i] = outputs[i];
		}
		return out;
	}
	
	public boolean[] getCrossingStates()
	{
		return crossings;
	}
	
	public void loadFile()
	{
		TrackControllerUI tcui = new TrackControllerUI(this);
	}
	
	public void loadFile(File PLCFile)
	{
		this.PLCFile = PLCFile;
		Scanner PLCReader = null;
		try
		{
			PLCReader = new Scanner(PLCFile);
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("file not found");
		}
		inputCount = PLCReader.nextInt();
		blockCount = PLCReader.nextInt();
		switchCount = PLCReader.nextInt();
		crossingCount = PLCReader.nextInt();
		System.out.println("inputCount = "+inputCount+" blockCount = "+blockCount+" switchCount = "+switchCount+" crossingCount = "+crossingCount);
		inputs = new boolean[inputCount];
		outputs = new boolean[blockCount];
		switches = new boolean[switchCount];
		crossings = new boolean[crossingCount];
		PLCReader.close();
	}
	
	public void relayAuthority(int authority, int blockID)
	{
		//tell track model to send authority signal to block
		
	}
	
	public void zeroAuthority(int blockID)
	{
		relayAuthority(0, blockID);
	}
	
	public void relaySpeed(float speed, int blockID)
	{
		//tell track model to send speed signal to block
	}
	
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		
		TrackController TCA = new TrackController(/*new File("test2.plc")*/
		);
		boolean[] TCAInputs = new boolean[TCA.inputs.length];
		for(int i=0;i<TCA.getInputCount();i++)
		{
			System.out.print("Enter a value for TCA block "+i+": ");
			String value = keyboard.nextLine();
			TCAInputs[i] = value.equalsIgnoreCase("true") || value.equals("1");
		}
		
		TCA.updateInputs(TCAInputs);
		
		for(int i=0;i<TCA.getBlockCount();i++)
		{
			System.out.println("TCA Block Out "+i+" = "+TCA.outputs[i]);
		}
		boolean[] TCASwitches = TCA.getSwitchStates();
		for(int i=0;i<TCA.getSwitchCount();i++)
		{
			System.out.println("TCA Switch State "+i+" = "+TCASwitches[i]);
		}
		for(int i=0;i<TCA.getCrossingCount();i++)
		{
			System.out.println("TCA Crossing State "+i+" = "+TCA.crossings[i]);
		}
		
	}
	
	
}
