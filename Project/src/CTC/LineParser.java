
import java.util.*;
import java.io.*;

public class LineParser{
  public ArrayList<Line> all = new ArrayList<Line>();
  public Line curline;
  public ArrayList<Integer> heads;
  public ArrayList<Integer> tails;
  public ArrayList<Integer> divergents;
  public Scanner s;
  public boolean incomment = false;
  public String fname;
  public Config c;
  public int linenumber;
  public LineParser(String filename, Config config){
    fname = filename;
    c = config;
    ArrayList<BlockInterface> newline = new ArrayList<BlockInterface>(512);
    heads = new ArrayList<Integer>(512);
    tails = new ArrayList<Integer>(512);
    divergents = new ArrayList<Integer>(512);
    for(int i = 0; i < 512; i++){
      heads.add(i,-1);
      tails.add(i,-1);
      divergents.add(i,-1);
    }
    System.out.println("To start out, heads's size is:" + heads.size());
    File f = new File(filename);
    try{
      s = new Scanner(f);
    }catch(Exception e){
      System.out.println(e);
    }
    while(s.hasNextLine()){
      String blockstring = s.nextLine();

      System.out.println("Parseing: " + blockstring);
      System.out.printf("State is:\n");
      if(curline != null && curline.name != null)
        System.out.printf("\tcurline.name:%s\n",curline.name);
      System.out.printf("\tfname:%s\n",fname);
      System.out.printf("\tlinenumber:%d\n",linenumber);
      System.out.printf("\tincomment:%d\n",incomment?1:0);

      linenumber++;
      parseLine(blockstring);
    }
    if(incomment){
      System.out.printf("Error: while parseing %s, hit end of file while looking for end of comment\n",fname);
      return;
    }
    if(curline != null){
      System.out.printf("Error: while parseing %s, hit end of file while createing line %s\n",fname, curline.name);
      return;
    }
    System.out.println("Block parser finished");
    //resolveblocks();
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
    System.out.printf("Error: Could not parse %s, failed on line %d, could not parse part(%s)\n",fname,linenumber,p);
    return null;
  }

