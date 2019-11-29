package com.rb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.log4j.Logger;

import alignment.AlignMainNew;

import com.rb.stringMapItem;
import com.rb.util.StringProcessing;
import com.rb.util.globalConfig;
import com.rb.util.audio.FileProcessing;

import edu.cmu.sphinx.demo.aligner.LongAudioAlignerNew;


public class TextAlign {
	private static int recongnizerWhileLoopCounter = globalConfig.recongnizerWhileLoopCounter;
	final static Logger logger  = Logger.getLogger(AlignMainNew.class);

	private static void usage() {
		final String prog = TextAlign.class.getName();
		System.out.format("Usage: %s audio.wav text.txt path/to/wsj\n", prog);
		System.out.format("Example: %s data\\audio.wav data\\text.txt C:\\sphinx4\\models\\acoustic\\wsj", prog);
	}
	
	private static String readFile(final File file) throws FileNotFoundException {
		final InputStreamReader in = new InputStreamReader(new FileInputStream(file));
		final BufferedReader reader = new BufferedReader(in);
		final StringBuilder buff = new StringBuilder();
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				buff.append(line);
				buff.append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//	System.out.println(buff.toString());
		return buff.toString();
	}
	

	
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
		String origTxt = FileProcessing.fileToStringBufferBr("C:/java/long_audio_aligner/TestFiles/bug_report/VERSEPARSEISSUE.txt").toString();
		
		SortedMap<Integer, stringMapItem> hm = processFileOnceLongAudio("C:/java/long_audio_aligner/TestFiles/bug_report/VERSEPARSEISSUE.wav",origTxt,"C:/java/auto_cue_sphinx/ProcessAudio_share/ProcessAudio_share/sphinx4-1.0beta4/models/acoustic/wsj","C:/java/long_audio_aligner/TestFiles/bug_report/DELETE_ME_C_",true);
		System.out.println("bye");
	//	processFile("C:/TEMP/test/seg/a5_out62.wav","C:/TEMP/test/artpublicspeaking_29_carnagey_esenwein/artpublicspeaking_05_carnagey_esenwein3.txt","C:/java/auto_cue_sphinx/ProcessAudio_share/ProcessAudio_share/sphinx4-1.0beta4/models/acoustic/wsj","c:/TEMP/B_");
	//	System.out.println("bye");
	}

	public static SortedMap<Integer, stringMapItem> processFileOnceLongAudio(String input16bit16kHzWav, String txtContentOrig, String inputWSJ,String outPutFilePath,boolean useOldFindReplace) throws IOException, UnsupportedAudioFileException {
        SortedMap<Integer, stringMapItem> hm = new TreeMap<Integer, stringMapItem>();
		String[] matchResults = new String[2];	
		boolean isFirstSentence = true;
		
        logger.info(" *****txtContentOrigLength:"+txtContentOrig.length());
		LongAudioAlignerNew.getMatchResults(input16bit16kHzWav, txtContentOrig, matchResults);
		   FileProcessing.appendStringToFile(matchResults[1]+" ||1 ", outPutFilePath+StringProcessing.getFileNameWithOutExtension(StringProcessing.getFileName(input16bit16kHzWav))+"_NT_1.txt");
		   FileProcessing.appendStringToFile(matchResults[0]+" ||2 ", outPutFilePath+StringProcessing.getFileNameWithOutExtension(StringProcessing.getFileName(input16bit16kHzWav))+"_T_1.txt"); 
		matchResults[0] = LongAudioAlignerNew.filterResult(matchResults[0]);	
	    matchResults[1] = LongAudioAlignerNew.filterResult(matchResults[1]);
	    
		   FileProcessing.appendStringToFile(matchResults[1]+" ||3 ", outPutFilePath+StringProcessing.getFileNameWithOutExtension(StringProcessing.getFileName(input16bit16kHzWav))+"_NT_2.txt");
		   FileProcessing.appendStringToFile(matchResults[0]+" ||4 ", outPutFilePath+StringProcessing.getFileNameWithOutExtension(StringProcessing.getFileName(input16bit16kHzWav))+"_T_2.txt"); 
   
	    if(matchResults[0] != null && matchResults[0].length() > 5) {
	       hm.put(1, new stringMapItem(matchResults[1],matchResults[0],isFirstSentence,txtContentOrig,useOldFindReplace));
	    }
	    
		   FileProcessing.appendStringToFile(" ||32 ", outPutFilePath+StringProcessing.getFileNameWithOutExtension(StringProcessing.getFileName(input16bit16kHzWav))+"_NT_3.txt");
		   FileProcessing.appendStringToFile(" ||42 ", outPutFilePath+StringProcessing.getFileNameWithOutExtension(StringProcessing.getFileName(input16bit16kHzWav))+"_T_3.txt"); 
   
	//	System.out.println("Timed Result: "+(matchResults[0])+"\n");	
	    return hm;
	}


}

