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
	int[] inputConversion;
	int[] outputConversion;
	int[] switchConversion;
	int crossingConversion;
	
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
	
	public void loadConversion(int[] inputConversion, int[] outputConversion, int[] switchConversion)
	{
		this.inputConversion = inputConversion;
		this.outputConversion = outputConversion;
		this.switchConversion = switchConversion;
	}
	
	public boolean updateInputs(boolean[] inputs)
	{
		boolean[] routeBit = {false, false};
		return updateInputs(inputs, routeBit);
	}
	
	public boolean updateInputs(boolean[] inputs, boolean[] routeBit)
	{
		this.inputs = inputs;
		return decode(routeBit);
	}
	
	public boolean decode(boolean[] routeBit)
	{
		boolean[] allOutputs = PLCDecoder.decode(inputs, routeBit, PLCFile);
		boolean[] outputsRedundant = PLCDecoder.decode(inputs, routeBit, PLCFile);
		for(int i=0;i<allOutputs.length;i++)
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
		inputConversion = readConversion(PLCReader, inputCount);
		blockCount = PLCReader.nextInt();
		outputConversion = readConversion(PLCReader, blockCount);
		switchCount = PLCReader.nextInt();
		switchConversion = new int[switchCount];
		int index = 0;
		while(PLCReader.hasNextInt())
		{
			switchConversion[index++] = PLCReader.nextInt();
		}
		PLCReader.nextLine();
		crossingCount = PLCReader.nextInt();
		if(crossingCount>0)	crossingConversion = PLCReader.nextInt();
		PLCReader.close();
		System.out.println("inputCount = "+inputCount+" blockCount = "+blockCount+" switchCount = "+switchCount+" crossingCount = "+crossingCount);
		inputs = new boolean[inputCount];
		outputs = new boolean[blockCount];
		switches = new boolean[switchCount];
		crossings = new boolean[crossingCount];
	}
	
	private int[] readConversion(Scanner configReader, int count)
	{
		int index = 0;
		int[] conversion = new int[count];
		while(configReader.hasNextInt())
		{
			int start = configReader.nextInt();
			String operation = configReader.next();
			if(operation.equals("-"))
			{
				int end = configReader.nextInt();
				System.out.print(start+" - "+end+",");
				for(int j=start;j<=end;j++)
				{
					conversion[index++] = j;
				}
			}
		}
		configReader.nextLine();
		return conversion;
	} 
	
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		
		TrackController TCA = new TrackController();
		//TCA.loadFile();
		boolean[] TCAInputs = new boolean[TCA.inputs.length];
		for(int i=0;i<TCA.getInputCount();i++)
		{
			System.out.print("Enter a value for TrackController block "+i+": ");
			String value = keyboard.nextLine();
			TCAInputs[i] = value.equalsIgnoreCase("true") || value.equals("1");
			//System.out.println(TCAInputs[i]);
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
