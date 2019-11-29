package com.rb.util;

import com.rb.util.escapeCharNewToSpace;
import com.rb.util.audio.FileProcessing;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * @author D.C
 * NEW VERSION OF FIND AND REPLACE FUNCTION EXIST IN FINDREPLACENET_READINGBEERSS
 */
public class NewFindReplace {
    /**
     * @param args the command line arguments
     */
//For where jealousy and faction are, there is confusion and every vile deed. 3:17 But the wisdom that is from above is first pure, then peaceable, gentle, easy to be entreated, full of mercy and good fruits, without variance, without hypocrisy. 3:18 And the fruit of righteousness is sown in peace for them that make peace.
//1:1 That which was from the beginning, that which we have heard, that which we have seen with our eyes, that which we beheld, and our hands handled, concerning the Word of life 1:2 (and the life was manifested, and we have seen, and bear witness, and declare unto you the life, the eternal life, which was with the Father, and was manifested unto us); 1:3
    public static void main(String[] args) {
    	
        int start_end_out[] = {-1,-1};        
      //  String findThis = "BUT RESEARCHER ELLEN           SAYS IT PROBABLY DOES MAKE YOU BETTER AT CERTAIN SKILLS     ELLEN             IMAGINE DRIVING DOWN THE HIGHWAY  THERE S MANY THINGS THAT COULD CAPTURE YOUR ATTENTION AND";
       // StringBuffer origText = new StringBuffer("BUT RESEARCHER ELLEN BIALYSTOK SAYS IT PROBABLY DOES MAKE YOU BETTER AT CERTAIN SKILLS     ELLEN BIALYSTOK   IMAGINE DRIVING DOWN THE HIGHWAY  THERE S MANY THINGS THAT COULD CAPTURE YOUR ATTENTION AND YOU REALLY NEED TO BE ABLE TO MONITOR ALL OF THEM  WHY WOULD BILINGUALISM MAKE YOU ANY BETTER AT THAT      AND THE ANSWER  SHE SAYS  IS THAT BILINGUAL PEOPLE ARE OFTEN BETTER AT CONTROLLING THEIR ATTENTION    A FUNCTION CALLED THE EXECUTIVE CONTROL SYSTEM     ELLEN BIALYSTOK   IT S QUITE POSSIBLY THE MOST IMPORTANT COGNITIVE SYSTEM WE HAVE BECAUSE IT S WHERE ALL OF YOUR DECISIONS ABOUT WHAT TO ATTEND TO  WHAT TO IGNORE  WHAT TO PROCESS ARE MADE      MS  BIALYSTOK IS A PSYCHOLOGY PROFESSOR AT YORK UNIVERSITY IN TORONTO  CANADA  SHE SAYS THE BEST METHOD TO MEASURE THE EXECUTIVE CONTROL SYSTEM IS CALLED THE STROOP TEST  A PERSON IS SHOWN WORDS IN DIFFERENT COLORS  THE PERSON HAS TO IGNORE THE WORD BUT SAY THE COLOR  THE PROBLEM IS THAT THE WORDS ARE ALL NAMES OF COLORS     ELLEN BIALYSTOK   SO YOU WOULD HAVE THE WORD BLUE WRITTEN IN RED  BUT YOU HAVE TO SAY RED  BUT BLUE IS SO SALIENT  IT S JUST LIGHTING UP ALL THESE CIRCUITS IN YOUR BRAIN  AND YOU REALLY WANT TO SAY BLUE  SO YOU NEED A MECHANISM TO OVERRIDE THAT SO THAT YOU CAN SAY RED  THAT S THE EXECUTIVE CONTROL SYSTEM      HER WORK SHOWS THAT BILINGUAL PEOPLE CONTINUALLY PRACTICE THIS FUNCTION  THEY HAVE TO  BECAUSE BOTH LANGUAGES ARE ACTIVE IN THEIR BRAIN AT THE SAME TIME  THEY NEED TO SUPPRESS ONE TO BE ABLE TO SPEAK IN THE OTHER     THIS MENTAL EXERCISE MIGHT HELP IN OTHER WAYS  TOO  RESEARCHERS SAY BILINGUAL CHILDREN ARE BETTER ABLE TO SEPARATE A WORD FROM ITS MEANING  AND MORE LIKELY TO HAVE FRIENDS FROM DIFFERENT CULTURES  BILINGUAL ADULTS ARE OFTEN FOUR TO FIVE YEARS LATER THAN OTHERS IN DEVELOPING DEMENTIA OR ALZHEIMER S DISEASE     FOREIGN LANGUAGE STUDY HAS INCREASED IN THE UNITED STATES  BUT LINGUIST ALISON MACKEY AT GEORGETOWN UNIVERSITY POINTS OUT THAT ENGLISH SPEAKING COUNTRIES ARE STILL FAR BEHIND THE REST OF THE WORLD     ALISON MACKEY   IN ENGLAND  LIKE IN THE UNITED STATES  BILINGUALISM IS SEEN AS SOMETHING SPECIAL AND UNIQUE AND SOMETHING TO BE COMMENTED ON AND PERHAPS WORK TOWARDS  WHEREAS IN MANY OTHER PARTS OF THE WORLD BEING BILINGUAL IS JUST SEEN AS A NATURAL PART OF LIFE      AND THAT S THE VOA SPECIAL ENGLISH HEALTH REPORT  WRITTEN BY KELLY NUXOLL  TELL US ABOUT YOUR EXPERIENCE LEARNING LANGUAGES  GO TO VOASPECIALENGLISH COM OR THE VOA LEARNING ENGLISH PAGE ON FACEBOOK  I M STEVE EMBER");
       // StringBuffer origText = FileProcessing.fileToStringBufferBr("C:/java/long_audio_aligner/TestFiles/TEST_ORIG1.txt");
       // String findThis = FileProcessing.fileToStringBufferBr("C:/java/long_audio_aligner/TestFiles/TEST_TS.txt").toString();
        StringBuffer origText = FileProcessing.fileToStringBufferBr("C:/java/long_audio_aligner/TestFiles/bug_report/VERSEPARSEISSUE.txt");
        String findThis = FileProcessing.fileToStringBufferBr("C:/java/long_audio_aligner/TestFiles/bug_report/TS.txt").toString();
        System.out.println(origText);
        String foundText[] = new String[3];
        int bottomPosition[] = {0};
        
        System.out.println("getting....");
        getSentenceWordNumNPosition(origText.toString());
        System.out.println("done....");

        parseBigChunkIgnorSpace(origText, findThis,0,foundText, bottomPosition);
        
        
       System.out.println("f0:"+foundText[0]+"-");
     //   System.out.println("f1:"+foundText[1]+"-");
     //   System.out.println("f2:"+foundText[2]+"-");
      
       
   // 	getSentenceWordNumNPosition("Auguest is my favorite month.  Today is sunny with blue sky.  Autume is comming..");
    	
    }

