package edu.cmu.lti.searchengine.hw4.experiments;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import edu.cmu.lti.searchengine.hw4.Configurator;
import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.DataRow;
import edu.cmu.lti.searchengine.hw4.Rating;
import edu.cmu.lti.searchengine.hw4.ratingestimate.MeanRatingEstimator;
import edu.cmu.lti.searchengine.hw4.ratingestimate.RatingEstimator;
import edu.cmu.lti.searchengine.hw4.ratingestimate.WeightedMeanRatingEstimator;
import edu.cmu.lti.searchengine.hw4.similarity.SimilarityCalculator;

public class ExperimentThree extends Experiment {
	private KNearestNeighbors knn;
	private HashMap<Integer, DataRow> standardized = new HashMap<Integer, DataRow>();
	private boolean isUserToUser = true;

	public ExperimentThree(Configurator config, DataIndex indexData) {
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
	public void runExperiment() throws IOException {
		pccStandardize();
		super.runExperiment();
	}

	// work on userToUser matrix
	private void pccStandardize() {
		isUserToUser = Boolean.parseBoolean(config.getProperty("isUserToUser"));

		HashMap<Integer, DataRow> dataMatrix;
		if (isUserToUser == true) {
			dataMatrix = indexData.getUserToUserIndex();
		} else { // movieToMovie
			dataMatrix = indexData.getMovieToMovieIndex();
		}

		Set<Integer> allIds;

		for (Entry<Integer, DataRow> rowEntry : dataMatrix.entrySet()) {
			DataRow rowCopy = new DataRow(rowEntry.getKey());
			standardized.put(rowEntry.getKey(), rowCopy);
			TreeMap<Integer, Rating> vector = rowEntry.getValue()
					.getMovieScores();
			if (isUserToUser) {
				allIds = indexData.getAllMovieIds();
			} else {
				allIds = indexData.getAllUserIds();
			}

			double sum = 0;
			int len = allIds.size();
			for (Entry<Integer, Rating> entry : vector.entrySet()) {
				sum += entry.getValue().getScore();
			}
			double mean = sum / len;
			double vecLen = rowEntry.getValue().getVectorLength();
			if (vecLen < 1e-5) { // if is close to zero
				continue; // all zeros, no need to standardize
			}
			for (Integer columnId : vector.keySet()) {
				rowCopy.addRating(
						columnId,
						new Rating((vector.get(columnId).getScore() - mean)
								/ vecLen, (vector.get(columnId).getTimeStamp())));
			}
		}
	}

	@Override
	protected double getPrediction(int movieId, int userId) {
		boolean considerTime = Boolean.parseBoolean(config
				.getProperty("considerTime"));
		long timeWindow = 0;
		if (considerTime == true) {
			timeWindow = Long.parseLong(config.getProperty("timeWindow"));
		}

		if (isUserToUser) {
			return knn.makePrediction(standardized, userId, movieId,
					isUserToUser, considerTime, timeWindow);
		} else {
			return knn.makePrediction(standardized, movieId, userId,
					isUserToUser, considerTime, timeWindow);
		}

	}

}
