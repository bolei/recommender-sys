package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	public double estimateRating(Map<Double, Integer> kwindow, int columnId,
			boolean isUserToUser) {
		int windowSize = kwindow.size(), vectorId;
		double average;
		Rating rating;
		average = 0;

		HashMap<Integer, DataRow> vectorsData;
		if (isUserToUser) {
			vectorsData = dataIndex.getUserToUserIndex();
		} else { // byMovie
			vectorsData = dataIndex.getMovieToMovieIndex();
		}

		for (Entry<Double, Integer> entry : kwindow.entrySet()) {
			vectorId = entry.getValue();
			rating = vectorsData.get(vectorId).getMovieScores().get(columnId);
			if (rating != null) {
				average += rating.getScore();
			}
		}
		average /= windowSize;
		return average;

	}

}
