package edu.cmu.lti.searchengine.hw4.similarity;

import edu.cmu.lti.searchengine.hw4.DataRow;

public class CosineSimilarityCalculator extends SimilarityCalculator {

	private DotProductSimilarityCalculator dotSimCal = new DotProductSimilarityCalculator();

	@Override
	public double getSimilarity(DataRow row1, DataRow row2) {
		double row1Len = row1.getVectorLength(), row2Len = row2
				.getVectorLength();
		if (row1Len * row2Len == 0) {
			return 0;
		}
		return dotSimCal.getSimilarity(row1, row2) / (row1Len * row2Len);
	}

}
