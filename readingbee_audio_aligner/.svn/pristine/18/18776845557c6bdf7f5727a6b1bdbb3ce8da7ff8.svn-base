/*
 * Copyright 1999-2002 Carnegie Mellon University.  
 * Portions Copyright 2002 Sun Microsystems, Inc.  
 * Portions Copyright 2002 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 * 
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL 
 * WARRANTIES.
 *
 */

package edu.cmu.sphinx.demo.phrasespotting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import edu.cmu.sphinx.decoder.search.AlignerSearchManager;
import edu.cmu.sphinx.decoder.search.SimpleBreadthFirstSearchManager;
import edu.cmu.sphinx.decoder.search.Token;
import edu.cmu.sphinx.frontend.FloatData;
import edu.cmu.sphinx.frontend.util.AudioFileDataSource;
import edu.cmu.sphinx.linguist.SearchState;
import edu.cmu.sphinx.linguist.UnitSearchState;
import edu.cmu.sphinx.linguist.WordSearchState;
import edu.cmu.sphinx.linguist.acoustic.Unit;
import edu.cmu.sphinx.linguist.aflat.AFlatLinguist;
import edu.cmu.sphinx.linguist.dictionary.Word;
import edu.cmu.sphinx.linguist.language.grammar.AlignerGrammar;
import edu.cmu.sphinx.phrasespotter.Result;
import edu.cmu.sphinx.phrasespotter.simplephrasespotter.SimplePhraseSpotter;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.AlignerResult;
import edu.cmu.sphinx.util.StringCustomise;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class PhraseSpotting {
	public static void main(String Args[]) throws IOException {
		
		// Initialise demo related variables
		final String phrase = "We"; // Phrase to be spotted
		String pathToAudioFile = "./resource/wav/obama_test.wav"; // Audio file
		String pathToTextFile = "./resource/Transcription/obama.txt"; // Transcription
		// file

		System.out.println("Phrase: " + phrase);
		
		SimplePhraseSpotter sp =new SimplePhraseSpotter("./src/phraseSpotterConfig.xml");
		sp.setPhrase(phrase);
		sp.setAudioDataSource(new URL("file:" + pathToAudioFile));
		sp.allocate();
		sp.startSpotting();
		Iterator<edu.cmu.sphinx.phrasespotter.Result> iter = sp.getTimedResult().iterator();
		while(iter.hasNext()){
			edu.cmu.sphinx.phrasespotter.Result result = iter.next();
			System.out.println("(" + result.getStartTime() + "," + result.getEndTime() + ")");
		}
		
		//  Now start the Informed Alignment using spotter's result
		// By informed alignment we now mean to improve beam efficiency 
		
		ConfigurationManager cm = new ConfigurationManager("./src/config.xml");
		Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
		AFlatLinguist aflatLinguist = (AFlatLinguist) cm.lookup("linguist");
		AlignerGrammar grammar = (AlignerGrammar) cm.lookup("grammar");
		grammar.setGrammarType("MODEL_DELETIONS");
		AudioFileDataSource dataSource = (AudioFileDataSource) cm
				.lookup("audioFileDataSource");
		AlignerSearchManager sm = (AlignerSearchManager) cm.lookup("searchManager");
		sm.setPhrase(phrase);
		sm.setSpotterResult(sp.getTimedResult());

		BufferedReader reader = new BufferedReader(new FileReader(
				pathToTextFile));
		String input = "";
		String line;
		while ((line = reader.readLine()) != null) {
			input = input.concat(line + " ");
		}
		StringCustomise sc = new StringCustomise();
		input = sc.customise(input);
		grammar.setText(input);
		dataSource.setAudioFile(new URL("file:" + pathToAudioFile), null);
		recognizer.allocate();

		System.out
				.println("-------------------- Generating Timed Result -----------------------");
		edu.cmu.sphinx.result.Result baseResult = recognizer.recognize();
		String timedResult = baseResult.getTimedBestResult(false, true);
		System.out.println(timedResult);
		
		/*
		System.out
				.println("-------------------- Timed Phone Result ----------------------------");
		AlignerResult alignerResult = new AlignerResult(baseResult);
		String aResult = alignerResult.getBestTimedPhoneResult();
		System.out.println(aResult);
		*/	
		
	}
}
