package edu.uci.isr.xarchflat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLConnection;

import edu.uci.isr.sysutils.SystemUtils;

public class URLContentStore implements IContentStore {

	public boolean canProcessURI(String uri) {
		try {
			// toURL forces a check for a handler
			return new URI(uri).toURL() != null;
		}
		catch (Exception e) {
		}
		return false;
	}

	public byte[] retrieveContents(String uri) throws IOException {
		try {
			return SystemUtils.blt(new URI(uri).toURL().openConnection().getInputStream());
		}
		catch (IOException e) {
			throw e;
		}
		catch (Throwable t) {
			throw new IOException(t.getMessage());
		}
	}

	public void storeContents(String uriString, byte[] contents) throws IOException {
		OutputStream os = null;

		try {
			URI uri = new URI(uriString);

			// FileURLConnection does not handle file output
			// so we handle this manually
			if ("file".equalsIgnoreCase(uri.getScheme())) {
				os = new FileOutputStream(new File(uri), false);
			}
			else {
				URLConnection con = uri.toURL().openConnection();
				con.setDoOutput(true);
				os = con.getOutputStream();
			}

			os.write(contents);
			os.flush();
			os.close();
		}
		catch (IOException e) {
			throw e;
		}
		catch (Throwable t) {
			throw new IOException(t.getMessage());
		}
		finally {
			if (os != null) {
				try {
					os.close();
				}
				catch (Exception e) {
				}
				os = null;
			}
		}
	}

	public void contentsStored(String uri) {
		// do nothing
	}
}
