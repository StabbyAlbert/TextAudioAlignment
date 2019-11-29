package com.rb;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import org.findreplace.app.listDirFile;
import com.rb.util.NewFindReplace;
import com.rb.util.globalConfig;
import com.rb.util.audio.FileProcessing;

public class AlignTXTFinalStep {
//	private static final boolean skipFirstMatch = globalConfig.skipFirstMatch; 
	
	
	public static void main(String[] args) {

	     String foundText[] = new String[3];
	     foundText[0] = "";  // return found string
	     foundText[1] = "";  // return previous string before found string
	     foundText[2] = "";  // return next string after found string 
		SortedMap<Integer, alignTXTFinal> hm = null;
		
		hm = getHMInProcessedOrigTXT("C:/TEMP/test/seg/a5/d8_8_InsTime1.txt");
		AlignOrigFileToHashMap(foundText,"C:/TEMP/test/seg/a5/d8_8_InsTime1.txt",hm);
	
	}
	
	public static SortedMap<Integer, alignTXTFinal> getHMInProcessedOrigTXT(String fileNameIn_p){
	//	SortedMap<Integer, alignTXTFinal> hm = new TreeMap<Integer, alignTXTFinal>(java.util.Collections.reverseOrder());
		SortedMap<Integer, alignTXTFinal> hm = new TreeMap<Integer, alignTXTFinal>();

		        listDirFile ldf = new listDirFile();
		        StringBuffer fileContentBuff = FileProcessing.fileToStringBufferBr(fileNameIn_p);
		        String topSearchText = "[BEGIN";
		        String midSearchText = "[END";
		        String bottomSearchText = "]";
		        String replacementText = "";
		        String foundTextPre[] = new String[3];
		        String foundText[] = new String[3];
		        foundText[0] = "";  // return found string
		        foundText[1] = "";  // return previous string before found string
		        foundText[2] = "";  // return next string after found string 
		        int startPosition = 0;
		        int startPositionPre = 0;
		        int numReplaced = 0;
		        int bottomPosition[] = new int[1];
	            Integer sMIKeyPre = 0;
	            int estimatedMatchStrLength = 0;
	            int hmCounter = 0;

		        bottomPosition[0] = 0;
	            float endTimePre = (float) 0.0;
		        try
		        {
			    	  boolean parseOK = true;
		              while(startPosition >= 0 && parseOK) 
		              {		            	  
		            	 parseOK = ldf.parseBigChunk(fileContentBuff, topSearchText, midSearchText, bottomSearchText, startPosition, foundText, bottomPosition);
		            	 if(parseOK) {	            	  
		                   fileContentBuff = new StringBuffer(foundText[1]);
		                   replacementText = "";          	   
		            	   replacementText += foundText[0];		            	   
		            	   hm.put(hmCounter, new alignTXTFinal(replacementText));
		            	   hmCounter++;
		            	   fileContentBuff.append(replacementText);
		                   fileContentBuff.append(foundText[2]);
		                   startPosition = foundText[1].length() + replacementText.length(); 
		            //	   System.out.println(" Found0:"+replacementText +" NextStart:"+startPosition);
		                   numReplaced++;
		            	  }		                
		              }
    //  System.out.println("Last thing found: "+foundText[0]);
		          
		        }// end if;
		        catch(Exception iex)
		        {
		            System.out.println("File input output exception");
		        }
		        return hm;
}

		
		
	
	

