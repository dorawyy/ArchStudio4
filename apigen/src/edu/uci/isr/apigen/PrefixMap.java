package edu.uci.isr.apigen;

import java.util.Enumeration;
import java.util.Hashtable;

public class PrefixMap{

	protected Hashtable map = new Hashtable();
	
	public PrefixMap(){
	}
	
	public PrefixMap(PrefixMap baseMap){
		for(Enumeration en = baseMap.prefixes(); en.hasMoreElements(); ){
			String name = (String)en.nextElement();
			String value = baseMap.getValue(name);
			map.put(name, value);
		}
	}
	
	public PrefixMap(String prefix, String uri){
		this(new String[]{prefix}, new String[]{uri});
	}
	
	public PrefixMap(String[] prefixes, String[] uris){
		if(prefixes.length != uris.length){
			throw new IllegalArgumentException("Number of tokens and values must be equal.");
		}
		for(int i = 0; i < prefixes.length; i++){
			map.put(prefixes[i], uris[i]);
		}
	}
	
	public void addPrefix(String prefix, String uri){
		map.put(prefix, uri);
	}
	
	public boolean hasPrefix(String prefix){
		return map.get(prefix) != null;
	}
	
	public Enumeration prefixes(){
		return map.keys();
	}
	
	public Enumeration URIs(){
		return map.elements();
	}
	
	public String getValue(String prefix){
		return (String)map.get(prefix);
	}
	
	public void remove(String prefix){
		map.remove(prefix);
	}
	
	public String toString(){
		return map.toString();
	}

}

