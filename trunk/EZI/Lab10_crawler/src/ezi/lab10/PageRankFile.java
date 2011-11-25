package ezi.lab10;

import ir.utilities.MoreMath;
import ir.webutils.Graph;
import ir.webutils.Node;
import ir.webutils.PageRank;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class PageRankFile extends PageRank {
	private String folderName = null;

	public PageRankFile(String folderName) {
		this.folderName = folderName;
	}

	public void calculate(Graph graph, double alpha, int iterations) {
		Node[] nodes = graph.nodeArray();
		HashMap indexMap = new HashMap((int) 1.4 * nodes.length);
		double[] r = new double[nodes.length];
		double[] rp = new double[nodes.length];
		double[] e = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			indexMap.put(nodes[i].toString(), new Integer(i));
			r[i] = 1.0 / nodes.length;
			e[i] = alpha / nodes.length;
		}
		for (int j = 0; j < iterations; j++) {
			for (int i = 0; i < nodes.length; i++) {
				ArrayList inNodes = nodes[i].getEdgesIn();
				rp[i] = 0;
				for (int k = 0; k < inNodes.size(); k++) {
					Node inNode = (Node) inNodes.get(k);
					String inName = inNode.toString();
					int fanOut = inNode.getEdgesOut().size();
					rp[i] = rp[i]
							+ r[((Integer) indexMap.get(inName)).intValue()]
							/ fanOut;
				}
				rp[i] = rp[i] + e[i];
			}
			for (int i = 0; i < r.length; i++)
				r[i] = rp[i];
			normalize(r);
		}
		
		try {
			PrintWriter out = new PrintWriter(new FileWriter(folderName
					+ "/PageRanks.txt"));
			for (int i = 0; i < nodes.length; i++){
				System.out.println("PR("+nodes[i].toString() + ") " + r[i]);
				out.println(nodes[i].toString() + " " + r[i]);
			}
			
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
