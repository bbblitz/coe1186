
public class LineParser{
  public ArrayList<Line> all = new ArrayList<Line>();
  public LineParser(String filename,int linenum,Config config){
    ArrayList<BlockInterface> newline = new ArrayList<BlockInterface>();
    File f = new File(filename);
    Scanner s = new Scanner(f);
    while(s.hasNextLine()){
      String blockstring = s.getNextLine();
      if(blockstring.sub(0,3).equals("str")){//This block should be straight

      }else if(blockstring.sub(0,3).equals("cur")){//This block should be curved

      }else if(blockstring.sub(0,3).equals("swi")){//This block should be a switch

      }

    }
    config.aldl.set(linenum,newline);
  }
}
