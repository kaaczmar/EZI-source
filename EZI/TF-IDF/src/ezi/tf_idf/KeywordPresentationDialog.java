package ezi.tf_idf;

import java.util.ArrayList;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import ezi.tf_idf.data.Keyword;

public class KeywordPresentationDialog extends PresentationDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -626861654865397691L;

	public KeywordPresentationDialog() {
		this.setTitle("Keywords");
		try {
			originalDocument.insertString(originalDocument.getLength(), "No keywords to display at this time", errorAttributes);
			stemmedDocument.insertString(stemmedDocument.getLength(), "No keywords to display at this time", errorAttributes);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		displayTextPane.setStyledDocument(originalDocument);
	}
	
	public void update(ArrayList<Keyword> keywords){
		originalDocument = new DefaultStyledDocument();
		stemmedDocument = new DefaultStyledDocument();
		
		try{
			for (Keyword keyword : keywords) {
				stemmedDocument.insertString(stemmedDocument.getLength(), keyword.getStemmedKeyword() + "\n", textAttributes);
				originalDocument.insertString(originalDocument.getLength(), keyword.getOriginalKeyword() + "\n", textAttributes);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		if (rdbtnOriginal.isSelected())
			displayTextPane.setStyledDocument(originalDocument);
		else
			displayTextPane.setStyledDocument(stemmedDocument);
		
		scrollPane.getVerticalScrollBar().setValue(0);
	
	}
	
}
