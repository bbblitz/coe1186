
import java.util.*;

public class LineOne extends Line{
  //public ArrayList<BlockInterface> line = new ArrayList<BlockInterface>();
  public LineOne(){
    lineid = 0;
    name = "LineOne";
    BlockCurved p1 = new BlockCurved(0,0,0,270,100);

    BlockSwitch s1 = new BlockSwitch(100,100,180,50);
    BlockCurved s1u = new BlockCurved(100,0,-180,90,100);
    BlockStraight s1s = new BlockStraight(150,100,180,100);
    BlockStraight s1t = new BlockStraight(150,100,0,50);

    BlockSwitch s2 = new BlockSwitch(150,100,0,50);
    BlockCurved s2d = new BlockCurved(200,100,90,-90,100);
    BlockStraight s2s = new BlockStraight(250,100,0,100);
    BlockStraight s2t = new BlockStraight(200,100,0,50);

    BlockCurved p2 = new BlockCurved(300,100,90,-270,100);

    //BlockSwitch s2 = new BlockSwitch(150,100,0,50);
    //BlockCurved p2 = new BlockCurved(150,100,90,-270,100);

    s1.head = s1s;
    s1.headto = BlockPart.SEC_TAIL;
    s1.divergent = s1u;
    s1.divergentto =  BlockPart.SEC_HEAD;
    s1.tail = s1t;
    s1.tailto =  BlockPart.SEC_HEAD;
    s1u.tail = p1;
    s1u.tailto = BlockPart.SEC_TAIL;
    s1u.head = s1;
    s1u.headto = BlockPart.SEC_DIVERGENT;
    s1s.head = p1;
    s1s.headto = BlockPart.SEC_HEAD;
    s1s.tail = s1;
    s1s.tailto = BlockPart.SEC_HEAD;
    s1t.head = s2t;
    s1t.headto = BlockPart.SEC_TAIL;
    s1t.tail = s1;
    s1t.tailto = BlockPart.SEC_TAIL;

    p1.head = s1s;
    p1.headto = BlockPart.SEC_HEAD;
    p1.tail = s1u;
    p1.tailto = BlockPart.SEC_TAIL;

    s2.head = s2s;
    s2.headto = BlockPart.SEC_TAIL;
    s2.divergent = s2d;
    s2.divergentto = BlockPart.SEC_HEAD;
    s2.tail = s2t;
    s2.tailto = BlockPart.SEC_HEAD;
    s2d.head = s2;
    s2d.headto = BlockPart.SEC_DIVERGENT;
    s2d.tail = p2;
    s2d.tailto = BlockPart.SEC_TAIL;
    s2s.tail = s2;
    s2s.tailto = BlockPart.SEC_HEAD;
    s2s.head = p2;
    s2s.headto = BlockPart.SEC_HEAD;
    s2t.head = s2;
    s2t.headto = BlockPart.SEC_TAIL;
    s2t.tail = s1t;

    p2.head = s2s;
    p2.headto = BlockPart.SEC_HEAD;
    p2.tail = s2d;
    p2.tailto = BlockPart.SEC_TAIL;



    /*
    s2.head = p2;
    s2.headto =  BlockPart.SEC_TAIL;
    s2.divergent = p2;
    s2.divergentto =  BlockPart.SEC_TAIL;
    s2.tail = s1;
    s2.tailto =  BlockPart.SEC_TAIL;

    p2.connected[0] = s2;
    p2.connectedto[0] =  BlockPart.SEC_TAIL;
    p2.connected[1] = s2;
    p2.connectedto[0] =  BlockPart.SEC_TAIL;
    */
    super.add(s1);
    //super.add(s2);
    super.add(p1);
    //super.add(p2);
    super.add(s2);

    super.add(p2);
  }
}
