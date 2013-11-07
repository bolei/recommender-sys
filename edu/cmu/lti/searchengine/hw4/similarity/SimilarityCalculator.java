package edu.cmu.lti.searchengine.hw4.similarity;

import edu.cmu.lti.searchengine.hw4.DataRow;

public abstract class SimilarityCalculator {
	/**
	 * Calculate the similarity between 2 rows
	 * 
	 * @param row
	 * @return similarity
	 */
	public abstract double getSimilarity(DataRow row1, DataRow row2);

	public double getSimilarity(DataRow row1, DataRow row2, int columnId,
			long timeWindow) {
		if (row1.getMovieScores().containsKey(columnId)
				&& row2.getMovieScores().containsKey(columnId)
				&& (Math.abs(row1.getMovieScores().get(columnId).getTimeStamp()
						- row2.getMovieScores().get(columnId).getTimeStamp()) > timeWindow)) {
			return 0d;
		}
		return getSimilarity(row1, row2);
	}

	public static SimilarityCalculator createSimilarityCalculator(String typeStr) {
		SimilarityCalculator simCal;
		if (typeStr.equals("kld")) { // kl divergence
			simCal = new RatingValueKLDivergenceSimilarityCalculator();
		} else if (typeStr.equals("cosine")) { // cosine
			simCal = new CosineSimilarityCalculator();
		} else { // default dot product
			simCal = new DotProductSimilarityCalculator();
		}
		return simCal;
	}
}
