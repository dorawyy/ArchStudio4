package edu.uci.isr.apigen;

public class SchemaProcessingException extends RuntimeException{
	
	public SchemaProcessingException(){
		super();
	}
	
	public SchemaProcessingException(String msg){
		super(msg);
	}
	
	public SchemaProcessingException(Exception e){
		super(e);
	}


}

