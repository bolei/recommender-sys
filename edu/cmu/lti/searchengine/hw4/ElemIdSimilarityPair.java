package edu.cmu.lti.searchengine.hw4;

public class ElemIdSimilarityPair implements Comparable<ElemIdSimilarityPair> {
	private int elemId;
	private double similarity;

	public ElemIdSimilarityPair(int elemId, double similarity) {
		this.elemId = elemId;
		this.similarity = similarity;
	}

	@Override
	public int compareTo(ElemIdSimilarityPair o) {
		if (this.similarity < o.similarity) {
			return -1;
		} else if (this.similarity > o.similarity) {
			return 1;
		} else {
			if (this.elemId < o.elemId) {
				return -1;
			} else if (this.elemId > o.elemId) {
				return 1;
			} else {
				return 0;
			}

		}
	}

	public int getElemId() {
		return elemId;
	}

	public double getSimilarity() {
		return similarity;
	}

	@Override
	public String toString() {
		return "[id=" + elemId + ", sim=" + similarity + "]";
	}
}
