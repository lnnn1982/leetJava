package leetCode;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TwoSum {
	
    static public int[] twoSum(int[] nums, int target) {
    	int [] result = new int[2];
    	Map<Integer, List<Integer> > indexMap = new HashMap<>();
    	
    	for(int i = 0; i < nums.length; i++) {
    		if(!indexMap.containsKey(nums[i])) {
    			final int copy = i;
    			indexMap.put(nums[i], new ArrayList<Integer>() {{add(copy);}});
    		}
    		else {
    			indexMap.get(nums[i]).add(i);
    		}
    	}
    	
    	for (Map.Entry<Integer, List<Integer> > item : indexMap.entrySet()) {
    		if((target - item.getKey()) == item.getKey()) {
    			if(item.getValue().size() >= 1) {
    				result[0] = item.getValue().get(0);
    				result[1] = item.getValue().get(1);

    				break;
    			}
    		}
    		else if(indexMap.containsKey(target - item.getKey())) {
    			result[0] = item.getValue().get(0);
    			result[1] = indexMap.get(target-item.getKey()).get(0);
    			break;
    		}
    	}
    	
    	System.out.println("result:" + Arrays.toString(result));
    	return result;
    }
    
    static public int[] twoSumV2(int[] nums, int target) {
    	Map<Integer, Integer> hashMap = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
        	if(hashMap.containsKey(target - nums[i])) {
        		return new int[]{hashMap.get(target - nums[i]), i};
        	}
        	else {
        		hashMap.put(nums[i], i);
        	}
        }
        
        throw new IllegalArgumentException("input has no sum");
    }
    
    
    @Test
    void testNormal() {  	   	
    	assertArrayEquals(twoSum(new int[] {2, 7, 11, 15}, 9), new int[] {0,1});
    }
    
    @Test
    void testNormal1() {  	   	
    	assertArrayEquals(twoSum(new int[] {2, 7, 11, 15}, 26), new int[] {2,3});
    }
    
    @Test
    void duplicateTest() {  	   	
    	assertArrayEquals(twoSum(new int[] {2, 7, 2, 4}, 4), new int[] {0,2});
    }
    
  @Test
  void testNormalVersion2() {  	   	
  	assertArrayEquals(twoSumV2(new int[] {2, 7, 11, 15}, 9), new int[] {0,1});
  }
  
  @Test
  void testNormal1Version2() {  	   	
  	assertArrayEquals(twoSumV2(new int[] {2, 7, 11, 15}, 26), new int[] {2,3});
  }
  
  @Test
  void duplicateTestVersion2() {  	   	
  	assertArrayEquals(twoSumV2(new int[] {2, 7, 2, 4}, 4), new int[] {0,2});
  }
    

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//        int[] result = twoSum(new int[] {2, 7, 11, 15}, 9);
//        System.out.println("result:" + Arrays.toString(result));
	}

}
