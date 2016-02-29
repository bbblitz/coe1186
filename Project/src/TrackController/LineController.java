import java.io.File;

public class LineController
{
	TrackController[] controllers;
	
	public LineController(TrackController[] controllers)
	{
		this.controllers = controllers;
	}
	
	public void updateInputs(boolean[][] inputs)
	{
		for(int i = 0;i<controllers.length;i++)
		{
			controllers[i].updateInputs(inputs[i]);
		}
	}
	
	public static void main(String[] args)
	{
		TrackController TCA = new TrackController(new File("test1.plc"));
		TrackController TCB = new TrackController(new File("test2.plc"));
		TrackController TCC = new TrackController(new File("test1.plc"));
		TrackController TCD = new TrackController(new File("test2.plc"));
		LineController LCA = new LineController(new TrackController[] {TCA, TCB});
		LineController LCB = new LineController(new TrackController[] {TCC, TCD});
		
		boolean[] TCAInputs = new boolean[] {true, true, true};
		boolean[] TCBInputs = new boolean[] {true, true, true, true, true};
		boolean[] TCCInputs = new boolean[] {false, false, false};
		boolean[] TCDInputs = new boolean[] {false, false, false, false, false};
		LCA.updateInputs(new boolean[][] {TCAInputs, TCBInputs});
		LCB.updateInputs(new boolean[][] {TCCInputs, TCDInputs});
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
		}
	}
}
