package leetCode;
import java.util.*;

//https://leetcode.com/problems/backspace-string-compare/submissions/
class BackspaceCompareSol {
    public boolean backspaceCompare(String S, String T) {
        int i = S.length()-1;
        int j = T.length()-1;
        
        while(i >= 0 || j >= 0) {
            int sCount = 0;
            while(i>=0) {
                if(S.charAt(i) == '#') {
                    sCount++;
                    i--;
                }
                else if(sCount>0) {
                    sCount--;
                    i--;
                }
                else {
                    break;
                }
            }
            
            int tCount = 0;
            while(j>=0) {
                if(T.charAt(j) == '#') {
                    tCount++;
                    j--;
                }
                else if(tCount>0) {
                    tCount--;
                    j--;
                }
                else {
                    break;
                }
            }
            
            
            if(i>=0 && j>= 0 && S.charAt(i) != T.charAt(j)) {
                return false;
            }
            
            if((j >= 0 && i < 0) || (i >= 0 && j < 0)) {
                return false;
            }
            
            i--;
            j--;
        }
        
        return true;
    }

    public boolean backspaceCompareOrgVersion(String S, String T) {
        int i = S.length()-1;
        int j = T.length()-1;
        
        while(i >= 0 || j >= 0) {
            if(i>=0 && j>= 0 && S.charAt(i) != '#' && T.charAt(j) != '#') {
                if(S.charAt(i) == T.charAt(j)) {
                    i--;
                    j--;
                    continue;
                }
                else {
                    return false;
                }
            }
            
            if(i>=0 && j<0 && S.charAt(i) != '#') {
                return false;
            }
            
            if(j>=0 && i<0 && T.charAt(j) != '#') {
                return false;
            }
            
            if(i >= 0 && S.charAt(i) == '#') {
                int sCount = 1;
                i--;
                while(i>=0 && sCount>0) {
                    if(S.charAt(i) == '#') {
                        sCount++;
                    }
                    else {
                        sCount--;
                    }
                    i--;
                }
            }

            if(j >= 0 && T.charAt(j) == '#') {
                int tCount = 1;
                j--;
                while(j>=0 && tCount>0) {
                    if(T.charAt(j) == '#') {
                        tCount++;
                    }
                    else {
                        tCount--;
                    }
                    j--;
                }
            }
        }
        
        return true;
        

    }
}

//https://leetcode.com/problems/sort-colors/
class SortColorsSolution {
    public void sortColors(int[] nums) {
        int i = 0;
        int j = 0;
        int k = nums.length-1;
        
        while(j <= k) {
            if(nums[j] == 0) {
                int tmp = nums[j];
                nums[j] = nums[i];
                nums[i] = tmp;
                j++;
                i++;
            }
            else if(nums[j] == 2) {
                int tmp = nums[j];
                nums[j] = nums[k];
                nums[k] = tmp;
                k--;
            }
            else if(nums[j] == 1) {
                j++;
            }
        }
        
        return;
    }
}

//https://leetcode.com/problems/search-in-rotated-sorted-array-ii/
class  SearchinRotatedSortedArraySolution {
    public boolean search(int[] nums, int target, int left, int right) {
        if(left > right) return false;
        
        int mid = (left+right)/2;
        if(nums[mid] == target) {
            return true;
        }
        
        if(nums[left] < nums[mid]) {
            if(nums[left] <= target && target < nums[mid]) {
                return search(nums, target, left, mid-1);
            }
            else {
                return search(nums, target, mid+1, right);
            }
        }
        else if(nums[mid] < nums[left]) {
            if(nums[mid] < target && target <= nums[right]) {
                return search(nums, target, mid+1, right);
            }
            else {
                return search(nums, target, left, mid-1);
            }
        }
        else if(nums[mid] == nums[left]) {
            if(nums[mid] != nums[right]) {
               return  search(nums, target, mid+1, right);
            }
            else {
                if(search(nums, target, left, mid-1)) {
                    return true;
                }
                else {
                    return search(nums, target, mid+1, right);
                }

            }
        }
        
        return false;
    }
    
    public boolean search(int[] nums, int target) {
        return search(nums, target, 0, nums.length-1);
    }

}

//https://www.cnblogs.com/grandyang/p/5595614.html
class SortTransformedArraySolution {
	public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
		int i = 0;
		int j = nums.length-1;
		int[] rst = new int[nums.length];
		int time = 0;
		
		while(i <= j) {
			int iY = calc(a,b,c,nums[i]);
			int jY = calc(a,b,c,nums[j]);
			if(a >= 0) {
				if(iY >= jY) {
					rst[nums.length-time-1] = iY;
					i++;
				}
				else {
					rst[nums.length-time-1] = jY;
					j--;
				}
			}
			else {
				if(iY <= jY) {
					rst[time] = iY;
					i++;
				}
				else {
					rst[time] = jY;
					j--;
				}				
			}
			time++;
		}
		
		return rst;
	}

	int calc(int a, int b, int c, int x) {
		return a*x*x + b*x + c;
	}
	
	static public void test() {
		SortTransformedArraySolution sol = new SortTransformedArraySolution();
//		int[] input = {-4, -2, 2, 4};
//		int[] rst = sol.sortTransformedArray(input, 1, 3, 5);
		
//		int[] input = {-4, -2, 2, 4};
//		int[] rst = sol.sortTransformedArray(input, -1, 3, 5);
		
//		int[] input = {-4, -2, 2, 4};
//		int[] rst = sol.sortTransformedArray(input, 0, 3, 5);
		
		int[] input = {-4, -2, 2, 4};
		int[] rst = sol.sortTransformedArray(input, 0, -3, 5);
		System.out.println(Arrays.toString(rst));
	}
}

    















public class DoublePointer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SortTransformedArraySolution.test();

	}

}
