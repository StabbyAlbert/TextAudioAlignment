package com.rb;

import java.util.HashMap;

import com.rb.util.StringProcessing;
import com.rb.util.globalConfig;

public class stringMapItem {
	public static final int toleratedSkip = globalConfig.toleratedSkip;
	
//	private static final int toleratedTimeFastFoward = 2;
//	private static final double speechSpeedCharPerSec = 14;
	
	private static final int toleratedTimeFastFoward = globalConfig.toleratedTimeFastFoward;
	private static final double speechSpeedCharPerSec = globalConfig.speechSpeedCharPerSec;
    private static final double timeDiffMin = 20.5;
	
	public String resultStr;
	public String resultStrTimed;
	public String longestWord;
	public int estimatedLongestWordPosition = 0;

	public int estimatedMatchStrLengthtoStart = 0;
	
	public String beginWord = "";
	public String endWord = "";	
	public String timeStart = "";
	public String timeEndFirst = "";
	public String timeEnd = "";
	public int beginWordBeginPosition = 0;
	public int endWorldEndPosition = 0;
	public boolean nextItemOutOfSync = false;
	public String resultStrOld;
	public String resultStrTimedOld;
	public String longestWordOld;
	public String beginWordOld = "";
	public String endWordOld = "";	
	private String timeStartOld = "";
	private String timeEndFirstOld = "";
	private String timeEndOld = "";
	public int beginWordBeginPositionOld = 0;
	public int endWorldEndPositionOld = 0;
	
	public HashMap<Integer,Float> hmWorldNumNTime = new HashMap<Integer,Float>();
	
	
	boolean isFirstSentence = false;
	public int estimatedMatchStrLengthtoStart_for_Diff_Sentence() {
		return (int)((getTimeEnd() - getTimeStart())*speechSpeedCharPerSec);
	}

	public float getTimeStart() {
		float returnFloat = 0;
		try {
		   returnFloat = Float.parseFloat(timeStart);
		}catch (Exception e) {}
		
		return returnFloat;
	}

	public float getTimeEndFirst() {
	float returnFloat = 0;
		try {
		   returnFloat = Float.parseFloat(timeEndFirst);
		}catch (Exception e) {}
		
		return returnFloat;
	}
	
