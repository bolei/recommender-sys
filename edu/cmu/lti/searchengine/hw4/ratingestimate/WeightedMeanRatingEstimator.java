package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.Map;
import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.DataRow;

public class WeightedMeanRatingEstimator extends RatingEstimator {

	public WeightedMeanRatingEstimator(DataIndex dataIndex) {
		super(dataIndex);
	}

	/*
	 * The weighted mean rating for a movie among the neighbors uses the
	 * similarity measure from step (1) as the weight.
	 */
	@Override
	public double estimateRating(Map<Double, DataRow> kwindow, int columnId) {
		double totalWeight = 0;
		double weightedAverage;

		weightedAverage = 0;
		for (Entry<Double, DataRow> entry : kwindow.entrySet()) {
			if (entry.getValue().getMovieScores().containsKey(columnId)) {
				weightedAverage += entry.getValue().getMovieScores()
						.get(columnId).getScore()
						* entry.getKey();
			}
			totalWeight += entry.getKey();
		}
		weightedAverage /= totalWeight;

		return weightedAverage;

	}

}
