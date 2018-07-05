package leetCode;

import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MedianTwoSortedArrays_4 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    	if(nums1.length == 0 || nums2.length == 0) {
    		throw new IllegalArgumentException("input has no sum");
    	}
    	
        if(nums1.length > nums2.length) {
        	return findMedianSortedArrays(nums2, nums1);
        }

        int len1 = nums1.length, len2 = nums2.length;
        int iMin = 0, iMax = nums1.length;
        int i = (iMin+iMax)/2;
        int j = (len1+len2+1)/2 - i;
        int median1 = 0, median2 = 0;
        while(true) {
        	if(i == 0) {
        		if(nums1[i] >= nums2[j-1]) {
//        			median1 = nums2[j-1];
//        			if(j == len2) {
//        				median2 = nums1[i];
//        			}
//        			else {
//        				median2 = Math.max(nums2[j], nums1[i]);
//        			}
        			break;
        		}
        		else {
        			iMin = i+1;
        		}
        	}
        	else if(i == len1) {
        		if(nums1[i-1] <= nums2[j]) {
//        			median2 = nums2[j];
//        			if(j == 0) {
//        				median1 = nums1[i-1];
//        			}
//        			else {
//        				median1 = Math.max(nums1[i-1], nums2[j-1]);
//        			}
        			break;
        		}
        		else {
        			iMax = i-1;
        		}
        	}
        	else {
        		if(nums1[i-1] > nums2[j]) {
        			iMax = i-1;
        		}
        		else if(nums2[j-1] > nums1[i]) {
        			iMin = i+1;
        		}
        		else {
//        			median2 = Math.max(nums2[j], nums1[i]);
//        			median1 = Math.max(nums1[i-1], nums2[j-1]);
        			break;
        		}
        	}
        	
        	i = (iMin+iMax)/2;
        	j = (len1+len2+1)/2-i;
        }
    	
		if(j == 0) {
			median1 = nums1[i-1];
		}
		else if(i == 0) {
			median1 = nums2[j-1];
		}
		else {
			median1 = Math.max(nums1[i-1], nums2[j-1]);
		}
    	
		if(j == len2) {
			median2 = nums1[i];
		}
		else if(i == len1) {
			median2 = nums2[j];
		}
		else {
			median2 = Math.min(nums2[j], nums1[i]);
		}
		//System.out.println("i:" + i + ", j:" + j);
    	
    	if(((len1+len2)&1) == 0) {
    		//System.out.println((median1+median2)/2.0);
    		return (median1+median2)/2.0;
    	}
    	else {
    		//System.out.println((double)median1);
    		return (double)median1;
    	}
    }
    
    @Test
    void testNormal() {  	   	
    	assertEquals (findMedianSortedArrays(new int[]{1, 3}, new int[]{2}), 2.0) ;
    }
    
    
    @Test
    void testNorma2() {  	   	
    	assertEquals (findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}), 2.5) ;
    }
    
    
    @Test
    void testNorma3() {  	   	
    	assertEquals (findMedianSortedArrays(new int[]{1}, new int[]{1}), 1.0) ;
    }
    
    @Test
    void testNorma4() {  	   	
    	assertEquals (findMedianSortedArrays(new int[]{1}, new int[]{1, 2, 3}), 1.5) ;
    }
    
    @Test
    void testNorma5() {  	   	
    	assertEquals (findMedianSortedArrays(new int[]{9}, new int[]{1, 2, 3}), 2.5) ;
    }
    
    @Test
    void testNorma6() {  	   	
    	assertEquals (findMedianSortedArrays(new int[]{1, 3, 5, 7}, new int[]{2, 4, 6, 8}), 4.5) ;
    }
    
    @Test
    void testNorma7() {  	   	
    	assertEquals (findMedianSortedArrays(new int[]{1, 3, 5, 7, 9}, new int[]{2, 4, 6, 8}), 5) ;
    }
}











