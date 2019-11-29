package com.rb.util.audio;
import java.util.HashMap;
import java.util.Map;

public class Mime {

	private Map<String, String> mMimeTypes;

	public Mime() {
		mMimeTypes = new HashMap<String,String>();
	}
	
	public void put(String type, String extension) {
		extension = extension.toLowerCase();
		
		mMimeTypes.put(type, extension);
	}
	
	
	public String getMimeType(String filename) {
		
		String extension = FileProcessing.getExtension(filename);
		
		extension = extension.toLowerCase();
		
		String mimetype = mMimeTypes.get(extension);
		
		return mimetype;
	}
	
	public static boolean isImageFiles(String cap_name_in){
	    return (cap_name_in.endsWith(".PNG")
                || cap_name_in.endsWith(".JPG")
	    		);
	}

	public static boolean isHtmlFiles(String cap_name_in){
	    return (cap_name_in.endsWith(".HTM")
                || cap_name_in.endsWith(".HTML")
	    		);
	}
	
	public static boolean isLyricFiles(String cap_name_in){
	    return (cap_name_in.endsWith(".TXT") || cap_name_in.endsWith(".LRC")  
	        );
	}
	public static boolean isMusicFiles(String name_in){
		
    	String name = name_in.toUpperCase();

	return (name.endsWith(".MP3") 
    		|| name.endsWith(".WAV") 
     		|| name.endsWith(".AAC")        		
    		|| name.endsWith(".WMA")
    		|| name.endsWith(".RA")
    		|| name.endsWith(".3GP")     		
    		

	        );
	}

	public static boolean isVideoFiles(String name_in){
    	String name = name_in.toUpperCase();
        return ( 
        		name.endsWith(".WMA") 
        		|| name.endsWith(".MP2")
        		|| name.endsWith(".MP4")        		
        		|| name.endsWith(".M4V")        	        		
        		|| name.endsWith(".3GP") 
        		|| name.endsWith(".RM")
        		|| name.endsWith(".RMD")
        		|| name.endsWith(".RMJ")
        		|| name.endsWith(".RMS")
        		|| name.endsWith(".MND")
        		|| name.endsWith(".RMC")
        		|| name.endsWith(".RMVB")
        		|| name.endsWith(".MNS")
        		|| name.endsWith(".MRC")
        		|| name.endsWith(".RAX")
        		|| name.endsWith(".RVX")
        		|| name.endsWith(".RV")
        		|| name.endsWith(".MPA")
        		|| name.endsWith(".MP1")
        		|| name.endsWith(".MPGA")
        		|| name.endsWith(".MPG")
        		|| name.endsWith(".MPEG")
        		|| name.endsWith(".MPV")
        		|| name.endsWith(".DAT")
        		|| name.endsWith(".3GP2")
        		|| name.endsWith(".3GP") 
        		|| name.endsWith(".ASF")
        		|| name.endsWith(".WMV")
             ); 		
		
	}

}
