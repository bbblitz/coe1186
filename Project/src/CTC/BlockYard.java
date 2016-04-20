import java.util.*;
import java.awt.*;

public class BlockYard extends BlockInterface{
	public BlockInterface head;
	public BlockPart headto;

	//The x offset from some origin
	public int x;
	public static final int width = 100;
	public static final int height = 50;
	//The y offset from some origin
	public int y;

	public BlockInterface goesto(BlockInterface from){
		return head;
	}

	public BlockInterface getHead(){
		return head;
	}
	public BlockInterface getTail(){
		return null;
	}
	public void drawBlock(Graphics g){
		g.drawRect(x,y,width,height);
		g.drawString("YARD",x,y);
	}
	public void drawTrainOn(Graphics g, boolean on){

	}
}
