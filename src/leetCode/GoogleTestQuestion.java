package leetCode;

import java.util.*;

public class GoogleTestQuestion {
	
	static public class CarPeopleMatchProblem {
		int maxX = Integer.MIN_VALUE;
		int minX = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		
		int minDistance = Integer.MAX_VALUE;
		List<List<int []>> minRst = null;
		int iterationNum = 0;
		
		List<List<int[]>> peopleCarMatchUsingBFS(int [][] carCoordinates, int [][] peopleCoordinates) {
			getCoordinateBoundary(carCoordinates);
			getCoordinateBoundary(peopleCoordinates);
			System.out.println("maxX:"+maxX+", minX:"+minX+", maxY:"+maxY+", minY:"+minY);

			int[][] matrix = new int[maxX-minX+1][maxY-minY+1];
			setMatrixValue(matrix, peopleCoordinates, 1);
			setMatrixValue(matrix, carCoordinates, 2);
			System.out.println("matrix:"+Arrays.deepToString(matrix));
			
			List<List<int[]>> rst = new ArrayList<>();
			for(int[] oneCoordinate : peopleCoordinates) {
				bfsForOnePeople(oneCoordinate, matrix, rst);
			}
			
			return rst;
		}
		
		void getCoordinateBoundary(int [][] coordinates) {
			for(int[] oneCoordinate : coordinates) {
				if(oneCoordinate[0] > maxX) {
					maxX = oneCoordinate[0];
				}
				
				if(oneCoordinate[0] < minX) {
					minX = oneCoordinate[0];
				}
				
				if(oneCoordinate[1] > maxY) {
					maxY = oneCoordinate[1];
				}
				
				if(oneCoordinate[1] < minY) {
					minY = oneCoordinate[1];
				}
			}
		}
		
		void setMatrixValue(int[][] matrix, int [][] coordinates, int value) {
			for(int [] coordinate : coordinates) {
				int x = coordinate[0] - minX;
				int y = coordinate[1] - minY;
				matrix[x][y] = value;
			}
		}
		
		void bfsForOnePeople(int[] pCoordinate, int[][] matrix, List<List<int[]>> rst) {
			int[] pAdjCoordinate = {pCoordinate[0]-minX, pCoordinate[1]-minY};
			
			boolean[][] visit = new boolean[maxX-minX+1][maxY-minY+1];
			LinkedList<int[]> queue = new LinkedList<>();
			queue.offer(pAdjCoordinate);
			visit[pAdjCoordinate[0]][pAdjCoordinate[1]] = true;
			
			int[][] dirctions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
			while(!queue.isEmpty()) {
				int[] curPoint = queue.poll();
				for(int[] oneDir : dirctions) {
					int newX = curPoint[0]-oneDir[0];
					int newY = curPoint[1]-oneDir[1];
					if(newX<0 || newX >(maxX-minX) || newY < 0 || newY > (maxY-minY)) {
						continue;
					}
					
					if(matrix[newX][newY] == 2) {
						matrix[newX][newY] = 3;
						List<int[]> onePair = new ArrayList<>();
						onePair.add(pCoordinate);
						onePair.add(new int[] {newX+minX, newY+minY});
						rst.add(onePair);
						return;
					}
					
					if(!visit[newX][newY]) {
						visit[newX][newY] = true;
						queue.add(new int[] {newX, newY});
					}
				}
			}
		}
	
		class CoordinateDistance {
		    int distance;
		    int[] peopleCoordinate = null;
		    int[] carCoordinate = null;
		    public CoordinateDistance(int[] peopleCoordinate, int[] carCoordinate) {
		    	this.distance = Math.abs(peopleCoordinate[0]-carCoordinate[0]) + Math.abs(peopleCoordinate[1]
		    			- carCoordinate[1]);
		    	this.peopleCoordinate = peopleCoordinate;
		    	this.carCoordinate = carCoordinate;
		    }
		} 	
		
