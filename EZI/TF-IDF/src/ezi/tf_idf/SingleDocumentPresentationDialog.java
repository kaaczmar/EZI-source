package ezi.tf_idf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import ezi.tf_idf.data.Document;

public class SingleDocumentPresentationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6063877896245955998L;
	protected JScrollPane scrollPane;
	private JTextPane textPane;
	protected JRadioButton rdbtnOriginal;
	protected JRadioButton rdbtnStemmed;
	protected final ButtonGroup typeButtonGroup = new ButtonGroup();

	private Document doc;

	/**
	 * Create the dialog.
	 */
	public SingleDocumentPresentationDialog() {
		setResizable(false);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 39, 774, 519);

		getContentPane().add(scrollPane);

		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);

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

	public void displayDocument(Document doc) {
		this.doc = doc;

		this.setTitle(doc.getTitle());

		textPane.setText(doc.getContent());

		rdbtnOriginal.setEnabled(true);
		rdbtnStemmed.setEnabled(true);
	}

	private void rdbtClick() {
		if (rdbtnOriginal.isSelected())
			textPane.setText(doc.getContent());
		else
			textPane.setText(doc.getStemmedDocument());
	}
}
