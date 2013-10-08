package edu.uci.isr.bna4.facets;

import org.eclipse.draw2d.geometry.Point;

import edu.uci.isr.bna4.IThing;

public interface IHasPoints extends IThing {

	public static final String POINTS_PROPERTY_NAME = "points";

	public Point[] getPoints();
}
