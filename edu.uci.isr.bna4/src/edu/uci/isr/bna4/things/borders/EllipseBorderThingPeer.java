package edu.uci.isr.bna4.things.borders;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;

public class EllipseBorderThingPeer extends AbstractThingPeer<EllipseBorderThing> {

	public EllipseBorderThingPeer(EllipseBorderThing t) {
		super(t, EllipseBorderThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb)) {
			return;
		}

		g.setForegroundColor(res.getColor(t.getColor(), SWT.COLOR_BLACK));
		g.setLineStyle(t.getLineStyle());
		int lineWidth = t.getLineWidth();
		g.setLineWidth(lineWidth);

		int count = t.getCount();
		while (count-- > 0 && lbb.width >= 0 && lbb.height >= 0) {
			g.drawOval(lbb.x, lbb.y, lbb.width, lbb.height);
			if (count > 0) {
				lbb.expand(-lineWidth * 2, -lineWidth * 2);
			}
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
