package edu.uci.isr.apigen.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

class SchemaLocationMappingDialog extends JDialog implements /*ChangeListener,*/ ActionListener{

	public static final int EXIT_OK = 100;
	public static final int EXIT_CANCEL = 200;
	
	protected int exitState = 0;
	
	protected Frame parentFrame;
	
	protected JTextField schemaURIField;
	protected JTextField fileNameField;
	protected JTextField urlField;
	protected JTextField packageField;
	
	protected JRadioButton fileButton;
	protected JRadioButton urlButton;
	
	protected JButton browseButton;
	protected JButton okButton;
	protected JButton cancelButton;
	
	public SchemaLocationMappingDialog(Frame parentFrame){
		super(parentFrame, "Edit Schema Location Mapping", true);
		this.parentFrame = parentFrame;
		init();
	}
	
	public void init(){
		this.getContentPane().setLayout(new BorderLayout());
		Box mainPanel = Box.createVerticalBox();
		
		JPanel uriPanel = new JInsetPanel(new FlowLayout(FlowLayout.LEFT), 5);
		uriPanel.add(new JLabel("Schema URI:"));
		schemaURIField = new JTextField(15);
		uriPanel.add(schemaURIField);
		
		JPanel filePanel = new JInsetPanel(new FlowLayout(FlowLayout.LEFT), 5);
		fileButton = new JRadioButton("File", true);
		fileButton.addActionListener(this);
		filePanel.add(fileButton);
		fileNameField = new JTextField(15);
		filePanel.add(fileNameField);
		browseButton = new JButton("Browse...");
		browseButton.addActionListener(this);
		filePanel.add(browseButton);
		
		JPanel urlPanel = new JInsetPanel(new FlowLayout(FlowLayout.LEFT), 5);
		urlButton = new JRadioButton("URL", false);
		urlButton.addActionListener(this);
		urlPanel.add(urlButton);
		urlField = new JTextField(15);
		urlPanel.add(urlField);
		
		JPanel packagePanel = new JInsetPanel(new FlowLayout(FlowLayout.LEFT), 5);
		packagePanel.add(new JLabel("Java package name:"));
		packageField = new JTextField(15);
		packagePanel.add(packageField);
		
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(urlButton);
		bg1.add(fileButton);
		
		JPanel buttonPanel = new JInsetPanel(5);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		mainPanel.add(uriPanel);
		mainPanel.add(filePanel);
		mainPanel.add(urlPanel);
		mainPanel.add(packagePanel);
		mainPanel.add(buttonPanel);
		this.getContentPane().add(mainPanel);
		
		stateChanged();
		
		this.setSize(400, 300);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == fileButton){
			stateChanged();
		}
		else if(evt.getSource() == urlButton){
			stateChanged();
		}
		else if(evt.getSource() == cancelButton){
			exitState = EXIT_CANCEL;
			this.setVisible(false);
			this.dispose();
		}
		else if(evt.getSource() == okButton){
			if(urlWasSelected()){
				String urlString = getURL();
				try{
					URL url = new URL(urlString);
				}
				catch(MalformedURLException e){
					JOptionPane.showMessageDialog(this, "Please enter a valid URL.", 
						"Invalid URL", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			exitState = EXIT_OK;
			this.setVisible(false);
			this.dispose();
		}
		else if(evt.getSource() == browseButton){
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showDialog(this, "Select");
			if(result == JFileChooser.CANCEL_OPTION){
				return;
			}
			
			File f = fileChooser.getSelectedFile();
			if(f != null){
				try{
					String canonicalName = f.getCanonicalPath();
					fileNameField.setText(canonicalName);
				}
				catch(IOException e){
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
	
	public String getFileName(){
		return fileNameField.getText().trim();
	}
	
	public String getURI(){
		return schemaURIField.getText().trim();
	}
	
	public String getURL(){
		return urlField.getText().trim();
	}
	
	public String getPackage(){
		return packageField.getText().trim();
	}
	
	public boolean urlWasSelected(){
		return urlButton.isSelected();
	}
	
	public boolean fileWasSelected(){
		return fileButton.isSelected();
	}
	
	public int getExitState(){
		return exitState;
	}
	
	public void stateChanged(){
		if(fileButton.isSelected()){
			urlField.setEnabled(false);
			fileNameField.setEnabled(true);
			browseButton.setEnabled(true);
		}
		if(urlButton.isSelected()){
			urlField.setEnabled(true);
			fileNameField.setEnabled(false);
			browseButton.setEnabled(false);
		}
	}
	
}

