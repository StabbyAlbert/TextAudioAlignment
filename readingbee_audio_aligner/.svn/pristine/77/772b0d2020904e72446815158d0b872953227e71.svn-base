package edu.cmu.sphinx.result;


import edu.cmu.sphinx.decoder.search.Token;
import edu.cmu.sphinx.frontend.FloatData;
import edu.cmu.sphinx.linguist.HMMSearchState;
import edu.cmu.sphinx.linguist.SearchState;
import edu.cmu.sphinx.linguist.UnitSearchState;
import edu.cmu.sphinx.linguist.acoustic.Unit;

public class AlignerResult {

	private Result result;

	public AlignerResult(Result result) {
		this.result = result;
	}

	public String getBestTimedPhoneResult() {
		Token tok = result.getBestToken();

		StringBuilder sb = new StringBuilder();
		float sampleRate = 1.0f;	// non-zero initialisation. 
		long lastSampleNumber = -1;
		long currSampleNumber = 0;
		boolean unitDetected = false;
		Unit lastUnit = null;
		while (tok != null) {
			FloatData data = (FloatData) tok.getData();
			SearchState searchState = tok.getSearchState();
			if (data != null) {
				currSampleNumber = data.getFirstSampleNumber();
				if (lastSampleNumber == -1) {
					lastSampleNumber = currSampleNumber;
				}
				if (sampleRate == 1.0f) {
					sampleRate = data.getSampleRate();	// Initialise sample rate to correct value
				}
			}
			if (unitDetected) {
				if (!lastUnit.isSilence()) {
					sb.insert(0, lastUnit.getName() + "("
							+ (float) currSampleNumber / sampleRate + ","
							+ (float) lastSampleNumber / sampleRate + ") ");
				}
				lastSampleNumber = currSampleNumber;
				unitDetected = false;
			}
			if (searchState instanceof UnitSearchState) {				
				UnitSearchState unitState = (UnitSearchState) searchState;
				Unit tmpUnit = unitState.getUnit();
				if(lastUnit == null) {
					lastUnit = tmpUnit;
				}
				if(tmpUnit.getName().compareToIgnoreCase(lastUnit.getName()) != 0) {
					lastUnit = tmpUnit;
					unitDetected = true;
				}
			} else if ( searchState instanceof HMMSearchState) {
				//System.out.println("here");
				HMMSearchState hmmState = (HMMSearchState) searchState;
				Unit tmpUnit = hmmState.getHMMState().getHMM().getBaseUnit();
				if(lastUnit == null) {
					lastUnit = tmpUnit;
				}
				if(tmpUnit.getName().compareToIgnoreCase(lastUnit.getName()) != 0) {
					lastUnit = tmpUnit;
					unitDetected = true;
				}
			}
			tok = tok.getPredecessor();	
			
		}
		return sb.toString();
	}

}
