package leetCode;

import java.util.*;



//https://leetcode.com/problems/lfu-cache/submissions/
class LFUCache {
    static class Node {
        int key;
        int value;
        Node next = null;
        Node prev = null;
        long freq;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            freq = 1;
        }
    }
    
    static class NodePair {
        Node head;
        Node tail;
    }
    
    int capacity;
    long minFreq = 1;

    HashMap<Integer, Node> map = new HashMap<>();
    HashMap<Long, NodePair> freqMap = new HashMap<>();
    
    
    public LFUCache(int capacity) {
       this.capacity = capacity; 
    }
    
    void insertNewNodeToFreqMap(Node node) {
        minFreq = 1;
        insertNodeToFreqMap(node);
    }
    
    void insertNodeToFreqMap(Node node) {
        //System.out.println("insert node freq:"+node.freq+", key:"+node.key);
        if(!freqMap.containsKey(node.freq)) {
            NodePair nodePair = new NodePair();
            nodePair.head = node;
            nodePair.tail = node;
            freqMap.put(node.freq, nodePair);
        }
        else {
            NodePair nodePair = freqMap.get(node.freq);
            nodePair.tail.next = node;
            node.prev = nodePair.tail;
            nodePair.tail = node;
        }
        //printFreqNodeMap();
        
    }
    
    void updateNodeInFreqMap(Node node) {
        //System.out.println("update node freq:"+node.freq+", key:"+node.key+", val:"+node.value);
        
        NodePair oldNodePair = freqMap.get(node.freq);
        deleteNodeFromNodePair(node, oldNodePair);
        if(!freqMap.containsKey(node.freq)) {
            if(minFreq == node.freq) {
                minFreq = node.freq+1;
            }
        }
        node.freq++;
        insertNodeToFreqMap(node);
    }
    
    void deleteNodeFromNodePair(Node node, NodePair nodePair) {
        //System.out.println("deleteNodeFromNodePair nodeFreq:"+node.freq+", key:"+ node.key+", pair head:"+nodePair.head.key+", tail:"+nodePair.tail.key);
        //System.out.println("1111111111111prev:"+node.prev+", next:"+node.next);

        if(node.prev == null) {
            nodePair.head = node.next;
        }
        else {
            node.prev.next = node.next;
        }
        
        if(node.next == null) {
            nodePair.tail = node.prev;
        }
        else {
            node.next.prev = node.prev;
        }
        
        if(nodePair.head == null) {
            freqMap.remove(node.freq);
        }
        
        node.prev = null;
        node.next = null;
        
        //printFreqNodeMap();
    }
    
    void printFreqNodeMap() {
        for(Map.Entry<Long, NodePair> oneEntry : freqMap.entrySet()) {
            System.out.println("freq:"+oneEntry.getKey()+", nodePair head:"+oneEntry.getValue().head.key+ ", tail:"+oneEntry.getValue().tail.key);
        }
    }
    
    void deleteLeastFreqNode() {
        NodePair nodePair = freqMap.get(minFreq);
        Node node = nodePair.head;
        //System.out.println("deleteLeastFreqNode. key:"+node.key+", minFreq:"+minFreq+", value:"+node.value+", freq:"+node.freq);
        deleteNodeFromNodePair(node, nodePair);
        map.remove(node.key);
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if(node != null) {
            updateNodeInFreqMap(node);
            return map.get(key).value;
        }
        else {
            return -1;
        }
    }
    
    public void put(int key, int value) {
        if(capacity == 0) return;
        
        Node node = map.get(key);
        if(node == null) {
            if(map.size() == capacity) {
                //System.out.println("delete least node. key:"+key);
                deleteLeastFreqNode();
            }
            node = new Node(key, value);
            insertNewNodeToFreqMap(node);
            map.put(key, node);
        }
        else {
            node.value = value;
            updateNodeInFreqMap(node);
        }
    }
}

//https://leetcode.com/problems/lru-cache/submissions/
class LRUCache {
    class Node {
        int key;
        int value;
        Node next = null;
        Node prev = null;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    Node head = null;
    Node tail = null;
    HashMap<Integer, Node> map = new HashMap<>();
    int capacity = 0;
    
    void removeNodeToTail(Node node) {
        if(node == tail) return;
        if(node.prev != null) {
            node.prev.next = node.next;
        }
        else {
            head = node.next;
        }
        
        if(node.next != null) {
            node.next.prev = node.prev;
        }
        
        tail.next = node;
        node.prev = tail;
        node.next = null;
        tail = node;
    }
    
    void removeHead() {
        if(head == null) return;
        if(head == tail) {
            head = null;
            tail = null;
            return;
        }
        
        Node node = head;
        head = node.next;
        head.prev = null;
        node.next = null;
        node.prev = null;
    }
    
    void insertNodeToTail(Node node) {
        if(head == null || tail == null) {
            head = node;
            tail = node;
            return;
        }
        
        tail.next = node;
        node.prev = tail;
        tail = node;
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if(node == null) {
            return -1;
        }
        else {
            removeNodeToTail(node);
            return node.value;
        }
    }
    
    public void put(int key, int value) {
        if(capacity == 0) return;
        
        Node node = map.get(key);
        if(node == null) {
            if(map.size() == capacity) {
                map.remove(head.key);
                removeHead();  
            }
            
            node = new Node(key, value);
            insertNodeToTail(node);
            map.put(key, node);
        }
        else {
            removeNodeToTail(node); 
            node.value = value;
            map.put(key, node); 
        }
    }
}










public class DesignQuestions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
