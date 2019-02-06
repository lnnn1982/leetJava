package leetCode;
import java.util.*;

public class AmazonTest1 {
    static List<String> getMostFrequentWords(String sentence, List<String> excludeWords) {
        StringBuilder newBuilder = new StringBuilder();
        for(int i = 0; i < sentence.length(); i++) {
            if((sentence.charAt(i) >= 'a' && sentence.charAt(i) <= 'z') 
                || (sentence.charAt(i) >= 'A' && sentence.charAt(i) <= 'Z')) {
                newBuilder.append(sentence.charAt(i));
            }
            else {
                newBuilder.append(' ');
            }
        }

        String[] newStrList = newBuilder.toString().split(" +");
        HashMap<String, Integer> strFreMap = new HashMap<>();
        List<String> rst = new ArrayList<>();
        int maxFreq = 0;

        for(String oneStr : newStrList) {
            if(isExcludeStr(excludeWords, oneStr)) {
                continue;
            }
            
            Integer freq = strFreMap.get(oneStr);
            if(freq == null) {
            	strFreMap.put(oneStr, 1);
            }
            else {
            	strFreMap.put(oneStr, freq+1);
            }

            freq = strFreMap.get(oneStr);
            if(freq == maxFreq) {
                rst.add(oneStr);
            }
            else if(freq > maxFreq) {
                maxFreq = freq;
                rst = new ArrayList<>();
                rst.add(oneStr);
            }
        }
        
        //System.out.println("1111111111," + rst);
        return rst;
    }
    
    static boolean isExcludeStr(List<String> excList, String str) {
        for(String oneStr : excList) {
            if(str.equals(oneStr)) {
                return true;
            }
        }

        return false;
    }
	
	static void testGetMostFrequentWords() {
		List<String> excList = new ArrayList<>();
		excList.add("and");
		excList.add("he");
		excList.add("the");
		excList.add("to");
		excList.add("is");
		System.out.println(getMostFrequentWords("jack and jill went to the market to buy bread and cheese cheese is jack favorite food",
				excList));
	}
	
