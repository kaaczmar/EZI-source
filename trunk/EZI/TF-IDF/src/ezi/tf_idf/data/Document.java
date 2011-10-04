package ezi.tf_idf.data;

import ezi.tf_idf.utils.Stemmer;

public class Document {
	private String title;
	private String content;
	private String stemmedDocument;
	
	public String getTitle() { return title; }
	public String getContent() { return content; }
	public String getStemmedDocument() { return stemmedDocument; }
	
	public Document(String title, String content)
	{
		this.title = title.trim();
		this.content = content.trim();
		stemmedDocument = "";
		
		Stemmer s = new Stemmer();
		String temp = this.title + "\n" + this.content;
		for (int i = 0; i < temp.length(); i++)
		{
			char ch = temp.charAt(i);
			if (Character.isLetter(ch)) s.add(ch);
			else if (Character.isWhitespace(ch))
			{
				s.stem();
				stemmedDocument += s.toString() + " ";
			}
		}
		s.stem();
		stemmedDocument += s.toString();
		stemmedDocument = stemmedDocument.replaceAll("[ \t\n]+", " ").trim();
	}
}
