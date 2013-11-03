package edu.cmu.lti.searchengine.hw4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class IndexBuilder {

	public DataIndex loadIndex(File indexFile, boolean isByUser)
			throws IOException {

		HashSet<Integer> userIds = new HashSet<Integer>();
		HashSet<Integer> movieIds = new HashSet<Integer>();

		FileReader fileReader = null;
		BufferedReader bin = null;
		HashMap<Integer, DataRow> index = new HashMap<Integer, DataRow>();
		try {
			fileReader = new FileReader(indexFile);
			bin = new BufferedReader(fileReader);
			String line;
			String[] strArr;
			int userId, movieId;
			DataRow row;
			while ((line = bin.readLine()) != null) {
				strArr = line.trim().split(",\\s*");
				if (strArr.length % 3 != 1) {
					// System.err.println(line);
					// System.err.println("strArr[strArr.length - 1]="
					// + strArr[strArr.length - 1]);
					throw new IOException("bad format index row");
				}
				if (isByUser) {
					userId = Integer.parseInt(strArr[0]);
					userIds.add(userId);
					row = new DataRow(userId);
					for (int i = 1; i < strArr.length; i += 3) {
						movieId = Integer.parseInt(strArr[i]);
						movieIds.add(movieId);
						row.addRating(movieId,
								new Rating(Double.parseDouble(strArr[i + 1]),
										strArr[i + 2]));
					}
					index.put(userId, row);
				} else {
					movieId = Integer.parseInt(strArr[0]);
					movieIds.add(movieId);
					row = new DataRow(movieId);
					for (int i = 1; i < strArr.length; i += 3) {
						userId = Integer.parseInt(strArr[i]);
						userIds.add(userId);
						row.addRating(userId,
								new Rating(Double.parseDouble(strArr[i + 1]),
										strArr[i + 2]));
					}
					index.put(movieId, row);
				}
			}
		} finally {
			Configurator.closeReader(bin);
			Configurator.closeReader(fileReader);
		}
		return new DataIndex(userIds, movieIds, index);
	}

	public DataIndex buildIndex(File trainingFile, File indexFile,
			boolean isByUser) throws IOException {
		FileReader fileReader = null;
		BufferedReader bin = null;
		FileWriter fileWriter = null;
		BufferedWriter bout = null;

		HashSet<Integer> userIds = new HashSet<Integer>();
		HashSet<Integer> movieIds = new HashSet<Integer>();
		HashMap<Integer, DataRow> index = new HashMap<Integer, DataRow>();
		try {
			fileReader = new FileReader(trainingFile);
			bin = new BufferedReader(fileReader);
			fileWriter = new FileWriter(indexFile);
			bout = new BufferedWriter(fileWriter);
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
				if (isByUser) {
					if (index.containsKey(userId) == false) {
						index.put(userId, new DataRow(userId));
					}
					if (rating.getScore() != 0) {
						index.get(userId).addRating(movieId, rating);
					}
				} else { // by movie
					if (index.containsKey(movieId) == false) {
						index.put(movieId, new DataRow(movieId));
					}
					if (rating.getScore() != 0) {
						index.get(movieId).addRating(userId, rating);
					}
				}

			}

			// dump to disk
			for (Entry<Integer, DataRow> entry : index.entrySet()) {
				bout.write(entry.getValue().toString());
				bout.newLine();
			}

		} finally {
			Configurator.closeReader(bin);
			Configurator.closeWriter(bout);
			Configurator.closeReader(fileReader);
			Configurator.closeWriter(fileWriter);
		}
		return new DataIndex(userIds, movieIds, index);
	}

}
