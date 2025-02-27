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
package edu.uci.isr.xarch.types;

import java.util.*;

import edu.uci.isr.xarch.*;

import org.w3c.dom.*;

import edu.uci.isr.xarch.IXArch;
import edu.uci.isr.xarch.IXArchContext;

/**
 * The context interface for the types package.
 * This interface is used to create objects that are used
 * in the types namespace.
 *
 * @author Automatically Generated by xArch apigen
 */
public interface ITypesContext extends IXArchContext{

	/**
	 * Create an ISignatureServiceSimpleType object in this namespace.
	 * @return New ISignatureServiceSimpleType object.
	 */
	public ISignatureServiceSimpleType createSignatureServiceSimpleType();

	/**
	 * Brings an ISignatureServiceSimpleType object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public ISignatureServiceSimpleType recontextualizeSignatureServiceSimpleType(ISignatureServiceSimpleType value);

	/**
	 * Create an IInterface object in this namespace.
	 * @return New IInterface object.
	 */
	public IInterface createInterface();

	/**
	 * Brings an IInterface object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IInterface recontextualizeInterface(IInterface value);

	/**
	 * Create an edu.uci.isr.xarch.instance.IDescription object in this namespace.
	 * @return New edu.uci.isr.xarch.instance.IDescription object.
	 */
	public edu.uci.isr.xarch.instance.IDescription createDescription();

	/**
	 * Brings an edu.uci.isr.xarch.instance.IDescription object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public edu.uci.isr.xarch.instance.IDescription recontextualizeDescription(edu.uci.isr.xarch.instance.IDescription value);

	/**
	 * Create an edu.uci.isr.xarch.instance.IDirection object in this namespace.
	 * @return New edu.uci.isr.xarch.instance.IDirection object.
	 */
	public edu.uci.isr.xarch.instance.IDirection createDirection();

	/**
	 * Brings an edu.uci.isr.xarch.instance.IDirection object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public edu.uci.isr.xarch.instance.IDirection recontextualizeDirection(edu.uci.isr.xarch.instance.IDirection value);

	/**
	 * Create an edu.uci.isr.xarch.instance.IXMLLink object in this namespace.
	 * @return New edu.uci.isr.xarch.instance.IXMLLink object.
	 */
	public edu.uci.isr.xarch.instance.IXMLLink createXMLLink();

	/**
	 * Brings an edu.uci.isr.xarch.instance.IXMLLink object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public edu.uci.isr.xarch.instance.IXMLLink recontextualizeXMLLink(edu.uci.isr.xarch.instance.IXMLLink value);

	/**
	 * Create an IComponent object in this namespace.
	 * @return New IComponent object.
	 */
	public IComponent createComponent();

	/**
	 * Brings an IComponent object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IComponent recontextualizeComponent(IComponent value);

	/**
	 * Create an IConnector object in this namespace.
	 * @return New IConnector object.
	 */
	public IConnector createConnector();

	/**
	 * Brings an IConnector object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IConnector recontextualizeConnector(IConnector value);

	/**
	 * Create an ILink object in this namespace.
	 * @return New ILink object.
	 */
	public ILink createLink();

	/**
	 * Brings an ILink object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public ILink recontextualizeLink(ILink value);

	/**
	 * Create an edu.uci.isr.xarch.instance.IPoint object in this namespace.
	 * @return New edu.uci.isr.xarch.instance.IPoint object.
	 */
	public edu.uci.isr.xarch.instance.IPoint createPoint();

	/**
	 * Brings an edu.uci.isr.xarch.instance.IPoint object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public edu.uci.isr.xarch.instance.IPoint recontextualizePoint(edu.uci.isr.xarch.instance.IPoint value);

	/**
	 * Create an ISignatureInterfaceMapping object in this namespace.
	 * @return New ISignatureInterfaceMapping object.
	 */
	public ISignatureInterfaceMapping createSignatureInterfaceMapping();

	/**
	 * Brings an ISignatureInterfaceMapping object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public ISignatureInterfaceMapping recontextualizeSignatureInterfaceMapping(ISignatureInterfaceMapping value);

	/**
	 * Create an ISubArchitecture object in this namespace.
	 * @return New ISubArchitecture object.
	 */
	public ISubArchitecture createSubArchitecture();

	/**
	 * Brings an ISubArchitecture object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public ISubArchitecture recontextualizeSubArchitecture(ISubArchitecture value);

	/**
	 * Create an IArchStructure object in this namespace.
	 * @return New IArchStructure object.
	 */
	public IArchStructure createArchStructure();

