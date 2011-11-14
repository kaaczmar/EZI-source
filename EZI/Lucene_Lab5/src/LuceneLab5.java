import java.io.*;
import java.util.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;
import org.apache.lucene.document.*;

public class LuceneLab5 {
	//directory where the index would be placed in
	//provided by the user as args[1]; set in main()   
    public static String indexPath;

    public static void createIndex(String path) throws Exception {
    	IndexWriter indexWriter = new IndexWriter(indexPath, new StandardAnalyzer(), true);
    	File documents = new File(path);
    	
    	for (String doc : documents.list()){
    		indexWriter.addDocument(indexDoc(documents.toString()+"/"+doc.toString()));
    	}
    	indexWriter.close();

    }
    
    public static Document indexDoc(String docPath) throws Exception {
    	FileInputStream file = new FileInputStream(docPath); 
    	Document doc = new Document();  
    	doc.add(new Field("path", docPath, Field.Store.YES, Field.Index.TOKENIZED));  
    	doc.add(new Field("content", (Reader) new InputStreamReader(file)));
		return doc;
    }

    public static Hits processQuery(String queryString) throws IOException, ParseException {
    	IndexSearcher isearch = new IndexSearcher(indexPath);
    	Analyzer analyzer = new StandardAnalyzer();  
    	QueryParser qparser = new QueryParser("content", analyzer);

		Query query = qparser.parse(queryString);  
		Hits hits = isearch.search(query);
    	
		return hits;
    }
    
    public static void main(String [] args) {
		if (args.length < 2) {
		    System.out.println("java -cp lucene-core-2.2.0.jar:. BasicIRsystem texts_path index_path");
		    System.out.println("need two args with paths to the collection of texts and to the directory where the index would be stored, respectively");
		    System.exit(1);
		}
		try {
		    String textsPath = args[0];
		    indexPath = args[1];
		    createIndex(textsPath);
		    String query = " ";

		    //process queries until one writes "lab5"
		    while (true) {
				Scanner sc = new Scanner(System.in);
				System.out.println("Please enter your query: (lab5 to quit)");
				query = sc.next();
				
				if (query.equals("lab5")) {break;} //to quit
					
				Hits hits = processQuery(query);
				
				if (hits != null)
				{
					System.out.println(hits.length() + " result(s) found");
				
					Iterator iter = hits.iterator();
					while(iter.hasNext()){
					    Hit hit = (Hit) iter.next();
					    
					    try {
					    	Document doc = hit.getDocument();
					    	Field title = doc.getField("path");
					    	System.out.println(title.stringValue()+" : "+hit.getScore());
					    }
					    catch (Exception e) {
					    	System.err.println("Unexpected exception");
					    	System.err.println(e.toString());
					    }
					}
				}
				else
				{
					System.out.println("Processing the query still not implemented, heh?");
				}
		    }
		    
		} catch (Exception e) {
		    System.err.println("Even more unexpected exception");
		    System.err.println(e.toString());
		}   
    }
}