package ezi.tf_idf;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JDialog;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ezi.tf_idf.data.Document;
import ezi.tf_idf.data.Keyword;

public class PresentationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6063877896245955998L;
	private JScrollPane scrollPane;
	private JTextPane displayTextPane;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// try {
	// PresentationDialog dialog = new PresentationDialog();
	// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	// dialog.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * Create the dialog.
	 */
	public PresentationDialog() {
		setResizable(false);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 774, 546);

		getContentPane().add(scrollPane);

		displayTextPane = new JTextPane();
		displayTextPane.setEditable(false);
		scrollPane.setViewportView(displayTextPane);

	}

	public void displayDocuments(String title, ArrayList<Document> documents,
			boolean stemmed) {
		this.setTitle(title);

		StyledDocument document = new DefaultStyledDocument();
		
		SimpleAttributeSet titleAttributes = new SimpleAttributeSet();
		titleAttributes.addAttribute(StyleConstants.CharacterConstants.Bold,
				Boolean.TRUE);
		titleAttributes.addAttribute(
				StyleConstants.CharacterConstants.Underline, Boolean.TRUE);
		
		SimpleAttributeSet textAttributes = new SimpleAttributeSet();
		
		SimpleAttributeSet errorAttributes = new SimpleAttributeSet();
		
		errorAttributes.addAttribute(StyleConstants.Foreground, Color.RED);
		errorAttributes.addAttribute(StyleConstants.CharacterConstants.Bold,
				Boolean.TRUE);

		if (documents == null) {
			try {
				document.insertString(document.getLength(), "No documents to display at this time", errorAttributes);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} else {

			for (Document doc : documents) {
				if (stemmed)
					try {
						document.insertString(document.getLength(), doc
								.getTitle()
								+ "\n", titleAttributes);
						document.insertString(document.getLength(), doc
								.getStemmedDocument()
								+ "\n\n", textAttributes);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				else {
					try {
						document.insertString(document.getLength(), doc
								.getTitle()
								+ "\n", titleAttributes);
						document.insertString(document.getLength(), doc
								.getContent()
								+ "\n\n", textAttributes);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		displayTextPane.setStyledDocument(document);
	}
	
	public void displayKeywords(String title, ArrayList<Keyword> keywords,
			boolean stemmed) {
		this.setTitle(title);

		StyledDocument document = new DefaultStyledDocument();
		
		SimpleAttributeSet textAttributes = new SimpleAttributeSet();
		
		SimpleAttributeSet errorAttributes = new SimpleAttributeSet();
		
		errorAttributes.addAttribute(StyleConstants.Foreground, Color.RED);
		errorAttributes.addAttribute(StyleConstants.CharacterConstants.Bold,
				Boolean.TRUE);

		if (keywords == null) {
			try {
				document.insertString(document.getLength(), "No keywords to display at this time", errorAttributes);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} else {

			for (Keyword keyword : keywords) {
				if (stemmed)
					try {
						document.insertString(document.getLength(), keyword.getStemmedKeyword() + "\n", textAttributes);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				else {
					try {
						document.insertString(document.getLength(), keyword.getOriginalKeyword() + "\n", textAttributes);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		displayTextPane.setStyledDocument(document);
	}
}
