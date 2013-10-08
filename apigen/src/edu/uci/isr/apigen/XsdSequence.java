package edu.uci.isr.apigen;

import org.w3c.dom.*;

import java.util.*;

public class XsdSequence{

	protected Element elt;
	
	public XsdSequence(Element elt){
		this.elt = elt;
	}
	
	public Collection getItems(){
		Vector v = new Vector();
		NodeList nl = elt.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "element")){
					v.addElement(new XsdElement((Element)n));
				}
				else if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "choice")){
					v.addElement(new XsdChoice((Element)n));
				}
				else if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "sequence")){
					v.addElement(new XsdSequence((Element)n));
				}
			}
		}
		return v;
	}

}

