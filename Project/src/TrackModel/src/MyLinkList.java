//====MyLinkList.java

public class MyLinkList
{
   public ListNode firstLink;
   MyLinkList(){firstLink = null;}
   public boolean isEmpty(){ return(firstLink == null);}
   public void insertFristLink(String newline, char newsection, int newblock_number, 
           double newblock_length, double newblock_grade, 
           int newspeed_limit, String newinfrastruct,
           double newelevation, double newcummulative_el,
           String newswitch_block, String newdirection){
	   ListNode newLink = new ListNode(newline, newsection, newblock_number, 
	           newblock_length, newblock_grade, 
	           newspeed_limit, newinfrastruct,
	           newelevation, newcummulative_el,
	           newswitch_block, newdirection);
	   newLink.next = firstLink;
	   firstLink = newLink;  
   }
   
   public ListNode removeFirst(){
	   ListNode linkReference = firstLink;
	   if(!isEmpty()){
		   firstLink = firstLink.next;
	   } else {
		   System.out.println("Emply LinkedList");
	   }
	   return linkReference;
   }
   
   //cycle through linknodes
   public void display(){
	   ListNode theLink = firstLink;
	   while(theLink != null){
		   theLink.display();
		   System.out.println("next Link:" + theLink.next);
		   theLink = theLink.next;
		   System.out.println();
	   }
   }
   //find a linknode
   public ListNode find(int block_number){
	   ListNode theLink = firstLink;
	   if(!isEmpty()){
		   while(theLink.block_number != block_number){
			   if(theLink.next == null){
				   return null;	   
			   }else {
				   theLink = theLink.next;
			   }
		   }
	   }else {
		   System.out.println("Empty LinkedList");
	   }
	   return theLink;
   }
   
   //remove a specific linknode
   public ListNode removeLink(int block_number){
	   ListNode currentLink = firstLink;
	   ListNode previousLink = firstLink;
	   while(currentLink.block_number != block_number){
		   if(currentLink.next == null){
			   return null;
		   }else {
			   previousLink = currentLink;
			   currentLink = currentLink.next;
		   }
	   }
	   if(currentLink == firstLink){
		   firstLink = firstLink.next;
	   }else {
		   previousLink.next = currentLink.next;
	   }
	   return currentLink;
   }
}