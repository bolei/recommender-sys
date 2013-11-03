package edu.cmu.lti.searchengine.hw4.similarity;

import edu.cmu.lti.searchengine.hw4.DataRow;

public class CosineSimilarityCalculator implements SimilarityCalculator {

	private DotProductSimilarityCalculator dotSimCal = new DotProductSimilarityCalculator();

	@Override
	public double getSimilarity(DataRow row1, DataRow row2) {
		return dotSimCal.getSimilarity(row1, row2)
				/ (row1.getVectorLength() * row2.getVectorLength());
	}

}
