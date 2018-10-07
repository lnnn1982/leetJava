package leetCode;

import java.util.*;

public class HashMapQuestions {

	
	class LRUCache {
	    int capacity = 0;
	    LinkedHashMap<Integer, Integer> cache = null;

	    public LRUCache(int capacity) {
	        this.capacity = capacity;
	        cache = new LinkedHashMap<Integer, Integer>(capacity, (float)0.75, true);
	    }
	    
	    public int get(int key) {
	        Integer value = cache.get(key);
	        if(value == null) {
	            return -1;
	        }
	        else {
	            return value;
	        }
	    }
	    
	    public void put(int key, int value) {
	        if(cache.containsKey(key) || cache.size() < capacity) {
	            cache.put(key, value);
	            return;
	        }
	        
	        cache.remove(cache.entrySet().iterator().next().getKey());
	        cache.put(key, value);

	    }
	}

	
	
	//https://leetcode.com/problems/copy-list-with-random-pointer/description/
	/**
	 * Definition for singly-linked list with a random pointer.
	 */
	public class RandomListNode {
		int label;
		RandomListNode next, random;

		RandomListNode(int x) {
			this.label = x;
		}
	};
	 

	public RandomListNode copyRandomList(RandomListNode head) {
		if (head == null)
			return null;

		HashMap<RandomListNode, RandomListNode> oldNewMap = new HashMap<>();
		RandomListNode curONode = head;
		RandomListNode curNNode = new RandomListNode(curONode.label);
		RandomListNode curHeadNode = curNNode;
		oldNewMap.put(curONode, curNNode);

		while (curONode != null) {
			if (curONode.next != null) {
				RandomListNode newNextNode = oldNewMap.get(curONode.next);
				if (newNextNode == null) {
					newNextNode = new RandomListNode(curONode.next.label);
					oldNewMap.put(curONode.next, newNextNode);
				}
				curNNode.next = newNextNode;
			}

			if (curONode.random != null) {
				RandomListNode newRandomNode = oldNewMap.get(curONode.random);
				if (newRandomNode == null) {
					newRandomNode = new RandomListNode(curONode.random.label);
					oldNewMap.put(curONode.random, newRandomNode);
				}
				curNNode.random = newRandomNode;
			}

			curONode = curONode.next;
			curNNode = curNNode.next;
		}

		return curHeadNode;
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		
		
		
	}
}
