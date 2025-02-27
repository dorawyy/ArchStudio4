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
package edu.uci.isr.xarch.changesets;

import java.util.*;

import edu.uci.isr.xarch.*;

import org.w3c.dom.*;

import edu.uci.isr.xarch.IXArch;
import edu.uci.isr.xarch.IXArchContext;

/**
 * The context interface for the changesets package.
 * This interface is used to create objects that are used
 * in the changesets namespace.
 *
 * @author Automatically Generated by xArch apigen
 */
public interface IChangesetsContext extends IXArchContext{

	/**
	 * Create an IBooleanValue object in this namespace.
	 * @return New IBooleanValue object.
	 */
	public IBooleanValue createBooleanValue();

	/**
	 * Brings an IBooleanValue object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IBooleanValue recontextualizeBooleanValue(IBooleanValue value);

	/**
	 * Create an IPathReference object in this namespace.
	 * @return New IPathReference object.
	 */
	public IPathReference createPathReference();

	/**
	 * Brings an IPathReference object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IPathReference recontextualizePathReference(IPathReference value);

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
	 * Create an IArchChangeSets object in this namespace.
	 * @return New IArchChangeSets object.
	 */
	public IArchChangeSets createArchChangeSets();

	/**
	 * Brings an IArchChangeSets object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IArchChangeSets recontextualizeArchChangeSets(IArchChangeSets value);

	/**
	 * Create an IAbstractChangeSet object in this namespace.
	 * @return New IAbstractChangeSet object.
	 */
	public IAbstractChangeSet createAbstractChangeSet();

	/**
	 * Brings an IAbstractChangeSet object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IAbstractChangeSet recontextualizeAbstractChangeSet(IAbstractChangeSet value);

	/**
	 * Create an IChangeSetLink object in this namespace.
	 * @return New IChangeSetLink object.
	 */
	public IChangeSetLink createChangeSetLink();

	/**
	 * Brings an IChangeSetLink object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IChangeSetLink recontextualizeChangeSetLink(IChangeSetLink value);

	/**
	 * Promote an object of type <code>IAbstractChangeSet</code>
	 * to one of type <code>IChangeSetLink</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IAbstractChangeSet</code>
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
	public IChangeSetLink promoteToChangeSetLink(
	IAbstractChangeSet value);

	/**
	 * Create an IChangeSet object in this namespace.
	 * @return New IChangeSet object.
	 */
	public IChangeSet createChangeSet();

	/**
	 * Brings an IChangeSet object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IChangeSet recontextualizeChangeSet(IChangeSet value);

	/**
	 * Promote an object of type <code>IAbstractChangeSet</code>
	 * to one of type <code>IChangeSet</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IAbstractChangeSet</code>
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
	public IChangeSet promoteToChangeSet(
	IAbstractChangeSet value);

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
	 * Create an IChangeSegment object in this namespace.
	 * @return New IChangeSegment object.
	 */
	public IChangeSegment createChangeSegment();

	/**
	 * Brings an IChangeSegment object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IChangeSegment recontextualizeChangeSegment(IChangeSegment value);

	/**
	 * Create an IAttributeSegment object in this namespace.
	 * @return New IAttributeSegment object.
	 */
	public IAttributeSegment createAttributeSegment();

	/**
	 * Brings an IAttributeSegment object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IAttributeSegment recontextualizeAttributeSegment(IAttributeSegment value);

	/**
	 * Promote an object of type <code>IChangeSegment</code>
	 * to one of type <code>IAttributeSegment</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IChangeSegment</code>
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
	public IAttributeSegment promoteToAttributeSegment(
	IChangeSegment value);

	/**
	 * Create an IElementSegment object in this namespace.
	 * @return New IElementSegment object.
	 */
	public IElementSegment createElementSegment();

	/**
	 * Brings an IElementSegment object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IElementSegment recontextualizeElementSegment(IElementSegment value);

	/**
	 * Promote an object of type <code>IChangeSegment</code>
	 * to one of type <code>IElementSegment</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IChangeSegment</code>
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
	public IElementSegment promoteToElementSegment(
	IChangeSegment value);

	/**
	 * Create an IElementManySegment object in this namespace.
	 * @return New IElementManySegment object.
	 */
	public IElementManySegment createElementManySegment();

	/**
	 * Brings an IElementManySegment object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IElementManySegment recontextualizeElementManySegment(IElementManySegment value);

	/**
	 * Promote an object of type <code>IChangeSegment</code>
	 * to one of type <code>IElementManySegment</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IChangeSegment</code>
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
	public IElementManySegment promoteToElementManySegment(
	IChangeSegment value);

	/**
	 * Create an IAbstractRelationship object in this namespace.
	 * @return New IAbstractRelationship object.
	 */
	public IAbstractRelationship createAbstractRelationship();

	/**
	 * Brings an IAbstractRelationship object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IAbstractRelationship recontextualizeAbstractRelationship(IAbstractRelationship value);

	/**
	 * Create an IRelationshipLink object in this namespace.
	 * @return New IRelationshipLink object.
	 */
	public IRelationshipLink createRelationshipLink();

