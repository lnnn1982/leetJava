package leetCode;

import java.util.*;

public class AmazonTest {
    static List<String> getMostFrequentWords(String sentence, List<String> excludeList) {
        List<String> rst = new ArrayList<>();
        
        StringBuilder newSenB = new StringBuilder();
        StringBuilder newSenBLower = new StringBuilder();
        for(int i = 0; i < sentence.length(); i++) {
            if((sentence.charAt(i) >= 'A' && sentence.charAt(i) <= 'Z') || 
                (sentence.charAt(i) >= 'a' && sentence.charAt(i) <= 'z')) {
                newSenB.append(sentence.charAt(i));
                newSenBLower.append(Character.toLowerCase(sentence.charAt(i)));
            }
            else {
                newSenB.append(' ');
                newSenBLower.append(' ');
            }
        }
        
        System.out.println("newSenBLower:" + newSenBLower.toString());
        String[] words = newSenBLower.toString().split(" +");
        System.out.println("words:" + Arrays.toString(words));
        HashMap<String, Integer> wordFreq = new HashMap<>();
        int maxFreq = 0;
        for(String word : words) {
            if(findWordInList(excludeList, word) || word.equals(" ")) {
                continue;
            }

            Integer freq = wordFreq.get(word);
            if(freq == null) {
                wordFreq.put(word, 1);
                if(rst.isEmpty()) {
                    rst.add(word);
                    maxFreq = 1;
                }
            }
            else {
                int newFreq = freq+1;
                wordFreq.put(word, newFreq);
                if(newFreq > maxFreq) {
                    rst.clear();
                    rst.add(word);
                    maxFreq = newFreq;
                }
                else if(newFreq == maxFreq) {
                    rst.add(word);
                }
            }
        }
        
        String[] orgWords = newSenB.toString().split(" +");
        HashMap<String,String> wordMap = new HashMap<>();
        for(String orgWord:orgWords) {
        	if(!wordMap.containsKey(orgWord.toLowerCase())) {
        		wordMap.put(orgWord.toLowerCase(), orgWord);
        	}
        }
        
        List<String> newRst = new ArrayList<>();
        for(String oneRst : rst) {
        	newRst.add(wordMap.get(oneRst));
        }
        
        return newRst;
    }

    static boolean findWordInList(List<String> excludeList, String word) {
        for(String exl : excludeList) {
            if(exl.equalsIgnoreCase(word)) {
                return true;
            }
        }

        return false;
    }
	
	
	public static void testGetMostFrequentWords() {
		String sentence = "I am Jack and my father is Jimmy. I like wearing jack and Jone's.";
		List<String> excludeList = new ArrayList<>();
		excludeList.add("I");
		excludeList.add("and");
		excludeList.add("father");
		List<String> rst = getMostFrequentWords(sentence, excludeList);
		System.out.println("rst:" + rst);
	}
	
	
	static class LogInfo {
		String id;
		String content;
	}
	
	public static List<String> sortLog(List<String> logLines) {
		ArrayList<LogInfo> logInfoList = new ArrayList<>();
		for(String line : logLines) {
			int spaceIn = line.indexOf(' ');
			String id = line.substring(0, spaceIn);
			String content = line.substring(spaceIn+1);
			
			LogInfo log = new LogInfo();
			log.id = id;
			log.content = content;
			logInfoList.add(log);
		}
		
		Collections.sort(logInfoList, new Comparator<LogInfo>() {
			public int compare(LogInfo a, LogInfo b) {
				if(a.content.equals(b.content)) {
					return a.id.compareTo(b.id);
				}
				
				if(Character.isDigit(a.content.charAt(0)) && Character.isDigit(b.content.charAt(0))) {
					return 0;
				}
				
				if((!Character.isDigit(a.content.charAt(0))) && (!Character.isDigit(b.content.charAt(0)))) {
					return a.content.compareTo(b.content);
				}
				
				if(Character.isDigit(a.content.charAt(0))) {
					System.out.println("a:"+a.content+", b:"+b.content+ " 1");
					return 1;
				}
				else {
					System.out.println("a:"+a.content+", b:"+b.content+ " -1");
					return -1;
				}
			}
		});
		
		List<String> ret = new ArrayList<>();
		for(LogInfo log : logInfoList) {
			ret.add(log.id + " " + log.content);
		}
		
		return ret;
	}
	
