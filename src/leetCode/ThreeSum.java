package leetCode;

import java.util.*;

public class ThreeSum {
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> sumList = new ArrayList<List<Integer>>();
        
        if(nums.length < 3) {
        	return sumList;
        }
        
        Arrays.sort(nums);
        
        for(int i = 0; i < nums.length-2; ) {
        	int j = i+1, k = nums.length-1;
        	while(j < k) {
        		int diff = nums[i]+nums[j]+nums[k];
        		if(diff > 0) {
        			k--;
        			while(k>=0 && nums[k] == nums[k+1]) {k--;}
        		}
        		else if(diff < 0) {
        			j++;
        			while(j<nums.length && nums[j-1]==nums[j]) {j++;}
        		}
        		else {
        			List<Integer> oneResult = new ArrayList<Integer>();
        			oneResult.add(nums[i]);
        			oneResult.add(nums[j]);
        			oneResult.add(nums[k]);
        			sumList.add(oneResult);
        			
        			j++;
        			k--;
        			
        			while(j<nums.length && nums[j-1]==nums[j]) {j++;}
        			while(k>=0 && nums[k] == nums[k+1]) {k--;}
        		}
        	}
        	
        	i++;
        	while(i < nums.length-1 && nums[i] == nums[i-1]) {i++;}
        }
    	
    	return sumList;
    }
	
    public static int threeSumClosest(int[] nums, int target) {
    	if(nums.length == 0) {
    		return 0;
    	}
    	else if(nums.length == 1) {
    		return nums[0];
    	}
    	else if(nums.length == 2) {
    		return nums[0]+nums[1];
    	}
    	
        Arrays.sort(nums);
        
        int closestDiff = Integer.MAX_VALUE;
        int closesSum = 0;
    	for(int i = 0; i < nums.length-2; i++) {
    		int j = i+1, k = nums.length-1;
    		while(j != k ) {
    			int threeSum = nums[i]+nums[j]+nums[k];
        		int curDiff = target-threeSum;
        		if(curDiff == 0) {
        			return threeSum;
        		}
        		
        		if(Math.abs(curDiff) < closestDiff) {
        			closestDiff = Math.abs(curDiff);
        			closesSum = threeSum;
        		}
        		
        		if(curDiff > 0) {
        			j++;
        			
        		}
        		else if(curDiff < 0) {
        			k--;
        		}
    		}
    	}
    	
    	return closesSum;
    }
	
    public static void printResultList(List<List<Integer>> results) {
    	System.out.println("print result begin " + results.size());
    	for(List<Integer> oneResult : results) {
    		Integer [] array = new Integer[oneResult.size()];
    		System.out.println(Arrays.toString(oneResult.toArray(array)));
    	}
    	System.out.println("print result end");
    }
	
	public static void main(String[] args) {
//		int closest = threeSumClosest(new int[]{-1, 2, 1, -4}, 1);
//		System.out.println("closest:" + closest);
//		
//		closest = threeSumClosest(new int[]{1, 2, 3, 4}, 6);
//		System.out.println("closest:" + closest);
//		
//		closest = threeSumClosest(new int[]{-1,0,1,1,55}, 3);
//		System.out.println("closest:" + closest);
		
		List<List<Integer>> resultList = null;
		
//		resultList = threeSum(new int[]{1, -1, 0});
//		printResultList(resultList);
//		
//		resultList = threeSum(new int[]{1, -1, 0, 3});
//		printResultList(resultList);
//		
//		resultList = threeSum(new int[]{3, 7, 1, 4, -1, 0, 3});
//		printResultList(resultList);
//		
//		resultList = threeSum(new int[]{3, 7, 1, 4});
//		printResultList(resultList);
//		
//		resultList = threeSum(new int[]{0,0,0,0,0,0,0});
//		printResultList(resultList);		
		
		resultList = threeSum(new int[]{-1, 0, 1, 2, -1, -4});
		printResultList(resultList);
		
		
		System.out.println("end");
		
	}
}