	/**
	 * Brings an IArchStructure object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IArchStructure recontextualizeArchStructure(IArchStructure value);

	/**
	 * Create an edu.uci.isr.xarch.instance.IGroup object in this namespace.
	 * @return New edu.uci.isr.xarch.instance.IGroup object.
	 */
	public edu.uci.isr.xarch.instance.IGroup createGroup();

	/**
	 * Brings an edu.uci.isr.xarch.instance.IGroup object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public edu.uci.isr.xarch.instance.IGroup recontextualizeGroup(edu.uci.isr.xarch.instance.IGroup value);

	/**
	 * Create an ISignature object in this namespace.
	 * @return New ISignature object.
	 */
	public ISignature createSignature();

	/**
	 * Brings an ISignature object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public ISignature recontextualizeSignature(ISignature value);

	/**
	 * Create an ISignatureServiceType object in this namespace.
	 * @return New ISignatureServiceType object.
	 */
	public ISignatureServiceType createSignatureServiceType();

	/**
	 * Brings an ISignatureServiceType object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public ISignatureServiceType recontextualizeSignatureServiceType(ISignatureServiceType value);

	/**
	 * Create an IComponentType object in this namespace.
	 * @return New IComponentType object.
	 */
	public IComponentType createComponentType();

	/**
	 * Brings an IComponentType object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IComponentType recontextualizeComponentType(IComponentType value);

	/**
	 * Create an IConnectorType object in this namespace.
	 * @return New IConnectorType object.
	 */
	public IConnectorType createConnectorType();

	/**
	 * Brings an IConnectorType object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IConnectorType recontextualizeConnectorType(IConnectorType value);

	/**
	 * Create an IInterfaceType object in this namespace.
	 * @return New IInterfaceType object.
	 */
	public IInterfaceType createInterfaceType();

	/**
	 * Brings an IInterfaceType object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IInterfaceType recontextualizeInterfaceType(IInterfaceType value);

	/**
	 * Create an IArchTypes object in this namespace.
	 * @return New IArchTypes object.
	 */
	public IArchTypes createArchTypes();

	/**
	 * Brings an IArchTypes object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IArchTypes recontextualizeArchTypes(IArchTypes value);

	/**
	 * Create an IPrescribedComponentInstance object in this namespace.
	 * @return New IPrescribedComponentInstance object.
	 */
	public IPrescribedComponentInstance createPrescribedComponentInstance();

	/**
	 * Brings an IPrescribedComponentInstance object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IPrescribedComponentInstance recontextualizePrescribedComponentInstance(IPrescribedComponentInstance value);

	/**
	 * Promote an object of type <code>edu.uci.isr.xarch.instance.IComponentInstance</code>
	 * to one of type <code>IPrescribedComponentInstance</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>edu.uci.isr.xarch.instance.IComponentInstance</code>
	 * object wraps a DOM element that is the base type, then the 
	 * <code>xsi:type</code> of the base element is promoted.  Otherwise, 
	 * it is left unchanged.
	 *
	 * This function also emits an <CODE>XArchEvent</CODE> with type
	 * PROMOTE_TYPE.  The source for this events is the pre-promoted
	 * IXArchElement object (should no longer be used), and the
	 * target is the post-promotion object.  The target name is
	 * the name of the interface class that was the target of the
	 * promotion.
	 * 
	 * @param value Object to promote.
	 * @return Promoted object.
	 */
	public IPrescribedComponentInstance promoteToPrescribedComponentInstance(
	edu.uci.isr.xarch.instance.IComponentInstance value);

	/**
	 * Create an IPrescribedConnectorInstance object in this namespace.
	 * @return New IPrescribedConnectorInstance object.
	 */
	public IPrescribedConnectorInstance createPrescribedConnectorInstance();

	/**
	 * Brings an IPrescribedConnectorInstance object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IPrescribedConnectorInstance recontextualizePrescribedConnectorInstance(IPrescribedConnectorInstance value);

	/**
	 * Promote an object of type <code>edu.uci.isr.xarch.instance.IConnectorInstance</code>
	 * to one of type <code>IPrescribedConnectorInstance</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>edu.uci.isr.xarch.instance.IConnectorInstance</code>
	 * object wraps a DOM element that is the base type, then the 
	 * <code>xsi:type</code> of the base element is promoted.  Otherwise, 
	 * it is left unchanged.
	 *
	 * This function also emits an <CODE>XArchEvent</CODE> with type
	 * PROMOTE_TYPE.  The source for this events is the pre-promoted
	 * IXArchElement object (should no longer be used), and the
	 * target is the post-promotion object.  The target name is
	 * the name of the interface class that was the target of the
	 * promotion.
	 * 
	 * @param value Object to promote.
	 * @return Promoted object.
	 */
	public IPrescribedConnectorInstance promoteToPrescribedConnectorInstance(
	edu.uci.isr.xarch.instance.IConnectorInstance value);

