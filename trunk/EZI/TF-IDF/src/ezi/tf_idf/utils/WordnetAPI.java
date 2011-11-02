package ezi.tf_idf.utils;

import java.util.ArrayList;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
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
	
	private boolean queryContains(ArrayList<String> query, String keyword){
		Keyword checkedKeyword = new Keyword(keyword);		
		
		for (String word : query){
			Keyword queryKeyword = new Keyword(word);
			if (queryKeyword.getStemmedKeyword().equalsIgnoreCase(checkedKeyword.getStemmedKeyword()))
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
					&& !queryContains(query, (String)syn.getWordForms()[0])){
				synonims.add(syn.getWordForms()[0]);
			}
		}
		
		synsets = database.getSynsets(word, SynsetType.NOUN);
		
		for (Synset syn : synsets) { 
		    NounSynset nounSynset = (NounSynset)(syn); 
		    NounSynset[] hypernyms = nounSynset.getHypernyms();
//		    System.out.println(nounSynset.getWordForms()[0] + 
//		            ": " + nounSynset.getDefinition() + ") has " + hyponyms.length + " hyponyms");
		    for (NounSynset syn2 : hypernyms){
		    	if (!synonims.contains((String)syn2.getWordForms()[0]) 
					&& !syn2.getWordForms()[0].equalsIgnoreCase(word) 
					&& keywordContains(keywords, (String)syn2.getWordForms()[0]) 
					&& !queryContains(query, (String)syn2.getWordForms()[0])){
				synonims.add(syn2.getWordForms()[0]);
		    	}
		    }
		}
	}
	
}