		List<List<int[]>> peopleCarMatchUsingPQ(int [][] carCoordinates, int [][] peopleCoordinates) {
			PriorityQueue<CoordinateDistance> pq = new PriorityQueue<>(new Comparator<CoordinateDistance>() {
				public int compare(CoordinateDistance a, CoordinateDistance b) {
					return a.distance-b.distance;
				}
			});
			for(int [] carCoordinate : carCoordinates) {
				for(int [] peopleCoordinate : peopleCoordinates) {
					CoordinateDistance distance = this.new CoordinateDistance(peopleCoordinate, carCoordinate);
					pq.offer(distance);
				}
			}
			
			List<List<int []>> rst = new ArrayList<>();
			while(!pq.isEmpty()) {
				CoordinateDistance dis = pq.poll();
				System.out.println("dis carCoordinate:" + Arrays.toString(dis.carCoordinate)+", peopleCoordinate:"
						+ Arrays.toString(dis.peopleCoordinate) + ", distance:" + dis.distance);
				if(isAlreadyAssigned(dis, rst)) {
					continue;
				}
				
				List<int []> onePair = new ArrayList<>();
				onePair.add(dis.peopleCoordinate);
				onePair.add(dis.carCoordinate);
				rst.add(onePair);
				
				if(rst.size() == peopleCoordinates.length) {
					break;
				}
			}
			
			return rst;
		}
		
		boolean isAlreadyAssigned(CoordinateDistance dis, List<List<int[]>> rst) {
			for(List<int []> onePair : rst) {
				int[] peopleCoordinate = onePair.get(0);
				int[] carCoordinate = onePair.get(1);
				if(dis.peopleCoordinate[0] == peopleCoordinate[0] 
						&& dis.peopleCoordinate[1] == peopleCoordinate[1]) {
					return true;
				}
				
				if(dis.carCoordinate[0] == carCoordinate[0] 
						&& dis.carCoordinate[1] == carCoordinate[1]) {
					return true;
				}				
			}
			
			return false;
		}
		
		void doPeopleCarMatchUsingDFS(int [][] carCoordinates, int [][] peopleCoordinates, 
				List<List<int[]>> rst, int curDistance)
		{
            if(rst.size() == peopleCoordinates.length) {
            	System.out.println("one iterator. curDistance:" + curDistance + ", iterationNum:"+iterationNum);
            	iterationNum++;
            	printRst(rst);
				if(curDistance < minDistance) {
					minDistance = curDistance;
					minRst = (List<List<int[]>>)(((ArrayList<List<int[]>>)(rst)).clone());
				}
				return;
			}
            
            int [] peopleCoordinate = peopleCoordinates[rst.size()];
			for(int [] carCoordinate : carCoordinates) {
				if(isCoordinateVisit(carCoordinate, rst)) {
					continue;
				}
				
				List<int []> onePair = new ArrayList<>();
				onePair.add(peopleCoordinate);
				onePair.add(carCoordinate);
				
				rst.add(onePair);
				int newDistance = curDistance + Math.abs(peopleCoordinate[0]-carCoordinate[0]) + Math.abs(peopleCoordinate[1]
		    			- carCoordinate[1]);
				doPeopleCarMatchUsingDFS(carCoordinates, peopleCoordinates, rst, newDistance);
				rst.remove(rst.size()-1);
			}
		}
		
		boolean isCoordinateVisit(int [] coordinate, List<List<int[]>> rst) {
			for(List<int[]> onePair: rst) {
				if(onePair.get(0)[0] == coordinate[0] && onePair.get(0)[1] == coordinate[1]) {
					return true;
				}
				if(onePair.get(1)[0] == coordinate[0] && onePair.get(1)[1] == coordinate[1]) {
					return true;
				}				
				
			}
			
			return false;
		}
		
		List<List<int[]>> peopleCarMatchUsingDFS(int [][] carCoordinates, int [][] peopleCoordinates) {
			List<List<int[]>> rst = new ArrayList<>();
			doPeopleCarMatchUsingDFS(carCoordinates, peopleCoordinates, rst, 0);
			System.out.println("minDis:"+minDistance);
			return minRst;
		}
		
