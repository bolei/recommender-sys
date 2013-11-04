package edu.cmu.lti.searchengine.hw4.experiments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

				double predictVal = getPrediction(movieId, userId);

				// dump out prediction result
				bout.write(Double.toString(predictVal));
				bout.newLine();
				bout.flush();
//				System.out.println(predictVal);
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
