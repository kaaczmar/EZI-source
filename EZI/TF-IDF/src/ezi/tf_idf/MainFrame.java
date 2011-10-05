package ezi.tf_idf;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.io.File;

import javax.swing.JSeparator;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 4320302161936573161L;

	private JPanel contentPane;

	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem mntmOpenDocumentsFile;
	private JMenuItem mntmOpen;
	
	private JFileChooser fileChooser;
	private JTextField textFieldQuery;
	private JButton btnSearch;
	private JMenu mnView;
	private JMenuItem mntmOriginalDocuments;
	private JMenuItem mntmStemmedDocuments;
	private JMenuItem mntmOriginalKeywords;
	private JMenuItem mntmStemmedKeywords;
	private JSeparator separator;

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
				
			}
		});
		menuFile.add(mntmOpen);
		
		mnView = new JMenu("View");
		menuBar.add(mnView);
		
		mntmOriginalDocuments = new JMenuItem("Original documents");
		mnView.add(mntmOriginalDocuments);
		
		mntmStemmedDocuments = new JMenuItem("Stemmed documents");
		mnView.add(mntmStemmedDocuments);
		
		separator = new JSeparator();
		mnView.add(separator);
		
		mntmOriginalKeywords = new JMenuItem("Original keywords");
		mnView.add(mntmOriginalKeywords);
		
		mntmStemmedKeywords = new JMenuItem("Stemmed keywords");
		mnView.add(mntmStemmedKeywords);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldQuery = new JTextField();
		textFieldQuery.setFont(new Font("Dialog", Font.PLAIN, 16));
		textFieldQuery.setText("<---load documents and keywords files first--->");
		textFieldQuery.setEnabled(false);
		textFieldQuery.setBounds(12, 12, 774, 25);
		contentPane.add(textFieldQuery);
		textFieldQuery.setColumns(10);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(669, 43, 117, 25);
		contentPane.add(btnSearch);
	}
	
	private void loadDocuments(){
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			DocumentFileParser.parse(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//	Document doc = new Document("UCI Machine Learning Repository",
//"Welcome to the UCI Machine Learning Repository! ... The majority of the entries in the "+
//"repository were contributed by machine learning researchers outside of UCI. ..."+ 
//"Description: A repository of databases, domain theories and data generators that are used by the machine learning...");
//		System.out.println(doc.getStemmedDocument());
//		
//		Term term = new Term("international");
//		System.out.println(term.getStemmedTerm());
//		
//		return;
	
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
