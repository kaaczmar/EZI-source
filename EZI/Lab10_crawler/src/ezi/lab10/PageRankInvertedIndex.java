package ezi.lab10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ir.utilities.DoubleValue;
import ir.utilities.Weight;
import ir.vsr.DocumentIterator;
import ir.vsr.DocumentReference;
import ir.vsr.HashMapVector;
import ir.vsr.InvertedIndex;
import ir.vsr.Retrieval;

public class PageRankInvertedIndex extends InvertedIndex {

	private double weight = 0;
	private HashMap<String, Double> pageRanks = null;

	public PageRankInvertedIndex(File dirFile, short docType, boolean stem,
			boolean feedback, double weight) {
		super(dirFile, docType, stem, feedback);
		this.weight = weight;
		loadPageRanks();
	}
	
	private void loadPageRanks(){
		pageRanks = new HashMap<String, Double>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(dirFile.getAbsolutePath()+"/PageRanks.txt")));
			
			String line = null;
			while (( line = br.readLine()) != null){
				String[] values = line.split(" ");
				pageRanks.put(values[0], Double.parseDouble(values[1]));
	        }
			
			br.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("PageRanks file not found in directory");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Perform ranked retrieval on this input query Document vector. */
	@Override
    public Retrieval[] retrieve(HashMapVector vector) {
	// Create a hashtable to store the retrieved documents.  Keys
	// are docRefs and values are DoubleValues which indicate the
	// partial score accumulated for this document so far.
	// As each token in the query is processed, each document
	// it indexes is added to this hashtable and its retrieval
	// score (similarity to the query) is appropriately updated.
	HashMap retrievalHash = new HashMap();
	// Initialize a variable to store the length of the query vector
	double queryLength = 0.0;
	// Iterate through each token in the query input Document
	Iterator mapEntries = vector.iterator();
	while (mapEntries.hasNext()) {
	    // Get the token and the count for each token in the query
	    Map.Entry entry = (Map.Entry)mapEntries.next();
	    String token = (String)entry.getKey();
	    double count = ((Weight)entry.getValue()).getValue();
	    // Determine the score added to the similarity of each document
	    // indexed under this token and update the length of the
	    // query vector with the square of the weight for this token.
	    queryLength = queryLength + incorporateToken(token, count, retrievalHash);
	}
	// Finalize the length of the query vector by taking the square-root of the
	// final sum of squares of its token wieghts.
	queryLength = Math.sqrt(queryLength);
	// Make an array to store the final ranked Retrievals.
	Retrieval[] retrievals = new Retrieval[retrievalHash.size()];
	// Iterate through each of the retreived docuements stored in
	// the final retrievalHash.
	Iterator rmapEntries = retrievalHash.entrySet().iterator();
	int retrievalCount = 0;
	while (rmapEntries.hasNext()) {
	    // Get the DocumentReference and score for each retrieved document
	    Map.Entry entry = (Map.Entry)rmapEntries.next();
	    DocumentReference docRef = (DocumentReference)entry.getKey();
	    double score = ((DoubleValue)entry.getValue()).value;
	    // Normalize score for the lengths of the two document vectors
	    score = score / (queryLength * docRef.length);  
	    score = score + (weight * pageRanks.get(docRef.file.getName()));
	    // Add a Retrieval for this document to the result array
	    retrievals[retrievalCount++] = new Retrieval(docRef, score);
	}
	// Sort the retrievals to produce a final ranked list using the
	// Comparator for retrievals that produces a best to worst ordering.
	Arrays.sort(retrievals);
	return retrievals;
    }

	public static void main(String[] args) {
		// Parse the arguments into a directory name and optional flag
		String dirName = args[args.length - 1];
		short docType = DocumentIterator.TYPE_HTML;
		boolean stem = false, feedback = false;
		double weight = 2;
		int i = 0;
		while (i < args.length - 1) {
			String flag = args[i];
			if (flag.equals("-stem"))
				// Stem tokens with Porter stemmer
				stem = true;
			else if (flag.equals("-feedback"))
				// Use relevance feedback
				feedback = true;
			else if (flag.equals("-weight")) {
				i++;
				weight = Double.parseDouble(args[i]);
			} else {
				System.out.println("\nUnknown flag: " + flag);
				System.exit(1);
			}
			i++;
		}
		// Create an inverted index for the files in the given directory.
		PageRankInvertedIndex index = new PageRankInvertedIndex(new File(
				dirName), docType, stem, feedback, weight);
		// index.print();
		// Interactively process queries to this index.
		index.processQueries();
	}

}
