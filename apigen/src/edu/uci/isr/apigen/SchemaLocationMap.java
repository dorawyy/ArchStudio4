package edu.uci.isr.apigen;

import java.io.File;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SchemaLocationMap implements java.io.Serializable {

	protected Map<String, Object> locationMap = new Hashtable();
	protected List<String> remapLocations = new ArrayList<String>();
	protected boolean locationsAreCanonical = false;
	protected Hashtable packageMap = new Hashtable();
	static final long serialVersionUID = 2786542185453830569L;
	// An " " separated list of namespaces that should not be fixed during serialization
	protected String noPrefix = "";

	public SchemaLocationMap() {
	}

	public void addLocation(String schemaURI, URL location) {
		locationMap.put(schemaURI, location);
	}

	public void addLocation(String schemaURI, File location) {
		locationMap.put(schemaURI, location);
	}

	public void addRemap(String from, String to) {
		if (remapLocations == null) {
			/*
			 * We need this because previously serialized versions will have
			 * this value initially set to null.
			 */
			remapLocations = new ArrayList<String>();
		}
		remapLocations.add(from);
		remapLocations.add(to);
	}

	protected void checkForOlderVersion() {
		// If an old version object is deserialized, it might not have this field 
		if (packageMap == null) {
			packageMap = new Hashtable();
		}
	}

	public void addPackageName(String schemaURI, String packageName) {
		if (packageName != null) {
			checkForOlderVersion();
			packageMap.put(schemaURI, packageName);
		}
	}

	public void setNoPrefix(String noPrefix) {
		this.noPrefix = noPrefix;
	}

	public String getNoPrefix() {
		return noPrefix;
	}

	public boolean hasLocation(String schemaURI) {
		return locationMap.get(schemaURI) == null;
	}

	public Enumeration keys() {
		return Collections.enumeration(locationMap.keySet());
	}

	public Object getValue(String schemaURI) {
		return locationMap.get(schemaURI);
	}

	public String getPackageName(String schemaURI) {
		checkForOlderVersion();
		return (String) packageMap.get(schemaURI);
	}

	public Reader openLocation(String schemaURI) {
		if (locationMap.get(schemaURI) == null) {
			try {
				URL url = new URL(schemaURI);
				return ApiGenUtils.openURL(url);
			}
			catch (MalformedURLException e) {
				return null;
			}
		}
		else {
			Object o = remap(locationMap.get(schemaURI));
			if (o instanceof URL) {
				return ApiGenUtils.openURL((URL) o);
			}
			else if (o instanceof File) {
				return ApiGenUtils.openFile((File) o);
			}
			else {
				return null;
			}
		}
	}

	private Object remap(Object o) {
		if (remapLocations == null) {
			return o;
		}
		for (int i = 0; i < remapLocations.size();) {
			String from = remapLocations.get(i++);
			String to = remapLocations.get(i++);
			if (o.toString().startsWith(from)) {
				o = new File(to + o.toString().substring(from.length()));
			}
		}
		return o;
	}

	public void remove(String token) {
		locationMap.remove(token);
		checkForOlderVersion();
		packageMap.remove(token);
	}

	public boolean locationsAreCanonical() {
		return locationsAreCanonical;
	}

	public void setLocationsAreCanonical(boolean locationsAreCanonical) {
		this.locationsAreCanonical = locationsAreCanonical;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Map<String, String> nameURIMap = new HashMap();
		for (String uri : locationMap.keySet()) {
			int s = uri.lastIndexOf('/') + 1;
			int e = uri.lastIndexOf('.');
			if (e <= s) {
				e = uri.length();
			}
			String name = uri.substring(s, e);
			nameURIMap.put(name, uri);
		}
		List<String> names = new ArrayList<String>(nameURIMap.keySet());
		Collections.sort(names);
		for (String name : names) {
			String uri = nameURIMap.get(name);
			String url = locationMap.get(uri).toString();
			sb.append("			<schema").append(System.getProperty("line.separator"));
			sb.append("			 name=\"" + name + "\"").append(System.getProperty("line.separator"));
			sb.append("			 schemaLocalCopy=\"res/schema/" + name + ".xsd\"").append(System.getProperty("line.separator"));
			sb.append("			 schemaURI=\"" + uri + "\"").append(System.getProperty("line.separator"));
			sb.append("			 schemaURL=\"" + url + "\"></schema>").append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
}
