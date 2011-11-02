package ezi.tf_idf;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import ezi.tf_idf.algorithm.IDF;
import ezi.tf_idf.data.CustomListCellRenderers;
import ezi.tf_idf.data.Document;
import ezi.tf_idf.data.Keyword;
import ezi.tf_idf.data.Query;
import ezi.tf_idf.utils.DocumentFileParser;
import ezi.tf_idf.utils.KeywordFileParser;
import ezi.tf_idf.utils.WordnetAPI;

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
	private JLabel extendedQueryInformation;
	private JButton btnSearch;
	private JMenu mnView;
	private JMenuItem mntmDocuments;
	private JMenuItem mntmKeywords;

	private DocumentPresentationDialog documentPresentationDialog;
	private KeywordPresentationDialog keywordPresentationDialog;
	private SingleDocumentPresentationDialog singleDocumentPresentationDialog;

	private JList list;
	private JScrollPane scrollPane;
	private JButton expandButton;

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
		singleDocumentPresentationDialog = new SingleDocumentPresentationDialog();

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

		expandButton = new JButton("Expand query");
		expandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				expandQuery();
			}
		});
		expandButton.setEnabled(false);
		expandButton.setBounds(511, 43, 130, 25);
		contentPane.add(expandButton);

		extendedQueryInformation = new JLabel();
		extendedQueryInformation.setText("The query was expanded");
		extendedQueryInformation.setForeground(Color.RED);
		extendedQueryInformation.setFont(new Font("Dialog", Font.PLAIN, 16));
		extendedQueryInformation.setBounds(12, 43, 400, 25);
		extendedQueryInformation.setVisible(false);
		contentPane.add(extendedQueryInformation);
		
		textFieldQuery = new JTextField();
		textFieldQuery.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					computeTFIDF();
				if (textFieldQuery.isEnabled()
						&& textFieldQuery.getText().length() > 0)
					expandButton.setEnabled(true);
				else
					expandButton.setEnabled(false);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (textFieldQuery.isEnabled()
						&& textFieldQuery.getText().length() > 0)
					expandButton.setEnabled(true);
				else
					expandButton.setEnabled(false);
			}

		});
		textFieldQuery.setFont(new Font("Dialog", Font.PLAIN, 16));
		textFieldQuery
				.setText("<---load documents and keywords files first--->");
		textFieldQuery.setEnabled(false);
		textFieldQuery.setBounds(12, 12, 774, 25);
		contentPane.add(textFieldQuery);
		textFieldQuery.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.setEnabled(false);
		btnSearch.setBounds(669, 43, 117, 25);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				computeTFIDF();
			}
		});
		contentPane.add(btnSearch);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(12, 82, 774, 455);
		contentPane.add(scrollPane);

		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new CustomListCellRenderers.ResultsCellRenderer());
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					singleDocumentPresentationDialog
							.displayDocument((Document) list.getSelectedValue());
					singleDocumentPresentationDialog.setVisible(true);
				}

			}
		});
		scrollPane.setViewportView(list);

	}

	private void computeTFIDF() {
		extendedQueryInformation.setVisible(false);
		Query query = new Query(textFieldQuery.getText(), keywords, idf);

		if (!query.getIsQueryValid()) {
			JOptionPane
					.showMessageDialog(
							this,
							"Ups! Your query is not compatible with keywords database, sorry...",
							"Query invalid", JOptionPane.ERROR_MESSAGE);
		}

		for (Document document : documents) {
			// document.calculateTFSimiliarity(query);
			// System.out.println(document.getTitle() + " TF: " +
			// document.getSimiliarity());
			document.calculateTFIDFSimiliarity(query);
		}
		Collections.sort(documents);
		// check documents list
		// for (Document document : documents)
		// {
		// System.out.println(document.getTitle() + " TFIDF: " +
		// document.getSimiliarity());
		// }

		showResults();
	}

	private void loadDocuments() {
		fileChooser.setSelectedFile(new File(""));
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
				btnSearch.setEnabled(true);
			} else {
				textFieldQuery.setText("<---load keywords file first--->");
			}
		}
	}

	private void loadKeywords() {
		fileChooser.setSelectedFile(new File(""));
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
				btnSearch.setEnabled(true);
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

	private void showResults() {
		// list = new JList(documents.toArray());
		list.setListData(documents.toArray());
		scrollPane.setVisible(true);
	}

	private void expandQuery() {
		WordnetAPI wordnet = new WordnetAPI();
		ArrayList<String> synonims = new ArrayList<String>();

		ArrayList<String> query = new ArrayList<String>();
		
		StringTokenizer st = new StringTokenizer(textFieldQuery.getText());
		while (st.hasMoreTokens()) {
			query.add(st.nextToken());
		}
		
		for (String word : query){
			wordnet.findSynonims(word, synonims, keywords, query);
		}
		
		for (String s : synonims)
			System.out.println(s);
		
		QueryExpansionDialog dialog = new QueryExpansionDialog(this);
		String expandedQueryString = textFieldQuery.getText();
		for (String s : synonims){
			expandedQueryString += " " + s;
		}
		
		dialog.initTable(expandedQueryString, idf, keywords);
		
		dialog.setVisible(true);
		
		if (dialog.getResult()){
			System.out.println("Search");
			for (Document document : documents) {
				// document.calculateTFSimiliarity(query);
				// System.out.println(document.getTitle() + " TF: " +
				// document.getSimiliarity());
				document.calculateTFIDFSimiliarity(dialog.getQuery());
			}
			Collections.sort(documents);
			showResults();
			textFieldQuery.setText(dialog.getQuery().getContent());
			extendedQueryInformation.setVisible(true);
		} else {
			System.out.println("Cancel");
		}
		
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
