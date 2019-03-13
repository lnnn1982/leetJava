package leetCode;
import java.util.*;

/////////////////////////////////////////////////////////////////////////////////////////////////////////
//https://leetcode.com/problems/course-schedule-ii/submissions/
class CourseScheduleSolution {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    Map<Integer, Integer> degree = new HashMap<>();
    
    void initGraph(int numCourses, int[][] prerequisites) {
        for(int i = 0; i < numCourses; i++) {
            List<Integer> neighbors = new ArrayList<>();
            graph.put(i, neighbors);
            degree.put(i,0);
        }
        
        for(int[] edge : prerequisites) {
            graph.get(edge[1]).add(edge[0]);
            int curD = degree.get(edge[0]);
            degree.put(edge[0], curD+1);
        }
        //System.out.println("graph:"+graph);
        //System.out.println("degree:"+degree);
    }
    
    
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        initGraph(numCourses, prerequisites);
        int[] rst = new int[numCourses];
        
        LinkedList<Integer> queue = new LinkedList<>();
        for(Map.Entry<Integer, Integer> entry : degree.entrySet()) {
            if(entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }
        
        int num = 0;
        while(!queue.isEmpty()) {
            int vertice = queue.pop();
            rst[num++] = vertice;
            
            List<Integer> neighbors = graph.get(vertice);
            for(int oneNeigh : neighbors) {
                int curD = degree.get(oneNeigh);
                degree.put(oneNeigh, curD-1);
                if(degree.get(oneNeigh) == 0) {
                    queue.add(oneNeigh);
                }
            }
        }
        
        //System.out.println("num:"+num);
        if(num != numCourses) {
            return new int[0];
        }
        
        return rst;
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//https://leetcode.com/problems/word-ladder-ii/
class WordLadderSol {
    Map<String, List<String>> graph = new HashMap<>();
    Map<String, String> father = new HashMap<>();
    List<List<String>> rst = new ArrayList<>();
    
    public void constructGraph(List<String> wList) {
        for(int i = 0; i < wList.size(); i++) {
            //System.out.println("aaaaaaaaaaaaaaaa i:"+i);
            for(int j = i+1; j < wList.size(); j++) {
                //System.out.println("bbbbbbbbbbbbbbbbbbbbbbb j:"+j);
                
                if(isRelated(wList.get(i), wList.get(j))) {
                    List<String> neighs = graph.get(wList.get(i));
                    if(neighs == null) {
                        neighs = new ArrayList<String>();
                        graph.put(wList.get(i), neighs);
                    }
                   // System.out.println("wList i:" + i + ", get:" + wList.get(i) + ", neigh:" + neighs);
                    neighs.add(wList.get(j));
                   // System.out.println("add j:" + j + ", get:" + wList.get(j) + ", neigh:" + neighs);
                    
                    neighs = graph.get(wList.get(j));
                    if(neighs == null) {
                        neighs = new ArrayList<String>();
                        graph.put(wList.get(j), neighs);
                    }
                    //System.out.println("wList j:" + j + ", get:" + wList.get(j) + ", neigh:" + neighs);
                    neighs.add(wList.get(i));
                    //System.out.println("add i:" + i + ", get:" + wList.get(i) + ", neigh:" + neighs);
                }
            }
        }
        
        System.out.println("graph:"+graph);
    }
    
    public boolean isRelated(String a, String b) {
        int diff = 0;
        for(int i = 0; i < a.length(); i++) {
            if(a.charAt(i) != b.charAt(i)) diff++;
        }
        
        return diff == 1;
    }
    
    boolean isInPrevList(List<String> prevList, String curStr) {
        for(String onePrev : prevList) {
            if(onePrev.equals(curStr)) return true;
        }
        
        return false;
    }
    
    public void dfs(String curStr, String endWord, List<String> prevList) {
        if(curStr.equals(endWord)) {
            List<String> oneRstList = (List<String>)((ArrayList<String>)prevList).clone();
            oneRstList.add(endWord);
            if(rst.isEmpty()) {
                rst.add(oneRstList);
            }
            else {
                if(oneRstList.size() < rst.get(0).size()) {
                    rst.clear();
                    rst.add(oneRstList);
                }
                else if(oneRstList.size() == rst.get(0).size()) {
                    rst.add(oneRstList);
                }
            }
        }
        
        if(graph.containsKey(curStr)) {
            List<String> neighs = graph.get(curStr);
            for(String oneNeigh : neighs) {
                if(isInPrevList(prevList, oneNeigh)) {
                    continue;
                }
                
                prevList.add(curStr);
                dfs(oneNeigh, endWord, prevList);
                prevList.remove(prevList.size()-1);
            }
        }
    }
    
    public void genRstForBFS(List<String> oneRst, String curWord, String endWord, Map<String, Set<String>> fatherMap) 
    {
    	if(curWord.equals(endWord)) {
    		ArrayList<String> newRst = new ArrayList<>(oneRst);
    		newRst.add(curWord);
    		rst.add(newRst);
    		Collections.reverse(newRst);
    		return;
    	}
    	
    	oneRst.add(curWord);
    	Set<String> fatherSet = fatherMap.get(curWord);
    	for(String oneFather : fatherSet) {
    		genRstForBFS(oneRst, oneFather, endWord, fatherMap);
    	}
    	oneRst.remove(oneRst.size()-1);
    }
    
    public void buildNeighbors(HashSet<String> wordSet, String word, Map<String, List<String>> graph) {
        char[] wordArray = word.toCharArray();
        for(char ch = 'a'; ch <= 'z'; ch++) {
            for(int i = 0; i < wordArray.length; i++) {
                if(wordArray[i] == ch) continue;
                char oldChar = wordArray[i];
                wordArray[i] = ch;
                String newStr = String.valueOf(wordArray);
                if(wordSet.contains(newStr)) {
                    List<String> neighbors = graph.get(word);
                    //if(neighbors == null) {
                        //neighbors = new ArrayList<String>();
                        //graph.put(word, neighbors);
                    //}
                    neighbors.add(newStr);
                }
                
                wordArray[i] = oldChar;
            }
        }
    }
    
    public void bfs(String beginWord, String endWord, HashSet<String> wordSet) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Set<String>> fatherMap = new HashMap<>();
        
        Set<String> levelSet = new HashSet<>();
        levelSet.add(beginWord);
        Set<String> visit = new HashSet<>();
        visit.add(beginWord);
        
        boolean isFound = false;
        while(!levelSet.isEmpty() && !isFound) {
            Set<String> newLevelSet = new HashSet<>();
            for(String oneStr : levelSet) {
                graph.put(oneStr, new ArrayList<String>());
                buildNeighbors(wordSet, oneStr, graph);
                
                List<String> neighs = graph.get(oneStr);
                //if(neighs == null) continue;
                for(String oneNei : neighs) {
                    if(!visit.contains(oneNei)) {
                        Set<String> fatherSet = new HashSet<>();
                        fatherSet.add(oneStr);
                        fatherMap.put(oneNei, fatherSet);
                        visit.add(oneNei);
                        newLevelSet.add(oneNei);
                    }
                    else {
                        if(newLevelSet.contains(oneNei)) {
                            fatherMap.get(oneNei).add(oneStr);
                        }
                    }
                    
                    if(oneNei.equals(endWord)) {
                        isFound = true;
                    }
                }
            }

            levelSet = newLevelSet;
        }
        
        //System.out.println("fatherMap:" + fatherMap);
        
        if(isFound) {
            //gen result. Maybe multiple
            List<String> tmpRst = new ArrayList<>();
        	genRstForBFS(tmpRst, endWord, beginWord, fatherMap);
        }
    }
    
    public void bfs(String beginWord, String endWord) {
        Map<String, Set<String>> fatherMap = new HashMap<>();
        Set<String> levelSet = new HashSet<>();
        levelSet.add(beginWord);
        Set<String> visit = new HashSet<>();
        visit.add(beginWord);
        
        boolean isFound = false;
        while(!levelSet.isEmpty() && !isFound) {
            Set<String> newLevelSet = new HashSet<>();
            
            for(String oneStr : levelSet) {
                List<String> neighs = graph.get(oneStr);
                if(neighs == null) continue;
                
                for(String oneNei : neighs) {
                    if(!visit.contains(oneNei)) {
                        Set<String> fatherSet = new HashSet<>();
                        fatherSet.add(oneStr);
                        fatherMap.put(oneNei, fatherSet);
                        visit.add(oneNei);
                        newLevelSet.add(oneNei);
                    }
                    else {
                        if(newLevelSet.contains(oneNei)) {
                            fatherMap.get(oneNei).add(oneStr);
                        }
                    }
                    
                    if(oneNei.equals(endWord)) {
                        isFound = true;
                    }
                }
            }

            levelSet = newLevelSet;
        }
        
        System.out.println("fatherMap:" + fatherMap);
        
        if(isFound) {
            //gen result. Maybe multiple
            List<String> tmpRst = new ArrayList<>();
        	genRstForBFS(tmpRst, endWord, beginWord, fatherMap);
        }
    }
    
    
    
    
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        if(!isInPrevList(wordList, beginWord)) {
            wordList.add(beginWord);
        }
        
        constructGraph(wordList);
        
//        List<String> prevList = new ArrayList<>();
//        dfs(beginWord, endWord, prevList);
        bfs(beginWord, endWord);
        return rst;
    }
    
