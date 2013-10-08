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
package edu.uci.isr.xarch.activitydiagramsspecial;

import java.util.Collection;
import edu.uci.isr.xarch.XArchActionMetadata;
import edu.uci.isr.xarch.XArchTypeMetadata;
import edu.uci.isr.xarch.XArchPropertyMetadata;

/**
 * Interface for accessing objects of the
 * ActivityDiagram <code>xsi:type</code> in the
 * activitydiagramsspecial namespace.  Extends and
 * inherits the properties of the
 * ActivityDiagram <code>xsi:type</code>.
 * 
 * @author xArch apigen
 */
public interface IActivityDiagram extends edu.uci.isr.xarch.uml212superstructure.IActivityDiagram, edu.uci.isr.xarch.IXArchElement{

	public final static XArchTypeMetadata TYPE_METADATA = new XArchTypeMetadata(
		XArchTypeMetadata.XARCH_ELEMENT,
		"activitydiagramsspecial", "ActivityDiagram", edu.uci.isr.xarch.uml212superstructure.IActivityDiagram.TYPE_METADATA,
		new XArchPropertyMetadata[]{
			XArchPropertyMetadata.createElement("note", "activitydiagramsspecial", "Note", 0, XArchPropertyMetadata.UNBOUNDED),
			XArchPropertyMetadata.createElement("actor", "uml212superstructure", "Actor", 0, XArchPropertyMetadata.UNBOUNDED),
			XArchPropertyMetadata.createElement("activityDiagramReference", "activitydiagramsspecial", "ExternalReference", 0, XArchPropertyMetadata.UNBOUNDED)},
		new XArchActionMetadata[]{});

	/**
	 * Add a note to this ActivityDiagram.
	 * @param newNote note to add.
	 */
	public void addNote(INote newNote);

	/**
	 * Add a collection of notes to this ActivityDiagram.
	 * @param notes notes to add.
	 */
	public void addNotes(Collection notes);

	/**
	 * Remove all notes from this ActivityDiagram.
	 */
	public void clearNotes();

	/**
	 * Remove the given note from this ActivityDiagram.
	 * Matching is done by the <code>isEquivalent(...)</code> function.
	 * @param noteToRemove note to remove.
	 */
	public void removeNote(INote noteToRemove);

	/**
	 * Remove all the given notes from this ActivityDiagram.
	 * Matching is done by the <code>isEquivalent(...)</code> function.
	 * @param notes note to remove.
	 */
	public void removeNotes(Collection notes);

	/**
	 * Get all the notes from this ActivityDiagram.
	 * @return all notes in this ActivityDiagram.
	 */
	public Collection getAllNotes();

	/**
	 * Determine if this ActivityDiagram contains a given note.
	 * @return <code>true</code> if this ActivityDiagram contains the given
	 * noteToCheck, <code>false</code> otherwise.
	 */
	public boolean hasNote(INote noteToCheck);

	/**
	 * Determine if this ActivityDiagram contains the given set of notes.
	 * @param notesToCheck notes to check for.
	 * @return Collection of <code>java.lang.Boolean</code>.  If the i<sup>th</sup>
	 * element in <code>notes</code> was found, then the i<sup>th</sup>
	 * element of the collection will be set to <code>true</code>, otherwise it
	 * will be set to <code>false</code>.  Matching is done with the
	 * <code>isEquivalent(...)</code> method.
	 */
	public Collection hasNotes(Collection notesToCheck);

	/**
	 * Determine if this ActivityDiagram contains each element in the 
	 * given set of notes.
	 * @param notesToCheck notes to check for.
	 * @return <code>true</code> if every element in
	 * <code>notes</code> is found in this ActivityDiagram,
	 * <code>false</code> otherwise.
	 */
	public boolean hasAllNotes(Collection notesToCheck);

	/**
	 * Gets the note from this ActivityDiagram with the given
	 * id.
	 * @param id ID to look for.
	 * @return note with the given ID, or <code>null</code> if not found.
	 */
	public INote getNote(String id);

	/**
	 * Gets the notes from this ActivityDiagram with the given
	 * ids.
	 * @param ids ID to look for.
	 * @return notes with the given IDs.  If an element with a given
	 * ID was not found, that ID is ignored.
	 */
	public Collection getNotes(Collection ids);


	/**
	 * Add a actor to this ActivityDiagram.
	 * @param newActor actor to add.
	 */
	public void addActor(edu.uci.isr.xarch.uml212superstructure.IActor newActor);

	/**
	 * Add a collection of actors to this ActivityDiagram.
	 * @param actors actors to add.
	 */
	public void addActors(Collection actors);

	/**
	 * Remove all actors from this ActivityDiagram.
	 */
	public void clearActors();

	/**
	 * Remove the given actor from this ActivityDiagram.
	 * Matching is done by the <code>isEquivalent(...)</code> function.
	 * @param actorToRemove actor to remove.
	 */
	public void removeActor(edu.uci.isr.xarch.uml212superstructure.IActor actorToRemove);

	/**
	 * Remove all the given actors from this ActivityDiagram.
	 * Matching is done by the <code>isEquivalent(...)</code> function.
	 * @param actors actor to remove.
	 */
	public void removeActors(Collection actors);

	/**
	 * Get all the actors from this ActivityDiagram.
	 * @return all actors in this ActivityDiagram.
	 */
	public Collection getAllActors();

