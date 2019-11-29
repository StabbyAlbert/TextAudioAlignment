package alignment;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.log4j.Logger;

import com.rb.AlignTXTFinalStep;
import com.rb.TextAlign;
import com.rb.alignTXTFinal;
import com.rb.locationNTimeInTXT;
import com.rb.stringMapItem;
import com.rb.util.FileProcessingNew;
import com.rb.util.StringProcessing;
import com.rb.util.escapeCharNewToSpace;
import com.rb.util.globalConfig;
import com.rb.util.locationNTime;
import com.rb.util.audio.AudioFileProcessing;
import com.rb.util.audio.FileProcessing;
/*
 * WAV FILE MUST BE 16bit per sample, 16khz mono
 */
public class AlignMainNew extends com.rb.AlignMainBase{
	private static SortedMap<Integer, locationNTimeInTXT> hmFoundInOrigTXT;
	private static final boolean removeProcessedWAV = globalConfig.removeProcessedWAV;
	private static final String sphinxModelPath = globalConfig.sphinxModelPath;
	private static int hmFoundInOrigTXTCounter = 1;
	private static int hmFoundInOrigTXTCounter2 = 100;
	private static int hmFoundInOrigTXTCounter3 = 200;
	private static int hmFoundInOrigTXTCounter4 = 300;
	private static int fileCounter = 1;
	private static double wavCutSecCumulative = 0;
	private static int txtCutLengthCumulative = 0;
	private static int totalTXTLength = 0;
	private static String segWAVFileOrig = "";
	private static String segWAVFile_p_pre = "";
	public static final String defaultWavClipSec = "55";
	
	
	final static Logger logger  = Logger.getLogger(FileProcessingNew.class);

	
	//public static String origTXT = "";
	/*
	 * Method 1) Takes in a WAV audio and TXT transcript files as input, creates a new transcript file 
	 *           with time stamp.  New transcript filename: NewTranscript.txt
	 *        2) The new transcript file is then turn into HASHMAP and mapped against the original text file,
	 *           final result file is then generated.
	 *           
	 *           ARGS[] 1) inputFileFoulder 2) Input WAV filename 3) debug on/off
	 */
	

	
	public static void main(String args[]) throws Exception, IOException, UnsupportedAudioFileException{
		   boolean isDebugMode = false;   // default false = debug off   true = debug on  -- overriden by arg2
		   boolean isDebugModeSkipProcessing = false;  // default false  -- overridden by arg3
		   boolean isProcessMarkedFile = false;  // default false -- overridden by arg4
		   boolean useOldFindReplace = false;   // default false -- overridden by arg7
		   float delHeadSegmentSec = 0;  // args9debug
		   String args0debug = "C:/TEMP/advanced1/";  // inputFileFolder
		   String args1debug = "Exhibit Explores Modern Native American Art.wav"; // input WAV FileName
		   String args2debug = "0"; // DEFAULT 0 isDebugMode=false
		   String args3debug = "0"; // DEFAULT 0 isDebugModeSkipProcessing=false
		   String args4debug = "0"; // DEFAULT 0 isProcessMarkedFile=false
		   String args5debug = "14"; // DEFAULT 14 characters per second
		   String args6debug = "0"; // DEFAULT 0 isPhaseShift - 1/2 sample phase shift disabled
		   String args7debug = "0"; // DEFAULT 0 useOldFindReplace=false
		   String args8debug = defaultWavClipSec; // DEFAULT 30 seconds of wav file to clip
		   String args9debug = "0";
		   hmFoundInOrigTXT = new TreeMap<Integer, locationNTimeInTXT>(java.util.Collections.reverseOrder());
           if(args.length >= 2) {
        	   
        	   
        	   
		   try {
			   logger.info("ARGS FROM CMD: " + Arrays.deepToString(args));
			   
			   args0debug = args[0];

			   args1debug = args[1];
			   if(args.length >= 3) {
				   try{
				   if(Integer.parseInt(args[2]) >= 0) {
					   args2debug = args[2];					   
				   }
				   if(args.length >= 4) {
					   if(Integer.parseInt(args[3]) >= 0) {
					      args3debug = args[3];
					   }
					   if(args.length >= 5) {
						   if(Integer.parseInt(args[4]) >= 0) {
							      args4debug = args[4];
							      if(args.length >= 6) {
									   if(Double.parseDouble(args[5]) >= 0) {
								            args5debug = args[5];
								        	logger.info(args.length + "-args5debug.....: "+args5debug);     
								            if(args.length >= 7) {
												   if(Integer.parseInt(args[6]) >= 0) {
													   args6debug = args[6];													   
										        	   logger.info("args6debug.....: "+args6debug);

													   if(args.length >= 8) {
															   if(Integer.parseInt(args[7]) >= 0) {
																   args7debug = args[7];		  
													        	   logger.info("args7debug.....: "+args7debug);
																   if(args.length >= 9) {
																	   if(Integer.parseInt(args[8]) >= 0) {
																		   args8debug = args[8];		  
															        	   logger.info(args.length+"args8debug..a...: "+args8debug);
																		   if(args.length >= 10) {
																			   if(Float.parseFloat(args[9]) >= 0) {
																				   args9debug = args[9];		  
																	        	   logger.info("args9debug.....: "+args9debug);															        	   
																			   }
																		   }
																	   }
																   }											        	   
													        	   
															   }
											            }
												   }
								            }
									   }
							      }
							}						   
					   }
				   }	
				   }catch(Exception e) {
					   e.printStackTrace();
				   };
			   }                 
        		   logger.info("INPUT OK");        	  	   
           } catch (Exception e) {
        		   e.printStackTrace();
        		   return;
           }         
           }
		   String inputFileFolder = args0debug;
		   String origWAVFileName = inputFileFolder+args1debug;   
		   String outputFileFolder = inputFileFolder + StringProcessing.getFileNameWithOutExtension(args1debug)+"/";
		   String finalOutputFileFolder = inputFileFolder + "out2/";
		   String origTXT_Initial = StringProcessing.getFileNameWithOutExtension(origWAVFileName)+".txt";
		   String origTXT = StringProcessing.getFileNameWithOutExtension(origWAVFileName)+"_processing.txt";   
		   String finalOutTXT = finalOutputFileFolder + StringProcessing.getFileNameWithOutExtension(args1debug)+".lrc"; 
		   String segWAVFile =  outputFileFolder+"SEG_WAVTXT.wav";
		   String segTXTFile = outputFileFolder + "SEG_WAVTXT.txt";
		   logger.info(" args length: "+args.length);       
		   String finalTimedTXTFile = outputFileFolder+"InsTime";
		
		   if (inputFileFolder.equals("/") || inputFileFolder.length() < 3 || outputFileFolder.equals("/") ||  outputFileFolder.length() < 3) {
               logger.error("DIRECTORY NOT ALLOWED");
			   return;
		   }
		 
		   int shiftBit = 0;
		   int wavClipSec = Integer.parseInt(args8debug);
		   FileProcessingNew.checkNCreateFile(origTXT_Initial,origTXT);
           if(Integer.parseInt(args2debug) == 1) {
        	   isDebugMode = true;
        	   logger.info(" New isDebugMode: " +Integer.parseInt(args2debug));
        	   logger.info("DIRECTORY NOT ALLOWED");
           }
           if(Integer.parseInt(args3debug) == 1) {
        	   isDebugModeSkipProcessing = true;
        	   logger.info(" New isDebugModeSkipProcessing: " +Integer.parseInt(args3debug));
           }  
           if(Integer.parseInt(args4debug) == 1) {
        	   isProcessMarkedFile = true;
        	   logger.info(" New isProcessMarkedFile: " +Integer.parseInt(args4debug));
           }
           if(Double.parseDouble(args5debug) > 0) {
        	   globalConfig.speechSpeedCharPerSec = Double.parseDouble(args5debug);
        	   logger.info(" New speechSpeedCharPerSec: " + Double.parseDouble(args5debug));
           }
           if(Integer.parseInt(args6debug) > 0) {
        	   shiftBit = Integer.parseInt(args6debug);
        	   logger.info("Shift bit set to: "+shiftBit);
           }
           if(Integer.parseInt(args7debug) == 1) {
        	   useOldFindReplace = true;
        	   logger.info("useOldFindReplace.....: "+args7debug);
           } else {
        	   logger.info("NOTuseOldFindReplace.....: "+args7debug);
           }
           
           if(Integer.parseInt(args8debug) > 0) {
        	   wavClipSec = Integer.parseInt(args8debug);
        	   logger.info("Wav Clip Sec.....: "+wavClipSec);
           } else {
        	   logger.info("NO wavClipSec.....: "+wavClipSec);
           }           
           if(Float.parseFloat(args9debug) > 0) {
        	   delHeadSegmentSec = Float.parseFloat(args9debug);
           }
           if(isProcessMarkedFile)
           {
        	   logger.info("!!!!!!!!!!!!222222!!!!!!!!!!!!!!!!");        	   

        	   FileProcessingNew.bkFileCopyFileReturnString(finalTimedTXTFile+".txt",origTXT,origTXT_Initial);
       	       return;
           } else {
        	   logger.info("!!!!!!!!!!!!222222!!!!!!!!!!!!!!!!");        	   
           }
		   segWAVFileOrig = segWAVFile;   
		   File basedirectory = new File(outputFileFolder);
		   if(!basedirectory.mkdirs()) {
				logger.info("**********FAIL TO CREATE DIR ok*******"+outputFileFolder);
		   } else {
				logger.info("Dir Created "+outputFileFolder);
		   }
           logger.info("Ok: "+outputFileFolder+StringProcessing.getFileNameWithOutExtension(StringProcessing.getFileName(origWAVFileName))+"_NT_v8.txt");
           logger.info("Ok2: "+outputFileFolder+StringProcessing.getFileNameWithOutExtension(StringProcessing.getFileName(origWAVFileName))+"_NT_v8.txt");
//		    String finalTimedTXTFile = outputFileFolder+"InsTime";
	       if(isDebugMode == false) {
		     // FileProcessing.copyStringToFile("", finalTimedTXTFile+".txt");
		  	  FileProcessing.copyStringToFile("", outputFileFolder+StringProcessing.getFileNameWithOutExtension(StringProcessing.getFileName(origWAVFileName))+"_NT_v8.txt");
			  FileProcessing.copyStringToFile("", outputFileFolder+StringProcessing.getFileNameWithOutExtension(StringProcessing.getFileName(origWAVFileName))+"_NT_v8.txt");
	       }
		   StringBuffer origTXTStrBuff = FileProcessing.fileToStringBufferBr(origTXT);
		   totalTXTLength = origTXTStrBuff.length();
		   File segWAVFileFile = new File(segWAVFile);
		   segWAVFileFile.delete();
		   File segTXTFileFile = new File(segTXTFile);
		   segTXTFileFile.delete();
		
		   locationNTime LNT = StringProcessing.getTimeNLocation(origTXTStrBuff.toString());
		   if(LNT != null) {
			   delHeadSegmentSec = LNT.getTimeSec();
			   logger.info(" OUTPUTFILE_NAME:"+segTXTFile);
			   
			   FileProcessing.copyStringToFile(StringProcessing.bufFilter(origTXTStrBuff.substring(LNT.indexLocation+1)), segTXTFile);	   
		   } else {
			   FileProcessing.copyFileToFile(StringProcessing.bufFilter(origTXT), segTXTFile);			   
		   }   
		 //   delHeadSegmentSec = 33;
		   txtCutLengthCumulative = 0;
		   AudioFileProcessing.processDelHead(wavClipSec,origWAVFileName, FileProcessing.getFileNameWithOutExtension(segWAVFile)+fileCounter+".wav", delHeadSegmentSec,shiftBit); 
		 //  delHeadSegmentSec = 10;
		   wavCutSecCumulative = delHeadSegmentSec*1000;		   
		     String foundText[] = new String[3];
		     foundText[0] = "";  // return found string
		     foundText[1] = "";  // return previous string before found string
		     foundText[2] = "";  // return next string after found string 
           if(isDebugMode == true) {
             if(isDebugModeSkipProcessing == false) {
	         while(processOneAudioSegment(wavClipSec,origWAVFileName,FileProcessing.getFileNameWithOutExtension(segWAVFile)+fileCounter+".wav",segTXTFile,FileProcessing.getFileNameWithOutExtension(segWAVFile)+fileCounter+"_InsTime"+".txt",outputFileFolder,isDebugMode,useOldFindReplace,shiftBit));
           }
           SortedMap<Integer, alignTXTFinal> hmFinal = null;
	       hmFinal = AlignTXTFinalStep.getHMInProcessedOrigTXT(finalTimedTXTFile+".txt");			
	       FileProcessing.copyStringToFile(AlignTXTFinalStep.AlignOrigFileToHashMap(foundText,origTXT_Initial,hmFinal).toString(),finalOutTXT);
           logger.info("Processing complete. Exit...");
           } else  if(isDebugMode == false) {
	       if(isDebugModeSkipProcessing == false) {	
		      while(processOneAudioSegment(wavClipSec,origWAVFileName,FileProcessing.getFileNameWithOutExtension(segWAVFile)+fileCounter+".wav",segTXTFile,finalTimedTXTFile+".txt",outputFileFolder,isDebugMode,useOldFindReplace,shiftBit));
	       }
		   basedirectory = new File(finalOutputFileFolder);
		   if(!basedirectory.mkdirs()) {
				logger.info("**********FAIL TO CREATE DIR*******"+finalOutputFileFolder);
		   } else {
				logger.info("Dir Created "+finalOutputFileFolder);
		   }
		    SortedMap<Integer, alignTXTFinal> hmFinal = null;
			hmFinal = AlignTXTFinalStep.getHMInProcessedOrigTXT(finalTimedTXTFile+".txt");			
			FileProcessing.copyStringToFile(AlignTXTFinalStep.AlignOrigFileToHashMap(foundText,origTXT_Initial,hmFinal).toString(),finalOutTXT);
		    logger.info("Processing complete. Exit...");
}
		    logger.info("Exit Align");
           
	}
	/*
	 * Method 1) Performs an audio/txt alignment using processFileOnce(wavfile,text) and obtain a HASHMAP;
	 *        2) Map the result from the HASHMAP into the top part truncated original text; 
	 *        3) If the result comes back with unmatched PREVIOUS SEARCH STRING foundText[1] in the front
	 *           or back by toleratedSkip = 30, then perform a rematch (by rearranging strings in foundText[0,1,2])
	 *           NOTE: foundText[0] - String just matched by matchToOrigTXT 
	 *                 foundText[1] - String found previously + [BEGIN TIME]foundText[0][END TIME] -> Save time stamped
	 *                 foundText[2] - String not yet processed -> to be saved as next segment file
	 *        4) Append time stamped TXT to file: e8_8_InsTime1.txt
	 *        5) If further processing is needed, prepare the audio and text file:
	 *           Note: a) Next segment wav filename: SEG_WAV.wav
	 *                 b) Next segment text filename: foundText[0] -> SEG_TXT.txt
	 *        6) Return to the calling table a flag indicating whether further processing is needed
	 * 
	 */
	private static boolean processOneAudioSegment(int wavClipSec,String origWAVFileName, String segWAVFile_p,String segTXTFile_p,String finalTimedTXTFile_p, String outputFileFoulder, boolean isDebugMode, boolean useOldFindReplace,int shiftBit) throws IOException, UnsupportedAudioFileException{
	       hmFound = new TreeMap<Integer, stringMapItem>();
		   String foundText[] = new String[3];
		   StringBuffer segTXTFile_StrBuff = FileProcessing.fileToStringBufferBr(segTXTFile_p);
		//   hm = TextAlign.processFileOnce(segWAVFile_p, escapeCharNewToSpace.forAUDIOTXT(segTXTFile_StrBuff.toString()), sphinxModelPath, outputFileFoulder,useOldFindReplace);
		   hm = TextAlign.processFileOnceLongAudio(segWAVFile_p, escapeCharNewToSpace.forAUDIOTXT(segTXTFile_StrBuff.toString()), sphinxModelPath, outputFileFoulder,useOldFindReplace);

		   txtCutLengthCumulative = totalTXTLength-segTXTFile_StrBuff.length(); 
		   StringBuffer outputStrBuff = matchToOrigTxt(segTXTFile_StrBuff.toString(),foundText,wavCutSecCumulative,useOldFindReplace);
		 //  logger.info("****MARK1..."+hm.get(hm.firstKey()).getTimeStart());
		   if(hm.size() > 0 && foundText != null && foundText[1] != null) {
			   logger.info("****MARK2..."+hm.get(hm.firstKey()).getTimeStart());
			   logger.info("****MARK3..."+foundText[1].length());
			   logger.info("****MARK4..."+stringMapItem.toleratedSkip);
			   logger.info("****MARK5..."+foundText[1]);
			 //dc new  
			   foundText[1] = foundText[1].substring(0,foundText[1].lastIndexOf("]")+1);
	/***********************************************		   
			   if(foundText[1].replaceAll("[^\\[]","").length() < 3 && hm.get(hm.firstKey()).getTimeStart() < 1.99 && foundText[1].length() > stringMapItem.toleratedSkip && (foundText[1].substring(0,stringMapItem.toleratedSkip-1).indexOf("[") < 0 || foundText[1].substring(foundText[1].length() - stringMapItem.toleratedSkip).indexOf("[") < 0)) {
				   logger.info("****ToleratedSkip used, Rearraging foundTexts.");
				 if(foundText[1].indexOf("[") > 0) {
					// float totalSeconds = hm.get(hm.firstKey()).getTimeEnd() - hm.get(hm.firstKey()).getTimeStart();
					 float totalSeconds = hm.get(hm.firstKey()).getTimeEnd();
					  if(totalSeconds > 1) {
						  String tempStr = foundText[1];						  
                          logger.info("EstimatedMatchStrLengthtoStart: "+hm.get(hm.firstKey()).estimatedMatchStrLengthtoStart_for_Diff_Sentence());
                          int nextDoubleSpace = StringProcessing.getCloserIndex(new StringBuffer(escapeCharNewToSpace.forAUDIOTXT(foundText[1])),"  ", hm.get(hm.firstKey()).estimatedMatchStrLengthtoStart_for_Diff_Sentence());
                          logger.info("NextDoubleSpace: "+nextDoubleSpace);
                          
			//			  int nextDoubleSpace = escapeCharNewToSpace.forAUDIOTXT(foundText[1]).indexOf("  ",(int)totalSeconds*averageCharReadPerSec);
						  int nextLine = escapeCharNewToSpace.forAUDIOTXT(foundText[1]).indexOf("  ",(int)totalSeconds*averageCharReadPerSec);
						//  int nextLine = foundText[1].indexOf("\n",(int)totalSeconds*averageCharReadPerSec);
 
                          if(nextDoubleSpace < 0){
                        	  nextDoubleSpace = nextLine;
                          }
                          logger.info("EstimatedMatchStrLengthtoStart: "+hm.get(hm.firstKey()).estimatedMatchStrLengthtoStart_for_Diff_Sentence());
                          int nextSpace = StringProcessing.getCloserIndex(new StringBuffer(foundText[1])," ", hm.get(hm.firstKey()).estimatedMatchStrLengthtoStart_for_Diff_Sentence());
                          logger.info("Next: "+nextSpace);
                          
						//  int nextSpace = foundText[1].indexOf(" ",(int)totalSeconds*averageCharReadPerSec);
						  String tempSubFoundText1 = "";
						  String charSkiped = "";
						  String charRemind = "";
						  if(nextDoubleSpace > 0) {
							   charSkiped =  foundText[1].substring(0,nextDoubleSpace);
							    foundText[1] = charSkiped;
						  } else if(nextSpace > 0){
							  nextDoubleSpace = nextSpace;
							  charSkiped =  foundText[1].substring(0,nextSpace);
							  foundText[1] = charSkiped;							  
						  }
					      logger.info("********MODIFIED FOUNDTEXT[1]:"+foundText[1]);								  
						  logger.info("********CHARSKIPED:"+charSkiped);
						 String remindingStr = "";
						 if(tempStr.length() > charSkiped.length()) {
							 remindingStr = tempStr.substring(charSkiped.length());
							  logger.info("********REMINDINGSTR:"+remindingStr);
						 }
						  
					//	  foundText[1] = foundText[1].substring(0,foundText[1].indexOf("  ",(int)totalSeconds*averageCharReadPerSec));
						//   foundText[1] = foundText[1].substring(0,foundText[1].indexOf("["));
					//	  if(nextDoubleSpace > 0 && tempStr.length()>charSkiped.length()) {
  					     //dc    foundText[0] = StringProcessing.bufFilter(remindingStr+" "+foundText[0]);
						 foundText[0] = StringProcessing.bufFilter(remindingStr);
  							  logger.info("********MODIFIED FOUNDTEXT[0]:"+foundText[0]);
						//  }
                     //     return false;						  
					  } else {
						  String tempStr = foundText[1];
						  String firstWord = "";
							for (final String word : tempStr.toString().split(" ")) {
								if(word.trim().length() > 0) {
								    firstWord = word;	
					//			    logger.info("####################****: "+firstWord);
								    break;
								}
							 }
						  foundText[1] = tempStr.substring(0, tempStr.indexOf(firstWord)+firstWord.length());
  					      foundText[0] = StringProcessing.bufFilter(tempStr.substring(tempStr.indexOf(firstWord)+firstWord.length(),tempStr.indexOf("[")) + foundText[0]);
						  

    					 //   hm.get(hm.firstKey()).nextItemOutOfSync=true;

	 
					  }
					  	
				 }
			 
				   foundText[1] = "[BEGIN"+StringProcessing.getSavedMediaLengthFormated((int) (hm.get(hm.firstKey()).getTimeStart()*1000+wavCutSecCumulative))+"]"+ StringProcessing.bufFilter(foundText[1])+"[END"+StringProcessing.getSavedMediaLengthFormated((int) (hm.get(hm.firstKey()).getTimeEnd()*1000+wavCutSecCumulative))+"]";
				      hm.get(hm.firstKey()).nextItemOutOfSync = true;
				      hmFound.clear();
					  stringMapItem sMIF = null;
					  sMIF = hm.get(hm.firstKey());				      
				      hmFound.put(1, sMIF);
				      hmFound.get(1).nextItemOutOfSync = true;
				      foundText[2] =  StringProcessing.bufFilter(foundText[0] + foundText[2]);
			   }
		/* ***********************************************	   */
			   
			   if(FileProcessingNew.getFileName(finalTimedTXTFile_p).equalsIgnoreCase("INSTIME.TXT")) {
				   File testFile = new File(finalTimedTXTFile_p);  
				   if(globalConfig.insTimeFileModifiedTime == 0 || globalConfig.insTimeFileModifiedTime == testFile.lastModified()) {
					      FileProcessing.appendStringToFile(foundText[1].toString(), finalTimedTXTFile_p);
				          globalConfig.insTimeFileModifiedTime = new File(finalTimedTXTFile_p).lastModified();
						   logger.info("##############FILE APPEND OK#############");
				   } else {
					   logger.info("##############FILE MODIFIED BY OTHERS#############");					   
				       System.exit(0);
				   }
			   } else {
				   logger.info("##############first match#############");
				   FileProcessing.appendStringToFile(foundText[1].toString(), finalTimedTXTFile_p);				   
			   }
		      //break;
		      
	
		   }
		   logger.info("MARK1");
		   Iterator<Integer> hmFoundIter = hmFound.keySet().iterator(); 
		   stringMapItem sMI = null;
		      while(hmFoundIter.hasNext() ) {
				   logger.info("MARK2");

		    	  Integer sMIKey = hmFoundIter.next();
		    	  sMI = hmFound.get(sMIKey);
		 //   	  logger.info(sMIKey + " Time: "+sMI.getTimeStart()+" Position: "+sMI.beginWordBeginPosition);
		 //   	  logger.info(sMI.resultStr);
		 //   	  logger.info("Time: "+sMI.getTimeEnd()+" Position: "+sMI.endWorldEndPosition);
		      }  
		      try{
				   logger.info("MARK3");

		  //    logger.info("HIGHEST KEY VALUE IS: "+Collections.max(hmFound.keySet()));
		      sMI = hmFound.get(Collections.max(hmFound.keySet()));
		      } catch (Exception e) {
				   logger.info("MARK4");

		    	  e.printStackTrace();
		    	  return false;
		      }
			   logger.info("MARK5");

		   //   float wavCutSec = sMI.getTimeEnd();
		 // DC - FOR PRODUCTION RELEASE     
		//      float wavCutSec = (float) (sMI.getTimeEnd()-0.25);
		//      float wavCutSec = (float)Math.floor(sMI.getTimeEnd());
		      float wavCutSec = (float)(Math.round(sMI.getTimeEnd()*1000)/1000.0);
		//      logger.info("Next item out of sync flag: "+sMI.nextItemOutOfSync);
		      if(sMI.nextItemOutOfSync) {
		    	 new File(segTXTFile_p).renameTo(new File(FileProcessing.getFileNameWithOutExtension(segTXTFile_p)+(fileCounter)+".txt"));
	       	     FileProcessing.copyStringToFile(foundText[2], segTXTFile_p); 
	             wavCutSecCumulative += wavCutSec*1000;

	       	     AudioFileProcessing.processDelHead(wavClipSec, origWAVFileName, segWAVFile_p+".temp.txt", (int) (wavCutSecCumulative/1000),shiftBit);
	             if(removeProcessedWAV) {
	      		   logger.info("MARK6");

	            		 new File(segWAVFile_p_pre).delete();
	           	         logger.info("Removing: "+segWAVFile_p); 
	             } 
	             segWAVFile_p_pre = segWAVFile_p;
	      //       logger.info("Renaming: "+segWAVFile_p);
		/*    try {
	             FileProcessing.copyFileToFile(segWAVFile_p, segWAVFile_p+"_D_"+(fileCounter)+".wav");
	             new File(segWAVFile_p).delete();
		    } catch (Exception e) {e.printStackTrace();}
		    */     
	         /*    if(new File(segWAVFile_p).renameTo(new File(segWAVFile_p+"_D_"+(fileCounter)+".wav"))) {
		             logger.info("Renaming OK");	 
		    	 } else {
		             logger.info("Renaming FAIL. Old File: "+segWAVFile_p+"New File: "+segWAVFile_p+"_D_"+(fileCounter)+".wav"); 
		    	 }
		    	 */
		    	 fileCounter++;
           //      new File(segWAVFile_p).delete();
	      		   logger.info("MARK7");

	       	     if(FileProcessing.renameFile(segWAVFile_p+".temp.txt", FileProcessing.getFileNameWithOutExtension(segWAVFileOrig)+fileCounter+".wav")) {
		      		   logger.info("MARK8");

	       	        return true;
	             }
	      		   logger.info("MARK9");

		      }
     		   logger.info("MARK10");

		     // FileProcessing.appendStringToFile(outputStrBuff.toString(), finalTimedTXTFile_p);		      
		      
		   //   FileProcessing.appendStringToFile("READINGBEE0: "+foundText[0].toString()+"\nEdited by Readingbee.com", finalTimedTXTFile_p);		      
		    //  FileProcessing.appendStringToFile("READINGBEE1: "+foundText[1].toString()+"\nEdited by Readingbee.com", finalTimedTXTFile_p);		      
		     // FileProcessing.appendStringToFile(foundText[2].toString()+"\nEdited by Readingbee.com", finalTimedTXTFile_p);
		//      logger.info("Main done "+outputStrBuff);		
				
		return false;
	}
}
