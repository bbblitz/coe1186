import java.io.File;
import java.io.FileNotFoundException;

public class TrackController
{
	File PLCFile;
	boolean[] inputs;
	boolean[] outputs;
	int switchCount;
	
	public TrackController()
	{
		
	}
	
	public TrackController(File PLCFile)
	{
		this.PLCFile = PLCFile;
	}
	
	public void decode()
	{
		try
		{
			outputs = PLCDecoder.decode(inputs, PLCFile);
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("PLC file invalid or missing");
			return;
		}
	}
	
	public void updateInputs(boolean[] inputs)
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
		
	}
	
	public void zeroAuthority(int blockID)
	{
		relayAuthority(0, blockID);
	}
	
	public void relaySpeed()
	{
		
	}
	
	public static void main(String[] args)
	{
		TrackController TCA = new TrackController(new File("test1.plc"));
		TrackController TCB = new TrackController(new File("test2.plc"));
		
		TCA.updateInputs(new boolean[] {true, true, true});
		for(int i=0;i<TCA.outputs.length;i++)
		{
			System.out.println("TCA["+i+"] = "+TCA.outputs[i]);
		}
		TCB.updateInputs(new boolean[] {true, true, true, true, true});
		for(int i=0;i<TCB.outputs.length;i++)
		{
			System.out.println("TCB["+i+"] = "+TCB.outputs[i]);
		}
	}
	
	
}
