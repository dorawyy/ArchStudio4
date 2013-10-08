package edu.uci.isr.apigen;

import java.util.Vector;
import org.w3c.dom.*;

/**
 * Simple implementation of the DOM <code>NodeList</code>
 * interface.
 *
 * @author Eric M. Dashofy (edashofy@ics.uci.edu)
 */
class SimpleNodeList implements org.w3c.dom.NodeList{

	protected Vector listContents = new Vector();
	
	public SimpleNodeList(){
	}
	
	public void addNode(Node n){
		listContents.addElement(n);
	}
	
	public void removeNode(Node n){
		listContents.removeElement(n);
	}
	
	public int getLength(){
		return listContents.size();
	}
	
	public Node item(int n){
		return (Node)listContents.elementAt(n);
	}

}

