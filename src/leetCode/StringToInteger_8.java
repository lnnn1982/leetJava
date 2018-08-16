package leetCode;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringToInteger_8 {
    public int myAtoi(String str) {
        String trimStr = str.trim();
        if(trimStr.isEmpty()) {
        	return 0;
        }
        
        Boolean isPossitive = true;        
        if(trimStr.charAt(0) == '+') {
        	trimStr = trimStr.substring(1, trimStr.length());
        }
        else if(trimStr.charAt(0) == '-') {
        	trimStr = trimStr.substring(1, trimStr.length());
        	isPossitive = false;
        }
        else if(trimStr.charAt(0) > '9' || trimStr.charAt(0) < '0') {
        	return 0;
        }
        
        int ret = 0;
        for(int i = 0; i < trimStr.length(); i++) {
        	if(trimStr.charAt(i) > '9' || trimStr.charAt(i) < '0') {
        		break;
        	}
        	
        	int singleBit = trimStr.charAt(i)-'0';
        	if(ret > Integer.MAX_VALUE/10 || (ret == Integer.MAX_VALUE/10 && singleBit > Integer.MAX_VALUE%10)) {
        		return Integer.MAX_VALUE;
        	}
        	if(ret < Integer.MIN_VALUE/10 || (ret == Integer.MIN_VALUE/10 && (0-singleBit) < Integer.MIN_VALUE%10)) {
        		return Integer.MIN_VALUE;
        	}
        	
        	if(isPossitive) {
        		ret = ret*10+singleBit;
        	}
        	else {
        		ret = ret*10-singleBit;
        		
        	}
        	
        }
        
        return ret;
    }
	
	
    @Test
    void testEmptyStr() {  	   	
    	assertEquals (myAtoi(""), 0) ;
    }
	
    @Test
    void testSpaceStr() {  	   	
    	assertEquals (myAtoi("   "), 0) ;
    }
	
    @Test
    void testPossitiveStr() {  	   	
    	assertEquals (myAtoi(" +123  "), 123) ;
    }
	
    @Test
    void testNormalStr() {  	   	
    	assertEquals (myAtoi(" 123  "), 123) ;
    }
	
    @Test
    void testNegativeStr() {  	   	
    	assertEquals (myAtoi(" -123  "), -123) ;
    }
	
    @Test
    void testOnePossitiveSignChar() {  	   	
    	assertEquals (myAtoi("+"), 0) ;
    }
	
    @Test
    void testOneNegativeignChar() {  	   	
    	assertEquals (myAtoi("-"), 0) ;
    }
	
    @Test
    void testNonDigitBegin() {  	   	
    	assertEquals (myAtoi("word1234"), 0) ;
    }
	
    @Test
    void testNonDigitMiddle() {  	   	
    	assertEquals (myAtoi("1234word 5678"), 1234) ;
    }
	
    @Test
    void testPossitiveOverflow() {  	   	
    	assertEquals (myAtoi("2147483648"), 2147483647) ;
    }
    
    @Test
    void testPossitiveNotOverflow() {  	   	
    	assertEquals (myAtoi("2147483647"), 2147483647) ;
    }
	
    @Test
    void testPossitiveOverflow1() {  	   	
    	assertEquals (myAtoi("2147483650"), 2147483647) ;
    }
    
    @Test
    void testNegativeNotOverflow() {  	   	
    	assertEquals (myAtoi("-2147483648"),-2147483648) ;
    }
	
    @Test
    void testNegativeOverflow1() {  	   	
    	assertEquals (myAtoi("-2147483650"), -2147483648) ;
    }
    
    @Test
    void testNegativeOverflow2() {  	   	
    	assertEquals (myAtoi("-91283472332"), -2147483648) ;
    }
    
    @Test
    void testNegativeOverflow() {  	   	
    	assertEquals (myAtoi("-2147483649"), -2147483648) ;
    }
}
