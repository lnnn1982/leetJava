package leetCode;
import java.util.*;

//https://leetcode.com/problems/palindrome-pairs/submissions/
class PalindromeSolution {  
    boolean checkIfPalin(String words, int i, int j) {
        while(i < j) {
            if(words.charAt(i) != words.charAt(j)) {
                return false;
            }
            
            i++;
            j--;
        }
        return true;
    }
    
    boolean checkIfPalin(String word) {
        return checkIfPalin(word, 0, word.length()-1);
    }
    
    String getReverseStr(String str) {
        StringBuilder builder = new StringBuilder();
        for(int i = str.length()-1; i >=0; i--) {
            builder.append(str.charAt(i));
        }
        
        return builder.toString();
    }
    

    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> rstList = new ArrayList<>();
        Map<String, Integer> wordMap = new HashMap<>();
        for(int i = 0; i < words.length; i++) {
            wordMap.put(words[i], i);
        }
 
        for(int i = 0; i < words.length; i++) {
            String curWord = words[i];
            String reverseStr = getReverseStr(curWord);
            for(int j = 0; j < reverseStr.length()-1; j++) {
                String leftStr = reverseStr.substring(0, j+1);
                String rightStr = reverseStr.substring(j+1);
                
                Integer val = wordMap.get(leftStr);
                if(val != null && checkIfPalin(rightStr)) {
                    List<Integer> oneList = new ArrayList<Integer>();
                    oneList.add(val);
                    oneList.add(i);
                    rstList.add(oneList);
                }
                
                val = wordMap.get(rightStr);
                if(val != null && checkIfPalin(leftStr)) {
                    List<Integer> oneList = new ArrayList<Integer>();
                    oneList.add(i);
                    oneList.add(val);
                    rstList.add(oneList);
                }
            }
            
            if(reverseStr.equals(curWord)) {
                if(!curWord.isEmpty()) {
                    Integer val = wordMap.get("");
                    if(val != null) {
                        List<Integer> oneList = new ArrayList<Integer>();
                        oneList.add(i);
                        oneList.add(val);
                        rstList.add(oneList);
                        
                        oneList = new ArrayList<Integer>();
                        oneList.add(val);
                        oneList.add(i);
                        rstList.add(oneList);
                    }
                }
            }
            else {
                Integer val = wordMap.get(reverseStr);
                if(val != null) {
                    List<Integer> oneList = new ArrayList<Integer>();
                    oneList.add(i);
                    oneList.add(val);
                    rstList.add(oneList);
                }
            }
        }
        
        return rstList;
    }
}



public class StringQuestion {

}
