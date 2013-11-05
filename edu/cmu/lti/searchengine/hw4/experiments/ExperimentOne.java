package edu.cmu.lti.searchengine.hw4.experiments;

import edu.cmu.lti.searchengine.hw4.Configurator;
import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.DataRow;
import edu.cmu.lti.searchengine.hw4.ratingestimate.MeanRatingEstimator;
import edu.cmu.lti.searchengine.hw4.ratingestimate.RatingEstimator;
import edu.cmu.lti.searchengine.hw4.ratingestimate.WeightedMeanRatingEstimator;
import edu.cmu.lti.searchengine.hw4.similarity.CosineSimilarityCalculator;
import edu.cmu.lti.searchengine.hw4.similarity.DotProductSimilarityCalculator;
import edu.cmu.lti.searchengine.hw4.similarity.SimilarityCalculator;

public class ExperimentOne extends Experiment {

	private KNearestNeighbors knn;

	public ExperimentOne(Configurator config, DataIndex indexData) {
		super(config, indexData);
		int k = Integer.parseInt(config.getProperty("k"));

		SimilarityCalculator simCal;
		if (config.getProperty("similarityType").equals("dotp")) {
			simCal = new DotProductSimilarityCalculator();
		} else { // cosine
			simCal = new CosineSimilarityCalculator();
		}

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
		DataRow queryRow = indexData.getUserToUserIndex().get(userId);
		return knn.makePrediction(indexData.getUserToUserIndex(), queryRow,
				movieId, true);
	}
}
