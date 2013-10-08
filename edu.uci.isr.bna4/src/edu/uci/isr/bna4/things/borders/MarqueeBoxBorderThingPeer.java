package edu.uci.isr.bna4.things.borders;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;

public class MarqueeBoxBorderThingPeer extends AbstractThingPeer<MarqueeBoxBorderThing> {

	public MarqueeBoxBorderThingPeer(MarqueeBoxBorderThing t) {
		super(t, MarqueeBoxBorderThing.class);
	}

	@Override
	public void draw(IBNAView view, final Graphics g, Rectangle clip, ResourceUtils res) {
		Rectangle bb = t.getBoundingBox();
		final Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb)) {
			return;
		}

		final int offset = t.getRotatingOffset();
		BNAUtils.drawMarquee(g, res, offset, true, new Runnable() {

			public void run() {
				g.drawRectangle(lbb.x, lbb.y, lbb.width, lbb.height);
			};
		});
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
