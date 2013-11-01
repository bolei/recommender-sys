package edu.cmu.lti.searchengine.hw4;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configurator {
	private String trainFile = "/home/bolei/Works/data/11641-hw4/training_set.csv";
	private String testFile = "/home/bolei/Works/data/11641-hw4/test-all.csv";
	private String outputFile = "/home/bolei/Desktop/output.txt";
	private String configFile = "config.properties";

	private ExperimentType expType = ExperimentType.EXP_1;

	public Configurator(String[] argv) throws IOException {
		if (argv.length < 3) {
			throw new IllegalArgumentException("Illegal number of arguments");
		}
		trainFile = argv[0];
		testFile = argv[1];
		outputFile = argv[2];
		if (argv.length > 3) {
			configFile = argv[3];
		}
		// TODO: populate properties from configuration file
		Properties config = new Properties();
		config.load(new FileReader(configFile));
		expType = ExperimentType.valueOf(config.getProperty("expType"));
	}

	public String getTrainFile() {
		return trainFile;
	}

	public String getTestFile() {
		return testFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public String getConfigFile() {
		return configFile;
	}

	public ExperimentType getExpType() {
		return expType;
	}

	private enum ExperimentType {
		EXP_1, EXP_2, EXP_3, EXP_4
	}

}
