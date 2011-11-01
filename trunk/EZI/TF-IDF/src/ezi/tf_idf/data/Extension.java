package ezi.tf_idf.data;

public class Extension {
	private Keyword keyword;
	private double weight;
	
	public Extension(Keyword keyword, double weight){
		this.keyword = keyword;
		this.weight = weight;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
