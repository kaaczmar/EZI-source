package ezi.lab10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.print.attribute.standard.PagesPerMinute;

import ir.utilities.MoreMath;
import ir.utilities.MoreString;
import ir.webutils.Graph;
import ir.webutils.HTMLPage;
import ir.webutils.Link;
import ir.webutils.Node;
import ir.webutils.PathDisallowedException;
import ir.webutils.Spider;

public class PageRankSpider extends Spider {
	private Graph pageGraph = null;
	private HashMap<Link, String> pageMap = null;
	private List<HTMLPage> pages = null;

	@Override
	public void doCrawl() {
		pageMap = new HashMap<Link, String>();
		visited = new HashSet();
		pages = new ArrayList<HTMLPage>();
		
		while (linksToVisit.size() > 0 && count < maxCount) {
			// Pause if in slow mode
			if (slow) {
				synchronized (this) {
					try {
						wait(1000);
					} catch (InterruptedException e) {
					}
				}
			}
			// Take the top link off the queue
			Link link = (Link) linksToVisit.remove(0);
			System.out.println("Trying: " + link);
			// Skip if already visited this page
			if (!visited.add(link)) {
				System.out.println("Already visited");
				continue;
			}
			if (!linkToHTMLPage(link)) {
				System.out.println("Not HTML Page");
				continue;
			}
			HTMLPage currentPage = null;
			// Use the page retriever to get the page
			try {
				currentPage = webpr.getHTMLPage(link);
			} catch (PathDisallowedException e) {
				System.out.println("Robots excluded from access to" + link
						+ ": " + e);
				continue;
			}
			if (currentPage.empty()) {
				System.out.println("No Page Found");
				continue;
			}
			if (currentPage.indexAllowed()) {
				count++;
				System.out.println("Indexing" + "(" + count + "): " + link);
				processPage(currentPage);
				pageMap.put(currentPage.getLink(), "P" +  MoreString.padWithZeros(count,(int)Math.floor(MoreMath.log(maxCount, 10)) + 1) + ".html");
				pages.add(currentPage);
				
			}
			if (count < maxCount) {
				List newLinks = getNewLinks(currentPage);
				// System.out.println("Adding the following links" + newLinks);
				// Add new links to end of queue
				linksToVisit.addAll(newLinks);
			}
			
		}
		
		pageGraph = new Graph();
		for (HTMLPage page : pages){
			for (Object l : page.getOutLinks()){
				pageGraph.addEdge(pageMap.get(page.getLink()), pageMap.get(l));
			}
		}
		System.out.println("\nPage graph structure:");
		pageGraph.print();

		PageRankFile pageRankFile = new PageRankFile(saveDir.getAbsolutePath());
		
		System.out.println("\nPage ranks:");
		pageRankFile.calculate(pageGraph, 0.15, 50);
	}

	public static void main(String args[]) {
		new PageRankSpider().go(args);
	}
}
