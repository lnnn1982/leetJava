package leetCode;
import java.util.*;

//https://leetcode.com/problems/shortest-bridge/
class ShortestBridgeDFSSolution {
    public int shortestBridge(int[][] A) {
        int m = A.length;
        int [][] islandGroup = new int[m][m];

        getOneIsland(A, islandGroup);
        
        int is1Num = 0, is2Num = 0;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < m; j++) {
                if(islandGroup[i][j] == 1) {
                    is1Num++;
                }
                else {
                    if(A[i][j] == 1) {
                        islandGroup[i][j] = 2;
                        is2Num++;
                    }
                }
            }
        }
        
        //System.out.println("is1Num:" + is1Num + ", is2Num:" + is2Num);
        
        if(is1Num < is2Num) {
            return getMinFlipNum(1, 2, islandGroup);
        }
        else {
            return getMinFlipNum(2, 1, islandGroup);
        }
    }
    
    
    public void getOneIsland(int[][] A, int [][] islandGroup) {
        int m = A.length;
        boolean [][] visit = new boolean[m][m];
        
        int [] startPoint = new int[2];
        outer:for(int i = 0; i < m; i++) {
            for(int j = 0; j < m; j++) {
                if(A[i][j] == 1) {
                    startPoint[0] = i;
                    startPoint[1] = j;
                    //System.out.println("i:"+i+",j:"+j);
                    break outer;
                }
            }
        }
        
        dfsVist(A, startPoint, visit, islandGroup);
    }
    
    
    public void dfsVist(int[][]A, int[] curNode, boolean[][] visit, int[][] islandMat) {
        if(visit[curNode[0]][curNode[1]]) {
            return;
        }
        
        visit[curNode[0]][curNode[1]] = true;
        islandMat[curNode[0]][curNode[1]] = 1;
        //System.out.println("curNode[0]:"+curNode[0]+",curNode[1]:"+curNode[1]);
        
        int[][] direction = {{-1,0}, {1,0}, {0, -1}, {0, 1}};
        for(int[] oneDir : direction) {
            int row = curNode[0]+oneDir[0];
            int col = curNode[1]+oneDir[1];
            
            if(row < 0 || row >= A.length || col < 0 || col >= A.length) {
                continue;
            }
            
            if(A[row][col] == 1) {
               dfsVist(A, new int[]{row, col}, visit, islandMat);
            }
        }
    }
    
    
    public int getMinFlipNum(int myIsland, int oIsland, int [][] islandMat)     {
        int m = islandMat.length;
        int minFlip = Integer.MAX_VALUE;
        
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < m; j++) {
                if(islandMat[i][j] == myIsland) {
                    int ret = getOneFlipNum(new int[]{i,j}, oIsland, islandMat);
                    if(ret != -1 && ret < minFlip) {
                        minFlip = ret;
                    }
                }
            }
        }
        
        return minFlip;
    }
    
    public int getOneFlipNum(int[]startP, int oIsland, int [][] islandMat) 
    {
        //System.out.println("x:"+startP[0]+", y:"+startP[1]+", oIsland:"+oIsland);
        //System.out.println(Arrays.deepToString(islandMat));
        int m = islandMat.length;
        boolean [][] visit = new boolean[m][m];
        int[][] direction = {{-1,0}, {1,0}, {0, -1}, {0, 1}};  
        
        LinkedList<int []> queue = new LinkedList<>();
        queue.offer(startP);
        visit[startP[0]][startP[1]] = true;
        
        LinkedList<int []> nextLevelQueue = new LinkedList<>();
        int flipNum = 0;
        
        while(!queue.isEmpty()) {
            int[] oneNode = queue.poll();
            for(int[] oneDir : direction) {
                int row = oneNode[0]+oneDir[0];
                int col = oneNode[1]+oneDir[1];
                if(row < 0 || row >= m || col < 0 || col >= m) {
                    continue;
                }
                
                if(islandMat[row][col] == oIsland) {
                    //System.out.println("row:"+row+",col:"+col+", flipNum:"+flipNum);
                    return flipNum;
                }
                
                if((!visit[row][col]) && islandMat[row][col] == 0) {
                    nextLevelQueue.offer(new int[]{row, col});
                    visit[row][col] = true;
                }
            }
            
            if(queue.isEmpty() && !nextLevelQueue.isEmpty()) {
                LinkedList<int []> tmpQ = null;
                tmpQ = queue;
                queue = nextLevelQueue;
                nextLevelQueue = tmpQ;
                flipNum++;
            } 
        }
        
        return -1;
    }
}

