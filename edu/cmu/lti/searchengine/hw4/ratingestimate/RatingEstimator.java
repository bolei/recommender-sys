package edu.cmu.lti.searchengine.hw4.ratingestimate;

import java.util.Map;

import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.DataRow;

public abstract class RatingEstimator {

	protected DataIndex dataIndex;

	public RatingEstimator(DataIndex dataIndex) {
		this.dataIndex = dataIndex;
	}

	public abstract double estimateRating(Map<Double, DataRow> kwindow,
			int columnId);
}
