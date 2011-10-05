package ezi.tf_idf.algorithm;

import java.util.ArrayList;
import java.util.HashMap;

import ezi.tf_idf.data.Document;
import ezi.tf_idf.data.Keyword;

/**
 * IDF representation of keywords vector.
 * 
 * @author Mateusz
 * 
 */
public class IDF {
	private HashMap<String, Double> idfValues;

	public Double getWordValue(String word) {
		if (!idfValues.containsKey(word))
			return 0.0;
		return idfValues.get(word);
	}

	/**
	 * Constructs IDF vector of values using provided documents and keywords.
	 * 
	 * @param documents
	 *            list of documents
	 * @param keywords
	 *            list of keywords
	 */
	public IDF(ArrayList<Document> documents, ArrayList<Keyword> keywords) {
		idfValues = new HashMap<String, Double>();
		for (Keyword word : keywords) {
			Double count = 0.0;
			for (Document document : documents) {
				if (document.contains(word.getStemmedKeyword()))
					count += 1;
			}
			count = documents.size() / count;
			count = Math.log10(count);
			idfValues.put(word.getStemmedKeyword(), count);

			System.out.println(word.getStemmedKeyword() + ": " + count);
		}
	}
}
