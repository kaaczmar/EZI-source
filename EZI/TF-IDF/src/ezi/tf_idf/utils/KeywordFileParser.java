package ezi.tf_idf.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ezi.tf_idf.data.Keyword;

/**
 * Static class used to parse file and read all keywords stored in it. Checks
 * for double keywords and omits them.
 * 
 * @author Mateusz
 * 
 */
public class KeywordFileParser {
	public static ArrayList<Keyword> parse(String filename) {
		System.out.println("Parsing: " + filename);

		ArrayList<Keyword> keywords = new ArrayList<Keyword>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String str;
			while ((str = in.readLine()) != null) {
				if (!str.equals("")) {
					Keyword keyword = new Keyword(str);
					if (!keywords.contains(keyword)) {
						System.out.println("Keyword "
								+ keyword.getOriginalKeyword() + " as "
								+ keyword.getStemmedKeyword());
						keywords.add(keyword);
					} else
					{
						//set the property isDoubled for keyword and add to the list
						keyword.setIsDoubled(true);
						keywords.add(keyword);
						System.out
								.println("Hey, you have a double keyword there! ["
										+ keyword.getOriginalKeyword()
										+ ","
										+ keyword.getStemmedKeyword() + "]");
					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return keywords;
	}
}
