package ezi.tf_idf;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class QueryExpansionDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2800804400986018447L;
	private JTable table;
	private DefaultTableModel tableModel;
	boolean updatingTable;

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

	/**
	 * Create the dialog.
	 */
	public QueryExpansionDialog() {
		setBounds(100, 100, 500, 600);
		getContentPane().setLayout(null);

		table = new JTable() {
			private static final long serialVersionUID = -1978493142990925510L;
			// public TableCellRenderer getCellRenderer(int row, int column){
			// if (column == 1)
			// return new CustomTableCellRenderers.SpinnerCellRenderer();
			// else
			// return new DefaultTableCellRenderer();
			// }
		};

		tableModel = new DefaultTableModel(new Object[][] {}, new String[] {
				"Word", "Weight", "Weight Norm" }) {
			private static final long serialVersionUID = 7070720547880801555L;
			boolean[] columnEditables = new boolean[] { false, true, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
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

		tableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (updatingTable)
					return;
				countNormalizedWeights();
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 12, 474, 506);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		getContentPane().add(scrollPane);

	}

	private void countNormalizedWeights() {
		updatingTable = true;

		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			try {
				double value = Double.parseDouble((String) tableModel
						.getValueAt(i, 1));

				if (value < min)
					min = value;
				if (value > max)
					max = value;

			} catch (NumberFormatException e1) {
				System.err.println("Invalid number");
				return;
			}
		}

		if (min < 0) {
			System.err.println("Weight can not be lower than 0");
			return;
		}

		if (max == 0)
			return;

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			try {
				double value = Double.parseDouble((String) tableModel
						.getValueAt(i, 1));

				tableModel.setValueAt(Math.round((100 * value) / max) / 100.0,
						i, 2);
			} catch (NumberFormatException e1) {
				System.err.println("Invalid number");
			}
		}

		updatingTable = false;
	}

	public void initTable(ArrayList<String> words) {
		tableModel.setRowCount(0);

		for (String s : words)
			tableModel.addRow(new Object[] { s, "0", "0" });
	}

}
