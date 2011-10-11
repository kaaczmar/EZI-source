package ezi.tf_idf.data;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CustomListCellRenderers {
	public static class OriginalDocumentCellRenderer implements ListCellRenderer{
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			
			Document object = (Document) value;

			String text = new String("<b>"+object.getTitle()+"</b><br/><table width=550px><tr><td>" + object.getContent()+"</td></tr>");
			
			JEditorPane textPane = new JEditorPane();
			textPane.setContentType("text/html");
			textPane.setText(text);

			if (cellHasFocus || isSelected){
				textPane.setBackground(Color.LIGHT_GRAY);
			}
			
			return textPane;			
		}
		
	}
	
	public static class StemmedDocumentCellRenderer implements ListCellRenderer{

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			Document object = (Document) value;

			String text = new String("<b>"+object.getTitle()+"</b><br/><table width=550px><tr><td>" + object.getStemmedDocument()+"</td></tr>");
			
			JEditorPane textPane = new JEditorPane();
			textPane.setContentType("text/html");
			textPane.setText(text);

			if (cellHasFocus || isSelected){
				textPane.setBackground(Color.LIGHT_GRAY);
			}
			
			return textPane;		
		}
		
	}
	
	public static class OriginalKeywordCellRenderer implements ListCellRenderer{
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			
			Keyword object = (Keyword) value;

			String text = new String(object.getOriginalKeyword());
			
			JEditorPane textPane = new JEditorPane();
			textPane.setContentType("text/html");
			textPane.setText(text);

			if (cellHasFocus || isSelected){
				textPane.setBackground(Color.LIGHT_GRAY);
			}
			
			return textPane;			
		}
		
	}
	
	public static class StemmedKeywordCellRenderer implements ListCellRenderer{

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			Keyword object = (Keyword) value;

			String text = new String(object.getStemmedKeyword());
			
			JEditorPane textPane = new JEditorPane();
			textPane.setContentType("text/html");
			textPane.setText(text);

			if (cellHasFocus || isSelected){
				textPane.setBackground(Color.LIGHT_GRAY);
			}
			
			return textPane;		
		}
		
	}
	
	public static class ResultsCellRenderer implements ListCellRenderer{

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			Document object = (Document) value;

			String text = new String("<table width=550px><tr><td width=7%>" + (index+1) + "</td><td width=80%>" + object.getTitle() + "</td><td width=13%>" + Math.round(object.getSimiliarity()*10000) / 10000.0 +"</td></tr>");
			
			JEditorPane textPane = new JEditorPane();
			textPane.setContentType("text/html");
			textPane.setText(text);

			if (cellHasFocus || isSelected){
				textPane.setBackground(Color.LIGHT_GRAY);
			}
			
			return textPane;		
		}
		
	}
	
}
