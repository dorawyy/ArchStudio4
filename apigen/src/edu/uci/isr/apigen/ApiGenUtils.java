package edu.uci.isr.apigen;

import java.io.*;
import java.net.*;

import org.w3c.dom.*;
import org.xml.sax.*;
//import org.apache.xml.serialize.*;
//import org.apache.xerces.*;
//import org.apache.xerces.dom.*;
//import org.apache.xerces.parsers.*;
import javax.xml.parsers.*;


public class ApiGenUtils{

	/*
	public static void main(String[] args){
		Reader r = ApiGenUtils.openResource("data/Test.dat");
		if(r == null){
			System.out.println("No such resource.");
			return;
		}
		
		TokenMap tm = new TokenMap(
			new String[]{"METHOD_NAME", "METHOD_BODY"},
			new String[]{"foo", "bar();"}
		);
		TokenReplacerReader trr = new TokenReplacerReader(r, tm);
		char[] buf = new char[500];
		try{
			while(true){
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
			
			
	public static String replaceTokens(String tokenedString, TokenMap map){
		StringBuffer sb = new StringBuffer();
		try{
			StringReader sr = new StringReader(tokenedString);
			TokenReplacerReader trr = new TokenReplacerReader(sr, map);
			char[] buf = new char[256];
			while(true){
				int nr = trr.read(buf);
				sb.append(buf, 0, nr);
				if(nr < buf.length){
					return sb.toString();
				}
			}
		}
		catch(IOException e){
			throw new RuntimeException("This shouldn't happen.");
		}
	}

	public static Reader openResource(String resourceName){
		Class c = ApiGenUtils.class;
		InputStream is = c.getResourceAsStream(resourceName);
		if(is == null){
			return null;
		}
		else{
			return new InputStreamReader(is);
		}
	}

	public static Reader openFile(File f){
		try{
			FileReader fr = new FileReader(f);
			return fr;
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
			return null;
		}
	}

	public static Reader openFile(String filename){
		try{
			FileReader fr = new FileReader(filename);
			return fr;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static Reader openURL(String urlString){
		try{
			URL url = new URL(urlString);
			return openURL(url);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static Reader openURL(URL url){
		try{
			return new InputStreamReader(url.openStream());
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
		

	public static Document parseToDocument(Reader r) throws SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setValidating(false);
		dbf.setIgnoringElementContentWhitespace(true);
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(r));
			return doc;
		}
		catch(ParserConfigurationException pce){
			pce.printStackTrace();
			return null;
		}
		/*
		DOMParser parser = new DOMParser();
		parser.setIncludeIgnorableWhitespace(false);
		InputSource is = new InputSource(r);
		parser.parse(is);
		Document document = parser.getDocument();		
		return document;
		*/
	}

	public static boolean matches(Node n, String namespaceURI, String tagName){
		String nodeNSURI = n.getNamespaceURI();
		String nodeName = n.getLocalName();
		
		if(n == null){
			throw new SchemaProcessingException("Was expecting a node.");
		}
		return DOMUtils.objNullEq(nodeNSURI, namespaceURI) &&
			DOMUtils.objNullEq(tagName, nodeName);
	}
		
	public static void expect(Node n, String namespaceURI, String tagName){
		if(!matches(n, namespaceURI, tagName)){
			//dumpNode(n);
			throw new SchemaProcessingException("Was expecting a node {" + namespaceURI + "," + tagName + "}");
		}
	}

	public static void dumpNode(Node n){
		System.out.println("Node NSURI = " + n.getNamespaceURI());
		System.out.println("Node name = " + n.getNodeName());
		System.out.println("Node prefix = " + n.getPrefix());
		System.out.println("Node local name = " + n.getLocalName());
		if(n.getNodeType() == Node.ELEMENT_NODE){
			NamedNodeMap attributes = n.getAttributes();
			for(int i = 0; i < attributes.getLength(); i++){
				Attr attr = (Attr)attributes.item(i);
				System.out.println("Attribute: {");
				System.out.println("  name = " + attr.getName());
				System.out.println("  value = " + attr.getValue());
				System.out.println("  nsuri  = " + attr.getNamespaceURI());
				System.out.println("}");
			}
		}
	}
}

