import java.io.File;
public class TrackControllerTester
{
	public static void main(String args[])
	{
		TrackController GLT = null;
		TrackController GLM = null;
		TrackController GLB = null;
		TrackController RLT = null;
		TrackController RDT = null;
		TrackController RDB = null;
		TrackController RLB = null;
		String message="";
		int test = 1;
		System.out.println("Test "+test+": Load all PLC Files into separate Track Controllers");
		try
		{
			message = "Error Loading GreenLoopTop.plc";
			GLT = new TrackController(new File("GreenLoopTop.plc"));
			message = "Error Loading GreenLineMiddle.plc";
			GLM = new TrackController(new File("GreenLineMiddle.plc"));
			message = "Error Loading GreenLoopBottom.plc";
			GLB = new TrackController(new File("GreenLoopBottom.plc"));
			message = "Error Loading GreenLoopBottom.plc";
			RLT = new TrackController(new File("RedLineTopLoop.plc"));
			message = "Error Loading RedLineTopLoop.plc";
			RDT = new TrackController(new File("RedLineTopDiverge.plc"));
			message = "Error Loading RedLineTopDiverge.plc";
			RDB = new TrackController(new File("RedLineBottomDiverge.plc"));
			message = "Error Loading RedLineBottomDiverge.plc";
			RLB = new TrackController(new File("RedLineBottomLoop.plc"));
			message = "Error Loading RedLineBottomLoop.plc";
			System.out.println("Test 1 Passed");
		}
		catch (Exception e)
		{
			System.out.println("Test "+test+" Failed: "+message+" - "+e.getCause().getMessage());
		}
		test++;
		System.out.println("Test "+test+": Update Inputs for each Track Controller with all false boolean array");
		try
		{
			boolean safe = true;
			message = "Error with GLT";
			safe &= GLT.updateInputs(new boolean[GLT.getInputCount()]);
			message = "Error with GLM";
			safe &= GLM.updateInputs(new boolean[GLM.getInputCount()]);
			message = "Error with GLB";
			safe &= GLB.updateInputs(new boolean[GLB.getInputCount()]);
			message = "Error with RLT";
			safe &= RLT.updateInputs(new boolean[RLT.getInputCount()]);		//issue: throws empty stack exception fixed: 4/19 
			message = "Error with RDT";
			safe &= RDT.updateInputs(new boolean[RDT.getInputCount()]);
			message = "Error with RDB";
			safe &= RDB.updateInputs(new boolean[RDB.getInputCount()]);
			message = "Error with RLB";
			safe &= RLB.updateInputs(new boolean[RLB.getInputCount()]);
			if(!safe) throw new Exception("Safety Critical Architecthture caught inconsistant result from PLC file");
			System.out.println("Test "+test+" Passed");
		}
		catch(Exception e)
		{
			System.out.println("Test "+test+" failed: "+message+" - "+e.getCause().getMessage());
		}
		test++;
		System.out.println("Test "+test+": Load All TrackControllers into a LineController");
		LineController LC = null;
		TrackController[] controllers = new TrackController[7];
		try
		{
			message = "error with controller array";
			controllers[0] = GLT;
			controllers[1] = GLM;
			controllers[2] = GLB;
			controllers[3] = RLT;
			controllers[4] = RDT;
			controllers[5] = RDB;
			controllers[6] = RLB;
			LC = new LineController(controllers, new TrackModel());
			System.out.println("Test "+test+" Passed");
		}
		catch(Exception e)
		{
			System.out.println("Test "+test+" failed: "+message+" - "+e.getCause().getMessage());
		}
		
		
		test++;
		System.out.println("Test "+test+": Update Inputs of LineController with all false boolean array");
		try
		{
			message = "Error";
			if(!LC.updateInputs(new boolean[LC.getBlockCount()])) throw new Exception("Safety Critical Architecthture caught inconsistant result from PLC file");
			System.out.println("Test "+test+" Passed");
		}
		catch(Exception e)
		{
			System.out.println("Test "+test+" failed: "+message+" - "+e);
		}
		test++;
		System.out.println("Test "+test+": PLC Decoder OR operation");
		try
		{
			boolean[] inputs = {true, false, false, false, false};
			boolean[] outputs = PLCDecoder.decode(inputs, new File("test2.plc"));
			if(!outputs[0]) throw new Exception("OR operation with true resulted in false");
			inputs = new boolean[5];
			inputs[4] = true;
			outputs = PLCDecoder.decode(inputs, new File("test2.plc"));
			if(!outputs[0]) throw new Exception("OR operation with true resulted in false");
			inputs = new boolean[5];
			//for(boolean out : inputs) System.out.println(out);
			outputs = PLCDecoder.decode(inputs, new File("test2.plc"));
			if(outputs[0]) throw new Exception("OR operation with all false resulted in true");
			System.out.println("Test "+test+" Passed");
		}
		catch(Exception e)
		{
			System.out.println("Test "+test+" failed: "+message+" - "+e);
		}
		test++;
		System.out.println("Test "+test+": PLCDecoder AND Operation");
		try
		{
			boolean[] inputs = {true, true, true, true, true};
			boolean[] outputs = PLCDecoder.decode(inputs, new File("test2.plc"));
			if(!outputs[1]) throw new Exception("AND operation with all true resulted in false");
			inputs = new boolean[5];
			inputs[4] = true;
			outputs = PLCDecoder.decode(inputs, new File("test2.plc"));
			if(outputs[1]) throw new Exception("AND operation with one true resulted in true");
			inputs = new boolean[5];
			outputs = PLCDecoder.decode(inputs, new File("test2.plc"));
			if(outputs[1]) throw new Exception("AND operation with all false resulted in true");
			System.out.println("Test "+test+" Passed");
		}
		catch(Exception e)
		{
			System.out.println("Test "+test+" failed: "+message+" - "+e);
		}
		test++;
		System.out.println("Test "+test+": PLCDecoder NOT Operation");
		try
		{
			boolean[] inputs = {true, false, true, false, true};
			boolean[] outputs = PLCDecoder.decode(inputs, new File("test2.plc"));
			if(outputs[2]) throw new Exception("NOT true resulted in false");
			outputs = PLCDecoder.decode(inputs, new File("test2.plc"));
			if(!outputs[3]) throw new Exception("NOT false resulted in true");
			outputs = PLCDecoder.decode(inputs, new File("test2.plc"));
			if(!outputs[4]) throw new Exception("NOT NOT true resulted in false");
			System.out.println("Test "+test+" Passed");
		}
		catch(Exception e)
		{
			System.out.println("Test "+test+" failed: "+message+" - "+e);
		}
		test++;
		System.out.println("Test "+test+": PLCDecoder Route Information");
		try
		{
			boolean[] inputs = {true, false, true, false, true};
			boolean[] routeBit = {true, false};
			boolean[] outputs = PLCDecoder.decode(inputs, routeBit, new File("test2.plc"));
			if(!outputs[5]) throw new Exception("true route bit read as false");
			outputs = PLCDecoder.decode(inputs, new File("test2.plc"));
			if(outputs[6]) throw new Exception("false route bit read as true");
			System.out.println("Test "+test+" Passed");
		}
		catch(Exception e)
		{
			System.out.println("Test "+test+" failed: "+message+" - "+e);
		}
		test++;
		System.out.println("Test "+test+": ");
	}
}
