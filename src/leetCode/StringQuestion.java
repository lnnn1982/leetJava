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

//https://www.geeksforgeeks.org/count-number-of-substrings-with-exactly-k-distinct-characters/
class KDistinctCharactersSol {
    public static List<String> countkDist(String str, int k) {
        List<String> retList = new ArrayList<>();
        int[] map = new int[26];

        for(int i = 0; i < str.length(); i++) {
        	Arrays.fill(map, 0);
        	int count = 0;
            for(int j = i; j < str.length(); j++) {
                if(map[str.charAt(j)-'a'] == 0) {
                	count++;
                }

                map[str.charAt(j)-'a']++;
//                System.out.println("map:"+Arrays.toString(map) + ", count:" + count + ", i:" + i
//                		+ ", j:" + j + ", str:" + str.substring(i,j+1));
                if(count == k) {
                	retList.add(str.substring(i,j+1));
                }
                else if(count > k) {
                	break;
                }
            }
        }

        return retList;
    }

    public static boolean isContainKChar(String str, int k) {
        HashSet<Character> set = new HashSet<>();
        for(int i = 0; i < str.length(); i++) {
            set.add(str.charAt(i));
        }

        return set.size() == k;
    }
    
    public static String longestSubStringWithKDistinctChar(String str, int k) {
        if(k == 0) {
            return "";
        }
        
        String longStr = "";

        HashMap<Character, Integer> charNumMap = new HashMap<>();
        int fp = 0;
        int lp = 0;
        while(fp < str.length() && lp < str.length()) {
            while(lp < str.length()) {
                if(charNumMap.size() == k) {
                    if(!charNumMap.containsKey(str.charAt(lp))) {
                        break;
                    }
                }

                char ch = str.charAt(lp);
                Integer num = charNumMap.get(ch);
                if(num == null) {
                    charNumMap.put(ch, 1);
                }
                else {
                    charNumMap.put(ch, num+1);
                }

                if(charNumMap.size() == k) {
                    //System.out.println("000000000000000 "+ str.substring(fp, lp+1));
                    if((lp-fp+1) > longStr.length()) {
                    	longStr = str.substring(fp, lp+1);
                    }
                }

                lp++;
            }
            
            while(fp < str.length()) {
                if(fp == lp) {
                    break;
                }

                char ch = str.charAt(fp);
                Integer num = charNumMap.get(ch);
                num--;
                if(num == 0) {
                    charNumMap.remove(ch);
                }
                else {
                	charNumMap.put(ch, num);
                }

                fp++;

                if(charNumMap.size() < k) {
                    break;
                }

//                if(charNumMap.size() == k) {
//                    //System.out.println("11111111111111 "+ str.substring(fp, lp) + ", fp:" + fp
//                    		//+ ", lp:" + lp + ", charNumMap:" + charNumMap);
//                    retList.add(str.substring(fp, lp));
//                }
            }
        }

        return longStr;
    }
    
    public static void test() {
    	List<String> restList = null;
    	restList = countkDist("abc", 2);
    	System.out.println("ret:" + restList);
    	
    	restList = countkDist("aba", 2);
    	System.out.println("ret:" + restList);
    	
    	restList = countkDist("aa", 1);
    	System.out.println("ret:" + restList);
    	
    	restList = countkDist("abcabcefg", 3);
    	System.out.println("ret:" + restList);
    	
//    	String longStr = longestSubStringWithKDistinctChar("abcabcefg", 3);
//    	System.out.println("longStr:" + longStr);
    	

    	
    	
    	
    	
    }
}


public class StringQuestion {
	public static void main(String[] args) {
		KDistinctCharactersSol.test();
	}
	
}
