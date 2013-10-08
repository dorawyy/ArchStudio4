package edu.uci.isr.bna4.facets;

import org.eclipse.swt.graphics.ImageData;

public interface IHasImage {

	public static final String IMAGE_PATH_PROPERTY_NAME = "imagePath";
	public static final String IMAGE_DATA_PROPERTY_NAME = "imageData";

	/**
	 * @deprecated Use {@link #getImageData()} instead.
	 */
	@Deprecated
	public String getImagePath();

	public ImageData getImageData();
}
