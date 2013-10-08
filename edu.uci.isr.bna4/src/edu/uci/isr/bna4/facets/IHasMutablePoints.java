package edu.uci.isr.bna4.facets;

import org.eclipse.draw2d.geometry.Point;

public interface IHasMutablePoints extends IHasPoints {

	public void setPoints(Point[] points);
}
