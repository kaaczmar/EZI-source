package ezi.tf_idf;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JDialog;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ezi.tf_idf.data.CustomListCellRenderers;
import ezi.tf_idf.data.Document;
import ezi.tf_idf.data.Keyword;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public abstract class PresentationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6063877896245955998L;
	protected JScrollPane scrollPane;
	/**
	 * @wbp.nonvisual location=660,137
	 */
	protected final ButtonGroup typeButtonGroup = new ButtonGroup();
	protected JRadioButton rdbtnOriginal;
	protected JRadioButton rdbtnStemmed;
	
	protected JList displayList;

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
		
		displayList = new JList();
		displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(displayList);
		
		rdbtnOriginal = new JRadioButton("Original");
		rdbtnOriginal.setEnabled(false);
		rdbtnOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtClick();
			}
		});
		rdbtnOriginal.setSelected(true);
		rdbtnOriginal.setBounds(12, 8, 149, 23);
		
		rdbtnStemmed = new JRadioButton("Stemmed");
		rdbtnStemmed.setEnabled(false);
		rdbtnStemmed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtClick();
			}
		});
		rdbtnStemmed.setBounds(198, 8, 149, 23);
		
		typeButtonGroup.add(rdbtnOriginal);
		typeButtonGroup.add(rdbtnStemmed);
		
		getContentPane().add(rdbtnOriginal);
		getContentPane().add(rdbtnStemmed);
		
	}
	
	private void rdbtClick(){
		if (this instanceof DocumentPresentationDialog){
			if (rdbtnOriginal.isSelected())
				displayList.setCellRenderer(new CustomListCellRenderers.OriginalDocumentCellRenderer());
			else
				displayList.setCellRenderer(new CustomListCellRenderers.StemmedDocumentCellRenderer());
		} else {
			if (rdbtnOriginal.isSelected())
				displayList.setCellRenderer(new CustomListCellRenderers.OriginalKeywordCellRenderer());
			else
				displayList.setCellRenderer(new CustomListCellRenderers.StemmedKeywordCellRenderer());
		}
	}

}
