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

import java.util.*;

/**
 * DOM-Based implementation of the ITraceEndpoint interface.
 *
 * @author Automatically generated by xArch apigen.
 */
public class TraceEndpointImpl implements ITraceEndpoint, DOMBased{
	
	public static final String XSD_TYPE_NSURI = TracelinkConstants.NS_URI;
	public static final String XSD_TYPE_NAME = "TraceEndpoint";
	
	protected IXArch xArch;
	
	/** Tag name for traceEndpointIDs in this object. */
	public static final String TRACE_ENDPOINT_I_D_ATTR_NAME = "traceEndpointID";
	/** Tag name for locations in this object. */
	public static final String LOCATION_ELT_NAME = "location";
	/** Tag name for statuss in this object. */
	public static final String STATUS_ELT_NAME = "status";
	/** Tag name for authors in this object. */
	public static final String AUTHOR_ELT_NAME = "author";
	/** Tag name for timeStamps in this object. */
	public static final String TIME_STAMP_ELT_NAME = "timeStamp";
	/** Tag name for captureModes in this object. */
	public static final String CAPTURE_MODE_ELT_NAME = "captureMode";
	/** Tag name for actionTypes in this object. */
	public static final String ACTION_TYPE_ELT_NAME = "actionType";
	/** Tag name for traceLinks in this object. */
	public static final String TRACE_LINK_ELT_NAME = "traceLink";

	
	protected Element elt;
	
	private static SequenceOrder seqOrd = new SequenceOrder(
		new QName[]{
			new QName(TracelinkConstants.NS_URI, LOCATION_ELT_NAME),
			new QName(TracelinkConstants.NS_URI, STATUS_ELT_NAME),
			new QName(TracelinkConstants.NS_URI, AUTHOR_ELT_NAME),
			new QName(TracelinkConstants.NS_URI, TIME_STAMP_ELT_NAME),
			new QName(TracelinkConstants.NS_URI, CAPTURE_MODE_ELT_NAME),
			new QName(TracelinkConstants.NS_URI, ACTION_TYPE_ELT_NAME),
			new QName(TracelinkConstants.NS_URI, TRACE_LINK_ELT_NAME)
		}
	);
	
	public TraceEndpointImpl(Element elt){
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
				TraceEndpointImpl cloneImpl = new TraceEndpointImpl(cloneElt);
				cloneImpl.setXArch(getXArch());
				return cloneImpl;
			}
			else if(depth == 1){
				Element cloneElt = (Element)elt.cloneNode(false);
				cloneElt = (Element)doc.importNode(cloneElt, true);
				TraceEndpointImpl cloneImpl = new TraceEndpointImpl(cloneElt);
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
				TraceEndpointImpl cloneImpl = new TraceEndpointImpl(cloneElt);
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
		return ITraceEndpoint.TYPE_METADATA;
	}

