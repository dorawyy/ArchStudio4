package edu.uci.isr.myx2.eclipse.extension.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.runtime.IConfigurationElement;

import edu.uci.isr.myx2.eclipse.extension.IInterface;
import edu.uci.isr.myx2.eclipse.extension.IMyxBrickExtension;

/**
 * An internal implementation of IMyxBrickExtension
 * 
 * @author Nobu Takeo nobu.takeo@gmail.com, nobu.takeo@uci.edu
 */
public class MyxBrickExtension extends ExtensionPointLocation implements IMyxBrickExtension {

	/** the id attribute of this brick */
	private final String idAttr;

	/** the name attribute of this brick */
	private final String nameAttr;

	/**
	 * the baseJavaClass attribute. the fully qualified Java Class Name of Myx
	 * Base class of this brick
	 */
	private final String baseJavaClassAttr;

	/**
	 * the defaultImplClass attribute. the fully qualified Java Class Name of
	 * default implementation class of this brick
	 */
	private final String defaultImplClassAttr;

	/**
	 * the parent brick id attribute
	 */
	private final String parentBrickIdAttr;

	/** the description element of this brick */
	private final String descriptionElmnt;

	/** the list of Interfaces */
	private final Collection<IInterface> intfs = new ArrayList<IInterface>();

	/** the parent brick. The parent brick specified by the parentBrickId */
	private IMyxBrickExtension parentBrick = null;

	/**
	 * Returns the brick name from the given extension point element.
	 * 
	 * @param element
	 * @return the brick name, or null if the brick is not found.
	 */
	public static String getName(IConfigurationElement element) {
		String elementName = element.getName();
		if (IMyxBrickExtension.ELEMENT_NAME.equals(elementName)) {
			return element.getAttribute(IMyxBrickExtension.ATTR_NAME);
		}
		else {
			return null;
		}
	}

	/**
	 * Construcs a brick from the given extension point element.
	 * 
	 * @param element
	 */
	public MyxBrickExtension(URL pluginUrl, IConfigurationElement element) {
		super(pluginUrl);

		this.nameAttr = MyxBrickExtension.getName(element);

		//TODO: should I throw an exception if the name attribute is not specified?
		this.idAttr = element.getAttribute(IMyxBrickExtension.ATTR_ID);
		this.baseJavaClassAttr = element.getAttribute(IMyxBrickExtension.ATTR_BASE_JAVA_CLASS);
		this.defaultImplClassAttr = element.getAttribute(IMyxBrickExtension.ATTR_DEFAULT_IMPL__CLASS);

		IConfigurationElement[] children = element.getChildren();
		IConfigurationElement descriptionElmnt = null;
		IConfigurationElement parentElmnt = null;
		for (IConfigurationElement child : children) {
			if (IInterface.ELEMENT_NAME.equals(child.getName())) {
				//interface element
				Interface intf = new Interface(pluginUrl, child);
				intfs.add(intf);
			}
			else if (IMyxBrickExtension.ELEMENT_DESCRIPTION_NAME.equals(child.getName())) {
				//description element
				descriptionElmnt = child;
			}
			else if (IMyxBrickExtension.ELEMENT_PARENT_BRICK_NAME.equals(child.getName())) {
				//parentBrickId attribute of parentBrick element
				parentElmnt = child;
			}
		}
		this.descriptionElmnt = descriptionElmnt == null ? null : descriptionElmnt.getValue();
		this.parentBrickIdAttr = parentElmnt == null ? null : parentElmnt.getAttribute(IMyxBrickExtension.ATTR_PARENT_BRICK_ID);
	}

	public URL getExtensionPointUrl() {
		try {
			return new URL(pluginUrl.getProtocol() + ":" + pluginUrl.getPath() + "#" + idAttr);
		}
		catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * edu.uci.isr.myx2.eclipse.extension.IMyxBrickExtension#getInterfaces()
	 */
	public Collection<IInterface> getInterfaces() {
		return new ArrayList<IInterface>(this.intfs);
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uci.isr.myx2.eclipse.extension.IMyxBrickExtension#getId()
	 */
	public String getId() {
		return idAttr;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.uci.isr.myx2.eclipse.extension.IMyxBrickExtension#getName()
	 */
	public String getName() {
		return nameAttr;
	}

	/*
	 * (non-Javadoc)
	 * @seeedu.uci.isr.myx2.eclipse.extension.IMyxBrickExtension#
	 * getFQDefaultImplClassName()
	 */
	public String getFQDefaultImplClassName() {
		return defaultImplClassAttr;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * edu.uci.isr.myx2.eclipse.extension.IMyxBrickExtension#getFQBaseClassName
	 * ()
	 */
	public String getFQBaseClassName() {
		return baseJavaClassAttr;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * edu.uci.isr.myx2.eclipse.extension.IMyxBrickExtension#getParentBrickId()
	 */
	public String getParentBrickId() {
		return parentBrickIdAttr;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * edu.uci.isr.myx2.eclipse.extension.IMyxBrickExtension#getDescription()
	 */
	public String getDescription() {
		return descriptionElmnt;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * edu.uci.isr.myx2.eclipse.extension.IMyxBrickExtension#getParentBrick()
	 */
	public IMyxBrickExtension getParentBrick() {
		return parentBrick;
	}

	public void setParentBrick(IMyxBrickExtension parentBrick) {
		this.parentBrick = parentBrick;
	}

	/*
	 * (non-Javadoc)
	 * @seeedu.uci.isr.myx2.eclipse.extension.IMyxBrickExtension#
	 * getAncestorsExtensionInterfaces()
	 */
	public Collection<IInterface> getAncestorsExtensionInterfaces() {
		Collection<IInterface> ancestorIntfs = new HashSet<IInterface>();
		MyxBrickExtension.searchExtensionInterfaces(this.parentBrick, ancestorIntfs);
		return ancestorIntfs;
	}

	private static void searchExtensionInterfaces(IMyxBrickExtension extBrick, Collection<IInterface> ancestorIntfs) {
		if (extBrick == null) {
			return;
		}
		ancestorIntfs.addAll(extBrick.getInterfaces());
		MyxBrickExtension.searchExtensionInterfaces(extBrick.getParentBrick(), ancestorIntfs);

	}
}