    public static SortedMap<Integer, Integer> getSentenceWordNumNPosition(String sentence_in) {
    	SortedMap<Integer, Integer> outSMapInt = new TreeMap<Integer, Integer>(java.util.Collections.reverseOrder());
    //	String sentence_in = "Auguest is my favorite month.  Today is sunny with blue sky.  Autume is comming..";
    	//sentence_in.indexOf
        char[] separators1 = { '.', ';', '!','?'};
        char[] separators2 = {','};
        
        String[] ExcludeSeparators1 = {"Mr.","Dr.","Jr.","Sr.","St."};
        String[] ExcludeSeparators2 = {"Mrs."};
        int position1 = 0;
        int positionExclude1 = 0;
        int positionExclude2 = 0;
       
        int position2 = 0;   
        int position = 0;
        int prePosition = 0;
        
        String[] foundText = {"","",""};
        int[] bottomPosition = {0};
        boolean isFound = false;
        
   //     System.out.println("************Sentencein:" +sentence_in);
        while(position >= 0) {
        	 System.out.println("******************TO OF WHILE LOOP************************");
        	
        	prePosition = position;
        	// check for Mr.
        	positionExclude1 = StringProcessing.indexOfAny(sentence_in,ExcludeSeparators1,position);
        	positionExclude2 = StringProcessing.indexOfAny(sentence_in,ExcludeSeparators2,position);
        	
        	position1= StringProcessing.indexOfAny(sentence_in,separators1,position+50);
            isFound = parseBigChunkIgnorSpaceOrig(new StringBuilder(sentence_in), "\\s\\d*:\\d*\\s", position+10,foundText, bottomPosition);
            if(isFound) {
            	System.out.println("p1-0:" + position1+" b0:" + bottomPosition[0]);

            	if(bottomPosition[0] > 0 &&  bottomPosition[0] < (position1+200)) {
            		position1 = bottomPosition[0];
            		System.out.println("b1");
    //                break;
            	}
            }
        	
        	// If Mr. found, retry next period
        	if(positionExclude1+2 == position1 || positionExclude2+3 == position1){
            	position1= StringProcessing.indexOfAny(sentence_in,separators1,position1+2);  
                isFound = parseBigChunkIgnorSpaceOrig(new StringBuilder(sentence_in), "\\s\\d*:\\d*\\s", position1+2,foundText, bottomPosition);
                if(isFound) {
                	System.out.println("p1-1:" + position1+" b0:" + bottomPosition[0]);

                	if(bottomPosition[0] > 0 &&  bottomPosition[0] < (position1+200)) {
                		position1 = bottomPosition[0];
                		System.out.println("b2");

                		//              		break;
                	}
                }

        	}
        	if(position1 == -1) {
            	position1= StringProcessing.indexOfAny(sentence_in,separators2,position+50);
                isFound = parseBigChunkIgnorSpaceOrig(new StringBuilder(sentence_in), "\\s\\d*:\\d*\\s", position+20,foundText, bottomPosition);
                if(isFound) {
                	System.out.println("p1-2:" + position1+" b0:" + bottomPosition[0]);
                	if(bottomPosition[0] > 0 &&  bottomPosition[0] < (position1+200)) {
                		position1 = bottomPosition[0];
//                		break;
                		System.out.println("b3");
 
                	}
                }          	
        	}        	
        	if(position1 == -1) break;
 

        	System.out.println("****check preposition:"+prePosition+"  position1:"+position1+ "  bottomPosition0" + bottomPosition[0]);

        	if((position1 != bottomPosition[0]) && (position1 > prePosition+180)) {  // if bottomPositon is set skip the block
           
            	
 
            	// check for comma starting postion is 100
            	position2 = StringProcessing.indexOfAny(sentence_in,separators2,prePosition+100);
            	isFound = parseBigChunkIgnorSpaceOrig(new StringBuilder(sentence_in), "\\s\\d*:\\d*\\s", prePosition+100,foundText, bottomPosition);
                if(isFound) {
                	if(bottomPosition[0] > 0 &&  bottomPosition[0] < (position2+200)) {
                		position2 = bottomPosition[0];
                		System.out.println("position2 b4");

                	}
                }       
 
            	// if comma found is less than the whole senetence and greater then 0, then it is valid
                if(position2 < position1 && position2 > 0) {
                	position = position2;
                	System.out.println("==================position2:");

                } else {
                	position = position1;
                	System.out.println(">==================position1-1:");

                }
            } else {
            	position = position1;
            	System.out.println("==================position1-2:");
            }
        	int wordNum = 0;
        	if(position >= 0) {
                String subSentence = sentence_in.substring(0,position);
        	//	System.out.println(subSentence);
        		String subSentenceFiltered = escapeCharNewToSpace.forAUDIOTXT(subSentence).trim();
        		for (final String word : subSentenceFiltered.split(" ")) {
    				if(word.trim().length() > 0) {
    				   wordNum++;
    				}
    			}
            	outSMapInt.put(wordNum,position);
    			position++;
    	          System.out.println(position+" wP: "+wordNum);

        	}  //if
        	
        	
        } //while
        return outSMapInt;
    }