		static void printRst(List<List<int[]>> rst) {
	    	for(List<int []> onePair : rst) {
	    		int[] people = onePair.get(0);
	    		int[] car = onePair.get(1);
	    		System.out.println("people:"+Arrays.toString(people)+", car:"+Arrays.toString(car));
	    	}
		}
	
	    static void test() {
	    	int[][] peopleCoordinates = {{-2,-2}, {0,-3}, {-1,1}, {2,1}, {2,2}};
	    	int[][] carCoordinates = {{-1,4}, {0,3}, {0,0}, {1,-2}, {1,1}, {2,3}, {3,2}};
	    	
	    	CarPeopleMatchProblem cp = new CarPeopleMatchProblem();
	    	/*
	    	 * 
	    	 * people:[-2, -2], car:[1, -2]
people:[0, -3], car:[0, 0]
people:[-1, 1], car:[1, 1]
people:[2, 1], car:[3, 2]
people:[2, 2], car:[2, 3]
	    	 * */
	    	//List<List<int[]>> rst = cp.peopleCarMatchUsingBFS(carCoordinates, peopleCoordinates);
	    	
	    	/* 
	    	 * people:[2, 1], car:[1, 1]
people:[2, 2], car:[3, 2]
people:[0, -3], car:[1, -2]
people:[-1, 1], car:[0, 0]
people:[-2, -2], car:[-1, 4]

	    	 * */
	    	//List<List<int[]>> rst = cp.peopleCarMatchUsingPQ(carCoordinates, peopleCoordinates);
	    	
	    	/*
	    	 * minDis:11
result:
people:[-2, -2], car:[0, 0]
people:[0, -3], car:[1, -2]
people:[-1, 1], car:[-1, 4]
people:[2, 1], car:[1, 1]
people:[2, 2], car:[2, 3]
	    	 * 
	    	 * */
	    	List<List<int[]>> rst = cp.peopleCarMatchUsingDFS(carCoordinates, peopleCoordinates);
	    	
	    	System.out.println("result:");
	    	printRst(rst);
	    }
	}
	
	static public class GetCycle {
		List<List<Integer>> cycleList = new ArrayList<>();
		int visit[] = null;
		int father[] = null;
		
		void dfs(List<List<Integer>> graph, int curNode) {
			System.out.println("***************dfs, curNode:"+curNode);
			visit[curNode] = 1;
			List<Integer> neibors = graph.get(curNode);
			for(int neigh : neibors) {
				System.out.println("***************neigh"+ neigh + ", curNode:"+curNode);
				if(visit[neigh] == 1 && father[curNode] != neigh) {
					genCycle(curNode, neigh);
				}
				
				if(visit[neigh] == 0) {
					father[neigh] = curNode;
					System.out.println("neigh:"+neigh+", father:"+curNode);
					dfs(graph, neigh);
				}
			}
			
			visit[curNode] = 2;
		}
		
		void genCycle(int curNode, int neigh) {
			System.out.println("genCycle curNode:"+curNode+", neigh:"+neigh);
			//System.out.println("father:"+Arrays.toString(father));
			List<Integer> oneCycle = new ArrayList<>();
			while(curNode != neigh) {
				oneCycle.add(curNode);
				curNode = father[curNode];
			}
			
			oneCycle.add(neigh);
			System.out.println("oneCycle:"+oneCycle);
			cycleList.add(oneCycle);
		}
		
		List<List<Integer>> getCycleFromGraph(List<List<Integer>> graph) {
			System.out.println("graph size:" + graph.size());
			
			visit = new int[graph.size()];
			father = new int[graph.size()];
			Arrays.fill(father, -1);
			
			for(int i = 0; i < graph.size(); i++) {
				if(visit[i] == 0) {
					dfs(graph, i);
				}
			}
			
			return cycleList;
		}
		
