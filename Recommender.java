import java.io.IOException;

import edu.cmu.lti.searchengine.hw4.Configurator;

public class Recommender {

	private static Configurator config;

	public static void main(String[] argv) throws IOException {

		config = new Configurator(argv);

		// If you see these outputs, it means you have successfully compiled and
		// run the code.
		// Then you can remove these three lines if you want.
		System.out.println("Training File : " + config.getTrainFile());
		System.out.println("Test File : " + config.getTestFile());
		System.out.println("Output File : " + config.getOutputFile());

		// Implement your recommendation modules using trainFile and testFile.
		// And output the prediction scores to outputFile.

	}

}