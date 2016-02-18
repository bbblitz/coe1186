
import java.util.*;

public class DummyLineOne extends DummyLine{
  //public ArrayList<DummyTrackInterface> line = new ArrayList<DummyTrackInterface>();
  public DummyLineOne(){
    lineid = 0;
    name = "DummyLineOne";
    DummyTrackCurved p1 = new DummyTrackCurved(0,0,0,270,100);

    DummySwitch s1 = new DummySwitch(100,100,180,50);
    DummyTrackCurved s1u = new DummyTrackCurved(100,0,-180,90,100);
    DummyTrackStraight s1s = new DummyTrackStraight(150,100,180,100);
    DummyTrackStraight s1t = new DummyTrackStraight(100,100,0,50);

    //DummySwitch s2 = new DummySwitch(150,100,0,50);
    //DummyTrackCurved p2 = new DummyTrackCurved(150,100,90,-270,100);

    s1.head = s1s;
    s1.headto = DummySection.SEC_TAIL;
    s1.divergent = s1u;
    s1.divergentto =  DummySection.SEC_HEAD;
    s1.tail = s1t;
    s1.tailto =  DummySection.SEC_HEAD;
    s1u.tail = p1;
    s1u.tailto = DummySection.SEC_TAIL;
    s1u.head = s1;
    s1u.headto = DummySection.SEC_DIVERGENT;
    s1s.head = p1;
    s1s.headto = DummySection.SEC_HEAD;
    s1s.tail = s1;
    s1s.tailto = DummySection.SEC_HEAD;

    p1.head = s1s;
    p1.headto = DummySection.SEC_HEAD;
    p1.tail = s1u;
    p1.tailto = DummySection.SEC_TAIL;

    /*
    s2.head = p2;
    s2.headto =  DummySection.SEC_TAIL;
    s2.divergent = p2;
    s2.divergentto =  DummySection.SEC_TAIL;
    s2.tail = s1;
    s2.tailto =  DummySection.SEC_TAIL;

    p2.connected[0] = s2;
    p2.connectedto[0] =  DummySection.SEC_TAIL;
    p2.connected[1] = s2;
    p2.connectedto[0] =  DummySection.SEC_TAIL;
    */
    super.add(s1);
    //super.add(s2);
    super.add(p1);
    //super.add(p2);
  }
}
