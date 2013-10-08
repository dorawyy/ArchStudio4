package edu.uci.isr.apigen.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import edu.uci.isr.apigen.*;

class SchemaLocationMappingWidget extends JInsetPanel implements ActionListener{
	
	private JTable table;
	private DefaultTableModel tm;
	private DefaultTableColumnModel tcm;
	private JLabel noPrefixLabel;
	private JTextField noPrefixText;
	private JButton addButton;
	private JButton removeButton;
	private JCheckBox locationsAreCanonicalCheckbox;
		
	public SchemaLocationMappingWidget(){
		super(new BorderLayout(), 3);
		init();
	}
	
	public void init(){
		tm = new DefaultTableModel(new String[]{"Schema URI", "Is A", "At", "Package Name"}, 0){
			public boolean isCellEditable(int row, int column){
				return true;
			}
		};
		
		table = new JTable(tm);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//this.add("Center", JTable.createScrollPaneForTable(table));
		table.getColumn("Is A").setCellEditor(new DefaultCellEditor(new JComboBox(new String[]{"URL", "File"})));
		this.add("Center", new JScrollPane(table));
		
		JPanel noPrefixPanel = new JPanel();
		noPrefixPanel.setLayout(new FlowLayout());
		noPrefixLabel = new JLabel("Namespaces Opt-out: ");
		noPrefixText = new JTextField();
		noPrefixText.setColumns(30);
		noPrefixPanel.add(noPrefixLabel);
		noPrefixPanel.add(noPrefixText);
		
		addButton = new JButton("Add Mapping");
		addButton.addActionListener(this);
		
		removeButton = new JButton("Remove Mapping");
		removeButton.addActionListener(this);
		
		locationsAreCanonicalCheckbox = new JCheckBox("Schema locations are 'official.'");
		
		JPanel bottomPanel = new JPanel();
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		
		JPanel canonicalPanel = new JPanel();
		canonicalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		canonicalPanel.add(locationsAreCanonicalCheckbox);
		
		bottomPanel.setLayout(new GridLayout(3,1));
		bottomPanel.add(buttonPanel);
		bottomPanel.add(canonicalPanel);
		bottomPanel.add(noPrefixPanel);
		
		this.add("South", bottomPanel);
	}

	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == addButton){
			SchemaLocationMappingDialog dlg = new SchemaLocationMappingDialog(getParentFrame());
			if(dlg.getExitState() == SchemaLocationMappingDialog.EXIT_OK){
				String uri = dlg.getURI();
				String type = null;
				String value = null;
				if(dlg.fileWasSelected()){
					type = "File";
					value = dlg.getFileName();
				}
				else if(dlg.urlWasSelected()){
					type = "URL";
					value = dlg.getURL();
				}
				String packageName = dlg.getPackage();
				tm.addRow(new Object[]{uri, type, value, packageName});
			}
		}
		else if(evt.getSource() == removeButton){
			int row = table.getSelectedRow();
			if(row == -1){
				return;
			}
			else{
				tm.removeRow(row);
				repaint();
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
	
	public void setSchemaLocationMap(SchemaLocationMap map){
		while(tm.getRowCount() != 0){
			tm.removeRow(0);
		}
		for(Enumeration en = map.keys(); en.hasMoreElements(); ){
			String uri = (String)en.nextElement();
			Object valObj = map.getValue(uri);
			String type = null;
			String value = null;
			String packageName = map.getPackageName(uri);
			if(valObj instanceof URL){
				type = "URL";
				value = valObj.toString();
			}
			else if(valObj instanceof File){
				type = "File";
				try{
					value = ((File)valObj).getCanonicalPath();
				}
				catch(IOException e){
					e.printStackTrace();
					System.exit(1);
				}
			}
			
			tm.addRow(new Object[]{uri, type, value, packageName});
		}
		locationsAreCanonicalCheckbox.setSelected(map.locationsAreCanonical());
		noPrefixText.setText(map.getNoPrefix());
	}
			
	public SchemaLocationMap getSchemaLocationMap(){
		SchemaLocationMap map = new SchemaLocationMap();
		for(int i = 0; i < tm.getRowCount(); i++){
			String uri = (String)tm.getValueAt(i, 0);
			String type = (String)tm.getValueAt(i, 1);
			String value = (String)tm.getValueAt(i, 2);
			String packageName = (String)tm.getValueAt(i, 3);
			
			if(type.toLowerCase().equals("file")){
				map.addLocation(uri, new File(value));
			}
			else if(type.toLowerCase().equals("url")){
				try{
					map.addLocation(uri, new URL(value));
				}
				catch(MalformedURLException e){
					//Shouldn't happen.
					e.printStackTrace();
				}
			}
			map.addPackageName(uri, packageName);
		}
		map.setLocationsAreCanonical(locationsAreCanonicalCheckbox.isSelected());
		map.setNoPrefix(noPrefixText.getText().trim());
		return map;
	}
	
	public void commitEdits(){
		if(table.isEditing()){
			TableCellEditor cellEditor = table.getCellEditor();
			cellEditor.stopCellEditing();
		}
	}
}

