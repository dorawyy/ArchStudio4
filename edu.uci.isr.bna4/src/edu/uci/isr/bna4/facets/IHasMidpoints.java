package edu.uci.isr.bna4.facets;

import org.eclipse.draw2d.geometry.Point;

import edu.uci.isr.bna4.IThing;

public interface IHasMidpoints extends IThing {
	public static final String MIDPOINTS_PROPERTY_NAME = "midpoints";

	public Point[] getMidpoints();
}
