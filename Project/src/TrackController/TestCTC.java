import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestCTC implements ActionListener
{
	public JFrame window;
	public JPanel panel;
	public JComboBox blockBox;
	public JTextArea authorityText;
	public JTextArea speedText;
	public JButton send;
	private int select;
	private LineController LC;
	
	public TestCTC()
	{
		LC = new LineController();
		prepareGUI(231);
		while(true)
		{
			tick(0);
		}
	}
	
	public void prepareGUI(int blockCount)
	{
		window = new JFrame("CTC Test");
		panel = new JPanel();
		send = new JButton("Send Speed and Authority");
		
		Integer[] blockIDs = new Integer[blockCount];
		for(int i=0;i<blockCount;i++) blockIDs[i] = i;
		blockBox = new JComboBox(blockIDs);
		blockBox.addActionListener(this);
		send.addActionListener(this);
		
		authorityText = new JTextArea(1,5);
		speedText = new JTextArea(1,5);
		panel.add(new JLabel("Authority: "));
		panel.add(authorityText);
		panel.add(new JLabel("Speed: "));
		panel.add(speedText);
		panel.add(new JLabel("Block: "));
		panel.add(blockBox);
		panel.add(send);
		window.add(panel);
		window.pack();
		window.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent action)
	{
		if(action.getSource() == send)
		{
			LC.relayAuthority(Integer.parseInt(authorityText.getText()), select);
			LC.relaySpeed(Integer.parseInt(speedText.getText()), select);
		}
		if(action.getSource() == blockBox)
		{
			select = (int)(Integer)blockBox.getSelectedItem();
			//System.out.println("selected block "+select);
		}
	}
	
	public void tick(double deltaT)
	{
		LC.tick(deltaT);
	}
	
	public static void main(String args[])
	{
		TestCTC CTC = new TestCTC();
	}
	
	
	
}
