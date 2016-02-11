
import java.util.*;

public class DummyLineOne extends DummyLine{
  //public ArrayList<DummyTrackInterface> line = new ArrayList<DummyTrackInterface>();
  public DummyLineOne(){
    name = "DummyLineOne";
    DummyTrackCurved p1 = new DummyTrackCurved(0,0,0,270,100);
    DummySwitch s1 = new DummySwitch(100,100,180,50);
    DummySwitch s2 = new DummySwitch(150,100,0,50);
    DummyTrackCurved p2 = new DummyTrackCurved(150,100,90,-270,100);

    s1.head = p1;
    s1.headto = DummySection.SEC_TAIL;
    s1.divergent = p1;
    s1.divergentto =  DummySection.SEC_TAIL;
    s1.tail = s2;
    s1.tailto =  DummySection.SEC_TAIL;

    p1.connected[0] = s1;
    p1.connectedto[0] =  DummySection.SEC_TAIL;
    p1.connected[1] = s1;
    p1.connectedto[1] =  DummySection.SEC_TAIL;

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

    super.add(s1);
    super.add(s2);
    super.add(p1);
    super.add(p2);
  }
}
