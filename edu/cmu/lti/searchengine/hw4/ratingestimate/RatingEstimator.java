package edu.cmu.lti.searchengine.hw4.ratingestimate;

import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.ElemIdSimilarityPair;

public abstract class RatingEstimator {

	protected DataIndex dataIndex;

	public RatingEstimator(DataIndex dataIndex) {
		this.dataIndex = dataIndex;
	}

	public abstract double estimateRating(ElemIdSimilarityPair[] kwindow,
			int columnId, boolean isUserToUser);
}