////////////////////////////////////////////////////////////////////////////////////////
//https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
class DistanceKSolution {
  class TreeNode {
       int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }
   }

  class GraphNode {
      int val;
      List<GraphNode> nList;
  };

  GraphNode getNodeFromGraph(int val, Map<Integer, GraphNode> graph) {
      GraphNode node = graph.get(val);
      if(node != null) {
          return node;
      }
      node = new GraphNode();
      node.val = val;
      node.nList = new ArrayList<>();

      graph.put(val, node);
      return node;
  }

  void genGraph(TreeNode root, Map<Integer, GraphNode> graph) {
      if(root == null) {
          return;
      }

      GraphNode rootGNode = getNodeFromGraph(root.val, graph);
      if(root.left != null) {
          GraphNode lGNode = getNodeFromGraph(root.left.val, graph);
          rootGNode.nList.add(lGNode);
          lGNode.nList.add(rootGNode);
          genGraph(root.left, graph);
      }

      if(root.right != null) {
          GraphNode rGNode = getNodeFromGraph(root.right.val, graph);
          rootGNode.nList.add(rGNode);
          rGNode.nList.add(rootGNode);
          genGraph(root.right, graph);
      }
  }
  
  public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
      Map<Integer, GraphNode> graph = new HashMap<>();
      genGraph(root, graph);
      
      List<GraphNode> queue = new ArrayList<>();
      HashSet<GraphNode> visitSet = new HashSet<>();
      
      List<Integer> rstList = new ArrayList<>();
      GraphNode tNode = graph.get(target.val);
      if(tNode == null) {
          return rstList;
      }
      
      queue.add(tNode);
      visitSet.add(tNode);
      
      int level = 0;
      while(!queue.isEmpty()) {
          if(level == K) {
              for(GraphNode oneRst : queue) {
                  rstList.add(oneRst.val);
              }
              
              return rstList;
          }
          
          List<GraphNode> nextQ = new ArrayList<>();
          for(GraphNode curNode : queue) {
              for(GraphNode neigh : curNode.nList) {
                  if(!visitSet.contains(neigh)) {
                      nextQ.add(neigh);
                      visitSet.add(neigh);
                  }
              }
          }
          
          queue = nextQ;
          level++;
      }
      
      return rstList;
      
      
      
  }
}





////////////////////////////////////////////////////////////////////////////////////////





public class GraphQuestion {

    //amazon maze question
    public static boolean maze(int[][] matrix) {
    	  if(matrix == null || matrix.length == 0) {
    	      return false;	
    	  }
    	  
    	  int m = matrix.length;
    	  int n = matrix[0].length;
    	  if(n==0 || matrix[0][0] == 1) {
    	      return false;	
    	  }
    	  
		    int [][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
		    boolean [][] visited = new boolean[m][n];
		    
		    LinkedList<int []> queue = new LinkedList<>();
		    queue.add(new int[]{0, 0});
		    visited[0][0] = true;
		    
		    while(!queue.isEmpty()) {
		        int [] curPos = queue.poll();
		        if (matrix[curPos[0]][curPos[1]] == 9) {
		            return true;	
		        }
		        
		        for(int [] oneDirection : directions) {
		        	  int newX = curPos[0]+oneDirection[0];
		        	  int newY = curPos[1]+oneDirection[1];
		        	  
		        	  if(newX >= m || newX < 0 || newY >= n || newY < 0) {
		        	  	continue;
		        	  }
		        	  
		        	  if(matrix[newX][newY] == 9) {
		        	      return true;
		        	  }
		        	  
		            if(!visited[newX][newY] && matrix[newX][newY] == 0) {
		                	visited[newX][newY] = true;
		                	int[] newPos = {newX, newY};
		                	queue.add(newPos);
		            } 
		        }
		    }
		    
		    return false;
    }
    
////////////////////////////////////////////////////////////////////////////////////////
    //https://leetcode.com/problems/shortest-bridge/
    public int shortestBridge(int[][] A) {
        int m = A.length;
        int [][] islandGroup = new int[m][m];
        boolean [][] visit = new boolean[m][m];
        int[][] direction = {{-1,0}, {1,0}, {0, -1}, {0, 1}};
        
        LinkedList<int []> queue = new LinkedList<>();
        outer:for(int i = 0; i < m; i++) {
            for(int j = 0; j < m; j++) {
                if(A[i][j] == 1) {
                    islandGroup[i][j] = 1;
                    queue.offer(new int[]{i,j});
                    //System.out.println("i:"+i+",j:"+j);
                    visit[i][j] = true;
                    break outer;
                }
            }
        }
        
        while(!queue.isEmpty()) {
            int[] oneNode = queue.poll();
            for(int[] oneDir : direction) {
                int row = oneNode[0]+oneDir[0];
                int col = oneNode[1]+oneDir[1];
                if(row < 0 || row >= m || col < 0 || col >= m) {
                    continue;
                }
                
                if((!visit[row][col]) && A[row][col] == 1) {
                    queue.offer(new int[]{row, col});
                    islandGroup[row][col] = 1;
                    visit[row][col] = true;
                }
            }
        }
        
        int is1Num = 0, is2Num = 0;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < m; j++) {
                if(islandGroup[i][j] == 1) {
                    is1Num++;
                }
                else {
                    if(A[i][j] == 1) {
                        islandGroup[i][j] = 2;
                        is2Num++;
                    }
                }
            }
        }
        
