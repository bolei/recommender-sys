package edu.cmu.lti.searchengine.hw4;

public class Rating {

	private double score;
	private String dateStr;

	public Rating(double s, String d) {
		this.score = s;
		this.dateStr = d;
	}

	@Override
	public String toString() {
		return score + ", " + dateStr;
	}

	public double getScore() {
		return score;
	}

	public String getDateStr() {
		return dateStr;
	}

}
