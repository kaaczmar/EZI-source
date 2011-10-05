package ezi.tf_idf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DocumentFileParser {
	public static ArrayList<Document> parse(String filename){
		System.out.println("Parsing: " + filename);
		
		int id = 0;		
		ArrayList<Document> documents = new ArrayList<Document>();
		Document currentDocument = new Document(id++);
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String str;
			while ((str = in.readLine()) != null) {
				if (currentDocument.getTitle()== null){
					currentDocument.setTitle(str);
					System.out.println("Dokument " + currentDocument.getId() + " -> " + currentDocument.getTitle());
				}
				else if (str.equalsIgnoreCase("")) {
					documents.add(currentDocument);
					currentDocument = new Document(id++);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return documents;
	}
}
