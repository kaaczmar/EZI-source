package ezi.tf_idf;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import ezi.tf_idf.data.CustomListCellRenderers;
import ezi.tf_idf.data.Document;

public class DocumentPresentationDialog extends PresentationDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1664466968918092630L;

	public DocumentPresentationDialog() {
		this.setTitle("Documents");
		
		displayList.setListData(new String[]{"No documents to display at this time"});
//		displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		scrollPane.setViewportView(displayList);
		
	}
	
	public void update(ArrayList<Document> documents){
//		displayList = new JList(documents.toArray());
//		displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		displayList.setListData(documents.toArray());
		
		if (rdbtnOriginal.isSelected()){
			displayList.setCellRenderer(new CustomListCellRenderers.OriginalDocumentCellRenderer());
		}
		else{
			displayList.setCellRenderer(new CustomListCellRenderers.StemmedDocumentCellRenderer());
		}

//		scrollPane.setViewportView(displayList);
		scrollPane.getVerticalScrollBar().setValue(0);
		
		rdbtnOriginal.setEnabled(true);
		rdbtnStemmed.setEnabled(true);
	
	}
	
}
