package com.rb.util;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.*;

import org.apache.commons.lang3.StringUtils;
import org.findreplace.app.listDirFile;


public class StringProcessing {
	

public static StringBuffer bufFilter_StringBuffer(StringBuffer buf1) {     	        
int start = buf1.indexOf("[");
int end = buf1.indexOf("]", start);             	      	
while(start >= 0 && end > 0){
   buf1 = buf1.delete(start, end + 1);
   start = buf1.indexOf("[",start);
   end = buf1.indexOf("]", start);
} 
return buf1;
}  

public static StringBuffer bufFilter_StringBuffer2(StringBuffer buf1) {     	        
	int start = buf1.indexOf("(");
	int end = buf1.indexOf(")", start);             	      	
	while(start >= 0 && end > 0){
	   buf1 = buf1.delete(start, end + 1);
	   start = buf1.indexOf("(",start);
	   end = buf1.indexOf(")", start);
	} 
	return buf1;
}  


	public static String getSavedMediaLengthFormated(int savedMediaLength) {
		String timeFormated = "";
		NumberFormat format; // Format minutes:seconds with leading zeros
		format = NumberFormat.getNumberInstance();
		format.setMinimumIntegerDigits(2); // pad with 0 if necessary
		// Convert remaining milliseconds to mm:ss format and display 
		if (savedMediaLength < 0) savedMediaLength = 0; 
		int minutes = (int)(savedMediaLength/60000);
		int seconds = (int)((savedMediaLength%60000)/1000);
		timeFormated = (format.format(minutes) + ":" + format.format(seconds));
		return timeFormated;
	}
	
	  public static String findStr(String INPUT_in, String findBegin, String findEnd)
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
	  public static boolean getStr_inBE_ignorCase(StringBuffer originalText2, String beginString, String endString, String subString[], int nextPosition[], int maxLength)
	    {
	        int localPosition1 = nextPosition[0];
	        int localPosition2 = 0;
	        localPosition1 = originalText2.toString().toUpperCase().indexOf(beginString.toUpperCase(), localPosition1) + beginString.length();
	        localPosition2 = originalText2.toString().toUpperCase().indexOf(endString.toUpperCase(), localPosition1 + 1);
	        if(localPosition1 != -1 + beginString.length() && localPosition2 != -1)
	        {
	            if(localPosition2 - localPosition1 > maxLength)
	            {
	                localPosition2 = localPosition1 + 1;
	            } else
	            {
	                subString[0] = originalText2.substring(localPosition1, localPosition2);
	                nextPosition[0] = localPosition2;
	                return true;
	            }
	        } else
	        {
	            subString[0] = "--";
	        }
	        return false;
	    }

	  
	  public static StringBuffer filterGetCharOnly(String string_in){
		  StringBuffer returnStr = new StringBuffer();
		  Pattern thisP = Pattern.compile("[A-Z][a-z]*",Pattern.CASE_INSENSITIVE);
		  Matcher matcher = thisP.matcher(string_in); 
		  while(matcher.find()){
			  matcher.appendReplacement(returnStr, matcher.group(0).toUpperCase());
		  }
    //    Regex r = new Regex("[a-zA-Z]"," ");
	//	  returnStr = returnStr.replaceAll("![a-zA-Z]", " ");
		  return returnStr;
		  
	  }
	  
