package edu.cmu.lti.searchengine.hw4.experiments;

import edu.cmu.lti.searchengine.hw4.Configurator;
import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.ratingestimate.MeanRatingEstimator;
import edu.cmu.lti.searchengine.hw4.ratingestimate.RatingEstimator;
import edu.cmu.lti.searchengine.hw4.ratingestimate.WeightedMeanRatingEstimator;
import edu.cmu.lti.searchengine.hw4.similarity.SimilarityCalculator;

public class ExperimentOne extends Experiment {

	private KNearestNeighbors knn;

	public ExperimentOne(Configurator config, DataIndex indexData) {
		super(config, indexData);
		int k = Integer.parseInt(config.getProperty("k"));

		String typeStr = config.getProperty("similarityType");
		SimilarityCalculator simCal = SimilarityCalculator
				.createSimilarityCalculator(typeStr);

		RatingEstimator profEst;
		if (config.getProperty("ratingType").equals("mean")) {
			profEst = new MeanRatingEstimator(indexData);
		} else { // weightedMean
			profEst = new WeightedMeanRatingEstimator(indexData);
		}

		knn = new KNearestNeighbors(k, simCal, profEst);
	}

	@Override
	protected double getPrediction(int movieId, int userId) {
		boolean considerTime = Boolean.parseBoolean(config
				.getProperty("considerTime"));
		long timeWindow = 0;
		if (considerTime == true) {
			timeWindow = Long.parseLong(config.getProperty("timeWindow"));
		}
		return knn.makePrediction(indexData.getUserToUserIndex(), userId,
				movieId, true, considerTime, timeWindow);
	}
}
