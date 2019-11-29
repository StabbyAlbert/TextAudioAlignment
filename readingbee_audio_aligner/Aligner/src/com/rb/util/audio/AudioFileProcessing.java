package com.rb.util.audio;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioFileProcessing {
	   public final static int defaultSkip = 45;
	public static void main(String[] args) {
		   float segmentSec = (float)30;
    	//   String inputFile = "c:/TEMP/test/seg/artpublicspeaking_08_carnagey_esenwein.wav";
		   String inputFile = "C:/Program Files/MP3 WAV Converter/Wave Files/voa3.wav";
		   String outputFile = "C:/Program Files/MP3 WAV Converter/Wave Files/voa4.wav";
	       processDelHead(11,inputFile,outputFile,segmentSec,0);	   
	       System.out.println("done");
	   }
	
	
	/* 
	 * This method takes an audio file (16bit per sample, 16khz mono) and output a
	 *  truncated one with head segment cut out
	 * as specified by the number of seconds in segmentSec 
	 * 
	 * 
	 */
	
	public static void processDelHead(int endTime, String inputFile, String outputFile, float segmentSec, int shiftBit_in) {
		  try {
			   int segmentCounter = 1;
			   int sampleFrequency = 16000;			 
			   float startTime = segmentCounter*segmentSec;
			  // int endTime = startTime + segmentSec
			  // int endTime = 120;   // 3600 = 1 hour
			  // int endTime = 36000; 
			//   int endTime = 30; 
			   long startSubBit = (int)(startTime*2*sampleFrequency+defaultSkip);
			   long endSubBit = startSubBit+endTime*2*sampleFrequency;
			   int shiftBit = shiftBit_in;
if(shiftBit_in == 2) {
	shiftBit = 0;
	if(segmentSec > (8*60+44)){
		shiftBit = 1;
		System.out.print("Shift By 1");
	} else {
		System.out.print("No Shift");
	}
}

			   
			// String inputFile = "c:/TEMP/test/seg/artpublicspeaking_06_carnagey_esenwein.wav";
            // FileProcessing.copyFileToFile("c:/TEMP/test/audio_2.wav", "c"c:/TEMP/test/a5_2.wav":/TEMP/test/a5.wav", 0, 44);
			   DataOutputStream dataOutputAudioStream = null;
	           long totalBit = (endSubBit - startSubBit+1);
	           // DC change from while to if
	           if(totalBit >= (endSubBit - startSubBit+1)) {
	            startTime = segmentCounter*segmentSec;
	            //  endTime = startTime + segmentSec;
	            startSubBit = (long) (startTime*2*sampleFrequency+defaultSkip);
				   if(shiftBit > 0) {
					   startSubBit+= shiftBit;
					   System.out.println("PHASE SHIFT BY: "+shiftBit);
					   
				   } else {
					   System.out.println("NO PHASE SHIFT");
					   
				   }
	            
	            endSubBit = startSubBit+endTime*2*sampleFrequency;
				dataOutputAudioStream = null;
				try {
					File audio_file = new File(outputFile);			
					audio_file.createNewFile();
					// bufferedAudioStream  = new BufferedOutputStream(new FileOutputStream(audio_file));
					dataOutputAudioStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(audio_file)));
					WaveFormatHeader wfh = new WaveFormatHeader(sampleFrequency,totalBit);
					short headerdata[] = wfh.getWaveHeaderData();
					for(int i = 0; i<headerdata.length; i++) {
					dataOutputAudioStream.writeShort(headerdata[i]);
					dataOutputAudioStream.close();
					}
				} catch (IOException e) {
					dataOutputAudioStream.close();
					e.printStackTrace();
				}		
			  totalBit =  FileProcessing.appendFileToFileBinary(inputFile,outputFile,startSubBit,endSubBit);
		      FileProcessing.writeRandomAccessFileHeader(new WaveFormatHeader(sampleFrequency,totalBit-45).getWaveHeaderData(),outputFile);
	          segmentCounter++;
	     	  System.out.println("TotalBit:"+ totalBit + "start: "+startSubBit+" end: "+endSubBit);
	          }
	           
			  System.out.println("Audio File SEGMENT CREATED... ");

		   } catch (Exception e) {
			   
			   
		   };
		
	}

}
