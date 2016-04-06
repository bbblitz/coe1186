import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TrackControllerPrototype
{
	
	JFrame frame = new JFrame();
	
	JPanel[] panel = new JPanel[3];
	JLabel lineLabel = new JLabel("Line:");
	JComboBox line = new JComboBox(new String[] {"Prototype"});
	JLabel trackLabel = new JLabel("Track:");
	JComboBox track = new JComboBox();
	JLabel switchLabel = new JLabel("Switch Position:");
	JToggleButton setSwitch = new JToggleButton("Diverge Switch");
	JLabel crossingLabel = new JLabel("Railroad Crossing:");
	JToggleButton setCrossing = new JToggleButton("Close Crossing");
	JLabel trainLabel = new JLabel("Train Authority:");
	JTextField trainPresent = new JTextField("No Train Present");
	JLabel speedLabel = new JLabel("Track Speed:");
	JTextField trackSpeed = new JTextField("55 mph");
	
	public static void main(String args[])
	{
		new TrackControllerPrototype();
	}
	
	public TrackControllerPrototype()
	{
		
		frame.setSize(500, 500);
		frame.setLayout(new GridLayout(3,1));
		
		
		Track A = new Track("A");
		Track B1 = new Track("B1");
		Track B2 = new Track("B2");
		Crossing C1 = new Crossing("C1");
		Track C2 = new Track("C2");
		Switch S1 = new Switch("S1");
		Switch S2 = new Switch("S2");
		Track D = new Track("D");
		
		A.connectNext(S1);
		S1.connectNext(B1);
		S1.connectDivergent(C1);
		B1.connectNext(B2);
		C1.connectNext(C2);
		S2.next = C2;
		C2.next = S2;
		S2.prev = D;
		D.next = S2;
		
		Track[] protoLine = new Track[8];
		protoLine[0]=A;
		protoLine[1]=B1;		
		protoLine[2]=B2;
		protoLine[3]=C1;
		protoLine[4]=C2;
		protoLine[5]=D;
		protoLine[6]=S1;
		protoLine[7]=S2;
		
		track = new JComboBox(protoLine);
		
		
		
		for(int i=0; i<3; i++){
			panel[i] = new JPanel();
			
			frame.add(panel[i]);
		}
		panel[0].add(lineLabel);
		panel[0].add(line);
		panel[0].add(trackLabel);
		panel[0].add(track);
		panel[1].add(trainLabel);
		panel[1].add(trainPresent);
		panel[1].add(speedLabel);
		panel[1].add(trackSpeed);
		panel[2].add(switchLabel);
		panel[2].add(setSwitch);
		panel[2].add(crossingLabel);
		panel[2].add(setCrossing);
		
		
		frame.setVisible(true);
	}

}
