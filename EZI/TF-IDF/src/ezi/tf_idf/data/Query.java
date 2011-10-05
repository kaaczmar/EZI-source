package ezi.tf_idf.data;

import java.util.ArrayList;
import ezi.tf_idf.algorithm.IDF;

/**
 * This class is used to store the query typed by User. It extends
 * {@link Document} as it provides similar functionality. The reason for this
 * class is to prevent using Document as Query (which sounds irrational)
 * 
 * @author Mateusz
 * 
 */
public class Query extends Document {

	/**
	 * Constructs new Document with blank Title (as it would be merged in
	 * stemmed version) and feeds it with keywords to calculate TF and IDF table
	 * to calculate TFIDF.
	 * 
	 * @param queryText
	 *            query typed by User
	 * @param keywords
	 *            list of available keywords
	 * @param idf
	 *            {@link IDF} instance for given set of keywords
	 */
	public Query(String queryText, ArrayList<Keyword> keywords, IDF idf) {
		super("", queryText);
		title = "Query";
		applyKeywordSet(keywords);
		applyIDF(idf);
	}
}
