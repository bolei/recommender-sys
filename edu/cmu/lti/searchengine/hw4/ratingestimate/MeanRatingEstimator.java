package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.DataRow;
import edu.cmu.lti.searchengine.hw4.Rating;

public class MeanRatingEstimator extends RatingEstimator {

	public MeanRatingEstimator(DataIndex dataIndex) {
		super(dataIndex);
	}

	/*
	 * The mean rating is the average rating of this movie among the neighbors
	 */
	@Override
	public DataRow estimateRating(Map<Double, DataRow> kwindow, boolean isByUser) {
		int windowSize = kwindow.size();
		DataRow estimated = new DataRow(-1);
		double average;
		Set<Integer> allIds;
		if (isByUser) {
			allIds = dataIndex.getAllMovieIds();
		} else {
			allIds = dataIndex.getAllUserIds();
		}

		for (int id : allIds) {
			average = 0;
			for (Entry<Double, DataRow> entry : kwindow.entrySet()) {
				if (entry.getValue().getMovieScores().containsKey(id)) {
					average += entry.getValue().getMovieScores().get(id)
							.getScore();
				}
			}
			average /= windowSize;
			estimated.addRating(id, new Rating(average, "mean rating"));
		}
		return estimated;
	}

}