  public void resolveblocks(){
    /*
    System.out.println("Finished parseing line, resolveing blocks...");
    System.out.println("Heads is:");
    for(int i = 0; i < 8; i++){
      System.out.printf("\t%3d : %d\n",i,heads.get(i));
    }
    System.out.println("Tails is:");
    for(int i = 0; i < 8; i++){
      System.out.printf("\t%3d : %d\n",i,tails.get(i));
    }
    System.out.println("Lines are:");
    for(int i = 0; i < all.size(); i++){
      Line thisline = all.get(i);
      for(int j = 0; thisline.blocks.get(j) != null; j++){
        System.out.printf("\t%3d : ",j);
        System.out.printf("%3s\n",thisline.blocks.get(j).toString());
      }
    }
    */

    for(int i = 0; i < 8; i++){
      if(curline == null){
        System.out.println("\tCurrent line does not exist!");
        System.exit(1);
      }
      if(curline.blocks == null){
        System.out.println("\tNo blocks detected.");
        System.exit(1);
      }
      if(curline.blocks.get(i) == null){
        System.out.println("\tBlock " + i + " is null!");
      }else{
        System.out.printf("\t%3d :",i);
        System.out.printf("%3s\n", curline.blocks.get(i).toString());
      }
    }

    //Resolve all the pointers
    //Now we need to hook all the blocks up
    /*
    for(int i = 0; i < all.size(); i++){
      Line thisline = all.get(i);
      for(int j = 0; j < thisline.blocks.size(); j++){
        BlockInterface block = thisline.blocks.get(j);
        if(block instanceof BlockStraight){
          BlockStraight blockstr = (BlockStraight)block;
          if(curline.blocks.get(heads.get(i)) == null){
            System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
            return;
          }else{
            blockstr.head = curline.blocks.get(heads.get(i));
          }

          if(curline.blocks.get(tails.get(i)) == null){
            System.out.printf("Error: While parseing %s, track section %d's tail is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
            return;
          }else{
            blockstr.tail = curline.blocks.get(tails.get(i));
          }
        }else if(block instanceof BlockCurved){
          BlockCurved blockcur = (BlockCurved)block;
          System.out.println("Size of heads is " + heads.size() + " and i is " + i + " and curline.blocks size is " + curline.blocks.size());
          System.out.println("i is " + i);
          System.out.println("heads.get(i) is " + heads.get(i));
          //System.out.println("curline.blocks.get(heads.get(i)) is " + curline.blocks.get(heads.get(i)));
          if(heads.size() < i || curline.blocks.size() < heads.get(i) || heads.get(i) == -1 || curline.blocks.get(heads.get(i)) == null){
            System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
            return;
          }else{
            blockcur.head = curline.blocks.get(heads.get(i));
          }
        }else if(block instanceof BlockSwitch){
          BlockSwitch blockswi = (BlockSwitch)block;
          if(curline.blocks.get(heads.get(i)) == null){
            System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
            return;
          }else{
            blockswi.head = curline.blocks.get(heads.get(i));
          }

          if(curline.blocks.get(tails.get(i)) == null){
            System.out.printf("Error: While parseing %s, track section %d's tail is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
            return;
          }else{
            blockswi.tail = curline.blocks.get(tails.get(i));
          }

          if(curline.blocks.get(divergents.get(i)) == null){
            System.out.printf("Error: While parseing %s, track section %d's divergent is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
            return;
          }else{
            blockswi.divergent = curline.blocks.get(divergents.get(i));
          }
        }
        c.aldl.add(thisline);
        c.vislines.add(true);
      }
      */
    for(int i = 0; i < curline.blocks.size(); i++){
      BlockInterface block = curline.blocks.get(i);
      if(block instanceof BlockStraight){
        System.out.println("Blocks is 2");
        BlockStraight blockstr = (BlockStraight)block;
        if(curline.blocks.get(heads.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
          return;
        }else{
          blockstr.head = curline.blocks.get(heads.get(i));
        }

        if(curline.blocks.get(tails.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's tail is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
          return;
        }else{
          blockstr.tail = curline.blocks.get(tails.get(i));
        }
      }else if(block instanceof BlockCurved){
        BlockCurved blockcur = (BlockCurved)block;
        System.out.println("Size of heads is " + heads.size() + " and i is " + i + " and curline.blocks size is " + curline.blocks.size());
        System.out.println("i is " + i);
        System.out.println("heads.get(i) is " + heads.get(i));
        //System.out.println("curline.blocks.get(heads.get(i)) is " + curline.blocks.get(heads.get(i)));
        if(heads.size() < i || curline.blocks.size() < heads.get(i) || heads.get(i) == -1 || curline.blocks.get(heads.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
          return;
        }else{
          blockcur.head = curline.blocks.get(heads.get(i));
        }

        if(curline.blocks.get(tails.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's tail is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
          return;
        }else{
          blockcur.tail = curline.blocks.get(tails.get(i));
        }
      }else if(block instanceof BlockSwitch){
        BlockSwitch blockswi = (BlockSwitch)block;
        if(curline.blocks.get(heads.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's head is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
          return;
        }else{
          blockswi.head = curline.blocks.get(heads.get(i));
        }

        if(curline.blocks.get(tails.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's tail is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
          return;
        }else{
          blockswi.tail = curline.blocks.get(tails.get(i));
        }

        if(curline.blocks.get(divergents.get(i)) == null){
          System.out.printf("Error: While parseing %s, track section %d's divergent is connected to %d, but it dosen't exist!\n",fname,i,heads.get(i));
          return;
        }else{
          blockswi.divergent = curline.blocks.get(divergents.get(i));
        }
      }
    }
    c.aldl.add(curline);
    c.vislines.add(true);
  }

  public void parseLine(String blockstring){
    if(blockstring.isEmpty() || blockstring.substring(0,1).equals("#")){//Comment or blank line
      return;
    }
    if(blockstring.substring(0,2).equals("/*")){
      incomment = true;
      return;
    }else if(blockstring.substring(0,2).equals("*/")){
      incomment = false;
      return;
    }
    if(incomment){
      return;
    }
    if(blockstring.length() < 4){
      System.out.printf("Error parseing file %s on line %d: Incorrect syntax\n",fname,linenumber);
      System.exit(0);
    }

    //Seperateing lines:
    if(blockstring.substring(0,4).equals("LINE")){
      String[] vs = blockstring.split(" ");
      for(int i = 0; i < vs.length; i++){
        vs[i] = vs[i].trim();
      }
      if(vs[1].equals("END")){
        resolveblocks();
        all.add(curline);
        curline = null;
      }else{
        System.out.println("\nLine created");
        curline = new Line();
        curline.name = vs[1];
      }
    }else if(blockstring.substring(0,3).equals("str")){//This block should be straight
      //Format: x, y, rotation, length, head, tail, headto, tailto, id
      //  public BlockStraight(int tx, int ty, int dir, int length block_head, block_tail, headto, tailto){
      String[] vs = blockstring.split(",");
      for(int i = 0; i < vs.length; i++){
        vs[i] = vs[i].trim();
      }
      int x = Integer.parseInt(vs[1]);
      int y = Integer.parseInt(vs[2]);
      int direction = Integer.parseInt(vs[3]);
      int length = Integer.parseInt(vs[4]);
      int headid = Integer.parseInt(vs[5]);
      int tailid = Integer.parseInt(vs[6]);
      BlockPart headto = parsePart(vs[7]);
      BlockPart tailto = parsePart(vs[8]);
      int blockid = Integer.parseInt(vs[9]);
      BlockStraight newblock = new BlockStraight(x,y,direction,length);
      heads.add(blockid, headid);
      tails.add(blockid, tailid);
      curline.blocks.add(blockid, newblock);

    }else if(blockstring.substring(0,3).equals("cur")){//This block should be curved
      //Format: x, y, start_angle, angle_length, radius, block_clockwise, block_counterclockwise, block_clockwise_to, block_counterclockwise_to, block_id, grade
      //public BlockCurved(int tx, int ty, int sa, int ea, int r){
      String[] vs = blockstring.split(",");
      for(int i = 0; i < vs.length; i++){
        vs[i] = vs[i].trim();
      }
      int x = Integer.parseInt(vs[1]);
      int y = Integer.parseInt(vs[2]);
      int startang = Integer.parseInt(vs[3]);
      int anglen = Integer.parseInt(vs[4]);
      int radius = Integer.parseInt(vs[5]);
      int headid = Integer.parseInt(vs[6]);
      int tailid = Integer.parseInt(vs[7]);
      BlockPart headto = parsePart(vs[8]);
      BlockPart tailto = parsePart(vs[9]);
      int blockid = Integer.parseInt(vs[10]);
      float grade = Float.parseFloat(vs[11]);
      BlockCurved newblock = new BlockCurved(x,y,startang,anglen,radius);
      System.out.println("Size of heads:" + heads.size());
      heads.add(blockid,headid);
      tails.add(blockid,tailid);

      curline.blocks.add(blockid,newblock);

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
      curline.blocks.add(newblock);
      heads.add(blockid, headid);
      tails.add(blockid, tailid);
      divergents.add(blockid, divergentid);
    }else{
      System.out.printf("Unable to parse line: %d in %s\n",linenumber, fname);
    }
  }
}
