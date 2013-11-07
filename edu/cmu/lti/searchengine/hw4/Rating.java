package edu.cmu.lti.searchengine.hw4;

import java.util.Date;

public class Rating {

	private double score;
	private long timeStamp;

	public Rating(double s, long timeStamp) {
		score = s;
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return score + ", " + Configurator.df.format(new Date(timeStamp));
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

}
