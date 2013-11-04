package edu.cmu.lti.searchengine.hw4.experiments;

import java.io.IOException;

import edu.cmu.lti.searchengine.hw4.Configurator;
import edu.cmu.lti.searchengine.hw4.DataIndex;

public class ExperimentZero extends Experiment {

	public ExperimentZero(Configurator config, DataIndex indexData) {
		super(config, indexData);
	}

	@Override
	public void runExperiment() throws IOException {
		indexData.dumpStat();
	}

	@Override
	protected double getPrediction(int movieId, int userId) {
		return 0;
	}

}
