/*
 * Copyright (c) 2000-2005 Regents of the University of California.
 * All rights reserved.
 *
 * This software was developed at the University of California, Irvine.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the University of California, Irvine.  The name of the
 * University may not be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package edu.uci.isr.xarch.tracelink;

import org.w3c.dom.*;

import edu.uci.isr.xarch.*;


/**
 * DOM-Based implementation of the IAuthor interface.
 *
 * @author Automatically generated by xArch apigen.
 */
public class AuthorImpl implements IAuthor, DOMBased{
	
	public static final String XSD_TYPE_NSURI = TracelinkConstants.NS_URI;
	public static final String XSD_TYPE_NAME = "Author";
	
	protected IXArch xArch;
	
	/** Tag name for usernames in this object. */
	public static final String USERNAME_ATTR_NAME = "username";
	/** Tag name for groupnames in this object. */
	public static final String GROUPNAME_ATTR_NAME = "groupname";

	
	protected Element elt;
	
	private static SequenceOrder seqOrd = new SequenceOrder(
		new QName[]{
		}
	);
	
	public AuthorImpl(Element elt){
		if(elt == null){
			throw new IllegalArgumentException("Element cannot be null.");
		}
		this.elt = elt;
	}

	public Node getDOMNode(){
		return elt;
	}
	
	public void setDOMNode(Node node){
		if(node.getNodeType() != Node.ELEMENT_NODE){
			throw new IllegalArgumentException("Base DOM node of this type must be an Element.");
		}
		elt = (Element)node;
	}
	
	protected static SequenceOrder getSequenceOrder(){
		return seqOrd;
	}
	
	public void setXArch(IXArch xArch){
		this.xArch = xArch;
	}
	
	public IXArch getXArch(){
		return this.xArch;
	}

	public IXArchElement cloneElement(int depth){
		synchronized(DOMUtils.getDOMLock(elt)){
			Document doc = elt.getOwnerDocument();
			if(depth == 0){
				Element cloneElt = (Element)elt.cloneNode(false);
				cloneElt = (Element)doc.importNode(cloneElt, true);
				AuthorImpl cloneImpl = new AuthorImpl(cloneElt);
				cloneImpl.setXArch(getXArch());
				return cloneImpl;
			}
			else if(depth == 1){
				Element cloneElt = (Element)elt.cloneNode(false);
				cloneElt = (Element)doc.importNode(cloneElt, true);
				AuthorImpl cloneImpl = new AuthorImpl(cloneElt);
				cloneImpl.setXArch(getXArch());
				
				NodeList nl = elt.getChildNodes();
				int size = nl.getLength();
				for(int i = 0; i < size; i++){
					Node n = nl.item(i);
					Node cloneNode = (Node)n.cloneNode(false);
					cloneNode = doc.importNode(cloneNode, true);
					cloneElt.appendChild(cloneNode);
				}
				return cloneImpl;
			}
			else /* depth = infinity */{
				Element cloneElt = (Element)elt.cloneNode(true);
				cloneElt = (Element)doc.importNode(cloneElt, true);
				AuthorImpl cloneImpl = new AuthorImpl(cloneElt);
				cloneImpl.setXArch(getXArch());
				return cloneImpl;
			}
		}
	}

