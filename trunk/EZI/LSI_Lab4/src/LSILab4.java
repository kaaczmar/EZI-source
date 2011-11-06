import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

public class LSILab4 {
    Matrix M;
    Matrix Q;

    public static void main(String [] args) {
    	LSILab4 lsi = new LSILab4();
        lsi.go();
    }

    private void go() {
        // init the matrix and the query
        M = readMatrix("data.txt");
        Q = readMatrix("query.txt");
        
        // print
        System.out.println("Matrix:");
        M.print(3, 2);

        // print the dimensions of the matrix
        System.out.println("M: " + dim(M));
        // print the query
        System.out.println("Query:");
        Q.print(3, 2);
        System.out.println("Q: " + dim(Q));

        // do svd
        svd();
    }

    private void svd() {

	//TODO implement your solution

        SingularValueDecomposition svd = new SingularValueDecomposition(M);
        // get K, S, and D
        Matrix K = svd.getU();
        Matrix S = svd.getS();
        Matrix D = svd.getV();
        D = D.transpose();
        
        System.out.println("\nK: " + dim(K) + "\nS: " + dim(S) + "\nD: " + dim(D));
       
        // set number of largest singular values to be considered
        //int s = 4;
        int s = 2;
        
        System.out.println("\ns = " + s);

        // cut off appropriate columns and rows from K, S, and D
        K = K.getMatrix(0, K.getRowDimension() - 1, 0, s - 1);
        S = S.getMatrix(0, s - 1, 0, s - 1);
        D = D.getMatrix(0, s - 1, 0, D.getColumnDimension() - 1);
        
        System.out.print("\nKS: " + dim(K));
        K.print(3, 2);
        
        System.out.print("\nSS: " + dim(S));
        S.print(3, 2);
        
        System.out.print("\nDST: " + dim(D));
        D.print(3, 2);
        
        // transform the query vector
        Matrix Q_prim = Q.transpose();
        
        Q_prim = Q_prim.times(K);
        Q_prim = Q_prim.times(S.inverse());
        
        System.out.print("QS: ");
        Q_prim.print(3,2);
            
        // compute similaraty of the query and each of the documents, using cosine measure
        double q_norm2 = Q_prim.norm2();
        D = D.transpose();
        for (int i = 0; i < D.getRowDimension(); i++){
        	Matrix document = D.getMatrix(i, i, 0, D.getColumnDimension() - 1);
        	double document_norm2 = document.norm2();
        	double document_sum = sumMatrix(document.arrayTimes(Q_prim));
        	System.out.println("Doc "+(i+1)+": " + document_sum/(q_norm2*document_norm2) );
        }

    }
    
    private double sumMatrix(Matrix mat){
    	double sum = 0;
    	for (int x = 0; x < mat.getColumnDimension(); x++){
    		for (int y = 0; y < mat.getRowDimension(); y++){
    			sum += mat.get(y, x);
    		}
    	}
    	return sum;
    }


    // returns the dimensions of a matrix
    private String dim(Matrix M) {
        return M.getRowDimension() + "x" + M.getColumnDimension();
    }

    // reads a matrix from a file
    private Matrix readMatrix(String filename) {
        Vector<Vector<Double>> m = new Vector<Vector<Double>>();
        int colums = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while (br.ready()) {
                Vector<Double> row = new Vector<Double>();
                m.add(row);
                String line = br.readLine().trim();
                StringTokenizer st = new StringTokenizer(line, ", ");
                colums = 0;
                while (st.hasMoreTokens()) {
                    row.add(Double.parseDouble(st.nextToken()));
                    colums++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rows = m.size();
        Matrix M = new Matrix(rows, colums);
        int rowI = 0;
        for (Vector<Double> vector : m) {
            int colI = 0;
            for (Double d : vector) {
                M.set(rowI, colI, d);
                colI++;
            }
            rowI++;
        }
        return M;
    }
}
