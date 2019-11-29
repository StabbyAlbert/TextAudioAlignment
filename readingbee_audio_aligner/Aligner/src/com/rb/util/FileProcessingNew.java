package com.rb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

import org.apache.log4j.Logger;

//port org.apache.logging.log4j.Level;

import com.rb.AlignTXTFinalStep;
import com.rb.alignTXTFinal;
import com.rb.util.audio.FileProcessing;
import com.rb.util.audio.Mime;

public class FileProcessingNew {
	final static Logger logger  = Logger.getLogger(FileProcessingNew.class);
	public static void main(String [] args) {
		logger.info("TEST LOGGER");
		logger.debug("debugging");
	    bkFileCopyFileReturnString("C:/java/long_audio_aligner/TestFiles/TESTBEGIN/InsTime.txt", "C:/java/long_audio_aligner/TestFiles/TESTBEGIN/artpublicspeaking_20_carnagey_esenwein.txt","C:/java/long_audio_aligner/TestFiles/TESTBEGIN/h.txt");	
	//ogger.info("TEST LOGGER done");
//logger.log(arg0, arg1)
//logger.error("heeello");
	    
	    logger.error("eeeerrrorr");
	  //logger.
	    
	}
	
	public static String getFilePath(String curdirfile) {
		String curdir = curdirfile.substring(0,curdirfile.indexOf(getFileName(curdirfile)));
		if (curdir == null) return "";		
		return curdir;
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
	
	// SET TO PRIVATE TO PROTECT THIS METHOD FROM UNAUTHORIZED USAGE
	private static boolean removeOldFiles(String filePath, String fileFilterToRetain) {
    	FilenameFilter filter = null;
    	List<String> txtFiles = new ArrayList<String>();
    	List<String> wavFilelist = new ArrayList<String>();
		File aStartingDir = new File(filePath);
  	   logger.info("***Removing temp file from Path: " + filePath);

    	try {
     	   getFileListing(aStartingDir, filter,txtFiles, wavFilelist);
    	    Iterator<String> stringIter;
    	    String dirNfileName;
         
 //if(globalConstants.applicationType == globalConstants.pptApp) {  
 	    stringIter = txtFiles.iterator();
         while( stringIter.hasNext() ) {
         	dirNfileName = stringIter.next();
         	if(!dirNfileName.contains(fileFilterToRetain)) {   
         	   logger.info("removing temp file: " + dirNfileName);
         	   new File(dirNfileName).delete();
         	}
         }

  	    stringIter = wavFilelist.iterator();
        while( stringIter.hasNext() ) {
        	dirNfileName = stringIter.next();
        	if(!dirNfileName.contains(fileFilterToRetain)) {         		
          	   logger.info("removing temp file: " + dirNfileName);
        		new File(dirNfileName).delete();
        	}
        }
     	   
     	   
     	} catch (Exception e) {
     		e.printStackTrace();
  	       System.out.println("no good");    		
     	}    	
 	       System.out.println("ok");
		
		return true;
	}
	
	public static String bkFileCopyFileReturnString(String raw_TimedFile_in, String origTXT_Processing,String origTXT_Initial) {
		String backedUpFileName = FileProcessing.getFileNameWithOutExtension(raw_TimedFile_in)+".bk.txt";
		String backedUpFileName2 = FileProcessing.getFileNameWithOutExtension(raw_TimedFile_in)+".bk2.txt";
		

		
		StringBuffer timedFileStr = FileProcessing.fileToStringBufferBr(raw_TimedFile_in);
	/*	if(timedFileStr.indexOf("]~") < 0) {
			removeOldFiles(getFilePath(raw_TimedFile_in), getFileName(raw_TimedFile_in));

			System.out.println("NOTHING TO PROCESS.. TIMEDFILE NOT MARKED.");
			return "ERROR";
		} */
		logger.info("Removing tempary files from path" + getFilePath(raw_TimedFile_in));
		removeOldFiles(getFilePath(raw_TimedFile_in), getFileName(raw_TimedFile_in));
		logger.info("Done removing tempary files");

		try{
		   FileProcessing.copyFileToFile(raw_TimedFile_in, backedUpFileName);
		   String headString = fileToStringBufferBr_getHead(backedUpFileName).toString();
		   if(headString.length() > 0) {
		         FileProcessing.copyStringToFile(fileToStringBufferBr_getHead(backedUpFileName).toString(), raw_TimedFile_in);
		   } else {
			   System.out.println("##############ERROR#############");
		   }
		} catch (Exception e){
		   e.printStackTrace();
		}
		SortedMap<Integer, alignTXTFinal> hmFinal = null;
		hmFinal = AlignTXTFinalStep.getHMInProcessedOrigTXT(raw_TimedFile_in);
		try {
		     String foundText[] = new String[3];
		     foundText[0] = "";  // return found string
		     foundText[1] = "";  // return previous string before found string
		     foundText[2] = "";  // return next string after found string 
		     String headString = AlignTXTFinalStep.AlignOrigFileToHashMap(foundText,origTXT_Initial,hmFinal).toString();
			 if(headString.length() > 0) {		     
		        FileProcessing.copyStringToFile(headString,backedUpFileName2);
			 }
		     headString = hmFinal.get(Collections.max(hmFinal.keySet())).getTimeEndStr()+foundText[2];
			 if(headString.length() > 0) {
		         FileProcessing.copyStringToFile(headString,origTXT_Processing);
			 }
		     
		} catch (Exception e){
		   e.printStackTrace();
		}
		
		return "";
	}
	
    public static StringBuffer fileToStringBufferBr_getHead( String inFile) {
	    FileReader fr = null;
	    BufferedReader in = null;
	    StringBuffer buf = new StringBuffer();
	    boolean eof = false;
	    String line;
        int counter = 0;
	    try {
	        fr = new FileReader(inFile);
	        in = new BufferedReader(fr);
	        while (!eof && counter < 5000) {
	        	counter++;
	            line = in.readLine();	          
	            if(line == null) eof = true;
	            else {
	            	String newLine = line;
	            	if(newLine.contains("]~")){
	            		line = newLine.substring(0,newLine.lastIndexOf("]~")+1);
	            		eof = true;
	            	}
	            	buf.append(line).append("\r\n");
	            }
	         }
	         in.close();
	         fr.close();
	      } catch (IOException ioe) {
		 ioe.printStackTrace();
	      }
	          return buf;
	    }
	    
    public static boolean checkNCreateFile(String origTXT_Initial,String checkFile) {
    	File checkFileFile = new File(checkFile);
    	if(!checkFileFile.exists()) {
    		try {
    		FileProcessing.copyFileToFile(origTXT_Initial, checkFile);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
    	return true;
    }
    
    /**
	  * Recursively walk a directory tree and return a List of all
	  * Files found; the List is sorted using File.compareTo.
	  *
	  * @param aStartingDir is a valid directory, which can be read.
	  */
	  public static ArrayList<File> getFileListing( File aStartingDir, FilenameFilter filter, List<String> htmlFiles, List<String> imageFilelist) throws FileNotFoundException{
		  try{
	    validateDirectory(aStartingDir);
		  } catch (Exception e) {}
		  logger.info("Entering directory: " + aStartingDir);
	    ArrayList<File> result = new ArrayList<File>();
	File[] filesAndDirs = null; 
	List<File> filesDirs = null;
	List<File> dirOnly = null;
	    try {
	      filesAndDirs = aStartingDir.listFiles(filter);
	      filesDirs = Arrays.asList(filesAndDirs); 
	      FileFilter dirFilter = new FileFilter() {
	            public boolean accept(File dir) {
	                return dir.isDirectory();
	            }
	        };
	        filesAndDirs = aStartingDir.listFiles(dirFilter);
	        dirOnly = Arrays.asList(filesAndDirs);
	    } catch (NullPointerException iee) {
	      return result;
	    };
	    
	   // List filesDirs = Arrays.asList(filesAndDirs);
	   // System.out.println(aStartingDir.listFiles());    
	    Iterator<File> filesIter = filesDirs.iterator();
	    File file = null;
	    
	    String fileNameUpper;
	    while ( filesIter.hasNext() ) {
	      file = (File) filesIter.next();

	      fileNameUpper = file.getName().toUpperCase();
	      if(Mime.isLyricFiles(fileNameUpper)) {   	  
	    	      htmlFiles.add(file.toString());
	      } else if(Mime.isMusicFiles(fileNameUpper)){
	    	  imageFilelist.add(file.toString());
	      } else {
	         result.add(file); //always add, even if directory
	      }
	    }
	    /* THIS PROGRAM DOES NOT NEED TO BE RECURSIVE FOR IT'S TOO DANGEROUS WHEN USING THIS METHOD TO FIND FILES TO REMOVE
	     filesIter = dirOnly.iterator();
	     file = null;
		    while ( filesIter.hasNext() ) {
			      file = (File)filesIter.next();
			        List<File> deeperList = getFileListing(file, filter,htmlFiles,imageFilelist);
			        result.addAll(deeperList);
		    }
		    */
	    Collections.sort(result);
		  logger.info("Exiting directory: " + aStartingDir);

	    return result;
	  }
	  
		 /**
		  * Directory is valid if it exists, does not represent a file, and can be read.
		  */
		  private static void validateDirectory (File aDirectory) throws FileNotFoundException {
		    if (aDirectory == null) {
		      throw new IllegalArgumentException("Directory should not be null.");
		    }
		    if (!aDirectory.exists()) {
		      throw new FileNotFoundException("Directory does not exist: " + aDirectory);
		    }
		    if (!aDirectory.isDirectory()) {
		      throw new IllegalArgumentException("Is not a directory: " + aDirectory);
		    }
		    if (!aDirectory.canRead()) {
		      throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
		    }
		  }
	  
	
	
}
