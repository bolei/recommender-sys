package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.HashMap;
import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.DataRow;
import edu.cmu.lti.searchengine.hw4.ElemIdSimilarityPair;
import edu.cmu.lti.searchengine.hw4.Rating;

public class MeanRatingEstimator extends RatingEstimator {

	public MeanRatingEstimator(DataIndex dataIndex) {
		super(dataIndex);
	}

	/*
	 * The mean rating is the average rating of this movie among the neighbors
	 */
	@Override
	public double estimateRating(ElemIdSimilarityPair[] kwindow, int columnId,
			boolean isUserToUser) {
		int vectorId;
		double average = 0;
		Rating rating;

		HashMap<Integer, DataRow> vectorsData;
		if (isUserToUser) {
			vectorsData = dataIndex.getUserToUserIndex();
		} else { // byMovie
			vectorsData = dataIndex.getMovieToMovieIndex();
		}

		for (ElemIdSimilarityPair pair : kwindow) {
			vectorId = pair.getElemId();
			rating = vectorsData.get(vectorId).getMovieScores().get(columnId);
			if (rating != null) {
				average += rating.getScore();
			} else {
				int size = vectorsData.get(vectorId).getMovieScores().size();
				double totalRating = 0;
				for (Entry<Integer, Rating> movieRatingEntry : vectorsData
						.get(vectorId).getMovieScores().entrySet()) {
					totalRating += movieRatingEntry.getValue().getScore();
				}
				average += (totalRating / size);
			}
		}

		// get the mean of existing value

		average /= kwindow.length;
		return average;

	}
}
