package edu.cmu.lti.searchengine.hw4;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import edu.cmu.lti.searchengine.hw4.experiments.ExperimentType;

public class Configurator extends Properties {
	private static final long serialVersionUID = -851031049977917583L;

	public static final String CONFIG_TRAIN_FILE = "trainFile";
	public static final String CONFIG_TEST_FILE = "testFile";
	public static final String CONFIG_OUTPUT_FILE = "outputFile";

	public Configurator(String[] argv) throws IOException {
		if (argv.length < 3) {
			throw new IllegalArgumentException("Illegal number of arguments");
		}
		this.put(CONFIG_TRAIN_FILE, argv[0]);
		this.put(CONFIG_TEST_FILE, argv[1]);
		this.put(CONFIG_OUTPUT_FILE, argv[2]);

		String configFile = "config.properties";
		if (argv.length > 3) { // configuration file specified
			configFile = argv[3];
		}

		Properties config = new Properties();
		config.load(new FileReader(configFile));
		this.putAll(config);
	}

	public static void closeReader(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
				reader = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeWriter(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
				writer = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isByUser() {
		ExperimentType expType = ExperimentType.valueOf(getProperty("expType"));
		switch (expType) {
		case EXP_1:
			return true;
		case EXP_2:
			return false;
		default:
			return false;
		}
	}
}
