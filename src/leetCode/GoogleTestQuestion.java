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
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CarPeopleMatchProblem.test();
	}

}
