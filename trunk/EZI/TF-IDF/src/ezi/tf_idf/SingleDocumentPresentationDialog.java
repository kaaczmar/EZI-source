package ezi.tf_idf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JDialog;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
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

public class SingleDocumentPresentationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6063877896245955998L;
	protected JScrollPane scrollPane;
	private JTextPane textPane;

	/**
	 * Create the dialog.
	 */
	public SingleDocumentPresentationDialog() {
		setResizable(false);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 774, 546);

		getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
	}
	
	public void displayDocument(Document doc){
		this.setTitle(doc.getTitle());
		
		textPane.setText(doc.getContent());
	}
}
