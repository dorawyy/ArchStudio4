package edu.uci.isr.apigen;

import org.w3c.dom.*;

import java.util.*;

public class XsdRestriction{

	protected Element elt;
	
	public XsdRestriction(Element elt){
		this.elt = elt;
	}

	public String getBase(){
		return DOMUtils.getAttributeValue(elt, null, "base");
	}
	
	public Collection getEnumerationValues(){
		Vector v = new Vector();
		NodeList nl = elt.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "enumeration")){
					v.addElement(new XsdEnumeration((Element)n));
				}
			}
		}
		return v;
	}
	
	public String getFixed(){
		return DOMUtils.getAttributeValue(elt, null, "fixed");
	}
		
}

