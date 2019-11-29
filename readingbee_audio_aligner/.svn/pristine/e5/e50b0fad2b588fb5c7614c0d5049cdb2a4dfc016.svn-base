package com.rb.util.audio;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 *  HOW TO USE - CALL THE CONSTRUCTOR WaveFormatHeader(int sampleRate_, int audio_data_size_)
 *  Then call getWaveHeaderData() to get the header data, then append wav data behind it to
 *  create a sound file.
 *
 */

public class WaveFormatHeader {
	/*
	 * Dynamic Parameters for WAVE files:
	 * 4 ChunkSize -- setChunkSizeNSubchunk2SizeData(int subChunk2Size_)
	 * 24 SampleRate -- setSampleRateNByteRateData(int sampleRate_)
	 * 28 ByteRate = SampleRate*NumChannels*BitsPerSample/8; -- setSampleRateNByteRateData(int sampleRate_)
	 * 40 Subchunk2Size = data in bytes -- setChunkSizeNSubchunk2SizeData(int subChunk2Size_)
	 * 
	 */
	 private final static int numChannels = 1;
	 private int sampleRate = 16000;
	 private long subchunk2Size = 0;
	 private final static int bitsPerSample = 16; 
	// private final static int blockAlign = numChannels*bitsPerSample;

	 private short[] waveHeaderData = {
			   (short)0x5249
			   ,(short)0x4646
			   ,(short)0x54E8
			   ,(short)0x0200
			   ,(short)0x5741
			   ,(short)0x5645
			   ,(short)0x666D
			   ,(short)0x7420
			   ,(short)0x1000
			   ,(short)0x0000
			   ,(short)0x0100
			   ,(short)0x0100
			   ,(short)0x44AC
			   ,(short)0x0000
			   ,(short)0x8858
			   ,(short)0x0100
			   ,(short)0x0200
			   ,(short)0x1000
			   ,(short)0x6461
			   ,(short)0x7461
			   ,(short)0x30E8
			   ,(short)0x0200
			   };
public short[] getWaveHeaderData() {
	return waveHeaderData;
}

//  public WaveFormatHeader(){};

	 public WaveFormatHeader(int sampleRate_, long audio_data_size_){
		 setSampleRate(sampleRate_);
		 setSubchunk2Size(audio_data_size_);
	 };
/*
   public static void main(String[] args) {

         WaveFormatHeader hexsave = new WaveFormatHeader(44100,1024);
         hexsave.createHeaderFileUsingCurrentWaveHeaderData("/TEMP/test3.hex");
        		 
   }
  */ 
   
   
   // Set SubChunk2Size and ChunkSize
   public boolean setChunkSizeNSubchunk2SizeData(long subChunk2Size_) {
// setting SubChunk2Size	   
	   short temp_1 = (short) (subChunk2Size_  & 0xffff);
	   temp_1 = swapShortToLittleEnd(temp_1);
	   short temp_2 = (short) (subChunk2Size_>>16 & 0xffff);
	   temp_2 = swapShortToLittleEnd(temp_2);

	   swapData(20,temp_1);
	   swapData(21,temp_2);
	   
// setting ChunkSize
	   subChunk2Size_ += 36; 
	   temp_1 = (short) (subChunk2Size_  & 0xffff);
	   temp_1 = swapShortToLittleEnd(temp_1);
	   temp_2 = (short) (subChunk2Size_>>16 & 0xffff);
	   temp_2 = swapShortToLittleEnd(temp_2);
	   swapData(2,temp_1);
	   swapData(3,temp_2);	   
	   
	   return  true;
   }
   public boolean setSampleRateNByteRateData(int sampleRate_) {
		// setting SubChunk2Size	   
			   short temp_1 = (short) (sampleRate_  & 0xffff);
			   temp_1 = swapShortToLittleEnd(temp_1);
			   short temp_2 = (short) (sampleRate_>>16 & 0xffff);
			   temp_2 = swapShortToLittleEnd(temp_2);
			   swapData(12,temp_1);
			   swapData(13,temp_2);
			   int byteRate = getSampleRate()*numChannels*bitsPerSample/8;
			   temp_1 = (short) (byteRate  & 0xffff);
			   temp_1 = swapShortToLittleEnd(temp_1);
			   temp_2 = (short) (byteRate>>16 & 0xffff);
			   temp_2 = swapShortToLittleEnd(temp_2);
			   swapData(14,temp_1);
			   swapData(15,temp_2);			   
			   return true;
	   }
	   

   
 
   // position start from 0
   public static short swapShortToLittleEnd(short hex_) {
	   short hex = hex_;
	   byte subChunk2Size_b2=(byte) ((hex>>8) & 0xff);
	  return (short) ((hex_<<8) | (subChunk2Size_b2 & 0xff));
   }
   
   // position start from 0
  private boolean swapData(int position_, short hex_) {
	  if(position_ >= waveHeaderData.length) return false;
	  
	  waveHeaderData[position_] = hex_;
	  
	  return true;
  }



public void setSubchunk2Size(long subchunk2Size_) {
	subchunk2Size = subchunk2Size_;
	setChunkSizeNSubchunk2SizeData(subchunk2Size);
}



public long getSubchunk2Size() {
	return subchunk2Size;
}



private void setSampleRate(int sampleRate_) {
	sampleRate = sampleRate_;
	setSampleRateNByteRateData(sampleRate);
}


private int getSampleRate() {
	return sampleRate;
}

public void createHeaderFileUsingCurrentWaveHeaderData(String file_) {
	File file = new File(file_);
	   try{
		   BufferedOutputStream buffoswav = new BufferedOutputStream(new FileOutputStream(file));
		   DataOutputStream doswav = new DataOutputStream(buffoswav);
	       for(int i = 0; i<waveHeaderData.length; i++) {
	          doswav.writeShort(waveHeaderData[i]);
	       }
	      
	       doswav.close();
	       buffoswav.close();
	       
	   }catch (Exception e) {	
		   e.printStackTrace();
	   }
	
}   
   
}