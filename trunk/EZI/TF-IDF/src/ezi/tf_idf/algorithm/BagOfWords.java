package ezi.tf_idf.algorithm;

import java.util.HashMap;
import java.util.Set;

import ezi.tf_idf.data.Document;

/**
 * Simple bag-of-words representation of {@link Document}. For each word we
 * calculate its occurrence and store them in hash map for further actions. We
 * can easily check if document contains given word by checking if it is stored
 * in the bag. Also we can use this representation to calculate {@link TF} and
 * {@link TFIDF}.
 * 
 * @author Mateusz
 * 
 */
public class BagOfWords {

	private HashMap<String, Integer> bags;

	public Integer getWordCount(String word) {
		if (!bags.containsKey(word))
			return 0;
		return bags.get(word);
	}

	public Set<String> getWords() {
		return bags.keySet();
	}

	public BagOfWords(String text) {
		bags = new HashMap<String, Integer>();
		String[] array = text.split(" ");
		for (int i = 0; i < array.length; i++) {
			Integer val = bags.get(array[i]);
			if (val == null)
				val = 0;
			val += 1;
			bags.put(array[i], val);
		}
	}
}
