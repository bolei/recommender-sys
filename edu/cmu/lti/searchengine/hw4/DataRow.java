package edu.cmu.lti.searchengine.hw4;

import java.util.Map.Entry;
import java.util.TreeMap;

public class DataRow {

	private int id; // id = -1 means this row is estimated value
	private TreeMap<Integer, Rating> movieScores = new TreeMap<Integer, Rating>();

	public DataRow(int id) {
		this.id = id;
	}

	public void addRating(int itemId, Rating rating) {
		movieScores.put(itemId, rating);
	}

	public TreeMap<Integer, Rating> getMovieScores() {
		return movieScores;
	}

	public double getVectorLength() {
		double length = 0, score;
		for (Entry<Integer, Rating> entry : movieScores.entrySet()) {
			score = entry.getValue().getScore();
			length += score * score;
		}
		return Math.sqrt(length);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append(", ");
		for (Entry<Integer, Rating> entry : movieScores.entrySet()) {
			sb.append(entry.getKey() + ", ");
			sb.append(entry.getValue() + ", ");
		}
		return sb.toString();
	}

}
