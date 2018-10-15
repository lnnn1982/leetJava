package leetCode;
import java.util.*;

public class TreeQuestions {
	int diameter = 0;
	/**
	 Definition for a binary tree node.*/
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}
	
	
    public boolean matchTree(TreeNode t1, TreeNode t2) {
        if(t1 == null && t2 == null) {
            return true;
        }
        
        if(t1 == null || t2 == null) {
            return false;
        }
        
        if(t1.val != t2.val) {
            return false;
        }
        
        return matchTree(t1.left, t2.left) && matchTree(t1.right, t2.right);
    }
    
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(t == null) {
            return true;
        }
        
        if(s == null) {
            return false;
        }
        
        if(s.val == t.val && matchTree(s, t)) {
            return true;
        }
        
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }
	
	
    public void binaryTreePaths(TreeNode node, List<String> paths, String prevPath) {
        if(node == null) return;
        
        String newPath = null;
        if(prevPath.isEmpty()) {
            newPath = String.valueOf(node.val);
        }
        else {
            newPath = prevPath + "->" + String.valueOf(node.val);
        }
        
        if(node.left == null && node.right == null) {
            paths.add(newPath);
            return;
        }
        
        binaryTreePaths(node.left, paths, newPath);
        binaryTreePaths(node.right, paths, newPath);
    }
    
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> list = new ArrayList<String>();
        binaryTreePaths(root, list, "");
        return list;
        
    }
	
    public boolean hasPathSum(TreeNode root, int sum, int prevSum) {
        if(root == null) return false;
        
        int curSum = prevSum+root.val;
        if(root.left == null && root.right == null) {
            if(curSum == sum) {
                return true;
            }
        }
        
        return hasPathSum(root.left, sum, curSum) || hasPathSum(root.right, sum, curSum);
    }
    
    
    public boolean hasPathSum(TreeNode root, int sum) {
        return hasPathSum(root, sum, 0);
    }
	
    public int diameterOfBinaryTreeHelper(TreeNode root) {
        if (root == null) {return 0;}
        
        int leftPath = diameterOfBinaryTreeHelper(root.left);
        int rightPath = diameterOfBinaryTreeHelper(root.right);
        
        int curPath =  leftPath + rightPath;
        diameter = Math.max(curPath, diameter);
        
        return Math.max(leftPath, rightPath) + 1;
    }
    
    public int diameterOfBinaryTree(TreeNode root) {
    	diameterOfBinaryTreeHelper(root);
        return diameter;
    }
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || p == null || q == null) {
            return null;
        }

        TreeNode curNode = root;
        while(curNode != null) {
            if(curNode.val == p.val || curNode.val == q.val) { //p and q are different and both values will exist in the BST.
                return curNode;
            }
            
            //the curNode is not p or q
            if(curNode.val > p.val && curNode.val > q.val) {
                curNode = curNode.left;
            }
            else if (curNode.val < p.val && curNode.val < q.val) {
                curNode = curNode.right;
            }
            else {
                return curNode;
            }
        }
        
        return curNode;
    }
	
}
