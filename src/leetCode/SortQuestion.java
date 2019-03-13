package leetCode;

import java.util.*;

///////////////////////////////////////////////////////////////////////////////////////
//https://leetcode.com/problems/merge-k-sorted-lists/submissions/
class MergeKSortedListsSolution {
    ListNode getMiniumValue(PriorityQueue<ListNode> queue) {
        if(queue.isEmpty()) {
            return null;
        }
        
        ListNode rNode = queue.poll();
        if(rNode.next != null) {
            queue.offer(rNode.next);
        }
        
        return rNode;
    }
    
    ListNode getMiniumValue(ListNode[] pointerList) {
        int minIdx = -1;
        for(int i = 0; i < pointerList.length; i++) {
            if(pointerList[i] == null) {
                continue;
            }
            if(minIdx == -1) {
                minIdx = i;
            }
            else {
                if(pointerList[i].val < pointerList[minIdx].val) {
                    minIdx = i;
                }
            }
        }
        
        if(minIdx == -1) {
            return null;
        }
        else {
            ListNode rNode = pointerList[minIdx];
            pointerList[minIdx] = rNode.next;
            return rNode;
        }
    }
    
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode newList = null;
        ListNode curNode = newList;
        
        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            public int compare(ListNode a, ListNode b) {
                return a.val - b.val;
            }
        });
            
        for(ListNode oneNode : lists) {
            if(oneNode != null) {
                queue.offer(oneNode);
            }
        }  
        
        while(true) {
            //ListNode minNode = getMiniumValue(lists);
        	ListNode minNode = getMiniumValue(queue);
            if(minNode == null) break;
            if(newList == null) {
                newList = minNode;
                curNode = newList;
            }
            else {
                curNode.next = minNode;
                curNode = minNode;
            }
        }
        
        return newList;
    }
    
    ListNode mergeTwoList(ListNode l1, ListNode l2) {
        //no need for this. Already process this situation
        //if(l1 == null) return l2;
        //if(l2 == null) return l1;
        
        ListNode newList = null;
        ListNode curNode = newList;
        while(l1 != null && l2 != null) {
            if(l1.val < l2.val) {
                if(newList == null) {
                    newList = l1;
                    curNode = newList;
                }
                else {
                    curNode.next = l1;
                    curNode = curNode.next;
                }
                l1 = l1.next;
            }
            else {
                if(newList == null) {
                    newList = l2;
                    curNode = newList;
                }
                else {
                    curNode.next = l2;
                    curNode = curNode.next;
                }
                l2 = l2.next;                
            }
        }
        
        if(l1 != null) {
            if(newList == null) {
                newList = l1;
            }
            else {
                curNode.next = l1;
            }
        }
        
        if(l2 != null) {
            if(newList == null) {
                newList = l2;
            }
            else {
                curNode.next = l2;
            }
        }
        
        return newList;
    }
    
    
    ListNode mergeKLists(ListNode[] lists, int beg, int end) {
        if(beg == end) {
            return lists[beg];
        }
        
        int mid = (beg+end)/2;
        ListNode left = mergeKLists(lists, beg, mid);
        ListNode right = mergeKLists(lists, mid+1, end);
        return mergeTwoList(left, right);
    }
    
    
    public ListNode mergeKListsByMerge(ListNode[] lists) {
        if(lists.length == 0) return null;
        return mergeKLists(lists, 0, lists.length-1);
    }
}

///////////////////////////////////////////////////////////////////////////////////////
//https://leetcode.com/problems/sqrtx/submissions/
class SqrtSolution {
    public double sqrtHelper(double x, double lowBound, double highBound) {
        double rst = (lowBound+highBound)/2;
        double product = rst*rst;
        if(Math.abs(x-product) < 0.00001) return rst;
        if(x-product > 0) return sqrtHelper(x, rst, highBound);
        else return sqrtHelper(x, lowBound, rst);
    }
    
    public int mySqrt(int x) {
        if(x == 1) return 1;
        double sqrt = sqrtHelper((double)x, 0, (double)x);
        //System.out.println(sqrt);
        long round = (int)Math.round(sqrt);
        long newProdct = round*round;
        //System.out.println(round+","+newProdct);
        if(newProdct == (long)x) return (int)round;
        else if(newProdct < (long)x) return (int)round;
        else return (int)(round-1);
    }
}

///////////////////////////////////////////////////////////////////////////////////////
class SingleNonDuplicateSol {
	
	int binarySearch(int[] nums, int beg, int end) {
		int mid = (beg+end)/2;
		if(mid == 0) {
			if(nums[mid] != nums[mid+1]) return nums[mid];
		}
		else if(mid == nums.length-1) {
			if(nums[mid] != nums[mid-1]) return nums[mid];
		}
		else {
			if(nums[mid] != nums[mid+1] && nums[mid] != nums[mid-1]) return nums[mid];
			
		}
		
		if(mid > beg) {
			int rst = binarySearch(nums, beg, mid-1);
			if(rst != Integer.MIN_VALUE) return rst;
		}
		
		if(mid < end) {
			int rst = binarySearch(nums, mid+1, end);
			if(rst != Integer.MIN_VALUE) return rst;
		}
		
		return Integer.MIN_VALUE;
	}

	public int singleNonDuplicate(int[] nums) {
		if(nums.length == 1) {
			return nums[0];
		}
		
		return binarySearch(nums, 0, nums.length-1);
	}
	
	static public void test() {
		SingleNonDuplicateSol sol = new SingleNonDuplicateSol();
		System.out.println(sol.singleNonDuplicate(new int[] {1,1,2,3,3,4,4,8,8}));
		System.out.println(sol.singleNonDuplicate(new int[] {3,3,7,7,10,11,11}));
	}
}

///////////////////////////////////////////////////////////////////////////////////////

public class SortQuestion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Double a = 0.3;
//		a.compareTo(123.0);
		SingleNonDuplicateSol.test();

	}

}
