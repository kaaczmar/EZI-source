package ezi.tf_idf.algorithm;

import java.util.ArrayList;
import java.util.HashMap;

import ezi.tf_idf.data.Keyword;

/**
 * TF representation of given {@link BagOfWords}.
 * 
 * @author Mateusz
 * 
 */
public class TF {
	protected HashMap<String, Double> values;
	protected Integer maxCount;
	protected Double vectorLength;

	public Double getVectorLength() {
		return vectorLength;
	}

	public Double getWordValue(String word) {
		if (maxCount == 0)
			return 0.0;
		if (!values.containsKey(word))
			return 0.0;
		return new Double(values.get(word) / maxCount);
	}

	public TF(BagOfWords bag, ArrayList<Keyword> keywords) {
		values = new HashMap<String, Double>();
		maxCount = 0;
		for (Keyword word : keywords) {
			if (word.getIsDoubled())
				continue;
			Integer value = bag.getWordCount(word.getStemmedKeyword());
			values.put(word.getStemmedKeyword(), new Double(value));
			if (maxCount < value)
				maxCount = value;
		}

		updateVectorLength(keywords);
	}

	public void updateVectorLength(ArrayList<Keyword> keywords) {
		vectorLength = 0.0;
		for (Keyword word : keywords) {
			if (word.getIsDoubled())
				continue;
			vectorLength += Math.pow(getWordValue(word.getStemmedKeyword()), 2);
		}
		vectorLength = Math.sqrt(vectorLength);
	}
	
	/**
	 * This constructor is used by {@link TFIDF} representation, as it doesn't
	 * use list of keywords - it bases on TF vector.
	 */
	protected TF() {
	}
}
