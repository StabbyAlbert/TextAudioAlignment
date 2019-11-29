
package edu.cmu.sphinx.demo.aligner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import alignment.AlignMainNew;
import edu.cmu.sphinx.linguist.aflat.AFlatLinguist;
import edu.cmu.sphinx.linguist.language.grammar.AlignerGrammar;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.frontend.util.AudioFileDataSource;
import edu.cmu.sphinx.util.StringCustomise;
import edu.cmu.sphinx.util.escapeCharNewToSpace;

public class LongAudioAlignerNew {
	
	final static Logger logger  = Logger.getLogger(LongAudioAlignerNew.class);

	
	public static void main(String Args[]) throws IOException {
		//String findThis = "<unk>djk fdjds cdddddddddddddddddddddddd dddddddddddddddddddddjfd dddddddff fg fg ffdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd <unk>dflk dkjfdcccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc djkfdj kdj k jdjfk  dkfjdkj  djlj i<unk> <unk> <unk>";
	//	logger.info(filterResult(findThis));
      mainProc();
	}
	
	public static void mainProc() throws IOException{
		BufferedReader batchReader = new BufferedReader(new FileReader("C:/java/long_audio_aligner/Aligner/resource/batchFile.txt"));
		String Line;
		String[] matchResults = new String[2];
		while ((Line = batchReader.readLine())!=null) {
			StringTokenizer st = new StringTokenizer(Line);
			if (st.countTokens() != 2) {
				throw new Error("Corrupt Batch File");
			}
			String pathToTextFile = st.nextToken();
			String pathToAudioFile =st.nextToken();
		    String input = "";
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(pathToTextFile));
			while ((line = reader.readLine()) != null) {
				input = input.concat(line + " ");
			}
			input = escapeCharNewToSpace.forAUDIOTXT(input);
			logger.info("Path to Text File: "+pathToTextFile+"\nPath To Audio File: "+pathToAudioFile);
			getMatchResults(pathToAudioFile,input,matchResults);
			logger.info("========== GENERATING TIMED RESULT USING CORRECT TEXT =========");
			logger.info("Timed Result: "+filterResult(matchResults[0])+"\n");	
			logger.info(" Result: "+filterResult(matchResults[1])+"\n");
		}

	}

	
	public static void getMatchResults(String input16bit16kHzWav, String txtContentOrig,String[] matchResults) throws IOException  {
		logger.info("Configuring with: " + "config.xml");
		File f = new File("config.xml");
		
		logger.info("PATH: " + f.getAbsolutePath());
		ConfigurationManager cm = new ConfigurationManager("config.xml");
		logger.info("Using config: " + "config.xml");
		Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
		AFlatLinguist aflatLinguist = (AFlatLinguist) cm.lookup("AflatLinguist");
		AlignerGrammar grammar = (AlignerGrammar) cm.lookup("AlignerGrammar");
		AudioFileDataSource dataSource = (AudioFileDataSource) cm.lookup("audioFileDataSource");
		dataSource.setAudioFile(new URL("file:"+input16bit16kHzWav), null);	
		currTest(recognizer, aflatLinguist, grammar, dataSource,txtContentOrig,matchResults);
		recognizer.deallocate();
		
	}
	
		
	
	public static void  currTest(Recognizer recognizer, AFlatLinguist aflatLinguist,
		AlignerGrammar grammar, AudioFileDataSource dataSource,
		String txtContentOrig, String[] matchResults) throws IOException {				
		// Clean-up the file to be suitable for making grammar
		logger.info("textLength: "+txtContentOrig.length());
		
		StringCustomise sc= new StringCustomise();
		String txtContentOrigCS=sc.customise(txtContentOrig);	
		grammar.setText(txtContentOrigCS);
		grammar.setGrammarType("");			// FORCE ALIGNED GRAMMAR : Default	

		logger.info ("textLengthCS2: "+txtContentOrigCS.length());
		

		recognizer.allocate();
		
		logger.info("textLengthCS3: "+txtContentOrigCS.length());
		Result result;		
		String timedResult;
		String resultStr;
		
		logger.info ("textLengthCS4: "+txtContentOrigCS.length());
		
		result = recognizer.recognize();
		
		logger.info ("textLengthCS5: "+txtContentOrigCS.length());
		
		aflatLinguist.deallocate();
		timedResult = result.getTimedBestResult(true, true);	// Base result					
		resultStr = result.getBestResultNoFiller();	// Base result
	//	logger.info(" ResultP: "+result.getBestPronunciationResult()+"\n");
	//	logger.info(" ResultT: "+result.getTimedBestResult(true, false)+"\n");
	//	logger.info(" ResultR: "+result.getReferenceText()+"\n");
		matchResults[0] = timedResult;
		matchResults[1] = resultStr;
logger.info("Matched Result Length:"+resultStr.length());
	
	}

	public static String filterResult(String resultStrTimed_in2) {
		StringBuilder sbStr = new StringBuilder();
		String resultStrTimed_in = resultStrTimed_in2;

	    for (final String word : resultStrTimed_in2.toString().split(" ")) {
			  if(word.trim().length() > 0 && !word.contains("<unk>")) {
				  sbStr.append(word).append(" ");
			  }
		}
	    resultStrTimed_in = sbStr.toString();
		String resultStrTimed = resultStrTimed_in;
		
		int lastUNK1 = resultStrTimed_in.lastIndexOf("<sil>");
	//	int lastUNK2 = resultStrTimed_in.lastIndexOf("<sil>",lastUNK1-1);
        int loop = 888;
        if(lastUNK1 > 100) {
            resultStrTimed = resultStrTimed_in.substring(0,lastUNK1);
		/*    while(loop-- > 0 && resultStrTimed_in.length() > 124 && lastUNK2 > 0 && lastUNK1 > 0 && (lastUNK1 - lastUNK2) < 50) {
			  if(lastUNK2 > 0) {
				 resultStrTimed = resultStrTimed_in.substring(0,lastUNK2);
				 lastUNK1 = lastUNK2;
				 lastUNK2 = resultStrTimed_in.lastIndexOf("<sil>",lastUNK1-1);
		  	  } 
		   }
		   */
        }
        
	    sbStr = new StringBuilder();
        for (final String word : resultStrTimed.toString().split(" ")) {
			  if(word.trim().length() > 0 && !word.contains("<sil>")) {
				  sbStr.append(word).append(" ");
			  }
		}
		return sbStr.toString().trim();
}

}