	/**
	 * Brings an IRelationshipLink object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IRelationshipLink recontextualizeRelationshipLink(IRelationshipLink value);

	/**
	 * Promote an object of type <code>IAbstractRelationship</code>
	 * to one of type <code>IRelationshipLink</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IAbstractRelationship</code>
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
	public IRelationshipLink promoteToRelationshipLink(
	IAbstractRelationship value);

	/**
	 * Create an IRelationship object in this namespace.
	 * @return New IRelationship object.
	 */
	public IRelationship createRelationship();

	/**
	 * Brings an IRelationship object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IRelationship recontextualizeRelationship(IRelationship value);

	/**
	 * Promote an object of type <code>IAbstractRelationship</code>
	 * to one of type <code>IRelationship</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IAbstractRelationship</code>
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
	public IRelationship promoteToRelationship(
	IAbstractRelationship value);

	/**
	 * Create an IRelationshipRationale object in this namespace.
	 * @return New IRelationshipRationale object.
	 */
	public IRelationshipRationale createRelationshipRationale();

	/**
	 * Brings an IRelationshipRationale object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IRelationshipRationale recontextualizeRelationshipRationale(IRelationshipRationale value);

	/**
	 * Create an IDependencyRelationshipRationale object in this namespace.
	 * @return New IDependencyRelationshipRationale object.
	 */
	public IDependencyRelationshipRationale createDependencyRelationshipRationale();

	/**
	 * Brings an IDependencyRelationshipRationale object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IDependencyRelationshipRationale recontextualizeDependencyRelationshipRationale(IDependencyRelationshipRationale value);

	/**
	 * Promote an object of type <code>IRelationshipRationale</code>
	 * to one of type <code>IDependencyRelationshipRationale</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IRelationshipRationale</code>
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
	public IDependencyRelationshipRationale promoteToDependencyRelationshipRationale(
	IRelationshipRationale value);

	/**
	 * Create an IVariantRelationship object in this namespace.
	 * @return New IVariantRelationship object.
	 */
	public IVariantRelationship createVariantRelationship();

	/**
	 * Brings an IVariantRelationship object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IVariantRelationship recontextualizeVariantRelationship(IVariantRelationship value);

	/**
	 * Promote an object of type <code>IRelationship</code>
	 * to one of type <code>IVariantRelationship</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IRelationship</code>
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
	public IVariantRelationship promoteToVariantRelationship(
	IRelationship value);

	/**
	 * Create an IAndRelationship object in this namespace.
	 * @return New IAndRelationship object.
	 */
	public IAndRelationship createAndRelationship();

	/**
	 * Brings an IAndRelationship object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IAndRelationship recontextualizeAndRelationship(IAndRelationship value);

	/**
	 * Promote an object of type <code>IRelationship</code>
	 * to one of type <code>IAndRelationship</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IRelationship</code>
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
	public IAndRelationship promoteToAndRelationship(
	IRelationship value);

	/**
	 * Create an IOrRelationship object in this namespace.
	 * @return New IOrRelationship object.
	 */
	public IOrRelationship createOrRelationship();

	/**
	 * Brings an IOrRelationship object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IOrRelationship recontextualizeOrRelationship(IOrRelationship value);

	/**
	 * Promote an object of type <code>IRelationship</code>
	 * to one of type <code>IOrRelationship</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IRelationship</code>
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
	public IOrRelationship promoteToOrRelationship(
	IRelationship value);

	/**
	 * Create an IGroup object in this namespace.
	 * @return New IGroup object.
	 */
	public IGroup createGroup();

	/**
	 * Brings an IGroup object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IGroup recontextualizeGroup(IGroup value);

	/**
	 * Create an IXArchPathGroup object in this namespace.
	 * @return New IXArchPathGroup object.
	 */
	public IXArchPathGroup createXArchPathGroup();

	/**
	 * Brings an IXArchPathGroup object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IXArchPathGroup recontextualizeXArchPathGroup(IXArchPathGroup value);

	/**
	 * Promote an object of type <code>IGroup</code>
	 * to one of type <code>IXArchPathGroup</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IGroup</code>
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
	public IXArchPathGroup promoteToXArchPathGroup(
	IGroup value);

	/**
	 * Create an IManualGroup object in this namespace.
	 * @return New IManualGroup object.
	 */
	public IManualGroup createManualGroup();

	/**
	 * Brings an IManualGroup object created in another
	 * context into this context.
	 * @param value Object to recontextualize.
	 * @return <code>value</code> object in this namespace.
	 */
	public IManualGroup recontextualizeManualGroup(IManualGroup value);

