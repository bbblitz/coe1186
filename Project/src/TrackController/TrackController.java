import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TrackController
{
	File PLCFile;
	boolean[] inputs;
	boolean[] outputs;
	int blockCount;
	int switchCount;
	int crossingCount;
	
	public TrackController()
	{
		
	}
	
	public TrackController(File PLCFile) throws FileNotFoundException
	{
		this.PLCFile = PLCFile;
		Scanner PLCReader = new Scanner(PLCFile);
		PLCReader.nextInt();
		blockCount = PLCReader.nextInt();
		switchCount = PLCReader.nextInt();
		crossingCount = PLCReader.nextInt();
		PLCReader.close();
	}
	
	public void decode() throws Exception
	{
		boolean[] outputsRedundant;
		try
		{
			outputs = PLCDecoder.decode(inputs, PLCFile);
			outputsRedundant = PLCDecoder.decode(inputs, PLCFile);
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("PLC file invalid or missing");
			return;
		}
		catch(Exception e)
		{
			System.out.println("something went wrong");
			return;
		}
		/*if(outputs != outputsRedundant)
		{
			throw new Exception("Error With PLC Decoder");
		}*/
	}
	
	public void Tick(double deltaT)
	{
		//get inputs from track model
		//update inputs
		//send switch positions to track model
		//zero authority on all true tracks?
		for(int i=0;i<blockCount;i++)
		{
			if(outputs[i]) zeroAuthority(i);
		}
		//send speed to all blocks where speed changes?
		//relay occupancies to CTC
	}
	
	public void updateInputs(boolean[] inputs) throws Exception
	{
		this.inputs = inputs;
		decode();
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
	
	public void loadFile(File PLCFile)
	{
		this.PLCFile = PLCFile;
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
	
	public static void main(String[] args) throws Exception
	{
		TrackController GA = new TrackController(new File("GreenLoopTop.plc"));
		TrackController GB = new TrackController(new File("GreenLoopBottom.plc"));
		
		boolean[] GAInputs = new boolean[28];
		GAInputs[27] = true;
		GAInputs[20] = true;
		GAInputs[0] = true;
		
		boolean[] GBInputs = new boolean[24];
		GBInputs[3] = true;
		GBInputs[23] = true;
		
		GA.updateInputs(GAInputs);
		for(int i=0;i<GA.outputs.length;i++)
		{
			System.out.println("GA["+i+"] = "+GA.outputs[i]);
		}
		
		GB.updateInputs(GBInputs);
		for(int i=0;i<GB.outputs.length;i++)
		{
			System.out.println("GB["+i+"] = "+GB.outputs[i]);
		}
	}
	
	
}
