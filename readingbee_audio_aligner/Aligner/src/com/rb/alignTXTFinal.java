package com.rb;

import java.util.HashMap;

import com.rb.util.StringProcessing;
import com.rb.util.escapeCharNewToSpace;

// HASHMAP ITEM FOR USE IN FINAL ALIGMENT TO ORIGINAL TXT
public class alignTXTFinal {
	public String resultStr;
	public String resultStrTimed;
	public String longestWord;
	public String beginWord = "";
	public String endWord = "";	
	private String timeStart = "";
	private String timeEnd = "";

	
	public String getTimeStartStr() {
		return "["+timeStart+"]";
	}
	
	public String getTimeEndStr() {
		return "["+timeEnd+"]";
	}
    public String findStr(String INPUT_in, String findBegin, String findEnd)
    {      
        int bottomPosition[] = new int[1];
        bottomPosition[0] = 0;
        String subString[] = new String[1];
        subString[0] = "";  
        if(INPUT_in.length() > 10)
        {
            boolean foundStr = StringProcessing.getStr_inBE_ignorCase(new StringBuffer(INPUT_in), findBegin,findEnd, subString, bottomPosition, 8000);
            if(foundStr && subString[0].length() > 0) {
                return subString[0];
            } else {
            	return "";
            }        	
        } else
        {
            return "";
        }
    }
	
	public alignTXTFinal(String resultStrTimed_p){
		resultStrTimed = resultStrTimed_p.trim();		
		String separator = " ";		
		resultStr = escapeCharNewToSpace.forAUDIOTXT(findStr(resultStrTimed,"]","[END")).trim();
		
		timeStart = findStr(resultStrTimed,"[BEGIN","]").trim();
		timeEnd = findStr(resultStrTimed,"[END","]").trim();
		
		
		// fetch longestWord
		longestWord = "";
		
        HashMap<String, Integer> hmWord = new HashMap<String, Integer>();

        
        
        // dc new
        longestWord = resultStr;
        if(true) return;
       
   /*     int maxNum = 0;
        String maxNumString = "";
        

		for (final String word : resultStr.toString().split(" ")) {	
			if(word.trim().length()>1) {

			if(hmWord.get(word) == null) {
				hmWord.put(word,1);
			} else {
				hmWord.put(word, hmWord.get(word)+1);
				if(hmWord.get(word)+1 > maxNum) {
					maxNum = hmWord.get(word);
					maxNumString = word;
				} else if(hmWord.get(word)+1 == maxNum) {
					if(word.length() > maxNumString.length()) {
						maxNumString = word;
					}
				}
			}
			}
		} 
		*/
		
		for (final String word : resultStr.toString().split(" ")) {
			  if(word.trim().length() > longestWord.length()) {
				longestWord = word;				
			  }
		    }
		/*
		if(maxNum > 0) {
			System.out.println("#DUPLICATED WORD: " + maxNumString);
				longestWord = maxNumString;	
		} else {
			for (final String word : resultStr.toString().split(" ")) {
			  if(word.trim().length() > longestWord.length()) {
				longestWord = word;				
			  }
		    }
		
		}
		*/
		
		// fetch beginWord
		if(resultStr.length() > 0 && resultStr.indexOf(separator) > 0) {
			beginWord = resultStr.substring(0,resultStr.indexOf(separator)).trim();		  			
	    }
		if(beginWord == null || beginWord.trim().equals(longestWord.trim())) {

			   beginWord = "";
			
		}
		
		// fetch endWord
		if(resultStr.length() > 0 && resultStr.lastIndexOf(separator) > 0) {
		    String curdirfile = resultStr;
		        if(resultStr.endsWith(separator)) {
			    curdirfile = resultStr.substring(0, resultStr.lastIndexOf(separator));
		    }
	       StringBuffer curdirfileBuff = new StringBuffer(curdirfile);
			  if(curdirfile.lastIndexOf(separator) >= 0) {
				  if(!curdirfile.endsWith(separator)) {
	             	  curdirfileBuff.delete(0,curdirfile.lastIndexOf(separator)+1);
				  }
			  }
			endWord = curdirfileBuff.toString();
			if(endWord.length() < 10) {

					endWord = endWord.trim();
				
			}
		}
		if(endWord == null || endWord.trim().equals(longestWord.trim())) {

			    endWord = "";
			
		}	
	}
	
}
