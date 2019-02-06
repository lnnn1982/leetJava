package leetCode;

import java.util.*;


class DebugTest {
	static int appearKTime(int size, int inputArray[], int k) {
		Arrays.sort(inputArray);
		System.out.println(Arrays.toString(inputArray));
		int i = 1, count = 1;
		int element = inputArray[0];
		int res = -1;
		while(i < size) {
			if(element == inputArray[i]) {
				count++;
			}
			else {
				if(count == k) {
					res = element;
				}
				
				element = inputArray[i];
				count = 1;
			}

			i++;
		}
		
		if(count == k) {
			res = element;
		}
		
		return res;
	}
	
	static public void testAppearKTime() {
		int res = appearKTime(6, new int[]{1,1,2,2,3,4}, 2);
		System.out.println("res:"+res);
		
		res = appearKTime(6, new int[]{1,2,2,3,3,1}, 2);
		System.out.println("res:"+res);
	}
	
	static int countElement(int arr[], int n) {
		int count = 0, len = arr.length;
		int doubleN = 2*n;
		for(int i = 0; i < arr.length; ) {
			if(arr[i++] > doubleN) {
				count+=1;
			}
		}
		
		return count;
	}
	
	static void testCountElement() {
		int ret = countElement(new int[] {1, 2, 3, 4, 5, 5}, 2);
		System.out.println("ret:"+ret);
	}
	
	public static int distinctElementsCount(int size, int[] elements) {
		int[] counted = new int[size];
		int count, flag;
		counted[0] = elements[0];
		count = 1;
		for(int i=0; i < size; i++) {
			flag = 0;
			for(int j = 0; j < count; j++) {
				if(elements[i] == counted[j]) {
					flag = 1;
				}
			}
			
			if(flag == 0) {
				count++;
				counted[count-1] = elements[i];
			}
		}
		
		return count;	
	}
	
	public static void testDistinctElements() {
		System.out.println(distinctElementsCount(5, new int[] {1,1,3,4,5}));
	}
	
	static String eliminateVowel(String str) {
		String newString = "";
		int i = 0;
		char[] S = str.toCharArray();
		int len = str.length();
		if (len == 0) {
			return "";
		}
		while (i < S.length) {
			switch (S[i]) {
			default:
				newString += S[i];
				i++;
				break;
			case 'a':
				i++;
				break;
			case 'e':
				i++;
				break;
			case 'i':
				i++;
				break;
			case 'o':
				i++;
				break;
			case 'u':
				i++;
				break;
			case 'A':
				i++;
				break;
			case 'E':
				i++;
				break;
			case 'U':
				i++;
				break;
			case 'I':
				i++;
				break;
			case 'O':
				i++;
				break;

			}
		}

		return newString;
	}
	
	static void testeliminateVowel() {
		System.out.println(eliminateVowel("abcde"));
	}
	
	static int checkArmstrong(int num) {
		int digitCount = 0, result = 0;
		// calculate number of digits
		int temp = num;
		while (temp != 0) {

			temp = temp / 10;
			digitCount++;
		}

		System.out.println("digitCount:"+digitCount);
		// sum digits to nth power
		temp = num;
		while (temp != 0) {
			int remainder = temp % 10;
			result += Math.pow(remainder, digitCount);
			temp /= 10;
		}
		if (result == num)
			return 1;
		else
			return 0;
	}
	
    static void testArmStrong() {
    	System.out.println(checkArmstrong(153));
    }
	
    static int medianValue(int size, int[] arr1, int[] arr2) {
    	int[] arr = new int[2*size];
    	for(int i = 0; i < 2*size; i++) {
    		if(i<size) arr[i] = arr1[i];
    		else arr[i] = arr2[i-size];
    	}
    	
    	Arrays.sort(arr);
    	int length = 2*size;
    	int medi = (arr[length/2-1]+arr[length/2])/2;
    	return medi;
    }
    
    static void testMedianValue() {
    	System.out.println(medianValue(4, new int[]{1,2,3,4}, new int[] {1,2,3,4}));
    }
	
    private static final Set<Character> VOWELS = new HashSet<>();
    static {
        VOWELS.add('a'); VOWELS.add('A'); VOWELS.add('e'); VOWELS.add('E');
        VOWELS.add('i'); VOWELS.add('I'); VOWELS.add('o'); VOWELS.add('O');
        VOWELS.add('u'); VOWELS.add('U');
    }
    
    
    static int vowelsString(String inputstr) {
    	int i = 0, vcount = 0, len = inputstr.length();
    	while(i<len) {
    		if(VOWELS.contains(inputstr.charAt(i))) {
    			vcount += 1;
    		}
    		i++;
    	}
    	
    	if(vcount>(len/2)) return 1;
    	else return 0;
    }
    
