public class TrackController
{
	public static void main(String args[])
	{
		Track A = new Track();
		Track B = new Track();
		Crossing C = new Crossing();
		Switch D = new Switch();
		
		D.connectNext(A);
		D.connectPrev(B);	
		D.connectDivergent(C);
		
		
		System.out.println(D.switchPosition);
		D.toggle();
		System.out.println(D.switchPosition);
		
	}

}
