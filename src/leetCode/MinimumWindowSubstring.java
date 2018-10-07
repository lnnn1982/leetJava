package leetCode;

import java.util.*;

public class MinimumWindowSubstring {

	
	
	public String minWindow(String s, String t) {
        if(s == null || t == null || s.isEmpty() || t.isEmpty()) {
            return "";
        }
        
        HashMap<Character, Integer> tMap = new HashMap<>();
        for(int i = 0; i < t.length(); i++) {
            int cnt = tMap.getOrDefault(t.charAt(i), 0) + 1;
            tMap.put(t.charAt(i), cnt);
        }
        
        HashMap<Character, Integer> windowMap = new HashMap<>();
        int l = 0, r = 0;
        
        int minStartIndex = s.length(), minEndIndex = s.length();
        int form = 0;
        while(r < s.length()) {
            if(tMap.containsKey(s.charAt(r))) {
                int curCnt = windowMap.getOrDefault(s.charAt(r), 0) + 1;
                if(curCnt == tMap.get(s.charAt(r))) {
                    form++;
                }
                windowMap.put(s.charAt(r), curCnt);
            }
            r++;
            
            while(l < r && form == tMap.size()) {
                if((r - l) < (minEndIndex - minStartIndex) || (minStartIndex - minEndIndex) == 0) {
                    minStartIndex = l;
                    minEndIndex = r;
                }

                if(tMap.containsKey(s.charAt(l))) {
                    int curCount =  windowMap.get(s.charAt(l))-1;
                    if(curCount < tMap.get(s.charAt(l))) {
                        form--;
                    }

                    windowMap.put(s.charAt(l), curCount);
                }

                l++;
            }
        }

        
        return s.substring(minStartIndex, minEndIndex);
    }
	
	
	
	
	
	
	
	
	
}
