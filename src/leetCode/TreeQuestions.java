package leetCode;

import java.util.*;


/**
Definition for a binary tree node.*/
class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;

	TreeNode(int x) {
		val = x;
	}
}

//https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/solution/
//difficult to implement. Just use one recursive traverse. During the recursive, call find subtree function.
class DistanceKSampleSolution {
    List<Integer> ans;
    TreeNode target;
    int K;
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        ans = new LinkedList();
        this.target = target;
        this.K = K;
        dfs(root);
        return ans;
    }

    // Return distance from node to target if exists, else -1
    public int dfs(TreeNode node) {
        if (node == null)
            return -1;
        else if (node == target) {
        	//this is for the target is the top node. From the target to its subtree
            subtree_add(node, 0);
            //return 1 to get distance from its parent
            return 1;
        }

        else {
            int L = dfs(node.left), R = dfs(node.right);
            if (L != -1) {
            	//this is when target is in the left tree, than go through right subtree tree. 
            	//When L >= K, no need to go through the right subtree. The distance is already greater than k.
                if (L == K) ans.add(node.val);
                subtree_add(node.right, L + 1);
                return L + 1;
            } else if (R != -1) {
                if (R == K) ans.add(node.val);
                subtree_add(node.left, R + 1);
                return R + 1;
            } else {
                return -1;
            }
        }
    }

    // Add all nodes 'K - dist' from the node to answer.
    public void subtree_add(TreeNode node, int dist) {
        if (node == null) return;
        if (dist == K)
            ans.add(node.val);
        else {
            subtree_add(node.left, dist + 1);
            subtree_add(node.right, dist + 1);
        }
    }
}
///////////////////////////////////////////////////////////////////////////////////////////////

class BSTDistanceSolution {
	Map<TreeNode, TreeNode> nodeParantMap = new HashMap<>();
	Map<TreeNode, Integer> nodeDepthMap = new HashMap<>();
	TreeNode lca = null;
	
	int getDistance(TreeNode root, TreeNode n1, TreeNode n2 ) {
		dfs(root, 0, null);
		//dfsForBST(root, 0, null, n1, n2);
		if(!nodeDepthMap.containsKey(n1) || !nodeDepthMap.containsKey(n2)) {
			return -1;
		}
		
		int dep1 = nodeDepthMap.get(n1);
		int dep2 = nodeDepthMap.get(n2);
		return dep1+dep2-2*nodeDepthMap.get(getLCA(n1, n2));
		//return dep1+dep2-2*nodeDepthMap.get(lca);
	}
	
	//not for bst, for general binary queue
	void dfs(TreeNode root, int depth, TreeNode prevNode) {
		if(root == null) return;
		nodeParantMap.put(root,  prevNode);
		nodeDepthMap.put(root, depth);
		
		dfs(root.left, depth+1, root);
		dfs(root.right, depth+1, root);
	}
	
	//not for bst, for general binary queue
	TreeNode getLCA(TreeNode n1, TreeNode n2) {
		int dep1 = nodeDepthMap.get(n1);
		int dep2 = nodeDepthMap.get(n2);
		int diff = Math.abs(dep1-dep2);
		TreeNode slowNode = dep1 > dep2 ? n1:n2;
		TreeNode fastNode = dep1 > dep2 ? n2:n1;
		
		for(int i = 0; i < diff; i++) {
			slowNode = nodeParantMap.get(slowNode);
		}

		while(fastNode != slowNode) {
			slowNode = nodeParantMap.get(slowNode);
			fastNode = nodeParantMap.get(fastNode);
		}
		
		return slowNode;
		
	}
	
	
//	void dfsForBST(TreeNode root, int depth, TreeNode prevNode, TreeNode n1, TreeNode n2) {
//		if(root == null) return;
//		nodeParantMap.put(root,  prevNode);
//		nodeDepthMap.put(root, depth);
//		
//		if(lca == null) {
//			if((root == n1 || root == n2)) {
//				lca = root;
//			}
//			
//			if((n1.val > root.val && n2.val < root.val) || (n2.val > root.val && n1.val < root.val)) {
//				lca = root;
//			}
//		}
//		
//		dfs(root.left, depth+1, root);
//		dfs(root.right, depth+1, root);
//	}
}
//////////////////////////////////////////////////////////////////////////////////////////////////////

