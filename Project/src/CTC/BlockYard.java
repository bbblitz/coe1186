import java.util.*;
import java.awt.*;

public class BlockYard extends BlockInterface{
	public BlockInterface head;
	public BlockPart headto;

	//The x offset from some origin
	public int x;

	//The y offset from some origin
	public int y;

	public BlockInterface goesto(BlockInterface from){
		return null;
	}

	public BlockInterface getHead(){
		return null;
	}
	public BlockInterface getTail(){
		return null;
	}
	public void drawBlock(Graphics g){

	}
	public void drawTrainOn(Graphics g, boolean on){

	}
}
