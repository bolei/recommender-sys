package edu.cmu.lti.searchengine.hw4;

import java.util.HashMap;
import java.util.Set;

public class DataIndex {
	private Set<Integer> allUserIds;
	private Set<Integer> allMovieIds;

	private HashMap<Integer, DataRow> dataVector;

	public DataIndex(Set<Integer> allUserIds, Set<Integer> allMovieIds,

	HashMap<Integer, DataRow> dataVector) {
		this.allMovieIds = allMovieIds;
		this.allUserIds = allUserIds;
		this.dataVector = dataVector;
	}

	public Set<Integer> getAllUserIds() {
		return allUserIds;
	}

	public Set<Integer> getAllMovieIds() {
		return allMovieIds;
	}

	public HashMap<Integer, DataRow> getDataVector() {
		return dataVector;
	}

}
