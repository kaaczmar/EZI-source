package ezi.tf_idf.data;

import ezi.tf_idf.utils.Stemmer;

/**
 * Used to store a keyword in both original and stemmed format.
 * 
 * @author Mateusz
 * 
 */
public class Keyword {
	private String originalKeyword;
	private String stemmedKeyword;
	private Boolean isDoubled;

	public String getOriginalKeyword() {
		return originalKeyword;
	}

	public String getStemmedKeyword() {
		return stemmedKeyword;
	}

	public Boolean getIsDoubled() {
		return isDoubled;
	}

	public void setIsDoubled(Boolean isDoubled) {
		this.isDoubled = isDoubled;
	}
	
	public Keyword(String term) {
		isDoubled = false;
		
		originalKeyword = term.replaceAll("[ \t\n]+", " ").trim().toLowerCase();

		Stemmer s = new Stemmer();
		for (int i = 0; i < originalKeyword.length(); i++) {
			char ch = originalKeyword.charAt(i);
			if (Character.isLetter(ch))
				s.add(ch);
		}
		s.stem();
		stemmedKeyword = s.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stemmedKeyword == null) ? 0 : stemmedKeyword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Keyword other = (Keyword) obj;
		if (stemmedKeyword == null) {
			if (other.stemmedKeyword != null)
				return false;
		} else if (!stemmedKeyword.equals(other.stemmedKeyword))
			return false;
		return true;
	}
}
