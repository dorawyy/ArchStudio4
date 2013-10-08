package edu.uci.isr.bna4.things.shapes;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Pattern;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAComposite;
import edu.uci.isr.bna4.BNARenderingSettings;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;

public class BoxThingPeer extends AbstractThingPeer<BoxThing> {

	public BoxThingPeer(BoxThing t) {
		super(t, BoxThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);
		if (!clip.intersects(lbb)) {
			return;
		}

		boolean isGradientFilled = t.isGradientFilled();
		if (isGradientFilled) {
			BNAComposite c = (BNAComposite) BNAUtils.getParentComposite(view);
			if (c != null) {
				if (!BNARenderingSettings.getDecorativeGraphics(c)) {
					isGradientFilled = false;
				}
			}
		}

		Point lc = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb, t.getCornerWidth(), t.getCornerHeight());
		if (lc.x > 0 && lc.y > 0) {
			Pattern pattern = null;
			try {
				if (isGradientFilled) {
					g.setForegroundColor(res.getColor(t.getColor(), SWT.COLOR_GRAY));
					g.setBackgroundColor(res.getColor(t.getSecondaryColor(), SWT.COLOR_WHITE));
					pattern = new Pattern(res.getDevice(), lbb.x, lbb.y, lbb.x, lbb.y + lbb.height, g.getForegroundColor(), g.getBackgroundColor());
					g.setBackgroundPattern(pattern);
				}
				else {
					g.setBackgroundColor(res.getColor(t.getColor(), SWT.COLOR_GRAY));
				}
				g.fillRoundRectangle(lbb, lc.x, lc.y);
			}
			finally {
				if (pattern != null) {
					pattern.dispose();
					pattern = null;
					g.setBackgroundPattern(null);
				}
			}
		}
		else {
			if (isGradientFilled) {
				g.setForegroundColor(res.getColor(t.getColor(), SWT.COLOR_GRAY));
				g.setBackgroundColor(res.getColor(t.getSecondaryColor(), SWT.COLOR_WHITE));
				g.fillGradient(lbb, true);
			}
			else {
				g.setBackgroundColor(res.getColor(t.getColor(), SWT.COLOR_GRAY));
				g.fillRectangle(lbb.x, lbb.y, lbb.width, lbb.height);
			}
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
