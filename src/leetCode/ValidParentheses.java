package leetCode;

import java.util.*;

public class ValidParentheses {

	//678. Valid Parenthesis String
    public static boolean checkValidString(String s) {
        int lowBalance = 0, highBalance = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                lowBalance++;
                highBalance++;
            }
            else if(s.charAt(i) == ')') {
                lowBalance--;
                highBalance--;
            }
            else {
                lowBalance--;
                highBalance++;
            }
            
            if(highBalance < 0) {
                return false;
            }
            
            lowBalance = Math.max(0, lowBalance);
        }
        
        //System.out.println("low balance:" + lowBalance);
        
        return lowBalance == 0;
    }
    
    public boolean checkPairChar(Stack<Character> stack, char c) {
        if(stack.empty()) {
            return false;
        }    
        
        char c1 = stack.pop();
        if(c == ')') {
            return c1 == '(';
        }
        else if(c == ']') {
            return c1 == '[';
        }
        else if(c == '}') {
            return c1 == '{';
        }
        
        return false;
    }
    
    //20. Valid Parentheses
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++) {
            // if(s.charAt(i) == '(') {
            //     stack.push('(');
            // }
            // else if(s.charAt(i) == '[') {
            //     if(!stack.empty() && (stack.peek() != '{' && stack.peek() != '[')) {
            //         return false;
            //     }
            //     stack.push('[');
            // }
            // else if(s.charAt(i) == '{') {
            //     if(!stack.empty() && stack.peek() != '{' ) {
            //         return false;
            //     }
            //     stack.push('{');
            // }
            if(s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{') {
                stack.push(s.charAt(i));
            }
                
            else {
                if(!checkPairChar(stack, s.charAt(i))) {
                    return false;
                }
            }
        }
        
        return stack.empty();
    }
    
    //32. Longest Valid Parentheses
    public static int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int validNum = 0;
        stack.push(-1);
        
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                stack.push(i);
            }
            else {
                stack.pop();
                if(stack.empty()) {
                    stack.push(i);
                }
                else {
                    validNum = Math.max(i-stack.peek(), validNum);
                }
            }
        }
    
        return validNum;
    }
    
    public static void main(String[] args) {
    	boolean ret = false;
    	ret = checkValidString("(*)");
    	ret = checkValidString("(*))");
    	ret = checkValidString("(*)))");
    	
    	System.out.println("ret:" + ret);
    	
    	
    	
    	
    	
    	
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
