/*
 * Copyright (c) 2000-2001 Regents of the University of California. All rights
 * reserved. This software was developed at the University of California,
 * Irvine. Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are duplicated in
 * all such forms and that any documentation, advertising materials, and other
 * materials related to such distribution and use acknowledge that the software
 * was developed by the University of California, Irvine. The name of the
 * University may not be used to endorse or promote products derived from this
 * software without specific prior written permission. THIS SOFTWARE IS PROVIDED
 * ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, WITHOUT
 * LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE.
 */
package edu.uci.isr.xarchflat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.uci.isr.sysutils.BidirectionalHashMap;
import edu.uci.isr.sysutils.BidirectionalMap;
import edu.uci.isr.sysutils.ListenerList;
import edu.uci.isr.xadlutils.XadlUtils;
import edu.uci.isr.xarch.DOMBased;
import edu.uci.isr.xarch.DOMUtils;
import edu.uci.isr.xarch.IXArch;
import edu.uci.isr.xarch.IXArchElement;
import edu.uci.isr.xarch.IXArchImplementation;
import edu.uci.isr.xarch.QName;
import edu.uci.isr.xarch.XArchEvent;
import edu.uci.isr.xarch.XArchImpl;
import edu.uci.isr.xarch.XArchListener;
import edu.uci.isr.xarch.XArchParseException;
import edu.uci.isr.xarch.XArchUtils;
import edu.uci.isr.xarch.instance.IXMLLink;
import edu.uci.isr.xarch.instance.XMLLinkImpl;

/**
 * Id Table class. Across multiple XArch's this class keeps track of:
 * <OL>
 * <LI>all Ids and their associates XArch elements
 * <LI>for each Id, all references to (XML Links that reference) the
 * corresponding XArch element.
 * <LI>all open XArch's and their corresponding URL string (if any).
 * </OL>
 * Implementation Note: ID's must minimally be unique per document, however, the
 * table will work if id's are unique across documents as well.
 * 
 * @author Kari Nies
 * @author Eric M. Dashofy
 * @author Scott Hendrickson
 */
public class IdTable implements XArchListener {

	private final IXArchImplementation xArchImplementation;

	/**
	 * Gets the <code>IXArchImplementation</code> used by this
	 * <code>IdTable</code>.
	 * 
	 * @return the <code>IXArchImplementation</code> used by this
	 *         <code>IdTable</code>.
	 */
	public IXArchImplementation getXArchImplementation() {
		return xArchImplementation;
	}

	private final XMLLinkResolver resolver;
	private final IContentStore contentStore;

	public IdTable(IXArchImplementation xArchImplementation, IContentStore contentStore) {
		this.xArchImplementation = xArchImplementation;
		this.contentStore = contentStore;
		this.resolver = new XMLLinkResolver(this);
	}

	public IdTable() {
		this(XArchUtils.getDefaultXArchImplementation(), new URLContentStore());
	}

	/**
	 * XArch document URI to IXArch document map
	 */

	private final BidirectionalMap<URI, IXArch> uriToXArchMap = new BidirectionalHashMap<URI, IXArch>();

	/**
	 * Test whether the XML document corresponding to given URL string has been
	 * parsed into memory and has a corresponding XArch. <it> This
	 * implementation assumes that there is only one valid and unique URL string
	 * for a given XML document. Therefore one URL string must be chosen and
	 * used consistently.</it>
	 * 
	 * @param url
	 *            The URL string to test.
	 * @return whether or not the given URL has been parsed into memory.
	 */
	public boolean urlLoaded(String url) {
		try {
			return uriToXArchMap.containsKey(removeFragment(new URI(url).normalize()));
		}
		catch (URISyntaxException e) {
		}
		return false;
	}

	/**
	 * XArch file event listeners
	 */

	private final ListenerList<XArchFileListener> xArchFileListeners = new ListenerList<XArchFileListener>(XArchFileListener.class);

	public void addXArchFileListener(XArchFileListener l) {
		xArchFileListeners.add(l);
	}

	public void removeXArchFileListener(XArchFileListener l) {
		xArchFileListeners.remove(l);
	}

	protected void fireXArchFileEvent(XArchFileEvent evt) {
		for (XArchFileListener l : xArchFileListeners.getListeners()) {
			l.handleXArchFileEvent(evt);
		}
	}

	/**
	 * IXArch document to ID to IXArchElement within that document map
	 */

