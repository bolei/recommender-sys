package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.Map;
import java.util.Set;
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
	public DataRow estimateRating(Map<Double, DataRow> kwindow, boolean isByUser) {
		double totalWeight = 0;
		DataRow estimated = new DataRow(-1);
		double weightedAverage;
		Set<Integer> allIds;
		if (isByUser) {
			allIds = dataIndex.getAllMovieIds();
		} else {
			allIds = dataIndex.getAllUserIds();
		}

		// calculate total weight
		for (Entry<Double, DataRow> entry : kwindow.entrySet()) {
			totalWeight += entry.getKey();
		}

		for (int id : allIds) {
			weightedAverage = 0;
			for (Entry<Double, DataRow> entry : kwindow.entrySet()) {
				if (entry.getValue().getMovieScores().containsKey(id)) {
					weightedAverage += entry.getValue().getMovieScores()
							.get(id).getScore()
							* entry.getKey();
				}
			}
			weightedAverage /= totalWeight;
			estimated.addRating(id, new Rating(weightedAverage,
					"weighted mean rating"));
		}
		return estimated;

	}

}
