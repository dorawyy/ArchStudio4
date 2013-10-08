package edu.uci.isr.bna4.things.labels;

import org.eclipse.swt.graphics.ImageData;

import edu.uci.isr.bna4.IThingListener;
import edu.uci.isr.bna4.ThingEvent;
import edu.uci.isr.bna4.facets.IHasImage;
import edu.uci.isr.bna4.facets.IHasMutableHorizontalAlignment;
import edu.uci.isr.bna4.facets.IHasMutableImage;
import edu.uci.isr.bna4.facets.IHasMutableScaled;
import edu.uci.isr.bna4.facets.IHasMutableVerticalAlignment;
import edu.uci.isr.bna4.things.essence.RectangleEssenceThing;
import edu.uci.isr.widgets.swt.constants.HorizontalAlignment;
import edu.uci.isr.widgets.swt.constants.VerticalAlignment;

public class BoxedImageThing extends RectangleEssenceThing implements IHasMutableHorizontalAlignment, IHasMutableVerticalAlignment, IHasMutableImage,
        IHasMutableScaled {

	public BoxedImageThing() {
		this(null);
	}

	public BoxedImageThing(String id) {
		super(id);
		this.addThingListener(new IThingListener() {
			public void thingChanged(ThingEvent thingEvent) {
				if (IHasImage.IMAGE_PATH_PROPERTY_NAME.equals(thingEvent.getPropertyName())) {
					String imagePath = getImagePath();
					ImageData imageData = null;
					if (imagePath != null) {
						try {
							imageData = new ImageData(imagePath);
						}
						catch (Exception e) {
						}
					}
					setImageData(imageData);
				}
			}
		});
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setImagePath(null);
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		setVerticalAlignment(VerticalAlignment.MIDDLE);
		setScaled(false);
	}

	public HorizontalAlignment getHorizontalAlignment() {
		return (HorizontalAlignment) getProperty(HORIZONTAL_ALIGNMENT_PROPERTY_NAME);
	}

	public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
		setProperty(HORIZONTAL_ALIGNMENT_PROPERTY_NAME, horizontalAlignment);
	}

	public VerticalAlignment getVerticalAlignment() {
		return (VerticalAlignment) getProperty(VERTICAL_ALIGNMENT_PROPERTY_NAME);
	}

	public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
		setProperty(VERTICAL_ALIGNMENT_PROPERTY_NAME, verticalAlignment);
	}

	@Deprecated
	public String getImagePath() {
		return (String) getProperty(IMAGE_PATH_PROPERTY_NAME);
	}

	@Deprecated
	public void setImagePath(String s) {
		setProperty(IMAGE_PATH_PROPERTY_NAME, s);
	}

	public ImageData getImageData() {
		return getProperty(IHasImage.IMAGE_DATA_PROPERTY_NAME);
	}

	public void setImageData(ImageData imageData) {
		setProperty(IHasImage.IMAGE_DATA_PROPERTY_NAME, imageData);
	}

	public boolean isScaled() {
		return getProperty(SCALED_PROPERTY_NAME);
	}

	public void setScaled(boolean scaled) {
		setProperty(SCALED_PROPERTY_NAME, scaled);
	}
}
