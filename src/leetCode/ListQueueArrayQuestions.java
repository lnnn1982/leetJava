package leetCode;
import java.util.*;

//https://leetcode.com/problems/top-k-frequent-words/
class TopKFrequentWordSolution {
    public List<String> topKFrequent(String[] words, int k) {
        HashMap<String, Integer> wordFreqMap = new HashMap<>();
        for(String word : words) {
            Integer freq = wordFreqMap.get(word);
            if(freq == null) {
                wordFreqMap.put(word, 0);
            }
            else {
                wordFreqMap.put(word, freq+1);
            }
        }
        
        PriorityQueue<Map.Entry<String, Integer>> priQ = new
            PriorityQueue<>(k, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> a, 
                                   Map.Entry<String, Integer> b) 
                {
                    /*int feqDiff = a.getValue()-b.getValue();
                    if(feqDiff != 0) {
                        return feqDiff;
                    }
                    
                    return b.getKey().compareTo(a.getKey());*/
                	
                	int feqDiff = a.getValue()-b.getValue();
                    if(feqDiff > 0) {
                        return 1;
                    }
                    else if(feqDiff < 0) {
                    	return -1;
                    }
                    
                    if(b.getKey().compareTo(a.getKey()) > 0) {
                    	return 1;
                    }
                    else if(b.getKey().compareTo(a.getKey()) < 0) {
                    	return -1;
                    }
                    
                    return 0;
                }
            });
        
        for(Map.Entry<String, Integer> oneFreq : wordFreqMap.entrySet()) {
            priQ.add(oneFreq);
            if(priQ.size() > k) {
                priQ.poll();
            }
        }
        
        LinkedList<String> rst = new LinkedList<>();
        while(!priQ.isEmpty()) {
            Map.Entry<String, Integer> oneRst = priQ.poll();
            rst.addFirst(oneRst.getKey());
        }
        
        return rst;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
		next = null;
	}
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////

//https://leetcode.com/problems/top-k-frequent-elements/submissions/
class TopKFrequentSolution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> numFreqMap = new HashMap<>();
        for(int oneNum : nums) {
            Integer freq = numFreqMap.get(oneNum);
            if(freq == null) {
                numFreqMap.put(oneNum, 1);
            }
            else {
                numFreqMap.put(oneNum, freq+1);
            }
        }
        
        PriorityQueue<Map.Entry<Integer,Integer>> queue = new PriorityQueue<>(
            k, new Comparator<Map.Entry<Integer,Integer> >() {
                public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                    return a.getValue()-b.getValue();
                }
            });
        for(Map.Entry<Integer, Integer> elem : numFreqMap.entrySet()) {
            queue.add(elem);
            if(queue.size() > k) {
                queue.poll();
            }
        }
        
        List<Integer> rst = new ArrayList<>();
        for(Map.Entry<Integer,Integer> elem : queue) {
            rst.add(elem.getKey());
        }
        
        return rst;
            
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////

