package leetCode;

public class MergeTwoSortedLists {
	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
	    ListNode newHead = null;
		ListNode prevNewLNode = null;	
	    while(l1 != null && l2 != null) {    	
	    	if(l1.val != l2.val) {
	    		int newValue = 0;
		    	if(l1.val > l2.val) {
		    		newValue = l2.val;
		    		l2 = l2.next;
		    	}
		    	else {
		    		newValue = l1.val;
		    		l1 = l1.next;
		    	}

                ListNode curNode =  new ListNode(newValue);
	    		
	    		if(newHead == null) {
	    			newHead = curNode;
	    		}
	    		else {
	    			prevNewLNode.next = curNode;
	    		}
		    	prevNewLNode = curNode;
	    	}
	    	else {
	    		ListNode curNode1 =  new ListNode(l1.val);
	    		ListNode curNode2 =  new ListNode(l2.val);
	    		if(newHead == null) {
	    			newHead = curNode1;
	    		}
	    		else {
	    			prevNewLNode.next = curNode1;
	    		}
	    		
	    		curNode1.next = curNode2;
		    	prevNewLNode = curNode2;
	    		
		    	l1 = l1.next;
	    		l2 = l2.next;
	    	}
	    }
	    
	    while(l1 != null) {
            ListNode curNode =  new ListNode(l1.val);
    		
    		if(newHead == null) {
    			newHead = curNode;
    		}
    		else {
    			prevNewLNode.next = curNode;
    		}
	    	prevNewLNode = curNode;
	    	l1 = l1.next;
	    	
	    }
	    
	    while(l2!=null) {
            ListNode curNode =  new ListNode(l2.val);
    		
    		if(newHead == null) {
    			newHead = curNode;
    		}
    		else {
    			prevNewLNode.next = curNode;
    		}
	    	prevNewLNode = curNode;
	    	l2 = l2.next;
	    }
	    
	    return newHead;
	}
	
	public static void printNode(ListNode l) {
		while(l != null) {
			System.out.println("--" + l.val + "--");
			l = l.next;
		}
	}
	
	static void test1WithSameSize() {
		ListNode l1 = new ListNode(0);
		l1.next = new ListNode(1);
		
		ListNode l2 = new ListNode(2);
		l2.next = new ListNode(3);
		
		ListNode head = mergeTwoLists(l1, l2);
		printNode(head);
	}
	
	static void test1WithDiffSize() {
		ListNode l1 = new ListNode(0);
		l1.next = new ListNode(1);
		
		ListNode l2 = new ListNode(2);
		l2.next = new ListNode(3);
		l2.next.next = new ListNode(4);
		
		ListNode head = mergeTwoLists(l1, l2);
		printNode(head);
		
		System.out.println(".........................");
		head = mergeTwoLists(l2, l1);
		printNode(head);
	}
	
	static void testEmptyList() {
		ListNode l1 = null;
		
		ListNode l2 = new ListNode(2);
		l2.next = new ListNode(3);
		l2.next.next = new ListNode(4);
		
		ListNode head = mergeTwoLists(l1, l2);
		printNode(head);
		
		System.out.println(".........................");
		head = mergeTwoLists(l2, l1);
		printNode(head);
	}
	
	static void testWithDuplicateElements() {
		ListNode l1 = new ListNode(0);
		l1.next = new ListNode(1);
		
		ListNode l2 = new ListNode(2);
		l2.next = new ListNode(2);
		l2.next.next = new ListNode(3);
		
		ListNode head = mergeTwoLists(l1, l2);
		printNode(head);
		
		System.out.println(".........................");
		head = mergeTwoLists(l2, l1);
		printNode(head);
	}
	
	static void testWithBothDuplicateElements() {
		ListNode l1 = new ListNode(0);
		l1.next = new ListNode(1);
		
		ListNode l2 = new ListNode(1);
		l2.next = new ListNode(2);
		l2.next.next = new ListNode(3);
		
		ListNode head = mergeTwoLists(l1, l2);
		printNode(head);
		
		System.out.println(".........................");
		head = mergeTwoLists(l2, l1);
		printNode(head);
	}
	
    public static void main(String[] args) {
    	//test1WithSameSize();
    	//test1WithDiffSize();
    	//testEmptyList();
    	//testWithDuplicateElements();
    	testWithBothDuplicateElements();
	}
	
	
	
	
	
	
	
	
}
