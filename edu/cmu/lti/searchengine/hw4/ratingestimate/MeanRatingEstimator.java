package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.Map;
import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.DataRow;

public class MeanRatingEstimator extends RatingEstimator {

	public MeanRatingEstimator(DataIndex dataIndex) {
		super(dataIndex);
	}

	/*
	 * The mean rating is the average rating of this movie among the neighbors
	 */
	@Override
	public double estimateRating(Map<Double, DataRow> kwindow, int columnId) {
		int windowSize = kwindow.size();
		double average;

		average = 0;
		for (Entry<Double, DataRow> entry : kwindow.entrySet()) {
			if (entry.getValue().getMovieScores().containsKey(columnId)) {
				average += entry.getValue().getMovieScores().get(columnId)
						.getScore();
			}
		}
		average /= windowSize;
		return average;

	}

}
