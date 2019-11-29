package com.rb.util.audio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FileProcessing {
	
    public static void main(String[] args) {
    	//String sStartingDir = "C:\\tomcat5.0.30_old\\webapps\\ROOT\\virusfolder\\english\\";
    	String sStartingDir = "C:/tomcat5.0.30_old/webapps/ROOT/audiobook/ADVANCE_SPEECHES_LRC/";
    	    	String replaceStartingDir = "http://freebooks.readingbee.com/audiobook/";

    	//String replaceStartingDir = "http://10.0.2.2:8081/audiobook/VOA_LRC/";
    	File aStartingDir = new File(sStartingDir);
    	FilenameFilter filter = null;
    	List<String> htmlFiles = new ArrayList<String>();
    	List<String> imageFilelist = new ArrayList<String>();
    	try {
    	   getFileListing(aStartingDir, filter,htmlFiles, imageFilelist);
   	    Iterator<String> stringIter;
   	    String dirNfileName;
        
//if(globalConstants.applicationType == globalConstants.pptApp) {  
	    stringIter = htmlFiles.iterator();
        while( stringIter.hasNext() ) {
        	dirNfileName = stringIter.next();
            System.out.println(dirNfileName.replace('\\','/').replace(" ", "%20").replaceFirst(sStartingDir, replaceStartingDir)+";Online");
        }

    	   
    	   
    	} catch (Exception e) {
    		e.printStackTrace();
 	       System.out.println("no good");    		
    	}    	
	       System.out.println("ok");
	}
    public static boolean renameFile(String file1_p, String file2_p) {
    	 File file1 = new File(file1_p);
		   File file2 = new File(file2_p);
  	   if(file1.exists() && file1.renameTo(file2)) {
                 return true;
	       } else {
	    		 return false;
  	       }	
    }
    
	public static void writeRandomAccessFileHeader(short data[], String fileName) {
		 //   BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		 File file = new File(fileName);
		  if(!file.exists())
		{
		return;
		 }
		try{
		    //Open the file for both reading and writing
		   RandomAccessFile rand = new RandomAccessFile(file,"rw"); 
		   rand.seek(0);  //Seek to end of file
		   for (int i=0; i<data.length; i++){
		      rand.writeShort(data[i]);
		   }
		   // rand.writeBytes("Roseindia.net,");  //Write end of file
		    rand.close();
		}
		catch(IOException e)
		{
		    e.printStackTrace();
		}

		}
	
    /**
	  * Recursively walk a directory tree and return a List of all
	  * Files found; the List is sorted using File.compareTo.
	  *
	  * @param aStartingDir is a valid directory, which can be read.
	  */
	  public static ArrayList<File> getFileListing( File aStartingDir, FilenameFilter filter, List<String> htmlFiles, List<String> imageFilelist) throws FileNotFoundException{
	    validateDirectory(aStartingDir);
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
	      if(Mime.isMusicFiles(fileNameUpper)) {   	  
	    	      htmlFiles.add(file.toString());
	    	      File newTXTfile = new File(getFileNameWithOutExtension(file.getAbsolutePath()) + ".txt");

	    	      if(!newTXTfile.exists()) {
	    	    	  try{ 
	    	    	  newTXTfile.createNewFile();
	    	    	  System.out.println("Creating: "+newTXTfile.getAbsolutePath());
	    	    	  } catch (Exception e) {}
	    	      }
	    	  
	      } else if(Mime.isImageFiles(fileNameUpper)){
	    	  imageFilelist.add(file.toString());
	      } else {
	         result.add(file); //always add, even if directory
	      }
	    }
	     filesIter = dirOnly.iterator();
	     file = null;
		    while ( filesIter.hasNext() ) {
			      file = (File)filesIter.next();
			        List<File> deeperList = getFileListing(file, filter,htmlFiles,imageFilelist);
			        result.addAll(deeperList);
		    }
	    Collections.sort(result);
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
		 
		  
		   public static String getFileNameWithOutExtension(String filename_in) {
			   if(filename_in == null) return "";
			   String filename_out = filename_in;
			   if(filename_in.lastIndexOf('.') > 0) {
			      filename_out = filename_in.substring(0, filename_in.lastIndexOf('.'));
			   }
			   return filename_out;
		   }

	public static boolean isLocal(String uri) {
		if (uri != null && !uri.startsWith("http://")) {
			return true;
		}
		return false;
	}

	
	public static String getExtension(String uri) {
		if (uri == null) {
			return null;
		}

		int dot = uri.lastIndexOf(".");
		if (dot >= 0) {
			return uri.substring(dot);
		} else {
			return "";
		}
	}
    
    
	  public static void appendFileToFileBinary(String inFile, String outFile) throws Exception {
		    
		  
		    FileReader fr = new FileReader(inFile);
		    BufferedReader in = new BufferedReader(fr);

		    FileWriter fw= new FileWriter(outFile, true);
		    BufferedWriter buff = new BufferedWriter(fw);
		        
		    boolean eof = false;
		  //  String line;
		    int sLine;

		    try {
		         while (!eof) {
		        	 sLine = in.read();
		             buff.write(sLine);         
		         }
		         in.close();
		         fr.close();
		         
		         buff.close();
		         fw.close();
		     } catch (Exception e) {
			 e.printStackTrace();
		     }
		            
		   }  // end procedure
	  public static int appendFileToFileBinary(String inFile, String outFile, long start, long end) throws Exception {
		    
		    int totalBitWritten = 0;
		    FileReader fr = new FileReader(inFile);
		    BufferedReader in = new BufferedReader(fr);

		    FileWriter fw= new FileWriter(outFile, true);
		    BufferedWriter buff = new BufferedWriter(fw);
		        
		    boolean eof = false;
		  //  String line;
		    int sLine;
            long pCounter = 0;
            
		    try {
		         while (!eof && pCounter < end) {
		        	 sLine = in.read();
		        	 pCounter++;

		        	   if(pCounter >= start && sLine >= 0) {
		                  buff.write(sLine);
		                  totalBitWritten++;
		            	} else if (sLine < 0) {
		            		break;
		            	}

		         }
		         in.close();
		         fr.close();
		         
		         buff.close();
		         fw.close();
		     } catch (Exception e) {
			 e.printStackTrace();
			 return 0;
		     }
		     return totalBitWritten;
		            
		   }  // end procedure
	  
	  
    public static void copyFileToFile(String inFile, String outFile, int start, int end) throws Exception {
			    FileReader fr = new FileReader(inFile);
			    BufferedReader in = new BufferedReader(fr);

			    FileWriter fw= new FileWriter(outFile, false);
			    BufferedWriter buff = new BufferedWriter(fw);
			        
			    boolean eof = false;
			    String line;
			    int oneInt;
                int pCounter = 0;
			    try {
			         while (!eof && pCounter++ < end) {
			        	 
			          //  line = in.readLine();
			        	 oneInt = in.read();
			          //  if(line == null) eof = true;
			            
			            	if(pCounter >= start) {
			            	 //  buff.write(line);  
			            		buff.write(oneInt);
			            	};
			            
			         }
			         in.close();
			         fr.close();
			         
			         buff.close();
			         fw.close();
			     } catch (Exception e) {
				 e.printStackTrace();
			     }
			            
			   }  // end procedure


		   
		   public static void copyFileToFile(String inFile, String outFile) throws Exception {
		    FileReader fr = new FileReader(inFile);
		    BufferedReader in = new BufferedReader(fr);

		    FileWriter fw= new FileWriter(outFile, false);
		    BufferedWriter buff = new BufferedWriter(fw);
		        
		    boolean eof = false;
		    String line;

		    try {
		         while (!eof) {
		            line = in.readLine();
		            if(line == null) eof = true;
		            else buff.write(line+"\r\n");         
		         }
		         in.close();
		         fr.close();
		         
		         buff.close();
		         fw.close();
		     } catch (Exception e) {
			 e.printStackTrace();
		     }
		            
		   }  // end procedure

			  public static void copyStringToFile( String args1, String outFile ) throws IOException {
				    File file = new File(outFile);
				    if(!file.exists()) {
				    	file.createNewFile();
				    }
				    FileWriter fw= new FileWriter(outFile, false);
				    BufferedWriter buff = new BufferedWriter(fw);
				    try {
				         buff.write(args1);       
				        
				         buff.close();
				         fw.close();
				     } catch (Exception e) {
					 e.printStackTrace();
				     }
				  } // end procedure

		  public static void appendStringToFile( String args1, String outFile ) throws IOException {
		  	  
		    FileWriter fw= new FileWriter(outFile, true);
		    BufferedWriter buff = new BufferedWriter(fw);
		            
		    try {
		         buff.write(args1);       
		        
		         buff.close();
		         fw.close();
		     } catch (Exception e) {
			 e.printStackTrace();
		     }
		            
		  }  // end procedure
		  
		    public static StringBuffer fileToStringBufferBr( String inFile) {
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
		
				        /*    if(line.contains("“")) {
				            	line = line.replace('“', '#');
				            }
				            if(line.contains("”")) {
				            	line = line.replace('”', '#');
				            }
				            if(line.contains("’")) {
				            	line = line.replace('’', '#');
				            }		            	
				            if(line.contains("\u0092")) {
				            	line = line.replace('\u0092', '#');
				            }
				            if(line.contains("\u0093")) {
				            	line = line.replace('\u0093', '#');
				            }
				            if(line.contains("\u0094")) {
				            	line = line.replace('\u0094', '#');
				            }		            	
			            	*/
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
			    

}