	static List<String> getMostFrequcyKWords(String sentence, List<String> excludeWords, int k) {
	    String[] wordList = sentence.split(" +");
	    HashSet<String> excSet = new HashSet<>(excludeWords);
	    HashMap<String, Integer> wordFreqMap = new HashMap<>();
	    for(String oneW : wordList) {
	        if(excSet.contains(oneW)) {
	            continue;
	        }

	        Integer freq = wordFreqMap.get(oneW);
	        if(freq == null) {
	            wordFreqMap.put(oneW,1);
	        }
	        else {
	            wordFreqMap.put(oneW,freq+1);
	        }
	    }

	    PriorityQueue<Map.Entry<String, Integer>> que = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>(){
	        public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
	            return b.getValue()-a.getValue();
	        }
	    });

	    for(Map.Entry<String, Integer> onePair : wordFreqMap.entrySet()) {
	        que.offer(onePair);
	    }

	    List<String> rst = new ArrayList<>();
	    for(int i = 0; i < k; i++) {
	        Map.Entry<String,Integer> onePair = que.poll();
	        if(onePair != null) {
	            rst.add(onePair.getKey());
	        }
	    }

	    return rst;
	}
	
	static void testGetMostFrequcyKWordsWords() {
		List<String> excList = new ArrayList<>();
		excList.add("and");
		excList.add("he");
		excList.add("the");
		excList.add("to");
		excList.add("is");
		System.out.println(getMostFrequcyKWords("jack and jill went to the market to buy bread and cheese cheese is jack favorite food",
				excList, 3));
	}
	
	static List<String> sortLogFile(int logFileSize, List<String> logLines) {
        ArrayList<ArrayList<String> > idContList = new ArrayList<>();
        for(int i = 0; i < logFileSize; i++) {
            String oneLine = logLines.get(i);
            int spInd = oneLine.indexOf(" ");
            String id = oneLine.substring(0, spInd);
            String content = oneLine.substring(spInd+1);
            ArrayList<String> onePair = new ArrayList<>();
            onePair.add(id);
            onePair.add(content);
            idContList.add(onePair);
        }

        idContList.sort(new Comparator<ArrayList<String>>() {
            public int compare(ArrayList<String> a, ArrayList<String> b) {
                String aId = a.get(0);
                String aCont = a.get(1);
                String bId = b.get(0);
                String bCont = b.get(1);
                boolean isANum = Character.isDigit(aCont.charAt(0));
                boolean isBNum = Character.isDigit(bCont.charAt(0));
                if(isANum && isBNum) {
                    return 0;
                }

                if(isANum && !isBNum) {
                    return 1;
                }

                if(!isANum && isBNum) {
                    return -1;
                }

                if(aCont.equals(bCont)) {
                    return aId.compareTo(bId);
                }

                return aCont.compareTo(bCont);
            }
        });
        
        List<String> rst = new ArrayList<>();
        for(ArrayList<String> onePair : idContList) {
            StringBuilder strB = new StringBuilder();
            strB.append(onePair.get(0));
            strB.append(" ");
            strB.append(onePair.get(1));
            rst.add(strB.toString());
        }

        return rst;
	}
	
	static void testSortLogFile() {
		List<String> logLines = new ArrayList<>();
		logLines.add("a1 9 2 3 1");
		logLines.add("g1 act car");
		logLines.add("g2 act car");
		logLines.add("zo4 4 7");
		logLines.add("ab1 off key dog");
		logLines.add("a8 act zoo");
		System.out.println(sortLogFile(5, logLines));
	}
	
	static List<String> countkDist(String str, int k) {
		List<String> rst = new ArrayList<>();
        for(int i = 0; i <= str.length()-k; i++) {
            HashSet<Character> strSet = new HashSet<>();
            for(int j = i; j < str.length(); j++) {
                strSet.add(str.charAt(j));
                if(strSet.size() == k) {
                	rst.add(str.substring(i, j+1));
                }
                else if(strSet.size() > k) {
                    break;
                }
            }
        }
        //System.out.println(rst);
        return rst;
	}
	
	static void testCountkDist() {
		System.out.println(countkDist("abc", 2));
		System.out.println(countkDist("aba", 2));
		System.out.println(countkDist("aa", 1));
	}
	
	static List<String> strWithNoRepeatSubstrWithLenthL(String str, int l) {
        List<String> rst = new ArrayList<>();
        for(int i = 0; i <= str.length()-l; i++) {
            HashSet<Character> strSet = new HashSet<>();
            StringBuilder oneStrB = new StringBuilder();
            int j = i;
            for(; j < i+l; j++) {
                if(strSet.contains(str.charAt(j))) {
                    break;
                }
                strSet.add(str.charAt(j));
                oneStrB.append(str.charAt(j));
            }

            if(j == i+l) {
                rst.add(oneStrB.toString());
            }
        }
        
        return rst;
	}
	
    static List<String> strWithNoRepeatSubstrWithLenthLV1(String str, int l) {
        HashMap<Character, Integer> strMap = new HashMap<>();
        int i = 0, j = 0;
        List<String> rst = new ArrayList<>();
        while(j < str.length()) {
            if(!strMap.containsKey(str.charAt(j))) {
                strMap.put(str.charAt(j), j);
                if(strMap.size() == l) {
                    rst.add(str.substring(i, j+1));
                    strMap.remove(str.charAt(i));
                    i++;
                }
            }
            else {
                int ind = strMap.get(str.charAt(j));
                for(; i <= ind; i++) {
                    strMap.remove(str.charAt(i));
                }
                strMap.put(str.charAt(j), j);
            }
        
            j++;
        }

        return rst;
    }
	
	static void testStrWithNoRepeatSubstrWithLenthL() {
		String a = "aabcdedfgfhh";
		List<String> rst = strWithNoRepeatSubstrWithLenthL(a, 3);
		System.out.println("rst:"+rst);
		
		List<String> rst1 = strWithNoRepeatSubstrWithLenthLV1(a, 3);
		System.out.println("rst1:"+rst1);
		
		a = "abcdefg";
		rst = strWithNoRepeatSubstrWithLenthL(a, 3);
		System.out.println("rst:"+rst);
		
		rst1 = strWithNoRepeatSubstrWithLenthLV1(a, 3);
		System.out.println("rst1:"+rst1);
	}
	
    
    static class Node {
        int value;
        Node left = null;
        Node right = null;

        Node(int value) {
            this.value = value;
        }
    }

    static int btsDistance(int[] values, int node1, int node2) {
        HashSet<Integer> valueSet = new HashSet<>();
        for(int value : values) {
        	valueSet.add(value);
        }
        if(!valueSet.contains(node1) || !valueSet.contains(node2)) {
            return -1;
        }

        Node root = consTree(values);
        if(root == null) return -1;
        Node lca = getLCA(root, node1, node2);
        if(lca == null) return -1;
        System.out.println("lca:"+lca.value);

        return getDistance(lca, node1) + getDistance(lca, node2);
    }

    static Node consTree(int[] values) {
        if(values.length == 0) {
            return null;
        }

        Node root = new Node(values[0]);
        System.out.println("consTree root:"+root.value);
        for(int i = 1; i < values.length; i++) {
            Node newNode = new Node(values[i]);
            Node curNode = root;
            Node prevNode = curNode;
            while(curNode != null) {
                prevNode = curNode;
                if(newNode.value <= curNode.value) {
                    curNode = curNode.left;
                }
                else {
                    curNode = curNode.right;
                }
            }

            if(newNode.value <= prevNode.value) {
                prevNode.left = newNode;
                System.out.println("consTree prevNode:"+prevNode.value+", left:"+newNode.value);
            }
            else {
                prevNode.right = newNode;
                System.out.println("consTree prevNode:"+prevNode.value+", right:"+newNode.value);
            }
        }

        return root;
    }


    static Node getLCA(Node root, int v1, int v2) {
        Node curNode = root;
        while(curNode != null) {
            if(curNode.value == v1 || curNode.value == v2) {
                return curNode;
            }

            if(curNode.value > v1 && curNode.value > v2) {
                curNode = curNode.left;
            }
            else if(curNode.value < v1 && curNode.value < v2) {
                curNode = curNode.right;
            }
            else {
                return curNode;
            }
        }

        return null;
    }

    static int getDistance(Node lca, int chiV) {
        Node curNode = lca;
        int dis = 0;
        while(curNode != null) {
            if(curNode.value == chiV) return dis;
            else if(curNode.value < chiV) {
                curNode = curNode.right;
                dis++;
            }
            else {
                curNode = curNode.left;
                dis++;
            }

        }

        return dis;
    }
    
    static void testBTSDistance() {
    	//System.out.println(btsDistance(new int[] {1,3,-1,-2,5,9}, 1, 3));
    	//System.out.println(btsDistance(new int[] {1,3,-1,-2,5,9}, -2, 5));
    	//System.out.println(btsDistance(new int[] {1,3,-1,-2,5,9}, -7, 5));
    	System.out.println(btsDistance(new int[] {1}, 1, 1));	
    }
    
    static class TreeAmplitude {
        static int amplitude = 0;

        static void getAmplitude(Node root, int maxValue, int minValue) {
            if(root == null) {
                if(maxValue-minValue > amplitude) amplitude = maxValue-minValue;
                return;
            }

            if(root.value > maxValue) maxValue = root.value;
            if(root.value < minValue) minValue = root.value;
            getAmplitude(root.left, maxValue, minValue);
            getAmplitude(root.right, maxValue, minValue);
        }

        static int getAmplitude(Node root) {
            getAmplitude(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
            return amplitude;
        }
        
        static void testGetAmplitude() {
        	//Node root = consTree(new int[] {1,3,-1,-2,5,9});
        	Node root = consTree(new int[] {10,7,18,6,9,23,45});
        	System.out.println(getAmplitude(root));
        }
    }
    
    static class TreeMiniumPathSum {
        static int minSum = Integer.MAX_VALUE;
        static int getMiniumPathSum(Node root) {
            if(root == null) return 0;
            int leftSum = getMiniumPathSum(root.left);
            int rightSum = getMiniumPathSum(root.right);
            return Math.min(leftSum, rightSum) + root.value;
        }

        static int getMiniumPathSumPre(Node root) {
            getMiniumPathSum(root, 0);
            return minSum;
        }

        static void getMiniumPathSum(Node root, int sum) {
            if(root == null) return;
            int newSum = sum+root.value;
            if(root.left == null && root.right == null) {
                if(newSum < minSum) minSum = newSum;
                return;
            }

            getMiniumPathSum(root.left, newSum);
            getMiniumPathSum(root.right, newSum);
        }
        
        static void testgetMiniumPathSum() {
        	Node root = consTree(new int[] {1,3,-1,-2,5,9});
        	//Node root = consTree(new int[] {10,7,18,6,9,23,45});
        	System.out.println(getMiniumPathSum(root));
        	System.out.println(getMiniumPathSumPre(root));
        }
    }
    
    
    public static List<List<Integer>> getBestAirPlanMiles(int maximumMiles, 
    		List<List<Integer>> forwardRoutes, 
            List<List<Integer>> returnRoutes) 
    {
        forwardRoutes.sort(new Comparator<List<Integer>>(){
            public int compare(List<Integer> a, List<Integer> b) {
                return a.get(1)-b.get(1);
            }
        });

        returnRoutes.sort(new Comparator<List<Integer>>(){
            public int compare(List<Integer> a, List<Integer> b) {
                return b.get(1)-a.get(1);
            }
        });

        int i = 0;
        int j = 0;
        int closestMile = 0;
        List<List<Integer>> rst = new ArrayList<>();

        while(i < forwardRoutes.size() && j < returnRoutes.size()) {
        	System.out.println("i:"+i+", j:"+j);
            int forMile = forwardRoutes.get(i).get(1);
            int forInd = forwardRoutes.get(i).get(0);
            int retMile = returnRoutes.get(j).get(1);
            int retInd = returnRoutes.get(j).get(0);

            System.out.println("forMile:"+forMile+", forInd:"+forInd+", retMile:"
                +retMile+",retInd:"+retInd);
            if(forMile + retMile <= maximumMiles) {
                if(forMile + retMile > closestMile) {
                    closestMile = forMile + retMile;
                    rst.clear();

                    List<Integer> onePair = new ArrayList<>();
                    onePair.add(forInd);
                    onePair.add(retInd);
                    rst.add(onePair);
                }
                else if(forMile + retMile == closestMile) {
                    List<Integer> onePair = new ArrayList<>();
                    onePair.add(forInd);
                    onePair.add(retInd);
                    rst.add(onePair);
                }
                
                i++;
            }
            else {
                j++;
            }
        }
        return rst;
    }
    
    public static List<List<Integer>> getBestAirPlanMilesBrutal(int maximumMiles, 
            List<List<Integer>> forwardRoutes, 
            List<List<Integer>> returnRoutes) 
    {
        
        int closesMile = 0;
        List<List<Integer>> rst = new ArrayList<>();
        for(List<Integer> forwardRoute : forwardRoutes) {
            int forInd = forwardRoute.get(0);
            int forMile = forwardRoute.get(1);

            for(List<Integer> returnRoute : returnRoutes) {
                int returnId = returnRoute.get(0);
                int returnMile = returnRoute.get(1);

                if(forMile+returnMile > maximumMiles) continue;

                if(forMile+returnMile >= closesMile) {
                    if(forMile+returnMile > closesMile) {
                        closesMile = forMile+returnMile;
                        rst.clear();
                    }

                    List<Integer> indList = new ArrayList<>();
                    indList.add(forInd);
                    indList.add(returnId);
                    rst.add(indList);
                }
            }

        }

        return rst;
    }

    static void testGetBestAirPlanMiles() {
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

		System.out.println(getBestAirPlanMiles(10000, forwardRouts, returnRouts));
		System.out.println("brutal:"+getBestAirPlanMilesBrutal(10000, forwardRouts, returnRouts));
    }
    
    static public List<String> getLongestIntervalFromString(String str) {
        HashMap<Character, List<Integer>> charSegMap = new HashMap<>();
        for(int i = 0; i < str.length(); i++) {
            if(charSegMap.containsKey(str.charAt(i))) {
                List<Integer> segList = charSegMap.get(str.charAt(i));
                segList.set(1, i);
            }
            else {
                List<Integer> segList = new ArrayList<>();
                segList.add(i);
                segList.add(i);
                charSegMap.put(str.charAt(i), segList);
            }
        }
        
        System.out.println("charSegMap:"+charSegMap);

        List<List<Integer>> segList = new ArrayList<>();
        for(Map.Entry<Character, List<Integer>> entry: charSegMap.entrySet()) {
            segList.add(entry.getValue());
        }

        segList.sort(new Comparator<List<Integer>>(){
            public int compare(List<Integer> a, List<Integer> b) {
                return a.get(0)-b.get(0);
            }
        });
        
        List<String> ret = new ArrayList<>();
        if(segList.size() == 0) return ret;

        List<List<Integer>> maxIntervals = new ArrayList<>();

        List<Integer> prevInterval = segList.get(0);
        int maxInterval = Integer.MIN_VALUE;
        for(int i = 1; i < segList.size(); i++) {
            List<Integer> curInterval = segList.get(i);
            if(curInterval.get(0) > prevInterval.get(1)) {
                if(prevInterval.get(1)-prevInterval.get(0) > maxInterval) {
                    maxInterval = prevInterval.get(1)-prevInterval.get(0);
                    maxIntervals.clear();
                    maxIntervals.add(prevInterval);
                }
                else if(prevInterval.get(1)-prevInterval.get(0) == maxInterval) {
                    maxIntervals.add(prevInterval);
                }

                prevInterval = curInterval;
            }
            else {
                List<Integer> newInterval = new ArrayList<>();
                newInterval.add(prevInterval.get(0));
                newInterval.add(Math.max(prevInterval.get(1), curInterval.get(1)));
                prevInterval = newInterval;
            }
        }
        
        if(prevInterval.get(1)-prevInterval.get(0) > maxInterval) {
            maxInterval = prevInterval.get(1)-prevInterval.get(0);
            maxIntervals.clear();
            maxIntervals.add(prevInterval);
        }
        else if(prevInterval.get(1)-prevInterval.get(0) == maxInterval) {
            maxIntervals.add(prevInterval);
        }
        
        System.out.println("maxIntervals:"+maxIntervals);
        for(List<Integer> interval : maxIntervals) {
            ret.add(str.substring(interval.get(0), interval.get(1)+1));
        }

        return ret;
    }
    
    static void testGetLongestIntervalFromString() {
    	//System.out.println(getLongestIntervalFromString("abcd"));
    	//System.out.println(getLongestIntervalFromString("abcab"));
    	System.out.println(getLongestIntervalFromString("acafghbeb"));
    }
    
    static int maze(int[][] matrix) {
        int m = matrix.length;
        if(m == 0)  return 0;
        int n = matrix[0].length;
        if(n==0) return 0;

        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        //boolean[][] visit = new int[matrix.length][matrix[0].length];
        if(matrix[0][0] == 0) return 0;
        if(matrix[0][0] == 9) return 1;
        LinkedList<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0,0});
        matrix[0][0] = 2;

        while(!queue.isEmpty()) {
            int[] curPos = queue.poll();
            for(int[] oneDir : directions) {
                int row = curPos[0]+oneDir[0];
                int col = curPos[1]+oneDir[1];
                if(row>=0 && row<m && col>=0 && col<n) {
                    if(matrix[row][col] == 9) return 1;
                    if(matrix[row][col] == 1) {
                        queue.offer(new int[]{row, col});
                        matrix[row][col] = 2;
                    }
                }
            }
        }
        
        return 0;
    }
    
    public static void testMaze() {
    	int[][] matrix = {{1,1,0,0}, {0,1,0,0}, {1,1,1,9}};
    	System.out.println(maze(matrix));
    }
	
    static class MaxMinPath {
        static int maxV = Integer.MIN_VALUE;
        static int m = Integer.MIN_VALUE;
        static int n = Integer.MIN_VALUE;

        static void dfs(int[][] matrix, int[] curPos, int min) {
            if(matrix[curPos[0]][curPos[1]] < min) min = matrix[curPos[0]][curPos[1]];
            if(curPos[0] == m-1 && curPos[1] == n-1) {
                if(maxV < min) maxV = min;
                return;
            }

            if(curPos[0]+1 < m) {
                dfs(matrix, new int[]{curPos[0]+1, curPos[1]}, min);
            }

            if(curPos[1]+1 < n) {
                dfs(matrix, new int[]{curPos[0], curPos[1]+1}, min);
            }
        }

        static int getMaxMinPath(int[][] matrix) {
            m = matrix.length;
            if(m == 0) return Integer.MIN_VALUE;
            n = matrix[0].length;
            if(n == 0) return Integer.MIN_VALUE;
            
            dfs(matrix, new int[] {0,0}, matrix[0][0]);

            return maxV;
        }
        
        static void testGetMaxMinPath() {
        	int[][] matrix = {{8,4,7},{6,5,9}};
        	System.out.println(getMaxMinPath(matrix));
        }

    }
    
    static public int[] getMovieIndices(int[] movieDuration, int flighDuration) {
        int closestDuration = 0;
        int[] ret = null;

        for(int i = 0; i < movieDuration.length; i++) {
            int firDur = movieDuration[i];
            for(int j=i+1; j < movieDuration.length; j++) {
                int secondDur = movieDuration[j];
                if(firDur+secondDur > flighDuration-30) {
                    continue;
                }

                if(firDur+secondDur > closestDuration) {
                    closestDuration = firDur+secondDur;
                    ret = new int[]{i,j};
                }
                else if(firDur+secondDur == closestDuration) {
                    if(ret == null)  ret = new int[]{i,j};
                    else {
                        int lastMaxDur = Math.max(movieDuration[ret[0]], movieDuration[ret[1]]);
                        int curMaxDur = Math.max(movieDuration[i], movieDuration[j]);
                        if(lastMaxDur < curMaxDur) {
                            ret = new int[] {i, j};
                        }
                    }
                }
            }
        }
        
        return ret;
    }
    
    static public int[] getMovieIndicesTwoPointer(int[] movieDuration, int flighDuration) {
        List<List<Integer>> mList = new ArrayList<>();
        for(int i = 0; i < movieDuration.length; i++) {
            List<Integer> onePair = new ArrayList<>();
            onePair.add(i);
            onePair.add(movieDuration[i]);
            mList.add(onePair);
        }

        mList.sort((a, b)->a.get(1)-b.get(1));
        //mList.sort((a, b)->{return a.get(1)-b.get(1);});
//        mList.sort(new Comparator<List<Integer>>() {
//        	public int compare(List<Integer> a, List<Integer> b) {
//        		return a.get(1) - b.get(1);
//        	}
//        });
        int[] ret = null;
        int maxDuration = 0;
        int i =0;
        int j = mList.size()-1;
        while(i < j) {
            int firDur = mList.get(i).get(1);
            int secondDur = mList.get(j).get(1);
            int firId = mList.get(i).get(0);
            int secondId = mList.get(j).get(0);

            if(firDur+secondDur > flighDuration-30) {
                j--;
                continue;
            }

            if(firDur+secondDur > maxDuration) {
                maxDuration = firDur+secondDur;
                ret = new int[]{firId, secondId};
            }
            else if(firDur+secondDur == maxDuration) {
                int lastMaxSinDur = Math.max(movieDuration[ret[0]], movieDuration[ret[1]]);
                int curMaxSinDur = Math.max(firDur, secondDur);
                if(curMaxSinDur > lastMaxSinDur) {
                    ret = new int[]{firId, secondId};
                }
            }
            i++;
        }

        return ret;
    }
    
    static void testGetMovieIndices() {
		System.out.println("brutal force:"+Arrays.toString(getMovieIndices(new int[]{100, 200, 120, 150, 170, 90}, 300)));
		System.out.println("double pointer:"+
				Arrays.toString(getMovieIndicesTwoPointer(new int[]{100, 200, 120, 150, 170, 90}, 300)));
    }
    
    static class MostAvgTreeNode {
        static class AryTreeNode {
            int value;
            List<AryTreeNode> children = new ArrayList<>();
            AryTreeNode(int value) {
            	this.value = value;
            }
            void addChild(AryTreeNode node) {
            	children.add(node);
            }
            
        }

        static double maxAvgValue = 0;
        static AryTreeNode maxNode = null;

        static List<Integer> dfs(AryTreeNode root) {
            List<Integer> ret = new ArrayList<>();
            if(root == null) {
                ret.add(0);
                ret.add(0);
                return ret;
            }
            
            int sumGrade = 0;
            int sumChildren = 0;
            if(root.children != null) {
                for(AryTreeNode child : root.children) {
                    List<Integer> retOfOneChild = dfs(child);
                    sumGrade += retOfOneChild.get(0);
                    sumChildren = retOfOneChild.get(1);
                }
            }

            sumGrade += root.value;
            sumChildren += 1;
            double curAvgValue = sumGrade/sumChildren;
            if(Double.compare(curAvgValue, maxAvgValue) > 0) {
                maxNode = root;
            }
            
            ret.add(sumGrade);
            ret.add(sumChildren);
            return ret;
        }

        static AryTreeNode getMostAvgTreeNode(AryTreeNode root) {
            dfs(root);
            return maxNode;
        }
        
        static void testGetMostAvgTreeNode() {
        	AryTreeNode node00 = new AryTreeNode(40);
        	AryTreeNode node01 = new AryTreeNode(50);
        	AryTreeNode node02 = new AryTreeNode(60);
        	AryTreeNode node03 = new AryTreeNode(50);
        	AryTreeNode node04 = new AryTreeNode(70);
        	AryTreeNode node10 = new AryTreeNode(120);
        	AryTreeNode node11 = new AryTreeNode(80);
        	AryTreeNode node20 = new AryTreeNode(100);
        	node10.addChild(node00);
        	node10.addChild(node01);
        	node10.addChild(node02);
        	node11.addChild(node03);
        	node11.addChild(node04);
        	node20.addChild(node10);
        	node20.addChild(node11);
        	
        	System.out.println(getMostAvgTreeNode(node20).value);
        	System.out.println(getMostAvgTreeNode(node11).value);
        	System.out.println(getMostAvgTreeNode(node00).value);
        } 
    }
    
    
    
    
	public static void main(String[] args) {
		//testGetMostFrequentWords();
		//testSortLogFile();
		//testGetMostFrequcyKWordsWords();
		//testCountkDist();
		//testStrWithNoRepeatSubstrWithLenthL();
		//testBTSDistance();
		//TreeAmplitude.testGetAmplitude();
		//TreeMiniumPathSum.testgetMiniumPathSum();
		//testGetBestAirPlanMiles();
		//testGetLongestIntervalFromString();
		//testMaze();
		//MaxMinPath.testGetMaxMinPath();
		//testGetMovieIndices();
		MostAvgTreeNode.testGetMostAvgTreeNode();
		
		
	}

}

