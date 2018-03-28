package GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
//import java.text.DecimalFormat;

import javax.swing.*;

//import GUI.*;
import GUI.Formatter.*;
import GUI.Justifier;
import GUI.RJustifier;

//***************************************************************
//The formatter example is at line 109 to end of function
//***************************************************************
/**
 * Adds the UI control to select the file and format it.
 * 
 * @author SUHAS VENKATESH MURTHY VERSION 1.0 DATE 03/02/2018
 */
public class MainWindow implements ActionListener {

	private JFrame mainFrame;
	JTabbedPane tabbedPane = getTabbedPane();
	private JTextField inputFileNameTextField;
	private JTextField lineLengthTextField;
	private JTextField outputFileNameTextField;
	private JTextField averageLineTextField;
	private JTextField averageWordsTextField;
	private JTextField blankLinetextField;
	private JTextField lineCountTextField;
	private JTextField wordCountTextField;
	private JTextField addedSpacesTextField;
	private JRadioButton leftJustificationRadioButton;
	private JRadioButton rightJustificationradioButton;
	private JRadioButton fullJustificationradioButton;
	private JRadioButton singleSpaceRadioButton;
	private JRadioButton doubleSpaceRadioButton;
	private ButtonGroup group;
	private ButtonGroup group2;

	private static String inputFilePlaceHolder = "Enter File  Location and Name - Input File - .txt only";
	private static String outputFilePlaceHolder = "Enter File  Location and Name - Output File - .txt only";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addMainFrame();

		mainFrame.getContentPane().add(tabbedPane);

		JPanel fileSelectionPanel = getFileSelectionPanel();
		tabbedPane.addTab("File Selection", null, fileSelectionPanel, null);

		group = new ButtonGroup();
		group2 = new ButtonGroup();

		JPanel inptFileSelectionPanel = getInputFileSelectionPanel();
		fileSelectionPanel.add(inptFileSelectionPanel);

		JButton inputFileBrowseButton = getInputFileBrowseButton(tabbedPane);
		inptFileSelectionPanel.add(inputFileBrowseButton);

		getInputFileNameTextField();
		inptFileSelectionPanel.add(inputFileNameTextField);

		JPanel outputFileSelectionPanel = getOutputFileSelectionPanel();
		fileSelectionPanel.add(outputFileSelectionPanel);

		JButton outputFileBrowseButton = getOutputFIleBrowseButton(tabbedPane);
		outputFileSelectionPanel.add(outputFileBrowseButton);
		
		JPanel lineLengthPanel = getLineLengthPanel();
		fileSelectionPanel.add(lineLengthPanel);

		getOutputFileNameTextField();
		outputFileSelectionPanel.add(outputFileNameTextField);

		leftJustificationRadioButton = getLeftJustifyRdioButton();
		group.add(leftJustificationRadioButton);
		fileSelectionPanel.add(leftJustificationRadioButton);

		rightJustificationradioButton = getRightJustifyRadioButton();
		group.add(rightJustificationradioButton);
		fileSelectionPanel.add(rightJustificationradioButton);
		
		fullJustificationradioButton = getFullJustifyRdioButton(); //getRightJustifyRadioButton();
		group.add(fullJustificationradioButton);
		fileSelectionPanel.add(fullJustificationradioButton);
		
		singleSpaceRadioButton = getSingleSpaceRadioButton();
		group2.add(singleSpaceRadioButton);
		fileSelectionPanel.add(singleSpaceRadioButton);
		
		doubleSpaceRadioButton = getDoubleSpaceRadioButton();
		group2.add(doubleSpaceRadioButton);
		fileSelectionPanel.add(doubleSpaceRadioButton);

		JButton btnFormat = getFormatButton();
		btnFormat.addActionListener(this);
		fileSelectionPanel.add(btnFormat);

		JPanel outputStatsPanel = getOutputStatusPanel();
		tabbedPane.addTab("Output Stats", null, outputStatsPanel, null);

