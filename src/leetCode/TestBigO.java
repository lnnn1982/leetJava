package leetCode;

public class TestBigO {
	int count = 0;
	
    void permutation(String str) {
    	permutation(str, "");
    }
    
    void permutation(String str, String prefix) {
		System.out.println("count:"+count);
		count += 1;
		
    	System.out.println("str:"+str+", prefix:"+prefix);
    	if(str.length() == 0) {
    		System.out.println("************************"+prefix);
    	}
    	else {
    		for(int i = 0; i < str.length(); i++) {
    			String rem = str.substring(0, i) + str.substring(i+1);

    			permutation(rem, prefix+str.charAt(i));
    		}
    	}
    }
	
	public static void main(String[] args) {
		TestBigO testBigO = new TestBigO();
		testBigO.permutation("abc", "12");
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
}