   public static boolean parseBigChunkIgnorSpace(StringBuffer originalText2_in, String topSearchText_in,String midSearchText_in, String bottomSearchText_in, int startPosition, String foundText[], int bottomPosition[]) {    	    	
    	String midSearchText = midSearchText_in;    	
    	if(topSearchText_in.length() > 0) {
    	//	midSearchText = topSearchText_in+".*"+midSearchText; 
            midSearchText = topSearchText_in+"[^~]*"+midSearchText;
    	}    	
    	if(bottomSearchText_in.length() > 0) {
    	//	midSearchText = midSearchText+".*"+bottomSearchText_in; 
            midSearchText = midSearchText+"[^~]*"+bottomSearchText_in;
    	}
        return parseBigChunkIgnorSpace(originalText2_in, midSearchText, startPosition,foundText, bottomPosition);
    }

   public static Boolean parseBigChunkIgnorSpaceOrig(StringBuilder originalText2_in, String midSearchText_in, int startPosition, String[] foundText, int[] bottomPosition) {
	      String originalText = originalText2_in.toString();
	     //  String originalText =  escapeCharNewToSpace.forAUDIOTXT(originalText2_in.ToString().ToUpper());
	        String midSearchText = midSearchText_in;
	       while(midSearchText.contains("  ")) {
	           midSearchText = midSearchText.replace("  ", " ");
	       }
	  
	       System.out.println("originalText: " + originalText);
	       
	       System.out.println("midSearchText: "+midSearchText);
	        int[] start_end_out = {-1,-1};
	        
	       System.out.println("find index startPosition: " + startPosition);
	       
	        findIndex(originalText,midSearchText,start_end_out,startPosition);
	        
//1:1 That which was from the beginning, that which we have heard, that which we have seen with our eyes, that which we beheld, and our hands handled, concerning the Word of life 1:2 (and the life was manifested, and we have seen, and bear witness, and declare unto you the life, the eternal life, which was with the Father, and was manifested unto us); 1:3 that which we have seen and heard declare we unto you also, that ye also may have fellowship with us: yea, and our fellowship is with the Father, and with his Son Jesus Christ: 1:4 and these things we write, that our joy may be made full. 1:5 And this is the message which we have heard from him and announce unto you, that God is light, and in him is no darkness at all. 1:6 If we say that we have fellowship with him and walk in the darkness, we lie, and do not the truth: 1:7 but if we walk in the light, as he is in the light, we have fellowship one with another, and the blood of Jesus his Son cleanseth us from all sin. 1:8 If we say that we have no sin, we deceive ourselves, and the truth is not in us. 1:9 If we confess our sins, he is faithful and righteous to forgive us our sins, and to cleanse us from all unrighteousness. 1:10 If we say that we have not sinned, we make him a liar, and his word is not in us.	        
	       
	        if(start_end_out[0] < 0) {
	            bottomPosition[0] = -1;
	            foundText[0] = "";  // NOTHING FOUND
	            return false;
	        }     
	      //  System.out.println(start_end_out[0] + " *****1: "+start_end_out[1]);
	        int maxIndex = start_end_out[0]+start_end_out[1];
	        
	        if(maxIndex > originalText2_in.length()) {
	           maxIndex = originalText2_in.length();
	        }
	               foundText[0] = originalText2_in.toString().substring(start_end_out[0], maxIndex);
	               foundText[1] = originalText2_in.toString().substring(0,start_end_out[0]);
	               foundText[2] = originalText2_in.toString().substring(maxIndex);
	               bottomPosition[0] = start_end_out[1];
	           //    Console.WriteLine(" originalText:" + originalText);
	           //    Console.WriteLine(" midSearchText:" + midSearchText);
	           //   System.out.println("foundText0: " + foundText[0]);
	            //   System.out.println("foundText1: " + foundText[1]);
	            //   System.out.println("foundText2: " + foundText[2]);
	        return true;
	    }
	
   
   public static boolean parseBigChunkIgnorSpace(StringBuffer originalText2_in, String midSearchText_in, int startPosition, String foundText[], int bottomPosition[]) {
	   String originalText =  escapeCharNewToSpace.forAUDIOTXT(originalText2_in.toString().toUpperCase());
        String midSearchText = midSearchText_in.toUpperCase().trim();
       while(midSearchText.contains("  ")) {
    	   midSearchText = midSearchText.replaceAll("  ", " "); 
       }
    //    System.out.println("midSearchText: "+midSearchText);
        int start_end_out[] = {-1,-1};
        findIndex(originalText,midSearchText,start_end_out,startPosition);
        if(start_end_out[0] < 0) {
        	bottomPosition[0] = -1;
            foundText[0] = "NOTHING FOUND";
            return false;
        }
        if((start_end_out[1] - start_end_out[0]) > (midSearchText_in.length() + 2000)) {
        	bottomPosition[0] = -1;
            foundText[0] = "NOTHING FOUND2";
            return false;        	
        }
    //    System.out.println("originalText2_in:"+originalText2_in.length()+ " startendout0:"+start_end_out[0]+" startendout1:"+start_end_out[1]);
     //   System.out.flush();
        if(start_end_out[1] > 0 && start_end_out[1] < originalText2_in.length()) {
               foundText[0] = originalText2_in.substring(start_end_out[0], start_end_out[1]);
               foundText[2] = originalText2_in.substring(start_end_out[1]);
        }
               foundText[1] = originalText2_in.substring(0,start_end_out[0]);
               bottomPosition[0] = start_end_out[1];
        return true;
    }