    static void testVowelsString() {
    	System.out.println(vowelsString("abcaaa"));
    	System.out.println(vowelsString("abc"));
    }
    
    static int sumDistinct (int size, int[] inputArray)
    {
        Arrays.sort(inputArray);
        int sum =inputArray[0];
        int point = inputArray[0];
        for(int i = 1 ; i < size ; i++)
        {
            if(point != inputArray[i])
            {
                sum+=inputArray[i];
                point = inputArray[i];
            }
                 
        }
        return sum;
    }
    
    static void testSumDistinct() {
    	System.out.println(sumDistinct(4, new int[] {9,2,2,3}));
    }
    
    static int[] sortArray(int arr[]) {
    	int len = arr.length;
    	int i, j, temp;
    	for(i = 0; i <= len-1; i++) {
    		for(j = i; j < len; j++) {
    			temp = 0;
    			if(arr[i] < arr[j]) {
    				//System.out.println("i:"+i+", j:"+j+", a:"+ arr[i]+", a:"+arr[j]);
    				temp = arr[i];
    				arr[i] = arr[j];
    				arr[j] = temp;
    			}
    		}
    	}
    	return arr;
    }
    
    static void testSortArray() {
    	System.out.println(Arrays.toString(sortArray(new int[] {1, 2, 3, 4, 5})));
    }
    
    
    
	static public void test() {
		//testAppearKTime();
		//testCountElement();
		//testDistinctElements();
		//testeliminateVowel();
		//testArmStrong();
		//testMedianValue();
		//testVowelsString();
		//testSumDistinct();
		testSortArray();
	}
}

////////////////////////////////////////////////////////////////////////////////////////////
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
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////	
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
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////	
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
	    	int ret = bstDistance(new int[] {3,6, 2, 7, 9}, 2, 2);
	    	System.out.println(ret);
	    	
	    	
	    }
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static List<List<Integer>> getBestAirPlanMiles(int maximumMiles, List<List<Integer>> forwardRouts, 
			List<List<Integer>> returnRouts) 
	{
	    forwardRouts.sort(new Comparator<List<Integer>>(){
	        public int compare(List<Integer> a, List<Integer> b) {
	            return a.get(1)-b.get(1);
	        }
	    });

	    returnRouts.sort(new Comparator<List<Integer>>(){
	        public int compare(List<Integer> a, List<Integer> b) {
	            return b.get(1)-a.get(1);
	        }
	    });

	    int closestMile = Integer.MIN_VALUE;
	    int i = 0;
	    int j = 0;
	    List<List<Integer>> rst = new ArrayList<>();
	    while(i < forwardRouts.size() && j < returnRouts.size()) {
	        int curForMile = forwardRouts.get(i).get(1);
	        int returnMile = returnRouts.get(j).get(1);

	        if(curForMile+returnMile > maximumMiles) {
	            j++;
	            continue;
	        }

	        if(curForMile+returnMile > closestMile) {
	            closestMile = curForMile+returnMile;
	            rst.clear();
	            List<Integer> oneRst = new ArrayList<>();
	            oneRst.add(forwardRouts.get(i).get(0));
	            oneRst.add(returnRouts.get(j).get(0));
	            rst.add(oneRst);
	        }
	        else if(curForMile+returnMile == closestMile) {
	            List<Integer> oneRst = new ArrayList<>();
	            oneRst.add(forwardRouts.get(i).get(0));
	            oneRst.add(returnRouts.get(j).get(0));
	            rst.add(oneRst);
	        }
	    
	        i++;
	    }

	    return rst;
	}
	
	public static void testAirPlanMile() {
		List<List<Integer>> forwardRouts = new ArrayList<>();
		forwardRouts.add(Arrays.asList(new Integer[] {1, 3000}));
		forwardRouts.add(Arrays.asList(new Integer[] {2, 5000}));
		forwardRouts.add(Arrays.asList(new Integer[] {3, 7000}));
		forwardRouts.add(Arrays.asList(new Integer[] {4, 10000}));
		
		List<List<Integer>> returnRouts = new ArrayList<>();
		returnRouts.add(Arrays.asList(new Integer[] {1, 2000}));
		returnRouts.add(Arrays.asList(new Integer[] {2, 3000}));
		returnRouts.add(Arrays.asList(new Integer[] {3, 4000}));
		returnRouts.add(Arrays.asList(new Integer[] {4, 5000}));		
		
		List<List<Integer>> rst = getBestAirPlanMiles(10000, forwardRouts, returnRouts);
		System.out.println("rst:"+rst);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public static void main(String[] args) {
		//testGetMostFrequentWords();
		//testSortLog();
		//BSTDistance.testBTS();
		//testAirPlanMile();
		
		DebugTest.test();
		
	
	}
	
	
	
	
	 
}