	private final Map<IXArch, Map<String, IXArchElement>> xArchToIdToIXArchElementMap = new HashMap<IXArch, Map<String, IXArchElement>>();

	private void addId(IXArch xArch, String sourceId, IXArchElement element) {
		Map<String, IXArchElement> idTable = xArchToIdToIXArchElementMap.get(xArch);
		if (idTable == null) {
			xArchToIdToIXArchElementMap.put(xArch, idTable = new HashMap<String, IXArchElement>());
		}
		idTable.put(sourceId, element);
	}

	private void removeId(IXArch xArch, String sourceId) {
		Map<String, IXArchElement> idTable = xArchToIdToIXArchElementMap.get(xArch);
		if (idTable != null) {
			idTable.remove(sourceId);
			if (idTable.isEmpty()) {
				xArchToIdToIXArchElementMap.remove(xArch);
			}
		}
	}

	/**
	 * URI to id to XMLLinks that point to it. N.B.: a <code>null</code> id
	 * means that the reference is to the URL itself (i.e., without the
	 * "#fragment" part).
	 */

	private final Map<URI, Map<String, Collection<IXMLLink>>> uriToIdToXMLLinkMap = new HashMap<URI, Map<String, Collection<IXMLLink>>>();

	private void addRef(URI uri, String href, IXMLLink xmlLink) throws URISyntaxException {
		try {
			if (href != null && href.length() > 0) {
				uri = XadlUtils.resolve(uri, href).normalize();
				String fragment = uri.getFragment();
				uri = removeFragment(uri);

				Map<String, Collection<IXMLLink>> refTable = uriToIdToXMLLinkMap.get(uri);
				if (refTable == null) {
					uriToIdToXMLLinkMap.put(uri, refTable = new HashMap<String, Collection<IXMLLink>>());
				}
				Collection<IXMLLink> refs = refTable.get(fragment);
				if (refs == null) {
					refTable.put(fragment, refs = new ArrayList<IXMLLink>());
				}
				refs.add(xmlLink);
			}
		}
		catch (Throwable t) {
			System.err.println("Ignoring invalid href: \"" + href + "\" within document " + uri);
		}
	}

	private void addRef(IXMLLink xmlLink) throws URISyntaxException {
		URI uri = uriToXArchMap.getKey(xmlLink.getXArch());
		if (uri != null) {
			addRef(uri, xmlLink.getHref(), xmlLink);
		}
	}

	private void removeRef(URI uri, String href, IXMLLink xmlLink) throws URISyntaxException {
		if (href != null) {
			uri = XadlUtils.resolve(uri, href).normalize();
			String fragment = uri.getFragment();
			uri = removeFragment(uri);

			Map<String, Collection<IXMLLink>> refTable = uriToIdToXMLLinkMap.get(uri);
			if (refTable != null) {
				Collection<IXMLLink> refs = refTable.get(uri);
				if (refs != null) {
					refs.remove(xmlLink);
					if (refs.isEmpty()) {
						refTable.remove(fragment);
						if (refTable.isEmpty()) {
							uriToIdToXMLLinkMap.remove(uri);
						}
					}
				}
			}
		}
	}

	private void removeRef(String href, IXMLLink xmlLink) throws URISyntaxException {
		removeRef(uriToXArchMap.getKey(xmlLink.getXArch()), href, xmlLink);
	}

	private void removeRef(IXMLLink xmlLink) throws URISyntaxException {
		removeRef(xmlLink.getHref(), xmlLink);
	}

	/**
	 * Gets an already open xArch. A URL string must be provided.
	 * 
	 * @param url
	 *            The URL of the xArch to get.
	 * @return the given <CODE>IXArch</CODE> element, or <CODE>null</CODE> if
	 *         none exists.
	 */
	public IXArch getXArch(String url) throws URISyntaxException {
		return uriToXArchMap.get(removeFragment(new URI(url).normalize()));
	}

	public String getURL(IXArch xArch) {
		return uriToXArchMap.getKey(xArch).toString();
	}

	private void addXArch(URI uri, IXArch xArch) throws SAXException, IOException {
		xArch.addXArchListener(this);
		uriToXArchMap.put(uri, xArch);
		updateCache(xArch, uri, ((DOMBased) xArch).getDOMNode(), true);
	}

