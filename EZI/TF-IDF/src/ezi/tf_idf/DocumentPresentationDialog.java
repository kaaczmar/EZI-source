package ezi.tf_idf;

import java.util.ArrayList;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import ezi.tf_idf.data.Document;

public class DocumentPresentationDialog extends PresentationDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1664466968918092630L;

	public DocumentPresentationDialog() {
		this.setTitle("Documents");
		
		try {
			originalDocument.insertString(originalDocument.getLength(), "No documents to display at this time", errorAttributes);
			stemmedDocument.insertString(stemmedDocument.getLength(), "No documents to display at this time", errorAttributes);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		displayTextPane.setStyledDocument(originalDocument);
	}
	
	public void update(ArrayList<Document> documents){
		originalDocument = new DefaultStyledDocument();
		stemmedDocument = new DefaultStyledDocument();
		
		try {
			for (Document doc : documents) {
				stemmedDocument.insertString(stemmedDocument.getLength(), doc.getTitle() + "\n", titleAttributes);
				stemmedDocument.insertString(stemmedDocument.getLength(), doc.getStemmedDocument() + "\n\n", textAttributes);

				originalDocument.insertString(originalDocument.getLength(), doc.getTitle() + "\n", titleAttributes);
				originalDocument.insertString(originalDocument.getLength(), doc.getContent() + "\n\n", textAttributes);
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
