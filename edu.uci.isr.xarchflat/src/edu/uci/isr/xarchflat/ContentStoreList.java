package edu.uci.isr.xarchflat;

import java.io.IOException;

import edu.uci.isr.sysutils.ListenerList;

public class ContentStoreList implements IContentStore {

	ListenerList<IContentStore> contentStores = new ListenerList<IContentStore>(IContentStore.class);

	public boolean addContentStore(IContentStore contentStore) {
		return contentStores.add(contentStore);
	}

	public boolean removeContentStore(Object contentStore) {
		return contentStores.remove(contentStore);
	}

	public boolean canProcessURI(String uri) {
		for (IContentStore c : contentStores.getListeners()) {
			if (c.canProcessURI(uri)) {
				return true;
			}
		}
		return false;
	}

	public byte[] retrieveContents(String uri) throws IOException {
		IOException e1 = null;
		for (IContentStore c : contentStores.getListeners()) {
			if (c.canProcessURI(uri)) {
				try {
					return c.retrieveContents(uri);
				}
				catch (IOException e) {
					if (e1 == null) {
						e1 = e;
					}
				}
				catch (Throwable t) {
					if (e1 == null) {
						e1 = new IOException(t.getMessage());
					}
				}
			}
		}
		throw e1 != null ? e1 : new IOException("Cannot retrieve " + uri);
	}

	public void storeContents(String uri, byte[] contents) throws IOException {
		IOException e1 = null;
		for (IContentStore c : contentStores.getListeners()) {
			if (c.canProcessURI(uri)) {
				try {
					c.storeContents(uri, contents);
					return;
				}
				catch (IOException e) {
					if (e1 == null) {
						e1 = e;
					}
				}
				catch (Throwable t) {
					if (e1 == null) {
						e1 = new IOException(t.getMessage());
					}
				}
			}
		}
		throw e1 != null ? e1 : new IOException("Cannot store " + uri);
	}

	public void contentsStored(String uri) {
		for (IContentStore c : contentStores.getListeners()) {
			try {
				c.contentsStored(uri);
			}
			catch (Throwable t) {
				// do nothing
			}
		}
	}
}