    static public void test() {
    	String[] wordArray = {"hot","dot","dog","lot","log","cog"};    	
    	WordLadderSol sol = new WordLadderSol();
    	List<String> wordList = new ArrayList<>(Arrays.asList(wordArray));
    	List<List<String>> rst = sol.findLadders("hit", "cog", wordList);
    	System.out.println("rst:"+rst);
    }
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////
//https://leetcode.com/problems/sliding-puzzle/
class SlidingPuzzleSolution {
    
    void exchangeValue(HashMap<Integer,Integer> state, int zeroId, int newId) {
        int value = state.get(newId);
        state.put(zeroId, value);
        state.put(newId, 0);
    }
    
    void addNewStateToQueue(HashMap<Integer,Integer> state, int zeroId, int newId,
                           LinkedList<HashMap<Integer, Integer>> queue,
                           HashSet<HashMap<Integer, Integer>> visitSet)
    {
        HashMap<Integer, Integer> newState = new HashMap<Integer, Integer>(state);
        exchangeValue(newState, zeroId, newId);
        if(!visitSet.contains(newState)) {
            queue.add(newState);
            //System.out.println("1111new state:"+newState+queue);
            visitSet.add(newState);
        }
    }
    
    void getNeighForOneState(HashMap<Integer, Integer> oneState, LinkedList<HashMap<Integer, Integer>> queue,
                            HashSet<HashMap<Integer, Integer>> visitSet)
    {
        int slideId = 0;
        for(int i = 0; i < 6; i++) {
            if(oneState.get(i) == 0) slideId = i;
        }
        //System.out.println("slideId:"+slideId);
        switch(slideId){
            case 0:
                addNewStateToQueue(oneState, 0, 1, queue, visitSet);
                addNewStateToQueue(oneState, 0, 3, queue, visitSet);
                break;
            case 1:
                addNewStateToQueue(oneState, 1, 0, queue, visitSet);
                addNewStateToQueue(oneState, 1, 2, queue, visitSet);
                addNewStateToQueue(oneState, 1, 4, queue, visitSet);
                break;
            case 2:
                addNewStateToQueue(oneState, 2, 1, queue, visitSet);
                addNewStateToQueue(oneState, 2, 5, queue, visitSet);
                break;
            case 3:
                addNewStateToQueue(oneState, 3, 0, queue, visitSet);
                addNewStateToQueue(oneState, 3, 4, queue, visitSet);
                break;
            case 4:
                addNewStateToQueue(oneState, 4, 3, queue, visitSet);
                addNewStateToQueue(oneState, 4, 5, queue, visitSet);
                addNewStateToQueue(oneState, 4, 1, queue, visitSet);
                break;
            case 5:
                addNewStateToQueue(oneState, 5, 2, queue, visitSet);
                addNewStateToQueue(oneState, 5, 4, queue, visitSet);
                break;
        } 
    }
    
    public int slidingPuzzle(int[][] board) {
        HashMap<Integer, Integer> input = new HashMap<>();
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 3; j++) {
                input.put(i*3+j, board[i][j]);
            }
        }
        
        HashMap<Integer, Integer> goal = new HashMap<>();
        goal.put(0,1);goal.put(1,2);goal.put(2,3);
        goal.put(3,4);goal.put(4,5);goal.put(5,0);
        
        LinkedList<HashMap<Integer, Integer>> queue = new LinkedList<>();
        queue.add(input);
        HashSet<HashMap<Integer, Integer>> visitSet = new HashSet<>();
        visitSet.add(input);
        int len = 0;
        
        while(!queue.isEmpty()) {
            LinkedList<HashMap<Integer, Integer>> newQueue = new LinkedList<>();
            //System.out.println("*****queue:"+queue.size());
            for(HashMap<Integer, Integer> oneState : queue) {
                if(oneState.equals(goal)) {
                    return len;
                }
                
                getNeighForOneState(oneState, newQueue, visitSet);
            }
            
            len++;
            queue = newQueue;
        }
        
        return -1;
    }
}





/////////////////////////////////////////////////////////////////////////////////////////////////////////

public class GraphQuestion1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WordLadderSol.test();
	}

}
