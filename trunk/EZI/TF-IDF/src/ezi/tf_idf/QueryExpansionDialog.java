package ezi.tf_idf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ezi.tf_idf.algorithm.IDF;
import ezi.tf_idf.data.Keyword;
import ezi.tf_idf.data.Query;

public class QueryExpansionDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2800804400986018447L;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnSearch;
	private JButton btnCancel;
	private boolean result = false;

	private Query query;
	private ArrayList<Keyword> keywords;
	private IDF idf;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// try {
	// QueryExpansionDialog dialog = new QueryExpansionDialog();
	// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	// dialog.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public QueryExpansionDialog(JFrame owner) {
		super(owner);
		initGUI();
	}

	/**
	 * Create the dialog.
	 */
	public QueryExpansionDialog() {
		initGUI();
	}

	private void initGUI() {
		setTitle("Query expansion");
		setBounds(100, 100, 500, 600);
		getContentPane().setLayout(null);

		setModal(true);

		table = new JTable();

		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Word", "Weight", "Use" }) {
			private static final long serialVersionUID = 7070720547880801555L;
			boolean[] columnEditables = new boolean[] { false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

			Class[] columnTypes = new Class[] { String.class, Double.class,
					Boolean.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

		};

		table.setModel(tableModel);

		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(255);
		table.getColumnModel().getColumn(0).setMinWidth(255);
		table.getColumnModel().getColumn(0).setMaxWidth(255);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setMinWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(100);

		table.setRowHeight(40);

		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 12, 474, 506);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		getContentPane().add(scrollPane);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				collectResults();
				result = true;
				setVisible(false);
			}
		});
		btnSearch.setBounds(326, 530, 117, 25);
		getContentPane().add(btnSearch);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = false;
				setVisible(false);
			}
		});
		btnCancel.setBounds(38, 530, 117, 25);
		getContentPane().add(btnCancel);
	}

	public void initTable(String queryString, IDF idf,
			ArrayList<Keyword> keywords) {
		this.query = new Query(queryString, keywords, idf);
		this.idf = idf;
		this.keywords = keywords;

		// THIS COULD BE DONE BETTER IF BAG OF WORDS WOULD ACT ON KEYWORDS
		// INSTEAD OF STRINGS
		// BUT BECAUSE I'M LAZY IT STAYS AS IT IS ;)
		ArrayList<Keyword> usedWords = new ArrayList<Keyword>();

		tableModel.setRowCount(0);

		for (String word : query.getWords()) {
			if (word.isEmpty())
				continue;
			Keyword s = new Keyword(word);
			if (usedWords.contains(s))
				continue;
			usedWords.add(s);
			tableModel.addRow(new Object[] { s.getOriginalKeyword(),
					query.getWordValue(s.getStemmedKeyword()),
					new Boolean(false) });
		}
	}

	public boolean getResult() {
		return result;
	}

	public Query getQuery() {
		return query;
	}

	private void collectResults() {
		String newQuery = "";
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if ((Boolean) table.getValueAt(i, 2))
				newQuery += (String) table.getValueAt(i, 0) + " ";
		}
		query = new Query(newQuery, keywords, idf);
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if ((Boolean) table.getValueAt(i, 2)) {
				Keyword key = new Keyword((String) table.getValueAt(i, 0));
				query.setWordValue(key.getStemmedKeyword(),
						(Double) table.getValueAt(i, 1));
			}
		}
		query.updateVectorLength(keywords);
	}
}
