package edu.cmu.lti.searchengine.hw4.experiments;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.cmu.lti.searchengine.hw4.DataRow;
import edu.cmu.lti.searchengine.hw4.ratingestimate.RatingEstimator;
import edu.cmu.lti.searchengine.hw4.similarity.SimilarityCalculator;

public class KNearestNeighbors {

	private SimilarityCalculator simCal;
	private RatingEstimator profEst;

	private int k;

	public KNearestNeighbors(int k, SimilarityCalculator simCal,
			RatingEstimator profEst) {
		this.k = k;
		this.simCal = simCal;
		this.profEst = profEst;
	}

	public double makePrediction(HashMap<Integer, DataRow> train,
			DataRow query, int columnId) {
		FixedSizeTreeMap kwindow = new FixedSizeTreeMap(k);
		double sim, score;
		for (Entry<Integer, DataRow> entry : train.entrySet()) {
			sim = simCal.getSimilarity(entry.getValue(), query);
			if (entry.getValue().getMovieScores().get(columnId) == null) {
				score = 0d;
			} else {
				score = entry.getValue().getMovieScores().get(columnId)
						.getScore();
			}

			kwindow.put(sim, score);
		}

		// now we have the k nearest neighbors
		return profEst.estimateRating(kwindow, columnId);
	}

	/**
	 * @author bolei <br/>
	 *         Map sorted by key. Size fixed to MAX_SIZE<br/>
	 *         if size < MAX_SIZE, put a new item into map<br/>
	 *         if size >= MAX_SIZE && new item's key not greater than least key
	 *         in map, do not insert<br/>
	 *         if size >= MAX_SIZE && new item's key great than least key in
	 *         map, remove least key value pair in map, insert the new item
	 * 
	 * @param <K>
	 * @param <V>
	 */
	private class FixedSizeTreeMap extends TreeMap<Double, Double> {

		private static final long serialVersionUID = -3646662497160905763L;
		private final int MAX_SIZE;

		public FixedSizeTreeMap(int k) {
			MAX_SIZE = k;
		}

		@Override
		public Double put(Double key, Double value) {

			if (this.size() >= MAX_SIZE && !this.containsKey(key)) {
				// no space
				Double leastKey = this.firstKey();
				if (key - leastKey < 0.d) {
					// do nothing
					return null;
				} else {
					// remove the least value object
					this.remove(leastKey);

					// then add
					super.put(key, value); // O(nlogn)
					return null;
				}
			} else { // there's still space in the map
				super.put(key, value);
				return null;
			}
		}
	}

}
