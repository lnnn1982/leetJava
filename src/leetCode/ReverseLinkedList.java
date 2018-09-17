package leetCode;

public class ReverseLinkedList {

	
	public static class ListNode {
	       int val;
	       ListNode next;
	       ListNode(int x) { val = x; }
	}
	
	
	
	
	
	public static ListNode reverseBetween(ListNode head, int m, int n) {
        if(m > n) {
            return null;
        }

        ListNode curNode = head;
        ListNode backupNextNode = null;
        ListNode newHead = null;
        ListNode beforeNewHead = null;
        ListNode newTail = null;
        
        int count = 0;
        while(curNode != null) {
            count++;
            
            if(count < m) {         
                if(count == m-1) {
                    beforeNewHead = curNode;
                }
                curNode = curNode.next;
            }
            else {
                if(count == m) {
                    newTail = curNode;
                }
                backupNextNode = curNode.next;
                curNode.next = newHead;
                newHead = curNode;
                curNode = backupNextNode;
            } 
            
            if(count == n) {
                if(beforeNewHead != null) {
                    beforeNewHead.next = newHead;
                }
                else {
                    head = newHead;
                }
                
                if(newTail != null) {
                    newTail.next = curNode;
                }
                
                
                return head;
            }
        }
        
        return null;
    }	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
