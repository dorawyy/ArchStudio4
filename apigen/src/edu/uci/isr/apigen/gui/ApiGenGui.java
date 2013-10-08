package edu.uci.isr.apigen.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.*;

import javax.swing.*;

import edu.uci.isr.apigen.*;

public class ApiGenGui implements ActionListener{
	
	protected JFrame mainDialog;
	protected JPanel mainPanel;
	protected CardLayout mainPanelLayout;
	
	protected JButton loadSettingsButton;
	protected JButton saveSettingsButton;
	
	protected JButton backButton;
	protected JButton nextButton;
	protected JButton exitButton;
	
	protected JTextField progressLabel;
	protected JProgressBar progressBar;
	
	protected String currentState;
	
	protected static final String BACK_BUTTON_LABEL = "<- Back";
	protected static final String NEXT_BUTTON_LABEL = "Next ->";
	protected static final String DONE_BUTTON_LABEL = "Generate";
	protected static final String EXIT_BUTTON_LABEL = "Exit";
	
	protected SchemaLocationMappingWidget schemaLocationMappingWidget;
	protected SchemaPickerWidget schemaPickerWidget;
	protected DirectoryPickerWidget directoryPickerWidget;
	
	public static void main(String[] args){
		new ApiGenGui();
	}
	
	public ApiGenGui(){
		mainDialog = new JFrame();
		mainDialog.setTitle(ApiGenGui.getBaseTitle());
		mainDialog.getContentPane().setLayout(new BorderLayout());
		
		/*
		String xercesVersion = getXercesVersion();
		if(xercesVersion == null){
			JOptionPane.showMessageDialog(mainPanel, "Please add Xerces 1.2.3 or later to your classpath.", 
				"Error", JOptionPane.ERROR_MESSAGE); 
			System.exit(1);
		}
		*/
		mainPanel = new JPanel();
		mainPanelLayout = new CardLayout();
		mainPanel.setLayout(mainPanelLayout);
		
		mainDialog.getContentPane().add("Center", mainPanel);
		mainDialog.getContentPane().add("South", getButtonPanel());
		
		mainDialog.setSize(700, 400);
		//mainDialog.setResizable(false);
		mainDialog.setLocation(50, 50);
		
		mainPanel.add(getStartPanel(), "start");
		mainPanel.add(getSchemaLocationMappingPanel(), "schemaLocationMapping");
		mainPanel.add(getSchemaPickerPanel(), "schemaPicker");
		mainPanel.add(getDirectoryPickerPanel(), "directoryPicker");
		mainPanel.add(getFinalPanel(), "final");
		
		setState("start");
		mainDialog.setVisible(true);
		mainDialog.repaint();
		//System.out.println("Done?");
	}
	
	protected void setState(String state){
		mainPanelLayout.show(mainPanel, state);
		setupButtonPanel(state);
		currentState = state;
	}
	
	protected Component getStartPanel(){
		JPanel panel = new JPanel();
		BorderLayout mainLayout = new BorderLayout();
		panel.setLayout(mainLayout);
		
		JPanel leftPanel = new JInsetPanel(10);
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add("North", new JLabel(getImageIcon("res/logo.gif")));
		
		JPanel rightPanel = new JInsetPanel(10);
		rightPanel.setLayout(new BorderLayout(20, 20));
		
		//String xercesVersion = getXercesVersion();

		JLabel introLabel = new JLabel(
			"<html>" + 
			"<big><font color=\"#000088\">Welcome to " + ApiGen.PRODUCT_NAME + "!</font></big>" +
			"<br><font size=\"-2\" color=\"#888888\">(" + ApiGen.PRODUCT_VERSION + ")</font>" +
			"<br><br>This program allows you to generate Java APIs for xArch XML Schemas " +
			"or schema extensions.  In the next few steps, you will select which schemas " +
			"for which APIs will be generated, and a destination directory for those APIs. " +
			"Once generated, you can compile the APIs with any Java&nbsp;2<sup>TM</sup> compliant " +
			"Java compiler, and generate documentation for the API using the <code>javadoc</code> " +
			"tool." + 
//			"<br><br>You appear to be running Apache Xerces version " +
//			xercesVersion +
//			".  This generator has been tested with Xerces version 1.2.x," +
//			"and may not work with other versions." +
			"</html>"
		);
		
		rightPanel.add("North", introLabel);
		
		JPanel loadSettingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		loadSettingsButton = new JButton("Load Settings...");
		loadSettingsButton.addActionListener(this);
		loadSettingsPanel.add(loadSettingsButton);
		rightPanel.add("South", loadSettingsPanel);
		
		panel.add("West", leftPanel);
		panel.add("Center", rightPanel);
		return panel;
	}
	
