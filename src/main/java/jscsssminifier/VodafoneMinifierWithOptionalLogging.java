package jscsssminifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.Result;
import com.google.javascript.jscomp.SourceFile;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.yahoo.platform.yui.compressor.CssCompressor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class VodafoneMinifierWithOptionalLogging {
	private static JFrame frame_1;

	public static String x = "1";
	private static JTextField txtdev;
	private static JTextField txtMinify;
	private static JTextField CommitMessage;
	private static Boolean beaut = false;

	public static String compileCSS(String csscode) throws Exception {

		InputStream in = org.apache.commons.io.IOUtils.toInputStream(csscode, StandardCharsets.UTF_8);
		StringWriter writer = new StringWriter();

		CssCompressor compressor = new CssCompressor(new InputStreamReader(in));
		compressor.compress(writer, -1);
		return writer.toString();
	}

	public static String compile(String code, String inputFileName) throws Exception {

		Compiler compiler = new Compiler();

		CompilerOptions options = new CompilerOptions();
		options.setPrettyPrint(beaut);
		options.setEmitUseStrict(false);
		options.setOutputCharset(StandardCharsets.UTF_8);
		options.setTrustedStrings(true);

		if (x == "1") {
			CompilationLevel.WHITESPACE_ONLY.setOptionsForCompilationLevel(options);
		} else if (x == "2") {
			CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
		} else if (x == "3") {
			CompilationLevel.ADVANCED_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
		}

		SourceFile extern = SourceFile.fromCode("externs.js", "function alert(x) {}");
		SourceFile input = SourceFile.fromCode(inputFileName, code);
		Result result = compiler.compile(extern, input, options);
		if (result.success) {
			return compiler.toSource();
		} else {
			throw new Exception(compiler.getErrorManager().getErrors().toString());
			// throw new Exception(result.errors.toString());
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				} else if ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel".equals(info.getClassName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		createWindow();
	}

	private static void createWindow() {

		frame_1 = new JFrame("JAVASCRIPT & CSS MINIFIER");
		frame_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createUI(frame_1);
		frame_1.setSize(510, 510);
		frame_1.setLocationRelativeTo(null);
		frame_1.setVisible(true);
	}

	private static void createUI(final JFrame frame) {
		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		frame_1.getContentPane().setLayout(new BorderLayout(0, 0));

		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame_1.getContentPane().add(tabbedPane);

		JPanel Options = new JPanel();
		tabbedPane.addTab("Options", null, Options, null);

		JButton button1 = new JButton("select file to compile::");
		button1.setFont(new Font("Segoe UI", Font.BOLD, 10));
		button1.setToolTipText("select file to compile");
		button1.setBackground(Color.GREEN);

		JLabel lblNewLabel = new JLabel("Default input File ending::");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
		lblNewLabel.setToolTipText("Default input File ending");

		txtdev = new JTextField();
		txtdev.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		txtdev.setText("_dev");
		txtdev.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Output Folder Name::");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 10));
		lblNewLabel_1.setToolTipText("Output Folder Name");

		txtMinify = new JTextField();
		txtMinify.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		txtMinify.setText("custom");
		txtMinify.setColumns(10);
		final JRadioButton acvancedOpti = new JRadioButton("ADVANCED");
		acvancedOpti.setFont(new Font("Segoe UI", Font.BOLD, 10));
		acvancedOpti.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				x = "3";
			}
		});
		acvancedOpti.setEnabled(true);
		group.add(acvancedOpti);

		JButton button = new JButton("select input folder to compile:");
		button.setFont(new Font("Segoe UI", Font.BOLD, 10));

		button.setBackground(Color.GREEN);
		final JTextField button2 = new JTextField("output path here");
		button2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));

		final JRadioButton simpleOpti = new JRadioButton("SIMPLE");
		simpleOpti.setFont(new Font("Segoe UI", Font.BOLD, 10));
		simpleOpti.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				x = "2";
			}
		});
		simpleOpti.setEnabled(true);
		group.add(simpleOpti);

		final JRadioButton whiteSpace = new JRadioButton("WHITESPACE", true);
		whiteSpace.setFont(new Font("Segoe UI", Font.BOLD, 10));
		whiteSpace.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				x = "1";
			}
		});
		group.add(whiteSpace);

		JLabel lblNewLabel_3 = new JLabel("Output Path Below::");
		lblNewLabel_3.setFont(new Font("Segoe UI", Font.BOLD, 10));

		JLabel lblGitCommitMessage = new JLabel("GIT Commit Message (CR)::");
		lblGitCommitMessage.setFont(new Font("Segoe UI", Font.BOLD, 10));

		CommitMessage = new JTextField();
		CommitMessage.setColumns(10);
		CommitMessage.setEnabled(false);

		JLabel lblBeautify = new JLabel("Beautify:: ");
		lblBeautify.setFont(new Font("Segoe UI", Font.BOLD, 10));

		final JCheckBox isBeautify = new JCheckBox("");
		isBeautify.setSelected(beaut);
		isBeautify.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (isBeautify.isSelected() == true) {
					beaut = true;
				} else {
					beaut = false;
				}

			}
		});

		final JCheckBox toLogcheckbox = new JCheckBox("");
		toLogcheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (toLogcheckbox.isSelected() == true) {
					CommitMessage.setEnabled(true);
				} else {
					CommitMessage.setEnabled(false);
					CommitMessage.setText("");
				}

			}
		});

		JLabel lblNewLabel_2 = new JLabel("Log::");
		lblNewLabel_2.setFont(new Font("Segoe UI", Font.BOLD, 10));
		GroupLayout gl_Options = new GroupLayout(Options);
		gl_Options.setHorizontalGroup(gl_Options.createParallelGroup(Alignment.LEADING).addGroup(gl_Options
				.createSequentialGroup().addGap(69)
				.addGroup(gl_Options.createParallelGroup(Alignment.LEADING).addGroup(gl_Options
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_Options.createSequentialGroup().addComponent(whiteSpace).addGap(42)
								.addComponent(simpleOpti).addGap(413))
						.addGroup(gl_Options.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_Options.createSequentialGroup().addComponent(lblNewLabel_3)
										.addContainerGap())
								.addGroup(gl_Options.createSequentialGroup().addGroup(gl_Options
										.createParallelGroup(Alignment.LEADING)
										.addComponent(button2, GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
										.addComponent(lblNewLabel).addComponent(lblNewLabel_1)
										.addGroup(gl_Options.createSequentialGroup().addComponent(button).addGap(31)
												.addGroup(gl_Options.createParallelGroup(Alignment.LEADING)
														.addComponent(button1, GroupLayout.DEFAULT_SIZE, 145,
																Short.MAX_VALUE)
														.addComponent(txtMinify, GroupLayout.DEFAULT_SIZE, 145,
																Short.MAX_VALUE)
														.addComponent(txtdev, GroupLayout.DEFAULT_SIZE, 145,
																Short.MAX_VALUE)
														.addComponent(CommitMessage, GroupLayout.DEFAULT_SIZE, 145,
																Short.MAX_VALUE)
														.addComponent(acvancedOpti, Alignment.TRAILING))))
										.addGap(267))
								.addGroup(Alignment.TRAILING,
										gl_Options.createSequentialGroup()
												.addComponent(lblGitCommitMessage, GroupLayout.DEFAULT_SIZE, 602,
														Short.MAX_VALUE)
												.addContainerGap())))
						.addGroup(
								gl_Options.createSequentialGroup()
										.addGroup(gl_Options.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblBeautify).addComponent(lblNewLabel_2,
														GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_Options.createParallelGroup(Alignment.LEADING)
												.addComponent(isBeautify).addComponent(toLogcheckbox,
														GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
										.addGap(469)))));
		gl_Options.setVerticalGroup(gl_Options.createParallelGroup(Alignment.LEADING).addGroup(gl_Options
				.createSequentialGroup().addGap(93)
				.addGroup(gl_Options.createParallelGroup(Alignment.BASELINE).addComponent(button).addComponent(button1))
				.addGap(18)
				.addGroup(gl_Options.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(
						txtdev, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_Options.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1).addComponent(
						txtMinify, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_Options.createParallelGroup(Alignment.BASELINE).addComponent(lblGitCommitMessage)
						.addComponent(CommitMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_Options
						.createParallelGroup(Alignment.LEADING).addComponent(isBeautify).addComponent(lblBeautify))
				.addGap(2)
				.addGroup(gl_Options.createParallelGroup(Alignment.TRAILING).addComponent(lblNewLabel_2)
						.addComponent(toLogcheckbox))
				.addGap(16)
				.addGroup(gl_Options.createParallelGroup(Alignment.BASELINE).addComponent(whiteSpace)
						.addComponent(simpleOpti).addComponent(acvancedOpti))
				.addGap(18).addComponent(lblNewLabel_3).addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(button2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(62, Short.MAX_VALUE)));
		Options.setLayout(gl_Options);

		JPanel output = new JPanel();
		tabbedPane.addTab("output", null, output, null);

		JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		final JTextArea txt = new JTextArea();
		txt.setFont(new Font("Cascadia Mono", Font.PLAIN, 14));
		txt.setEnabled(true);
		txt.setEditable(false);
		scroll.setViewportView(txt);
		txt.setWrapStyleWord(true);
		txt.setLineWrap(true);
		txt.setVisible(true);
		output.setLayout(new BorderLayout(0, 0));
		txt.setSize(400, 400);
		txt.setText("errors and minification text messages appear here..\n");
		output.add(scroll);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
				txt.setText("");
				String currPath = new File("").getAbsolutePath();
				// JFileChooser fileChooser = new
				// JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				JFileChooser fileChooser = new JFileChooser(currPath);
				//open file explorer in details mode
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					if ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel".equals(info.getClassName())) {
						Action details = fileChooser.getActionMap().get("viewTypeDetails");
						details.actionPerformed(null);
					}
				}
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int option = fileChooser.showOpenDialog(frame);
				if (option == JFileChooser.APPROVE_OPTION) {
					File[] filesInDirectory = fileChooser.getCurrentDirectory().listFiles();
					File directory = new File(fileChooser.getCurrentDirectory().toString());

					String fileEnding = txtdev.getText();
					for (File file : filesInDirectory) {
						String outfilename = "";
						if (file.getName().toUpperCase().contains(fileEnding.toUpperCase())) {
							String fileExt = FilenameUtils.getExtension(file.getName()).toUpperCase();
							String ext1 = FilenameUtils.getExtension(file.getName());
							if (!fileEnding.isEmpty()) {
								int indx = file.getName().indexOf(fileEnding);
								outfilename = file.getName().substring(0, indx) + "." + ext1;
							} else {
								outfilename = file.getName();
							}
							String content2 = "", compiledCode12 = "", outfoldr = "";
							try {
								content2 = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
								if (fileExt.equals("JS")) {
									compiledCode12 = compile(content2, file.getName());
								} else if (fileExt.equals("CSS")) {
									compiledCode12 = compileCSS(content2);
								}

								outfoldr = directory.getParent().toString();
								outfoldr += File.separator + txtMinify.getText();
								File dirFile = new File(outfoldr);
								dirFile.mkdir();
								button2.setText(outfoldr);
								FileUtils.writeStringToFile(new File(outfoldr + File.separator + outfilename),
										compiledCode12, StandardCharsets.UTF_8);
								txt.append("File :: '" + outfilename + "' minified. \n");
							} catch (Exception e1) {
								txt.append("\n\n error in file '" + file.getName() + "' ::\n" + e1.toString());
								try {
									FileUtils.writeStringToFile(new File(outfoldr + File.separator + outfilename),
											compiledCode12, StandardCharsets.UTF_8);
								} catch (IOException e2) {
									e2.printStackTrace();
								}
							}
						}

					}
					if (toLogcheckbox.isSelected()) {
						try {
							Date date = Calendar.getInstance().getTime();
							FileUtils.writeStringToFile(
									new File(
											fileChooser.getCurrentDirectory().getParent() + File.separator + "log.txt"),
									"\nCommit Message:: " + CommitMessage.getText() + "\nLog Date:: " + date.toString()
											+ "\nLOG BELOW::\n" + txt.getText(),
									StandardCharsets.UTF_8, true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					JOptionPane.showMessageDialog(null, "operation ended.");
				} else {
					txt.append("\n this Folder cannot be accessed.\n");
				}
			}
		});

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
				txt.setText("");
				String currPath = new File("").getAbsolutePath();
				// JFileChooser fileChooser = new
				// JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				JFileChooser fileChooser = new JFileChooser(currPath);
				//open file explorer in details mode
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					if ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel".equals(info.getClassName())) {
						Action details = fileChooser.getActionMap().get("viewTypeDetails");
						details.actionPerformed(null);
					}
				}
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChooser.setMultiSelectionEnabled(true);
				
				fileChooser.setDialogTitle("Select CSS or JS file to minify.");
				int option = fileChooser.showOpenDialog(frame);
				if (option == JFileChooser.APPROVE_OPTION) {
					File[] filesInDirectory = fileChooser.getSelectedFiles();
					// button1.setText(fileChooser.getCurrentDirectory().toString());
					File directory = new File(fileChooser.getCurrentDirectory().toString());

					String fileEnding = txtdev.getText();
					for (File file : filesInDirectory) {
						String outfilename = "";
						if (file.getName().toUpperCase().contains(fileEnding.toUpperCase())) {
							String fileExt = FilenameUtils.getExtension(file.getName()).toUpperCase();
							String ext1 = FilenameUtils.getExtension(file.getName());
							if (!fileEnding.isEmpty()) {
								int indx = file.getName().indexOf(fileEnding);
								outfilename = file.getName().substring(0, indx) + "." + ext1;
							} else {
								outfilename = file.getName();
							}
							String content2 = "", compiledCode12 = "", outfoldr = "";
							try {
								content2 = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
								// compiledCode12 = compile(content2);
								if (fileExt.equals("JS")) {
									compiledCode12 = compile(content2, file.getName());
								} else if (fileExt.equals("CSS")) {
									compiledCode12 = compileCSS(content2);
								}
								outfoldr = directory.getParent().toString();
								outfoldr += File.separator + txtMinify.getText();
								File dirFile = new File(outfoldr);
								dirFile.mkdir();
								button2.setText(outfoldr);
								FileUtils.writeStringToFile(new File(outfoldr + File.separator + outfilename),
										compiledCode12, StandardCharsets.UTF_8);
								txt.append("File :: '" + outfilename + "' minified. \n");
							} catch (Exception e1) {
								txt.append("\n\n error in file '" + file.getName() + "' ::\n" + e1.toString());
								try {
									FileUtils.writeStringToFile(new File(outfoldr + File.separator + outfilename),
											compiledCode12, StandardCharsets.UTF_8);
								} catch (IOException e2) {
									e2.printStackTrace();
								}
							}
						}

					}
					if (toLogcheckbox.isSelected()) {
						try {
							Date date = Calendar.getInstance().getTime();
							FileUtils.writeStringToFile(
									new File(
											fileChooser.getCurrentDirectory().getParent() + File.separator + "log.txt"),
									"\nCommit Message:: " + CommitMessage.getText() + "\nLog Date:: " + date.toString()
											+ "\nLOG BELOW::\n" + txt.getText(),
									StandardCharsets.UTF_8, true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(null, "operation ended.");
				} else {
					txt.append("\n this Folder cannot be accessed.\n");
				}
			}
		});
	}
}