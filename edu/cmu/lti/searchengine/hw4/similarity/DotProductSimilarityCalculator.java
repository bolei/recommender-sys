package edu.cmu.lti.searchengine.hw4.similarity;

import java.util.Map.Entry;
import java.util.TreeMap;

import edu.cmu.lti.searchengine.hw4.Rating;
import edu.cmu.lti.searchengine.hw4.DataRow;

public class DotProductSimilarityCalculator implements SimilarityCalculator {

	@Override
	public double getSimilarity(DataRow row1, DataRow row2) {
		double result = 0;
		TreeMap<Integer, Rating> row2MovieScores = row2.getMovieScores();

		for (Entry<Integer, Rating> entry : row1.getMovieScores().entrySet()) {
			if (row2MovieScores.containsKey(entry.getKey()) == false) {
				continue;
			}
			result += entry.getValue().getScore()
					* row2MovieScores.get(entry.getKey()).getScore();
		}
		return result;

	}

}
