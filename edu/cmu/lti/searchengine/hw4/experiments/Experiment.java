package edu.cmu.lti.searchengine.hw4.experiments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.cmu.lti.searchengine.hw4.Configurator;
import edu.cmu.lti.searchengine.hw4.DataIndex;

public abstract class Experiment {
	protected Configurator config;
	protected DataIndex indexData;

	public Experiment(Configurator config, DataIndex indexData) {
		this.config = config;
		this.indexData = indexData;
	}

	public void runExperiment() throws IOException {
		// <movie Id, sorted <userIds, rating>>
		TreeMap<Integer, TreeMap<Integer, Double>> predictionResult = new TreeMap<Integer, TreeMap<Integer, Double>>();
		FileReader fileReader = null;
		BufferedReader bin = null;
		FileWriter fileWriter = null;
		BufferedWriter bout = null;
		try {
			fileReader = new FileReader(
					config.getProperty(Configurator.CONFIG_TEST_FILE));
			bin = new BufferedReader(fileReader);
			fileWriter = new FileWriter(
					config.getProperty(Configurator.CONFIG_OUTPUT_FILE));
			bout = new BufferedWriter(fileWriter);

			String line = bin.readLine(); // skip first line
			while ((line = bin.readLine()) != null) {
				String[] strArr = line.split(",");
				int movieId = Integer.parseInt(strArr[0]);
				int userId = Integer.parseInt(strArr[1]);

				// put prediction result into result holder
				double predictVal = getPrediction(movieId, userId);

				if (predictionResult.containsKey(movieId) == false) {
					predictionResult.put(movieId,
							new TreeMap<Integer, Double>());
				}

				// use predicted Value + 3 to offset the imputation impact
				predictionResult.get(movieId).put(userId, predictVal + 3);
			}

			// dump out prediction result
			for (Entry<Integer, TreeMap<Integer, Double>> entry : predictionResult
					.entrySet()) {
				for (Entry<Integer, Double> subEntry : entry.getValue()
						.entrySet()) {
					bout.write(Double.toString(subEntry.getValue()));
					bout.newLine();
				}
			}

		} finally {
			Configurator.closeReader(bin);
			Configurator.closeWriter(bout);
			Configurator.closeReader(fileReader);
			Configurator.closeWriter(fileWriter);
		}

	}

	protected abstract double getPrediction(int movieId, int userId);
}
