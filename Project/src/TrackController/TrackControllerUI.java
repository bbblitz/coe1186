import javax.swing.*;
import java.io.File;

public class TrackControllerUI {

	private static JFrame mainFrame;
	private static JFileChooser fileChooser;
	private static JButton loadButton;

	public static File loadFile()
	{
      	fileChooser = new JFileChooser();
      	fileChooser.setCurrentDirectory(new File("."));
      	fileChooser.setDialogTitle("Load a PLC file");
      	if(fileChooser.showOpenDialog(loadButton) == JFileChooser.APPROVE_OPTION);
      	return fileChooser.getSelectedFile();
	}
	
	public static void main(String[] args)
	{
		System.out.println("Loading File: "+loadFile());
	}

}
