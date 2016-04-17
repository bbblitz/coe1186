
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
		int blockID = fileReader.nextInt();
		String type = fileReader.next();
		switch(type)
		{
			case "block":
			case "b":
				Block addBlock = new Block();
				addBlock.setLength(fileReader.nextDouble());
				addBlock.setGradeTailToHead(fileReader.nextDouble());
				addBlock.setHead(fileReader.nextInt());
				addBlock.setTail(fileReader.nextInt());
				track.add(blockID, addBlock);
				fileReader.nextLine();
				break;
			case "switch":
			case "sw":
				BlockSwitch addSwitch = new BlockSwitch();
				addSwitch.setLength(fileReader.nextDouble());
				addSwitch.setGradeTailToHead(fileReader.nextDouble());
				addSwitch.setHead(fileReader.nextInt());
				addSwitch.setTail(fileReader.nextInt());
				addSwitch.setDivergent(fileReader.nextInt());
				track.add(blockID, addSwitch);
				fileReader.nextLine();
				break;
			case "station":
			case "st":
				BlockStation addStation = new BlockStation();
				addStation.setLength(fileReader.nextDouble());
				addStation.setGradeTailToHead(fileReader.nextDouble());
				addStation.setHead(fileReader.nextInt());
				addStation.setTail(fileReader.nextInt());
				addStation.setStationName(fileReader.nextLine());
				track.add(blockID, addStation);
				break;
			case "yard":
			case "y":
				BlockYard addYard = new BlockYard();
				addYard.setHead(fileReader.nextInt());
				track.add(blockID, addYard);
				fileReader.nextLine();
				break;
		}		
	}
	
	public static void main(String[] args)
	{
		TrackModelParser tmp = new TrackModelParser("dummyTrack.txt");
		ArrayList<BlockInterface> out = tmp.readFile();
		for(BlockInterface current : out)
		{
			System.out.println("block "+current.getID());
		}
	}
	
}