//https://leetcode.com/problems/delete-node-in-a-bst/
class DeleteNodeInBtsSol {
    public TreeNode findLeftMostNode(TreeNode node) {
        if(node == null) return null;
        
        while(node.left != null) {
            node = node.left;
        }
        
        return node;
    }
    
    public TreeNode deleteRoot(TreeNode rootNode) {
        TreeNode newRootNode = null;
        if(rootNode.left != null && rootNode.right != null) {
            newRootNode = rootNode.right;
            TreeNode leftNode = findLeftMostNode(rootNode.right);
            leftNode.left = rootNode.left;
        }
        else {
            newRootNode = rootNode.left == null ? rootNode.right : rootNode.left;
        }
        
        return newRootNode;
    }
    
    public void deleteNode(TreeNode prevNode, TreeNode deleteNode) {  
        if(deleteNode.left != null && deleteNode.right != null ) {
            if(prevNode.left == deleteNode) prevNode.left = deleteNode.right;
            else prevNode.right = deleteNode.right;
            TreeNode leftNode = findLeftMostNode(deleteNode.right);
            leftNode.left = deleteNode.left;
        }
        else {
            if(prevNode.left == deleteNode) {
                prevNode.left = deleteNode.left == null ? deleteNode.right : deleteNode.left;
            }
            else prevNode.right = deleteNode.left == null ? deleteNode.right : deleteNode.left;
        }
    }
    
    
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode prevNode = root;
        TreeNode curNode = root;
        while(curNode != null) {
            if(curNode.val == key) {
                if(curNode == root) {
                    return deleteRoot(root);
                }
                else {
                    deleteNode(prevNode, curNode);
                    return root;
                }
            }
            
            prevNode = curNode;
            if(curNode.left != null && key < curNode.val) {
                curNode = curNode.left;
            }
            else if(curNode.right != null && key > curNode.val)
            {
                curNode = curNode.right;
            } 
            else {
                return root;
            }
        }
        
        return root;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////


//https://leetcode.com/problems/binary-tree-maximum-path-sum/submissions/
class BinaryTreeMaximumPathSumSol {
    int maxNum = Integer.MIN_VALUE;
    
    
    public int getMaxValue(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        int leftValue = getMaxValue(root.left);
        int rightValue = getMaxValue(root.right);
        int maxRetValue = Math.max(leftValue+root.val, rightValue+root.val);
        maxRetValue = Math.max(root.val, maxRetValue);
        
        int maxValue = Math.max(root.val+leftValue+rightValue, maxRetValue);
        if(maxValue > maxNum) maxNum = maxValue;
        return maxRetValue;
    }
    public int maxPathSum(TreeNode root) {
        getMaxValue(root);
        return maxNum;
     
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////

//https://leetcode.com/problems/binary-tree-preorder-traversal/submissions/
class PreorderTraversalSolution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        
        if(root == null) return output;
        
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode curNode = stack.pop();
            output.add(curNode.val);
            
            if(curNode.right != null) {
                stack.push(curNode.right);
            }
            
            if(curNode.left != null) {
                stack.push(curNode.left);
            }
        }
        
        return output;
    }
}

