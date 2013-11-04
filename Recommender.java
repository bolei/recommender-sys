import java.io.File;
import java.io.IOException;

import edu.cmu.lti.searchengine.hw4.Configurator;
import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.IndexBuilder;
import edu.cmu.lti.searchengine.hw4.experiments.ExperimentOne;
import edu.cmu.lti.searchengine.hw4.experiments.ExperimentTwo;
import edu.cmu.lti.searchengine.hw4.experiments.ExperimentType;

public class Recommender {

	private static Configurator config;

	public static void main(String[] argv) throws IOException {
		long begin = System.currentTimeMillis();
		config = new Configurator(argv);

		// check if need to build index
		String indexFilePath = config.getProperty("indexFile");
		File indexFile = new File(indexFilePath);
		DataIndex index;
		IndexBuilder indexBuilder = new IndexBuilder();

		boolean isByUser = config.isByUser();

		if (indexFile.exists() == false) {
			// if no index file, build one
			File trainingFile = new File(
					config.getProperty(Configurator.CONFIG_TRAIN_FILE));
			index = indexBuilder.buildIndex(trainingFile, indexFile, isByUser);
		} else {
			index = indexBuilder.loadIndex(indexFile, isByUser);
		}
		switch (ExperimentType.valueOf(config.getProperty("expType"))) {
		case EXP_1:
			new ExperimentOne(config, index).runExperiment();
			break;
		case EXP_2:
			new ExperimentTwo(config, index).runExperiment();
		default:
			break;
		}
		long end = System.currentTimeMillis();
		System.err.println("time past:" + (end - begin));
	}

}