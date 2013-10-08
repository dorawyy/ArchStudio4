package edu.uci.isr.bna4.things.glass;

import java.awt.geom.Line2D;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ICoordinateMapper;
import edu.uci.isr.bna4.ResourceUtils;

public class SplineGlassThingPeer extends AbstractThingPeer<SplineGlassThing> {

	private final static int MAX_DISTANCE = 5;

	public SplineGlassThingPeer(SplineGlassThing t) {
		super(t, SplineGlassThing.class);
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

		Point[] points = t.getPoints();
		final int[] localXYArray = BNAUtils.toXYArray(points);
		BNAUtils.convertWorldToLocal(view.getCoordinateMapper(), localXYArray);
		int offset = t.getRotatingOffset();

		BNAUtils.drawMarquee(g, res, offset, true, new Runnable() {

			public void run() {
				g.drawPolyline(localXYArray);
			};
		});
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		ICoordinateMapper cm = view.getCoordinateMapper();
		int localX = cm.worldXtoLocalX(worldX);
		int localY = cm.worldYtoLocalY(worldY);

		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		lbb.expand(MAX_DISTANCE, MAX_DISTANCE);
		if (!lbb.contains(localX, localY)) {
			return false;
		}

		Point[] points = t.getPoints();
		final int[] localXYArray = BNAUtils.toXYArray(points);
		BNAUtils.convertWorldToLocal(view.getCoordinateMapper(), localXYArray);
		for (int i = 2; i < localXYArray.length; i += 2) {
			int dist = Math.abs(BNAUtils
			        .round(Line2D.ptSegDist(localXYArray[i - 2], localXYArray[i - 1], localXYArray[i], localXYArray[i + 1], localX, localY)));
			if (dist <= MAX_DISTANCE) {
				return true;
			}
		}

		return false;
	}

}
