//====ListNode.java
//line:         red
//section :     A
//block number: 3
//block length: 50m
//block grade:  1.5%
//speed limit:  40 km/hr
//infrastruct: STATION: SHADYSIDE
//elevation:    0.75
//cummlatvie el:1.50
//switch block: Switch 12
//arrow direct: head

public class ListNode   
{
 public String line;
 public char   section;
 public int    block_number;
 public double block_length;
 public double block_grade;
 public int    speed_limit;
 public String infrastruct;
 public double elevation;
 public double cummulative_el;
 public String switch_block;
 public String direction;
 
 public ListNode next;       //a reference pointing to next node

 public ListNode(String line, char section, int block_number, 
                 double block_length, double block_grade, 
                 int speed_limit, String infrastruct,
                 double elevation, double cummulative_el,
                 String switch_block, String direction) //constructor
 {
   this.line         	= line;
   this.section			= section;
   this.block_number	= block_number;
   this.block_length	= block_length;
   this.block_grade		= block_grade;
   this.speed_limit		= speed_limit;
   this.infrastruct		= infrastruct;
   this.elevation		= elevation;
   this.cummulative_el	= cummulative_el;
   this.switch_block	= switch_block;
   this.direction	    = direction;
 }
 
 public void display() {
	 System.out.println(line + ":" + section + ":" + block_number + ":" +
             block_length + ":" + block_grade + ":" + 
             speed_limit + ":" + infrastruct + ":" +
             elevation + ":" + cummulative_el + ":" +
             switch_block + ":" + direction + ":");
 }
 public String toString(){
	 return line;
 }
 
 //because all variables are public, you do not need any get or set
 //public ListNode getNext() {return next;}
 //public void setNext(ListNode newNext) {next = newNext;}

  public static void main(String[] args){
	  MyLinkList theLinkedList = new MyLinkList();
	  theLinkedList.insertFristLink("Red",     'A',	1,	50,	0.5, 40, "STATION: SHADYSIDE",  0.25,	0.25,	"Switch 6",	"Head");
	  theLinkedList.insertFristLink("GREEN",   'B',	2,	50,	0.5, 40, "STATION: SHADYSIDE",  0.25,	0.25,	"Switch 6",	"Head");
	  theLinkedList.insertFristLink("BLUE",    'C',	3,	50,	0.5, 40, "STATION: SHADYSIDE",  0.25,	0.25,	"Switch 6",	"Head");
	  theLinkedList.insertFristLink("ORANGE",  'D',	4,	50,	0.5, 40, "STATION: SHADYSIDE",  0.25,	0.25,	"Switch 6",	"Head");
	  theLinkedList.display();  //outputing on reverse order, ORANGE is the first link
	  theLinkedList.removeFirst(); //ORANGE is removed
	  System.out.println(theLinkedList.find(3).block_number + "  was found");
	  theLinkedList.removeLink(3);
	  theLinkedList.display();
  }
}