import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CTCWindow extends JFrame{

	private Config config;
	private final int WIDTH = 1200;
	private final int HEIGHT = 600;

	public CTCWindow(LineController lc) {
		super("CTC Office");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setJMenuBar(new TopRibbon());

		config = new Config();
		config.windowDim = new Dimension(WIDTH,HEIGHT);
		config.lineController = lc;

		LineParser parser = new LineParser("track.txt",config);

		//TODO:Remove this
		BlockInterface startblock = config.aldl.get(0).blocks.get(10);
		BlockInterface preblock = config.aldl.get(0).blocks.get(11);
		System.out.println("Start block is:");
		System.out.println(startblock.toString());
		Train ntrain = new Train(config, 0, startblock,preblock);
		config.pinkLineTrains.add(ntrain);

		JPanel holder = new JPanel();
    holder.setLayout(new BoxLayout(holder,BoxLayout.X_AXIS));
    JPanel tp = new TrackPane(config);
    config.trackpane = tp;
    System.out.println("config.trackpane is: " + config.trackpane);
    JPanel dp = new DetailPane(config);
    Dimension d = new Dimension((int)(WIDTH*0.7),HEIGHT);
    Dimension d2 = new Dimension((int)(WIDTH*0.3),HEIGHT);
    tp.setPreferredSize(d);
    dp.setPreferredSize(d2);
    holder.add(tp);
    holder.add(dp);

    super.getContentPane().add(holder);
    ((DetailPane)dp).createComponent();
    //JLabel label = new JLabel("Hello, world!");
    //window.getContentPane().add(label);
    super.pack();
    super.setVisible(true);
	}

	public void tick(double deltaT) {
		boolean[] blocksoccupied = config.lineController.getBlockOccupancies();
		ArrayList<Train> alltrains = config.pinkLineTrains;
		for(Train train : alltrains){
			//The train is not on an occupied block, move it forward
			if(train == null){
				System.out.println("Train was null!");
			}else{
				if(train.getCurrentBlock() == null){
					System.out.println("Block that train was on is null!");
					System.exit(-1);
				}else{
					if(!blocksoccupied[train.getCurrentBlock().getID()]){
						train.moveForwardOneBlock();
					}
				}
			}
		}
		//Update block occupancies for drawing
		for(Line l : config.aldl){
			for(BlockInterface bi : l.blocks){
				if(bi == null){
					//System.out.println("Found a null block");
				}else{
					if(bi.getID() >= blocksoccupied.length){
						System.out.println("blocksoccupied not big enough! " + bi.getID());
					}else{
						if(config.DEBUG_COMMUNICATION){
							System.out.printf("Block %d is set to %s\n", bi.getID(), blocksoccupied[bi.getID()]?"true":"false");
						}
						bi.setOccupied(blocksoccupied[bi.getID()]);
					}
				}
			}
		}

		config.trackpane.repaint();
		/*
		//LineController lc = config.lineController();
		ArrayList<Train> allTrains = config.getAllTrains();
		ArrayList<LineController> allTrackControllers = config.lineController.getAllTrackControllers();

		// flip all switches to correct position
		for (LineController trackController : allTrackControllers) {
			// get occupancy+switchstate arrays
			// process arrays
			boolean[] tcgss = trackController.getSwitchStates();
			ArrayList<Boolean> switchStates = new ArrayList<Boolean>(tcgss.length);
			for(int i = 0; i < tcgss.length; i++){
				switchStates.set(i,tcgss[i]);
			}
			for (int i = 0; i < switchStates.size(); i++) {
				// turn index into BlockSwitch, then set it flipped or unflipped
				BlockSwitch blockSwitch = config.trackControllerManager.getBlockFromSwitchArray(trackController, i);
				boolean flipped = switchStates.get(i);
				blockSwitch.setFlipped(flipped);
			}
		}

		// move trains forward if their current block is no longer occupied
		for (Train train : allTrains) {
			// if train.getCurrentBlock() is not occupied according to track controller(s)
			// then move train forward one block
			BlockInterface trainCurrentBlock = train.getCurrentBlock();
			boolean trainBlockOccupied = config.trackControllerManager.isBlockOccupied(trainCurrentBlock);
			if (!trainBlockOccupied) {
				// this train must have moved forward one block since its last known block is not occupied
				train.moveForwardOneBlock();
			}
		}
		*/
	}

}
