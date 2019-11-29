package com.rb.util;

public class locationNTime {
    public int indexLocation = 0;
    public String timeStr = "";
    
    public locationNTime(String timeStr_in, int indexLocation_in) {
    	indexLocation = indexLocation_in;
    	timeStr = timeStr_in;
    }
    public int getTimeSec() {
    	if(timeStr == null || timeStr.length() < 5) {
    		return 0;
    	}
    	String secStr = "0";
    	String minStr = "0";
    	if(timeStr.indexOf(":") >0) {
    		secStr = timeStr.substring(timeStr.indexOf(":")+1);
    		minStr = timeStr.substring(0, timeStr.indexOf(":"));
    		return Integer.parseInt(secStr) + Integer.parseInt(minStr)*60;
    	}
    	
    	return 0;
    }
    
    public static void main(String[] args) {
    	locationNTime LNT = new locationNTime("01:53",33);
    	System.out.println(" Hello:"+LNT.getTimeSec());
    	System.out.println(" Hello:"+LNT.indexLocation);

    }
    
}

/*
public static String getHHMMSS(long savedMediaLength) {
	String timeFormated = "";
	NumberFormat format; // Format minutes:seconds with leading zeros
	format = NumberFormat.getNumberInstance();
	format.setMinimumIntegerDigits(2); // pad with 0 if necessary
	// Convert remaining milliseconds to mm:ss format and display 
	if (savedMediaLength < 0) savedMediaLength = 0; 
	int hours = (int)(savedMediaLength/720000);
	int minutes = (int)((savedMediaLength%720000)/60000);
	int seconds = (int)((savedMediaLength%60000)/1000);
	timeFormated = (format.format(hours) + ":" + format.format(minutes) + ":" + format.format(seconds));
	return timeFormated;
}*/