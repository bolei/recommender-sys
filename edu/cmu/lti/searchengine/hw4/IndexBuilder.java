package edu.cmu.lti.searchengine.hw4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class IndexBuilder {

	public DataIndex buildIndex(File trainingFile) throws IOException {
		FileReader fileReader = null;
		BufferedReader bin = null;

		HashSet<Integer> userIds = new HashSet<Integer>();
		HashSet<Integer> movieIds = new HashSet<Integer>();
		HashMap<Integer, DataRow> byUserIndex = new HashMap<Integer, DataRow>(), byMovieIndex = new HashMap<Integer, DataRow>();
		try {
			fileReader = new FileReader(trainingFile);
			bin = new BufferedReader(fileReader);
			String line = bin.readLine(); // skip the first line
			String[] strArr;
			int userId, movieId;
			double score;
			String dateStr;
			Rating rating;

			// load training data into memory
			while ((line = bin.readLine()) != null) {
				strArr = line.split(",");
				movieId = Integer.parseInt(strArr[0]);
				movieIds.add(movieId);
				userId = Integer.parseInt(strArr[1]);
				userIds.add(userId);
				score = Double.parseDouble(strArr[2]);
				dateStr = strArr[3];
				// (score - 3) to apply missing value imputation
				rating = new Rating(score - 3, dateStr);
				// by user
				if (byUserIndex.containsKey(userId) == false) {
					byUserIndex.put(userId, new DataRow(userId));
				}
				byUserIndex.get(userId).addRating(movieId, rating);
				// by movie
				if (byMovieIndex.containsKey(movieId) == false) {
					byMovieIndex.put(movieId, new DataRow(movieId));
				}
				byMovieIndex.get(movieId).addRating(userId, rating);

			}

		} finally {
			Configurator.closeReader(bin);
			Configurator.closeReader(fileReader);
		}
		return new DataIndex(userIds, movieIds, byUserIndex, byMovieIndex);
	}

}
