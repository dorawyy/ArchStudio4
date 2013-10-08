package edu.uci.isr.apigen;

import java.util.*;

import org.w3c.dom.*;

public class XsdSchema{

	protected String uri;
	protected Document doc;
	protected Element elt;
	
	public XsdSchema(String uri, Document doc){
		this.uri = uri;
		this.doc = doc;
		elt = doc.getDocumentElement();
		if(elt == null){
			throw new IllegalArgumentException("Document has no root element!");
		}
		if(ApiGenUtils.matches(elt, XArchConstants.OLD_XSD_NS_URI, "schema")){
			throw new SchemaProcessingException(
				"This schema conforms to the October 2000 schema specification, which is no longer " +
				"supported.  Please convert your schema to the 2001 schema specification.");
		}
		ApiGenUtils.expect(elt, XArchConstants.XSD_NS_URI, "schema");
	}
	
	public String getURI(){
		return uri;
	}
	
	public Document getDocument(){
		return doc;
	}
	
	public Collection getComplexTypes(){
		Vector v = new Vector();
		NodeList nl = elt.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "complexType")){
					v.addElement(new XsdComplexType((Element)n));
				}
			}
		}
		return v;
	}
		
	public Collection getSimpleTypes(){
		Vector v = new Vector();
		NodeList nl = elt.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "simpleType")){
					v.addElement(new XsdSimpleType((Element)n));
				}
			}
		}
		return v;
	}
		
	public Collection getTypes(){
		Vector v = new Vector();
		NodeList nl = elt.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "simpleType")){
					v.addElement(new XsdSimpleType((Element)n));
				}
				else if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "complexType")){
					v.addElement(new XsdComplexType((Element)n));
				}
			}
		}
		return v;
	}

	public Collection getAttributes(){
		Vector v = new Vector();
		NodeList nl = elt.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "attribute")){
					v.addElement(new XsdAttribute((Element)n));
				}
			}
		}
		return v;
	}
	
	public Collection getElements(){
		Vector v = new Vector();
		NodeList nl = elt.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE){
				if(ApiGenUtils.matches(n, XArchConstants.XSD_NS_URI, "element")){
					v.addElement(new XsdElement((Element)n));
				}
			}
		}
		return v;
	}
	
	public PrefixMap getPrefixMap(){
		PrefixMap prefixMap = new PrefixMap();
		prefixMap.addPrefix("xmlns", XArchConstants.XMLNS_NS_URI);
		prefixMap.addPrefix("xsi", XArchConstants.XSI_NS_URI);
		prefixMap.addPrefix("xsd", XArchConstants.XSD_NS_URI);
		
		NamedNodeMap attributes = elt.getAttributes();
		//System.out.println("The schema URI for this schema is : " + uri);
		if(attributes != null){
			for(int i = 0; i < attributes.getLength(); i++){
				Node n = attributes.item(i);
				//System.out.println("Got an attribute!");
				//ApiGenUtils.dumpNode(n);
				if(n != null){
					String prefix = n.getPrefix();
					if(prefix != null){
						if(prefix.equals("xmlns")){
							String internalPrefix = n.getLocalName();
							String internalURI = n.getNodeValue();
							prefixMap.addPrefix(internalPrefix, internalURI);
						}
					}
				}
			}
		}
		return prefixMap;
	}
	
	public QName getTypeQName(String typeName){
		int colonIndex = typeName.indexOf(":");
		if(colonIndex == -1){
			return new QName(uri, typeName);
		}
		String prefix = typeName.substring(0, colonIndex);
		typeName = typeName.substring(colonIndex + 1);
		String prefixURI = getPrefixMap().getValue(prefix);
		if(prefixURI == null){
			System.out.println("Prefix Map Is : " + getPrefixMap());
			System.out.println("Schema URI Is : " + uri);
			throw new SchemaProcessingException("Error: can't resolve prefix: " + prefix);
		}
		return new QName(prefixURI, typeName);
	}
			
}

