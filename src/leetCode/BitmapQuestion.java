package leetCode;

import java.util.*;

public class BitmapQuestion {

	
	
    public static int hammingWeight(int n) {
        //long ln = (long)(n);
        //System.out.println(Long.toHexString(ln));
        //long val = 0xFFFFFFFFL;
        //long un = ln & val;
        //System.out.println(Long.toHexString(un));
        //System.out.println(un);

    	int un = n;
        int num = 0; 
        while(un != 0) {
            if((un & 1) == 1) num++;
            un = un >>> 1;
        }
        
        return num;
        
        
        
        
    }
	
	public static void main(String[] args) {
		
		int a = hammingWeight(0x80000000);
		System.out.println(a);
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
