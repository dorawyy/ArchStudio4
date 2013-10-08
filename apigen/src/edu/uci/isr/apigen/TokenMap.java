package edu.uci.isr.apigen;

import java.util.Enumeration;
import java.util.Hashtable;

public class TokenMap{

	protected Hashtable map = new Hashtable();
	
	public TokenMap(){
	}
	
	public TokenMap(TokenMap baseMap){
		for(Enumeration en = baseMap.tokens(); en.hasMoreElements(); ){
			String name = (String)en.nextElement();
			String value = baseMap.getValue(name);
			map.put(name, value);
		}
	}
	
	public TokenMap(String token, String value){
		this(new String[]{token}, new String[]{value});
	}
	
	public TokenMap(String[] tokens, String[] values){
		if(tokens.length != values.length){
			throw new IllegalArgumentException("Number of tokens and values must be equal.");
		}
		for(int i = 0; i < tokens.length; i++){
			map.put(tokens[i], values[i]);
		}
	}
	
	public void addToken(String name, String value){
		map.put(name, value);
	}
	
	public boolean hasToken(String token){
		return map.get(token) != null;
	}
	
	public Enumeration tokens(){
		return map.keys();
	}
	
	public Enumeration values(){
		return map.elements();
	}
	
	public String getValue(String token){
		return (String)map.get(token);
	}
	
	public void remove(String token){
		map.remove(token);
	}

}