	public URI removeXArch(IXArch xArch) {
		URI uri = uriToXArchMap.removeValue(xArch);
		if (uri != null) {
			xArch.removeXArchListener(this);
			xArchImplementation.remove(xArch);
			xArchToIdToIXArchElementMap.remove(xArch);
			for (Iterator<Map.Entry<URI, Map<String, Collection<IXMLLink>>>> uriToIdToXMLLinks = uriToIdToXMLLinkMap.entrySet().iterator(); uriToIdToXMLLinks
			        .hasNext();) {
				Map.Entry<URI, Map<String, Collection<IXMLLink>>> uriToIdToXMLLink = uriToIdToXMLLinks.next();
				for (Iterator<Map.Entry<String, Collection<IXMLLink>>> idToXMLLinks = uriToIdToXMLLink.getValue().entrySet().iterator(); idToXMLLinks.hasNext();) {
					Map.Entry<String, Collection<IXMLLink>> idToXMLLink = idToXMLLinks.next();
					for (Iterator<IXMLLink> xmlLinks = idToXMLLink.getValue().iterator(); xmlLinks.hasNext();) {
						IXMLLink xmlLink = xmlLinks.next();
						if (xArch.equals(xmlLink.getXArch())) {
							xmlLinks.remove();
						}
					}
					if (idToXMLLink.getValue().isEmpty()) {
						idToXMLLinks.remove();
					}
				}
				if (uriToIdToXMLLink.getValue().isEmpty()) {
					uriToIdToXMLLinks.remove();
				}
			}
		}
		return uri;
	}

	/**
	 * Removes an xArch from the IdTable class instance.
	 * 
	 * @param xArch
	 *            XArch to remove fromm IdTable.
	 */
	public void forgetXArch(IXArch xArch) {
		URI uri = removeXArch(xArch);
		if (uri != null) {
			fireXArchFileEvent(new XArchFileEvent(XArchFileEvent.XARCH_CLOSED_EVENT, uri.toString()));
		}
	}

	/**
	 * Creates an instance of an empty XArch. A URL string must be provided. For
	 * documents with no fixed URL, a <CODE>urn:</CODE> URL may be used.
	 * 
	 * @param url
	 *            The URL of the xArch to create.
	 * @return the newly created empty XArch.
	 * @throws URISyntaxException
	 */
	public IXArch createXArch(String url) throws URISyntaxException {
		URI uri = new URI(url).normalize();
		IXArch xArch = xArchImplementation.createXArch();

		try {
			addXArch(uri, xArch);
		}
		catch (SAXException se) {
			//This shouldn't happen
			throw new RuntimeException(se.toString());
		}
		catch (IOException ioe) {
			//This shouldn't happen
			throw new RuntimeException(ioe.toString());
		}

		fireXArchFileEvent(new XArchFileEvent(XArchFileEvent.XARCH_CREATED_EVENT, uri.toString()));

		return xArch;
	}

	/**
	 * Clones an XArch (document). A URL string for the clone must be provided.
	 * For documents with no fixed URL, a <CODE>urn:</CODE> URL may be used.
	 * 
	 * @param xArchToClone
	 *            The IXArch object to clone.
	 * @param url
	 *            The URL for the cloned xArch.
	 * @return the newly created cloned XArch.
	 * @throws URISyntaxException
	 */
	public IXArch cloneXArch(IXArch xArchToClone, String url) throws URISyntaxException {
		URI uri = new URI(url).normalize();
		IXArch xArch = xArchImplementation.cloneXArch(xArchToClone);

		try {
			addXArch(uri, xArch);
		}
		catch (SAXException se) {
			//This shouldn't happen
			throw new RuntimeException(se.toString());
		}
		catch (IOException ioe) {
			//This shouldn't happen
			throw new RuntimeException(ioe.toString());
		}

		fireXArchFileEvent(new XArchFileEvent(XArchFileEvent.XARCH_CREATED_EVENT, uri.toString()));

		return xArch;
	}