	protected Component getSchemaLocationMappingPanel(){
		JPanel panel = new JPanel();
		BorderLayout mainLayout = new BorderLayout();
		panel.setLayout(mainLayout);
		
		JPanel leftPanel = new JInsetPanel(10);
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add("North", new JLabel(getImageIcon("res/logo.gif")));
		
		JPanel rightPanel = new JInsetPanel(10);
		rightPanel.setLayout(new BorderLayout());
		
		//Box rightPanel = Box.createVerticalBox();
		
		//Box box = Box.createVerticalBox();
		JLabel explainLabel = new JLabel(
			"<html>" + 
			"<b>Schema Mappings</b>" +
			"<font size=-2>" +
			"<br><br>Every XML Schema has a target namespace URI that is unique to that " +
			"schema.  While this may represent a real URL on the Internet where the schema " +
			"definition resides, it may also be an opaque identifier that has nothing to  " +
			"to with its actual location.  Schema Mappings allows you to map a schema's " +
			"unique URI to a real file or URL where the schema definition can be found." + 
			"</font>" +
			"</html>"
			);
		
		rightPanel.add("North", explainLabel);
		schemaLocationMappingWidget = new SchemaLocationMappingWidget();
		rightPanel.add("Center", schemaLocationMappingWidget);
		
		//rightPanel.add("Center", box);
		
		panel.add("West", leftPanel);
		panel.add("Center", rightPanel);
		return panel;
	}
	
	protected Component getSchemaPickerPanel(){
		JPanel panel = new JPanel();
		BorderLayout mainLayout = new BorderLayout();
		panel.setLayout(mainLayout);
		
		JPanel leftPanel = new JInsetPanel(10);
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add("North", new JLabel(getImageIcon("res/logo.gif")));
		
		JPanel rightPanel = new JInsetPanel(10);
		rightPanel.setLayout(new BorderLayout());
		
		//Box rightPanel = Box.createVerticalBox();
		
		//Box box = Box.createVerticalBox();
		JLabel explainLabel = new JLabel(
			"<html>" + 
			"<b>Choose Schemas</b>" +
			"<font size=-2>" +
			"<br><br>Now that you have established where some or all of the schemas to be " +
			"processed are located, it is time to choose which schemas for which you would  " +
			"like to generate APIs.  Add them to the list below by specifying their URIs." +
			"</font>" +
			"</html>"
			);
		
		rightPanel.add("North", explainLabel);
		schemaPickerWidget = new SchemaPickerWidget();
		rightPanel.add("Center", schemaPickerWidget);
		
		//rightPanel.add("Center", box);
		
		panel.add("West", leftPanel);
		panel.add("Center", rightPanel);
		return panel;
	}
	
	protected Component getDirectoryPickerPanel(){
		JPanel panel = new JPanel();
		BorderLayout mainLayout = new BorderLayout();
		panel.setLayout(mainLayout);
		
		JPanel leftPanel = new JInsetPanel(10);
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add("North", new JLabel(getImageIcon("res/logo.gif")));
		
		JPanel rightPanel = new JInsetPanel(10);
		rightPanel.setLayout(new BorderLayout());
		
		//Box rightPanel = Box.createVerticalBox();
		
		//Box box = Box.createVerticalBox();
		JLabel explainLabel = new JLabel(
			"<html>" + 
			"<b>Select Directory</b>" +
			"<font size=-2>" +
			"<br><br>Now you have to choose the directory in which the APIs will be " +
			"generated.  Please select that directory below." +
			"</font>" +
			"</html>"
			);
		
		rightPanel.add("North", explainLabel);
		directoryPickerWidget = new DirectoryPickerWidget();
		rightPanel.add("Center", directoryPickerWidget);
		
		//rightPanel.add("Center", box);
		
		panel.add("West", leftPanel);
		panel.add("Center", rightPanel);
		return panel;
	}
	
