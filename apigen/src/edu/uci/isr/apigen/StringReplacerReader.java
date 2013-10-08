package edu.uci.isr.apigen;

import java.io.*;

public class StringReplacerReader extends FilterReader{

	protected String targetString;
	protected String replaceString;
	
	int pointer = 0;
	
	protected StringBuffer inbuf = new StringBuffer();
	protected StringBuffer outbuf = new StringBuffer();
	
	/*
	public static void main(String[] args){
		String formLetter =
			"Hello $$(FIRST) $$(LAST).  My name is $$(MYNAME).  Mr. $$(LAST), you have won $$(FOO)";
		
		char[] buf = new char[500];
		
		try{
			StringReader r = new StringReader(formLetter);
			StringReplacerReader trr = new StringReplacerReader(r, "FIRST", "Eric");
			while(true){
				//int chInt = trr.read();
				int nr = trr.read(buf, 0, buf.length);
				for(int i = 0; i < nr; i++){ System.out.print(buf[i]); }
				if(nr < buf.length){
					break;
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	*/

	public StringReplacerReader(Reader r, String targetString, String replaceString){
		super(r);
		this.targetString = targetString;
		this.replaceString = replaceString;
	}
	
	public int read() throws IOException{
		if(outbuf.length() != 0){
			char ch = outbuf.charAt(0);
			outbuf.deleteCharAt(0);
			return (int)ch;
		}
		
		int chInt = super.read();
		if(chInt == -1){
			if(inbuf.length() != 0){
				outbuf.append(inbuf.toString());
				inbuf.setLength(0);
				return read();
			}
			return -1;
		}
		
		char ch = (char)chInt;
		if(ch == targetString.charAt(pointer)){
			inbuf.append(ch);
			pointer++;
			if(pointer == targetString.length()){
				outbuf.append(replaceString);
				inbuf.setLength(0);
				pointer = 0;
			}
			return read();
		}
		else{
			if(inbuf.length() != 0){
				outbuf.append(inbuf.toString());
				outbuf.append(ch);
				inbuf.setLength(0);
				pointer = 0;
				return read();
			}
			return ch;
		}
	}

	public int read(char[] cbuf, int off, int len) throws IOException{
		int bytesActuallyRead = 0;
		for(int i = 0; i < len; i++){
			int chInt = read();
			if(chInt == -1){
				return bytesActuallyRead;
			}
			else{
				cbuf[off+i] = (char)chInt;
				bytesActuallyRead++;
			}
		}
		return len;
	}
	
	public int read(char[] cbuf) throws IOException{
		return read(cbuf, 0, cbuf.length);
	}
	
	
}