		JPanel averageLineLengthPanel = getAverageLineLengthPanel();
		outputStatsPanel.add(averageLineLengthPanel);

		JPanel averageWordspanel = getAverageWordsPanel();
		outputStatsPanel.add(averageWordspanel);

		JPanel blankLineRemovalPanel = getBlankLineRemovalPanel();
		outputStatsPanel.add(blankLineRemovalPanel);

		JPanel lineCountPanel = getLineCountPanel();
		outputStatsPanel.add(lineCountPanel);

		JPanel wordCountPanel = getWordCountPanel();
		outputStatsPanel.add(wordCountPanel);
		
		JPanel addedSpacesPanel = getAddedSpacesPanel();
		outputStatsPanel.add(addedSpacesPanel);
		
		//RJustifier.fullJustified("C:\\Users\\theri\\eclipse-workspace\\CSE360_Project\\testInput.txt", "C:\\Users\\theri\\eclipse-workspace\\CSE360_Project\\testOutput.txt", 80, true);
		//RJustifier.rightJustified("C:\\Users\\theri\\eclipse-workspace\\CSE360_Project\\testInput.txt", "C:\\Users\\theri\\eclipse-workspace\\CSE360_Project\\testOutput1.txt", 80, true);
		//Justifier.leftJustified("C:\\Users\\theri\\eclipse-workspace\\CSE360_Project\\testInput.txt", "C:\\Users\\theri\\eclipse-workspace\\CSE360_Project\\testOutput2.txt", 80, true);
		
		
		/*
		 * frame.setIconImage(icon);
		 */
	}

	// Format button function -- rightJustified still needs to be completed.
	public void actionPerformed(ActionEvent e) {
		String inputName = inputFileNameTextField.getText();
		String outputName = outputFileNameTextField.getText();
		File inputFile = new File(inputName);
		File outputFile = new File(outputName);
		String ext1 = GetExtension(inputFile);
		String ext2 = GetExtension(outputFile);

		if (inputName.equalsIgnoreCase(inputFilePlaceHolder) || inputName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please provide an input file name.", "I/O error ",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		else if (outputName.equalsIgnoreCase(outputFilePlaceHolder) || outputName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please provide an output file name.", "I/O error ",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		else if (inputName.equalsIgnoreCase(outputName)) {
			JOptionPane.showMessageDialog(null,
					"Error - Output file must be different than input file name.\nPlease choose a different name.",
					"Error", JOptionPane.WARNING_MESSAGE);
			// throw error message (input file cannot be output file)
			return;
			// returns after message pops up so file doesn't get formatted
		} else if (!ext1.equalsIgnoreCase("txt") || !ext2.equalsIgnoreCase("txt")) {
			JOptionPane.showMessageDialog(null, "File is not .txt\nPlease Correct", "Error",
					JOptionPane.WARNING_MESSAGE);
			// throw error message (not .txt)
			return;
			// returns after message pops up so file doesn't get formatted
		} else if (new File(outputName).isFile()) {
			// check if okay to overwrite
			int dialogResult = 1;
			dialogResult = JOptionPane.showConfirmDialog(null,
					"Caution - Output File already exists. This will overwrite the file.\nAre you sure?", "Alert",
					dialogResult);
			if (dialogResult == JOptionPane.YES_OPTION) {
				// no need to do anything
			} else {
				return;
				// returns when the user doesn't accept (either hits no or cancel)
			}
		}
		if (format()) {

			if (rightJustificationradioButton.isSelected()) 
			{
				FormatterOutput result = RJustifier.rightJustified(inputName, outputName, getLineLengthNum(), doubleSpaceRadioButton.isSelected()); //change 80 later
				SetFields(result);
				// rightJustified function call here
				/*
				 * FormatterOutput result = Justifier.RightJustified(inputName, outputName);
				 * SetFields(result);
				 */
				// Add changing to stats window here or call stats function
			} 
			else if (leftJustificationRadioButton.isSelected())
			{
				FormatterOutput result = Justifier.leftJustified(inputName, outputName, getLineLengthNum(),doubleSpaceRadioButton.isSelected()); //change 80 later
				SetFields(result);
				// Add changing to stats window here or call stats function
			}
			else
			{
				FormatterOutput result = RJustifier.fullJustified(inputName, outputName, getLineLengthNum(), doubleSpaceRadioButton.isSelected());
				SetFields(result);
			}
		} else {
			SetFields(null);
		}
		tabbedPane.setSelectedIndex(1);
	}

	private void SetFields(FormatterOutput result) {
		if (result != null) {
			lineCountTextField.setText(Integer.toString(Statistics.lineCount(result, doubleSpaceRadioButton.isSelected())));
			blankLinetextField.setText(Integer.toString(Statistics.blankLinesRemoved(result)));
			wordCountTextField.setText(Integer.toString(Statistics.wordCount(result)));
			addedSpacesTextField.setText(Integer.toString(Statistics.totalAddedSpaces(result, rightJustificationradioButton.isSelected(), fullJustificationradioButton.isSelected(), getLineLengthNum())));
			averageWordsTextField.setText(String.format("%#.2f", Statistics.averageWordPerLine(result, doubleSpaceRadioButton.isSelected())));
			averageLineTextField.setText(String.format("%#.2f", Statistics.averageLineLength(result, doubleSpaceRadioButton.isSelected())));
		}

		else {
			clear();
		}
	}

	private void clear() {
		lineCountTextField.setText("");
		blankLinetextField.setText("");
		wordCountTextField.setText("");
		averageWordsTextField.setText("");
		averageLineTextField.setText("");
	}

	private boolean format() {

		boolean formatFlag = false;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(outputFileNameTextField.getText(), "utf-8");
			FormatterOutput formatTest = new FormatterOutput();
			formatTest = Formatter.formatInput(inputFileNameTextField.getText(), getLineLengthNum()); //change 80 later

			if (formatTest != null) {
				// writer.println("Blanks Removed: " + formatTest.linesRem);
				// writer.println("Number of Lines: " + formatTest.inputList.size());
				for (int x = 0; x < formatTest.inputList.size(); x++) {
					writer.println(formatTest.inputList.get(x).lineReturn());
				}

				formatFlag = true;
			} else
				formatFlag = false;

		} catch (IOException e1) {
			e1.printStackTrace();

			formatFlag = false;
		}

		finally {
			if (writer != null)
				writer.close();
		}

		return formatFlag;
	}

	private String GetExtension(File file) {
		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}
	
	private int getLineLengthNum()
	{
		int tempNum = Integer.parseInt(lineLengthTextField.getText());
		if (tempNum <= 20)
		{
			return -1;
		}
		else if (tempNum > 100)
		{
			return -1;
		}
		else
		{
			return tempNum;
		}
	}
	
	private JPanel getLineLengthPanel() {
		JPanel lineLengthPanel = new JPanel();
		lineLengthPanel.setBackground(SystemColor.inactiveCaption);
		lineLengthPanel.setBounds(60, 220, 850, 50);
		lineLengthPanel.setLayout(null);

		lineLengthTextField = new JTextField();
		lineLengthTextField.setEditable(true);
		lineLengthTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lineLengthTextField.setColumns(10);
		lineLengthTextField.setBounds(new Rectangle(850 - 80, 5, 75, 40));
		lineLengthTextField.setText("20");
		lineLengthPanel.add(lineLengthTextField);

		JLabel lineLengthLabel = new JLabel("Line Length (20 to 100 characters)");
		lineLengthLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lineLengthLabel.setBounds(15, 5, 400, 40);
		lineLengthPanel.add(lineLengthLabel);
		return lineLengthPanel;
	}

	private JPanel getWordCountPanel() {
		JPanel wordCountPanel = new JPanel();
		wordCountPanel.setBackground(SystemColor.inactiveCaption);
		wordCountPanel.setBounds(50, 373, 600, 60);
		wordCountPanel.setLayout(null);

		wordCountTextField = new JTextField();
		wordCountTextField.setEditable(false);
		wordCountTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		wordCountTextField.setColumns(10);
		wordCountTextField.setBounds(new Rectangle(510, 10, 75, 40));
		wordCountPanel.add(wordCountTextField);

		JLabel wordCountLabel = new JLabel("Word Count");
		wordCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		wordCountLabel.setBounds(15, 10, 300, 40);
		wordCountPanel.add(wordCountLabel);
		return wordCountPanel;
	}

	private JPanel getLineCountPanel() {
		JPanel lineCountPanel = new JPanel();
		lineCountPanel.setBackground(SystemColor.inactiveCaption);
		lineCountPanel.setBounds(50, 297, 600, 60);
		lineCountPanel.setLayout(null);

		lineCountTextField = new JTextField();
		lineCountTextField.setEditable(false);
		lineCountTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lineCountTextField.setColumns(10);
		lineCountTextField.setBounds(new Rectangle(510, 10, 75, 40));
		lineCountPanel.add(lineCountTextField);

		JLabel lineCountLabel = new JLabel("Line Count");
		lineCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lineCountLabel.setBounds(15, 10, 300, 40);
		lineCountPanel.add(lineCountLabel);
		return lineCountPanel;
	}

	private JPanel getBlankLineRemovalPanel() {
		JPanel blankLineRemovalPanel = new JPanel();
		blankLineRemovalPanel.setBackground(SystemColor.inactiveCaption);
		blankLineRemovalPanel.setBounds(50, 218, 600, 60);
		blankLineRemovalPanel.setLayout(null);

		blankLinetextField = new JTextField();
		blankLinetextField.setEditable(false);
		blankLinetextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		blankLinetextField.setColumns(10);
		blankLinetextField.setBounds(new Rectangle(510, 10, 75, 40));
		blankLineRemovalPanel.add(blankLinetextField);

		JLabel blankLineRemovalLabel = new JLabel("Blank Line Removal Count\r\n");
		blankLineRemovalLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		blankLineRemovalLabel.setBounds(15, 10, 300, 40);
		blankLineRemovalPanel.add(blankLineRemovalLabel);

		return blankLineRemovalPanel;
	}

	private JPanel getAverageWordsPanel() {
		JPanel averageWordspanel = new JPanel();
		averageWordspanel.setBackground(SystemColor.inactiveCaption);
		averageWordspanel.setBounds(50, 142, 600, 60);
		averageWordspanel.setLayout(null);

		averageWordsTextField = new JTextField();
		averageWordsTextField.setEditable(false);
		averageWordsTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		averageWordsTextField.setColumns(10);
		averageWordsTextField.setBounds(new Rectangle(510, 10, 75, 40));
		averageWordspanel.add(averageWordsTextField);

		JLabel averageWordsLabel = new JLabel("Average Words Per Line");
		averageWordsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		averageWordsLabel.setBounds(15, 10, 300, 40);
		averageWordspanel.add(averageWordsLabel);
		return averageWordspanel;
	}
	
	private JPanel getAddedSpacesPanel() {
		JPanel addedSpacespanel = new JPanel();
		addedSpacespanel.setBackground(SystemColor.inactiveCaption);
		addedSpacespanel.setBounds(50, 449, 600, 60);
		addedSpacespanel.setLayout(null);

		addedSpacesTextField = new JTextField();
		addedSpacesTextField.setEditable(false);
		addedSpacesTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addedSpacesTextField.setColumns(10);
		addedSpacesTextField.setBounds(new Rectangle(510, 10, 75, 40));
		addedSpacespanel.add(addedSpacesTextField);

		JLabel addedSpacesLabel = new JLabel("Total Added Spaces");
		addedSpacesLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addedSpacesLabel.setBounds(15, 10, 300, 40);
		addedSpacespanel.add(addedSpacesLabel);
		return addedSpacespanel;
	}

	private JPanel getAverageLineLengthPanel() {
		JPanel averageLineLengthPanel = new JPanel();
		averageLineLengthPanel.setBackground(SystemColor.inactiveCaption);
		averageLineLengthPanel.setBounds(50, 66, 600, 60);
		averageLineLengthPanel.setLayout(null);

		averageLineTextField = new JTextField();
		averageLineTextField.setEditable(false);
		averageLineTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		averageLineTextField.setBounds(new Rectangle(510, 10, 75, 40));
		averageLineLengthPanel.add(averageLineTextField);
		averageLineTextField.setColumns(10);

		JLabel averageLineLengthLabel = new JLabel("Average Line Length");
		averageLineLengthLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		averageLineLengthLabel.setBounds(15, 10, 300, 40);
		averageLineLengthPanel.add(averageLineLengthLabel);

		return averageLineLengthPanel;
	}

	private JPanel getOutputStatusPanel() {
		JPanel outputStatsPanel = new JPanel();
		outputStatsPanel.setBackground(Color.WHITE);
		outputStatsPanel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		outputStatsPanel.setForeground(Color.WHITE);
		outputStatsPanel.setLayout(null);
		return outputStatsPanel;
	}

	private JButton getFormatButton() {
		JButton btnFormat = new JButton("Format");
		btnFormat.setBounds(393, 380, 150, 50);
		btnFormat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		return btnFormat;
	}

	private JRadioButton getRightJustifyRadioButton() {
		JRadioButton rightJustificationradioButton = new JRadioButton("Right Justification");
		rightJustificationradioButton.setBackground(Color.WHITE);
		rightJustificationradioButton.setBounds(new Rectangle(0, 0, 8, 9));
		rightJustificationradioButton.setBounds(350, 290, 190, 30);
		rightJustificationradioButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rightJustificationradioButton.setSelected(false);
		return rightJustificationradioButton;
	}

	private JRadioButton getLeftJustifyRdioButton() {
		JRadioButton leftJustificationRadioButton = new JRadioButton("Left Justification");
		leftJustificationRadioButton.setBackground(Color.WHITE);
		leftJustificationRadioButton.setBounds(new Rectangle(0, 0, 8, 9));
		leftJustificationRadioButton.setBounds(60, 290, 190, 30);
		leftJustificationRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		leftJustificationRadioButton.setSelected(true);
		return leftJustificationRadioButton;
	}
	
	private JRadioButton getFullJustifyRdioButton() {
		JRadioButton fullJustificationRadioButton = new JRadioButton("Full Justification");
		fullJustificationRadioButton.setBackground(Color.WHITE);
		fullJustificationRadioButton.setBounds(new Rectangle(0, 0, 8, 9));
		fullJustificationRadioButton.setBounds(700, 290, 190, 30);
		fullJustificationRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fullJustificationRadioButton.setSelected(false);
		return fullJustificationRadioButton;
	}
	
	private JRadioButton getSingleSpaceRadioButton() {
		JRadioButton singleSpaceRadioButton = new JRadioButton("Single Spacing");
		singleSpaceRadioButton.setBackground(Color.WHITE);
		singleSpaceRadioButton.setBounds(new Rectangle(0, 0, 8, 9));
		singleSpaceRadioButton.setBounds(200, 335, 190, 30);
		singleSpaceRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		singleSpaceRadioButton.setSelected(true);
		return singleSpaceRadioButton;
	}
	
	private JRadioButton getDoubleSpaceRadioButton() {
		JRadioButton singleDoubleRadioButton = new JRadioButton("Double Spacing");
		singleDoubleRadioButton.setBackground(Color.WHITE);
		singleDoubleRadioButton.setBounds(new Rectangle(0, 0, 8, 9));
		singleDoubleRadioButton.setBounds(600, 335, 190, 30);
		singleDoubleRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		singleDoubleRadioButton.setSelected(false);
		return singleDoubleRadioButton;
	}

	private void getOutputFileNameTextField() {
		outputFileNameTextField = new JTextField();
		outputFileNameTextField.setForeground(Color.GRAY);
		outputFileNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		outputFileNameTextField.setText(MainWindow.outputFilePlaceHolder);
		outputFileNameTextField.setColumns(10);
		outputFileNameTextField.setBounds(164, 7, 600, 35);
	}

	private JButton getOutputFIleBrowseButton(JTabbedPane tabbedPane) {
		JButton outputFileBrowseButton = new JButton("Browse");
		outputFileBrowseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser outputFileChooser = new JFileChooser();
				int result = outputFileChooser.showOpenDialog(tabbedPane);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedInputFile = outputFileChooser.getSelectedFile();
					outputFileNameTextField.setText(selectedInputFile.getPath());
				}
			}
		});
		outputFileBrowseButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		outputFileBrowseButton.setBounds(new Rectangle(46, 7, 100, 35));
		outputFileBrowseButton.setBackground(Color.WHITE);
		return outputFileBrowseButton;
	}

	private JPanel getOutputFileSelectionPanel() {
		JPanel outputFileSelectionPanel = new JPanel();
		outputFileSelectionPanel.setBackground(SystemColor.inactiveCaption);
		outputFileSelectionPanel.setBounds(60, 145, 850, 50);

		outputFileSelectionPanel.setLayout(null);
		return outputFileSelectionPanel;
	}

	private void getInputFileNameTextField() {
		inputFileNameTextField = new JTextField();
		inputFileNameTextField.setForeground(Color.GRAY);
		inputFileNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		inputFileNameTextField.setBounds(160, 7, 600, 35);
		inputFileNameTextField.setText(MainWindow.inputFilePlaceHolder);
		inputFileNameTextField.setColumns(10);
	}

	private JButton getInputFileBrowseButton(JTabbedPane tabbedPane) {
		JButton inputFileBrowseButton = new JButton("Browse");
		inputFileBrowseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser inputFileChooser = new JFileChooser();
				int result = inputFileChooser.showOpenDialog(tabbedPane);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedInputFile = inputFileChooser.getSelectedFile();
					inputFileNameTextField.setText(selectedInputFile.getPath());
				}
			}
		});
		inputFileBrowseButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		inputFileBrowseButton.setBackground(Color.WHITE);
		inputFileBrowseButton.setBounds(new Rectangle(40, 0, 0, 0));
		inputFileBrowseButton.setBounds(40, 7, 100, 35);
		return inputFileBrowseButton;
	}

	private JPanel getInputFileSelectionPanel() {
		JPanel inptFileSelectionPanel = new JPanel();
		inptFileSelectionPanel.setBackground(SystemColor.inactiveCaption);
		inptFileSelectionPanel.setBounds(60, 50, 850, 50);
		inptFileSelectionPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		inptFileSelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		inptFileSelectionPanel.setLayout(null);
		return inptFileSelectionPanel;
	}

	private JPanel getFileSelectionPanel() {
		JPanel fileSelectionPanel = new JPanel();
		fileSelectionPanel.setBackground(Color.WHITE);
		fileSelectionPanel.setBorder(null);
		fileSelectionPanel.setLayout(null);
		return fileSelectionPanel;
	}

	private JTabbedPane getTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.LIGHT_GRAY);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tabbedPane.setOpaque(true);
		tabbedPane.setBounds(0, 0, 1015, 675);
		tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
			@Override
			protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
				return 50;
			}
		});
		return tabbedPane;
	}

	private void addMainFrame() {
		mainFrame = new JFrame();
		mainFrame.setResizable(true);
		mainFrame.setBounds(100, 100, 1022, 715);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.setLocationRelativeTo(null);
	}
}
