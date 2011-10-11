package ezi.tf_idf.data;

import java.util.ArrayList;

import ezi.tf_idf.algorithm.BagOfWords;
import ezi.tf_idf.algorithm.IDF;
import ezi.tf_idf.algorithm.TF;
import ezi.tf_idf.algorithm.TFIDF;
import ezi.tf_idf.utils.Stemmer;

/**
 * Used to store documents with their respective titles and content. It also
 * prepares stemmed version of document. Consists of {@link BagOfWords} for
 * document and after updating list of {@link Keyword} calculates {@link TF}.
 * When we provide proper {@link IDF} vector we can also calculate {@link TFIDF}
 * . Provides functions to calculate both TF and TFIDF similarity measures.
 * 
 * @author Mateusz
 * 
 */
public class Document implements Comparable<Document> {
	protected String title;
	private String content;
	private String stemmedDocument;
	private BagOfWords bag;
	private TF tf;
	protected TFIDF tfidf;
	private Double similiarity;

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getStemmedDocument() {
		return stemmedDocument;
	}

	public Double getSimiliarity() {
		return similiarity;
	}

	public Boolean contains(String word) {
		if (bag.getWordCount(word) != 0)
			return true;
		return false;
	}

	public Document(String title, String content) {
		this.title = title.trim();
		this.content = content.trim();
		stemmedDocument = "";

		Stemmer s = new Stemmer();
		String temp = this.title + "\n" + this.content;
		temp = temp.toLowerCase().replace('-', ' ').replaceAll("[ \t\n]+", " ");
		for (int i = 0; i < temp.length(); i++) {
			char ch = temp.charAt(i);
			if (Character.isLetter(ch))
				s.add(ch);
			else if (Character.isWhitespace(ch)) {
				s.stem();
				stemmedDocument += s.toString() + " ";
			}
		}
		s.stem();
		stemmedDocument += s.toString();
		stemmedDocument = stemmedDocument.replaceAll("[ \t\n]+", " ").trim();
		bag = new BagOfWords(stemmedDocument);
	}

	public Document() {
		// TODO Auto-generated constructor stub
	}

	public void applyKeywordSet(ArrayList<Keyword> keywords) {
		tf = new TF(bag, keywords);
		// System.out.println(getTitle() + " length TF: " +
		// tf.getVectorLength());
	}

	public void applyIDF(IDF idf) {
		tfidf = new TFIDF(idf, tf);
		// System.out.println(getTitle() + " length TFIDF: "
		// + tfidf.getVectorLength());
	}

	public void calculateTFSimiliarity(Document document) {
		similiarity = 0.0;
		if (tf.getVectorLength() == 0)
			return;
		if (document.tf.getVectorLength() == 0)
			return;
		for (String keyword : bag.getWords()) {
			similiarity += tf.getWordValue(keyword)
					* document.tf.getWordValue(keyword);
		}
		similiarity = similiarity
				/ (tf.getVectorLength() * document.tf.getVectorLength());
	}

	public void calculateTFIDFSimiliarity(Document document) {
		similiarity = 0.0;
		if (tfidf.getVectorLength() == 0)
			return;
		if (document.tfidf.getVectorLength() == 0)
			return;
		for (String keyword : bag.getWords()) {
			similiarity += tfidf.getWordValue(keyword)
					* document.tfidf.getWordValue(keyword);
		}
		similiarity = similiarity
				/ (tfidf.getVectorLength() * document.tfidf.getVectorLength());
	}

	@Override
	public int compareTo(Document o) {
		if (o.getSimiliarity() > getSimiliarity())
			return 1;
		if (o.getSimiliarity() < getSimiliarity())
			return -1;
		return 0;
	}
}