class BinaryTreeInorderTraversalSolution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        
        if(root == null) return output;
        TreeNode curNode = root;
        
        while(!stack.isEmpty() || curNode != null) {
            while(curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
            
            curNode = stack.pop();
            output.add(curNode.val);
            curNode = curNode.right;
        }
        
        return output; 
        
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////

public class TreeQuestions {
	int diameter = 0;

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
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////
    
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////
    
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////
    
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
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////   
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

//////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int minDepth(TreeNode root) {
        if(root == null) return 0;
        
        LinkedList<TreeNode> curLevelNodeQueue = new LinkedList<>();
        LinkedList<TreeNode> newLevelNodeQueue = new LinkedList<>();
        curLevelNodeQueue.add(root);
        
        int depth = 1;
        while(!curLevelNodeQueue.isEmpty()) {
            TreeNode curNode = curLevelNodeQueue.poll();
            //System.out.println("curNode:" + curNode.val);
            if(curNode.left == null && curNode.right == null) {
                return depth;
            }
            
            if(curNode.left != null) {
                newLevelNodeQueue.add(curNode.left);
            }
                
            if(curNode.right != null) {
                newLevelNodeQueue.add(curNode.right);
            }
            
            if(curLevelNodeQueue.isEmpty()) {
                depth++;
                /*LinkedList<TreeNode> tmpNodeQueue = null;
                tmpNodeQueue = curLevelNodeQueue;
                curLevelNodeQueue = newLevelNodeQueue;
                newLevelNodeQueue = tmpNodeQueue;
                newLevelNodeQueue.clear();*/
                curLevelNodeQueue = (LinkedList<TreeNode>)newLevelNodeQueue.clone();
                newLevelNodeQueue.clear();
            }
        }

        return depth;
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////    
    List<List<Integer>> sumList = new ArrayList<>();
    int sumValue = 0;
    
    public void pathSum(TreeNode root, ArrayList<Integer>prevSumList) {
        if(root == null) {
            return;
        }
        
        ArrayList<Integer> curSumList =  (ArrayList<Integer>)(prevSumList.clone());
        curSumList.add(root.val);
        if(root.left == null && root.right == null) {
            int tmpSum = 0;
            for(Integer oneVal : curSumList) {
                tmpSum += oneVal;
            }
            
            if(tmpSum == sumValue) {
                sumList.add(curSumList);
            }
            
            return;
        }
        
        pathSum(root.left, curSumList);
        pathSum(root.right, curSumList);
        
    }
    
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if(root == null) return sumList;
        sumValue = sum;
        
        ArrayList<Integer>prevSumList = new ArrayList<>();
        pathSum(root, prevSumList);
        return sumList;   
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////    
    static public TreeNode makeTree(Integer[] values) {
        int len = values.length;
        if(len == 0) {
            return null;
        }
        
        TreeNode[] nodeTree = new TreeNode[len];

        for(int i = 0; i < len; i++) {
            if(values[i] != null) {
                TreeNode node = new TreeNode(values[i]);
                node.left = null;
                node.right = null;
                nodeTree[i] = node;
            }
        }

        for(int i = 0; i < len; i++) {
            if(nodeTree[i] == null) {
                continue;
            }

            int left = 2*i+1;
            int right = left+1;
            if(left < len) {
                nodeTree[i].left = nodeTree[left];
            }

            if(right < len) {
                nodeTree[i].right = nodeTree[right];
            }

         }

         return nodeTree[0];
    }

    
    static void traverseTree(TreeNode root)
    {
        if(root == null) {
            return;
        }

        System.out.println(root.val);
        traverseTree(root.left);
        traverseTree(root.right);
    }
    
    static TreeNode getNode(TreeNode root, int n) {
    	if(root == null) return null;
    	if(root.val == n) return root;
    	TreeNode left = getNode(root.left, n);
    	if(left != null) return left;
    	TreeNode right = getNode(root.right, n);
    	if(right != null) return right;
    	return null;
    }
 
//////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    static public void main(String[] args) {
    	//TreeNode root = makeTree(new Integer[]{0, 1, 2, 3, 4, 5});
    	//traverseTree(root);
    	
    	//TreeNode root = makeTree(new Integer[]{0, 1, null, 3, 4, null, null});
    	//traverseTree(root);
    	
    	TreeNode root = makeTree(new Integer[]{6,2,8,0,4,7,9,null,null,3,5});
    	traverseTree(root);
    	
    	BSTDistanceSolution distanceSolution = new BSTDistanceSolution();
    	int dis = distanceSolution.getDistance(root, getNode(root,4), getNode(root,7));
    	System.out.println("dis:" + dis);
    	
    	
    }
    
    
}