	/**
	 * Promote an object of type <code>IGroup</code>
	 * to one of type <code>IManualGroup</code>.  xArch APIs
	 * are structured in such a way that a regular cast is not possible
	 * to change interface types, so casting must be done through these
	 * promotion functions.  If the <code>IGroup</code>
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
	public IManualGroup promoteToManualGroup(
	IGroup value);

	/**
	 * Create a top-level element of type <code>IArchChangeSets</code>.
	 * This function should be used in lieu of <code>createArchChangeSets</code>
	 * if the element is to be added as a sub-object of <code>IXArch</code>.
	 * @return new IArchChangeSets suitable for adding
	 * as a child of xArch.
	 */
	public IArchChangeSets createArchChangeSetsElement();

	/**
	 * Gets the IArchChangeSets child from the given <code>IXArch</code>
	 * element.  If there are multiple matching children, this returns the first one.
	 * @param xArch <code>IXArch</code> object from which to get the child.
	 * @return <code>IArchChangeSets</code> that is the child
	 * of <code>xArch</code> or <code>null</code> if no such object exists.
	 */
	public IArchChangeSets getArchChangeSets(IXArch xArch);

	/**
	 * Gets all the IArchChangeSets children from the given 
	 * <code>IXArch</code> element.
	 * @param xArch <code>IXArch</code> object from which to get the children.
	 * @return Collection of <code>IArchChangeSets</code> that are 	
	 * the children of <code>xArch</code> or an empty collection if no such object exists.
	 */
	public Collection getAllArchChangeSetss(IXArch xArch);


	public final static XArchTypeMetadata TYPE_METADATA = new XArchTypeMetadata(
		XArchTypeMetadata.XARCH_CONTEXT,
		"changesets", null, null,
		new XArchPropertyMetadata[]{},
		new XArchActionMetadata[]{
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IBooleanValue.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IBooleanValue.TYPE_METADATA, IBooleanValue.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IPathReference.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IPathReference.TYPE_METADATA, IPathReference.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, edu.uci.isr.xarch.instance.IXMLLink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, edu.uci.isr.xarch.instance.IXMLLink.TYPE_METADATA, edu.uci.isr.xarch.instance.IXMLLink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IArchChangeSets.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IArchChangeSets.TYPE_METADATA, IArchChangeSets.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IAbstractChangeSet.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IAbstractChangeSet.TYPE_METADATA, IAbstractChangeSet.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IChangeSetLink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IChangeSetLink.TYPE_METADATA, IChangeSetLink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IAbstractChangeSet.TYPE_METADATA, IChangeSetLink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IChangeSet.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IChangeSet.TYPE_METADATA, IChangeSet.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IAbstractChangeSet.TYPE_METADATA, IChangeSet.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, edu.uci.isr.xarch.instance.IDescription.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, edu.uci.isr.xarch.instance.IDescription.TYPE_METADATA, edu.uci.isr.xarch.instance.IDescription.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IChangeSegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IChangeSegment.TYPE_METADATA, IChangeSegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IAttributeSegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IAttributeSegment.TYPE_METADATA, IAttributeSegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IChangeSegment.TYPE_METADATA, IAttributeSegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IElementSegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IElementSegment.TYPE_METADATA, IElementSegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IChangeSegment.TYPE_METADATA, IElementSegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IElementManySegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IElementManySegment.TYPE_METADATA, IElementManySegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IChangeSegment.TYPE_METADATA, IElementManySegment.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IAbstractRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IAbstractRelationship.TYPE_METADATA, IAbstractRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IRelationshipLink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IRelationshipLink.TYPE_METADATA, IRelationshipLink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IAbstractRelationship.TYPE_METADATA, IRelationshipLink.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IRelationship.TYPE_METADATA, IRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IAbstractRelationship.TYPE_METADATA, IRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IRelationshipRationale.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IRelationshipRationale.TYPE_METADATA, IRelationshipRationale.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IDependencyRelationshipRationale.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IDependencyRelationshipRationale.TYPE_METADATA, IDependencyRelationshipRationale.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IRelationshipRationale.TYPE_METADATA, IDependencyRelationshipRationale.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IVariantRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IVariantRelationship.TYPE_METADATA, IVariantRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IRelationship.TYPE_METADATA, IVariantRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IAndRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IAndRelationship.TYPE_METADATA, IAndRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IRelationship.TYPE_METADATA, IAndRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IOrRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IOrRelationship.TYPE_METADATA, IOrRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IRelationship.TYPE_METADATA, IOrRelationship.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IGroup.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IGroup.TYPE_METADATA, IGroup.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IXArchPathGroup.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IXArchPathGroup.TYPE_METADATA, IXArchPathGroup.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IGroup.TYPE_METADATA, IXArchPathGroup.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE, null, IManualGroup.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.RECONTEXTUALIZE, IManualGroup.TYPE_METADATA, IManualGroup.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.PROMOTE, IGroup.TYPE_METADATA, IManualGroup.TYPE_METADATA),
			new XArchActionMetadata(XArchActionMetadata.CREATE_ELEMENT, null, IArchChangeSets.TYPE_METADATA)});

}

