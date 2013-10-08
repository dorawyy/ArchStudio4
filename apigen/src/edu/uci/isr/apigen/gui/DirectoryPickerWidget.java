package edu.uci.isr.apigen.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

import edu.uci.isr.apigen.*;

class DirectoryPickerWidget extends JInsetPanel implements ActionListener{

	private JButton browseButton;
	private JTextField directoryField;
	
	public DirectoryPickerWidget(){
		super(new BorderLayout(), 3);
		init();
	}
	
	public void init(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.add("Center", panel);
		
		panel.add(new JLabel("Directory: "));
		
		directoryField = new JTextField(15);
		panel.add(directoryField);
		
		browseButton = new JButton("Browse...");
		browseButton.addActionListener(this);
		panel.add(browseButton);
	}

	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == browseButton){
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fc.showOpenDialog(this);
			if(result == JFileChooser.CANCEL_OPTION){
				return;
			}
			
			File f = fc.getSelectedFile();
			if(f != null){
				try{
					String canonicalName = f.getCanonicalPath();
					directoryField.setText(canonicalName);
				}
				catch(IOException e){
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
	
	private Frame getParentFrame(){
		Component c = this.getParent();
		while(!(c instanceof Frame)){
			c = c.getParent();
		}
		return (Frame)c;
	}
	
	public void setSelectedDirectory(File f){
		try{
			directoryField.setText(f.getCanonicalPath());
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "Problem setting directory: " + e.toString() + "; non-fatal: this can result from using someone else's settings file.", 
				"Warning", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			directoryField.setText("");
			//System.exit(1);
		}
	}
	
	public File getSelectedDirectory(){
		String selDirString = directoryField.getText().trim();
		if(selDirString.equals("")){
			return null;
		}
		else{
			return new File(selDirString);
		}
		
	}
			
}

