package edu.uci.isr.bna4.facets;

import org.eclipse.draw2d.geometry.Point;

public interface IHasSecondaryAnchorPoint extends IHasAnchorPoint {

	public static final String SECONDARY_ANCHOR_POINT_PROPERTY_NAME = "secondaryAnchorPoint";

	public Point getSecondaryAnchorPoint();
}
