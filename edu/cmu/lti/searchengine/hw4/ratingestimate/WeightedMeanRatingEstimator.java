package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.Map;
import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataIndex;

public class WeightedMeanRatingEstimator extends RatingEstimator {

	public WeightedMeanRatingEstimator(DataIndex dataIndex) {
		super(dataIndex);
	}

	/*
	 * The weighted mean rating for a movie among the neighbors uses the
	 * similarity measure from step (1) as the weight.
	 */
	@Override
	public double estimateRating(Map<Double, Double> kwindow, int columnId) {
		double totalWeight = 0;
		double weightedAverage;

		weightedAverage = 0;
		for (Entry<Double, Double> entry : kwindow.entrySet()) {
			weightedAverage += entry.getValue() * entry.getKey();
			totalWeight += entry.getKey();
		}
		weightedAverage /= totalWeight;

		return weightedAverage;

	}

}