		void bfs(List<List<Integer>> graph, int node) {
			LinkedList<Integer> queue = new LinkedList<>();
			queue.offer(node);
			visit[node] = 1;
			
			while(!queue.isEmpty()) {
				int curNode = queue.poll();
				List<Integer> neighbors = graph.get(curNode);
				for(int neighbor : neighbors) {
					if(visit[neighbor] == 1 &&  neighbor != father[curNode]) {
						genCyclesForBFS(curNode, neighbor);
					}
					
					if(visit[neighbor] == 0) {
						visit[neighbor] = 1;
						father[neighbor] = curNode;
						System.out.println("neighbor:"+neighbor+", father:"+ curNode);
						queue.offer(neighbor);
					}
					
				}
			}
		}
		
		void genCyclesForBFS(int curNode, int neighbor) {
			System.out.println("curNode:"+curNode+", neigh:"+neighbor);
			List<Integer> curPath = new ArrayList<>();
			List<Integer> neiPath = new ArrayList<>();
			int curFather = father[curNode];
			curPath.add(curNode);
			int neiFather = father[neighbor];
			neiPath.add(neighbor);
			while(curFather != neiFather) {
				curPath.add(curFather);
				neiPath.add(neiFather);
			}
			
			curPath.add(curFather);
			for(int i = neiPath.size()-1; i >= 0; i--) {
				curPath.add(neiPath.get(i));
			}
			
			System.out.println("curPath:" + curPath);
			cycleList.add(curPath);
			
		}
		
		List<List<Integer>> getCycleFromGraphBFS(List<List<Integer>> graph) {
			System.out.println("graph size:" + graph.size());
			
			visit = new int[graph.size()];
			father = new int[graph.size()];
			Arrays.fill(father, -1);
			
			for(int i = 0; i < graph.size(); i++) {
				if(visit[i] == 0) {
					bfs(graph, i);
				}
			}
			
			return cycleList;
		}
		
		static void test() {
			List<List<Integer>> graph = new ArrayList<>();
			List<Integer> oneNeigh = new ArrayList<>();
			oneNeigh.add(1);
			oneNeigh.add(2);
			oneNeigh.add(3);
			graph.add(oneNeigh);
			oneNeigh = new ArrayList<>();
			oneNeigh.add(0);
			oneNeigh.add(2);
			graph.add(oneNeigh);
			oneNeigh = new ArrayList<>();
			oneNeigh.add(0);
			oneNeigh.add(1);
			oneNeigh.add(3);
			graph.add(oneNeigh);
			oneNeigh = new ArrayList<>();
			oneNeigh.add(0);
			oneNeigh.add(2);
			graph.add(oneNeigh);
			oneNeigh = new ArrayList<>();
			oneNeigh.add(5);
			oneNeigh.add(6);
			graph.add(oneNeigh);			
			oneNeigh = new ArrayList<>();
			oneNeigh.add(4);
			oneNeigh.add(6);
			graph.add(oneNeigh);
			oneNeigh = new ArrayList<>();
			oneNeigh.add(4);
			oneNeigh.add(5);
			graph.add(oneNeigh);			
			
//			GetCycle cycle = new GetCycle();
//			List<List<Integer>> cycleList = cycle.getCycleFromGraph(graph);
			
			GetCycle cycle = new GetCycle();
			List<List<Integer>> cycleList = cycle.getCycleFromGraphBFS(graph);			
			System.out.println("cycleList:"+cycleList);
		}
	}
	
	static public class TaskAssignement {
		
		boolean dfs(List<List<String>> assignedPairs, HashMap<String, List<String>> staffPreference,
				List<String> staffList)
		{
 			if(assignedPairs.size() == staffList.size()) {
 				return true;
 			}
 			
 			String curStaff = staffList.get(assignedPairs.size());
 			List<String> prefTasks = staffPreference.get(curStaff);
 			for(String prefTask : prefTasks) {
 				if(!isTaskAssigned(prefTask, assignedPairs)) {
 					List<String> pair = new ArrayList<>();
 					pair.add(curStaff);
 					pair.add(prefTask);
 					
 					assignedPairs.add(pair);
 					if(dfs(assignedPairs, staffPreference, staffList)) {
 						return true;
 					}
 					
 					assignedPairs.remove(assignedPairs.size()-1);
 				}
 			}
			
			return false;
		}
		
