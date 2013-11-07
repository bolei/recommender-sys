package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.HashMap;
import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.DataRow;
import edu.cmu.lti.searchengine.hw4.ElemIdSimilarityPair;
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
	public double estimateRating(ElemIdSimilarityPair[] kwindow, int columnId,
			boolean isUserToUser) {
		double totalWeight = 0;
		double weightedAverage;
		int vectorId;
		Rating rating;
		weightedAverage = 0;

		HashMap<Integer, DataRow> vectorsData;
		if (isUserToUser) {
			vectorsData = dataIndex.getUserToUserIndex();
		} else { // byMovie
			vectorsData = dataIndex.getMovieToMovieIndex();
		}

		for (ElemIdSimilarityPair elemPair : kwindow) {
			vectorId = elemPair.getElemId();
			rating = vectorsData.get(vectorId).getMovieScores().get(columnId);
			if (rating != null) {
				weightedAverage += elemPair.getSimilarity() * rating.getScore();
			} else {

				// use the average rating
				double totalRating = 0;
				int size = vectorsData.get(vectorId).getMovieScores().size();
				for (Entry<Integer, Rating> movieRatingEntry : vectorsData
						.get(vectorId).getMovieScores().entrySet()) {
					totalRating += movieRatingEntry.getValue().getScore();
				}
				weightedAverage += (elemPair.getSimilarity() * (totalRating / size));
			}
			totalWeight += elemPair.getSimilarity();
		}

		if (Math.abs(totalWeight) < 1e-5) { // very close to zero
			weightedAverage = 0;
		} else {
			weightedAverage /= totalWeight;
		}
		return weightedAverage;
	}

}
