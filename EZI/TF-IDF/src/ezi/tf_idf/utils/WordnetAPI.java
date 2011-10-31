package ezi.tf_idf.utils;

import java.util.ArrayList;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class WordnetAPI {
	public WordnetAPI() {
		System.setProperty("wordnet.database.dir", "/usr/local/WordNet-3.0/dict");
	}
	
	public void findSynonims(String word, ArrayList<String> synonims){
		WordNetDatabase database = WordNetDatabase.getFileInstance(); 
		
		Synset[] synsets = database.getSynsets(word);
		
		for (Synset syn : synsets) { 
			if (!synonims.contains((String)syn.getWordForms()[0]) && !syn.getWordForms()[0].equalsIgnoreCase(word)){
				synonims.add(syn.getWordForms()[0]);
			}
		}
	}
	
}
