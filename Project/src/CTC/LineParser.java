
public class LineParser{
  public ArrayList<Line> all = new ArrayList<Line>();
  public ArrayList<Integer> heads = new ArrayList<Integer>();
  public ArrayList<Integer> tails = new ArrayList<Integer>();
  public Scanner s;
  public boolean incomment = false;
  public String fname;
  public LineParser(String filename,int linenum,Config config){
    fname = filename;
    ArrayList<BlockInterface> newline = new ArrayList<BlockInterface>();
    File f = new File(filename);
    s = new Scanner(f);
    while(s.hasNextLine()){
      String blockstring = s.getNextLine();
      System.out.println("Parseing: " + blockstring);

    }
    if(incomment){
      System.out.printf("Error: while parseing %s, hit end of file while looking for end of comment",fname);
    }
    config.aldl.set(linenum,newline);
  }

  public BlockPart parsePart(Strign p){
    if(p.equals("HEAD")){
      return BlockPart.SEC_HEAD;
    }else if(p.equals("TAIL")){
      return BlockPart.SEC_TAIL;
    }else if(p.equals("DIVERGENT")){
      return BlockPart.SEC_DIVERGENT;
    }
  }

  public void parseLine(String s){
    if(blockstring.sub(0,1).equals("#") || incomment){//Comment
      return;
    }
    if(blockstring.sub(0,2).equals("/*")){
      incomment = true;
      return;
    }else if(blockstring.sub(0,2).equals("*/")){
      incomment = false;
      return;
    }

    if(blockstring.sub(0,3).equals("str")){//This block should be straight
      //Format: x, y, rotation, length, head, tail, headto, tailto
      //  public BlockStraight(int tx, int ty, int dir, int length block_head, block_tail, headto, tailto){
      String[] vs = s.split(",");
      int x = Integer.parseInt(vs[0]);
      int y = Integer.parseInt(vs[1]);
      int direction = Integer.parseInt(vs[2]);
      int length = Integer.parseInt(vs[3]);
      int headid = Integer.parseInt(vs[4]);
      int tailid = Integer.parseInt(vs[5]);
      BlockPart headto = parsePart(vs[6]);
      BlockPart tailto = parsePart(vs[7]);
      BlockStraight newblock = new BlockStraight(x,y,direction,length);
      if(all.get(headid) != null){

      }
      if(all.get(Integer.parseInt(vs[4])))
      all.add(newblock);

    }else if(blockstring.sub(0,3).equals("cur")){//This block should be curved
      //Format: x, y, start_angle, angle_length, radius, block_clockwise, block_counterclockwise, block_clockwise_to, block_counterclockwise_to
      //public BlockCurved(int tx, int ty, int sa, int ea, int r){
      String[] vs = s.split(",");
      BlockCurved newblock = new BlockCurved(vs[0],vs[1],vs[2],vs[3], vs[4]);

      all.add(newblock);

    }else if(blockstring.sub(0,3).equals("swi")){//This block should be a switch
      System.out.println("Detected switch block");
      //Parse the next 3 parts
      for(int i = 0; i < 3; i++){
        if(!s.hasNextLine()){
          System.out.printf("Error: While parseing %s, hit the end of file while expecting switch parts", fname);
          return;
        }
        String headstring = parseLine(s.nextLine());
        String divergentstring = parseLine(s.nextLine());
        String tailstring = parseLine(s.nextLine());
        BlockPart tail = all.get(all.size()-1);
        BlockPart divergent = all.get(all.size()-2);
        BlockPart head = all.get(all.size()-3);
        //Format:
        //public BlockSwitch(int tx, int ty, int d, int l){
        //Followed by 3 parts that make up the switch
        BlockSwitch newblock = new BlockSwitch()

      }
    }
  }
}