	/**
	 * Determine if this ActivityDiagram contains a given actor.
	 * @return <code>true</code> if this ActivityDiagram contains the given
	 * actorToCheck, <code>false</code> otherwise.
	 */
	public boolean hasActor(edu.uci.isr.xarch.uml212superstructure.IActor actorToCheck);

	/**
	 * Determine if this ActivityDiagram contains the given set of actors.
	 * @param actorsToCheck actors to check for.
	 * @return Collection of <code>java.lang.Boolean</code>.  If the i<sup>th</sup>
	 * element in <code>actors</code> was found, then the i<sup>th</sup>
	 * element of the collection will be set to <code>true</code>, otherwise it
	 * will be set to <code>false</code>.  Matching is done with the
	 * <code>isEquivalent(...)</code> method.
	 */
	public Collection hasActors(Collection actorsToCheck);

	/**
	 * Determine if this ActivityDiagram contains each element in the 
	 * given set of actors.
	 * @param actorsToCheck actors to check for.
	 * @return <code>true</code> if every element in
	 * <code>actors</code> is found in this ActivityDiagram,
	 * <code>false</code> otherwise.
	 */
	public boolean hasAllActors(Collection actorsToCheck);

	/**
	 * Gets the actor from this ActivityDiagram with the given
	 * id.
	 * @param id ID to look for.
	 * @return actor with the given ID, or <code>null</code> if not found.
	 */
	public edu.uci.isr.xarch.uml212superstructure.IActor getActor(String id);

	/**
	 * Gets the actors from this ActivityDiagram with the given
	 * ids.
	 * @param ids ID to look for.
	 * @return actors with the given IDs.  If an element with a given
	 * ID was not found, that ID is ignored.
	 */
	public Collection getActors(Collection ids);


	/**
	 * Add a activityDiagramReference to this ActivityDiagram.
	 * @param newActivityDiagramReference activityDiagramReference to add.
	 */
	public void addActivityDiagramReference(IExternalReference newActivityDiagramReference);

	/**
	 * Add a collection of activityDiagramReferences to this ActivityDiagram.
	 * @param activityDiagramReferences activityDiagramReferences to add.
	 */
	public void addActivityDiagramReferences(Collection activityDiagramReferences);

	/**
	 * Remove all activityDiagramReferences from this ActivityDiagram.
	 */
	public void clearActivityDiagramReferences();

	/**
	 * Remove the given activityDiagramReference from this ActivityDiagram.
	 * Matching is done by the <code>isEquivalent(...)</code> function.
	 * @param activityDiagramReferenceToRemove activityDiagramReference to remove.
	 */
	public void removeActivityDiagramReference(IExternalReference activityDiagramReferenceToRemove);

	/**
	 * Remove all the given activityDiagramReferences from this ActivityDiagram.
	 * Matching is done by the <code>isEquivalent(...)</code> function.
	 * @param activityDiagramReferences activityDiagramReference to remove.
	 */
	public void removeActivityDiagramReferences(Collection activityDiagramReferences);

	/**
	 * Get all the activityDiagramReferences from this ActivityDiagram.
	 * @return all activityDiagramReferences in this ActivityDiagram.
	 */
	public Collection getAllActivityDiagramReferences();

	/**
	 * Determine if this ActivityDiagram contains a given activityDiagramReference.
	 * @return <code>true</code> if this ActivityDiagram contains the given
	 * activityDiagramReferenceToCheck, <code>false</code> otherwise.
	 */
	public boolean hasActivityDiagramReference(IExternalReference activityDiagramReferenceToCheck);

	/**
	 * Determine if this ActivityDiagram contains the given set of activityDiagramReferences.
	 * @param activityDiagramReferencesToCheck activityDiagramReferences to check for.
	 * @return Collection of <code>java.lang.Boolean</code>.  If the i<sup>th</sup>
	 * element in <code>activityDiagramReferences</code> was found, then the i<sup>th</sup>
	 * element of the collection will be set to <code>true</code>, otherwise it
	 * will be set to <code>false</code>.  Matching is done with the
	 * <code>isEquivalent(...)</code> method.
	 */
	public Collection hasActivityDiagramReferences(Collection activityDiagramReferencesToCheck);

	/**
	 * Determine if this ActivityDiagram contains each element in the 
	 * given set of activityDiagramReferences.
	 * @param activityDiagramReferencesToCheck activityDiagramReferences to check for.
	 * @return <code>true</code> if every element in
	 * <code>activityDiagramReferences</code> is found in this ActivityDiagram,
	 * <code>false</code> otherwise.
	 */
	public boolean hasAllActivityDiagramReferences(Collection activityDiagramReferencesToCheck);

	/**
	 * Gets the activityDiagramReference from this ActivityDiagram with the given
	 * id.
	 * @param id ID to look for.
	 * @return activityDiagramReference with the given ID, or <code>null</code> if not found.
	 */
	public IExternalReference getActivityDiagramReference(String id);

	/**
	 * Gets the activityDiagramReferences from this ActivityDiagram with the given
	 * ids.
	 * @param ids ID to look for.
	 * @return activityDiagramReferences with the given IDs.  If an element with a given
	 * ID was not found, that ID is ignored.
	 */
	public Collection getActivityDiagramReferences(Collection ids);

	/**
	 * Determine if another ActivityDiagram is equivalent to this one, ignoring
	 * ID's.
	 * @param ActivityDiagramToCheck ActivityDiagram to compare to this one.
	 * @return <code>true</code> if all the child elements of this
	 * ActivityDiagram are equivalent, <code>false</code> otherwise.
	 */
	public boolean isEquivalent(IActivityDiagram ActivityDiagramToCheck);

}
