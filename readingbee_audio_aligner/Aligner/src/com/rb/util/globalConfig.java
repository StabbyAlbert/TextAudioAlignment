package com.rb.util;

public class globalConfig {

	
	/************** AlignMainBase.java ****************/
	public static boolean exitAfterOneLoop = false; 

	//   public static final   boolean isDebugMode = false;   // default false = debug off   true = debug on
//	public static final   boolean isDebugModeSkipProcessing = false;  // default false

//	public static final   boolean isDebugMode = true;   // default false = debug off   true = debug on
//	public static final   boolean isDebugModeSkipProcessing = true;  // default false
	
	/************** TextAlign.java ****************/
	public static int recongnizerWhileLoopCounter = 15;
	

//	public static final boolean removeProcessedWAV = false;
	public static final boolean removeProcessedWAV = true;
	
	public static boolean skipFirstMatch = false;   // if set to true, final file will skip first match found
	public static final String sphinxModelPath = "C:/java/auto_cue_sphinx/ProcessAudio_share/ProcessAudio_share/sphinx4-1.0beta4/models/acoustic/wsj";
//	public static final String sphinxModelPath = "../sphinx4-1.0beta4/models/acoustic/wsj";

	/************ stringMapItem.java  ************/
	public static final int toleratedSkip = 30;
//	private static final int toleratedTimeFastFoward = 2;
//	public static final double speechSpeedCharPerSec = 14;
	public static final int toleratedTimeFastFoward = 3;
	public static double speechSpeedCharPerSec = 15;
	
	//
	public static long insTimeFileModifiedTime = 0;

}
