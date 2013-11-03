package edu.cmu.lti.searchengine.hw4.similarity;

import edu.cmu.lti.searchengine.hw4.DataRow;

public interface SimilarityCalculator {
	/**
	 * Calculate the similarity between 2 rows
	 * 
	 * @param row
	 * @return similarity
	 */
	double getSimilarity(DataRow row1, DataRow row2);
}
