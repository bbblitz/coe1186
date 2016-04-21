import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TrackControllerWindow implements ActionListener
{
	public JFrame window;
	public JLabel[] blockLabel;
	public JLabel[] failLabel;
	public JLabel[] occupancyLabel;
	public JLabel[] lightLabel;
	public JLabel[] switchLabel;
	public JToggleButton[] switchButton;
	public boolean[] suggestedSwitch;
	
	public TrackControllerWindow()
	{
		prepareGUI(10,4);
	}
	
	public TrackControllerWindow(int blockCount, int switchCount)
	{
		prepareGUI(blockCount, switchCount);
	}
	
	public void prepareGUI(int blockCount, int switchCount)
	{
		JPanel allRows = new JPanel();
		allRows.setLayout(new BoxLayout(allRows, BoxLayout.Y_AXIS));
		
		switchLabel = new JLabel[switchCount];
		blockLabel = new JLabel[blockCount];
		failLabel = new JLabel[blockCount];
		lightLabel = new JLabel[blockCount];
		occupancyLabel = new JLabel[blockCount];
		suggestedSwitch = new boolean[switchCount];
		switchButton = new JToggleButton[switchCount];
		
		for(int i=0;i<blockCount;i++)
		{
			JPanel subPanel = new JPanel();
			blockLabel[i] = new JLabel("Block "+i+": ");
			occupancyLabel[i] = new JLabel("Not Occupied");
			failLabel[i] = new JLabel("Feeling pretty ok");
			lightLabel[i] = new JLabel("Lights: Off");
			
			subPanel.add(blockLabel[i]);
			subPanel.add(occupancyLabel[i]);
			subPanel.add(failLabel[i]);
			subPanel.add(lightLabel[i]);
			allRows.add(subPanel);
		}
		for(int i=0;i<switchCount;i++)
		{
			JPanel subPanel = new JPanel();
			switchButton[i] = new JToggleButton("Switch "+i);
			switchButton[i].addActionListener(this);
			switchLabel[i] = new JLabel("Switch "+i+" straight");
			subPanel.add(switchLabel[i]);
			subPanel.add(switchButton[i]);
			allRows.add(subPanel);
		}
		
		window = new JFrame("Track Controller");
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
	
	public void updateFailStates(TrackFailState[] failStates)
	{
		for(int i=0;i<failStates.length;i++)
		{
			failLabel[i].setText(failStates[i].toString());
		}
	}
	
	public void updateLights(boolean[] lights)
	{
		for(int i=0;i<lights.length;i++)
		{
			if(lights[i]) 
			{
				lightLabel[i].setText("Lights: On");
				lightLabel[i].setForeground(Color.red);
			}
			else
			{
				 lightLabel[i].setText("Lights: Off");
				lightLabel[i].setForeground(Color.black);
				 
			}
		}
	}

	public void updateOccupancies(boolean[] occupancies)
	{
		for(int i=0;i<occupancies.length;i++)
		{
			if(occupancies[i]) occupancyLabel[i].setText("Occupied");
			else occupancyLabel[i].setText("Not Occupied");
		}		
	}
	
	public void actionPerformed(ActionEvent action)
	{
		for(int i=0;i<switchButton.length;i++)
		{
			//System.out.println("Checking buttons");
			if(action.getSource() == switchButton[i])
			{
				//System.out.println("button pressed");
				suggestedSwitch[i] = ! suggestedSwitch[i];
				updateSwitches(suggestedSwitch);
				//System.out.println("Block "+i+" set to "+occupancy[i]);
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		TrackControllerWindow tcw = new TrackControllerWindow();
		TrackFailState[] failStates = new TrackFailState[10];
		for(int i=0;i<failStates.length;i++) failStates[i] = TrackFailState.FS_NORMAL;
		failStates[3] = TrackFailState.FS_TRACK_CIRCUIT_FAILURE;
		tcw.updateFailStates(failStates);
	}
}
