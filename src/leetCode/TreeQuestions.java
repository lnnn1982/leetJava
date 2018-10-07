package leetCode;

public class TreeQuestions {

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
	
	
	
	
	
	
	
	
}