		boolean isTaskAssigned(String task, List<List<String>> assignedPairs) {
			for(List<String> pair : assignedPairs) {
				if(task.equals(pair.get(1))) {
					return true;
				}
			}
			
			return false;
		}
		
		List<List<String>> assignTaskDFS(HashMap<String, List<String>> staffPrefernce, List<String> staffList) 
		{
			List<List<String>> assignedPairs = new ArrayList<>();
			if(dfs(assignedPairs, staffPrefernce, staffList)) {
				return assignedPairs;
			}
			else {
				return null;
			}
		}
		
		
		boolean hungarianDFS(String staff, HashMap<String,String> staffTaskMap, HashMap<String, String> taskStaffMap,
				HashMap<String, List<String>> staffPreference, HashSet<String> checkedTask) 
		{
			List<String> tasks = staffPreference.get(staff);
			for(String task : tasks) {
				if(checkedTask.contains(task)) {
					continue;
				}
				
				checkedTask.add(task);
				if(!taskStaffMap.containsKey(task) || hungarianDFS(taskStaffMap.get(task),
						staffTaskMap, taskStaffMap, staffPreference, checkedTask)) 
				{
					staffTaskMap.put(staff, task);
					taskStaffMap.put(task, staff);
					return true;
				}
			}
			
			return false;
		}
		
		void assignTaskHungarianDFS(HashMap<String, List<String>> staffPreference, 
				List<String> staffList, HashMap<String,String> staffTaskMap,
				HashMap<String,String> taskStaffMap) 
		{
			for(String staff : staffList) {
				HashSet<String> checkedTask = new HashSet<>();
				if(hungarianDFS(staff, staffTaskMap, taskStaffMap, staffPreference, checkedTask)) {
					System.out.println("for staff:"+staff+" succ");
				}
				else {
					System.out.println("for staff:"+staff+" fail");
				}
			}
			
			System.out.println("staffTaskMap:" + staffTaskMap);
		}
		
		
		
		
		static void test() {
			List<String> staffList = new ArrayList<>();
			staffList.add("A");
			staffList.add("B");
			staffList.add("C");
			staffList.add("D");
			
//			List<String> tasks = new ArrayList<>();
//			tasks.add("a");
//			tasks.add("b");
//			tasks.add("c");
//			tasks.add("d");
			
			HashMap<String, List<String>> staffPrefernce = new HashMap<>();

			List<String> aList = new ArrayList<>();
			aList.add("a");
			aList.add("c");
			staffPrefernce.put("A", aList);
			
			List<String> bList = new ArrayList<>();
			bList.add("a");
			bList.add("b");
			bList.add("c");
			staffPrefernce.put("B", bList);

			List<String> cList = new ArrayList<>();
			cList.add("c");
			staffPrefernce.put("C", cList);
			
			List<String> dList = new ArrayList<>();
			dList.add("c");
			staffPrefernce.put("D", dList);
		
			TaskAssignement ass = new TaskAssignement();
//			List<List<String>> rst = ass.assignTaskDFS(staffPrefernce, staffList);
//			if(rst != null) {
//				System.out.println("rst:" + rst);
//			}
//			else {
//				System.out.println("no result");
//			}
			
			HashMap<String,String> staffTaskMap = new HashMap<>();
			HashMap<String,String> taskStaffMap = new HashMap<>();
			ass.assignTaskHungarianDFS(staffPrefernce, staffList, staffTaskMap, taskStaffMap);
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	};
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//CarPeopleMatchProblem.test();
		GetCycle.test();
		//TaskAssignement.test();
	}

}
