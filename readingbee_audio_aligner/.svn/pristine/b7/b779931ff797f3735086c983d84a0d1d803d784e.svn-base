/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */
package edu.cmu.sphinx.demo.aligner;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.RandomAccess;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import edu.cmu.sphinx.linguist.SearchGraph;
import edu.cmu.sphinx.linguist.SearchState;
import edu.cmu.sphinx.linguist.SearchStateArc;
import edu.cmu.sphinx.linguist.acoustic.AcousticModel;
import edu.cmu.sphinx.linguist.acoustic.tiedstate.TiedStateAcousticModel;
import edu.cmu.sphinx.linguist.aflat.AFlatLinguist;
import edu.cmu.sphinx.linguist.aflat.PhoneLoop;
import edu.cmu.sphinx.linguist.dflat.DynamicFlatLinguist;
import edu.cmu.sphinx.linguist.flat.FlatLinguist;
import edu.cmu.sphinx.linguist.language.grammar.AlignerGrammar;
import edu.cmu.sphinx.linguist.language.grammar.TextAlignerGrammar;
import edu.cmu.sphinx.linguist.util.GDLDumper;
import edu.cmu.sphinx.linguist.util.LinguistDumper;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.FrameStatistics;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.Configurable;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;
import edu.cmu.sphinx.util.props.PropertySheet;
import edu.cmu.sphinx.decoder.ResultListener;
import edu.cmu.sphinx.decoder.search.ActiveList;
import edu.cmu.sphinx.decoder.search.AlternateHypothesisManager;
import edu.cmu.sphinx.decoder.search.Token;
import edu.cmu.sphinx.frontend.util.AudioFileDataSource;

import edu.cmu.sphinx.util.AlignerTestCase;
import edu.cmu.sphinx.util.LogMath;
import edu.cmu.sphinx.util.NISTAlign;
import edu.cmu.sphinx.util.StringCustomise;
import edu.cmu.sphinx.util.StringErrorGenerator;
import edu.cmu.sphinx.util.WordErrorCount;

public class LongAudioAligner {
	public static void main(String Args[]) throws IOException {

		// Read Configuration file
		ConfigurationManager cm = new ConfigurationManager("./src/config.xml");
		Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
		AFlatLinguist aflatLinguist = (AFlatLinguist) cm
				.lookup("AflatLinguist");
		AlignerGrammar grammar = (AlignerGrammar) cm.lookup("AlignerGrammar");
		AudioFileDataSource dataSource = (AudioFileDataSource) cm
				.lookup("audioFileDataSource");

		// Read Batch File
		BufferedReader batchReader = new BufferedReader(new FileReader(
				"./resource/batchFile.txt"));
		String Line;
		while ((Line = batchReader.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(Line);
			if (st.countTokens() != 2) {
				throw new Error("Corrupt Batch File");
			}
			String pathToTextFile = st.nextToken();
			String pathToAudioFile = st.nextToken();
			dataSource.setAudioFile(new URL("file:" + pathToAudioFile), null);

			// Read raw input transcription from file
			String input = "";
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(
					pathToTextFile));
			while ((line = reader.readLine()) != null) {
				input = input.concat(line + " ");
			}
			URL URLToAudioFile = new URL("file:" + pathToAudioFile);
			System.out.println("Path to Text File: " + pathToTextFile
					+ "\nPath To Audio File: " + pathToAudioFile);
			currTest(recognizer, aflatLinguist, grammar, dataSource, input,
					URLToAudioFile);
			recognizer.deallocate();
		}

	}

	public static void currTest(Recognizer recognizer,
			AFlatLinguist aflatLinguist, AlignerGrammar grammar,
			AudioFileDataSource dataSource, String input, URL audioFileURL)
			throws IOException {

		// Clean-up the file to be suitable for making grammar
		StringCustomise sc = new StringCustomise();
		input = sc.customise(input);
		grammar.setText(input);
		grammar.setGrammarType(""); // FORCE ALIGNED GRAMMAR : Default

		recognizer.allocate();
		Result result;
		String timedResult;
		result = recognizer.recognize();
		aflatLinguist.deallocate();
		timedResult = result.getTimedBestResult(false, true); // Base result

		// URL pathToWordFile = new URL("file:./resource/models/wordFile.txt");
		// AlignerTestCase testCase = new AlignerTestCase(timedResult, 0.03,
		// pathToWordFile);

		System.out
				.println("========== GENERATING TIMED RESULT USING CORRECT TEXT =========");
		System.out.println("Timed Result: " + timedResult + "\n");

		/*
		 * // Corrupt the input using StringErrorGenerator String corruptedInput
		 * = testCase.getCorruptedText(); grammar.setText(corruptedInput);
		 * 
		 * // change grammar ConfigurationsSystem.out.println(
		 * "================== GRAMMAR MODEL: DELETIONS ===================");
		 * grammar.setGrammarType("MODEL_DELETIONS"); aflatLinguist.allocate();
		 * dataSource.setAudioFile(audioFileURL, null); result =
		 * recognizer.recognize(); timedResult =
		 * result.getTimedBestResult(false, true);
		 * System.out.println("Timed Result: "+timedResult+"\n");
		 * aflatLinguist.deallocate();
		 */
	}
}
