package edu.uci.isr.bna4.things.glass;

import java.awt.Polygon;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;

public class PolygonGlassThingPeer extends AbstractThingPeer<PolygonGlassThing> {

	public PolygonGlassThingPeer(PolygonGlassThing t) {
		super(t, PolygonGlassThing.class);
	}

	@Override
	public void draw(IBNAView view, final Graphics g, Rectangle clip, ResourceUtils res) {
		if (!t.isSelected()) {
			return;
		}

		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb)) {
			return;
		}

		int offset = t.getRotatingOffset();
		Point[] points = t.getPoints();
		final int[] localXYArray = BNAUtils.toXYArray(points);
		BNAUtils.convertWorldToLocal(view.getCoordinateMapper(), localXYArray);

		BNAUtils.drawMarquee(g, res, offset, true, new Runnable() {

			public void run() {
				g.drawPolygon(localXYArray);
			}
		});
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		if (BNAUtils.isWithin(t.getBoundingBox(), worldX, worldY)) {
			Point[] ps = t.getPoints();
			int[] xpoints = new int[ps.length];
			int[] ypoints = new int[ps.length];
			for (int i = 0; i < ps.length; i++) {
				Point p = ps[i];
				xpoints[i] = p.x;
				ypoints[i] = p.y;
			}

			return new Polygon(xpoints, ypoints, ps.length).contains(worldX, worldY);
		}
		return false;
	}
}
