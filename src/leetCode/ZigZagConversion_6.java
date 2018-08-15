package leetCode;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ZigZagConversion_6 {

    public String convert(String s, int numRows) {
    	if(numRows <= 1) {
    		return s;
    	}
    	
        StringBuilder[] strRows = new StringBuilder[numRows];
        for(int i = 0; i < numRows; i++) {
        	strRows[i] = new StringBuilder();
        }
        
        int curRow = 0;
        Boolean isStraight = true;
        for(int i = 0; i < s.length(); i++) {
        	char alphabit = s.charAt(i);
        	strRows[curRow].append(alphabit);
        	if(isStraight) {
        		if(curRow+1 == numRows) {
        			curRow = numRows-2;
        			if(curRow != 0) {
        				isStraight = false;
        			}        			
        		}
        		else {
        			curRow = curRow+1;
        		}
        	}
        	else {
        		if(curRow-1 == 0) {
        			isStraight = true;
        		}
        		curRow = curRow-1;
        	}
        }
        
        StringBuilder wholeStr = new StringBuilder();
        for(StringBuilder oneRow : strRows) {
        	wholeStr.append(oneRow.toString());
        }
        
        return wholeStr.toString();
    }
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
    @Test
    void testThreeRows() {  	   	
    	assertEquals(convert("PAYPALISHIRING", 3), "PAHNAPLSIIGYIR");
    }
	
	
    @Test
    void testFourRows() {  	   	
    	assertEquals(convert("PAYPALISHIRING", 4), "PINALSIGYAHRPI");
    }	
	
	
    @Test
    void testTwoRows() {  	   	
    	assertEquals(convert("PAYPA", 2), "PYAAP");
    }
	
    @Test
    void testFiveRows() {  	   	
    	assertEquals(convert("PAYPALISHIRING", 5), "PHASIYIRPLIGAN");
    }
	
	
	
}