	/**
	 * Creates and instance of an XArch and associates the XArch with the given
	 * URL string. If an XArch associated with the given URL string already
	 * exists, then that pre-existing XArch is returned. Otherwise the XML
	 * document whose location is specified by the given URL string is parsed
	 * and the corresponding XArch returned. An XArch created by this method may
	 * be referenced by an external XML document. <it> This implementation
	 * assumes that there is only one valid and unique URL string for a given
	 * XML document. Therefore the URL string must be chosen and used
	 * consistently.</it>
	 * 
	 * @param url
	 *            a URL string specifying the location of the XML document on
	 *            which the XArch will be based.
	 * @return an XArch instance based on the specified XML document
	 * @throws IOException
	 * @throws SAXException
	 * @throws URISyntaxException
	 */
	public IXArch parseFromReader(String url, Reader contents) throws SAXException, IOException, URISyntaxException {
		URI uri = removeFragment(new URI(url).normalize());

		IXArch xArch = uriToXArchMap.get(uri);
		if (xArch != null) {
			return xArch;
		}

		try {
			xArch = xArchImplementation.parse(contents);
		}
		catch (XArchParseException xpe) {
			Throwable t = xpe.getCause();
			if (t != null) {
				if (t instanceof SAXException) {
					throw (SAXException) t;
				}
				else if (t instanceof IOException) {
					throw (IOException) t;
				}
			}
			throw new RuntimeException(xpe);
		}

		addXArch(uri, xArch);

		fireXArchFileEvent(new XArchFileEvent(XArchFileEvent.XARCH_OPENED_EVENT, uri.toString()));

		return xArch;
	}

	/**
	 * Creates and instance of an XArch and associates the XArch with the given
	 * URL string. If an XArch associated with the given URL string already
	 * exists, then that pre-existing XArch is returned. Otherwise the XML
	 * document whose location is specified by the given URL string is parsed
	 * and the corresponding XArch returned. An XArch created by this method may
	 * be referenced by an external XML document. <it> This implementation
	 * assumes that there is only one valid and unique URL string for a given
	 * XML document. Therefore the URL string must be chosen and used
	 * consistently.</it>
	 * 
	 * @param url
	 *            a URL string specifying the location of the XML document on
	 *            which the XArch will be based.
	 * @return an XArch instance based on the specified XML document
	 * @throws URISyntaxException
	 */
	public IXArch parseFromURL(String url) throws SAXException, IOException, URISyntaxException {
		URI uri = removeFragment(new URI(url).normalize());

		IXArch xArch = uriToXArchMap.get(uri);
		if (xArch != null) {
			return xArch;
		}

		return parseFromReader(uri.toString(), new InputStreamReader(getContents(uri.toString())));
	}

	/**
	 * Given an <code>id</code> attribute value, returns the first XArch element
	 * found whose <code>id</code> attribute matches that value.
	 * 
	 * @param id
	 *            the <code>id</code> attribute value to search for.
	 * @return the first XArch Element found with the specified <code>id</code>
	 *         attribute value, or <code>null</code> if none are found
	 */
	public IXArchElement getEntity(String id) {
		for (Map<String, IXArchElement> idtable : xArchToIdToIXArchElementMap.values()) {
			IXArchElement element = idtable.get(id);
			if (element != null) {
				return element;
			}
		}
		return null;
	}

	/**
	 * Given an <code>id</code> attribute value, and the xArch object
	 * representing the root of the tree to search, returns the XArch element
	 * whose <code>id</code> attribute matches that value.
	 * 
	 * @param xArch
	 *            The root element of the tree to search
	 * @param id
	 *            the <code>id</code> attribute value to search for.
	 * @return the XArch Element with the specified <code>id</code> attribute
	 *         value. Will return <code>null</code> if the given id is not
	 *         known.
	 */
	public IXArchElement getEntity(IXArch xArch, String id) {
		Map<String, IXArchElement> idTable = xArchToIdToIXArchElementMap.get(xArch);
		if (idTable != null) {
			return idTable.get(id);
		}
		return null;
	}

	/**
	 * Given an <code>id</code> attribute value, returns a collection of XArch
	 * elements where each element references the XArch element with the given
	 * <code>id</code> attribute value. By "references" we mean that the
	 * referencing element is an xml link to the referenced element. If there
	 * are multiple elements with the same ID (in different documents), this
	 * will return an arbitrary one.
	 * 
	 * @param id
	 *            the <code>id</code> attribute value used to search for
	 *            references.
	 * @return a collection of XArch elements that reference the XArch element
	 *         with the given id value. If there are no such elements, an empty
	 *         collection is returned.
	 */
	public Collection<IXMLLink> getReferences(String id) {
		for (Map<String, Collection<IXMLLink>> refsTable : uriToIdToXMLLinkMap.values()) {
			Collection<IXMLLink> refs = refsTable.get(id);
			if (refs != null) {
				return refs;
			}
		}
		return Collections.emptyList();
	}

