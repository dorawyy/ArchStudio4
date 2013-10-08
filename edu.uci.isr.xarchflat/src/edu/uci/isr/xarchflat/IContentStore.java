package edu.uci.isr.xarchflat;

import java.io.IOException;

/**
 * Provides a generic way for URI-based content to be retrieved and stored
 * across process boundaries. Implementors should generally return
 * <code>null</code> or <code>false</code> if a particular URI cannot be
 * processed, and throw an {@link IOException} when processing a URI has failed.
 */
public interface IContentStore {

	/**
	 * Indicates whether this content provider can process the specified URI.
	 * 
	 * @param uri
	 *            in question.
	 * @return <code>true</code> if the URI can be processed, <code>false</code>
	 *         otherwise.
	 */
	boolean canProcessURI(String uri);

	/**
	 * Retrieves the contents of an {@link #canProcessURI(String) appropriate}
	 * URI.
	 * 
	 * @param uri
	 *            the URI of the contents to retrieve.
	 * @return a copy of the contents retrieved
	 * @throws IOException
	 *             if an error occurred while processing the URI
	 */
	byte[] retrieveContents(String uri) throws IOException;

	/**
	 * Stores the specified contents to an {@link #canProcessURI(String)
	 * appropriate} URI.
	 * 
	 * @param uri
	 *            the URI of the contents to store
	 * @param contents
	 *            the contents to store
	 * @throws IOException
	 *             if an error occurred while processing the URI
	 */
	void storeContents(String uri, byte[] contents) throws IOException;

	/**
	 * Notifies the content store that the specified contents have been stored,
	 * possibly by another content store, even if the URI is <b>not</b>
	 * {@link #canProcessURI(String) appropriate} for this particular content
	 * store.
	 * 
	 * @param uri
	 *            the URI of the contents to store
	 */
	void contentsStored(String uri);
}
