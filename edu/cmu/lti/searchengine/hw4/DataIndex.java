package edu.cmu.lti.searchengine.hw4;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class DataIndex {
	private Set<Integer> allUserIds;
	private Set<Integer> allMovieIds;

	private HashMap<Integer, DataRow> userToUserIndex, movieToMovieIndex;

	public DataIndex(Set<Integer> allUserIds, Set<Integer> allMovieIds,
			HashMap<Integer, DataRow> userToUserIndex,
			HashMap<Integer, DataRow> movieToMovieIndex) {
		this.allMovieIds = allMovieIds;
		this.allUserIds = allUserIds;
		this.userToUserIndex = userToUserIndex;
		this.movieToMovieIndex = movieToMovieIndex;
	}

	public Set<Integer> getAllUserIds() {
		return allUserIds;
	}

	public Set<Integer> getAllMovieIds() {
		return allMovieIds;
	}

	public HashMap<Integer, DataRow> getUserToUserIndex() {
		return userToUserIndex;
	}

	public HashMap<Integer, DataRow> getMovieToMovieIndex() {
		return movieToMovieIndex;
	}

	public void dumpStat() {
		System.out.println("Total number of movies: " + allMovieIds.size());
		System.out.println("Total number of users: " + allUserIds.size());

		int count1 = 0, count3 = 0, count5 = 0;
		int totalCount = 0;
		double totalRating = 0d;

		double delta = 1e-5;

		TreeMap<Integer, Rating> movieScores;
		for (Entry<Integer, DataRow> entry : userToUserIndex.entrySet()) {
			movieScores = entry.getValue().getMovieScores();
			for (Entry<Integer, Rating> movScr : movieScores.entrySet()) {
				totalCount++;
				totalRating += (movScr.getValue().getScore() + 3);
				if (Math.abs(movScr.getValue().getScore() + 2) < delta) {
					count1++;
				} else if (Math.abs(movScr.getValue().getScore()) < delta) {
					count3++;
				} else if (Math.abs(movScr.getValue().getScore() - 2) < delta) {
					count5++;
				}
			}
		}

		double averageRating = totalRating / totalCount;

		System.out.println("Number of times any movie rated '1': " + count1);
		System.out.println("Number of times any movie rated '3': " + count3);
		System.out.println("Number of times any movie rated '5': " + count5);
		System.out.println("Average movie rating across all users movies: "
				+ averageRating);

		System.out.println("For user ID 1234576");
		int userId = 1234576;
		DataRow userRow = userToUserIndex.get(userId);
		count1 = 0;
		count3 = 0;
		count5 = 0;
		totalCount = 0;
		totalRating = 0d;
		for (Entry<Integer, Rating> entry : userRow.getMovieScores().entrySet()) {
			totalCount++;
			totalRating += (entry.getValue().getScore() + 3);
			if (Math.abs(entry.getValue().getScore() + 2) < delta) {
				count1++;
			} else if (Math.abs(entry.getValue().getScore()) < delta) {
				count3++;
			} else if (Math.abs(entry.getValue().getScore() - 2) < delta) {
				count5++;
			}
		}
		averageRating = totalRating / totalCount;
		System.out.println("Number of movies rated: " + totalCount);
		System.out.println("Number of times the user gave a '1' rating: "
				+ count1);
		System.out.println("Number of times the user gave a '3' rating: "
				+ count3);
		System.out.println("Number of times the user gave a '5' rating: "
				+ count5);
		System.out.println("Average movie rating for this user: "
				+ averageRating);

		System.out.println("For movie ID 4321");
		int movieId = 4321;
		DataRow movieRow = movieToMovieIndex.get(movieId);
		count1 = 0;
		count3 = 0;
		count5 = 0;
		totalCount = 0;
		totalRating = 0d;
		for (Entry<Integer, Rating> entry : movieRow.getMovieScores()
				.entrySet()) {
			totalCount++;
			totalRating += (entry.getValue().getScore() + 3);
			if (Math.abs(entry.getValue().getScore() + 2) < delta) {
				count1++;
			} else if (Math.abs(entry.getValue().getScore()) < delta) {
				count3++;
			} else if (Math.abs(entry.getValue().getScore() - 2) < delta) {
				count5++;
			}
		}
		averageRating = totalRating / totalCount;
		System.out.println("Number of users rating this movie: " + totalCount);
		System.out.println("Number of times the user gave a '1' rating: "
				+ count1);
		System.out.println("Number of times the user gave a '3' rating: "
				+ count3);
		System.out.println("Number of times the user gave a '5' rating: "
				+ count5);
		System.out.println("Average movie rating for this movie: "
				+ averageRating);
	}

}
