
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class TrackModelParser
{
	private Scanner fileReader;
	
	public TrackModelParser(File filename) throws FileNotFoundException
	{
		fileReader = new Scanner(filename);
	}
	public TrackModelParser(String filename)
	{
		try
		{
			fileReader = new Scanner(new File(filename));
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("Couldn't parse Track File");
		}
	}
	
	public ArrayList<BlockInterface> readFile()
	{
		ArrayList<BlockInterface> track = new ArrayList<BlockInterface>();
		if(fileReader==null) return null;
		while(fileReader.hasNextLine()){
		int blockID = fileReader.nextInt();
		String type = fileReader.next();
		
		switch(type)
		{
			case "block":
			case "b":
				Block addBlock = new Block();
				addBlock.setTail(fileReader.nextInt());
				addBlock.setHead(fileReader.nextInt());
				addBlock.setLength(fileReader.nextDouble());
				addBlock.setGradeTailToHead(fileReader.nextDouble());
				addBlock.setSpeedLimit(fileReader.nextInt());
				addBlock.setBlockType(fileReader.next());
				addBlock.setStation(fileReader.next());
				addBlock.setCrossing(fileReader.next());
				addBlock.setElevation(fileReader.nextDouble());
				addBlock.setUnderground(fileReader.next());
				track.add(blockID, addBlock);
			    System.out.println("Added Block ID type block  " + blockID + " " + 
				addBlock.getTail() + " " + addBlock.getHead() + " " +  
				addBlock.getLength() + " " + addBlock.getGradeTailToHead() + " " + addBlock.getSpeedLimit()
				+ " " + addBlock.getBlockType()+ " " + addBlock.getStation()+ " " +
				addBlock.getCrossing()+ " " + addBlock.getElevation()+ " " + addBlock.getUnderground());
				if(fileReader.hasNextLine())fileReader.nextLine();
				break;
			case "switch":
			case "sw":
				BlockSwitch addSwitch = new BlockSwitch();
				addSwitch.setTail(fileReader.nextInt());
				addSwitch.setHead(fileReader.nextInt());
				addSwitch.setDivergent(fileReader.nextInt());
				addSwitch.setLength(fileReader.nextDouble());
				addSwitch.setGradeTailToHead(fileReader.nextDouble());
				addSwitch.setSpeedLimit(fileReader.nextInt());
				addSwitch.setBlockType(fileReader.next());
				addSwitch.setStation(fileReader.next());
				addSwitch.setCrossing(fileReader.next());
				addSwitch.setElevation(fileReader.nextDouble());
				addSwitch.setUnderground(fileReader.next());
				track.add(blockID, addSwitch);
				System.out.println("Added Block ID type Switch  " + blockID + " " + 
				addSwitch.getTail() + " " + addSwitch.getHead() + " " + addSwitch.getDivergent() + " " +
				addSwitch.getLength() + " " + addSwitch.getGradeTailToHead() + " " + addSwitch.getSpeedLimit()
				+ " " + addSwitch.getBlockType()+ " " + addSwitch.getStation()+ " " +
				addSwitch.getCrossing()+ " " + addSwitch.getElevation()+ " " + addSwitch.getUnderground());
				if(fileReader.hasNextLine())fileReader.nextLine();
				break;
			case "station":
			case "st":
				BlockStation addStation = new BlockStation();
				addStation.setTail(fileReader.nextInt());
				addStation.setHead(fileReader.nextInt());
				addStation.setLength(fileReader.nextDouble());
				addStation.setGradeTailToHead(fileReader.nextDouble());
				addStation.setSpeedLimit(fileReader.nextInt());
				addStation.setBlockType(fileReader.next());
				addStation.setStation(fileReader.next());
				addStation.setCrossing(fileReader.next());
				addStation.setElevation(fileReader.nextDouble());
				addStation.setUnderground(fileReader.next());
				track.add(blockID, addStation);
				System.out.println("Added Block ID type Station  " + blockID + " " + 
	            addStation.getTail() + " " + addStation.getHead() + " " +  
				addStation.getLength() + " " + addStation.getGradeTailToHead() + " " + addStation.getSpeedLimit()
				+ " " + addStation.getBlockType()+ " " + addStation.getStation()+ " " +
				addStation.getCrossing()+ " " + addStation.getElevation()+ " " + addStation.getUnderground());
				break;
			case "yard":
			case "y":
				BlockYard addYard = new BlockYard();
				addYard.setHead(fileReader.nextInt());
				track.add(blockID, addYard);
				fileReader.nextLine();
				System.out.print("Added Block ID type yard  " + blockID + "\n");
				break;
		}	
		}
		return track;	
	}
	
	
	
	public static void main(String[] args)
	{
		TrackModelParser tmp = new TrackModelParser("TrackM.txt");
		ArrayList<BlockInterface> out = tmp.readFile();
		System.out.println(out.get(3).getLength());
		//for(BlockInterface current : out)
		//System.out.println(out);
	}	
}