	protected Component getFinalPanel(){
		JPanel panel = new JPanel();
		BorderLayout mainLayout = new BorderLayout();
		panel.setLayout(mainLayout);
		
		JPanel leftPanel = new JInsetPanel(10);
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add("North", new JLabel(getImageIcon("res/logo.gif")));
		
		JPanel rightPanel = new JInsetPanel(10);
		rightPanel.setLayout(new BorderLayout(20, 20));
		
		JLabel introLabel = new JLabel(
			"<html>" + 
			"<b>Ready to Generate</b>" +
			"<br><br>You're ready to generate API's now. " +
			"If you need to make any changes, go back and make them. " +
			"Otherwise, click the 'Generate' button at the bottom of this window to " +
			"start the code generator. " +
			"</html>"
			);
		
		rightPanel.add("North", introLabel);
		
		Box progressPanel = Box.createVerticalBox();
		progressLabel = new JTextField(30);
		progressLabel.setText("Status: [Not Started]");
		progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		progressPanel.add(new JLabel("Generator Progress:"));
		JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tempPanel.add(progressLabel);
		progressPanel.add(tempPanel);
		progressPanel.add(progressBar);
		rightPanel.add("Center", progressPanel);
		
		JPanel saveSettingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		saveSettingsButton = new JButton("Save Settings...");
		saveSettingsButton.addActionListener(this);
		saveSettingsPanel.add(saveSettingsButton);
		rightPanel.add("South", saveSettingsPanel);
		
		panel.add("West", leftPanel);
		panel.add("Center", rightPanel);
		return panel;
	}
	
	protected Component getButtonPanel(){
		JPanel panel = new JInsetPanel(5);
		
		backButton = new JButton(BACK_BUTTON_LABEL);
		backButton.addActionListener(this);
		nextButton = new JButton(NEXT_BUTTON_LABEL);
		nextButton.addActionListener(this);
		exitButton = new JButton(EXIT_BUTTON_LABEL);
		exitButton.addActionListener(this);
		
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel.add(backButton);
		panel.add(nextButton);
		panel.add(exitButton);
		return panel;
	}
	
	protected void setupButtonPanel(String state){
		if(state.equals("start")){
			backButton.setEnabled(false);
		}
		else{
			backButton.setEnabled(true);
		}
		if(state.equals("final")){
			nextButton.setText(DONE_BUTTON_LABEL);
		}
		else{
			nextButton.setText(NEXT_BUTTON_LABEL);
		}
	}
	
