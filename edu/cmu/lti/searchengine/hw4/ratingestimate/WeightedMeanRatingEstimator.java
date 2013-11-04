package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.Map;
import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.Rating;

public class WeightedMeanRatingEstimator extends RatingEstimator {

	public WeightedMeanRatingEstimator(DataIndex dataIndex) {
		super(dataIndex);
	}

	/*
	 * The weighted mean rating for a movie among the neighbors uses the
	 * similarity measure from step (1) as the weight.
	 */
	@Override
	public double estimateRating(Map<Double, Integer> kwindow, int columnId) {
		double totalWeight = 0;
		double weightedAverage;
		int vectorId;
		Rating rating;
		weightedAverage = 0;
		for (Entry<Double, Integer> entry : kwindow.entrySet()) {
			vectorId = entry.getValue();
			rating = dataIndex.getDataVector().get(vectorId).getMovieScores()
					.get(columnId);
			if (rating != null) {
				weightedAverage += entry.getKey() * rating.getScore();
			}

			totalWeight += entry.getKey();
		}
		weightedAverage /= totalWeight;

		return weightedAverage;

	}

}
