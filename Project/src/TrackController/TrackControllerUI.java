import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;

public class TrackControllerUI {

	public JFrame mainFrame;
	public JPanel contentPane;
	public JLabel fileLabel;
	public JFileChooser fileChooser;
	public JButton loadButton;
	TrackController TC;
	
	public TrackControllerUI(TrackController TC) {
		this.TC = TC;
		this.prepareGUI();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new RefreshTask(this), 0, 100);
	}

	private void prepareGUI() {
		this.mainFrame = new JFrame("Train Controller");
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


      	this.contentPane = new JPanel();
      	this.contentPane.setOpaque(true);
      	this.contentPane.setLayout(null);

      	this.fileLabel = new JLabel("Load File");
      	this.fileLabel.setSize(300, 30);
      	this.fileLabel.setLocation(5, 5);
      	this.contentPane.add(this.fileLabel);
      	
      	this.loadButton = new JButton();
      	this.contentPane.add(loadButton);
      	
      	this.fileChooser = new JFileChooser();
      	fileChooser.setCurrentDirectory(new File("."));
      	fileChooser.setDialogTitle("Load a PLC file");
      	if(fileChooser.showOpenDialog(loadButton) == JFileChooser.APPROVE_OPTION);
      	TC.loadFile(fileChooser.getSelectedFile());
      	
      	//this.contentPane.add(fileChooser);

		/*this.mainFrame.setContentPane(this.contentPane);
		this.mainFrame.setSize(400, 400);
		this.mainFrame.setLocationByPlatform(true);
		this.mainFrame.setVisible(true);*/
	}

	public void log(String message) {
		
	}
	
	public static void main(String[] args)
	{
		TrackControllerUI tcui = new TrackControllerUI(new TrackController());
	}

}

class RefreshTask extends TimerTask {

	private TrackControllerUI trackControllerUI;

	public RefreshTask(TrackControllerUI trackControllerUI) {
		this.trackControllerUI = trackControllerUI;
	}

	@Override
	public void run() {
	}
}
