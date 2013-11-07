package edu.cmu.lti.searchengine.hw4.experiments;

import java.io.IOException;

import edu.cmu.lti.searchengine.hw4.Configurator;
import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.similarity.CosineSimilarityCalculator;
import edu.cmu.lti.searchengine.hw4.similarity.DotProductSimilarityCalculator;

public class ExperimentShowNeighbor extends Experiment {

	public ExperimentShowNeighbor(Configurator config, DataIndex indexData) {
		super(config, indexData);
	}

	@Override
	public void runExperiment() throws IOException {
		int userId = 1234576;
		int movieId = 4321;
		int k = 5;
		KNearestNeighbors knn = new KNearestNeighbors(k, null, null);

		DotProductSimilarityCalculator dotpSimCal = new DotProductSimilarityCalculator();
		CosineSimilarityCalculator cosineSimCal = new CosineSimilarityCalculator();

		knn.printNeighbors(indexData.getUserToUserIndex(), indexData
				.getUserToUserIndex().get(userId), dotpSimCal);
		knn.printNeighbors(indexData.getUserToUserIndex(), indexData
				.getUserToUserIndex().get(userId), cosineSimCal);

		knn.printNeighbors(indexData.getMovieToMovieIndex(), indexData
				.getMovieToMovieIndex().get(movieId), dotpSimCal);
		knn.printNeighbors(indexData.getMovieToMovieIndex(), indexData
				.getMovieToMovieIndex().get(movieId), cosineSimCal);
	}

	@Override
	protected double getPrediction(int movieId, int userId) {
		return 0;
	}

}
