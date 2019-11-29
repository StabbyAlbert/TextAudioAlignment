package com.rb;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedMap;
import org.findreplace.app.listDirFile;
import com.rb.util.NewFindReplace;
import com.rb.util.StringProcessing;
import com.rb.util.globalConfig;

/*
 * Base class for AlignMain
 */
public class AlignMainBase {
	public static SortedMap<Integer, stringMapItem> hm;
	public static SortedMap<Integer, stringMapItem> hmFound;
	public static final int averageCharReadPerSec = 9;
	public static final int maxCharLengthExceed = 30;
	public static final int numCharsToSkip = 8;
	private static int recongnizerToleratedTimeDiff = 300;  // sec
	
	/*
	 * 1) Takes in a) text file content INPUT_IN to match against hashmap - hm;  
	 *             b) wavCutSecCumulative - parameter indicating how many seconds was cut off from the 
	 *             audio file segment.  This is used to add to number second found to result in actual time
	 * 2) Return the following string to the caller:
	 *                 foundText[0] - String just matched by matchToOrigTXT 
	 *                 foundText[1] - String found previously + [BEGIN TIME]foundText[0][END TIME] -> Save time stamped
	 *                 foundText[2] - String not yet processed -> to be saved as next segment file                         
	 *                 
	 */                
	  protected static StringBuffer matchToOrigTxt(String INPUT_IN, String foundText[], double wavCutSecCumulative, boolean useOldFindReplace)
	    { 
    	  System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&NEW MATCH TO ORIGTXT&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			int segmentTXTLength = 0;
            boolean isFirstTime = true;
		    listDirFile ldf = new listDirFile();
//StringBuffer fileContentBuff = new StringBuffer(INPUT_IN.toUpperCase()); //This line no longer needed due to filter and case insensitive search methods
		    StringBuffer fileContentBuff = new StringBuffer(INPUT_IN);
		    StringBuffer fileContentBuffPre = fileContentBuff;
	        String topSearchText = "";
	        String midSearchText = "";
	        String bottomSearchText = "";
	        String replacementText = "";
	        String foundTextPre[] = new String[3];
	        foundText[0] = "";  // return found string
	        foundText[1] = "";  // return previous string before found string
	        foundText[2] = "";  // return next string after found string 
	        int startPosition = 0;
	        int startPositionPre = 0;
	        int numReplaced = 0;
	        int bottomPosition[] = new int[1];
            Integer sMIKeyPre = 0;
            int estimatedMatchStrLengthtoStart = 0;
	        bottomPosition[0] = 0;
	        float endTime = (float) 0.0;
	        float endTimePre = (float) 0.0;
	        String resultStr = "";
	        String resultStrPre = resultStr;	        
	        try
	        {
	  	      Iterator<Integer> hmIter = hm.keySet().iterator(); 
	  	      stringMapItem sMI = null;
		      if(hmIter.hasNext() ) {
		  	      Integer sMIKey = hmIter.next();
		  	      sMIKeyPre = sMIKey;
		    	  sMI = hm.get(sMIKey);
		    	  if(sMI.beginWord != null) {
		    		  topSearchText = sMI.beginWord.toUpperCase();
		    	//	  System.out.println(" Searching b.."+topSearchText);		    		  
		    	  }
		    	  if(sMI.longestWord != null) {
		    		  midSearchText = sMI.longestWord.toUpperCase();
		    	//	  System.out.println(" Searching.."+midSearchText);
		    	  }
		    	  if(sMI.endWord != null) {
		    		  bottomSearchText = sMI.endWord.toUpperCase();
		    	//	  System.out.println(" Searching e..."+bottomSearchText);
		    	  }
		    	  boolean parseOK = true;

	              while(startPosition >= 0 && parseOK) 
	              {
	            	  estimatedMatchStrLengthtoStart = sMI.estimatedMatchStrLengthtoStart;	            	  
	            	// System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&estimatedMatchStrLengthtoStart&"+estimatedMatchStrLengthtoStart);
			    	// estimatedMatchStrLengthtoStart = sMI.resultStr.trim().length() - sMI.endWord.length() - 1;
	            	  if(midSearchText.contains(" ") && midSearchText.length() > 15 && !useOldFindReplace) {
		            	 parseOK = NewFindReplace.parseBigChunkIgnorSpace(fileContentBuff, topSearchText, midSearchText, bottomSearchText, startPosition, foundText, bottomPosition);		            	 
	                	// parseOK = ldf.parseBigChunk(true,fileContentBuff, topSearchText, midSearchText, bottomSearchText, startPosition, foundText, bottomPosition,estimatedMatchStrLengthtoStart,sMI.estimatedLongestWordPosition);	            	  
	            	  } else {
	            	     parseOK = ldf.parseBigChunk(true,fileContentBuff, topSearchText, midSearchText, bottomSearchText, startPosition, foundText, bottomPosition,estimatedMatchStrLengthtoStart,sMI.estimatedLongestWordPosition);
	            	  }
	            	 if(parseOK) {	            	  
	                   fileContentBuff = new StringBuffer(foundText[1]);
	                   replacementText = "";
	            	   if(sMI.getTimeStart() >= 0) {
	            	/*	   if(isFirstTime && foundText[0].length() < 100) {
	   	         	         hmFoundInOrigTXT.put(hmFoundInOrigTXTCounter++, new locationNTimeInTXT(txtCutLengthCumulative+foundText[1].length(),sMI.getTimeStart()*1000.0+wavCutSecCumulative));
	            		   } else if(!isFirstTime ) {
		   	         	         hmFoundInOrigTXT.put(hmFoundInOrigTXTCounter++, new locationNTimeInTXT(txtCutLengthCumulative+foundText[1].length(),sMI.getTimeStart()*1000.0+wavCutSecCumulative));	            			   
	            		   }
	            	*/
		            	     replacementText += "[BEGIN"+StringProcessing.getSavedMediaLengthFormated((int) (sMI.getTimeStart()*1000.0+wavCutSecCumulative))+"]";
		               }
	            	   SortedMap<Integer, Integer> sentenceWordNumNPostion;
	            	   
	            	  if(foundText[0].length() > 30) {
	       	    		   System.out.println("MATCH START!!!!! ");
	       	    		   System.out.println("MATCH START!!!!! ");

	            		  sentenceWordNumNPostion = NewFindReplace.getSentenceWordNumNPosition(foundText[0]);
	       	    		   System.out.println("MATCH end!!!!! ");
System.out.flush();
	            		  
	            		  System.out.println("MATCH COMPLETE!!!!! "+sentenceWordNumNPostion.size());

	            		  Iterator<Integer> keyIter = sentenceWordNumNPostion.keySet().iterator(); 
	            		  Integer currentKey = 0;
	            	      int bestMatchKey = 0;
	            	       while( keyIter.hasNext() ) {
	            	    	   currentKey = keyIter.next();
	            	    	   if((sentenceWordNumNPostion.get(currentKey)) == null) {
	            	    		   break;
	            	    	   }
	            	    	   try {
	            	    		   System.out.println("INSERTING SENTENCE: "+currentKey);
	            	    	  
	            	    	      foundText[0] = StringProcessing.insertEndBeginTag(foundText[0], sentenceWordNumNPostion.get(currentKey), StringProcessing.getSavedMediaLengthFormated((int)(sMI.hmWorldNumNTime.get(currentKey)*1000+wavCutSecCumulative)));
	            	    	   } catch (Exception e3){e3.printStackTrace();}
  
	            	       }   
	            	  }
	            	  replacementText += foundText[0];
	            	  
	            	  
	            	  
	            	  if(sMI.getTimeStart() >= 0) {
		            	     replacementText += "[END"+StringProcessing.getSavedMediaLengthFormated((int) (sMI.getTimeEnd()*1000.0+wavCutSecCumulative))+"]";
		              }
	            	  
	            	//  System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+replacementText);
	            	fileContentBuff.append(replacementText);
	                fileContentBuff.append(foundText[2]);    
	                   //    foundText[1] = string before replacment string ~ string skiped
	                   // startPosition = next position for searching
	                int charLengthSkiped = getCharcterSkiped(foundText[1]);
	                int charLengthExceeded = foundText[0].length() - sMI.resultStr.length();
             //   	System.out.println("*************TIME PRE: "+endTimePre + " TIME BEGIN: "+sMI.getTimeStart()+" TIME END: "+sMI.getTimeEnd());
                    endTime=sMI.getTimeEnd();
				  	resultStr = sMI.resultStr;
	                float timeSkiped = sMI.getTimeStart() - endTimePre;
          //      	System.out.println("********BW:"+sMI.beginWord+"**EW:"+sMI.endWord+"***USED longest: "+sMI.longestWord+ " replacementText: "+replacementText+"***DONE");
//                	System.out.println("*************USED result needed: "+sMI.resultStr);
	              //  System.out.println("*************Found1: "+foundText[0] + " Processed: "+foundText[1]);

	           //     System.out.println("*************CHAR SKIPED1: "+charLengthSkiped + " Time Skiped: "+timeSkiped);

	                if(charLengthSkiped < numCharsToSkip && timeSkiped >= 0 && charLengthExceeded < maxCharLengthExceed) {  // takes [BEGIN: XX] and [END: XX] SIZE INTO CONSIDERATION
	     	             System.out.println("*************CHAR SKIPED1: "+charLengthSkiped + " Time Skiped: "+timeSkiped+" charLengthExceeded:"+charLengthExceeded);
	                   	 if(sMI.getTimeStart() - endTimePre > recongnizerToleratedTimeDiff) {
	                		 hmFound.get(sMIKeyPre).nextItemOutOfSync = true;
	                		  return fileContentBuff;	                		 
	                	 }
	                	 
	                	stringMapItem sMINew = new stringMapItem(sMI.resultStr,sMI.resultStrTimed,sMI.isFirstSentence,sMI.timeStart,sMI.timeEndFirst,sMI.timeEnd,sMI.estimatedMatchStrLengthtoStart,sMI.beginWord,sMI.longestWord,sMI.endWord);
		                sMINew.beginWordBeginPosition = foundText[1].length();
		                sMINew.endWorldEndPosition = foundText[1].length() + replacementText.length();
				  	    sMIKeyPre = sMIKey;
		                hmFound.put(sMIKey, sMINew);	    

		          	
		                
	                } else {
	                	
	                	System.out.println("*************CHAR SKIPED2: "+charLengthSkiped + " Time Skiped: "+timeSkiped);
	                //	System.out.println("**r:"+resultStr+"*****rp:"+resultStrPre+"******FOUNDTEXT1: "+foundText[1]);

	                	if(endTimePre > 0 && (!isOK_checkSkipedCharLengthWithAmountOfTimeUsed(charLengthSkiped, timeSkiped) || charLengthExceeded >= maxCharLengthExceed) || sMI.getTimeStart() - endTimePre > recongnizerToleratedTimeDiff || resultStr.trim().equals(resultStrPre.trim())) {
	                			System.out.println("Return NEW 1");	
	                			sMI = hmFound.get(sMIKeyPre);
	                			 replacementText = "";
	      	            	   if(sMI.getTimeStart() >= 0) {
	      		            	     replacementText += "[BEGIN"+StringProcessing.getSavedMediaLengthFormated((int) (sMI.getTimeStart()*1000.0+wavCutSecCumulative))+"]";
	      		               }
	      	            	  replacementText += foundTextPre[0];
	      	            	  if(sMI.getTimeStart() >= 0) {
	      		            	     replacementText += "[END"+StringProcessing.getSavedMediaLengthFormated((int) (sMI.getTimeEnd()*1000.0+wavCutSecCumulative))+"]";
	      		              }	         
	                			
						        foundText[0] = foundTextPre[0];  // return found string
						        
	   	         	         //    hmFoundInOrigTXT.put(hmFoundInOrigTXTCounter++, new locationNTimeInTXT(txtCutLengthCumulative+foundText[0].length(),sMI.getTimeStart()*1000.0+wavCutSecCumulative));	            	  	            		   						        
						        foundText[1] = foundTextPre[1]+replacementText;  // return previous string after found string

						        foundText[2] = foundTextPre[2];  // return next string after found string 
						   //     startPosition = startPositionPre;	                			
	                			hmFound.get(sMIKeyPre).nextItemOutOfSync = true;
	  	                	  return fileContentBuff;	                			
	                		}

	                		
	                		/* REDUNDENT TAKE OUT
	                		// TO DO: NEED TO PROVIDE A BETTER FORMULAR FOR CALCULATING WHEN TO EXIT 
	                		int misedWordLength = foundText[1].length();
	                		if(foundText[1].length() - startPosition > 6) {
		                		if(endTimePre > 0 && sMI.getTimeStart()*9 < endTimePre*9+misedWordLength) {
		                			System.out.println("Return2");

		                			sMI = hmFound.get(sMIKeyPre);
		                			 replacementText = "";
		      	            	   if(sMI.getTimeStart() > 0) {
		      		            	     replacementText += "[BEGIN: "+StringProcessing.getSavedMediaLengthFormated((int) (sMI.getTimeStart()*1000.0+wavCutSecCumulative))+"]";
		      		               }
		      	            	  replacementText += foundTextPre[0];
		      	            	  if(sMI.getTimeEnd() > 0) {
		      		            	     replacementText += "[END"+StringProcessing.getSavedMediaLengthFormated((int) (sMI.getTimeEnd()*1000.0+wavCutSecCumulative))+"]";
		      		              }	         
		                			
				                foundText[0] = foundTextPre[0];  // return found string					        
						        foundText[1] = foundTextPre[1]+replacementText;  // return previous string after found string
	   	         	    //         hmFoundInOrigTXT.put(hmFoundInOrigTXTCounter3++, new locationNTimeInTXT(txtCutLengthCumulative,sMI.getTimeStart()*1000.0+wavCutSecCumulative));		      	            		   

						        foundText[2] = foundTextPre[2];  // return next string after found string 
							        startPosition = startPositionPre;
		                			hmFound.get(sMIKeyPre).nextItemOutOfSync = true;

		                			return fileContentBuff;	                			
		                		}
		                		
		                	}*/
	                		
	               /* 	 if(sMI.getTimeStart() - endTimePre > recongnizerToleratedTimeDiff) {
						        foundText[0] = foundTextPre[0];  // return found string
						        
		   	         	         //    hmFoundInOrigTXT.put(hmFoundInOrigTXTCounter++, new locationNTimeInTXT(txtCutLengthCumulative+foundText[0].length(),sMI.getTimeStart()*1000.0+wavCutSecCumulative));	            	  	            		   						        
							        foundText[1] = foundTextPre[1];  // return previous string after found string

							        foundText[2] = foundTextPre[2];  // return next string after found string 
	                		        
	                		 hmFound.get(sMIKeyPre).nextItemOutOfSync = true;
	                		 
	                		  return fileContentBuffPre;
	                		 
	                	 }
	                	 */
	                	
			                stringMapItem sMINew = new stringMapItem(sMI.resultStr,sMI.resultStrTimed,sMI.isFirstSentence,sMI.timeStart,sMI.timeEndFirst,sMI.timeEnd,sMI.estimatedMatchStrLengthtoStart,sMI.beginWord,sMI.longestWord,sMI.endWord);
			                sMINew.beginWordBeginPosition = foundText[1].length();
			                sMINew.endWorldEndPosition = foundText[1].length() + replacementText.length();
					  	    sMIKeyPre = sMIKey;
			                hmFound.put(sMIKey, sMINew);

					        foundTextPre[0] = foundText[0];  // return found string
					        foundTextPre[1] = foundText[1];  // return previous string after found string
					        foundTextPre[2] = foundText[2];  // return next string after found string 
					        fileContentBuffPre = fileContentBuff;
					        startPositionPre = startPosition;
					        // ok
						  	endTimePre = endTime;
						  	resultStrPre = resultStr;

	                	}
	                startPosition = foundText[1].length() + replacementText.length(); 	                
	                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%newSTARTPOSITION:"+startPosition);
	             //   System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%newfileContentBuff:"+ fileContentBuff);
	                isFirstTime = false;
	                numReplaced++;
	            	  } else if(hmIter.hasNext()){
	            		  
	            		  // if nothing found use previous value to find the next item in MAP
	            		  parseOK = true;
					        foundText[0] = foundTextPre[0];  // return found string
					        foundText[1] = foundTextPre[1];  // return previous string after found string
					        foundText[2] = foundTextPre[2];  // return next string after found string 
					        fileContentBuffPre = fileContentBuff;
					        startPosition = startPositionPre;
						  	endTime = endTimePre;
						  	  resultStrPre = resultStr;

	            	  } 
	  		        if(hmIter.hasNext() ) {
	  		          sMIKey = hmIter.next();
			    	  sMI = hm.get(sMIKey);
			    	  
			    	  
			         if(sMI.resultStr.equals(resultStr) || globalConfig.exitAfterOneLoop) {
					    foundText[1] = foundText[1]+replacementText;  // return previous string after found string
			        	 hmFound.get(Collections.max(hmFound.keySet())).nextItemOutOfSync=true;
	            	//	hmFound.get(sMIKeyPre).nextItemOutOfSync = true;
		                	  return fileContentBuff;
	  		           }
	  		           
	  		           
			    	  
			    	  if(sMI.beginWord != null) {
			    		  topSearchText = sMI.beginWord.toUpperCase();
			    	//	  System.out.println(sMIKey+" Searching b..."+topSearchText);  
			    	  }
			    	  if(sMI.longestWord != null) {
			    		  midSearchText = sMI.longestWord.toUpperCase();
			    		//  System.out.println(" Searching..."+midSearchText);
			    	  } else continue;
			    	  if(sMI.endWord != null) {
			    		  bottomSearchText = sMI.endWord.toUpperCase();
			    	//	  System.out.println(" Searching e..."+bottomSearchText);
			    	  }
				        foundTextPre[0] = foundText[0];  // return found string
				        foundTextPre[1] = foundText[1];  // return previous string after found string
				        foundTextPre[2] = foundText[2];  // return next string after found string 
				        fileContentBuffPre = fileContentBuff;
				        startPositionPre = startPosition;
				        // ok2
				        endTimePre = endTime;
					  	  resultStrPre = resultStr;

	  		        } else {
	  		        	hmFound.get(Collections.max(hmFound.keySet())).nextItemOutOfSync=true;
	  		        	foundText[1] = foundText[1]+replacementText;  // return previous string after found string
	  		        	
	  		        	break;}
	                
	              }
	     //         System.out.println("Last thing found: "+foundText[0]);
	          }
	        }// end if;
	        catch(Exception iex)
	        {
	        	iex.printStackTrace();
	         //   System.out.println("File input output exception");
	        }
	        return fileContentBuff;
	    }
	  
	  
	  // return false if charcter is within range
	  public static boolean isOK_checkSkipedCharLengthWithAmountOfTimeUsed(int lengthSkiped, float secTimeUsed){	  
		  if(secTimeUsed*(averageCharReadPerSec-2) < lengthSkiped && secTimeUsed*(averageCharReadPerSec+2) > lengthSkiped) {
			  return true;
		  }
		  return false; 
	  }
	  
		private static int getCharcterSkiped(String foundText1_p) {
			int returnInt = 0;
			if(foundText1_p.length() > 0) {
				if(foundText1_p.lastIndexOf("]") > 0) {
				   returnInt = foundText1_p.substring(foundText1_p.lastIndexOf("]")).length();
				} else {
					returnInt = 0;
				}
				if(returnInt < 0) {
					returnInt = 0;
				}
			}
			return returnInt;			
		}

}
