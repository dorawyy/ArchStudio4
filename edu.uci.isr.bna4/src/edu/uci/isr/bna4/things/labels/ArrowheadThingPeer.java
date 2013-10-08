package edu.uci.isr.bna4.things.labels;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.ArrowheadUtils;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ICoordinateMapper;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.bna4.constants.ArrowheadShape;

public class ArrowheadThingPeer extends AbstractThingPeer<ArrowheadThing> {

	public ArrowheadThingPeer(ArrowheadThing t) {
		super(t, ArrowheadThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {

		ICoordinateMapper cm = view.getCoordinateMapper();
		Point wp1 = t.getAnchorPoint();
		Point lp1 = BNAUtils.worldToLocal(cm, wp1);
		Point wp2 = t.getSecondaryAnchorPoint();
		Point lp2 = BNAUtils.worldToLocal(cm, wp2);

		if (!clip.contains(lp1) && clip.contains(lp2)) {
			return;
		}

		ArrowheadShape arrowheadShape = t.getArrowheadShape();
		if (arrowheadShape == null || arrowheadShape == ArrowheadShape.NONE) {
			return;
		}

		int arrowheadSize = t.getArrowheadSize();
		boolean arrowheadFilled = t.isArrowheadFilled();

		Color fg = res.getColor(t.getColor(), SWT.COLOR_BLACK);
		Color bg = res.getColor(t.getSecondaryColor(), SWT.COLOR_BLACK);

		int[] points = ArrowheadUtils.calculateTriangularArrowhead(wp2.x, wp2.y, wp1.x, wp1.y, arrowheadSize);
		if (points == null) {
			return;
		}
		for (int i = 0; i < points.length; i += 2) {
			points[i] = cm.worldXtoLocalX(points[i]);
			points[i + 1] = cm.worldYtoLocalY(points[i + 1]);
		}

		if (arrowheadFilled) {
			g.setBackgroundColor(bg);
			g.fillPolygon(points);
		}
		g.setForegroundColor(fg);
		if (arrowheadShape == ArrowheadShape.WEDGE) {
			g.drawPolyline(points);
		}
		else if (arrowheadShape == ArrowheadShape.TRIANGLE) {
			g.drawPolygon(points);
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
