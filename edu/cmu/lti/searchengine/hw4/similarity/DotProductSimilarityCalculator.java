package edu.cmu.lti.searchengine.hw4.similarity;

import java.util.Map.Entry;
import java.util.TreeMap;

import edu.cmu.lti.searchengine.hw4.Rating;
import edu.cmu.lti.searchengine.hw4.DataRow;

public class DotProductSimilarityCalculator implements SimilarityCalculator {

	@Override
	public double getSimilarity(DataRow row1, DataRow row2) {
		double result = 0;

		DataRow small, big;
		if (row1.getMovieScores().size() < row2.getMovieScores().size()) {
			small = row1;
			big = row2;
		} else {
			small = row2;
			big = row1;
		}

		TreeMap<Integer, Rating> bigMovieScores = big.getMovieScores();

		for (Entry<Integer, Rating> entry : small.getMovieScores().entrySet()) {
			if (bigMovieScores.containsKey(entry.getKey()) == false) {
				continue;
			}
			result += entry.getValue().getScore()
					* bigMovieScores.get(entry.getKey()).getScore();
		}
		return result;

	}

}
