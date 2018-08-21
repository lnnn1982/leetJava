package leetCode;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegularExpressionMatching_10 {

	
    public boolean isMatchByRecursive(String text, String pattern) {
        if(pattern.isEmpty())	return text.isEmpty();
        
        boolean isFirstMatch = text.length()>0 && (text.charAt(0) == pattern.charAt(0)
        		|| pattern.charAt(0) == '.');
        
        if(pattern.length()>1 && pattern.charAt(1)=='*') {
        	return isMatchByRecursive(text, pattern.substring(2)) ||
        			(isFirstMatch && isMatchByRecursive(text.substring(1), pattern));
        }
        else {
        	return isFirstMatch && isMatchByRecursive(text.substring(1), pattern.substring(1));
        }    
    }
	
    public boolean isMatch(String text, String pattern) {
          boolean[][] dp = new boolean[text.length()+1][pattern.length()+1];
          dp[text.length()][pattern.length()] = true;
          
          for(int i=text.length(); i>=0; i--) {
        	  for(int j=pattern.length()-1; j>=0; j--) {
        		  boolean isFirstMatch = (i < text.length())
        				  && (text.charAt(i) == pattern.charAt(j) || pattern.charAt(j) == '.');
        		  
        		  if(j < (pattern.length()-1) && pattern.charAt(j+1) == '*') {
        			  dp[i][j] = dp[i][j+2] || (isFirstMatch&&dp[i+1][j]);
        	      }
        		  else {
        			  dp[i][j] = isFirstMatch&&dp[i+1][j+1];
        		  }
        	  }
          }

          return dp[0][0];
    }
	
	
	
    @Test
    void testNoStarTrue() {  	   	
    	assertEquals(isMatchByRecursive("abc", "abc"), true);
    	assertEquals(isMatch("abc", "abc"), true);
    }
	
    @Test
    void testNoStarFalsePatternLess() {  	   	
    	assertEquals(isMatchByRecursive("abc", "ab"), false);
    	assertEquals(isMatch("abc", "ab"), false);
    }
    
    @Test
    void testNoStarFalseTextLess() {  	   	
    	assertEquals(isMatchByRecursive("ab", "abc"), false);
    	assertEquals(isMatch("ab", "abc"), false);
    }
    
    @Test
    void testStarTrueMatchFirst() {  	   	
    	assertEquals(isMatchByRecursive("aab", "a*ab"), true);
    	assertEquals(isMatch("aab", "a*ab"), true);
    }
    
    @Test
    void testStarTrueMatchSecond() {  	   	
    	assertEquals(isMatchByRecursive("d", "d*d"), true);
    	assertEquals(isMatch("d", "d*d"), true);
    }
    
    @Test
    void testStarTrueMatchDot() {  	   	
    	assertEquals(isMatchByRecursive("def", ".*"), true);
    	assertEquals(isMatch("def", ".*"), true);
    }
    
    @Test
    void testStarFalse() {  	   	
    	assertEquals(isMatchByRecursive("mississippi", "mis*is*p*."), false);
    	assertEquals(isMatch("mississippi", "mis*is*p*."), false);
    }
    
}

