import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LineControllerUI implements ActionListener
{
	private JFrame window;
	private JPanel allrows;
	private JLabel[] controllerLabels;
	private JTextArea[] fileText;
	private JButton[] loadButton;
	private JButton loadAll;
	private File[] controllerFiles;

	public LineControllerUI()
	{
		this.prepareGUI(new File("PinkLineConfig.txt"));
	}
	
	public LineControllerUI(File configFile)
	{
		this.prepareGUI(configFile);
	}
	
	public static File[] loadFiles(File configFile)
	{
		LineControllerUI lcui = new LineControllerUI(configFile);
		boolean notDoneYet;
		do
		{
			notDoneYet = false;
			for(int i=0;i<lcui.controllerFiles.length;i++)
			{
				if(lcui.controllerFiles[i] == null)
				{
					notDoneYet = true;
					break;
				}
			}
		}while(notDoneYet);
		lcui.close();
		return lcui.controllerFiles;
	}
	
	public void prepareGUI(File configFile)
	{
		Scanner configReader = null;
		try
		{
			configReader = new Scanner(configFile);
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("configFile Missing");
		}
		int fileCount = configReader.nextInt();
		configReader.nextLine();
		

		
		controllerFiles = new File[fileCount];
		allrows = new JPanel();
		allrows.setLayout(new BoxLayout(allrows, BoxLayout.Y_AXIS));
		controllerLabels = new JLabel[fileCount];
		fileText = new JTextArea[fileCount];
		loadButton = new JButton[fileCount];
		
		JPanel header = new JPanel();
		header.add(new JLabel("Files Loaded From Config File: "+configFile.toString()));
		
		allrows.add(header);
		
		for(int i=0;i<fileCount;i++)
		{
			JPanel l = new JPanel();
			controllerLabels[i] = new JLabel("Track Controller "+i);
			fileText[i] = new JTextArea(2,30);
			fileText[i].setText(configReader.nextLine());
			JScrollPane scroller = new JScrollPane(fileText[i]);
			loadButton[i] = new JButton("Load PLC File");
			loadButton[i].addActionListener(this);
			l.add(controllerLabels[i]);
			l.add(scroller);
			l.add(loadButton[i]);
			allrows.add(l);
		}
		loadAll = new JButton("Load All PLC Files");
		loadAll.addActionListener(this);
		allrows.add(loadAll);
		
		window = new JFrame("Load Files for Track Controller");
		window.add(allrows);
		window.pack();
		window.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent action)
	{
		if(action.getSource() == loadAll)
		{
			for(int i=0;i<fileText.length;i++)
			{
				String filename = fileText[i].getText();
				//System.out.println("sent file: "+filename);
				controllerFiles[i] = new File(filename);
			}
		}
		for(int i=0;i<loadButton.length;i++)
		{
			//System.out.println("Checking buttons");
			if(action.getSource() == loadButton[i])
			{
				//System.out.println("button pressed");
				File filename = TrackControllerUI.loadFile();
				//System.out.println("loaded file: "+filename);
				fileText[i].setText(filename.toString());
			}
		}
	}
	
	public void close()
	{
		window.setVisible(false);
		window.dispose();
	}
	
	public static void main(String args[])
	{
		File[] controllerFiles = loadFiles(new File("RedGreenLineConfig.txt"));
		for(int i=0;i<controllerFiles.length;i++)
		{
			System.out.println(controllerFiles[i]);
		}
	}
}
