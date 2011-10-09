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
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public abstract class PresentationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6063877896245955998L;
	protected JScrollPane scrollPane;
	protected JTextPane displayTextPane;
	/**
	 * @wbp.nonvisual location=660,137
	 */
	protected final ButtonGroup typeButtonGroup = new ButtonGroup();
	protected JRadioButton rdbtnOriginal;
	protected JRadioButton rdbtnStemmed;
	
	protected DefaultStyledDocument originalDocument;
	protected DefaultStyledDocument stemmedDocument;
	protected SimpleAttributeSet titleAttributes;
	protected SimpleAttributeSet textAttributes;
	protected SimpleAttributeSet errorAttributes;

	/**
	 * Create the dialog.
	 */
	public PresentationDialog() {
		setResizable(false);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 39, 774, 519);

		getContentPane().add(scrollPane);

		displayTextPane = new JTextPane();
		displayTextPane.setEditable(false);
		scrollPane.setViewportView(displayTextPane);
		
		rdbtnOriginal = new JRadioButton("Original");
		rdbtnOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnOriginal.isSelected())
					displayTextPane.setStyledDocument(originalDocument);
			}
		});
		rdbtnOriginal.setSelected(true);
		rdbtnOriginal.setBounds(12, 8, 149, 23);
		
		rdbtnStemmed = new JRadioButton("Stemmed");
		rdbtnStemmed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnStemmed.isSelected())
					displayTextPane.setStyledDocument(stemmedDocument);
			}
		});
		rdbtnStemmed.setBounds(198, 8, 149, 23);
		
		typeButtonGroup.add(rdbtnOriginal);
		typeButtonGroup.add(rdbtnStemmed);
		
		getContentPane().add(rdbtnOriginal);
		getContentPane().add(rdbtnStemmed);
		
		titleAttributes = new SimpleAttributeSet();
		titleAttributes.addAttribute(StyleConstants.CharacterConstants.Bold,
				Boolean.TRUE);
		titleAttributes.addAttribute(
				StyleConstants.CharacterConstants.Underline, Boolean.TRUE);
		
		textAttributes = new SimpleAttributeSet();
		
		errorAttributes = new SimpleAttributeSet();
		errorAttributes.addAttribute(StyleConstants.Foreground, Color.RED);
		errorAttributes.addAttribute(StyleConstants.CharacterConstants.Bold,
				Boolean.TRUE);
		
		originalDocument = new DefaultStyledDocument();
		stemmedDocument = new DefaultStyledDocument();

	}

}
