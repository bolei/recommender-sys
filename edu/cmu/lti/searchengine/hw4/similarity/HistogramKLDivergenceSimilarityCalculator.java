package edu.cmu.lti.searchengine.hw4.similarity;

import java.util.HashMap;
import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataRow;
import edu.cmu.lti.searchengine.hw4.Rating;

public class HistogramKLDivergenceSimilarityCalculator extends
		SimilarityCalculator {

	private HashMap<Integer, Integer> getHistogram(DataRow row) {
		HashMap<Integer, Integer> rowHistgram = new HashMap<Integer, Integer>();
		for (Entry<Integer, Rating> entry : row.getMovieScores().entrySet()) {
			int rating = (int) entry.getValue().getScore();
			if (rowHistgram.containsKey(rating) == false) {
				rowHistgram.put(rating, 1);
			}
			rowHistgram.put(rating, rowHistgram.get(rating) + 1);
		}
		return rowHistgram;
	}

	private int getTotalCount(HashMap<Integer, Integer> histgram) {
		int ttl = 0;
		for (Entry<Integer, Integer> entry : histgram.entrySet()) {
			ttl += entry.getValue();
		}
		return ttl;
	}

	@Override
	public double getSimilarity(DataRow row1, DataRow row2) {
		HashMap<Integer, Integer> row1Histgram = getHistogram(row1);
		HashMap<Integer, Integer> row2Histgram = getHistogram(row2);
		int row1TtlCnt = getTotalCount(row1Histgram);

		if (row1TtlCnt == 0) {
			return 0;
		}

		double kld = 0;

		// 0*log0 = 0
		// thus KL divergence operates on intersection of the two
		// distributions, should loop over a smaller set
		for (int i = 1; i <= 5; i++) {
			if (row1Histgram.containsKey(i) == false
					|| row2Histgram.containsKey(i) == false) {
				continue;
			}
			kld += row1Histgram.get(i)
					* Math.log(row1Histgram.get(i) / row2Histgram.get(i));
		}

		kld /= row1TtlCnt;
		return kld == 0 ? Double.MAX_VALUE : 1 / kld;
	}

}
