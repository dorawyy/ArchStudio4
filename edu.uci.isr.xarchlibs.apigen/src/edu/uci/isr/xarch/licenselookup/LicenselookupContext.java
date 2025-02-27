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
package edu.uci.isr.xarch.licenselookup;

import java.util.*;

import edu.uci.isr.xarch.*;

import org.w3c.dom.*;

import edu.uci.isr.xarch.IXArch;
import edu.uci.isr.xarch.IXArchContext;

/**
 * The context object for the licenselookup package.
 * This object is used to create objects that are used
 * in the licenselookup namespace.
 *
 * @author Automatically Generated by xArch apigen
 */
public class LicenselookupContext implements ILicenselookupContext {

	protected static final String DEFAULT_ELT_NAME = "anonymousInstanceTag";
	protected Document doc;
	protected IXArch xArch;

	/**
	 * Create a new LicenselookupContext for the given
	 * IXArch object.
	 * @param xArch XArch object to contextualize in this namespace.
	 */
	public LicenselookupContext(IXArch xArch){
		if(!(xArch instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Node docRootNode = ((DOMBased)xArch).getDOMNode();
		synchronized(DOMUtils.getDOMLock(docRootNode)){
			this.doc = docRootNode.getOwnerDocument();
			xArch.addSchemaLocation("http://www.ics.uci.edu/pub/arch/xArch/licenselookup.xsd", "http://acts.ics.uci.edu/tracelink/licenselookup.xsd");
			this.xArch = xArch;
		}
	}

	public IXArch getXArch(){
		return xArch;
	}
	
	protected Element createElement(String name){
		synchronized(DOMUtils.getDOMLock(doc)){
			return doc.createElementNS(LicenselookupConstants.NS_URI, name);
		}
	}

	public XArchTypeMetadata getTypeMetadata(){
		return ILicenselookupContext.TYPE_METADATA;
	}
	/**
	 * Create an IActorSimple object in this namespace.
	 * @return New IActorSimple object.
	 */
	public IActorSimple createActorSimple(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, ActorSimpleImpl.XSD_TYPE_NSURI, ActorSimpleImpl.XSD_TYPE_NAME);
		ActorSimpleImpl newElt = new ActorSimpleImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an IActorSimple object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IActorSimple recontextualizeActorSimple(IActorSimple value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an IOperationSimple object in this namespace.
	 * @return New IOperationSimple object.
	 */
	public IOperationSimple createOperationSimple(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, OperationSimpleImpl.XSD_TYPE_NSURI, OperationSimpleImpl.XSD_TYPE_NAME);
		OperationSimpleImpl newElt = new OperationSimpleImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an IOperationSimple object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IOperationSimple recontextualizeOperationSimple(IOperationSimple value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an IActionSimple object in this namespace.
	 * @return New IActionSimple object.
	 */
	public IActionSimple createActionSimple(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, ActionSimpleImpl.XSD_TYPE_NSURI, ActionSimpleImpl.XSD_TYPE_NAME);
		ActionSimpleImpl newElt = new ActionSimpleImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an IActionSimple object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IActionSimple recontextualizeActionSimple(IActionSimple value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an IObjectSimple object in this namespace.
	 * @return New IObjectSimple object.
	 */
	public IObjectSimple createObjectSimple(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, ObjectSimpleImpl.XSD_TYPE_NSURI, ObjectSimpleImpl.XSD_TYPE_NAME);
		ObjectSimpleImpl newElt = new ObjectSimpleImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an IObjectSimple object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IObjectSimple recontextualizeObjectSimple(IObjectSimple value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an ILicenseLookup object in this namespace.
	 * @return New ILicenseLookup object.
	 */
	public ILicenseLookup createLicenseLookup(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, LicenseLookupImpl.XSD_TYPE_NSURI, LicenseLookupImpl.XSD_TYPE_NAME);
		LicenseLookupImpl newElt = new LicenseLookupImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an ILicenseLookup object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public ILicenseLookup recontextualizeLicenseLookup(ILicenseLookup value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an ILicenseType object in this namespace.
	 * @return New ILicenseType object.
	 */
	public ILicenseType createLicenseType(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, LicenseTypeImpl.XSD_TYPE_NSURI, LicenseTypeImpl.XSD_TYPE_NAME);
		LicenseTypeImpl newElt = new LicenseTypeImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an ILicenseType object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public ILicenseType recontextualizeLicenseType(ILicenseType value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an edu.uci.isr.xarch.instance.IDescription object in this namespace.
	 * @return New edu.uci.isr.xarch.instance.IDescription object.
	 */
	public edu.uci.isr.xarch.instance.IDescription createDescription(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, edu.uci.isr.xarch.instance.DescriptionImpl.XSD_TYPE_NSURI, edu.uci.isr.xarch.instance.DescriptionImpl.XSD_TYPE_NAME);
		edu.uci.isr.xarch.instance.DescriptionImpl newElt = new edu.uci.isr.xarch.instance.DescriptionImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an edu.uci.isr.xarch.instance.IDescription object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public edu.uci.isr.xarch.instance.IDescription recontextualizeDescription(edu.uci.isr.xarch.instance.IDescription value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an edu.uci.isr.xarch.instance.IXMLLink object in this namespace.
	 * @return New edu.uci.isr.xarch.instance.IXMLLink object.
	 */
	public edu.uci.isr.xarch.instance.IXMLLink createXMLLink(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, edu.uci.isr.xarch.instance.XMLLinkImpl.XSD_TYPE_NSURI, edu.uci.isr.xarch.instance.XMLLinkImpl.XSD_TYPE_NAME);
		edu.uci.isr.xarch.instance.XMLLinkImpl newElt = new edu.uci.isr.xarch.instance.XMLLinkImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an edu.uci.isr.xarch.instance.IXMLLink object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public edu.uci.isr.xarch.instance.IXMLLink recontextualizeXMLLink(edu.uci.isr.xarch.instance.IXMLLink value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an IObligation object in this namespace.
	 * @return New IObligation object.
	 */
	public IObligation createObligation(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, ObligationImpl.XSD_TYPE_NSURI, ObligationImpl.XSD_TYPE_NAME);
		ObligationImpl newElt = new ObligationImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an IObligation object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IObligation recontextualizeObligation(IObligation value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an IRight object in this namespace.
	 * @return New IRight object.
	 */
	public IRight createRight(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, RightImpl.XSD_TYPE_NSURI, RightImpl.XSD_TYPE_NAME);
		RightImpl newElt = new RightImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an IRight object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IRight recontextualizeRight(IRight value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an IActor object in this namespace.
	 * @return New IActor object.
	 */
	public IActor createActor(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, ActorImpl.XSD_TYPE_NSURI, ActorImpl.XSD_TYPE_NAME);
		ActorImpl newElt = new ActorImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an IActor object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IActor recontextualizeActor(IActor value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an IOperation object in this namespace.
	 * @return New IOperation object.
	 */
	public IOperation createOperation(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, OperationImpl.XSD_TYPE_NSURI, OperationImpl.XSD_TYPE_NAME);
		OperationImpl newElt = new OperationImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an IOperation object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IOperation recontextualizeOperation(IOperation value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an IAction object in this namespace.
	 * @return New IAction object.
	 */
	public IAction createAction(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, ActionImpl.XSD_TYPE_NSURI, ActionImpl.XSD_TYPE_NAME);
		ActionImpl newElt = new ActionImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an IAction object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IAction recontextualizeAction(IAction value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an IObject object in this namespace.
	 * @return New IObject object.
	 */
	public IObject createObject(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, ObjectImpl.XSD_TYPE_NSURI, ObjectImpl.XSD_TYPE_NAME);
		ObjectImpl newElt = new ObjectImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an IObject object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IObject recontextualizeObject(IObject value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create an ISatisfy object in this namespace.
	 * @return New ISatisfy object.
	 */
	public ISatisfy createSatisfy(){
		Element elt = createElement(DEFAULT_ELT_NAME);
		DOMUtils.addXSIType(elt, SatisfyImpl.XSD_TYPE_NSURI, SatisfyImpl.XSD_TYPE_NAME);
		SatisfyImpl newElt = new SatisfyImpl(elt);
		newElt.setXArch(this.getXArch());
		return newElt;
	}

	/**
	 * Brings an ISatisfy object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public ISatisfy recontextualizeSatisfy(ISatisfy value){
		if(!(value instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Element elt = (Element)((DOMBased)value).getDOMNode();

		elt = DOMUtils.cloneAndRename(elt, doc, LicenselookupConstants.NS_URI, elt.getLocalName());
		//elt = DOMUtils.cloneAndRename(elt, LicenselookupConstants.NS_URI, elt.getTagName());

		//Removed next line because it causes an illegal character DOM exception
		//elt.setPrefix(null);

		((DOMBased)value).setDOMNode(elt);
		((IXArchElement)value).setXArch(this.getXArch());
		return value;
	}

	/**
	 * Create a top-level element of type <code>ILicenseLookup</code>.
	 * This function should be used in lieu of <code>createLicenseLookup</code>
	 * if the element is to be added as a sub-object of <code>IXArch</code>.
	 * @return new ILicenseLookup suitable for adding
	 * as a child of xArch.
	 */
	public ILicenseLookup createLicenseLookupElement(){
		Element elt = createElement("licenseLookup");
		DOMUtils.addXSIType(elt, LicenseLookupImpl.XSD_TYPE_NSURI, 
			LicenseLookupImpl.XSD_TYPE_NAME);
		LicenseLookupImpl newElt = new LicenseLookupImpl(elt);

		IXArch de = getXArch();
		newElt.setXArch(de);
		return newElt;
	}

	/**
	 * Gets the ILicenseLookup child from the given <code>IXArch</code>
	 * element.  If there are multiple matching children, this returns the first one.
	 * @param xArch <code>IXArch</code> object from which to get the child.
	 * @return <code>ILicenseLookup</code> that is the child
	 * of <code>xArch</code> or <code>null</code> if no such object exists.
	 */
	public ILicenseLookup getLicenseLookup(IXArch xArch){
		if(!(xArch instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Collection elementCollection = xArch.getAllObjects();
		for(Iterator en = elementCollection.iterator(); en.hasNext(); ){
			Object o = en.next();
			if(o instanceof ILicenseLookup){
				return (ILicenseLookup)o;
			}
			else if(o instanceof Element){
				Element elt = (Element)o;
				synchronized(DOMUtils.getDOMLock(elt)){
					String nsURI = elt.getNamespaceURI();
					String localName = elt.getLocalName();
					if((nsURI != null) && (nsURI.equals(LicenselookupConstants.NS_URI))){
						if((localName != null) && (localName.equals("licenseLookup"))){
							LicenseLookupImpl newElt = new LicenseLookupImpl(elt);
							newElt.setXArch(this.getXArch());
							return newElt;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets all the ILicenseLookup children from the given 
	 * <code>IXArch</code> element.
	 * @param xArch <code>IXArch</code> object from which to get the children.
	 * @return Collection of <code>ILicenseLookup</code> that are 	
	 * the children of <code>xArch</code> or an empty collection if no such object exists.
	 */
	public Collection getAllLicenseLookups(IXArch xArch){
		if(!(xArch instanceof DOMBased)){
			throw new IllegalArgumentException("Cannot process non-DOM based xArch entities.");
		}
		Collection elementCollection = xArch.getAllObjects();
		Vector v = new Vector();

		for(Iterator en = elementCollection.iterator(); en.hasNext(); ){
			Object o = en.next();
			if(o instanceof ILicenseLookup){
				v.addElement(o);
			}
			else if(o instanceof Element){
				Element elt = (Element)o;
				synchronized(DOMUtils.getDOMLock(elt)){
					String nsURI = elt.getNamespaceURI();
					String localName = elt.getLocalName();
					if((nsURI != null) && (nsURI.equals(LicenselookupConstants.NS_URI))){
						if((localName != null) && (localName.equals("licenseLookup"))){
							LicenseLookupImpl newElt = new LicenseLookupImpl(elt);
							newElt.setXArch(this.getXArch());
							v.addElement(newElt);
						}
					}
				}
			}
		}
		return v;
	}

}

