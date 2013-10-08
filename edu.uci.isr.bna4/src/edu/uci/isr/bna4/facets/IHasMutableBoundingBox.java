package edu.uci.isr.bna4.facets;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public interface IHasMutableBoundingBox extends IHasBoundingBox, IRelativeMovable {

	public static final String USER_MAY_RESIZE = "userMayResize";

	public static final String MINIMUM_BOUNDING_BOX_SIZE_PROPERTY_NAME = "minBoundingBoxSize";

	public void setBoundingBox(Rectangle worldBoundingBox);

	public Point getMinBoundingBoxSize();

	public void setMinBoundingBoxSize(Point minSize);
}
