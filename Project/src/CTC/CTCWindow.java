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
		/*
		BlockInterface startblock = config.aldl.get(0).blocks.get(10);
		BlockInterface preblock = config.aldl.get(0).blocks.get(11);
		System.out.println("Start block is:");
		System.out.println(startblock.toString());
		Train ntrain = new Train(config, 0, startblock,preblock);
		//ntrain.schedule.put("First Ave",5000);
		config.alltrains.add(ntrain);
		*/


		JPanel holder = new JPanel();
    holder.setLayout(new BoxLayout(holder,BoxLayout.X_AXIS));
    JPanel tp = new TrackPane(config);
    config.trackpane = tp;
    JPanel dp = new DetailPane(config);
		//JPanel dp = new JPanel();
    Dimension d = new Dimension((int)(WIDTH*0.7),HEIGHT);
    Dimension d2 = new Dimension((int)(WIDTH*0.3),HEIGHT);
    tp.setPreferredSize(d);
    dp.setPreferredSize(d2);

    holder.add(tp);
    holder.add(dp);

    super.getContentPane().add(holder);
    //((DetailPane)dp).createComponent();
    //JLabel label = new JLabel("Hello, world!");
    //window.getContentPane().add(label);
    super.pack();
    super.setVisible(true);
	}

	public void tick(double deltaT) {
		boolean[] blocksoccupied = config.lineController.getBlockOccupancies();
		TrackFailState[] failstates = config.lineController.getBlockFailStates();
		ArrayList<Train> alltrains = config.alltrains;
		boolean[] switchstates = config.lineController.getSwitchStates();
		//The wierd switch mapping
		//((BlockSwitch)config.aldl.get(0).blocks.get(16)).flipped = switchstates[6];
		((BlockSwitch)config.aldl.get(0).blocks.get(27)).flipped = switchstates[7];
		((BlockSwitch)config.aldl.get(0).blocks.get(33)).flipped = switchstates[8];
		((BlockSwitch)config.aldl.get(0).blocks.get(38)).flipped = switchstates[9];
		((BlockSwitch)config.aldl.get(0).blocks.get(44)).flipped = switchstates[10];
		((BlockSwitch)config.aldl.get(0).blocks.get(52)).flipped = switchstates[11];
		((BlockSwitch)config.aldl.get(0).blocks.get(9)).flipped = switchstates[12];

		try{
		for(Train train : alltrains){
				//System.out.println("Looping over alltrains");
				//The train is not on an occupied block, move it forward
				if(train == null){
					System.out.println("Train was null!");
				}else{
					if(train.getCurrentBlock() == null){
						System.out.println("Block that train was on is null!");
						System.exit(-1);
					}else{
						if(!blocksoccupied[train.getCurrentBlock().getID()]){
							if(train.getAnticipatedNextBlock() instanceof BlockYard){
								alltrains.remove(train);
								((DispatchPane)config.dispatchpane).trainlist.removeItem(train.getID());
								((SchedulePane)config.schedulepane).trainnum.removeItem(train.getID());
							}else{
								//System.out.println("Moveing train forward one block!");
								train.moveForwardOneBlock();
								//System.out.println("Train is now on block:");
								//System.out.println(train.getCurrentBlock());
							}
						}
					}
				}

				if(train != null && train.getRoute() != null){
					ArrayList<BlockInterface> b = train.getRoute().route;
					BlockInterface dest = b.get(b.size());
					//Route the train the correct way if it wants to go into the yard
					if(train.getCurrentBlock().getID() == 4){ //red line
						if(dest instanceof BlockYard || dest.getID() == 77){
							config.lineController.routeToYard(true,0);
						}else{
							config.lineController.routeToYard(false,0);
						}
					}else if(train.getCurrentBlock().getID() == 132){ //yard block
						if(dest instanceof BlockYard || dest.getID() == 229){
							config.lineController.routeToYard(true,0);
						}else{
							config.lineController.routeToYard(false,0);
						}
					}

					//Check to see if a train is at it's destination, and if it is, un-dispatch it.
					ArrayList<BlockInterface> r = train.getRoute().route;
					if(r.get(r.size()) == train.getCurrentBlock()){
						train.dispatched = false;
					}
				}

				//Check to see if the train can make it's scheduleing on time, and if it can't, disptach it.
				BlockInterface station = null;
				String nextstationto = null;
				if(train.schedule.size() > 0)
					nextstationto = (String)train.schedule.keySet().toArray()[0];
				//System.out.println("I think the next station is: " + nextstationto);
				if(nextstationto != null && !train.dispatched){
					if(train == null){
						System.out.println("Error 1001: Train was null!");
						System.exit(1);
					}
					if(train.getCurrentBlock() == null){
						System.out.println("Error 1010: Train's current block was null!");
					}
					int linenum = (train.getCurrentBlock().getID() < 78)?0:1;
					for(BlockInterface bi : config.aldl.get(linenum).blocks){
						if(bi instanceof BlockStation){
							BlockStation bs = (BlockStation)bi;
							if(bs.stationName.equals(nextstationto)){
								station = bi;
							}
						}
					}
					int timeto = Route.calculateTimeTo(train,station);
					int targettime = train.schedule.get(nextstationto);
					//System.out.printf("current time:%d\ttimeto:%d\ttarget time:%d\n",config.time,timeto,targettime);
					if(config.time + timeto > targettime){
						//System.out.println("Dispatching a train!");
						int auth = Route.calculateAuthority(train, station.getID());
						int speed = config.CONSTANT_SPEED;
						config.lineController.relayAuthority(auth, train.getCurrentBlock().getID());
						config.lineController.relaySpeed(speed,train.getCurrentBlock().getID());
						train.dispatched = true;
					}
				}
			}
		}
		catch (Exception e){
			System.out.println("Error 001: You're createing trains too fast! Slow down!");
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
						bi.setFailState(failstates[bi.getID()]);
					}
				}
			}
		}

		config.trackpane.repaint();
		config.time += deltaT;
		System.out.printf("%d elapsed\n",config.time);

	}
}
