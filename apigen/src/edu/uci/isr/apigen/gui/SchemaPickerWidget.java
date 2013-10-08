package edu.uci.isr.apigen.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import edu.uci.isr.apigen.*;

class SchemaPickerWidget extends JInsetPanel implements ActionListener{

	private JTable table;
	private DefaultTableModel tm;
	private DefaultTableColumnModel tcm;
	private JButton addButton;
	private JButton removeButton;
	
	public SchemaPickerWidget(){
		super(new BorderLayout(), 3);
		init();
	}
	
	public void init(){
		tm = new DefaultTableModel(new String[]{"Schema URI"}, 0){
			public boolean isCellEditable(int row, int column){
				return true;
			}
		};
		
		table = new JTable(tm);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//this.add("Center", JTable.createScrollPaneForTable(table));
		this.add("Center", new JScrollPane(table));
		
		addButton = new JButton("Add Schema");
		addButton.addActionListener(this);
		
		removeButton = new JButton("Remove Schema");
		removeButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		
		this.add("South", buttonPanel);
	}

	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == addButton){
			String uri = JOptionPane.showInputDialog(this, "Schema URI", 
				"Schema to Process", JOptionPane.QUESTION_MESSAGE); 

			if(uri == null){
				return;
			}
			tm.addRow(new Object[]{uri});
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
	
	public String[] getSchemaURIs(){
		String[] res = new String[tm.getRowCount()];
		
		for(int i = 0; i < tm.getRowCount(); i++){
			res[i] = (String)tm.getValueAt(i, 0);
		}
		return res;
	}

	public void setSchemaURIs(String[] schemaURIs){
		while(tm.getRowCount() != 0){
			tm.removeRow(0);
		}
		for(int i = 0; i < schemaURIs.length; i++){
			tm.addRow(new Object[]{schemaURIs[i]});
		}
	}

	public void commitEdits(){
		if(table.isEditing()){
			TableCellEditor cellEditor = table.getCellEditor();
			cellEditor.stopCellEditing();
		}
	}
}

