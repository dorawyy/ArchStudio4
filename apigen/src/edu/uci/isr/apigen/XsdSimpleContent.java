package edu.uci.isr.apigen;

import java.util.*;

import org.w3c.dom.*;

public class XsdSimpleContent implements XsdType{

	protected Element elt;
	
	public String getName(){
		return DOMUtils.getAttributeValue(elt, null, "name");
	}
	
	public XsdSimpleContent(Element elt){
		this.elt = elt;
	}
	
	public XsdRestriction getRestriction(){
		NodeList nl = elt.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "restriction")){
					return new XsdRestriction((Element)n);
				}
			}
		}
		return null;
	}
	
	public XsdExtension getExtension(){
		NodeList nl = elt.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "extension")){
					return new XsdExtension((Element)n);
				}
			}
		}
		return null;
	}
		
}