	/**
	 * Given an <code>id</code> attribute value, returns a collection of XArch
	 * elements where each element references the XArch element with the given
	 * <code>id</code> attribute value. By "references" we mean that the
	 * referencing element is an xml link to the referenced element.
	 * 
	 * @param id
	 *            the <code>id</code> attribute value used to search for
	 *            references.
	 * @return a collection of XArch elements that reference the XArch element
	 *         with the given id value. If there are no such elements, an empty
	 *         collection is returned.
	 */
	public Collection<IXMLLink> getReferences(IXArch xArch, String id) {

		Map<String, Collection<IXMLLink>> refsTable = uriToIdToXMLLinkMap.get(uriToXArchMap.getKey(xArch));
		if (refsTable != null) {
			Collection<IXMLLink> refs = refsTable.get(id);
			if (refs != null) {
				return refs;
			}
		}
		return Collections.emptyList();
	}

	/**
	 * Traverses the specified XArch node and all children updating the ID and
	 * XMLLink caches.
	 */
	private void updateCache(IXArch xArch, URI uri, Node n, boolean adding) {
		if (n instanceof Element) {

			// update id cache

			String idValue = IdTable.getId((Element) n);
			if (idValue != null) {
				Object o = IdTable.makeWrapper(xArch, (Element) n);
				if (o != null) {
					if (adding) {
						addId(xArch, idValue, (IXArchElement) o);
					}
					else {
						removeId(xArch, idValue);
					}
				}
				else {
					//We probably don't have the appropriate xarchlibs installed.
					//This is not a fatal error.

					//throw new RuntimeException
					//("Failed to make wrapper while caching Ids.");
				}
			}

			// update ref cache

			if (DOMUtils.hasXSIType((Element) n, "http://www.ics.uci.edu/pub/arch/xArch/instance.xsd", "XMLLink")) {
				IXMLLink xmlLink = new XMLLinkImpl((Element) n);
				xmlLink.setXArch(xArch);
				try {
					if (adding) {
						addRef(uri, xmlLink.getHref(), xmlLink);
					}
					else {
						removeRef(uri, xmlLink.getHref(), xmlLink);
					}
				}
				catch (URISyntaxException e) {
					// probably not a valid href, just ignore it until it changes
				}
			}
		}

		// process children
		NodeList nl = n.getChildNodes();
		for (int i = 0, size = nl.getLength(); i < size; i++) {
			updateCache(xArch, uri, nl.item(i), adding);
		}
	}

	/**
	 * Updates the ID and XMLLink caches based on changes to the DOM model.
	 * 
	 * @see edu.uci.isr.xarch.XArchListener#handleXArchEvent(edu.uci.isr.xarch.XArchEvent)
	 */
	public void handleXArchEvent(XArchEvent evt) {
		if (!evt.getIsAttached()) {
			return;
		}

		IXArchElement src = evt.getSource();
		IXArch xArch = src.getXArch();
		int srcType = evt.getSourceType();
		int eventType = evt.getEventType();
		String targetName = evt.getTargetName();

		if (srcType == XArchEvent.ATTRIBUTE_CHANGED) {

			// handle change to id attribute
			if (targetName.equals("id")) {
				String idValue = (String) evt.getTarget();
				if (eventType == XArchEvent.SET_EVENT) {
					addId(xArch, idValue, src);
				}
				else if (evt.getEventType() == XArchEvent.CLEAR_EVENT) {
					removeId(xArch, idValue);
				}
			}

			// handle change to href attribute
			else if (targetName.equals("href")) {
				if (src instanceof IXMLLink) {
					try {
						if (eventType == XArchEvent.SET_EVENT) {
							addRef((IXMLLink) src);
						}
						else if (evt.getEventType() == XArchEvent.CLEAR_EVENT) {
							removeRef((String) evt.getTarget(), (IXMLLink) src);
						}
					}
					catch (URISyntaxException e) {
						// probably a bad href
					}
				}
			}
		}
		else if (srcType == XArchEvent.ELEMENT_CHANGED && (eventType == XArchEvent.ADD_EVENT || eventType == XArchEvent.SET_EVENT)) {

			// scans newly added elements, adding ids and refs
			IXArchElement xArchElt = (IXArchElement) evt.getTarget();

			// N.B.: a set event will be fired even if the value is set to null, so we check for that here
			if (xArchElt != null) {
				if (!(xArchElt instanceof DOMBased)) {
					throw new IllegalArgumentException("Cannot handle non-DOM-based xArch entities.");
				}
				updateCache(xArch, uriToXArchMap.getKey(xArch), ((DOMBased) xArchElt).getDOMNode(), true);
			}
		}
		else if (srcType == XArchEvent.ELEMENT_CHANGED && (eventType == XArchEvent.REMOVE_EVENT || eventType == XArchEvent.CLEAR_EVENT)) {

			// scans recently removed elements, removing ids and refs
			IXArchElement xArchElt = (IXArchElement) evt.getTarget();

			// N.B.: a clear event will be fired even if the value is already null, so we check for that here
			if (xArchElt != null) {
				if (!(xArchElt instanceof DOMBased)) {
					throw new IllegalArgumentException("Cannot handle non-DOM-based xArch entities.");
				}
				updateCache(xArch, uriToXArchMap.getKey(xArch), ((DOMBased) xArchElt).getDOMNode(), false);
			}
		}
	}

