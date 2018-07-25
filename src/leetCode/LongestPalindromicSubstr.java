package leetCode;

import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LongestPalindromicSubstr {

	public String longestPalindrome(String s) {
        if(s.isEmpty()) {
        	return "";
        }
        
        boolean [][] dp = new boolean[s.length()][s.length()];
        int maxLen=1;
        int startIndex=0;
        
        for(int i = 0; i < s.length(); i++) {
        	dp[i][i] = true;
        	
        	if(i+1 < s.length()) {
        		if(s.charAt(i)== s.charAt(i+1)) {
        			dp[i][i+1] = true;
        			if(maxLen != 2) {
            			maxLen = 2;
            			startIndex = i;
        			}
        		}
        		else {
        			dp[i][i+1] = false;
        		}
        	}
        }
        
        for(int len = 3; len <= s.length(); len++) {
        	for(int i=0; i <= s.length()-len; i++) {
        		if(s.charAt(i) == s.charAt(i+len-1) && dp[i+1][i+len-2]) {
        			dp[i][i+len-1] = true;
        			if(maxLen != len) {
            			maxLen = len;
            			startIndex = i;
        			}
        		}
        		else {
        			dp[i][i+len-1] = false;
        		}
        	}
        }

        return s.substring(startIndex, startIndex+maxLen);
	}
	
	@Test
	void testOneCharacter() {
		assertEquals(longestPalindrome("2"), "2");
	}
	
	@Test
	void testSinglePalindromicCharacter() {
		assertEquals(longestPalindrome("abcda"), "a");
	}
	
	@Test
	void testEmptyCharacter() {
		assertEquals(longestPalindrome(""), "");
	}	
	
	@Test
	void testDuplicateCharOdd() {
		assertEquals(longestPalindrome("55555"), "55555");
	}
	
	@Test
	void testDuplicateCharEven() {
		assertEquals(longestPalindrome("555555"), "555555");
	}	
	
	@Test
	void testTwoCharacters() {
		assertEquals(longestPalindrome("22"), "22");
	}
	
	@Test
	void testTwoCharactersLeading() {
		assertEquals(longestPalindrome("22abc"), "22");
	}
	
	@Test
	void testPalindromicFirst() {
		assertEquals(longestPalindrome("babad"), "bab");
	}
	
	@Test
	void testPalindromicMiddle() {
		assertEquals(longestPalindrome("cbbd"), "bb");
	}
	
	@Test
	void testPalindromicAllEven() {
		assertEquals(longestPalindrome("abccba"), "abccba");
	}
	
	@Test
	void testPalindromicAllOdd() {
		assertEquals(longestPalindrome("abcdcba"), "abcdcba");
	}
	
	

}
