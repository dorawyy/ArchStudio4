package edu.uci.isr.apigen;

import org.w3c.dom.*;

public class XsdElement{

	protected Element elt;
	
	// An element that has this element in its "ref" attribute
	protected XsdElement referencing;
	
	public XsdElement(Element elt){
		this.elt = elt;
	}
	
	public void setReferencing(XsdElement referencing){
		this.referencing = referencing;
	}
	
	public XsdElement getReferencing() {
		return referencing;
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
	
	public XsdType getAnonymousType(){
		NodeList nl = elt.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "complexType")){
					return new XsdComplexType((Element)n);
				}
				else if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "simpleType")){
					return new XsdSimpleType((Element)n);
				}
			}
		}
		return null;
	}
	
		
	
	public int getMinOccurs(){
		if (referencing != null)
			return referencing.getMinOccurs();
		
		String minOccursString = DOMUtils.getAttributeValue(elt, null, "minOccurs");
		if(minOccursString == null){
			return 1;
		}
		return Integer.parseInt(minOccursString);
	}
	
	public int getMaxOccurs(){
		if (referencing != null)
			return referencing.getMaxOccurs();
		
		String maxOccursString = DOMUtils.getAttributeValue(elt, null, "maxOccurs");
		if(maxOccursString == null){
			return 1;
		}
		else if(maxOccursString.equals("unbounded")){
			return -1;
		}
		return Integer.parseInt(maxOccursString);
	}
	
	public String getFixed(){
		if (referencing != null)
			return referencing.getFixed();
		
		return DOMUtils.getAttributeValue(elt, null, "fixed");
	}
	
}