  private static void findIndex(String textToSearch_in, String findThis, int start_end_out[], int startPosition) {
      String textToSearch = textToSearch_in.toString();
      if(textToSearch_in.length() > startPosition) {
          textToSearch = textToSearch.substring(startPosition);
      } else {
          return;
      }
      int start_end_out_l[] = {-1,-1};
      findIndex(textToSearch, findThis, start_end_out_l);
      if(start_end_out_l[0] >= 0) {
          start_end_out[0] = start_end_out_l[0]+startPosition;
          if(start_end_out_l[1] > 0) {
             start_end_out[1] = start_end_out_l[1]+startPosition;
          }
      }
      return;
    }


  private static void findIndex(String text, String findThis_in, int start_end_out[]) {
      String findThis = findThis_in;
      if(findThis.lastIndexOf(" ") > 0) {
    	 // findThis = findThis.re
    	  findThis = findThis.replaceAll("^(.*) (.*)$","$1[\\\\s]*$2"); 
      }
      if(findThis.lastIndexOf(" ") > 0) {
     	 // findThis = findThis.re
     	  findThis = findThis.replaceAll("^(.*) (.*)$","$1[\\\\s]*$2"); 
       }
      String regex = findThis.replaceAll(" ", "[\\\\s]*");
  //    String regex = findThis.replaceAll(" ", "[^~]*");
	//  String regex = findThis.replaceAll(" ", ".*");
     // System.out.println("regex: "+regex);
     // System.out.println("text: "+text);

      Pattern p = Pattern.compile(regex);
      Matcher matcher = p.matcher(text);
      if (matcher.find()) {
         start_end_out[0] = matcher.start();
         start_end_out[1] = matcher.end();
 //        System.out.println("MATCH FOUND...:"+matcher.start()+" :"+matcher.end());
      }
  }
}
