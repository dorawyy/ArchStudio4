package edu.uci.isr.apigen;

import org.w3c.dom.*;

import java.util.*;

public class XsdEnumeration{

	protected Element elt;
	
	public XsdEnumeration(Element elt){
		this.elt = elt;
	}

	public String getValue(){
		return DOMUtils.getAttributeValue(elt, null, "value");
	}
}