	public static StringBuffer AlignOrigFileToHashMap(String foundText[],String fileNameIn_p, SortedMap<Integer, alignTXTFinal> hmFoundInOrigTXT) {
		/*   Iterator<Integer> hmIter = hmFoundInOrigTXT.keySet().iterator(); 
		   alignTXTFinal aTF = null;		   
		   while(hmIter.hasNext() ) {
		  	      Integer aTFKey = hmIter.next();
		  	      aTF =  hmFoundInOrigTXT.get(aTFKey);
		          System.out.println(aTFKey+ "-HM result: "+aTF.getTimeEndStr()+aTF.resultStr);
		          System.out.println(" OK "+aTF.longestWord);
		   }
		  */ 
			int segmentTXTLength = 0;
		//	boolean firstMatch = skipFirstMatch;
		    listDirFile ldf = new listDirFile();
		    StringBuffer fileContentBuff = FileProcessing.fileToStringBufferBr(fileNameIn_p);
	        String topSearchText = "";
	        String midSearchText = "";
	        String bottomSearchText = "";
	        String replacementText = "";
	      //  String foundText[] = new String[3];
	     //   foundText[0] = "";  // return found string
	     //   foundText[1] = "";  // return previous string before found string
	     //   foundText[2] = "";  // return next string after found string 
	        int startPosition = 0;
	        int startPositionPre = 0;
	        int numReplaced = 0;
	        int bottomPosition[] = new int[1];
            int estimatedMatchStrLengthtoStart = 0;
	        bottomPosition[0] = 0;
	        float endTime = (float) 0.0;
	        try
	        {
	  	      Iterator<Integer> hmIter = hmFoundInOrigTXT.keySet().iterator(); 
	  	      alignTXTFinal aTF = null;
		      if(hmIter.hasNext() ) {
		  	      Integer aTFKey = hmIter.next();
		  	      aTF = hmFoundInOrigTXT.get(aTFKey);

		  	      
		  	  //  System.out.println(" *************************ATF HMFOUND:"+aTF.getTimeStartStr());	
		  	      
		    	  if(aTF.beginWord != null) {
		    		  topSearchText = aTF.beginWord.toUpperCase();
		    	//	  System.out.println(" Searching b.."+topSearchText);		    		  
		    	  }
		    	  if(aTF.longestWord != null) {
		    		  midSearchText = aTF.longestWord.toUpperCase();
		    	//	  System.out.println(" Searching.."+midSearchText);
		    	  }
		    	  if(aTF.endWord != null) {
		    		  bottomSearchText = aTF.endWord.toUpperCase();
		    	//	  System.out.println(" Searching e..."+bottomSearchText);		    		  
		    	  }
		    	  boolean parseOK = true;
	             while(startPosition >= 0 && (parseOK || hmIter.hasNext())) 
		  //  	  while(startPosition >= 0 && (parseOK)) 
	              {
			    	 estimatedMatchStrLengthtoStart = aTF.resultStr.trim().length() - aTF.endWord.length() - 1;
			    	 System.out.println("##STARTPOSITION: "+startPosition);
			    	 
			    	 
	            	//dc new parseOK = ldf.parseBigChunk(false,fileContentBuff, topSearchText, midSearchText, bottomSearchText, startPosition, foundText, bottomPosition,estimatedMatchStrLengthtoStart,0);
	            	 parseOK = NewFindReplace.parseBigChunkIgnorSpace(fileContentBuff, topSearchText, midSearchText, bottomSearchText, startPosition, foundText, bottomPosition);		            	 

	        //    	 System.out.println("BW: "+aTF.beginWord+" LW:"+aTF.longestWord+" EW:"+aTF.endWord);
	           // 	 System.out.println("resultstr: "+aTF.resultStr);
          		//   System.out.println(" ************a**"+firstMatch);

	            	 if(parseOK) {	            	  
	                   fileContentBuff = new StringBuffer(foundText[1]);
	                   replacementText = "";
	            	   if(aTF.getTimeStartStr().length() > 0) {
	            	//	   System.out.println(" ***********b***"+firstMatch);
	            		   if(globalConfig.skipFirstMatch == false) {	            			  
	            			   if (!(foundText[0].indexOf("[") >= 0 && foundText[0].indexOf("]") >= 0)) {
	            				   replacementText += aTF.getTimeStartStr();   
	            			   }		            	        
	            		   }
	            		   globalConfig.skipFirstMatch  = false;
		            	     if(foundText[0].length() < aTF.resultStr.length()+500) {
		     	            	replacementText += foundText[0];		            	    	 

		            	     } else {
		            	    	 replacementText = foundText[0];
		            	     }
		               }
	   		  	//    System.out.println(" *************************ATF HMFOUND:"+aTF.getTimeStartStr()+"::"+replacementText);	

	            	fileContentBuff.append(replacementText);
	 	            startPosition = fileContentBuff.length()-1;
//System.out.println("**FILECONENT1:"+fileContentBuff);
	            	fileContentBuff.append(foundText[2]);
	            //	System.out.println("**FILECONENT2:"+fileContentBuff);

	            	numReplaced++;	           
	            	 }
	  		        if(hmIter.hasNext() ) {
	  		          aTFKey = hmIter.next();
			    	  aTF = hmFoundInOrigTXT.get(aTFKey);
			    	  if(aTF.beginWord != null) {
			    		  topSearchText = aTF.beginWord.toUpperCase();
			    	//	  System.out.println(sMIKey+" Searching b..."+topSearchText);			    		  
			    	  }
			    	  if(aTF.longestWord != null) {
			    		  midSearchText = aTF.longestWord.toUpperCase();
			    	//	  System.out.println(" Searching..."+midSearchText);

			    	  } else continue;
			    	  if(aTF.endWord != null) {
			    		  bottomSearchText = aTF.endWord.toUpperCase();
			    	//	  System.out.println(" Searching e..."+bottomSearchText);
			    	  }			    	 			    	 
	  		        } else {break;}
	                
	              
	     //         System.out.println("Last thing found: "+foundText[0]);
	          } // while
		      } // end if
	        } catch(Exception iex){
	        	iex.printStackTrace();
	        }
	        return fileContentBuff;
		      
		      
     }
}