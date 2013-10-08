package edu.uci.isr.myx2.eclipse.extension.internal;

import java.net.URL;

import edu.uci.isr.myx2.eclipse.extension.IExtensionPointLocation;
import edu.uci.isr.myx2.eclipse.extension.MyxBrickExtensionUtils;

public abstract class ExtensionPointLocation implements IExtensionPointLocation {

	protected final URL pluginUrl;

	public ExtensionPointLocation(URL pluginUrl) {
		this.pluginUrl = pluginUrl;
	}

	public URL getPluginUrl() {
		return pluginUrl;
	}

	public String getSymbolicName() {
		return MyxBrickExtensionUtils.getPluginSymbolicName(pluginUrl);
	}
}
