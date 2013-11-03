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
	private boolean isByUser;

	public KNearestNeighbors(int k, SimilarityCalculator simCal,
			RatingEstimator profEst, boolean isByUser) {
		this.k = k;
		this.simCal = simCal;
		this.profEst = profEst;
		this.isByUser = isByUser;
	}

	public DataRow prediction(HashMap<Integer, DataRow> train, DataRow query) {
		FixedSizeTreeMap<Double, DataRow> kwindow = new FixedSizeTreeMap<Double, DataRow>(
				k);
		double sim;
		for (Entry<Integer, DataRow> entry : train.entrySet()) {
			sim = simCal.getSimilarity(entry.getValue(), query);
			kwindow.put(sim, entry.getValue());
		}

		// now we have the k nearest neighbors
		return profEst.estimateRating(kwindow, isByUser);
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
	private class FixedSizeTreeMap<K, V> extends TreeMap<K, V> {

		private static final long serialVersionUID = -3646662497160905763L;
		private final int MAX_SIZE;

		public FixedSizeTreeMap(int k) {
			MAX_SIZE = k;
		}

		@Override
		public V put(K key, V value) {

			if (this.size() >= MAX_SIZE && !this.containsKey(key)) {
				// no space
				K leastKey = this.firstKey();
				if (this.comparator().compare(key, leastKey) <= 0) {
					// do nothing
					return null;
				} else {
					// remove the least value object
					this.remove(leastKey);

					// then add
					this.put(key, value); // O(nlogn)
					return null;
				}
			} else { // there's still space in the map
				this.put(key, value);
				return null;
			}
		}
	}

}