	  public static void main(String[] args) {
		  System.out.println(bufFilter("He[llo] there! my [na : m]e is..."));

		///  System.out.println(escapeCharNewToSpace.forAUDIOTXT("Hello there! my name is..."));
	      System.out.println(filterGetCharOnly("Hello there! my name is..."));	  
	  }
	
	  
	  public static StringBuffer hasMapKeyToStringBuffer(HashMap<String,Integer> hashMap_in)  {
	      StringBuffer outBuff = new StringBuffer();   
	      Iterator<String> stringIter = hashMap_in.keySet().iterator();  	
	      int counter = 0;
	      while( stringIter.hasNext() ) {
	    	   
	    	   if(counter == 0) {
	    		   outBuff = new StringBuffer(stringIter.next().toString());
	    	   } else {
	    		  outBuff.append(",");
	    	      outBuff.append(stringIter.next().toString());
	    	   }
	    	   ++counter;
	      }
		  return outBuff;
	   }
	  

	  
	    public static String bufFilter(String buf5)
	    {
	        StringBuffer buf4 = new StringBuffer(buf5);
	        int start5 = buf4.indexOf("[");
	        for(int end5 = buf4.indexOf("]", start5); start5 >= 0 && end5 > 0; end5 = buf4.indexOf("]", start5))
	        {
	            buf4 = buf4.delete(start5, end5 + 1);
	            start5 = buf4.indexOf("[");
	        }

	        String buf4_Str = buf4.toString();
	        buf4_Str = buf4_Str.trim();
	        return buf4_Str;
	    }
	    
	    public static int getCloserIndex(StringBuffer str_in, String search_str_in, int searchIndex_in){
	        int returnIndex = 0;
	        int index1 = 0;
	        int index2 = 0;
	        int searchIndex = searchIndex_in;
	        if(searchIndex_in < 0) {
	            searchIndex_in = 0;
	        }
	        if(searchIndex_in > str_in.length()) {
	            searchIndex_in = str_in.length();
	        }
	        if(str_in.lastIndexOf(search_str_in, searchIndex) >= 0) {
	            index2 = str_in.lastIndexOf(search_str_in, searchIndex);
	            System.out.println(" index2:"+index2);

	            returnIndex = index2;
	        }
	        if(str_in.indexOf(search_str_in, searchIndex) >= 0) {
	            index1 = str_in.indexOf(search_str_in, searchIndex);
	            System.out.println(" index1:"+index1);
	            if(index1 >= 0 && Math.abs(index1 - searchIndex) < Math.abs(searchIndex - index2)) {
	                   returnIndex = index1;
	            }
	            if(returnIndex == 0) {
	                if(index2 != 0)  {
	                   returnIndex = index2;
	                } else {
	                    returnIndex = index1;
	                }
	            }
	        }
	        return returnIndex;
	    }
	    
		   public static String getFileNameWithOutExtension(String filename_in) {
			   if(filename_in == null) return "";
			   String filename_out = filename_in;
			   if(filename_in.lastIndexOf('.') > 0) {
			      filename_out = filename_in.substring(0, filename_in.lastIndexOf('.'));
			   }
			   return filename_out;
		   }
	    
	    
	    public static locationNTime getTimeNLocation(String line) {
		    String lineLRP;
		    
        	if(line.contains("[") && line.contains("[")) {
        		int lineLeftP = line.indexOf("[");
        		int lineRightP = line.indexOf("]",lineLeftP);
        		
        		if(lineRightP > lineLeftP) {
        			lineLRP = line.substring(lineLeftP+1,lineRightP);
        			if(lineLRP.indexOf(':') < lineLRP.lastIndexOf('.')) {
        				lineLRP = lineLRP.substring(0,lineLRP.lastIndexOf('.'));
        			}
    				return new locationNTime(lineLRP,lineRightP);
        		}
        	}
        	return null;
	    }
	    
