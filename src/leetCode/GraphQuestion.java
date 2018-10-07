package leetCode;
import java.util.*;

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
