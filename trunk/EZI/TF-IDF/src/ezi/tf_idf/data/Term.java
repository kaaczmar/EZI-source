package ezi.tf_idf.data;

import ezi.tf_idf.utils.Stemmer;

public class Term {
	private String originalTerm;
	private String stemmedTerm;
	
	public String getOriginalTerm() { return originalTerm; }
	public String getStemmedTerm() { return stemmedTerm; }
	
	public Term(String term)
	{
		originalTerm = term.replaceAll("[ \t\n]+", " ").trim();
		
		Stemmer s = new Stemmer();
		for (int i = 0; i < originalTerm.length(); i++)
		{
			char ch = originalTerm.charAt(i);
			if (Character.isLetter(ch)) s.add(ch);
		}
		s.stem();
		stemmedTerm = s.toString();
	}
}
