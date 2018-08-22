package leetCode;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContainerWithMostWater_11 {

    public int maxArea(int[] height) {
        int area = 0;
    	int i = 0;
    	int j = height.length-1;
    	
    	while(i<j) {
    		int newArea = Math.min(height[i], height[j])*(j-i);
    		area = Math.max(area, newArea);
    		
    		if(height[i] < height[j]) {
    			i++;
    		}
    		else {
    			j--;
    		}
    	}
    	
    	return area;
    }
	
	
    @Test
    void testNegativeValueFalse() {  	   	
    	assertEquals(maxArea(new int[]{1,8,6,2,5,4,8,3,7}), 49);
    }
	
	
	
	
	
	
	
	
	
	
	
}
