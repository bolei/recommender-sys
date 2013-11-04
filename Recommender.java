import java.io.File;
import java.io.IOException;

import edu.cmu.lti.searchengine.hw4.Configurator;
import edu.cmu.lti.searchengine.hw4.DataIndex;
import edu.cmu.lti.searchengine.hw4.IndexBuilder;
import edu.cmu.lti.searchengine.hw4.experiments.Experiment;
import edu.cmu.lti.searchengine.hw4.experiments.ExperimentType;

public class Recommender {

	public static void main(String[] argv) throws IOException {
		long begin = System.currentTimeMillis();

		Configurator config = new Configurator(argv);

		IndexBuilder indexBuilder = new IndexBuilder();

		// if no index file, build one
		File trainingFile = new File(
				config.getProperty(Configurator.CONFIG_TRAIN_FILE));
		DataIndex index = indexBuilder.buildIndex(trainingFile);

		// run the experiment
		Experiment experiment = Experiment.createExperiment(
				ExperimentType.valueOf(config.getProperty("expType")), config,
				index);
		if (experiment == null) {
			System.out.println("unknown experiment: "
					+ config.getProperty("expType"));
			return;
		}
		experiment.runExperiment();

		long end = System.currentTimeMillis();
		System.err.println("time past:" + (end - begin));
	}

}