	public float getTimeEnd() {
		float returnFloat = 0;
		try {
		   returnFloat = Float.parseFloat(timeEnd);
		}catch (Exception e) {}
		
		return returnFloat;
	}
	
	
	public static void main(String args[]) {
	//	String resultA = "the latins have bequeathed";
		String resultB = "the(0.61,0.81) latins(0.81,1.42) have(1.42,1.66) bequeathed(1.66,20.12)";
		
	//	System.out.println("ok"+resultB.replaceAll("[^\\ ]","")+"THERE");
		//stringMapItem sMI = new stringMapItem(resultA,resultB);
		//System.out.println("Begin: "+sMI.beginWord+" longest: "+sMI.longestWord+" End: "+sMI.endWord);
		//System.out.println("TimeStart: "+sMI.timeStart+" TInt: "+sMI.getTimeStart()+" TimeEndFirst: "+sMI.getTimeEndFirst(resultB)+" TimeEnd: "+sMI.timeEnd);
		
	  // String replacementText = "["+StringProcessing.getSavedMediaLengthFormated((int)(sMI.getTimeStart()*1000.0))+"]";
//System.out.println(replacementText);
	//	getExtraLengthNeeded(resultB);
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
	

	private static String getTimeEndFirst(String resultStrTimed_p) {
		String returnTime = "";
	      String line = resultStrTimed_p;
		    String lineLRP;
		    String lineRemind;
		if(line.contains("(") && line.contains("(")) {
			int lineRightP = line.lastIndexOf(")");		
			int lineLeftP = line.lastIndexOf("(",lineRightP);
			if(lineRightP > lineLeftP && lineLeftP > 0) {
				lineLRP = line.substring(lineLeftP+1,lineRightP);
				if(lineLRP.indexOf(',') > 0) {
					returnTime = lineLRP.substring(0,lineLRP.indexOf(','));
				}
			}
		}
		return returnTime;
		}
	
	private static String getTimeEnd(String resultStrTimed_p) {
		String returnTime = "";
	      String line = resultStrTimed_p;
		    String lineLRP;
		    String lineRemind;
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
	

	//COPY NEW ONE
	public  stringMapItem(String resultStr_p, String resultStrTimed_p, boolean isFirstSentence_p, String timeStart_p, String timeEndFirst_p,String timeEnd_p, int estimatedMatchStrLengthtoStart_p, String beginWord_p, String longestWord_p, String endWord_p){
		isFirstSentence = isFirstSentence_p;
		resultStr = resultStr_p.trim();
		resultStrTimed = resultStrTimed_p.trim();			
		timeStart = timeStart_p;
		timeEndFirst = timeEndFirst_p; 
		timeEnd = timeEnd_p;		
		estimatedMatchStrLengthtoStart = estimatedMatchStrLengthtoStart_p;
		beginWord = beginWord_p;
		longestWord = longestWord_p;
		endWord = endWord_p;
	}
	
	public  stringMapItem(String resultStr_p, String resultStrTimed_p, boolean isFirstSentence_p, String originalText2, boolean useOldFindReplace){
		isFirstSentence = isFirstSentence_p;
		//resultStr = resultStr_p;
		//resultStr = StringProcessing.bufFilter_StringBuffer2(new StringBuffer(resultStrTimed_p)).toString().trim();
		resultStrTimed = resultStrTimed_p.trim();
        int wordNum = 0;
        Float outFOld=new Float(-1.0);;
		  Float outF = new Float(-1.0);
		  StringBuilder sbStr = new StringBuilder();
		  System.out.println("WORD1 ");

		for (final String word : resultStrTimed.toString().split(" ")) {
			  if(word.trim().length() > 0) {
				  System.out.println("WORD2: "+word.trim());
				  wordNum++;
				  outFOld = outF;
				  outF = new Float(-1.0);
				  try{
					  outF = Float.parseFloat(getTimeEnd(word));
				  } catch (Exception e) {};
				  
				  if(outF > 0) {
					  if (!(outFOld > 0 && (outF - outFOld) > 5)) {
						  sbStr.append(word).append(" ");

						     hmWorldNumNTime.put(wordNum,outF);
					  } else {
						  break;
					  }
				  }
				
			  }
		    }	
		  System.out.println("WORD2 ");

		if(sbStr.length() > 10) {
		   resultStrTimed = sbStr.toString().trim();

		} 
		resultStr = StringProcessing.bufFilter_StringBuffer2(new StringBuffer(resultStrTimed)).toString().trim();

		String separator = " ";
		timeStart = getTimeStart(resultStrTimed);
		timeEndFirst = getTimeEndFirst(resultStrTimed); 
		timeEnd = getTimeEnd(resultStrTimed);
		// fetch longestWord
		longestWord = "";
	//	System.out.println("@@@@@@@@@@@@@@@@@@");
		
	    float time1f = 0;
	    float time2f = 0;
		time1f = Float.parseFloat(timeEndFirst);
		time2f = Float.parseFloat(timeEnd);
		if(useOldFindReplace) {
			longestWord = " ";
			beginWord = " ";
			endWord = "  ";			
			estimatedMatchStrLengthtoStart = estimatedMatchStrLengthtoStart_for_Diff_Sentence();
		//	System.out.println("using... useOldFindReplace");
			return;
		} 
		
		System.out.println("NOT... useOldFindReplace");
		if(time2f-time1f < timeDiffMin) {
			int numWords = 0;
			for (final String word : resultStr.toString().split(" ")) {
				  numWords++;
			}
			if(numWords > 4 && !useOldFindReplace) {
				longestWord = resultStr;
				beginWord = "";
				endWord = "";				
				return;
			}
		}


		
        HashMap<String, Integer> hmWord = new HashMap<String, Integer>();
        
        /*
        int maxNum = 0;
        String maxNumString = "";
		for (final String word : resultStr.toString().split(" ")) {			
			//if(hmWord.get(word) == null) {
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
	/*	if(maxNum > 0) {
			System.out.println("##############DUPLICATED WORD: " + maxNumString);


				longestWord = maxNumString;	
			
		} else {
		*/

			for (final String word : resultStr.toString().split(" ")) {
			  if(word.trim().length() > longestWord.length()) {
				longestWord = word;				
			  }
		    }
	   
			
			
	//	}
		// fetch beginWord
		if(resultStr.length() > 0 && resultStr.trim().indexOf(separator) > 0) {
			beginWord = resultStr.substring(0,resultStr.trim().indexOf(separator)).trim();		  			
	    } 
		String localWord = beginWord;
		if(localWord.length() <= 0) {
			localWord = resultStr.trim();
		}
		if(beginWord == null || beginWord.trim().equals(longestWord.trim())) {
			   beginWord = "";			
		}
		
		estimatedLongestWordPosition = resultStr.indexOf(longestWord);
		
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
		} else {
			endWord = " "+endWord.trim();
		}
		// fetch ESTIMATED RESULT LENGTH
		//dc1 estimatedMatchStrLengthtoStart = resultStr.length() - endWord.length() - 1;	
		int extraLengthNeeded = getExtraLengthNeeded(resultStrTimed_p);
        if(extraLengthNeeded > 0) {
		    endWord = "  ";
		  //  estimatedMatchStrLengthtoStart += extraLengthNeeded; 
		    estimatedMatchStrLengthtoStart = estimatedMatchStrLengthtoStart_for_Diff_Sentence();
			System.out.println("******MATCH LENGTH:"+estimatedMatchStrLengthtoStart);
		//	System.out.println("******BW:"+beginWord+" EW:"+endWord+" LW: "+longestWord);
		    
        }

		
 //TO DO: NEED TO IMPROVE LOCATION FUNCTION
		
		//int localPosition1 = originalText2.toLowerCase().indexOf(localWord);
		int localPosition1 = StringProcessing.indexOf_parseBigChunk(originalText2.toLowerCase(),beginWord,longestWord,endWord);
	//	System.out.println(isFirstSentence+"@@@@@@@@@@@@"+localWord+"@@@@@@LOCALPOSITION1:"+localPosition1);
	//	System.out.println("@@@@@@@@@@@@TIMESTART"+getTimeStart()+"@@@@@@ToleratedTimeFastF:"+toleratedTimeFastFoward);

		if((getTimeStart() > toleratedTimeFastFoward || localPosition1 > toleratedSkip) && isFirstSentence) {
			beginWord = " ";
			longestWord = " ";
			endWord = "  ";
			if(getTimeStart() > toleratedTimeFastFoward) {
			   estimatedMatchStrLengthtoStart = (int) (getTimeStart()*speechSpeedCharPerSec);
				timeEnd = timeStart;
				timeEndFirst = timeStart;
				timeStart = "0";
		//		System.out.println("@@@@@@@@:"+getTimeStart()+"@@@@@@@@@@LOCALPOSITION111:"+localPosition1);
			} else {
				estimatedMatchStrLengthtoStart = estimatedMatchStrLengthtoStart_for_Diff_Sentence();
		//		System.out.println("@@@@@@@@@@@@@@@@@@LOCALPOSITION22:"+localPosition1);
			}
		} else {
			estimatedMatchStrLengthtoStart = estimatedMatchStrLengthtoStart_for_Diff_Sentence();
		}
		if((getTimeEnd() - getTimeStart()) < timeDiffMin) {
			endWord = " ";
		}
		
	//	System.out.println("*******ENDWORD:"+endWord);
	}
	
	private static int getExtraLengthNeeded(String resultStrTimed_p) {
      int extraLengthNeeded = 0;
	    String time1 = "";
	    String time2 = "";
	    float time1f = 0;
	    float time2f = 0;
		for (final String word : resultStrTimed_p.toString().split(" ")) {
                time1 = getTimeEndFirst(word);
                time2 = getTimeEnd(word);
        		try {
        			time1f = Float.parseFloat(time1);
        			time2f = Float.parseFloat(time2);
        			if(time2f-time1f >= 1.0) {
            			extraLengthNeeded += (int)((time2f-time1f)*speechSpeedCharPerSec);
            		}
        		}catch (Exception e) {}
		    }
    //       System.out.println("extraLengthNeeded: "+ extraLengthNeeded);
	    return extraLengthNeeded;
	}
}
