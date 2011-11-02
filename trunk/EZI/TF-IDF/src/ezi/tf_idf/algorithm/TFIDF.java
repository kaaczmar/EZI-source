package ezi.tf_idf.algorithm;

import java.util.HashMap;

/**
 * TFIDF representation of keywords vector - extends {@link TF} as it provides
 * similar functionality.
 * 
 * @author Mateusz
 * 
 */
public class TFIDF extends TF {

	/**
	 * Creates new instance of TFIDF vector which consists of words and their
	 * TFIDF values.
	 * 
	 * @param idf
	 *            given {@link IDF} vector
	 * @param tf
	 *            given {@link TF} vector
	 */
	public TFIDF(IDF idf, TF tf) {
		super();
		values = new HashMap<String, Double>();
		maxCount = tf.maxCount;
		for (String word : tf.values.keySet()) {
			values.put(word, tf.values.get(word) * idf.getWordValue(word));
		}

		vectorLength = 0.0;
		for (String word : values.keySet()) {
			vectorLength += Math.pow(getWordValue(word), 2);
		}
		vectorLength = Math.sqrt(vectorLength);
	}

	public void setWordValue(String word, Double value) {
		values.put(word, value);
	}
}