	/**
	 * Create an IPrescribedInterfaceInstance object in this namespace.
	 * @return New IPrescribedInterfaceInstance object.
	 */
	public IPrescribedInterfaceInstance createPrescribedInterfaceInstance();

	/**
	 * Brings an IPrescribedInterfaceInstance object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IPrescribedInterfaceInstance recontextualizePrescribedInterfaceInstance(IPrescribedInterfaceInstance value);

	/**
	 * Promote an object of type <code>edu.uci.isr.xarch.instance.IInterfaceInstance</code>
	 * to one of type <code>IPrescribedInterfaceInstance</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>edu.uci.isr.xarch.instance.IInterfaceInstance</code>
	 * object wraps a DOM element that is the base type, then the 
	 * <code>xsi:type</code> of the base element is promoted.  Otherwise, 
	 * it is left unchanged.
	 *
	 * This function also emits an <CODE>XArchEvent</CODE> with type
	 * PROMOTE_TYPE.  The source for this events is the pre-promoted
	 * IXArchElement object (should no longer be used), and the
	 * target is the post-promotion object.  The target name is
	 * the name of the interface class that was the target of the
	 * promotion.
	 * 
	 * @param value Object to promote.
	 * @return Promoted object.
	 */
	public IPrescribedInterfaceInstance promoteToPrescribedInterfaceInstance(
	edu.uci.isr.xarch.instance.IInterfaceInstance value);

	/**
	 * Create an IPrescribedLinkInstance object in this namespace.
	 * @return New IPrescribedLinkInstance object.
	 */
	public IPrescribedLinkInstance createPrescribedLinkInstance();

	/**
	 * Brings an IPrescribedLinkInstance object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IPrescribedLinkInstance recontextualizePrescribedLinkInstance(IPrescribedLinkInstance value);

	/**
	 * Promote an object of type <code>edu.uci.isr.xarch.instance.ILinkInstance</code>
	 * to one of type <code>IPrescribedLinkInstance</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>edu.uci.isr.xarch.instance.ILinkInstance</code>
	 * object wraps a DOM element that is the base type, then the 
	 * <code>xsi:type</code> of the base element is promoted.  Otherwise, 
	 * it is left unchanged.
	 *
	 * This function also emits an <CODE>XArchEvent</CODE> with type
	 * PROMOTE_TYPE.  The source for this events is the pre-promoted
	 * IXArchElement object (should no longer be used), and the
	 * target is the post-promotion object.  The target name is
	 * the name of the interface class that was the target of the
	 * promotion.
	 * 
	 * @param value Object to promote.
	 * @return Promoted object.
	 */
	public IPrescribedLinkInstance promoteToPrescribedLinkInstance(
	edu.uci.isr.xarch.instance.ILinkInstance value);

	/**
	 * Create a top-level element of type <code>IArchStructure</code>.
	 * This function should be used in lieu of <code>createArchStructure</code>
	 * if the element is to be added as a sub-object of <code>IXArch</code>.
	 * @return new IArchStructure suitable for adding
	 * as a child of xArch.
	 */
	public IArchStructure createArchStructureElement();

	/**
	 * Gets the IArchStructure child from the given <code>IXArch</code>
	 * element.  If there are multiple matching children, this returns the first one.
	 * @param xArch <code>IXArch</code> object from which to get the child.
	 * @return <code>IArchStructure</code> that is the child
	 * of <code>xArch</code> or <code>null</code> if no such object exists.
	 */
	public IArchStructure getArchStructure(IXArch xArch);

	/**
	 * Gets all the IArchStructure children from the given 
	 * <code>IXArch</code> element.
	 * @param xArch <code>IXArch</code> object from which to get the children.
	 * @return Collection of <code>IArchStructure</code> that are 	
	 * the children of <code>xArch</code> or an empty collection if no such object exists.
	 */
	public Collection getAllArchStructures(IXArch xArch);

	/**
	 * Create a top-level element of type <code>IArchTypes</code>.
	 * This function should be used in lieu of <code>createArchTypes</code>
	 * if the element is to be added as a sub-object of <code>IXArch</code>.
	 * @return new IArchTypes suitable for adding
	 * as a child of xArch.
	 */
	public IArchTypes createArchTypesElement();

