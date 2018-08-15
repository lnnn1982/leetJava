package leetCode;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Reverse_Integer_7 {
    public int reverse(int x) {
        int value = Math.abs(x);
        long result = 0;
        int residual = 0;
        
    	while(value > 0) {
    		residual = value%10;
    		value = value/10;
    		result = result*10+residual;
    	}
    	
    	if(x < 0) result = 0-result;

    	long pow2_31 = ((long)1<<31);
    	long possitiveMax = pow2_31-1;
    	long negativeMin = 0-pow2_31;
    	//System.out.println("pow2_31:"+pow2_31+", possitiveMax:"+possitiveMax+", negativeMin:"+negativeMin);
    	
    	if(result > possitiveMax || result < negativeMin) {
    		return 0;
    	}
    	
    	return (int)result;
    }
	
    @Test
    void testPositiveValue() {  	   	
    	assertEquals(reverse(4963), 3694);
    }
	
    @Test
    void testNegativeValue() {  	   	
    	assertEquals(reverse(-4963), -3694);
    }	
	
    @Test
    void testZeroValue() {  	   	
    	assertEquals(reverse(0), 0);
    }
	
    @Test
    void testZeroEndValue() {  	   	
    	assertEquals(reverse(120), 21);
    }
	
	
    @Test
    void testLargeValue() {  	   	
    	assertEquals(reverse(1999999999), 0);
    }	
	
    @Test
    void testLargeValueLimit() {  	   	
    	assertEquals(reverse(1463847412), 2147483641);
    }
}
