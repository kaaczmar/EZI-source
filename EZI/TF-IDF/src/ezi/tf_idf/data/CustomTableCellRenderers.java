package ezi.tf_idf.data;

import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellRenderer;

public class CustomTableCellRenderers {
	public static class SpinnerCellRenderer implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			SpinnerModel model = new SpinnerNumberModel(0,0,10,1);

			JSpinner spinner = new JSpinner(model);
			spinner.setValue(value);
			
			return spinner;
		}
		
	}

}