	private URI removeFragment(URI uri) throws URISyntaxException {
		String fragment = uri.getFragment();
		if (fragment == null) {
			return uri;
		}
		return new URI(uri.getScheme(), uri.getSchemeSpecificPart(), null);
	}

	/**
	 * Given a URL, returns an open InputStream.
	 * 
	 * @param URL
	 *            URL string for an XML document.
	 */
	private InputStream getContents(String url) throws MalformedURLException, IOException {
		if (contentStore.canProcessURI(url)) {
			return new ByteArrayInputStream(contentStore.retrieveContents(url));
		}
		throw new IOException("Unable to retrieve content for " + url);
	}

	/*
	 * Methods below should probably be in a utility class, but alas, there's no
	 * point in moving them now.
	 */

	public IXArchElement getParent(IXArchElement elt) {
		if (!(elt instanceof DOMBased)) {
			throw new IllegalArgumentException("This function does not work on non-DOM-based entities.");
		}
		DOMBased db = (DOMBased) elt;
		Node parentNode = db.getDOMNode().getParentNode();
		if (parentNode == null) {
			//System.out.println("Parent was null of : " + elt);
			return null;
		}
		if (!(parentNode instanceof Element)) {
			if (parentNode instanceof Document) {
				//System.out.println("Parent was document of : " + elt);
				return null;
			}

			throw new RuntimeException("Parent node is a " + parentNode.getClass() + ", not an element?!?");
		}
		IXArchElement p = (IXArchElement) IdTable.makeWrapper(elt.getXArch(), (Element) parentNode);
		//if(p ==  null) System.out.println("MakeWrapper failed on : " + elt);
		return p;
	}

	/**
	 * For internal use only. Directly copied from
	 * {@link XArchImpl#makeWrapper(Element)}
	 */
	private static Object makeWrapper_(Element elt) {
		QName typeName = XArchUtils.getXSIType(elt);
		if (typeName == null) {
			return null;
		}
		else {
			try {
				String packageTitle = XArchUtils.getPackageTitle(typeName.getNamespaceURI());
				String packageName = XArchUtils.getPackageName(packageTitle);
				String implName = XArchUtils.getImplName(packageName, typeName.getName());
				Class c = Class.forName(implName);
				java.lang.reflect.Constructor con = c.getConstructor(new Class[] { Element.class });
				Object o = con.newInstance(new Object[] { elt });
				return o;
			}
			catch (Exception e) {
				//Lots of bad things could happen, but this
				//is OK, because this is best-effort anyway.
			}
			return null;
		}
	}

	public static Object makeWrapper(IXArch xArch, Element elt) {
		try {
			if (elt.equals(((DOMBased) xArch).getDOMNode())) {
				return xArch;
			}
		}
		catch (Exception e) {
		}

		Object o = IdTable.makeWrapper_(elt);

		if (o instanceof IXArchElement) {
			((IXArchElement) o).setXArch(xArch);
		}

		return o;
	}

	public static String getId(Element n) {

		String namespaceURI = n.getNamespaceURI();
		String idValue = DOMUtils.getAttributeValue(n, namespaceURI, "id");
		if (idValue != null) {
			return idValue;
		}

		NamedNodeMap nnm = n.getAttributes();
		int len = nnm.getLength();
		for (int i = 0; i < len; i++) {
			Attr child = (Attr) nnm.item(i);
			//System.out.println(child.getName());
			if (child.getName().equals("id")) {
				return child.getValue();
			}
			if (child.getName().endsWith(":id")) {
				return child.getValue();
			}
		}

		return null;
	}

