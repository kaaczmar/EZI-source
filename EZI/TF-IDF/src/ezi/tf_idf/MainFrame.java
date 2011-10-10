package ezi.tf_idf;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ezi.tf_idf.algorithm.IDF;
import ezi.tf_idf.data.Document;
import ezi.tf_idf.data.Keyword;
import ezi.tf_idf.data.Query;
import ezi.tf_idf.utils.DocumentFileParser;
import ezi.tf_idf.utils.KeywordFileParser;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 4320302161936573161L;

	private ArrayList<Keyword> keywords;
	private ArrayList<Document> documents;
	private IDF idf;

	private JPanel contentPane;

	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem mntmOpenDocumentsFile;
	private JMenuItem mntmOpen;

	private JFileChooser fileChooser;
	private JTextField textFieldQuery;
	private JButton btnSearch;
	private JMenu mnView;
	private JMenuItem mntmDocuments;
	private JMenuItem mntmKeywords;

	private DocumentPresentationDialog documentPresentationDialog;
	private KeywordPresentationDialog keywordPresentationDialog;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setResizable(false);
		setTitle("CJ Search Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);

		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));

		keywordPresentationDialog = new KeywordPresentationDialog();
		documentPresentationDialog = new DocumentPresentationDialog();

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuFile = new JMenu("File");
		menuBar.add(menuFile);

		mntmOpenDocumentsFile = new JMenuItem("Open documents file");
		mntmOpenDocumentsFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadDocuments();
			}
		});
		menuFile.add(mntmOpenDocumentsFile);

		mntmOpen = new JMenuItem("Open keywords file");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadKeywords();
			}
		});
		menuFile.add(mntmOpen);

		mnView = new JMenu("View");
		menuBar.add(mnView);

		mntmDocuments = new JMenuItem("Documents");
		mntmDocuments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showStemmedDocuments();
			}
		});
		mnView.add(mntmDocuments);

		mntmKeywords = new JMenuItem("Keywords");
		mntmKeywords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showOriginalKeywords();
			}
		});
		mnView.add(mntmKeywords);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldQuery = new JTextField();
		textFieldQuery.setFont(new Font("Dialog", Font.PLAIN, 16));
		textFieldQuery
				.setText("<---load documents and keywords files first--->");
		textFieldQuery.setEnabled(false);
		textFieldQuery.setBounds(12, 12, 774, 25);
		contentPane.add(textFieldQuery);
		textFieldQuery.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.setBounds(669, 43, 117, 25);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				computeTFIDF();
			}
		});
		contentPane.add(btnSearch);

	}

	private void computeTFIDF() {
		Query query = new Query(textFieldQuery.getText(), keywords, idf);
		for (Document document : documents) {
			// document.calculateTFSimiliarity(query);
			// System.out.println(document.getTitle() + " TF: " +
			// document.getSimiliarity());
			document.calculateTFIDFSimiliarity(query);
		}
		Collections.sort(documents);
		// check documents list
		for (Document document : documents) {
			System.out.println(document.getTitle() + " TFIDF: "
					+ document.getSimiliarity());
		}
	}

	private void loadDocuments() {
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			documents = DocumentFileParser.parse(fileChooser.getSelectedFile()
					.getAbsolutePath());
			documentPresentationDialog.update(documents);

			if (keywords != null) {
				idf = new IDF(documents, keywords);

				for (Document document : documents) {
					document.applyKeywordSet(keywords);
					document.applyIDF(idf);
				}

				textFieldQuery.setText("<---type your query here--->");
				textFieldQuery.setEnabled(true);
			} else {
				textFieldQuery.setText("<---load keywords file first--->");
			}
		}
	}

	private void loadKeywords() {
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			keywords = KeywordFileParser.parse(fileChooser.getSelectedFile()
					.getAbsolutePath());
			keywordPresentationDialog.update(keywords);

			if (documents != null) {
				idf = new IDF(documents, keywords);

				for (Document document : documents) {
					document.applyKeywordSet(keywords);
					document.applyIDF(idf);
				}

				textFieldQuery.setText("<---type your query here--->");
				textFieldQuery.setEnabled(true);
			} else {
				textFieldQuery.setText("<---load documents file first--->");
			}
		}
	}

	private void showStemmedDocuments() {
		documentPresentationDialog.setVisible(true);
	}

	private void showOriginalKeywords() {
		keywordPresentationDialog.setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
