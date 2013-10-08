package edu.uci.isr.bna4.things.borders;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;

public class PolygonBorderThingPeer extends AbstractThingPeer<PolygonBorderThing> {

	public PolygonBorderThingPeer(PolygonBorderThing t) {
		super(t, PolygonBorderThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb)) {
			return;
		}

		Point[] points = t.getPoints();
		int[] localXYArray = BNAUtils.toXYArray(points);
		BNAUtils.convertWorldToLocal(view.getCoordinateMapper(), localXYArray);

		g.setForegroundColor(res.getColor(t.getColor(), SWT.COLOR_GRAY));
		g.setLineStyle(t.getLineStyle());
		g.setLineWidth(t.getLineWidth());

		g.drawPolyline(localXYArray);
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