	public ImageIcon getImageIcon(String resourceName){
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream resourceInputStream = getClass().getResourceAsStream(resourceName);
			if(resourceInputStream == null){
				return null;
			}
			byte buf[] = new byte[2048];
			while(true){
				int len = resourceInputStream.read(buf, 0, buf.length);
				baos.write(buf, 0, len);
				if(len < buf.length){
					resourceInputStream.close();
					baos.close();
					return new ImageIcon(baos.toByteArray());
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getBaseTitle(){
		return ApiGen.PRODUCT_NAME;
	}
	
	public void back(){
		if(currentState.equals("schemaLocationMapping")){
			setState("start");
		}
		else if(currentState.equals("schemaPicker")){
			setState("schemaLocationMapping");
		}
		else if(currentState.equals("directoryPicker")){
			setState("schemaPicker");
		}
		else if(currentState.equals("final")){
			setState("directoryPicker");
		}
	}
	
	public void next(){
		if(currentState.equals("start")){
			setState("schemaLocationMapping");
		}
		else if(currentState.equals("schemaLocationMapping")){
			schemaLocationMappingWidget.commitEdits();
			setState("schemaPicker");
		}
		else if(currentState.equals("schemaPicker")){
			schemaPickerWidget.commitEdits();
			String[] schemaURIs = schemaPickerWidget.getSchemaURIs();
			if(schemaURIs.length == 0){
				JOptionPane.showMessageDialog(mainPanel, "Please enter at least one schema to process.", 
					"Error", JOptionPane.ERROR_MESSAGE); 
				return;
			}
			setState("directoryPicker");
		}
		else if(currentState.equals("directoryPicker")){
			File dir = directoryPickerWidget.getSelectedDirectory();
			if((dir == null) || (!dir.exists()) || (!dir.isDirectory())){
				JOptionPane.showMessageDialog(mainPanel, "Please select a valid directory.", 
					"Error", JOptionPane.ERROR_MESSAGE); 
				return;
			}
			setState("final");
		}
		else if(currentState.equals("final")){
			generateCode();
		}
	}

	/*
	public String getXercesVersion(){
		try{
			Class c = Class.forName("org.apache.xerces.framework.Version");
			Field f = c.getField("fVersion");
			String version = (String)f.get(null);
			return version;
		}
		catch(Exception e){
			return null;
		}
	}
	*/
	
	public void saveSettings(File f) throws IOException{
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(schemaLocationMappingWidget.getSchemaLocationMap());
		oos.writeObject(schemaPickerWidget.getSchemaURIs());
		oos.writeObject(directoryPickerWidget.getSelectedDirectory());
		oos.close();
		fos.close();
	}
	
	public void loadSettings(File f) throws Exception{
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		SchemaLocationMap slm = (SchemaLocationMap)ois.readObject();
		String[] schemaURIs = (String[])ois.readObject();
		File dir = (File)ois.readObject();
		schemaLocationMappingWidget.setSchemaLocationMap(slm);
		schemaPickerWidget.setSchemaURIs(schemaURIs);
		directoryPickerWidget.setSelectedDirectory(dir);
		fis.close();
		ois.close();
	}
	
	public void generateCode(){
		backButton.setEnabled(false);
		backButton.paint(backButton.getGraphics());
		nextButton.setEnabled(false);
		nextButton.paint(nextButton.getGraphics());
		exitButton.setEnabled(false);
		exitButton.paint(exitButton.getGraphics());
		
		SchemaLocationMap slm = schemaLocationMappingWidget.getSchemaLocationMap();
		String[] schemasToProcess = schemaPickerWidget.getSchemaURIs();
		File outputDirectory = directoryPickerWidget.getSelectedDirectory();
		
		progressLabel.setText("Status: Initializing...");
		progressBar.setValue(1);
		
		progressLabel.paint(progressLabel.getGraphics());
		progressBar.paint(progressBar.getGraphics());
		mainPanel.paint(mainPanel.getGraphics());

		GeneratorThread gt = new GeneratorThread(slm, outputDirectory);
		gt.start();
		
		for(int i = 0; i < schemasToProcess.length; i++){
			progressLabel.setText("Status: Processing " + schemasToProcess[i]);		
			progressLabel.paint(progressLabel.getGraphics());
			progressBar.paint(progressBar.getGraphics());
			mainPanel.paint(mainPanel.getGraphics());
			//do processing
			gt.process(schemasToProcess[i]);
			gt.waitUntilDone();
			Exception e = gt.getLastResult();
			if(e != null){
				e.printStackTrace();
				JOptionPane.showMessageDialog(mainPanel, e.toString(),
					"Processing Error", JOptionPane.ERROR_MESSAGE);
				System.exit(2);
			}
			int progressBarValue;
			float pbv = (float)(i+1) / (float)schemasToProcess.length;
			pbv *= 100.0;
			progressBar.setValue((int)pbv);
			progressLabel.paint(progressLabel.getGraphics());
			progressBar.paint(progressBar.getGraphics());
			mainPanel.paint(mainPanel.getGraphics());
		}
		JOptionPane.showMessageDialog(mainPanel, "APIs Generated!",
			"Done!", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	public void actionPerformed(ActionEvent evt){
		//System.out.println("Got actionEvent: " + evt);
		Object src = evt.getSource();
		if(src instanceof JButton){
			JButton srcButton = (JButton)src;
			if(srcButton == exitButton){
				System.exit(0);
			}
			else if(srcButton == backButton){
				back();
			}
			else if(srcButton == nextButton){
				next();
			}
			else if(srcButton == loadSettingsButton){
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = fc.showOpenDialog(mainDialog);
				if(result == JFileChooser.CANCEL_OPTION){
					return;
				}
				
				File f = fc.getSelectedFile();
				if(f != null){
					try{
						loadSettings(f);
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(mainPanel, e.toString(),
							"Load Settings Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
			else if(srcButton == saveSettingsButton){
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = fc.showSaveDialog(mainDialog);
				if(result == JFileChooser.CANCEL_OPTION){
					return;
				}
				
				File f = fc.getSelectedFile();
				if(f != null){
					try{
						saveSettings(f);
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(mainPanel, e.toString(),
							"Save Settings Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
		}
	}
	
}