        //System.out.println("is1Num:" + is1Num + ", is2Num:" + is2Num);
        
        if(is1Num < is2Num) {
            return getMinFlipNum(1, 2, islandGroup);
        }
        else {
            return getMinFlipNum(2, 1, islandGroup);
        }
    }
    
    public int getMinFlipNum(int myIsland, int oIsland, int [][] islandMat)     {
        int m = islandMat.length;
        int minFlip = Integer.MAX_VALUE;
        
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < m; j++) {
                if(islandMat[i][j] == myIsland) {
                    int ret = getOneFlipNum(new int[]{i,j}, oIsland, islandMat);
                    if(ret != -1 && ret < minFlip) {
                        minFlip = ret;
                    }
                }
            }
        }
        
        return minFlip;
    }
    
    public int getOneFlipNum(int[]startP, int oIsland, int [][] islandMat) 
    {
        //System.out.println("x:"+startP[0]+", y:"+startP[1]+", oIsland:"+oIsland);
        //System.out.println(Arrays.deepToString(islandMat));
        int m = islandMat.length;
        boolean [][] visit = new boolean[m][m];
        int[][] direction = {{-1,0}, {1,0}, {0, -1}, {0, 1}};  
        
        LinkedList<int []> queue = new LinkedList<>();
        queue.offer(startP);
        visit[startP[0]][startP[1]] = true;
        
        LinkedList<int []> nextLevelQueue = new LinkedList<>();
        int flipNum = 0;
        
        while(!queue.isEmpty()) {
            int[] oneNode = queue.poll();
            for(int[] oneDir : direction) {
                int row = oneNode[0]+oneDir[0];
                int col = oneNode[1]+oneDir[1];
                if(row < 0 || row >= m || col < 0 || col >= m) {
                    continue;
                }
                
                if(islandMat[row][col] == oIsland) {
                    //System.out.println("row:"+row+",col:"+col+", flipNum:"+flipNum);
                    return flipNum;
                }
                
                if((!visit[row][col]) && islandMat[row][col] == 0) {
                    nextLevelQueue.offer(new int[]{row, col});
                    visit[row][col] = true;
                }
            }
            
            if(queue.isEmpty() && !nextLevelQueue.isEmpty()) {
                LinkedList<int []> tmpQ = null;
                tmpQ = queue;
                queue = nextLevelQueue;
                nextLevelQueue = tmpQ;
                flipNum++;
            } 
        }
        
        return -1;
    }
	
////////////////////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
    	int[][] matrix = {{0,0,0,0}, {1,1,9,0}, {1,0,1,1}};
    	boolean rst = maze(matrix);
    	System.out.println("rst:" + rst);
    	
    	int[][] matrix1 = {{0,1,0,0}, {1,1,9,0}, {1,0,1,1}};
    	rst = maze(matrix1);
    	System.out.println("rst:" + rst);
    	
    	int[][] matrix2 = {{0,1,0,0}, {0,1,9,0}, {0,0,1,1}};
    	rst = maze(matrix2);
    	System.out.println("rst:" + rst);
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
}