		public static String getFileName(String curdirfile_in) {
			String separator = "/";
			String curdirfile = curdirfile_in;
			if(curdirfile_in.endsWith(separator)) {
				curdirfile = curdirfile_in.substring(0, curdirfile_in.lastIndexOf(separator));
			}
	        StringBuffer curdirfileBuff = new StringBuffer(curdirfile);

			  if(curdirfile.lastIndexOf(separator) >= 0) {
				  if(!curdirfile.endsWith(separator)) {
	             	  curdirfileBuff.delete(0,curdirfile.lastIndexOf(separator)+1);
				  }
			  }
			return curdirfileBuff.toString();
		}

		
	    public static int indexOf_parseBigChunk(String INPUT, String topSearchText, String midSearchText, String bottomSearchText)
	    {

	        int bottomPosition[] = new int[1];
	        bottomPosition[0] = 1;
	        int startPosition = 1;
	        String foundText[] = new String[3];
	        foundText[0] = "";
	        foundText[1] = "";
	        foundText[2] = "";
	        startPosition = bottomPosition[0];
	        new listDirFile().parseBigChunk(new StringBuffer(INPUT), topSearchText, midSearchText, bottomSearchText, startPosition, foundText, bottomPosition);
	        return foundText[1].length();

	    }
	    private static String getTimeEnd(String resultStrTimed_p) {
			String returnTime = "";
		      String line = resultStrTimed_p;
			    String lineLRP;
			if(line.contains("(") && line.contains("(")) {
				int lineRightP = line.lastIndexOf(")");		
				int lineLeftP = line.lastIndexOf("(",lineRightP);
				if(lineRightP > lineLeftP && lineLeftP > 0) {
					lineLRP = line.substring(lineLeftP+1,lineRightP);
					if(lineLRP.indexOf(',') > 0) {
						returnTime = lineLRP.substring(lineLRP.indexOf(',')+1);
					}
				}
			}
			return returnTime;
			}
		public static float getTimeEndFloat(String resultStrTimed_p) {
			float returnFloat = 0;
			try {
			   returnFloat = Float.parseFloat(getTimeEnd(resultStrTimed_p));
			}catch (Exception e) {}
			
			return returnFloat;
		}
	    
	    
	    

		private static String getTimeStart(String resultStrTimed_p) {
		    String returnTime = "";
	        String line = resultStrTimed_p;
		    String lineLRP;
		    String lineRemind;
		if(line.contains("(") && line.contains("(")) {
			int lineLeftP = line.indexOf("(");
			int lineRightP = line.indexOf(")",lineLeftP);		
			if(lineRightP > lineLeftP) {
				lineLRP = line.substring(lineLeftP+1,lineRightP);
				if(lineLRP.indexOf(',') > 0) {
					returnTime = lineLRP.substring(0,lineLRP.indexOf(','));
				}
			}
		}
		return returnTime;
		}
		
		public static float getTimeStartFloat(String resultStrTimed_p) {
			float returnFloat = 0;
			try {
			   returnFloat = Float.parseFloat(getTimeStart(resultStrTimed_p));
			}catch (Exception e) {}
			
			return returnFloat;
		}
		
		
	    public static int indexOfAny(String textToSearch_in, char[] findThis, int startPosition) {
	        String textToSearch = textToSearch_in;
	        int returnPosition = 0;
	        if(textToSearch_in.length() > startPosition) {
	            if (startPosition >= 0)
	            {
	                textToSearch = textToSearch.substring(startPosition);
	            }
	        } else {
	            return -1;
	        }
	        
	        int position = StringUtils.indexOfAny(textToSearch, findThis);

	        if(position >= 0) {
	            position += startPosition;
	        } else {
	        	return -1;
	        }
	        return position;
	      }
	    
	    public static int indexOfAny(String textToSearch_in, String[] findThis, int startPosition) {
	        String textToSearch = textToSearch_in;
	        int returnPosition = 0;
	        if(textToSearch_in.length() > startPosition) {
	            if (startPosition >= 0)
	            {
	                textToSearch = textToSearch.substring(startPosition);
	            }
	        } else {
	            return -1;
	        }
	        
	        int position = StringUtils.indexOfAny(textToSearch, findThis);

	        if(position >= 0) {
	            position += startPosition;
	        } else {
	        	return -1;
	        }
	        return position;
	      }
	    
	    
	    public static String insertEndBeginTag(String str_in, int startPosition, String curTime)
        {
            if (str_in.length() <= startPosition+10 || startPosition < 0)
            {
          //      Console.WriteLine("skip insert");
                return str_in;
            }
           // Console.WriteLine("insert start");
                String str1 = str_in.substring(0, startPosition);
                String str2 = str_in.substring(startPosition);
                String strOut = str1 + "[END" + curTime + "]" + "[BEGIN" + curTime + "]"+str2;
                return strOut;
        }
	    
	    
}
