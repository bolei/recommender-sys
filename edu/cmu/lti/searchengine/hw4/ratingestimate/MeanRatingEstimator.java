package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.Map;
import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataIndex;

public class MeanRatingEstimator extends RatingEstimator {

	public MeanRatingEstimator(DataIndex dataIndex) {
		super(dataIndex);
	}

	/*
	 * The mean rating is the average rating of this movie among the neighbors
	 */
	@Override
	public double estimateRating(Map<Double, Double> kwindow, int columnId) {
		int windowSize = kwindow.size();
		double average;

		average = 0;
		for (Entry<Double, Double> entry : kwindow.entrySet()) {
			average += entry.getValue();
		}
		average /= windowSize;
		return average;

	}

}