	/**
	 * Gets the IArchTypes child from the given <code>IXArch</code>
	 * element.  If there are multiple matching children, this returns the first one.
	 * @param xArch <code>IXArch</code> object from which to get the child.
	 * @return <code>IArchTypes</code> that is the child
	 * of <code>xArch</code> or <code>null</code> if no such object exists.
	 */
	public IArchTypes getArchTypes(IXArch xArch);

	/**
	 * Gets all the IArchTypes children from the given 
	 * <code>IXArch</code> element.
	 * @param xArch <code>IXArch</code> object from which to get the children.
	 * @return Collection of <code>IArchTypes</code> that are 	
	 * the children of <code>xArch</code> or an empty collection if no such object exists.
	 */
	public Collection getAllArchTypess(IXArch xArch);


	public final static XArchTypeMetadata TYPE_METADATA = new XArchTypeMetadata(
		XArchTypeMetadata.XARCH_CONTEXT,
		"types", null, null,
		new XArchPropertyMetadata[]{},
		new XArchActionMetadata[]{
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, ISignatureServiceSimpleType.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, ISignatureServiceSimpleType.TYPE_METADATA, ISignatureServiceSimpleType.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IInterface.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IInterface.TYPE_METADATA, IInterface.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, edu.uci.isr.xarch.instance.IDescription.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, edu.uci.isr.xarch.instance.IDescription.TYPE_METADATA, edu.uci.isr.xarch.instance.IDescription.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, edu.uci.isr.xarch.instance.IDirection.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, edu.uci.isr.xarch.instance.IDirection.TYPE_METADATA, edu.uci.isr.xarch.instance.IDirection.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, edu.uci.isr.xarch.instance.IXMLLink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, edu.uci.isr.xarch.instance.IXMLLink.TYPE_METADATA, edu.uci.isr.xarch.instance.IXMLLink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IComponent.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IComponent.TYPE_METADATA, IComponent.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IConnector.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IConnector.TYPE_METADATA, IConnector.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, ILink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, ILink.TYPE_METADATA, ILink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, edu.uci.isr.xarch.instance.IPoint.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, edu.uci.isr.xarch.instance.IPoint.TYPE_METADATA, edu.uci.isr.xarch.instance.IPoint.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, ISignatureInterfaceMapping.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, ISignatureInterfaceMapping.TYPE_METADATA, ISignatureInterfaceMapping.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, ISubArchitecture.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, ISubArchitecture.TYPE_METADATA, ISubArchitecture.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IArchStructure.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IArchStructure.TYPE_METADATA, IArchStructure.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, edu.uci.isr.xarch.instance.IGroup.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, edu.uci.isr.xarch.instance.IGroup.TYPE_METADATA, edu.uci.isr.xarch.instance.IGroup.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, ISignature.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, ISignature.TYPE_METADATA, ISignature.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, ISignatureServiceType.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, ISignatureServiceType.TYPE_METADATA, ISignatureServiceType.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IComponentType.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IComponentType.TYPE_METADATA, IComponentType.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IConnectorType.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IConnectorType.TYPE_METADATA, IConnectorType.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IInterfaceType.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IInterfaceType.TYPE_METADATA, IInterfaceType.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IArchTypes.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IArchTypes.TYPE_METADATA, IArchTypes.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IPrescribedComponentInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IPrescribedComponentInstance.TYPE_METADATA, IPrescribedComponentInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, edu.uci.isr.xarch.instance.IComponentInstance.TYPE_METADATA, IPrescribedComponentInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IPrescribedConnectorInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IPrescribedConnectorInstance.TYPE_METADATA, IPrescribedConnectorInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, edu.uci.isr.xarch.instance.IConnectorInstance.TYPE_METADATA, IPrescribedConnectorInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IPrescribedInterfaceInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IPrescribedInterfaceInstance.TYPE_METADATA, IPrescribedInterfaceInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, edu.uci.isr.xarch.instance.IInterfaceInstance.TYPE_METADATA, IPrescribedInterfaceInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IPrescribedLinkInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IPrescribedLinkInstance.TYPE_METADATA, IPrescribedLinkInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, edu.uci.isr.xarch.instance.ILinkInstance.TYPE_METADATA, IPrescribedLinkInstance.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE_ELEMENT, null, IArchStructure.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE_ELEMENT, null, IArchTypes.TYPE_METADATA)});

}

