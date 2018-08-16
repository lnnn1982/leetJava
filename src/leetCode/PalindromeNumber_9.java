package leetCode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PalindromeNumber_9 {
    public boolean isPalindrome(int x) {
        //String a = String.valueOf(x);
        if(x<0) return false;

        long revt = 0;
        int value = x;
        while(value > 0) {
            revt = revt*10+value%10;
            value=value/10;
        }
        
        return revt == (long)(x);
    }
	
    @Test
    void testPositiveValueTrue() {  	   	
    	assertEquals(isPalindrome(121), true);
    }
	
    @Test
    void testPositiveValueFalse() {  	   	
    	assertEquals(isPalindrome(1212), false);
    }
	
    @Test
    void testNegativeValueFalse() {  	   	
    	assertEquals(isPalindrome(-12), false);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
}
