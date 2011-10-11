package ezi.tf_idf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import ezi.tf_idf.data.CustomListCellRenderers;

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

	private void rdbtClick() {
		if (this instanceof DocumentPresentationDialog) {
			if (rdbtnOriginal.isSelected())
				displayList
						.setCellRenderer(new CustomListCellRenderers.OriginalDocumentCellRenderer());
			else
				displayList
						.setCellRenderer(new CustomListCellRenderers.StemmedDocumentCellRenderer());
		} else {
			if (rdbtnOriginal.isSelected())
				displayList
						.setCellRenderer(new CustomListCellRenderers.OriginalKeywordCellRenderer());
			else
				displayList
						.setCellRenderer(new CustomListCellRenderers.StemmedKeywordCellRenderer());
		}
	}

}
