package leetCode;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LongestSubstrWithoutRepeatingChar_3 {
	
    public int lengthOfLongestSubstring(String s) {
        int i = 0, j = 0, len = 0;
        Map<Character, Integer> charIndexMap = new HashMap<>();
        for(; j < s.length(); j++) {
        	char curChar = s.charAt(j);
        	Integer index = charIndexMap.get(curChar);
        	if(index != null) {
        		len = Math.max(len, charIndexMap.size());
        		for(int i1 = i; i1 < index+1; i1++) {
        			charIndexMap.remove(s.charAt(i1));
        		}
        		i = index+1;
        	}
        	charIndexMap.put(curChar, j);
        }
        
        len = Math.max(len, charIndexMap.size());
        System.out.println("len:" + len);
        
        return len;
    }
    
    
    public int lengthOfLongestSubstringStep1(String s) {
        int i = 0, j = 0, len = 0;
        Set<Character> charSet = new HashSet<>();
        while(j < s.length()) {
        	if(charSet.contains(s.charAt(j))) {
        		len = Math.max(len, charSet.size());
        		charSet.remove(s.charAt(i));
        		i++;
        	}
        	else {
        		charSet.add(s.charAt(j));
        		j++;
        	}
        }
        
        len = Math.max(len, charSet.size());
        return len;
    }
    
    
    @Test
    void testNormal() {  	   	
    	assertEquals (lengthOfLongestSubstring("abcabcbb"), 3);
    	assertEquals (lengthOfLongestSubstringStep1("abcabcbb"), 3);
    }

    @Test
    void testNormal1() {  	   	
    	assertEquals (lengthOfLongestSubstring("bbbbb"), 1);
    	assertEquals (lengthOfLongestSubstringStep1("bbbbb"), 1);
    }
    
    @Test
    void testNormal2() {  	   	
    	assertEquals (lengthOfLongestSubstring("pwwkew"), 3);
    	assertEquals (lengthOfLongestSubstringStep1("pwwkew"), 3);
    	
    }
    
    @Test
    void testNormal3() {  	   	
    	assertEquals (lengthOfLongestSubstring("abcbdef"), 5);
    	assertEquals (lengthOfLongestSubstringStep1("abcbdef"), 5);
    }
    
    @Test
    void testNormal4() {  	   	
    	assertEquals (lengthOfLongestSubstring("abcgdef"), 7);
    	assertEquals (lengthOfLongestSubstringStep1("abcgdef"), 7);
    }
}
