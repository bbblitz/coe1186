import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestTrackModelUI implements ActionListener
{
	public JFrame window;
	public JPanel allRows;
	public JTextArea log;
	public JLabel[] blockLabel;
	public JLabel[] switchLabel;
	public JCheckBox[] occupied;
	public JComboBox[] failstate;
	public boolean[] occupancy;
	public boolean[] switches;
	public TrackFailState[] tfs;
	
	public TestTrackModelUI()
	{
		prepareGUI(100, 3);
	}
	
	public TestTrackModelUI(int blockCount, int switchCount)
	{
		prepareGUI(blockCount, switchCount);
	}
	
	public void prepareGUI(int blockCount, int switchCount)
	{
		JPanel allRows = new JPanel();
		allRows.setLayout(new BoxLayout(allRows, BoxLayout.Y_AXIS));
		log = new JTextArea(5,30);
		allRows.add(new JScrollPane(log));
		
		switchLabel = new JLabel[switchCount];
		blockLabel = new JLabel[blockCount];
		occupied = new JCheckBox[blockCount];
		failstate = new JComboBox[blockCount];
		occupancy = new boolean[blockCount];
		tfs = new TrackFailState[blockCount];
		
		for(int i=0;i<blockCount;i++)
		{
			JPanel subPanel = new JPanel();
			blockLabel[i] = new JLabel("Block "+i+": ");
			occupied[i] = new JCheckBox("Occupied");
			String[] failstates = {"FS_NORMAL","FS_BROKEN_RAIL","FS_POWER_FAILURE","FS_TRACK_CIRCUIT_FAILURE","FS_RAIL_AND_POWER","FS_CIRCUIT_AND_RAIL","FS_CIRCUIT_AND_POWER","FS_CIRCUIT_RAIL_POWER"};
			failstate[i] = new JComboBox(failstates);
			tfs[i] = TrackFailState.FS_NORMAL;
			occupied[i].addActionListener(this);
			failstate[i].addActionListener(this);
		
			subPanel.add(blockLabel[i]);
			subPanel.add(occupied[i]);
			subPanel.add(failstate[i]);
			allRows.add(subPanel);
		}
		for(int i=0;i<switchCount;i++)
		{
			JPanel subPanel = new JPanel();
			switchLabel[i] = new JLabel("Switch "+i+" straight");
			subPanel.add(switchLabel[i]);
			allRows.add(subPanel);
		}
		
		window = new JFrame("Track Model Test");
		JScrollPane scroller = new JScrollPane(allRows);
		window.add(scroller);
		window.pack();
		window.setSize(400, 600);
		window.setVisible(true);
		
	}
	
	public void updateSwitches(boolean[] switches)
	{		
		for(int i=0;i<switches.length;i++)
		{
			if(switches[i])
			{
				switchLabel[i].setText("Switch "+i+" divergent");
				switchLabel[i].setForeground(Color.red);
			}
			else
			{
				switchLabel[i].setText("Switch "+i+" straight");
				switchLabel[i].setForeground(Color.green);				
			}
		}
	}
	
	public TrackFailState getFailStateFromString(String fsstr){
		if(fsstr.equals("FS_NORMAL"))return TrackFailState.FS_NORMAL;
		else if(fsstr.equals("FS_BROKEN_RAIL"))return TrackFailState.FS_BROKEN_RAIL;
		else if(fsstr.equals("FS_POWER_FAILURE"))return TrackFailState.FS_POWER_FAILURE;
		else if(fsstr.equals("FS_TRACK_CIRCUIT_FAILURE"))return TrackFailState.FS_TRACK_CIRCUIT_FAILURE;
		else if(fsstr.equals("FS_RAIL_AND_POWER"))return TrackFailState.FS_RAIL_AND_POWER;
		else if(fsstr.equals("FS_CIRCUIT_AND_RAIL"))return TrackFailState.FS_CIRCUIT_AND_RAIL;
		else if(fsstr.equals("FS_CIRCUIT_AND_POWER"))return TrackFailState.FS_CIRCUIT_AND_POWER;
		else if(fsstr.equals("FS_CIRCUIT_RAIL_POWER"))return TrackFailState.FS_CIRCUIT_RAIL_POWER;
		else return TrackFailState.FS_NORMAL;
	}
	
	
	public void actionPerformed(ActionEvent action)
	{
		for(int i=0;i<occupied.length;i++)
		{
			//System.out.println("Checking buttons");
			if(action.getSource() == occupied[i])
			{
				//System.out.println("button pressed");
				occupancy[i] = ! occupancy[i]; 
				//System.out.println("Block "+i+" set to "+occupancy[i]);
			}
			if(action.getSource() == failstate[i])
			{
				tfs[i] = getFailStateFromString((String)failstate[i].getSelectedItem());
				//System.out.println("Block "+i+" set to "+tfs[i]);
			}
		}
	}
	
	public static void main(String args[])
	{
		TestTrackModelUI ttmui = new TestTrackModelUI();
	}
}