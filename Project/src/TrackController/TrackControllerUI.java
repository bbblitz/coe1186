import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;

public class TrackControllerUI {

	private JFrame mainFrame;
	private JFileChooser fileChooser;
	private JButton loadButton;
	private TrackController TC;
	
	public TrackControllerUI(TrackController TC)
	{
		this.TC = TC;
		this.prepareGUI();
	}

	private void prepareGUI() 
	{
      	this.fileChooser = new JFileChooser();
      	fileChooser.setCurrentDirectory(new File("."));
      	fileChooser.setDialogTitle("Load a PLC file");
      	if(fileChooser.showOpenDialog(loadButton) == JFileChooser.APPROVE_OPTION);
      	TC.loadFile(fileChooser.getSelectedFile());
	}
	
	public static void main(String[] args)
	{
		TrackControllerUI tcui = new TrackControllerUI(new TrackController());
	}

}
