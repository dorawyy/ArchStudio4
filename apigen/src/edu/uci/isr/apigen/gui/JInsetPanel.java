package edu.uci.isr.apigen.gui;

import java.awt.*;
import javax.swing.*;

public class JInsetPanel extends JPanel{
	
	protected Insets insets;
	
	public JInsetPanel(int inset){
		super();
		insets = new Insets(inset, inset, inset, inset);
	}
	
	public JInsetPanel(LayoutManager lm, int inset){
		super(lm);
		insets = new Insets(inset, inset, inset, inset);
	}
	/*
	public Insets insets(){
		return insets;
	}
	*/
	public Insets getInsets(){
		return insets;
	}
	
}

