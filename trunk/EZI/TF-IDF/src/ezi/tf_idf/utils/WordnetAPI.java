package ezi.tf_idf.utils;

import java.util.ArrayList;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import ezi.tf_idf.data.Keyword;

public class WordnetAPI {
	public WordnetAPI() {
		System.setProperty("wordnet.database.dir", "/usr/local/WordNet-3.0/dict");
	}
	
	private boolean keywordContains(ArrayList<Keyword> keywords, String keyword){
		Keyword checkedKeyword = new Keyword(keyword);		
		
		for (Keyword key : keywords){
			if (key.getStemmedKeyword().equalsIgnoreCase(checkedKeyword.getStemmedKeyword()))
				return true;
		}
		return false;
	}
	
	public void findSynonims(String word, ArrayList<String> synonims, ArrayList<Keyword> keywords, ArrayList<String> query){
		WordNetDatabase database = WordNetDatabase.getFileInstance(); 
		
		Synset[] synsets = database.getSynsets(word);
		
		for (Synset syn : synsets) { 
			if (!synonims.contains((String)syn.getWordForms()[0]) 
					&& !syn.getWordForms()[0].equalsIgnoreCase(word) 
					&& keywordContains(keywords, (String)syn.getWordForms()[0]) 
					&& !query.contains((String)syn.getWordForms()[0])){
				synonims.add(syn.getWordForms()[0]);
			}
		}
	}
	
}