	public XArchInstanceMetadata getInstanceMetadata(){
		return new XArchInstanceMetadata(XArchUtils.getPackageTitle(elt.getNamespaceURI()));
	}
	/**
	 * Set the traceEndpointID attribute on this object.
	 * @param traceEndpointID attribute value.
	 */
	public void setTraceEndpointID(String traceEndpointID){
		{
			String oldValue = getTraceEndpointID();
			if(oldValue == null ? traceEndpointID == null : oldValue.equals(traceEndpointID))
				return;
			DOMUtils.removeAttribute(elt, TracelinkConstants.NS_URI, TRACE_ENDPOINT_I_D_ATTR_NAME);
			IXArch _x = getXArch();
			if(_x != null){
				_x.fireXArchEvent(
					new XArchEvent(this, 
					XArchEvent.CLEAR_EVENT,
					XArchEvent.ATTRIBUTE_CHANGED,
					"traceEndpointID", oldValue,
					XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this), true)
				);
			}
		}
		DOMUtils.setAttribute(elt, TracelinkConstants.NS_URI, TRACE_ENDPOINT_I_D_ATTR_NAME, traceEndpointID);
		IXArch _x = getXArch();
		if(_x != null){
			_x.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.SET_EVENT,
				XArchEvent.ATTRIBUTE_CHANGED,
				"traceEndpointID", traceEndpointID,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
		
	/**
	 * Removes the traceEndpointID attribute from this object.
	 */
	public void clearTraceEndpointID(){
		String oldValue = getTraceEndpointID();
		if(oldValue == null)
			return;
		DOMUtils.removeAttribute(elt, TracelinkConstants.NS_URI, TRACE_ENDPOINT_I_D_ATTR_NAME);
		IXArch _x = getXArch();
		if(_x != null){
			_x.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.CLEAR_EVENT,
				XArchEvent.ATTRIBUTE_CHANGED,
				"traceEndpointID", oldValue,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	/**
	 * Gets the value of the traceEndpointID attribute on this object.
	 * @return traceEndpointID attribute's value or <code>null</code> if that
	 * attribute is not set.
	 */
	public String getTraceEndpointID(){
		return DOMUtils.getAttributeValue(elt, TracelinkConstants.NS_URI, TRACE_ENDPOINT_I_D_ATTR_NAME);
	}
	
	/**
	 * Determines if this object's traceEndpointID attribute has the
	 * given value.
	 * @param traceEndpointID value to test.
	 * @return <code>true</code> if the values match, <code>false</code> otherwise.
	 * Matching is done by string-matching.
	 */
	public boolean hasTraceEndpointID(String traceEndpointID){
		return DOMUtils.objNullEq(getTraceEndpointID(), traceEndpointID);
	}


	public void setLocation(edu.uci.isr.xarch.instance.IXMLLink value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot handle non-DOM-based xArch entities.");
		}
		{
			edu.uci.isr.xarch.instance.IXMLLink oldElt = getLocation();
			DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, LOCATION_ELT_NAME);
			
			IXArch context = getXArch();
			if(context != null){
				context.fireXArchEvent(
					new XArchEvent(this, 
					XArchEvent.CLEAR_EVENT,
					XArchEvent.ELEMENT_CHANGED,
					"location", oldElt,
					XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this), true)
				);
			}
		}
		Element newChildElt = (Element)(((DOMBased)value).getDOMNode());
		newChildElt = DOMUtils.cloneAndRename(newChildElt, TracelinkConstants.NS_URI, LOCATION_ELT_NAME);
		((DOMBased)value).setDOMNode(newChildElt);
		
		synchronized(DOMUtils.getDOMLock(elt)){
			elt.appendChild(newChildElt);
			DOMUtils.order(elt, getSequenceOrder());
		}
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.SET_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"location", value,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public void clearLocation(){
		edu.uci.isr.xarch.instance.IXMLLink oldElt = getLocation();
		DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, LOCATION_ELT_NAME);
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.CLEAR_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"location", oldElt,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public edu.uci.isr.xarch.instance.IXMLLink getLocation(){
		NodeList nl = DOMUtils.getChildren(elt, TracelinkConstants.NS_URI, LOCATION_ELT_NAME);
		if(nl.getLength() == 0){
			return null;
		}
		else{
			Element el = (Element)nl.item(0);
			IXArch de = getXArch();
			if(de != null){
				IXArchElement cachedXArchElt = de.getWrapper(el);
				if(cachedXArchElt != null){
					return (edu.uci.isr.xarch.instance.IXMLLink)cachedXArchElt;
				}
			}
			
			Object o = makeDerivedWrapper(el, "XMLLink");
			if(o != null){
				try{
					((edu.uci.isr.xarch.IXArchElement)o).setXArch(getXArch());
					if(de != null){
						de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)o));
					}
					return (edu.uci.isr.xarch.instance.IXMLLink)o;
				}
				catch(Exception e){}
			}
			edu.uci.isr.xarch.instance.XMLLinkImpl eltImpl = new edu.uci.isr.xarch.instance.XMLLinkImpl(el);
			eltImpl.setXArch(getXArch());
			if(de != null){
				de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)eltImpl));
			}
			return eltImpl;
		}
	}
	
	public boolean hasLocation(edu.uci.isr.xarch.instance.IXMLLink value){
		edu.uci.isr.xarch.instance.IXMLLink thisValue = getLocation();
		edu.uci.isr.xarch.instance.IXMLLink thatValue = value;
		
		if((thisValue == null) && (thatValue == null)){
			return true;
		}
		else if((thisValue == null) && (thatValue != null)){
			return false;
		}
		else if((thisValue != null) && (thatValue == null)){
			return false;
		}
		return thisValue.isEquivalent(thatValue);
	}


	public void setStatus(IStatus value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot handle non-DOM-based xArch entities.");
		}
		{
			IStatus oldElt = getStatus();
			DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, STATUS_ELT_NAME);
			
			IXArch context = getXArch();
			if(context != null){
				context.fireXArchEvent(
					new XArchEvent(this, 
					XArchEvent.CLEAR_EVENT,
					XArchEvent.ELEMENT_CHANGED,
					"status", oldElt,
					XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this), true)
				);
			}
		}
		Element newChildElt = (Element)(((DOMBased)value).getDOMNode());
		newChildElt = DOMUtils.cloneAndRename(newChildElt, TracelinkConstants.NS_URI, STATUS_ELT_NAME);
		((DOMBased)value).setDOMNode(newChildElt);
		
		synchronized(DOMUtils.getDOMLock(elt)){
			elt.appendChild(newChildElt);
			DOMUtils.order(elt, getSequenceOrder());
		}
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.SET_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"status", value,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public void clearStatus(){
		IStatus oldElt = getStatus();
		DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, STATUS_ELT_NAME);
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.CLEAR_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"status", oldElt,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public IStatus getStatus(){
		NodeList nl = DOMUtils.getChildren(elt, TracelinkConstants.NS_URI, STATUS_ELT_NAME);
		if(nl.getLength() == 0){
			return null;
		}
		else{
			Element el = (Element)nl.item(0);
			IXArch de = getXArch();
			if(de != null){
				IXArchElement cachedXArchElt = de.getWrapper(el);
				if(cachedXArchElt != null){
					return (IStatus)cachedXArchElt;
				}
			}
			
			Object o = makeDerivedWrapper(el, "Status");
			if(o != null){
				try{
					((edu.uci.isr.xarch.IXArchElement)o).setXArch(getXArch());
					if(de != null){
						de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)o));
					}
					return (IStatus)o;
				}
				catch(Exception e){}
			}
			StatusImpl eltImpl = new StatusImpl(el);
			eltImpl.setXArch(getXArch());
			if(de != null){
				de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)eltImpl));
			}
			return eltImpl;
		}
	}
	
	public boolean hasStatus(IStatus value){
		IStatus thisValue = getStatus();
		IStatus thatValue = value;
		
		if((thisValue == null) && (thatValue == null)){
			return true;
		}
		else if((thisValue == null) && (thatValue != null)){
			return false;
		}
		else if((thisValue != null) && (thatValue == null)){
			return false;
		}
		return thisValue.isEquivalent(thatValue);
	}


	public void setAuthor(IAuthor value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot handle non-DOM-based xArch entities.");
		}
		{
			IAuthor oldElt = getAuthor();
			DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, AUTHOR_ELT_NAME);
			
			IXArch context = getXArch();
			if(context != null){
				context.fireXArchEvent(
					new XArchEvent(this, 
					XArchEvent.CLEAR_EVENT,
					XArchEvent.ELEMENT_CHANGED,
					"author", oldElt,
					XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this), true)
				);
			}
		}
		Element newChildElt = (Element)(((DOMBased)value).getDOMNode());
		newChildElt = DOMUtils.cloneAndRename(newChildElt, TracelinkConstants.NS_URI, AUTHOR_ELT_NAME);
		((DOMBased)value).setDOMNode(newChildElt);
		
		synchronized(DOMUtils.getDOMLock(elt)){
			elt.appendChild(newChildElt);
			DOMUtils.order(elt, getSequenceOrder());
		}
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.SET_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"author", value,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public void clearAuthor(){
		IAuthor oldElt = getAuthor();
		DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, AUTHOR_ELT_NAME);
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.CLEAR_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"author", oldElt,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public IAuthor getAuthor(){
		NodeList nl = DOMUtils.getChildren(elt, TracelinkConstants.NS_URI, AUTHOR_ELT_NAME);
		if(nl.getLength() == 0){
			return null;
		}
		else{
			Element el = (Element)nl.item(0);
			IXArch de = getXArch();
			if(de != null){
				IXArchElement cachedXArchElt = de.getWrapper(el);
				if(cachedXArchElt != null){
					return (IAuthor)cachedXArchElt;
				}
			}
			
			Object o = makeDerivedWrapper(el, "Author");
			if(o != null){
				try{
					((edu.uci.isr.xarch.IXArchElement)o).setXArch(getXArch());
					if(de != null){
						de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)o));
					}
					return (IAuthor)o;
				}
				catch(Exception e){}
			}
			AuthorImpl eltImpl = new AuthorImpl(el);
			eltImpl.setXArch(getXArch());
			if(de != null){
				de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)eltImpl));
			}
			return eltImpl;
		}
	}
	
	public boolean hasAuthor(IAuthor value){
		IAuthor thisValue = getAuthor();
		IAuthor thatValue = value;
		
		if((thisValue == null) && (thatValue == null)){
			return true;
		}
		else if((thisValue == null) && (thatValue != null)){
			return false;
		}
		else if((thisValue != null) && (thatValue == null)){
			return false;
		}
		return thisValue.isEquivalent(thatValue);
	}


	public void setTimeStamp(ITimeStamp value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot handle non-DOM-based xArch entities.");
		}
		{
			ITimeStamp oldElt = getTimeStamp();
			DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, TIME_STAMP_ELT_NAME);
			
			IXArch context = getXArch();
			if(context != null){
				context.fireXArchEvent(
					new XArchEvent(this, 
					XArchEvent.CLEAR_EVENT,
					XArchEvent.ELEMENT_CHANGED,
					"timeStamp", oldElt,
					XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this), true)
				);
			}
		}
		Element newChildElt = (Element)(((DOMBased)value).getDOMNode());
		newChildElt = DOMUtils.cloneAndRename(newChildElt, TracelinkConstants.NS_URI, TIME_STAMP_ELT_NAME);
		((DOMBased)value).setDOMNode(newChildElt);
		
		synchronized(DOMUtils.getDOMLock(elt)){
			elt.appendChild(newChildElt);
			DOMUtils.order(elt, getSequenceOrder());
		}
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.SET_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"timeStamp", value,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public void clearTimeStamp(){
		ITimeStamp oldElt = getTimeStamp();
		DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, TIME_STAMP_ELT_NAME);
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.CLEAR_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"timeStamp", oldElt,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public ITimeStamp getTimeStamp(){
		NodeList nl = DOMUtils.getChildren(elt, TracelinkConstants.NS_URI, TIME_STAMP_ELT_NAME);
		if(nl.getLength() == 0){
			return null;
		}
		else{
			Element el = (Element)nl.item(0);
			IXArch de = getXArch();
			if(de != null){
				IXArchElement cachedXArchElt = de.getWrapper(el);
				if(cachedXArchElt != null){
					return (ITimeStamp)cachedXArchElt;
				}
			}
			
			Object o = makeDerivedWrapper(el, "TimeStamp");
			if(o != null){
				try{
					((edu.uci.isr.xarch.IXArchElement)o).setXArch(getXArch());
					if(de != null){
						de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)o));
					}
					return (ITimeStamp)o;
				}
				catch(Exception e){}
			}
			TimeStampImpl eltImpl = new TimeStampImpl(el);
			eltImpl.setXArch(getXArch());
			if(de != null){
				de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)eltImpl));
			}
			return eltImpl;
		}
	}
	
	public boolean hasTimeStamp(ITimeStamp value){
		ITimeStamp thisValue = getTimeStamp();
		ITimeStamp thatValue = value;
		
		if((thisValue == null) && (thatValue == null)){
			return true;
		}
		else if((thisValue == null) && (thatValue != null)){
			return false;
		}
		else if((thisValue != null) && (thatValue == null)){
			return false;
		}
		return thisValue.isEquivalent(thatValue);
	}


	public void setCaptureMode(ICaptureMode value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot handle non-DOM-based xArch entities.");
		}
		{
			ICaptureMode oldElt = getCaptureMode();
			DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, CAPTURE_MODE_ELT_NAME);
			
			IXArch context = getXArch();
			if(context != null){
				context.fireXArchEvent(
					new XArchEvent(this, 
					XArchEvent.CLEAR_EVENT,
					XArchEvent.ELEMENT_CHANGED,
					"captureMode", oldElt,
					XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this), true)
				);
			}
		}
		Element newChildElt = (Element)(((DOMBased)value).getDOMNode());
		newChildElt = DOMUtils.cloneAndRename(newChildElt, TracelinkConstants.NS_URI, CAPTURE_MODE_ELT_NAME);
		((DOMBased)value).setDOMNode(newChildElt);
		
		synchronized(DOMUtils.getDOMLock(elt)){
			elt.appendChild(newChildElt);
			DOMUtils.order(elt, getSequenceOrder());
		}
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.SET_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"captureMode", value,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public void clearCaptureMode(){
		ICaptureMode oldElt = getCaptureMode();
		DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, CAPTURE_MODE_ELT_NAME);
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.CLEAR_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"captureMode", oldElt,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public ICaptureMode getCaptureMode(){
		NodeList nl = DOMUtils.getChildren(elt, TracelinkConstants.NS_URI, CAPTURE_MODE_ELT_NAME);
		if(nl.getLength() == 0){
			return null;
		}
		else{
			Element el = (Element)nl.item(0);
			IXArch de = getXArch();
			if(de != null){
				IXArchElement cachedXArchElt = de.getWrapper(el);
				if(cachedXArchElt != null){
					return (ICaptureMode)cachedXArchElt;
				}
			}
			
			Object o = makeDerivedWrapper(el, "CaptureMode");
			if(o != null){
				try{
					((edu.uci.isr.xarch.IXArchElement)o).setXArch(getXArch());
					if(de != null){
						de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)o));
					}
					return (ICaptureMode)o;
				}
				catch(Exception e){}
			}
			CaptureModeImpl eltImpl = new CaptureModeImpl(el);
			eltImpl.setXArch(getXArch());
			if(de != null){
				de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)eltImpl));
			}
			return eltImpl;
		}
	}
	
	public boolean hasCaptureMode(ICaptureMode value){
		ICaptureMode thisValue = getCaptureMode();
		ICaptureMode thatValue = value;
		
		if((thisValue == null) && (thatValue == null)){
			return true;
		}
		else if((thisValue == null) && (thatValue != null)){
			return false;
		}
		else if((thisValue != null) && (thatValue == null)){
			return false;
		}
		return thisValue.isEquivalent(thatValue);
	}

	public void addActionType(IActionType newActionType){
		if(!(newActionType instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot handle non-DOM-based xArch entities.");
		}
		Element newChildElt = (Element)(((DOMBased)newActionType).getDOMNode());
		newChildElt = DOMUtils.cloneAndRename(newChildElt, TracelinkConstants.NS_URI, ACTION_TYPE_ELT_NAME);
		((DOMBased)newActionType).setDOMNode(newChildElt);

		synchronized(DOMUtils.getDOMLock(elt)){
			elt.appendChild(newChildElt);
			DOMUtils.order(elt, getSequenceOrder());
		}

		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.ADD_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"actionType", newActionType,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
		
	public void addActionTypes(Collection<IActionType> actionTypes){
		for(IActionType elt : actionTypes){
			addActionType(elt);
		}
	}		
		
	public void clearActionTypes(){
		//DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, ACTION_TYPE_ELT_NAME);
		Collection<IActionType> coll = getAllActionTypes();
		removeActionTypes(coll);
	}
	
	public void removeActionType(IActionType actionTypeToRemove){
		if(!(actionTypeToRemove instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot handle non-DOM-based xArch entities.");
		}
		NodeList nl = DOMUtils.getChildren(elt, TracelinkConstants.NS_URI, ACTION_TYPE_ELT_NAME);
		for(int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			if(n == ((DOMBased)actionTypeToRemove).getDOMNode()){
				synchronized(DOMUtils.getDOMLock(elt)){
					elt.removeChild(n);
				}

				IXArch context = getXArch();
				if(context != null){
					context.fireXArchEvent(
						new XArchEvent(this, 
						XArchEvent.REMOVE_EVENT,
						XArchEvent.ELEMENT_CHANGED,
						"actionType", actionTypeToRemove,
						XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
					);
				}

				return;
			}
		}
	}
	
	public void removeActionTypes(Collection<IActionType> actionTypes){
		for(IActionType elt : actionTypes){
			removeActionType(elt);
		}
	}
	
	public Collection<IActionType> getAllActionTypes(){
		NodeList nl = DOMUtils.getChildren(elt, TracelinkConstants.NS_URI, ACTION_TYPE_ELT_NAME);
		int nlLength = nl.getLength();
		ArrayList<IActionType> v = new ArrayList<IActionType>(nlLength);
		IXArch de = getXArch();
		for(int i = 0; i < nlLength; i++){
			Element el = (Element)nl.item(i);
			boolean found = false;
			if(de != null){
				IXArchElement cachedXArchElt = de.getWrapper(el);
				if(cachedXArchElt != null){
					v.add((IActionType)cachedXArchElt);
					found = true;
				}
			}
			if(!found){
				Object o = makeDerivedWrapper(el, "ActionType");	
				if(o != null){
					try{
						((edu.uci.isr.xarch.IXArchElement)o).setXArch(getXArch());
						if(de != null){
							de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)o));
						}
						v.add((IActionType)o);
					}
					catch(Exception e){
						ActionTypeImpl eltImpl = new ActionTypeImpl((Element)nl.item(i));
						eltImpl.setXArch(getXArch());
						if(de != null){
							de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)eltImpl));
						}
						v.add(eltImpl);
					}
				}
				else{
					ActionTypeImpl eltImpl = new ActionTypeImpl((Element)nl.item(i));
					eltImpl.setXArch(getXArch());
					if(de != null){
						de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)eltImpl));
					}
					v.add(eltImpl);
				}
			}
		}
		return v;
	}

	public boolean hasActionType(IActionType actionTypeToCheck){
		Collection<IActionType> c = getAllActionTypes();
		
		for(IActionType elt : c){
			if(elt.isEquivalent(actionTypeToCheck)){
				return true;
			}
		}
		return false;
	}
	
	public Collection<Boolean> hasActionTypes(Collection<IActionType> actionTypesToCheck){
		Vector<Boolean> v = new Vector<Boolean>();
		for(IActionType elt : actionTypesToCheck){
			v.addElement(new Boolean(hasActionType(elt)));
		}
		return v;
	}
		
	public boolean hasAllActionTypes(Collection<IActionType> actionTypesToCheck){
		for(IActionType elt : actionTypesToCheck){
			if(!hasActionType(elt)){
				return false;
			}
		}
		return true;
	}

	public void setTraceLink(ITraceLink value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot handle non-DOM-based xArch entities.");
		}
		{
			ITraceLink oldElt = getTraceLink();
			DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, TRACE_LINK_ELT_NAME);
			
			IXArch context = getXArch();
			if(context != null){
				context.fireXArchEvent(
					new XArchEvent(this, 
					XArchEvent.CLEAR_EVENT,
					XArchEvent.ELEMENT_CHANGED,
					"traceLink", oldElt,
					XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this), true)
				);
			}
		}
		Element newChildElt = (Element)(((DOMBased)value).getDOMNode());
		newChildElt = DOMUtils.cloneAndRename(newChildElt, TracelinkConstants.NS_URI, TRACE_LINK_ELT_NAME);
		((DOMBased)value).setDOMNode(newChildElt);
		
		synchronized(DOMUtils.getDOMLock(elt)){
			elt.appendChild(newChildElt);
			DOMUtils.order(elt, getSequenceOrder());
		}
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.SET_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"traceLink", value,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public void clearTraceLink(){
		ITraceLink oldElt = getTraceLink();
		DOMUtils.removeChildren(elt, TracelinkConstants.NS_URI, TRACE_LINK_ELT_NAME);
		
		IXArch context = getXArch();
		if(context != null){
			context.fireXArchEvent(
				new XArchEvent(this, 
				XArchEvent.CLEAR_EVENT,
				XArchEvent.ELEMENT_CHANGED,
				"traceLink", oldElt,
				XArchUtils.getDefaultXArchImplementation().isContainedIn(xArch, this))
			);
		}
	}
	
	public ITraceLink getTraceLink(){
		NodeList nl = DOMUtils.getChildren(elt, TracelinkConstants.NS_URI, TRACE_LINK_ELT_NAME);
		if(nl.getLength() == 0){
			return null;
		}
		else{
			Element el = (Element)nl.item(0);
			IXArch de = getXArch();
			if(de != null){
				IXArchElement cachedXArchElt = de.getWrapper(el);
				if(cachedXArchElt != null){
					return (ITraceLink)cachedXArchElt;
				}
			}
			
			Object o = makeDerivedWrapper(el, "TraceLink");
			if(o != null){
				try{
					((edu.uci.isr.xarch.IXArchElement)o).setXArch(getXArch());
					if(de != null){
						de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)o));
					}
					return (ITraceLink)o;
				}
				catch(Exception e){}
			}
			TraceLinkImpl eltImpl = new TraceLinkImpl(el);
			eltImpl.setXArch(getXArch());
			if(de != null){
				de.cacheWrapper(el, ((edu.uci.isr.xarch.IXArchElement)eltImpl));
			}
			return eltImpl;
		}
	}
	
	public boolean hasTraceLink(ITraceLink value){
		ITraceLink thisValue = getTraceLink();
		ITraceLink thatValue = value;
		
		if((thisValue == null) && (thatValue == null)){
			return true;
		}
		else if((thisValue == null) && (thatValue != null)){
			return false;
		}
		else if((thisValue != null) && (thatValue == null)){
			return false;
		}
		return thisValue.isEquivalent(thatValue);
	}

	public boolean isEquivalent(ITraceEndpoint c){
		return (getClass().equals(c.getClass())) &&
		hasTraceEndpointID(c.getTraceEndpointID()) &&
			hasLocation(c.getLocation()) &&
			hasStatus(c.getStatus()) &&
			hasAuthor(c.getAuthor()) &&
			hasTimeStamp(c.getTimeStamp()) &&
			hasCaptureMode(c.getCaptureMode()) &&
			hasAllActionTypes(c.getAllActionTypes()) &&
			c.hasAllActionTypes(getAllActionTypes()) &&
			hasTraceLink(c.getTraceLink()) ;
	}

}
