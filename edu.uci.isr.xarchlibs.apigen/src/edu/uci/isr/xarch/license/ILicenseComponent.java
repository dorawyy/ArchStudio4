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
package edu.uci.isr.xarch.license;

import java.util.Collection;
import edu.uci.isr.xarch.XArchActionMetadata;
import edu.uci.isr.xarch.XArchTypeMetadata;
import edu.uci.isr.xarch.XArchPropertyMetadata;

/**
 * Interface for accessing objects of the
 * LicenseComponent <code>xsi:type</code> in the
 * license namespace.  Extends and
 * inherits the properties of the
 * Component <code>xsi:type</code>.
 * 
 * @author xArch apigen
 */
public interface ILicenseComponent extends edu.uci.isr.xarch.types.IComponent, edu.uci.isr.xarch.IXArchElement{

	public final static XArchTypeMetadata TYPE_METADATA = new XArchTypeMetadata(
		XArchTypeMetadata.XARCH_ELEMENT,
		"license", "LicenseComponent", edu.uci.isr.xarch.types.IComponent.TYPE_METADATA,
		new XArchPropertyMetadata[]{
			XArchPropertyMetadata.createElement("license", "license", "License", 1, 1)},
		new XArchActionMetadata[]{});

	/**
	 * Set the license for this LicenseComponent.
	 * @param value new license
	 */
	public void setLicense(ILicense value);

	/**
	 * Clear the license from this LicenseComponent.
	 */
	public void clearLicense();

	/**
	 * Get the license from this LicenseComponent.
	 * @return license
	 */
	public ILicense getLicense();

	/**
	 * Determine if this LicenseComponent has the given license
	 * @param licenseToCheck license to compare
	 * @return <code>true</code> if the licenses are equivalent,
	 * <code>false</code> otherwise
	 */
	public boolean hasLicense(ILicense licenseToCheck);
	/**
	 * Determine if another LicenseComponent is equivalent to this one, ignoring
	 * ID's.
	 * @param LicenseComponentToCheck LicenseComponent to compare to this one.
	 * @return <code>true</code> if all the child elements of this
	 * LicenseComponent are equivalent, <code>false</code> otherwise.
	 */
	public boolean isEquivalent(ILicenseComponent LicenseComponentToCheck);

}
