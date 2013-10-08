package edu.uci.isr.apigen;

import java.util.Enumeration;
import java.util.Hashtable;

public class SchemaMap{

	protected Hashtable map = new Hashtable();
	
	public SchemaMap(){
	}
	
	public void addSchema(String uri, XsdSchema schema){
		map.put(uri, schema);
	}
	
	public boolean hasSchema(String uri){
		return map.get(uri) != null;
	}
	
	public Enumeration URIs(){
		return map.keys();
	}
	
	public Enumeration schemas(){
		return map.elements();
	}
	
	public XsdSchema getSchema(String uri){
		return (XsdSchema)map.get(uri);
	}
	
	public void remove(String uri){
		map.remove(uri);
	}

}

