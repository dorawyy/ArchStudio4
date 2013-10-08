package edu.uci.isr.apigen;

import java.io.*;

public class TokenReplacerReader extends FilterReader{

	protected TokenMap map;
	
	protected StringBuffer inbuf = new StringBuffer();
	protected StringBuffer outbuf = new StringBuffer();
	/*
	public static void main(String[] args){
		String formLetter =
			"Hello $$(FIRST) $$(LAST).  My name is $$(MYNAME).  Mr. $$(LAST), you have won $$(FOO)";
		TokenMap m = new TokenMap(
			new String[]{"FIRST", "LAST", "MYNAME"},
			new String[]{"Raymond", "Jones", "Eric Dashofy"}
		);
		
		char[] buf = new char[500];
		
		try{
			StringReader r = new StringReader(formLetter);
			TokenReplacerReader trr = new TokenReplacerReader(r, m);
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

	public TokenReplacerReader(Reader r, TokenMap map){
		super(r);
		this.map = map;
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
		if(ch == '$'){
			inbuf.append(ch);
			return read();
		}
		else if(ch == '('){
			if(inbuf.toString().equals("$$")){
				inbuf.append(ch);
				//We found a token start!
				while(true){
					int chInt2 = super.read();
					if(chInt2 == -1){
						//oops, there's no token end.
						outbuf.append(inbuf.toString());
						inbuf.setLength(0);
						return read();
					}
					char ch2 = (char)chInt2;
					if(ch2 == ')'){
						//That's the token end.
						String tokenName = inbuf.toString().substring(3);
						inbuf.append(ch2);
						String tokenValue = map.getValue(tokenName);
						//Not a token we know.  Just leave it untouched.
						if(tokenValue == null){
							outbuf.append(inbuf.toString());
							inbuf.setLength(0);
							return(read());
						}
						else{
							outbuf.append(tokenValue);
							inbuf.setLength(0);
							return(read());
						}
					}
					else{
						//We're still in the token name.
						inbuf.append(ch2);
					}
				}
			}
			else{
				outbuf.append(inbuf.toString());
				outbuf.append(ch);
				inbuf.setLength(0);
				return read();
			}
		}
		else{
			if(inbuf.length() != 0){
				outbuf.append(inbuf.toString());
				outbuf.append(ch);
				inbuf.setLength(0);
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