	public static void testSortLog() {
		ArrayList<String> testLog = new ArrayList<String>();
		testLog.add("fhie 1df8 sfds");
		testLog.add("fdsf 2def sees");
		testLog.add("efe2 br9o fjsd");
		testLog.add("asd1 awer jik9");
		
		List<String> ret = sortLog(testLog);
		System.out.println(ret);

	}
	
	
	static class TreeNode {
	    TreeNode left;
	    TreeNode right;
	    int val;

	    TreeNode(int val) {
	        this.val = val;
	    }
	}

	static class BSTDistance {
		static TreeNode root = null;

	    static int node1Level = Integer.MIN_VALUE;
	    static int node2Level = Integer.MIN_VALUE;

	    static HashMap<TreeNode, Integer> nodeLevelMap = new HashMap<>();

	    static int bstDistance(int[] values, int node1, int node2) {
	        for(int val : values) {
	            constructTree(val);
	        }

	        traverseTree(root);

	        return calDistance(node1, node2);
	    }

	    static void constructTree(int val) {
	        TreeNode newNode = new TreeNode(val);
	        if(root == null) {
	            root = newNode;
	            return;
	        }

	        TreeNode curNode = root;
	        TreeNode prevNode = curNode;
	        while(curNode != null) {
	            prevNode = curNode;
	            if(val <= curNode.val) {
	                curNode = curNode.left;
	            }
	            else {
	                curNode = curNode.right;
	            }
	        }

	        if(val <= prevNode.val) {
	            prevNode.left = newNode;
	        }
	        else {
	            prevNode.right = newNode;
	        }
	    }

	    static void traverseTree(TreeNode node) {
	        if(node == null) return;
	        traverseTree(node.left);
	        System.out.println("node:"+node.val);
	        traverseTree(node.right);
	    }

	    static int calDistance(int node1, int node2) {
	        dfs(root, 0, node1, node2);

	        if(node1Level == Integer.MIN_VALUE || node2Level == Integer.MIN_VALUE) {
	            return -1;
	        }

	        TreeNode lca = getLAC(node1, node2);
	        System.out.println("lca:"+lca.val);

	        return node1Level+node2Level-2*nodeLevelMap.get(lca);

	    }

	    static void dfs(TreeNode node, int level, int node1, int node2) {
	        if(node == null) {
	            return;
	        }

	        if(node.val == node1 && node1Level == Integer.MIN_VALUE) {
	            node1Level = level;
	        }

	        if(node.val == node2 && node2Level == Integer.MIN_VALUE) {
	            node2Level = level;
	        }
	        
	        System.out.println("node:"+node.val+", level:"+level);

	        nodeLevelMap.put(node, level);
	        dfs(node.left, level+1, node1, node2);
	        dfs(node.right, level+1, node1, node2);
	    }

	    static TreeNode getLAC(int node1, int node2) {
	        TreeNode curNode = root;
	        while(curNode != null) {
	            if(curNode.val == node1 || curNode.val == node2) {
	                return curNode;
	            }

	            if(node1 > curNode.val && node2 > curNode.val) {
	                curNode = curNode.right;
	            }
	            else if(node1 <= curNode.val && node2 <= curNode.val) {
	                curNode = curNode.left;
	            }
	            else {
	                return curNode;
	            }
	        }

	        return curNode;
	    }
	    
	    static void testBTS() {
	    	int ret = bstDistance(new int[] {3,6, 2, 7, 9}, 2, 3);
	    	System.out.println(ret);
	    	
	    	
	    }
	}
	
	
	
	public static void main(String[] args) {
		//testGetMostFrequentWords();
		//testSortLog();
		BSTDistance.testBTS();
	
	
	}
	
	
	
	
	 
}
