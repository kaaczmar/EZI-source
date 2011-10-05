package ezi.tf_idf;

public class Document {
	private int id = -1;
	private String title = null;
	private String originalText = null;
	private String stemmedText = null;
	
	public Document(int id){
		this.id = id;
	}
	
	public Document(int id, String title, String originalText) {
		this.id = id;
		this.title = title;
		this.originalText = originalText;
		this.stemmedText = Stemmer.stem(originalText);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setStemmedText(String stemmedText) {
		this.stemmedText = stemmedText;
	}

	public String getStemmedText() {
		return stemmedText;
	}
}
