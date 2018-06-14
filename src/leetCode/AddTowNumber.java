package leetCode;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AddTowNumber {
	public class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}
	}
	
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	ListNode rstHead = null;
    	ListNode previousNode = null;
    	int previousCarryBit = 0;
    	
    	 while(l1 != null || l2 != null) {
        	int totalValue = 0;
        	if(l1 != null && l2 != null) {
        		totalValue = l1.val + l2.val;
        		l1 = l1.next;
        		l2 = l2.next;
        	}
        	else if(l1 != null) {
        		totalValue = l1.val;
        		l1 = l1.next;
        	}
        	else if(l2 != null) {
        		totalValue = l2.val;
        		l2 = l2.next;
        	}
        	
        	int curBit = (totalValue + previousCarryBit)%10;
        	if(rstHead == null) {
        		rstHead = new ListNode(curBit);
        		previousNode = rstHead;
        	}
        	else {
        		ListNode curRstNode = new ListNode(curBit);
        		previousNode.next = curRstNode;
        		previousNode = curRstNode;
        	}
        	
        	previousCarryBit = (totalValue + previousCarryBit)/10;
        }
    	 
    	 if(previousCarryBit != 0) {
    		 ListNode lastBit = new ListNode(previousCarryBit);
    		 previousNode.next = lastBit;
    	 }
    	
    	return rstHead;
    }
	
	
    @Test
    void testAddTwoNum1() {
    	ListNode l1OnePlace = new ListNode(2);
    	ListNode l1TenPlace = new ListNode(4);
    	l1OnePlace.next = l1TenPlace;
    	ListNode l1HundredPlace = new ListNode(3);
    	l1TenPlace.next = l1HundredPlace;    	
  	
    	ListNode l2OnePlace = new ListNode(5);
    	ListNode l2TenPlace = new ListNode(6);
    	l2OnePlace.next = l2TenPlace;
    	ListNode l2HundredPlace = new ListNode(4);
    	l2TenPlace.next = l2HundredPlace;    	

		ListNode result = addTwoNumbers(l1OnePlace, l2OnePlace);
		assertAll(() -> assertEquals(7, result.val), () -> assertEquals(0, result.next.val),
				() -> assertEquals(8, result.next.next.val));
    }
	
    @Test
    void testAddTwoNu2() {
    	ListNode l1OnePlace = new ListNode(8);
    	ListNode l1TenPlace = new ListNode(7);
    	l1OnePlace.next = l1TenPlace;
    	ListNode l1HundredPlace = new ListNode(6);
    	l1TenPlace.next = l1HundredPlace;    	
  	
    	ListNode l2OnePlace = new ListNode(9);
    	ListNode l2TenPlace = new ListNode(8);
    	l2OnePlace.next = l2TenPlace;
    	ListNode l2HundredPlace = new ListNode(7);
    	l2TenPlace.next = l2HundredPlace;    	

		ListNode result = addTwoNumbers(l1OnePlace, l2OnePlace);
		assertAll(() -> assertEquals(7, result.val), () -> assertEquals(6, result.next.val),
				() -> assertEquals(4, result.next.next.val),
				() -> assertEquals(1, result.next.next.next.val));
    }
	
    @Test
    void testAddTwoNum3() {
    	ListNode l1OnePlace = new ListNode(2);
    	ListNode l1TenPlace = new ListNode(4);
    	l1OnePlace.next = l1TenPlace;
    	ListNode l1HundredPlace = new ListNode(3);
    	l1TenPlace.next = l1HundredPlace;    	
  	
    	ListNode l2OnePlace = new ListNode(2);
    	ListNode l2TenPlace = new ListNode(3);
    	l2OnePlace.next = l2TenPlace;    	

		ListNode result = addTwoNumbers(l1OnePlace, l2OnePlace);
		assertAll(() -> assertEquals(4, result.val), () -> assertEquals(7, result.next.val),
				() -> assertEquals(3, result.next.next.val));
		
		ListNode result1 = addTwoNumbers(l2OnePlace, l1OnePlace);
		assertAll(() -> assertEquals(4, result1.val), () -> assertEquals(7, result1.next.val),
				() -> assertEquals(3, result1.next.next.val));
    }
	
	
	
	
	
	
}
