package trackmodelprototype;

public class trackmodelp {
	
	private String Line;
	private String Section;
	private String BlockNo;
	private String BlockLength;
	private String BlockGrade;
	private String SpeedLimit;
	
	public trackmodelp(){}
	    public trackmodelp(String Line, String Section, String BlockNo, String BlockLength, String BlockGrade, String SpeedLimit) {
	        this.Line = Line;
	        this.Section = Section;
	        this.BlockNo = BlockNo;
	        this.BlockLength = BlockLength;
	        this.BlockGrade = BlockGrade;
	        this.SpeedLimit = SpeedLimit;
	    }
	    public String toString() {
	        return Line+ ": Section "+Section+ " BlockNo "+BlockNo+" BlockLength "+BlockLength+" BlockGrade "+BlockGrade+" SpeedLimit "+SpeedLimit;
	    }
	}