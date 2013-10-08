package edu.uci.isr.bna4.things.shapes;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;

public class SplineThingPeer extends AbstractThingPeer<SplineThing> {

	public SplineThingPeer(SplineThing t) {
		super(t, SplineThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		Point[] points = BNAUtils.worldToLocal(view.getCoordinateMapper(), t.getPoints());
		if (points.length == 2) {
			int x1 = points[0].x;
			int y1 = points[0].y;
			int x2 = points[1].x;
			int y2 = points[1].y;
			int cx1 = clip.x;
			int cy1 = clip.y;
			int cx2 = cx1 + clip.width;
			int cy2 = cy1 + clip.height;
			if (x1 < cx1 && x2 < cx1 || x1 > cx2 && x2 > cx2) {
				return;
			}
			if (y1 < cy1 && y2 < cy1 || y1 > cy2 && y2 > cy2) {
				return;
			}

			g.setForegroundColor(res.getColor(t.getColor(), SWT.COLOR_BLACK));
			g.setLineWidth(t.getLineWidth());
			g.setLineStyle(t.getLineStyle());

			g.drawLine(x1, y1, x2, y2);
		}
		else if (points.length > 2) {
			int[] localXYArray = BNAUtils.toXYArray(points);

			g.setForegroundColor(res.getColor(t.getColor(), SWT.COLOR_BLACK));
			g.setLineWidth(t.getLineWidth());
			g.setLineStyle(t.getLineStyle());

			g.drawPolyline(localXYArray);
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
