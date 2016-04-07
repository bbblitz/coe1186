import java.io.File;

public class LineController
{
	TrackController controller1;
	TrackController controller2;
	TrackModel model;
	
	public LineController(TrackController controller1, TrackController controller2, TrackModel model)
	{
		this.controller1 = controller1;
		this.controller2 = controller2;
		this.model = model;
	}
		
	public void updateInputs(boolean[] inputs)
	{
		boolean[] inputs1 = new boolean[8];
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
		controller2.updateInputs(inputs2);
	}
	
	public boolean[] getBlockOccupancies()
	{
		boolean[] out = model.getBlockOccupancies();
		/*boolean[] out = new boolean[14];
		for(int i=0;i<7;i++)
		{
			out[i] = controller1.inputs[i];
			out[i+7] = controller2.inputs[i];
		}*/
		return out;
	}
	
	public boolean[] getSwitchStates()
	{
		boolean[] switches = new boolean[controller1.switchCount+controller2.switchCount];
		int j=0;
		for(int i=controller1.blockCount;i<controller1.blockCount+controller1.switchCount;i++)
		{
			switches[j++] = controller1.outputs[i];
		}
		for(int i=controller2.blockCount;i<controller1.blockCount+controller2.switchCount;i++)
		{
			switches[j++] = controller2.outputs[i];
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
		for(int i=0;i<controller1.blockCount;i++)
		{
			if(controller1.outputs[i])
			{
				zeroAuthority(i);
			}
		}
		for(int i=0;i<controller2.blockCount;i++)
		{
			if(controller2.outputs[i])
			{
				zeroAuthority(i+7);
			}
		}
	}
	
	public static void main(String[] args)
	{
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