//https://leetcode.com/problems/linked-list-cycle-ii/submissions/
class DetectCycleSolution {
    public ListNode detectCycle(ListNode head) {
        if(head == null) return null;
        ListNode fast = head;
        ListNode slow = head;
        
        while(fast != null && fast.next != null && slow != null) {
            slow = slow.next;
            fast = fast.next;
            fast = fast.next;
            if(fast == slow) break;
        }
        
        if(fast == null || fast.next == null) {
            return null;
        }
        
        slow = head;
        while(slow != null && fast != null) {
            if(slow == fast) return slow;
            slow = slow.next;
            fast = fast.next;
        }
        
        return null;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////

//https://leetcode.com/problems/permutations/
class AllPermutationSolution {
  
  int [] perNums;
  List<List<Integer>> rstList = new ArrayList<>();
  
  void genPermutation(LinkedList<Integer> permutaionList, boolean[] visited) {       
      if(permutaionList.size() == perNums.length) {
          LinkedList<Integer> oneRst = (LinkedList<Integer>)permutaionList.clone();
          rstList.add(oneRst);
      }
      else {
          for(int i = 0; i < perNums.length; i++) {
              if(!visited[i]) {
                  permutaionList.add(perNums[i]);
                  visited[i] = true;
                  genPermutation(permutaionList, visited);
                  permutaionList.removeLast();
                  visited[i] = false;
              }
          }
      }
  }
  
  boolean findNumInList(List<Integer> permutaionList, int num) {
      for(int cNum : permutaionList) {
          if(cNum == num) {
              return true;
          }
      }
      
      return false;
  }
  
  public List<List<Integer>> permute(int[] nums) {
      perNums = nums;
      
      LinkedList<Integer> permutaionList = new LinkedList<>();
      boolean[] visited = new boolean[nums.length];
      genPermutation(permutaionList, visited);
      
      return rstList;
      
      
  }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////

//https://leetcode.com/problems/next-permutation/
class NextPermutationSolution {
  public void nextPermutation(int[] nums) {
      if(nums.length < 2) {
          return;
      }
      
      int i = nums.length-1;
      for(; i >= 1 ; i--) {
          if(nums[i-1] < nums[i]) {
              break;
          }
      }
      
      if(i == 0) {
          Arrays.sort(nums);
          return;
      }
      
      int startP = i-1;
      int changIndex = startP+1;       
      for(int j = changIndex+1; j < nums.length; j++) {
          if(nums[j] <= nums[startP]) {
              break;
          }
          
          if(nums[j] < nums[changIndex]) {
              changIndex = j;
          }
      }
      
      int tem = nums[startP];
      nums[startP] = nums[changIndex];
      nums[changIndex] = tem;
      
      Arrays.sort(nums, startP+1, nums.length);
      
      return;
  }
}

class NextPermutationSolution1 {
    public void swap(int[] nums, int i, int j) {
        int tem = nums[i];
        nums[i] = nums[j];
        nums[j] = tem;
    }
    
    public void reverse(int[] nums, int startIndex, int endIndex) {
        int i = startIndex;
        int j = endIndex-1;
        while(i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }
    
    
    public void nextPermutation(int[] nums) {
        if(nums.length < 2) {
            return;
        }
        
        int i = nums.length-1;
        for(; i >= 1 ; i--) {
            if(nums[i-1] < nums[i]) {
                break;
            }
        }
        
        if(i == 0) {
            reverse(nums, 0, nums.length);
            return;
        }
        
        int startP = i-1;
        int changIndex = startP+1;       
        //from the last to the front
        for(int j = nums.length-1; j > changIndex; j--) {
            if(nums[j] > nums[startP]) {
                changIndex = j;
                break;
            }
        }
        
        swap(nums, startP, changIndex);
        
        reverse(nums, startP+1, nums.length);
        
        return;
         
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////

//https://leetcode.com/problems/basic-calculator/submissions/
class BasicCal1Sol {
    public int calculate(String s) {
        LinkedList<String> postExpList = new LinkedList<String>();
        LinkedList<Character> opStack = new LinkedList<>();
        for(int i = 0; i < s.length(); i++) {
            StringBuilder oneNumB = new StringBuilder();
            while(i < s.length() && Character.isDigit(s.charAt(i))) {
                oneNumB.append(s.charAt(i));
                i++;
            }
            
            if(!oneNumB.toString().isEmpty()) {
                postExpList.add(oneNumB.toString());
            }
            
            if(i == s.length()) {
                break;
            }
            
            char ch = s.charAt(i);
            if(s.charAt(i) == ' ') {
                continue;
            }
            
            Character topChar = null;
            while((topChar = opStack.peek()) != null) {
                if(ch == '(') {
                    break;
                }
                
                if(ch == ')') {
                    if(topChar == '(') {
                        opStack.pop();
                        break;
                    }
                    else {
                        postExpList.add(String.valueOf(opStack.pop()));
                    }
                }
                else {
                    if(topChar == '(') {
                        break;
                    }
                    else {
                        postExpList.add(String.valueOf(opStack.pop()));
                    }
                    
                }

            }
            
            if(ch != ')') {
                opStack.push(ch);
            }
            
            //System.out.println("bbbbbbbbbbbbbbb i: "+i  + ", strB:" + strB.toString()+ " "+ opStack);
        }
        
        while(!opStack.isEmpty()) {
            postExpList.add(String.valueOf(opStack.pop()));
        }
        
        //System.out.println(postExpList);
        
        LinkedList<Integer> digitStack = new LinkedList<>();

        for(String ele : postExpList) {
            if(ele.equals("+")) {
                int sum = digitStack.pop()+digitStack.pop();
                digitStack.push(sum);
            }
            else if(ele.equals("-")) {
                int minus = 0-digitStack.pop()+digitStack.pop();
                digitStack.push(minus);
            }
            else {
                digitStack.push(Integer.parseInt(ele));
            }

        }
        
        return digitStack.pop();

    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
//https://leetcode.com/problems/merge-intervals/
class MergeIntervalSolution {
	
	/**
	 * Definition for an interval.*/
	 public class Interval {
	      int start;
	      int end;
	      Interval() { start = 0; end = 0; }
	      Interval(int s, int e) { start = s; end = e; }
	 }
	
    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> rst = new ArrayList<Interval>();
        if(intervals.size() == 0) {
            return rst;
        }
        else if(intervals.size() == 1) {
            rst.add(intervals.get(0));
            return rst;
        }
        
        Collections.sort(intervals, new Comparator<Interval>(){
            public int compare(Interval a, Interval b) {
                return a.start-b.start;
            }
        });
        
        Interval lastInterval = intervals.get(0);
        for(int i = 1; i < intervals.size(); i++) {
            Interval curInterval = intervals.get(i);
            if(curInterval.start <= lastInterval.end && curInterval.end > lastInterval.end) {
                lastInterval = new Interval(lastInterval.start, curInterval.end);
                continue;
            }
            
            if(curInterval.start > lastInterval.end) {
                rst.add(lastInterval);
                lastInterval = curInterval;
                continue;
            }
        }
        
        rst.add(lastInterval);
        
        return rst;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////

//https://leetcode.com/problems/find-median-from-data-stream/
class MedianFinder {
    PriorityQueue<Integer> firstQueue = new PriorityQueue<>(new Comparator<Integer>(){
        public int compare(Integer a, Integer b) {
            return b-a;
        }
    });
    PriorityQueue<Integer> secondQueue = new PriorityQueue<>();

    /** initialize your data structure here. */
    public MedianFinder() {
    }
    
    public void addNum(int num) {
        if(firstQueue.isEmpty()) {
            firstQueue.offer(num);
            
            return;
        }
        
        if(secondQueue.isEmpty()) {
            if(firstQueue.peek() <= num) {
                secondQueue.offer(num);
            }
            else {
                secondQueue.offer(firstQueue.poll());
                firstQueue.offer(num);
            }
            
            return;
        }
        
        if(num >= secondQueue.peek()) {
            if(secondQueue.size() > firstQueue.size()) {
                firstQueue.offer(secondQueue.poll());
            }
            secondQueue.offer(num);
        }
        else if(num <= firstQueue.peek()) {
            if(firstQueue.size() > secondQueue.size()) {
                secondQueue.offer(firstQueue.poll());
            }
            firstQueue.offer(num);
        }
        else {
            if(firstQueue.size() <= secondQueue.size()) {
                firstQueue.offer(num);
            }
            else {
                secondQueue.offer(num);
            }
        }
    }
    
    public double findMedian() {
        if(firstQueue.isEmpty() && secondQueue.isEmpty()) {
            return 0;
        }
        
        if(firstQueue.size() == secondQueue.size()) {
            return (double)(firstQueue.peek()+secondQueue.peek())/2;
        }
        else if(firstQueue.size() > secondQueue.size()) {
            return firstQueue.peek();
        }
        else {
            return secondQueue.peek();
        }
        
        
    }
    
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////

//https://leetcode.com/problems/sliding-window-maximum/
class SlidingwWindowMaximumSol {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums.length == 0 || k == 0) {
            return new int[]{};
        }
        
        LinkedList<Integer> queue = new LinkedList<>();
        int[] rst = new int[nums.length-k+1];
        for(int i = 0; i < nums.length; i++) {
            if(i >= k) {
                Integer index = queue.peek();
                if(index != null && (index == i-k)) {
                    queue.poll();
                }
            }
            
            while(true) {
                if(queue.isEmpty()) {
                    break;
                }
                
                if(nums[queue.peekLast()] <= nums[i]) {
                    queue.pollLast();
                }
                else {
                    break;
                }
            }
            

            
            queue.offer(i);
            if(i >= k-1) {
                rst[i-k+1] = nums[queue.peek()];
            }
        }
        
        return rst;
    }
    

}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//https://leetcode.com/problems/rotate-list/
class RotateListSolution {
    public ListNode rotateRight(ListNode head, int k) {
        if(head == null) return null;
        
        ListNode curNode = head;
        int len = 0;
        while(curNode != null) {
            len++;
            curNode = curNode.next;
        }
        
        k = k%len;
        if(k == 0) return head;
        
        int firNum = len-k;
        ListNode newLastNode = null;
        ListNode newHeadNode = null;
        ListNode lastNode = null;
        
        curNode = head;
        int newLen = 0;
        while(curNode != null) {
            newLen++;
            if(newLen == firNum) {
                newLastNode = curNode;
                newHeadNode = curNode.next;
            }
            else if(newLen == len) {
                lastNode = curNode;
            }
            
            curNode = curNode.next;
        }
        
        lastNode.next = head;
        newLastNode.next = null;
        return newHeadNode;
        
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class ListQueueArrayQuestions {
	
    static public void main(String[] args) {
        TopKFrequentWordSolution solution = new TopKFrequentWordSolution();
        List<String> rst = solution.topKFrequent(new String[] {
                "the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}, 4);
        System.out.println("rst:"+rst);
    }	
	
	
}


