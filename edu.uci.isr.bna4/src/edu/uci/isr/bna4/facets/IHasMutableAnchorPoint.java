package edu.uci.isr.bna4.facets;

import org.eclipse.draw2d.geometry.Point;

public interface IHasMutableAnchorPoint extends IHasAnchorPoint {

	public void setAnchorPoint(Point newAnchorPoint);
}
