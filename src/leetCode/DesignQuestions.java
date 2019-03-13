package leetCode;

import java.util.*;
import java.util.concurrent.*;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//https://www.cnblogs.com/grandyang/p/6944331.html
class FileSystem {
	interface FileNode {
		List<String> ls(String path);
		void mkdir(String path);
		void addContentToFile(String filePath, String content);
		String readContentFromFile(String filePath);
		String getName();
	}
	
	class Directory implements FileNode {
		private String name = "";
		Map<String, FileNode> nodeChildren = new HashMap<>();
		
		Directory(String name) {
			this.name = name;
		}
		
		public List<String> ls(String path) {
			String[] sPaths = splitPath(path);
            String childName = sPaths[0];
            String recChildName = sPaths[1];
            if(childName.isEmpty()) {
            	ArrayList<String> rst = new ArrayList<>();
    			for(String fileName : nodeChildren.keySet()) {
    				rst.add(fileName);
    			}
    			Collections.sort(rst);
    			return rst;
            }
			
            FileNode child = nodeChildren.get(childName);
            if(child != null) {
            	return child.ls(recChildName);
            }
            else {
            	return new ArrayList<String>();
            }
		}
		
		//remove the first /
		public void mkdir(String path) {		
			String[] sPaths = splitPath(path);
            String childName = sPaths[0];
            String recChildName = sPaths[1];
			
			FileNode child = nodeChildren.get(childName);
			if(child == null) {
				FileNode newC = new Directory(childName);
				nodeChildren.put(childName, newC);
				child = nodeChildren.get(childName);
			}
			if(!recChildName.isEmpty()) {
				child.mkdir(recChildName);
			}
		}
		
		private String[] splitPath(String filePath) {
			String[] rst = {filePath, ""};
            int sepIndex = filePath.indexOf("/");
            if(sepIndex != -1) {
            	rst[0] = filePath.substring(0, sepIndex);
            	rst[1] = filePath.substring(sepIndex+1);
            }
            
            //System.out.println("childName:"+rst[0]+", recChildName:"+rst[1]);
            return rst;
		}
		
		public void addContentToFile(String filePath, String content) {
			String[] sPaths = splitPath(filePath);
            String childName = sPaths[0];
            String recChildName = sPaths[1];
            
            FileNode child = nodeChildren.get(childName);
            if(recChildName.isEmpty()) {
            	if(child != null) {
            		child.addContentToFile(childName, content);
            	}
            	else {
    				FileNode newC = new SimpleFile(childName);
    				System.out.println("dir:"+name+", add one file:" + childName);
    				nodeChildren.put(childName, newC);
    				newC.addContentToFile(childName, content);
            	}
            }
            else {
    			if(child != null) {
    				child.addContentToFile(recChildName, content);
    			}
            }
		}
		
		public String readContentFromFile(String filePath) {
			String[] sPaths = splitPath(filePath);
            String childName = sPaths[0];
            String recChildName = sPaths[1];
            
            FileNode child = nodeChildren.get(childName);
            if(child != null) {
        		return child.readContentFromFile(recChildName);
        	}
            else return "";		
		}	
		
		public String getName() {
			return name;
		}
	}
	
	class SimpleFile implements FileNode {
		private String name;
		private String content = "";
		
		SimpleFile(String name) {
			this.name = name;
		}
		
		public List<String> ls(String path) {
			//throw new Exception("not support");
			List<String> rst = new ArrayList<>();
			rst.add(name);
			return rst;
		}
		
		public void mkdir(String path) {
			return;
		}
		
		public void addContentToFile(String filePath, String content) {
			this.content += content;
			System.out.println("addContentToFile name:" + name + ", content:" + content);
		}
		
		public String readContentFromFile(String filePath) {
			return content;
		}	
		
		public String getName() {
			return name;
		}
	}
	
	private Directory root;
	FileSystem() {
		root = new Directory("/");
	}
	
	List<String> ls(String path) {
        if(path.isEmpty()) {
        	return new ArrayList<>();
        }
        
        if(path.charAt(0) == '/') {
        	return root.ls(path.substring(1));
        }
        else {
        	return root.ls(path);
        }
	}
	