	/**
	 * Clone function that doesn't break IDs. Prefixes all IDs with "Copy of"
	 * like the Windows file system; i.e. foo -> Copy of foo (or Copy (2) of
	 * foo, etc.).
	 * 
	 * @param element
	 *            <CODE>IXArchElement</CODE> to clone.
	 * @param depth
	 *            Depth at which to clone. See IXArchElement documentation for
	 *            more info on depth.
	 */
	public IXArchElement cloneElement(IXArchElement elt, int depth) {
		if (!(elt instanceof DOMBased)) {
			throw new IllegalArgumentException("Parameter elt in function cloneElement() must be DOM-based.");
		}
		//First, clone the element.
		IXArchElement clonedElt = elt.cloneElement(depth);
		//Now, fix up all the IDs to clean up duplicates:
		changeIdsToCopies(elt.getXArch(), ((DOMBased) clonedElt).getDOMNode());
		/*
		 * try{ cacheRefs(elt.getXArch(), ((DOMBased)elt).getDOMNode()); }
		 * catch(IOException ioe){ throw new RuntimeException(ioe); }
		 * catch(SAXException se){ throw new RuntimeException(se); }
		 */
		return clonedElt;
	}

	private void changeIdsToCopies(IXArch xArch, Node n) {
		if (n instanceof Element) {
			// check if n has an "id" attibute
			//System.out.println("Caching ID for: " + n);
			//String namespaceURI = 
			//    DOMUtils.getXSIType((Element)n).getNamespaceURI();
			String namespaceURI = ((Element) n).getNamespaceURI();
			String idValue = DOMUtils.getAttributeValue(n, namespaceURI, "id");
			if (idValue == null) {
				idValue = IdTable.getId((Element) n);
			}

			if (idValue != null) {
				boolean isGood = false;
				do {
					idValue = getCopyIdValue(idValue);
					if (getEntity(xArch, idValue) == null) {
						isGood = true;
					}
				} while (!isGood);
				setId(n, idValue);
			}
		}
		NodeList nl = n.getChildNodes();
		int size = nl.getLength();
		for (int i = 0; i < size; i++) {
			changeIdsToCopies(xArch, nl.item(i));
		}
	}

	private String getCopyIdValue(String idValue) {
		if (!idValue.startsWith("Copy")) {
			return "Copy of " + idValue;
		}
		if (idValue.startsWith("Copy of ")) {
			return "Copy (2) of " + idValue.substring(8);
		}
		if (idValue.startsWith("Copy (")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 6; i < idValue.length(); i++) {
				if (idValue.charAt(i) == ')') {
					break;
				}
				else {
					sb.append(idValue.charAt(i));
				}
			}

			String numString = sb.toString();
			int num;
			try {
				num = Integer.parseInt(numString);
			}
			catch (NumberFormatException nfe) {
				return "Copy of " + idValue;
			}

			String currentPrefix = "Copy (" + numString + ") of ";
			if (!idValue.startsWith(currentPrefix)) {
				return "Copy of " + idValue;
			}
			int newNum = num + 1;
			String newPrefix = "Copy (" + newNum + ") of ";
			String s = idValue.substring(currentPrefix.length());
			return newPrefix + s;
		}
		return "Copy of " + idValue;
	}

	private void setId(Node n, String id) {
		NamedNodeMap nnm = n.getAttributes();
		int len = nnm.getLength();
		for (int i = 0; i < len; i++) {
			Attr child = (Attr) nnm.item(i);
			//System.out.println(child.getName());
			if (child.getName().equals("id")) {
				child.setValue(id);
				return;
			}
			if (child.getName().endsWith(":id")) {
				child.setValue(id);
				return;
			}
		}
	}

	public void writeToURL(String url, String contents) throws IOException {
		try {
			if (contentStore.canProcessURI(url)) {
				contentStore.storeContents(url, contents.getBytes());
				contentStore.contentsStored(url);
				return;
			}
		}
		catch (Throwable t) {
			throw new IOException("Unable to store content to " + url);
		}
		throw new IOException("Unable to store content to " + url);
	}
}
