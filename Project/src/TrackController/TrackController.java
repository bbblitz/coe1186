import java.io.File;
import java.io.FileNotFoundException;

public class TrackController
{
	File PLCFile;
	boolean[] inputs;
	boolean[] outputs;
	
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
			System.out.println("you done messed up");
			return;
		}
	}
	
	public void updateInputs(boolean[] inputs)
	{
		this.inputs = inputs;
		decode();
	}
	
	public void loadFile(File PLCFile)
	{
		this.PLCFile = PLCFile;
	}
	
	public static void main(String[] args)
	{
		TrackController TCA = new TrackController(new File("test1.plc"));
		TrackController TCB = new TrackController(new File("test2.plc"));
		
		TCA.updateInputs(new boolean[] {true, true, true});
		for(int i=0;i<TCA.outputs.length;i++)
		{
			System.out.println(TCA.outputs[i]);
		}
		TCB.updateInputs(new boolean[] {true, true, true, true, true});
		for(int i=0;i<TCB.outputs.length;i++)
		{
			System.out.println(TCB.outputs[i]);
		}
	}
	
	
}
