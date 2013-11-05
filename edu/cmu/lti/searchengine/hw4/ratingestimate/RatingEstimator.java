package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.Map;

import edu.cmu.lti.searchengine.hw4.DataIndex;

public abstract class RatingEstimator {

	protected DataIndex dataIndex;

	public RatingEstimator(DataIndex dataIndex) {
		this.dataIndex = dataIndex;
	}

	public abstract double estimateRating(Map<Double, Integer> kwindow,
			int columnId, boolean isUserToUser);
}
