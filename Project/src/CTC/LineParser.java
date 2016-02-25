
import java.util.*;
import java.io.*;

public class LineParser{
  public ArrayList<Line> all = new ArrayList<Line>();
  public Line curline;
  public ArrayList<Integer> heads = new ArrayList<Integer>();
  public ArrayList<Integer> tails = new ArrayList<Integer>();
  public ArrayList<Integer> divergents = new ArrayList<Integer>();
  public Scanner s;
  public boolean incomment = false;
  public String fname;
  public Config c;
  public LineParser(String filename, Config config){
    fname = filename;
    c = config;
    ArrayList<BlockInterface> newline = new ArrayList<BlockInterface>();
    File f = new File(filename);
    s = new Scanner(f);
    while(s.hasNextLine()){
      String blockstring = s.nextLine();
      System.out.println("Parseing: " + blockstring);

    }
    if(incomment){
      System.out.printf("Error: while parseing %s, hit end of file while looking for end of comment",fname);
      return;
    }
    //config.aldl.add(linenum,newline);
  }

  public BlockPart parsePart(String p){
    if(p.equals("HEAD")){
      return BlockPart.SEC_HEAD;
    }else if(p.equals("TAIL")){
      return BlockPart.SEC_TAIL;
    }else if(p.equals("DIVERGENT")){
      return BlockPart.SEC_DIVERGENT;
    }
  }

  public void resolveblocks(){
    //Resolve all the pointers
    //Now we need to hook all the blocks up
    for(int i = 0; i < all.size(); i++){
      BlockInterface block = curline.blocks.get(i);
      if(block instanceof BlockStraight){
        BlockStraight blockstr = (BlockStraight)block;
        if(curline.blocks.get(heads.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!");
          return;
        }else{
          blockstr.head = curline.blocks.get(heads.get(i));
        }

        if(curline.blocks.get(tails.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!");
          return;
        }else{
          blockstr.tail = curline.blocks.get(tails.get(i));
        }
      }else if(block instanceof BlockCurved){
        BlockCurved blockcur = (BlockCurved)block;
        if(curline.blocks.get(heads.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!");
          return;
        }else{
          blockcur.head = curline.blocks.get(heads.get(i));
        }

        if(curline.blocks.get(tails.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!");
          return;
        }else{
          blockcur.tail = curline.blocks.get(tails.get(i));
        }
      }else if(block instanceof BlockSwitch){
        BlockSwitch blockswi = (BlockSwitch)block;
        if(curline.blocks.get(heads.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!");
          return;
        }else{
          blockswi.head = curline.blocks.get(heads.get(i));
        }

        if(curline.blocks.get(tails.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!");
          return;
        }else{
          blockswi.tail = curline.blocks.get(tails.get(i));
        }

        if(curline.blocks.get(divergents.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!");
          return;
        }else{
          blockswi.divergent = curline.blocks.get(divergents.get(i));
        }
      }



    }
  }

  public void parseLine(String blockstring){
    if(blockstring.substring(0,1).equals("#") || incomment){//Comment
      return;
    }
    if(blockstring.substring(0,2).equals("/*")){
      incomment = true;
      return;
    }else if(blockstring.substring(0,2).equals("*/")){
      incomment = false;
      return;
    }

    //Seperateing lines:
    if(blockstring.substring(0,4).equals("LINE")){
      String[] vs = blockstring.split(" ");
      if(vs[1].equals("END")){
        resolveblocks();
        all.add(curline);
        curline = null;
      }else{
        curline = new Line();
        curline.name = vs[1];
      }
    }

    if(blockstring.substring(0,3).equals("str")){//This block should be straight
      //Format: x, y, rotation, length, head, tail, headto, tailto, id
      //  public BlockStraight(int tx, int ty, int dir, int length block_head, block_tail, headto, tailto){
      String[] vs = blockstring.split(",");
      int x = Integer.parseInt(vs[0]);
      int y = Integer.parseInt(vs[1]);
      int direction = Integer.parseInt(vs[2]);
      int length = Integer.parseInt(vs[3]);
      int headid = Integer.parseInt(vs[4]);
      int tailid = Integer.parseInt(vs[5]);
      BlockPart headto = parsePart(vs[6]);
      BlockPart tailto = parsePart(vs[7]);
      int blockid = Integer.parseInt(vs[8]);
      BlockStraight newblock = new BlockStraight(x,y,direction,length);
      heads.add(blockid, headid);
      tails.add(blockid, tailid);
      curline.blocks.add(blockid, newblock);

    }else if(blockstring.substring(0,3).equals("cur")){//This block should be curved
      //Format: x, y, start_angle, angle_length, radius, block_clockwise, block_counterclockwise, block_clockwise_to, block_counterclockwise_to, block_id
      //public BlockCurved(int tx, int ty, int sa, int ea, int r){
      String[] vs = blockstring.split(",");
      int x = Integer.parseInt(vs[0]);
      int y = Integer.parseInt(vs[1]);
      int startang = Integer.parseInt(vs[2]);
      int anglen = Integer.parseInt(vs[3]);
      int radius = Integer.parseInt(vs[4]);
      int headid = Integer.parseInt(vs[5]);
      int tailid = Integer.parseInt(vs[6]);
      BlockPart headto = parsePart(vs[7]);
      BlockPart tailto = parsePart(vs[8]);
      int blockid = Integer.parseInt(vs[9]);
      BlockCurved newblock = new BlockCurved(x,y,startang,anglen,radius);
      heads.add(blockid,headid);
      tails.add(blockid,tailid);

      curline.blocks.add(newblock);

    }else if(blockstring.substring(0,3).equals("swi")){//This block should be a switch
      System.out.println("Detected switch block");
      String[] vs = blockstring.split(",");
      int x = Integer.parseInt(vs[0]);
      int y = Integer.parseInt(vs[1]);
      int direction = Integer.parseInt(vs[2]);
      int length = Integer.parseInt(vs[3]);
      int headid = Integer.parseInt(vs[4]);
      int tailid = Integer.parseInt(vs[5]);
      int divergentid = Integer.parseInt(vs[6]);
      BlockPart headto = parsePart(vs[7]);
      BlockPart tailto = parsePart(vs[8]);
      BlockPart divto = parsePart(vs[9]);
      int blockid = Integer.parseInt(vs[10]);

      BlockSwitch newblock = new BlockSwitch(x,y,direction,length);
      newblock.headto = headto;
      newblock.tailto = tailto;
      newblock.divergentto = divto;
      heads.add(blockid, headid);
      tails.add(blockid, tailid);
      divergents.add(blockid, divergentid);
      //Parse the next 3 parts
      /*
      for(int i = 0; i < 3; i++){
        if(!s.hasNextLine()){
          System.out.printf("Error: While parseing %s, hit the end of file while expecting switch parts", fname);
          return;
        }
        parseLine(s.nextLine());
        parseLine(s.nextLine());
        parseLine(s.nextLine());
        BlockInterface tail = curline.blocks.get(curline.blocks.size()-1);
        BlockInterface divergent = curline.blocks.get(curline.blocks.size()-2);
        BlockInterface head = curline.blocks.get(curline.blocks.size()-3);
        //Format:
        //public BlockSwitch(int tx, int ty, int d, int l){
        //Followed by 3 parts that make up the switch
        */
      }
    }
  }
}
