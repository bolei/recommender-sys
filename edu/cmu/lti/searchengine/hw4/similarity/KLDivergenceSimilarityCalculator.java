package edu.cmu.lti.searchengine.hw4.similarity;

import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataRow;
import edu.cmu.lti.searchengine.hw4.Rating;

public class KLDivergenceSimilarityCalculator extends SimilarityCalculator {

	@Override
	public double getSimilarity(DataRow row1, DataRow row2) {
		double row1Sum = 0;
		int row1Count = row1.getMovieScores().size();
		int row2Count = row2.getMovieScores().size();
		for (Entry<Integer, Rating> entry : row1.getMovieScores().entrySet()) {
			row1Sum += entry.getValue().getScore();
		}

		double kld = 0;

		// 0*log0 = 0
		// thus KL divergence operates on intersection of the two
		// distributions, should loop over a smaller set
		if (row1Count < row2Count) {
			for (Entry<Integer, Rating> entry : row1.getMovieScores()
					.entrySet()) {
				if (entry.getValue().getScore() < 1e-5) {
					continue;
				}
				if (row2.getMovieScores().containsKey(entry.getKey()) == false
						|| row2.getMovieScores().get(entry.getKey()).getScore() < 1e-5) {
					continue;
				}
				kld += entry.getValue().getScore()
						* Math.log(entry.getValue().getScore()
								/ row2.getMovieScores().get(entry.getKey())
										.getScore());
			}
		} else {
			for (Entry<Integer, Rating> entry : row2.getMovieScores()
					.entrySet()) {
				if (entry.getValue().getScore() < 1e-5) {
					continue;
				}
				if (row1.getMovieScores().containsKey(entry.getKey()) == false
						|| row1.getMovieScores().get(entry.getKey()).getScore() < 1e-5) {
					continue;
				}
				kld += row1.getMovieScores().get(entry.getKey()).getScore()
						* Math.log(row1.getMovieScores().get(entry.getKey())
								.getScore()
								/ entry.getValue().getScore());
			}
		}
		kld /= row1Sum;
		return kld;
	}
}
