package leetCode;
import java.util.*;

////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//https://www.cnblogs.com/grandyang/p/5285868.html
class WallsGatesSol {
	public void wallsAndGates(int[][] rooms) {
		if(rooms == null || rooms.length == 0 || rooms[0].length == 0) {
			return;
		}
		int m = rooms.length;
		int n = rooms[0].length;
		
		LinkedList<int[]> queue = new LinkedList<>();
		boolean[][] visit = new boolean[m][n];
		int[][] direct = {{0,-1}, {0,1}, {-1,0}, {1,0}};
		
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				if(rooms[i][j] == 0) {
					queue.offer(new int[] {i,j});
				}
			}
		}
		
		int level = 0;
		while(!queue.isEmpty()) {
			level++;
			LinkedList<int[]> nextLevelQueue = new LinkedList<>();
			for(int[] curPos : queue) {
				for(int[] oneDirect : direct) {
					int x = curPos[0]+oneDirect[0];
					int y = curPos[1]+oneDirect[1];
					if(x<0||x>=m||y<0||y>=n) {
						continue;
					}
					
					if(rooms[x][y] == -1) {
						continue;
					}
					
					if(rooms[x][y] == Integer.MAX_VALUE) {
						rooms[x][y] = level;
						nextLevelQueue.offer(new int[]{x, y});
					}
				}
			}
			
			queue = nextLevelQueue;
		}
		
		System.out.println(Arrays.deepToString(rooms));
	}
	
	static public void test() {
		int[][] rooms = {{Integer.MAX_VALUE, -1, 0, Integer.MAX_VALUE},
				{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, -1},
				{Integer.MAX_VALUE, -1, Integer.MAX_VALUE, -1},
				{0, -1, Integer.MAX_VALUE, Integer.MAX_VALUE}};
		WallsGatesSol sol = new WallsGatesSol();
		sol.wallsAndGates(rooms);
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////
//https://leetcode.com/problems/redundant-connection/solution/
class RedundantConnectionSolution {
    int[] visit = null;
    int[] father = null;
    boolean cycle = false;
    
    void dfs(int curNode, ArrayList[] neighbors) {
        if(cycle) {
            return;
        }
        
        visit[curNode] = 1;
        ArrayList<Integer> neighbor = neighbors[curNode];
        for(int oneNei : neighbor) {
            if(father[curNode] == oneNei) {
                continue;
            }
            
            if(visit[oneNei] == 1 || visit[oneNei] == 2) {
                cycle = true;
                visit[curNode] = 2;
                return;
            }
            
            if(visit[oneNei] == 0) {
                father[oneNei] = curNode;
                dfs(oneNei, neighbors);
            }
        }
        
        visit[curNode] = 2;
    }
   
    public int[] findRedundantConnection(int[][] edges) {
        if(edges == null || edges.length == 0) {
            return new int[]{};
        }
        
        ArrayList[] neighbors = new ArrayList[edges.length+1];
        for(int i = 0; i < neighbors.length; i++) {
            neighbors[i] = new ArrayList<>();
        }
        
        visit = new int[edges.length+1];
        father = new int[edges.length+1];
        

        for(int[] oneEdge : edges) {
            Arrays.fill(father, -1);
            Arrays.fill(visit, 0);
            
            neighbors[oneEdge[0]].add(oneEdge[1]);
            neighbors[oneEdge[1]].add(oneEdge[0]);

            dfs(oneEdge[0], neighbors);

            if(cycle) {
                return oneEdge;
            }
        }
        
        return new int[]{};
    }
    
    public int[] findRedundantConnectionUsingGroup(int[][] edges) {
        if(edges == null || edges.length == 0) {
            return new int[]{};
        }
        
        ArrayList<HashSet<Integer>> groupList = new ArrayList<>();
        int groupStatus = 0;
        
        for(int[] edge : edges) {
            int oneGroupInd = -1;
            int twoGroupInd = -1;
            
            for(int i = 0; i < groupList.size(); i++) {
                if(groupList.get(i).contains(edge[0]) && groupList.get(i).contains(edge[1])) {
                    return edge;
                }
                
                if(groupList.get(i).contains(edge[0])) {
                    oneGroupInd = i;
                }
                
                if(groupList.get(i).contains(edge[1])) {
                    twoGroupInd = i;
                }
            }
            
            if(oneGroupInd == -1 && twoGroupInd == -1) {
                HashSet<Integer> oneGroup = new HashSet<>();
                oneGroup.add(edge[0]);
                oneGroup.add(edge[1]);
                groupList.add(oneGroup);
                continue;
            }
            
            // && oneGroupInd != twoGroupInd
            if(oneGroupInd != -1 && twoGroupInd != -1) {
                HashSet<Integer> oneGroup = groupList.get(oneGroupInd);
                HashSet<Integer> twoGroup = groupList.get(twoGroupInd);
                oneGroup.addAll(twoGroup);
                groupList.remove(twoGroupInd);
                continue;
            }
            
            if(oneGroupInd != -1) {
                groupList.get(oneGroupInd).add(edge[1]);
            }
            else {
                groupList.get(twoGroupInd).add(edge[0]);
            }
        }
        
        return new int[]{};
    }
    
    boolean dfs(ArrayList<Integer>[] graph, int curNode, int targetNode) {
        if(curNode == targetNode) {
            return true;
        }
        
        visit[curNode] = 1;
        
        ArrayList<Integer> neighbors = graph[curNode];
        for(int oneNeigh : neighbors) {
            if(oneNeigh == targetNode) {
                return true;
            }
            
            if(visit[oneNeigh] == 1) {
                continue;
            }
            
            
            if(dfs(graph, oneNeigh, targetNode)) {
                return true;
            }
        }
        
        return false;
    }

    public int[] findRedundantConnectionUsingDFS(int[][] edges) {
        if(edges == null || edges.length == 0) {
            return new int[]{};
        }
        
        ArrayList<Integer>[] graph = new ArrayList[edges.length+1];
        for(int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        visit = new int[edges.length+1];
        
        for(int[] oneEdge : edges) {
            Arrays.fill(visit, 0);
            if(dfs(graph, oneEdge[0], oneEdge[1])) {
                return oneEdge;
            }
            
            graph[oneEdge[0]].add(oneEdge[1]);
            graph[oneEdge[1]].add(oneEdge[0]);   
        }
        
        
        return new int[]{};
    }

	class UnionFinder {
		int[] rank = null;
		int[] parent = null;

		UnionFinder(int size) {
			rank = new int[size];
			parent = new int[size];

			for (int i = 0; i < parent.length; i++) {
				parent[i] = i;
			}
		}

		int find(int node) {
			while (parent[node] != node) {
				node = parent[node];
			}

			return node;
		}

		boolean union(int node1, int node2) {
			int pNode1 = find(node1);
			int pNode2 = find(node2);

			// System.out.println("111111111111node1:"+node1+",pNode1:"+pNode1+",node2:"+node2+",pNode2:"+pNode2);

			if (pNode1 == pNode2)
				return false;

			if (rank[pNode1] == rank[pNode2]) {
				parent[pNode2] = pNode1;
				rank[pNode1]++;
			} else if (rank[pNode1] < rank[pNode2]) {
				parent[pNode1] = pNode2;
			} else {
				parent[pNode2] = pNode1;
			}

			// System.out.println("2222222222node1:"+node1+",pNode1:"+pNode1+",node2:"+node2+",pNode2:"+pNode2);
			return true;
		}
	}
     
     public int[] findRedundantConnectionUsUF(int[][] edges) {
         if(edges == null || edges.length == 0) {
             return new int[]{};
         }
         
         UnionFinder uf = new UnionFinder(edges.length+1);
         for(int[] edge : edges) {
             if(!uf.union(edge[0], edge[1])) {
                 return edge;
             }
         }
         
         return new int[]{};
     }
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////
//https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
class MostStonesRemovedwitSameRoworColumnSolution {
    int[][] direct = {{0,1}, {0,-1}, {-1,0},{1,0}};
   
    void dfs(int[][] graph, int ox, int oy) {
        graph[ox][oy] = 2;
        for(int[] oneDirect : direct) {
            int x = ox+oneDirect[0];
            int y = oy+oneDirect[1];
            boolean flg = false;
            while(x>=0&&x<graph.length && y>=0&&y<graph[0].length) {
                if(graph[x][y] == 1) {
                    flg = true;
                    break;
                }
                
                x = x+oneDirect[0];
                y = y+oneDirect[1];
                
            }
            
            if(flg) {
                dfs(graph, x, y);
            }
        }
    }
    
        
    public int removeStonesUsDFS(int[][] stones) {
        if(stones == null || stones.length == 0) return 0;
        
        int m = 0, n = 0;
        for(int[] oneStone : stones) {
            if(oneStone[0] > m) m = oneStone[0];
            if(oneStone[1] > n) n = oneStone[1];
        }
        
        if(m == 0 || n == 0) {
            return 0;
        }
        
        int[][] graph = new int[m+1][n+1];
        for(int[] oneStone : stones) {
            graph[oneStone[0]][oneStone[1]] = 1;
        }
        //System.out.println(Arrays.deepToString(graph));
        
        int group = 1;
        for(int[] oneStone : stones) {
            if(graph[oneStone[0]][oneStone[1]] == 1) {
                dfs(graph, oneStone[0], oneStone[1]);
                //System.out.println(Arrays.deepToString(graph));
                group++;
            }
        }
        group--;
        //System.out.println("group:"+group);
        return stones.length-group;
    }
    
    class UnionFinder {
        int[] parent = null;
        int[] rank = null;
        
        UnionFinder(int size) {
            parent = new int[size];
            rank = new int[size];
            for(int i = 0; i < parent.length; i++) {
                parent[i] = i;
            }
        }
        
        int find(int node) {
            while(parent[node] != node) {
                node = parent[node];
            }
            
            return node;
        }
        
        void union(int x, int y) {
            int ox = find(x);
            int oy = find(y);
            if(ox == oy) return;
            if(rank[ox] == rank[oy]) {
                parent[oy] = ox;
                rank[ox]++;
            }
            else if(rank[ox] < rank[oy]) {
                parent[ox] = oy;
            }
            else {
                parent[oy] = ox;
            }
        }
    } 
     
         
     public int removeStones(int[][] stones) {
         if(stones == null || stones.length == 0) return 0;
         UnionFinder uf = new UnionFinder(20000);
         for(int[] stone : stones) {
             uf.union(stone[0], stone[1]+10000);
         }
         
         HashSet<Integer> groups = new HashSet<>();
         for(int[] stone : stones) {
             groups.add(uf.find(stone[0]));
         }
         
         return stones.length-groups.size();

     } 
}

class MostStonesRemovedwitSameRoworColumnSolution1 {
    HashMap<Integer, List<Integer>> colMap = new HashMap<>();
    HashMap<Integer, List<Integer>> rowMap = new HashMap<>();
    int group = 0;
    
    void dfs(int[][] stones, int i, boolean[] visit) {
        visit[i] = true;
        List<Integer> colList = colMap.get(stones[i][1]);
        for(int neiId : colList) {
            if(!visit[neiId]) {
                dfs(stones, neiId, visit);
            }
        }
        List<Integer> rowList = rowMap.get(stones[i][0]);
        for(int neiId : rowList) {
            if(!visit[neiId]) {
                dfs(stones, neiId, visit);
            }
        }
    }
    
    void getGroupByDFS(int[][]stones) {
        boolean[] visit = new boolean[stones.length];
        for(int i = 0; i < stones.length; i++) {
            if(!visit[i]) {
                group++;
                dfs(stones, i, visit);
            }
        }
    }
    
    void bfs(int[][] stones) {
        boolean[] visit = new boolean[stones.length];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        for(int i = 0; i < stones.length; i++) {
            if(visit[i]) {
                continue;
            }
            group++;
            queue.offer(i);
            visit[i] = true;
            while(!queue.isEmpty()) {
                int curId = queue.poll();
                List<Integer> rowList = rowMap.get(stones[curId][0]);
                for(int neiId : rowList) {
                    if(!visit[neiId]) {
                        queue.add(neiId);
                        visit[neiId] = true;
                    }
                }
                List<Integer> colList = colMap.get(stones[curId][1]);
                for(int neiId : colList) {
                    if(!visit[neiId]) {
                        queue.add(neiId);
                        visit[neiId] = true;
                    }
                }
                
            }  
        } 
    }
    
    
    public int removeStones(int[][] stones) {
        for(int i = 0; i < stones.length; i++) {
            int[] stone = stones[i];
            List<Integer> rList = rowMap.get(stone[0]);
            if(rList == null) {
                List<Integer> nList = new ArrayList<Integer>();
                nList.add(i);
                rowMap.put(stone[0], nList);
            }
            else {
                rList.add(i);
            }
            
            List<Integer> cList = colMap.get(stone[1]);
            if(cList == null) {
                List<Integer> nList = new ArrayList<Integer>();
                nList.add(i);
                colMap.put(stone[1], nList);
            }
            else {
                cList.add(i);
            }
        }
        
        //bfs(stones);
        getGroupByDFS(stones);
        return stones.length - group;
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    
    public static void testMaze() {
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
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////
    
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
    	WallsGatesSol.test();
    	
    }
}
