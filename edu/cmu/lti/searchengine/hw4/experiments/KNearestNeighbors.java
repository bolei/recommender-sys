package edu.cmu.lti.searchengine.hw4.experiments;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import edu.cmu.lti.searchengine.hw4.DataRow;
import edu.cmu.lti.searchengine.hw4.ElemIdSimilarityPair;
import edu.cmu.lti.searchengine.hw4.ratingestimate.RatingEstimator;
import edu.cmu.lti.searchengine.hw4.similarity.SimilarityCalculator;

public class KNearestNeighbors {

	private SimilarityCalculator simCal;
	private RatingEstimator profEst;
	private int k;

	// k nearest neighbors for each user
	private HashMap<Integer, ElemIdSimilarityPair[]> cache = new HashMap<Integer, ElemIdSimilarityPair[]>();

	public KNearestNeighbors(int k, SimilarityCalculator simCal,
			RatingEstimator profEst) {
		this.k = k;
		this.simCal = simCal;
		this.profEst = profEst;
	}

	public void printNeighbors(HashMap<Integer, DataRow> train, DataRow query,
			SimilarityCalculator simCal) {
		ElemIdSimilarityPair[] kwindow = new ElemIdSimilarityPair[k];
		int elemCnt = 0;
		double sim;
		for (Entry<Integer, DataRow> entry : train.entrySet()) {
			if (entry.getKey() == query.getId()) {
				// exclude the row itself
				continue;
			}
			sim = simCal.getSimilarity(entry.getValue(), query);

			// put <userId, similarity> into kwindow
			if (elemCnt < k) { // there's still space
				kwindow[elemCnt] = new ElemIdSimilarityPair(entry.getKey(), sim);
				elemCnt++;
				if (elemCnt == k) {
					Arrays.sort(kwindow);
				}
			} else {
				if (sim <= kwindow[0].getSimilarity()) {
					continue;
				}
				kwindow[0] = new ElemIdSimilarityPair(entry.getKey(), sim);
				Arrays.sort(kwindow);
			}
		}
		System.out.println(Arrays.toString(kwindow));
	}

	public double makePrediction(HashMap<Integer, DataRow> train, int queryId,
			int columnId, boolean isUserToUser, boolean considerTime,
			long timeWindow) {

		DataRow query = train.get(queryId);

		ElemIdSimilarityPair[] kwindow = getKwindow(train, query, simCal,
				considerTime, columnId, timeWindow);

		// now we have the k nearest neighbors
		return profEst.estimateRating(kwindow, columnId, isUserToUser);
	}

	private ElemIdSimilarityPair[] getKwindow(HashMap<Integer, DataRow> train,
			DataRow query, SimilarityCalculator simCal, boolean considerTime,
			int columnId, long timeWindow) {
		ElemIdSimilarityPair[] kwindow;
		if (cache.containsKey(query.getId())) {
			kwindow = cache.get(query.getId());
		} else {
			kwindow = new ElemIdSimilarityPair[k];
			int elemCnt = 0;
			double sim;
			for (Entry<Integer, DataRow> entry : train.entrySet()) {
				if (entry.getKey() == query.getId()) {
					// exclude the row itself
					continue;
				}
				if (considerTime == true) {
					sim = simCal.getSimilarity(entry.getValue(), query,
							columnId, timeWindow);
				} else {
					sim = simCal.getSimilarity(entry.getValue(), query);
				}

				// put <userId, similarity> into kwindow
				if (elemCnt < k) { // there's still space
					kwindow[elemCnt] = new ElemIdSimilarityPair(entry.getKey(),
							sim);
					elemCnt++;
					if (elemCnt == k) {
						Arrays.sort(kwindow);
					}
				} else {
					if (sim <= kwindow[0].getSimilarity()) {
						continue;
					}
					kwindow[0] = new ElemIdSimilarityPair(entry.getKey(), sim);
					Arrays.sort(kwindow);
				}
			}
			cache.put(query.getId(), kwindow);
		}
		return kwindow;
	}

}