	void mkdir(String path) {
        if(path.isEmpty()) {
        	return;
        }
        
        if(path.charAt(0) == '/') {
        	root.mkdir(path.substring(1));
        }
        else {
        	root.mkdir(path);
        }
	}
	
	void addContentToFile(String filePath, String content) {
        if(filePath.isEmpty()) {
        	return;
        }
        
        if(filePath.charAt(0) == '/') {
        	root.addContentToFile(filePath.substring(1), content);
        }
        else {
        	root.addContentToFile(filePath,content);
        }
	}
	
	String readContentFromFile(String filePath) {
        if(filePath.isEmpty()) {
        	return "";
        }
        
        if(filePath.charAt(0) == '/') {
        	return root.readContentFromFile(filePath.substring(1));
        }
        else {
        	return root.readContentFromFile(filePath);
        }		
	}
	
    static void test() {
    	FileSystem file = new FileSystem();
    	System.out.println(file.ls("/"));
    	
//    	file.addContentToFile("/a", "hello");
//    	System.out.println("fileContent:"+ file.readContentFromFile("/a"));
//    	System.out.println(file.ls("/"));
    	
    	file.mkdir("/b");
    	System.out.println(file.ls("/"));
    	
    	file.mkdir("/c/c1/c2");
    	System.out.println(file.ls("/"));
    	System.out.println(file.ls("/c/c1"));
    	
    	
    	file.mkdir("/a/b/c");
    	file.addContentToFile("/a/b/c/d", "hello");
    	System.out.println(file.ls("/"));
    	System.out.println(file.readContentFromFile("/a/b/c/d"));
    	
    	file.mkdir("/a/b/c1");
    	file.addContentToFile("/a/b/c1/d", "hello");
    	System.out.println(file.ls("/a/b"));
    	System.out.println(file.readContentFromFile("/a/b/c1/d"));
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//https://wdxtub.com/interview/14520604445920.html
class ElevatorRequest {
	enum ActionType {
		IN_ELEVATOR_UP,
		IN_ELEVATOR_DOWN,
		OUT_ELEVATOR,
	}
	
	int level;
	ActionType aType;
	
	ElevatorRequest(ActionType aType, int level) {
		this.level = level;
		this.aType = aType;
	}
}

class Elevator {
	public enum Status {
		DOWN,
		STAND,
		UP,
		STOP
	}
	
	private volatile Status curStatus = Status.STOP;
	private volatile int curLevel = 0;
	private volatile int openLevel = -1;
	private int id = -1;
	
	//BlockingQueue<ElevatorRequest>  requests = new LinkedBlockingQueue<>();
	//ConcurrentLinkedQueue<ElevatorRequest>  requests = new ConcurrentLinkedQueue<>();
	private TreeSet<ElevatorRequest> requests = new TreeSet<>((a,b)->{
		if(a.level != b.level) {
			return a.level-b.level;
		}
		
		return a.aType.ordinal()-b.aType.ordinal();
	});
	
	public Elevator(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public Status getCurStatus() {
		return curStatus;
	}
	
	public int getCurLevel() {
		return curLevel;
	}
	
	public int getOpenLevel() {
		return openLevel;
	}
	
	public void start() {
		System.out.println("["+ Thread.currentThread().getId() + "] " + 
	        "ele id:" + id + " start");
		curStatus = Status.STAND;
		try {
			runLoop();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void runLoop() throws Exception{
		while(true) {
			waitForRequst();
			updateStatus();
            run();
		}
	}
	
	private synchronized void waitForRequst() throws Exception{
		while(requests.isEmpty()) {
			System.out.println("["+ Thread.currentThread().getId() + "] " + " waitForRequst");
            wait();
		}
		
		System.out.println("["+ Thread.currentThread().getId() + "] " + 
		        "waitForRequst get one request");
	}
	
	private void run()throws Exception {
		while(curStatus != Status.STAND) {
			moveToCurLevel();
			if(curStatus == Status.UP) {
				curLevel++;
				System.out.println("cur level increase. curLevel:" + curLevel);
			}
			else if(curStatus == Status.DOWN) {
				//move funtion
				curLevel--;
				System.out.println("cur level decrease. curLevel:" + curLevel);
			}
		}
	}
	
	private synchronized boolean isNeedStop() {
		for(ElevatorRequest request : requests) {
			if(request.level != curLevel) continue;
			
			if(request.aType == ElevatorRequest.ActionType.OUT_ELEVATOR) return true;
			if(request.aType == ElevatorRequest.ActionType.IN_ELEVATOR_UP 
					&& curStatus == Status.UP) return true;
			if(request.aType == ElevatorRequest.ActionType.IN_ELEVATOR_DOWN
					&& curStatus == Status.DOWN) return true;
		}
		
		return false;
	}
	
	private synchronized void updateStatus() {
		System.out.println("["+ Thread.currentThread().getId() + "] " + 
	        "before updateStatus:" + curStatus + ", curLevel:"+curLevel);
        if(requests.isEmpty()) {
        	curStatus = Status.STAND;
        	System.out.println("["+ Thread.currentThread().getId() + "] " + 
        	    "after updateStatus:" + curStatus);
        	return;
        }
        
        if(curStatus == Status.UP) {
        	if(requests.last().level < curLevel) {
        		curStatus = Status.DOWN;
        	}
        	//when elevator reaches the highest level and the request is to go down
        	else if(requests.last().level == curLevel 
        			&& requests.last().aType == ElevatorRequest.ActionType.IN_ELEVATOR_DOWN) 
        	{
        		curStatus = Status.DOWN;
        	}
        }
        else if(curStatus == Status.DOWN) {
        	if(requests.first().level > curLevel) {
        		curStatus = Status.UP;
        	}
        	//when elevator reaches the highest level and the request is to go down
        	else if(requests.first().level == curLevel 
        			&& requests.first().aType == ElevatorRequest.ActionType.IN_ELEVATOR_UP) 
        	{
        		curStatus = Status.UP;
        	}
        }
        else {
        	if(requests.last().level > curLevel) {
        		curStatus = Status.UP;
        	}
        	else if(requests.last().level < curLevel) {
        		curStatus = Status.DOWN;
        	}
        	else {
        		if(requests.last().aType == ElevatorRequest.ActionType.IN_ELEVATOR_UP) {
        			curStatus = Status.UP;
        		}
        		else if(requests.last().aType == ElevatorRequest.ActionType.IN_ELEVATOR_DOWN) {
        			curStatus = Status.DOWN;
        		}
        	}
        }
        System.out.println("["+ Thread.currentThread().getId() + "] " + 
            "after updateStatus:" + curStatus);
	}
	
	private synchronized void clearRequest() {
		Iterator<ElevatorRequest> iterator = requests.iterator();
		while(iterator.hasNext()) {
			ElevatorRequest request = iterator.next();
			if(request.level != curLevel) {
				continue;
			}
			
			if(request.aType == ElevatorRequest.ActionType.OUT_ELEVATOR) {
				iterator.remove();
			}
			else if(request.aType == ElevatorRequest.ActionType.IN_ELEVATOR_UP) {
				if(curStatus == Status.UP) {
					iterator.remove();
				}
			}
			else {
				if(curStatus == Status.DOWN) {
					iterator.remove();
				}
			}
		}
	}
	
	private void moveToCurLevel() throws Exception {
		System.out.println("["+ Thread.currentThread().getId() + "] " +
	        "moveToCurLevel. id:"+id+", level:"+curLevel);
		updateStatus();
		while(isNeedStop()) {
			clearRequest();
			
			openLevel = curLevel;
			System.out.println("["+ Thread.currentThread().getId() + "] " + 
			    "id:" + id + " open door. level :" + curLevel);
			//open door wait for new requests
			Thread.sleep(2000);
			openLevel = -1;
			System.out.println("["+ Thread.currentThread().getId() + "] " + 
				    "id:" + id + " close door. level :" + curLevel);
			
			updateStatus();
		}
	}
	
	public synchronized void addRequest(ElevatorRequest request) {
		System.out.println("["+ Thread.currentThread().getId() + "] " + "addRequest. elev: id:" + id
				+ ", req level:" + request.level + ", aType:" + request.aType);
		requests.add(request);
		
		for(ElevatorRequest req : requests) {
			System.out.println("one req. elev: id:" + id
					+ ", req level:" + req.level + ", aType:" + req.aType);
		}
		
		notify();
	}
}

class ElevatorControler {
	ArrayList<Elevator> elevators = new ArrayList<>();
	HashMap<Integer, Thread> eleThreadMap = new HashMap<>();
	Random rand = new Random();
	
	public ElevatorControler() {
		for(int i = 0; i < 2; i++) {
			Elevator ele = new Elevator(i);
			elevators.add(ele);
		}
	}
	
	public void startElevator(int id) {
		Thread t = new Thread(()->{
			elevators.get(id).start();
		});
		t.start();
		eleThreadMap.put(id, t);
	}
	
	public void stopElevator(int id) {
		
	}
	
	public void addOutEleRequest(ElevatorRequest req, int eleId) {
		elevators.get(eleId).addRequest(req);
	}
	
	public int getOpenDoorLevel(int eleId) {
	    return elevators.get(eleId).getOpenLevel();
	}
	
	public int addInEleRequest(ElevatorRequest req) {
		int id = 0;
		while(true) {
			id = rand.nextInt(elevators.size());
			if(elevators.get(id).getCurStatus() != Elevator.Status.STOP) {
				break;
			}
		}
		
		int score = calcScoreForElev(elevators.get(id), req);
		for(int i = 0; i < elevators.size(); i++) {
			if(elevators.get(i).getCurStatus() == Elevator.Status.STOP) {
				continue;
			}
			
			int tempScore = calcScoreForElev(elevators.get(i), req);
			if(tempScore < score) {
				score = tempScore;
				id = i;
			}
		}
		
		System.out.println("["+ Thread.currentThread().getId() + "] " + "addInEleRequest id:" + id);
		elevators.get(id).addRequest(req);
		return id;
	}
	
	private int calcScoreForElev(Elevator elev, ElevatorRequest req) {
		if(elev.getCurStatus() == Elevator.Status.STAND) {
			return Math.abs(req.level-elev.getCurLevel());
		}
		else if(elev.getCurStatus() == Elevator.Status.UP) {
			if(req.aType == ElevatorRequest.ActionType.IN_ELEVATOR_UP 
					&& req.level > elev.getCurLevel()) {
				return Math.abs(req.level-elev.getCurLevel());
			}
		}
		else if(elev.getCurStatus() == Elevator.Status.DOWN) {
			if(req.aType == ElevatorRequest.ActionType.IN_ELEVATOR_DOWN
					&& req.level < elev.getCurLevel()) {
				return Math.abs(req.level-elev.getCurLevel());
			}
		}
		
		return Integer.MAX_VALUE;
	}
	
	
	
	static public void test() throws Exception{
		ElevatorControler elevControl = new ElevatorControler();
		elevControl.startElevator(0);
		//elevControl.startElevator(1);
		
		System.out.println("test1**********************************************");
		ElevatorRequest req = new ElevatorRequest(ElevatorRequest.ActionType.IN_ELEVATOR_UP, 0);
		int id = elevControl.addInEleRequest(req);
		
		while(elevControl.getOpenDoorLevel(id) != 0) {
		}
		if(elevControl.getOpenDoorLevel(id) == 0) {
			req = new ElevatorRequest(ElevatorRequest.ActionType.OUT_ELEVATOR, 0);
			elevControl.addOutEleRequest(req, id);
		}
		
		
		Thread.sleep(5000);
		
		System.out.println("test2**********************************************");
		req = new ElevatorRequest(ElevatorRequest.ActionType.IN_ELEVATOR_UP, 5);
		elevControl.addInEleRequest(req);
		req = new ElevatorRequest(ElevatorRequest.ActionType.IN_ELEVATOR_UP, 7);
		elevControl.addInEleRequest(req);		
		req = new ElevatorRequest(ElevatorRequest.ActionType.IN_ELEVATOR_UP, 8);
		elevControl.addInEleRequest(req);
		Thread.sleep(10000);
		req = new ElevatorRequest(ElevatorRequest.ActionType.IN_ELEVATOR_UP, 4);
		elevControl.addInEleRequest(req);
		
		
		
		
		
	}
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class SpotLocation {
	int level;
	int row;
	int spotNum;
}

class SpotTicket {
	public SpotLocation location;
	public int startTime = 0;
	public int stopTime = 0;
	int spotNum = 1;
	int fee = 0;
}

enum SpotSize {
	Bicycle,
	General,
	Large
}

class Vehicle {
	private SpotSize size;
	private int neededSpotsNum = 1;
	SpotTicket ticket = null;
	
	public Vehicle(SpotSize size, int neededSpotsNum) {
		this.size = size;
        this.neededSpotsNum = neededSpotsNum;
	}
	
	public int getNeededSpotsNum() {
		return neededSpotsNum;
	}
	
	public SpotSize getSize() {
		return size;
	}
	
	public void setSpotTicket(SpotTicket ticket) {
		this.ticket = ticket;
	}
	
	public SpotTicket getSpotTicket() {
		return this.ticket;
	}
}

class CarSpot {
	private SpotLocation location;
	private SpotSize size;
	private Vehicle vehicle = null;
	
	public CarSpot(SpotLocation location, SpotSize size) {
		this.location = location;
		this.size = size;
	}
	
	public SpotLocation getLocation() {
		return location;
	}
	
	public SpotSize getSize() {
		return size;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}

class ParkingLevel {
	private ArrayList<ArrayList<CarSpot> > spots = new ArrayList<>();
	int levelNum;
	
	ParkingLevel(int levelNum) {
		this.levelNum = levelNum;
	}
	
	SpotLocation parkVehicle(Vehicle v) {
    	SpotSize spotSize = v.getSize();
    	int neededSpotsNum = v.getNeededSpotsNum();
    	for(int i = 0; i < spots.size(); i++) {
			int availNum = 0;
			ArrayList<CarSpot> rowSpots = spots.get(i);
			for(int j = 0; j < rowSpots.size(); j++) {
				if(rowSpots.get(j).getSize().ordinal() >= spotSize.ordinal() 
						&& rowSpots.get(j).getVehicle() == null) {
					availNum++;
				}
				else {
					availNum = 0;
				}
				
				if(availNum == neededSpotsNum) {
					SpotLocation spotLocation = new SpotLocation();
					spotLocation.level = levelNum;
					spotLocation.row = i;
					spotLocation.spotNum = j-availNum+1;
					
					setVehicleToSpots(spotLocation.row, spotLocation.spotNum, neededSpotsNum, v);
					
					return spotLocation;
				}
			}
		}
    	
    	return null;
	}
    
	private void setVehicleToSpots(int row, int spotNum, int neededSpotsNum, Vehicle v) {
		for(int i = 0; i < neededSpotsNum; i++) {
			spots.get(row).get(spotNum+i).setVehicle(v);
		}
	}
	
    void removeVehicle(Vehicle v) {
    	SpotLocation loc = v.getSpotTicket().location;
    	for(int i = 0; i < v.getNeededSpotsNum(); i++) {
    		spots.get(loc.row).get(loc.spotNum+i).setVehicle(null);
    	}
    }
}

class ParkingLot {
	private ArrayList<ParkingLevel> levelSpot = new ArrayList<>();
	
	public boolean parkVehicle(Vehicle v) {
		for(ParkingLevel level : levelSpot) {
			SpotLocation loc = level.parkVehicle(v);
			if(loc != null) {
				SpotTicket ticket = new SpotTicket();
				ticket.location = loc;
				//ticket.startTime
				v.setSpotTicket(ticket);
				return true;
			}
		}
		
		return false;
	}
	
	public int fetchVehicle(Vehicle v) {
		SpotTicket ticket = v.getSpotTicket();
		//ticket.stopTime =
		//ticket.fee = 
		//return ticket.fee
		return 0;
	}
	
	public void payTheFee(Vehicle v, int fee) {
		//check if the fee is correct
		levelSpot.get(v.getSpotTicket().location.level).removeVehicle(v);
	}
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
























////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class DesignQuestions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//FileSystem.test();
		try {
			ElevatorControler.test();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
