package edu.uci.isr.apigen;

import org.w3c.dom.*;

public class XsdAttribute{
	protected Element elt;
	
	public XsdAttribute(Element elt){
		this.elt = elt;
	}
	
	public String getName(){
		return DOMUtils.getAttributeValue(elt, null, "name");
	}
	
	public String getType(){
		return DOMUtils.getAttributeValue(elt, null, "type");
	}
	
	public String getRef(){
		return DOMUtils.getAttributeValue(elt, null, "ref");
	}
	
	public String getFixed(){
		return DOMUtils.getAttributeValue(elt, null, "fixed");
	}
}

