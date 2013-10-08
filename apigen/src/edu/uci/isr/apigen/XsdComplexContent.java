package edu.uci.isr.apigen;

import org.w3c.dom.*;

import java.util.*;

public class XsdComplexContent{

	protected Element elt;
	
	public XsdComplexContent(Element elt){
		this.elt = elt;
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

