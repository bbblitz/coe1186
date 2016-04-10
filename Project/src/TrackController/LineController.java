import java.io.File;
import java.util.Scanner;

public class LineController
{
	private TrackController controllers[];
	private TrackModel model;
	
	public LineController(TrackController controller1, TrackController controller2, TrackModel model)
	{
		controllers = new TrackController[2];
		this.controllers[0] = controller1;
		this.controllers[1] = controller2;
		this.model = model;
	}
	
	public LineController(TrackController[] controllers)
	{
		int i = 0;
		for(TrackController current : controllers)
		{
			this.controllers[i++] = current;
		}
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
			int[] whatever = convertToTrackBlock(i);
			controllerInputs[whatever[0]][whatever[i]] = inputs[i];
		}
		for(int i=0;i<controllers.length;i++)
		{
			controllers[i].updateInputs(controllerInputs[i]);
		}
		/*boolean[] inputs1 = new boolean[8];
		boolean[] inputs2 = new boolean[8];
		for(int i=0;i<7;i++)
		{
			inputs1[i] = inputs[i];
		}
		inputs2[0] = inputs[7];
		for(int i=1;i<7;i++)
		{
			inputs2[i] = inputs[i+7];
		}
		inputs2[7] = inputs[6];
		controller1.updateInputs(inputs1);
		controller2.updateInputs(inputs2);*/
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
		//hard code blockID
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
	
	public TrackFailState[] getFailStates()
	{
		//return array of failstates
		//probably call model.getFailStates();
		return null;
	}
	
	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the amount of controllers: ");
		int controllerCount = keyboard.nextInt();
		TrackController[] LCControllers = new TrackController[controllerCount];
		for(TrackController current : LCControllers)
		{
			current = new TrackController();
		}
		/*TrackController TCA = new TrackController(new File("test1.plc"));
		TrackController TCB = new TrackController(new File("test2.plc"));
		TrackController TCC = new TrackController(new File("test1.plc"));
		TrackController TCD = new TrackController(new File("test2.plc"));
		//LineController LCA = new LineController(new TrackController[] {TCA, TCB});
		//LineController LCB = new LineController(new TrackController[] {TCC, TCD});
		
		boolean[] TCAInputs = new boolean[] {true, true, true};
		boolean[] TCBInputs = new boolean[] {true, true, true, true, true};
		boolean[] TCCInputs = new boolean[] {false, false, false};
		boolean[] TCDInputs = new boolean[] {false, false, false, false, false};
		//LCA.updateInputs(new boolean[][] {TCAInputs, TCBInputs});
		//LCB.updateInputs(new boolean[][] {TCCInputs, TCDInputs});
		for(int i=0;i<TCAInputs.length;i++)
		{
			System.out.println("TCA["+i+"] = "+TCA.outputs[i]);
		}
		for(int i=0;i<TCBInputs.length;i++)
		{
			System.out.println("TCB["+i+"] = "+TCB.outputs[i]);
		}
		for(int i=0;i<TCCInputs.length;i++)
		{
			System.out.println("TCC["+i+"] = "+TCC.outputs[i]);
		}
		for(int i=0;i<TCDInputs.length;i++)
		{
			System.out.println("TCD["+i+"] = "+TCD.outputs[i]);
		}*/
	}
}
