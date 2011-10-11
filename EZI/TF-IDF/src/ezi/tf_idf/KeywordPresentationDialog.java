package ezi.tf_idf;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import ezi.tf_idf.data.CustomListCellRenderers;
import ezi.tf_idf.data.Keyword;

public class KeywordPresentationDialog extends PresentationDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = -626861654865397691L;

	public KeywordPresentationDialog() {
		this.setTitle("Keywords");
	
		displayList.setListData(new String[]{"No keywords to display at this time"});
//		scrollPane.setViewportView(displayList);
	}
	
	public void update(ArrayList<Keyword> keywords){
//		displayList = new JList(keywords.toArray());
//		displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		displayList.setListData(keywords.toArray());
		
		if (rdbtnOriginal.isSelected()){
			displayList.setCellRenderer(new CustomListCellRenderers.OriginalKeywordCellRenderer());
		}
		else{
			displayList.setCellRenderer(new CustomListCellRenderers.StemmedKeywordCellRenderer());
		}

//		scrollPane.setViewportView(displayList);
		scrollPane.getVerticalScrollBar().setValue(0);
		
		rdbtnOriginal.setEnabled(true);
		rdbtnStemmed.setEnabled(true);
	
	}
	
}
