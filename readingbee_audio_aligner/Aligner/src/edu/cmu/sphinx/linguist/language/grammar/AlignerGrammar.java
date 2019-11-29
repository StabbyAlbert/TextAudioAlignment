package edu.cmu.sphinx.linguist.language.grammar;

import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import edu.cmu.sphinx.linguist.dictionary.Dictionary;
import edu.cmu.sphinx.util.ExtendedStreamTokenizer;
import edu.cmu.sphinx.util.LogMath;
import edu.cmu.sphinx.util.props.PropertyException;
import edu.cmu.sphinx.util.props.PropertySheet;
import edu.cmu.sphinx.util.props.S4Component;

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
public class AlignerGrammar extends Grammar {
	@S4Component(type = LogMath.class)
	public final static String PROP_LOG_MATH = "logMath";
	private LogMath logMath;
	private int start;

	private boolean model_repetitions = false;
	private boolean model_insertions = false;
	private boolean model_deletions = false;
	private boolean model_backwardJumps = false;

	private double selfLoopProbability;
	private double backwardTransitionProbability;
	private double forwardJumpProbability;
	private int numAllowedWordJumps;

	protected GrammarNode finalNode;
	private final List<String> tokens = new ArrayList<String>();

	public AlignerGrammar(final String text, final LogMath logMath,
			final boolean showGrammar, final boolean optimizeGrammar,
			final boolean addSilenceWords, final boolean addFillerWords,
			final Dictionary dictionary) {
		super(showGrammar, optimizeGrammar, addSilenceWords, addFillerWords,
				dictionary);
		this.logMath = logMath;
		setText(text);
	}

	public AlignerGrammar() {

	}

	/*
	 * Reads Text and converts it into a list of tokens
	 */
	public void setText(String text) {
		String word;
		try {
			final ExtendedStreamTokenizer tok = new ExtendedStreamTokenizer(
					new StringReader(text), true);

			tokens.clear();
			while (!tok.isEOF()) {
				while ((word = tok.getString()) != null) {
					word = word.toLowerCase();
					tokens.add(word);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setGrammarType(String GrammarType) {
		// Restore Default
		model_backwardJumps = false;
		model_deletions = false;
		model_insertions = false;
		model_repetitions = false;
		StringTokenizer st = new StringTokenizer(GrammarType, "|");
		while (st.hasMoreTokens()) {
			String GType = st.nextToken();
			if (GType.compareToIgnoreCase("MODEL_INSERTIONS") == 0) {

				// allows for word insertions
				model_insertions = true;
			} else if (GType.compareToIgnoreCase("MODEL_REPETITIONS") == 0) {

				// allows for a word to repeated a number of times with certain
				// penality associated with it

				model_repetitions = true;
			} else if (GType.compareTo("MODEL_DELETIONS") == 0) {

				// grammar allows for forward jumps with certain penalty
				// associated with it
				model_deletions = true;
			} else if (GType.compareToIgnoreCase("MODEL_BACKWARD_JUMPS") == 0) {

				model_backwardJumps = true;
			} else {
				throw new Error("UNKNOWN GRAMMAR MODEL");
			}
		}
	}

	@Override
	public void newProperties(PropertySheet ps) throws PropertyException {
		super.newProperties(ps);
		logMath = (LogMath) ps.getComponent(PROP_LOG_MATH);
	}

	@Override
	protected GrammarNode createGrammar() throws IOException {

		logger.info("Creating Grammar");
		if (model_repetitions) {
			selfLoopProbability = 0.000001; // penalty for repetition
		} else {
			selfLoopProbability = 0.0;
		}

		if (model_deletions) {
			forwardJumpProbability = 1.0; // penality for forward skips
		} else {
			forwardJumpProbability = 0.0;
		}

		if (model_backwardJumps) {

			backwardTransitionProbability = 0.000001;
		} else {
			backwardTransitionProbability = 0.0;
		}
		if (model_insertions) {
			// TODO CIPL

		}

		initialNode = createGrammarNode(Dictionary.SILENCE_SPELLING);
		finalNode = createGrammarNode(Dictionary.SILENCE_SPELLING);
		finalNode.setFinalNode(true);
		final GrammarNode branchNode = createGrammarNode(false);

		final List<GrammarNode> wordGrammarNodes = new ArrayList<GrammarNode>();
		final int end = tokens.size();

		for (final String word : tokens.subList(start, end)) {
			final GrammarNode wordNode = createGrammarNode(word.toLowerCase());
			wordGrammarNodes.add(wordNode);
		}

		// now connect all the GrammarNodes together
		initialNode.add(branchNode, LogMath.getLogOne());

		for (int i = 0; i < wordGrammarNodes.size(); i++) {
			final GrammarNode wordNode = wordGrammarNodes.get(i);

			// Link first word nodes with branch node
			if (i <= numAllowedWordJumps) {
				if (i != 0) {

					// case when first word can be skipped
					branchNode.add(wordNode, logMath
							.linearToLog(forwardJumpProbability));
				} else {
					branchNode.add(wordNode, logMath.getLogOne());
				}
			}

			// Link last nodes with final node
			if (i + numAllowedWordJumps + 1 >= wordGrammarNodes.size()) {
				if (i + 1 != wordGrammarNodes.size()) {
					wordNode.add(finalNode, logMath
							.linearToLog(forwardJumpProbability));
				} else {
					wordNode.add(finalNode, logMath.getLogOne());
				}
			}

			// allowing word repetitions: probability is still under test.
			wordNode.add(wordNode, logMath.linearToLog(selfLoopProbability));

			// add connections to close words
			for (int j = i + 1; j <= i + 1 + numAllowedWordJumps; j++) {
				if (j < wordGrammarNodes.size()) {
					final GrammarNode neighbour = wordGrammarNodes.get(j);
					if (j != i + 1) {
						wordNode.add(neighbour, logMath
								.linearToLog(forwardJumpProbability));

					} else {

						// immediate neighbour
						wordNode.add(neighbour, logMath.getLogOne());
					}
					neighbour.add(wordNode, logMath
							.linearToLog(backwardTransitionProbability));
				}
			}
		}
		logger.info("Grammar Generated");
		return initialNode;
	}

}