	//Override 'equals' to be DOM-based...	
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(!(o instanceof DOMBased)){
			return super.equals(o);
		}
		DOMBased db = (DOMBased)o;
		Node dbNode = db.getDOMNode();
		return dbNode.equals(getDOMNode());
	}

	//Override 'hashCode' to be based on the underlying node
	public int hashCode(){
		return getDOMNode().hashCode();
	}

	/**
	 * For internal use only.
	 */
	private static Object makeDerivedWrapper(Element elt, String baseTypeName){
		synchronized(DOMUtils.getDOMLock(elt)){
			QName typeName = XArchUtils.getXSIType(elt);
			if(typeName == null){
				return null;
			}
			else{
				if(!DOMUtils.hasXSIType(elt, "http://www.ics.uci.edu/pub/arch/xArch/tracelink.xsd", baseTypeName)){
					try{
						String packageTitle = XArchUtils.getPackageTitle(typeName.getNamespaceURI());
						String packageName = XArchUtils.getPackageName(packageTitle);
						String implName = XArchUtils.getImplName(packageName, typeName.getName());
						Class c = Class.forName(implName);
						java.lang.reflect.Constructor con = c.getConstructor(new Class[]{Element.class});
						Object o = con.newInstance(new Object[]{elt});
						return o;
					}
					catch(Exception e){
						//Lots of bad things could happen, but this
						//is OK, because this is best-effort anyway.
					}
				}
				return null;
			}
		}
	}

	public XArchTypeMetadata getTypeMetadata(){
		return IAuthor.TYPE_METADATA;
	}

	public XArchInstanceMetadata getInstanceMetadata(){
		return new XArchInstanceMetadata(XArchUtils.getPackageTitle(elt.getNamespaceURI()));
	}
	/**
	 * Set the username attribute on this object.
	 * @param username attribute value.
	 */
	public void setUsername(String username){
		{
			String oldValue = getUsername();
			if(oldValue == null ? username == null : oldValue.equals(username))
				return;
			DOMUtils.removeAttribute(elt, TracelinkConstants.NS_URI, USERNAME_ATTR_NAME);
			IXArch _x = getXArch();
			if(_x != null){
				_x.fireXArchEvent(
					new XArchEvent(this, 
					XArchEvent.CLEAR_EVENT,
					XArchEvent.ATTRIBUTE_CHANGED,
					"username", oldValue,
					XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this), true)
				);
			}
		}
		DOMUtils.setAttribute(elt, TracelinkConstants.NS_URI, USERNAME_ATTR_NAME, username);
		IXArch _x = getXArch();
		if(_x != null){
			_x.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.SET_EVENT,
				XArchEvent.ATTRIBUTE_CHANGED,
				"username", username,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
		
	/**
	 * Removes the username attribute from this object.
	 */
	public void clearUsername(){
		String oldValue = getUsername();
		if(oldValue == null)
			return;
		DOMUtils.removeAttribute(elt, TracelinkConstants.NS_URI, USERNAME_ATTR_NAME);
		IXArch _x = getXArch();
		if(_x != null){
			_x.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.CLEAR_EVENT,
				XArchEvent.ATTRIBUTE_CHANGED,
				"username", oldValue,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	/**
	 * Gets the value of the username attribute on this object.
	 * @return username attribute's value or <code>null</code> if that
	 * attribute is not set.
	 */
	public String getUsername(){
		return DOMUtils.getAttributeValue(elt, TracelinkConstants.NS_URI, USERNAME_ATTR_NAME);
	}
	
	/**
	 * Determines if this object's username attribute has the
	 * given value.
	 * @param username value to test.
	 * @return <code>true</code> if the values match, <code>false</code> otherwise.
	 * Matching is done by string-matching.
	 */
	public boolean hasUsername(String username){
		return DOMUtils.objNullEq(getUsername(), username);
	}

	/**
	 * Set the groupname attribute on this object.
	 * @param groupname attribute value.
	 */
	public void setGroupname(String groupname){
		{
			String oldValue = getGroupname();
			if(oldValue == null ? groupname == null : oldValue.equals(groupname))
				return;
			DOMUtils.removeAttribute(elt, TracelinkConstants.NS_URI, GROUPNAME_ATTR_NAME);
			IXArch _x = getXArch();
			if(_x != null){
				_x.fireXArchEvent(
					new XArchEvent(this, 
					XArchEvent.CLEAR_EVENT,
					XArchEvent.ATTRIBUTE_CHANGED,
					"groupname", oldValue,
					XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this), true)
				);
			}
		}
		DOMUtils.setAttribute(elt, TracelinkConstants.NS_URI, GROUPNAME_ATTR_NAME, groupname);
		IXArch _x = getXArch();
		if(_x != null){
			_x.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.SET_EVENT,
				XArchEvent.ATTRIBUTE_CHANGED,
				"groupname", groupname,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
		
	/**
	 * Removes the groupname attribute from this object.
	 */
	public void clearGroupname(){
		String oldValue = getGroupname();
		if(oldValue == null)
			return;
		DOMUtils.removeAttribute(elt, TracelinkConstants.NS_URI, GROUPNAME_ATTR_NAME);
		IXArch _x = getXArch();
		if(_x != null){
			_x.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.CLEAR_EVENT,
				XArchEvent.ATTRIBUTE_CHANGED,
				"groupname", oldValue,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	/**
	 * Gets the value of the groupname attribute on this object.
	 * @return groupname attribute's value or <code>null</code> if that
	 * attribute is not set.
	 */
	public String getGroupname(){
		return DOMUtils.getAttributeValue(elt, TracelinkConstants.NS_URI, GROUPNAME_ATTR_NAME);
	}
	
	/**
	 * Determines if this object's groupname attribute has the
	 * given value.
	 * @param groupname value to test.
	 * @return <code>true</code> if the values match, <code>false</code> otherwise.
	 * Matching is done by string-matching.
	 */
	public boolean hasGroupname(String groupname){
		return DOMUtils.objNullEq(getGroupname(), groupname);
	}

	public boolean isEquivalent(IAuthor c){
		return (getClass().equals(c.getClass())) &&
		hasUsername(c.getUsername()) &&
		hasGroupname(c.getGroupname()) ;
	}

}
