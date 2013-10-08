package edu.uci.isr.bna4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Display;

import edu.uci.isr.bna4.constants.IFontConstants;

public final class ResourceUtils {

	private final Device d;

	private ResourceUtils(Device d) {
		this.d = d;
	}

	public static ResourceUtils resourceUtilsFor(Display d) {
		{
			ResourceUtils res = (ResourceUtils) d.getData(ResourceUtils.class.getName());
			if (res != null) {
				return res;
			}
		}
		final ResourceUtils res = new ResourceUtils(d);
		d.disposeExec(new Runnable() {
			public void run() {
				res.dispose();
			}
		});
		d.setData(ResourceUtils.class.getName(), res);
		return res;
	}

	public static ResourceUtils resourceUtilsFor(Device d) {
		return new ResourceUtils(d);
	}

	private final Collection<Resource> toDispose = new ArrayList<Resource>();

	public void dispose() {
		for (Iterator<Resource> i = toDispose.iterator(); i.hasNext();) {
			Resource r = i.next();
			if (!r.isDisposed()) {
				r.dispose();
			}
			i.remove();
		}
		colors.clear();
		fonts.clear();
	}

	public Device getDevice() {
		return d;
	}

	private final Map<RGB, Color> colors = new HashMap<RGB, Color>();

	public Color getColor(RGB rgb, int defaultSystemColor) {
		if (rgb == null) {
			return d.getSystemColor(defaultSystemColor);
		}
		else {
			Color c = colors.get(rgb);
			if (c == null) {
				c = new Color(d, rgb);
				toDispose.add(c);
				colors.put(rgb, c);
			}
			return c;
		}
	}

	private final Map<FontData, Font> fonts = new HashMap<FontData, Font>();

	public Font getFont(String faceName, int height, int style) {
		if (faceName.equals(IFontConstants.DEFAULT_FONT_NAME)) {
			faceName = d.getSystemFont().getFontData()[0].getName();
		}

		FontData fd = new FontData(faceName, height, style);
		Font f = fonts.get(fd);
		if (f == null) {
			f = new Font(d, fd);
			toDispose.add(f);
			fonts.put(fd, f);
		}
		return f;
	}

	private final Map<ImageData, Image> images = new HashMap<ImageData, Image>();

	public Image getImage(ImageData imageData) {
		Image image = images.get(imageData);
		if (image == null) {
			image = new Image(d, imageData);
			toDispose.add(image);
			images.put(imageData, image);
		}
		return image;
	}
}
