package edu.uci.isr.bna4.things.glass;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;

public class EllipseGlassThingPeer extends AbstractThingPeer<EllipseGlassThing> {

	public EllipseGlassThingPeer(EllipseGlassThing t) {
		super(t, EllipseGlassThing.class);
	}

	@Override
	public void draw(IBNAView view, final Graphics g, Rectangle clip, ResourceUtils res) {
		if (t.isSelected()) {
			Rectangle bb = t.getBoundingBox();
			final Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
			if (!clip.intersects(lbb)) {
				return;
			}

			int offset = t.getRotatingOffset();
			BNAUtils.drawMarquee(g, res, offset, false, new Runnable() {

				public void run() {
					g.drawOval(lbb.x, lbb.y, lbb.width, lbb.height);
				}
			});
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		Rectangle bb = BNAUtils.normalizeRectangle(t.getBoundingBox());
		return new Ellipse2D.Float(bb.x, bb.y, bb.width, bb.height).contains(new Point2D.Float(worldX, worldY));
	}
}
