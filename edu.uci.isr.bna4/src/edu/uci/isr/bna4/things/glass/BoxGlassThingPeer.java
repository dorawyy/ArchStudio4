package edu.uci.isr.bna4.things.glass;

import java.awt.geom.RoundRectangle2D;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;

public class BoxGlassThingPeer extends AbstractThingPeer<BoxGlassThing> {

	public BoxGlassThingPeer(BoxGlassThing t) {
		super(t, BoxGlassThing.class);
	}

	@Override
	public void draw(IBNAView view, final Graphics g, Rectangle clip, ResourceUtils res) {
		if (!t.isSelected()) {
			return;
		}

		Rectangle bb = t.getBoundingBox();
		final Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb)) {
			return;
		}

		final Point lc = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb, t.getCornerWidth(), t.getCornerHeight());
		BNAUtils.drawMarquee(g, res, t.getRotatingOffset(), true, new Runnable() {

			public void run() {
				if (lc.x > 0 && lc.y > 0) {
					g.drawRoundRectangle(lbb, lc.x, lc.y);
				}
				else {
					g.drawRectangle(lbb.x, lbb.y, lbb.width, lbb.height);
				}
			};
		});
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		Rectangle r = t.getBoundingBox();
		if (BNAUtils.isWithin(r, worldX, worldY)) {
			int cornerWidth = t.getCornerWidth();
			int cornerHeight = t.getCornerHeight();
			if (cornerWidth > 0 && cornerHeight > 0) {
				return new RoundRectangle2D.Float(r.x, r.y, r.width, r.height, cornerWidth, cornerHeight).contains(worldX, worldY);
			}
			return true;
		}
		return false;
	}
}
