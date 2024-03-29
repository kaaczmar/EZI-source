package ezi.tf_idf.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ezi.tf_idf.data.Document;

public class DocumentFileParser {
	public static ArrayList<Document> parse(String filename) {
		// System.out.println("Parsing: " + filename);

		ArrayList<Document> documents = new ArrayList<Document>();
		String title = null;
		String documentClass = null;
		String content = null;

		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String str;
			while ((str = in.readLine()) != null) {
				if (documentClass == null) {
					documentClass = new String(str);
					// System.out.println("Document " + title);
					content = new String();
				} else if (title == null) {
					title = new String(str);
					// System.out.println("Document " + title);
					System.out.println(title);
//					content = new String();
				} else if (str.equalsIgnoreCase("")) {
					documents.add(new Document(title, documentClass, content));
					// System.out.println("Content: " + content);
					title = null;
					documentClass = null;
					content = null;
				} else {
					content += str + "\n";
				}
			}
			if (!content.equals(null)) {
				documents.add(new Document(title, documentClass, content));
				// System.out.println("Content: " + content);
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
