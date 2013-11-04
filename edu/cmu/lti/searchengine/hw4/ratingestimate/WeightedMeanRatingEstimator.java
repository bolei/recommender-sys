package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.DataRow;
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
	public double estimateRating(Map<Double, Integer> kwindow, int columnId,
			boolean isByUser) {
		double totalWeight = 0;
		double weightedAverage;
		int vectorId;
		Rating rating;
		weightedAverage = 0;

		HashMap<Integer, DataRow> vectorsData;
		if (isByUser) {
			vectorsData = dataIndex.getByUserIndex();
		} else { // byMovie
			vectorsData = dataIndex.getByMovieIndex();
		}

		for (Entry<Double, Integer> entry : kwindow.entrySet()) {
			vectorId = entry.getValue();
			rating = vectorsData.get(vectorId).getMovieScores().get(columnId);
			if (rating != null) {
				weightedAverage += entry.getKey() * rating.getScore();
			}

			totalWeight += entry.getKey();
		}
		weightedAverage /= totalWeight;

		return weightedAverage;

	}

}
