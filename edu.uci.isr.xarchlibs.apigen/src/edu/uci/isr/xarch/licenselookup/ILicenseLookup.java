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

import java.util.Collection;
import edu.uci.isr.xarch.XArchActionMetadata;
import edu.uci.isr.xarch.XArchTypeMetadata;
import edu.uci.isr.xarch.XArchPropertyMetadata;

/**
 * Interface for accessing objects of the
 * LicenseLookup <code>xsi:type</code> in the
 * licenselookup namespace.
 * 
 * @author Automatically generated by xArch apigen
 */
public interface ILicenseLookup extends edu.uci.isr.xarch.IXArchElement{

	public final static XArchTypeMetadata TYPE_METADATA = new XArchTypeMetadata(
		XArchTypeMetadata.XARCH_ELEMENT,
		"licenselookup", "LicenseLookup", edu.uci.isr.xarch.IXArchElement.TYPE_METADATA,
		new XArchPropertyMetadata[]{
			XArchPropertyMetadata.createElement("licenseType", "licenselookup", "LicenseType", 0, XArchPropertyMetadata.UNBOUNDED)},
		new XArchActionMetadata[]{});

	/**
	 * Add a licenseType to this LicenseLookup.
	 * @param newLicenseType licenseType to add.
	 */
	public void addLicenseType(ILicenseType newLicenseType);

	/**
	 * Add a collection of licenseTypes to this LicenseLookup.
	 * @param licenseTypes licenseTypes to add.
	 */
	public void addLicenseTypes(Collection licenseTypes);

	/**
	 * Remove all licenseTypes from this LicenseLookup.
	 */
	public void clearLicenseTypes();

	/**
	 * Remove the given licenseType from this LicenseLookup.
	 * Matching is done by the <code>isEquivalent(...)</code> function.
	 * @param licenseTypeToRemove licenseType to remove.
	 */
	public void removeLicenseType(ILicenseType licenseTypeToRemove);

	/**
	 * Remove all the given licenseTypes from this LicenseLookup.
	 * Matching is done by the <code>isEquivalent(...)</code> function.
	 * @param licenseTypes licenseType to remove.
	 */
	public void removeLicenseTypes(Collection licenseTypes);

	/**
	 * Get all the licenseTypes from this LicenseLookup.
	 * @return all licenseTypes in this LicenseLookup.
	 */
	public Collection getAllLicenseTypes();

	/**
	 * Determine if this LicenseLookup contains a given licenseType.
	 * @return <code>true</code> if this LicenseLookup contains the given
	 * licenseTypeToCheck, <code>false</code> otherwise.
	 */
	public boolean hasLicenseType(ILicenseType licenseTypeToCheck);

	/**
	 * Determine if this LicenseLookup contains the given set of licenseTypes.
	 * @param licenseTypesToCheck licenseTypes to check for.
	 * @return Collection of <code>java.lang.Boolean</code>.  If the i<sup>th</sup>
	 * element in <code>licenseTypes</code> was found, then the i<sup>th</sup>
	 * element of the collection will be set to <code>true</code>, otherwise it
	 * will be set to <code>false</code>.  Matching is done with the
	 * <code>isEquivalent(...)</code> method.
	 */
	public Collection hasLicenseTypes(Collection licenseTypesToCheck);

	/**
	 * Determine if this LicenseLookup contains each element in the 
	 * given set of licenseTypes.
	 * @param licenseTypesToCheck licenseTypes to check for.
	 * @return <code>true</code> if every element in
	 * <code>licenseTypes</code> is found in this LicenseLookup,
	 * <code>false</code> otherwise.
	 */
	public boolean hasAllLicenseTypes(Collection licenseTypesToCheck);

	/**
	 * Determine if another LicenseLookup is equivalent to this one, ignoring
	 * ID's.
	 * @param LicenseLookupToCheck LicenseLookup to compare to this one.
	 * @return <code>true</code> if all the child elements of this
	 * LicenseLookup are equivalent, <code>false</code> otherwise.
	 */
	public boolean isEquivalent(ILicenseLookup LicenseLookupToCheck);

}
