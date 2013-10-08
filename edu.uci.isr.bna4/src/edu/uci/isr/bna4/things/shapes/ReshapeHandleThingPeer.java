package edu.uci.isr.bna4.things.shapes;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.widgets.swt.constants.Orientation;

public class ReshapeHandleThingPeer extends AbstractThingPeer<ReshapeHandleThing> {

	public static final int HANDLE_WIDTH = 5;
	public static final int HANDLE_HEIGHT = 5;

	protected Rectangle lbb = new Rectangle(0, 0, 0, 0);

	public ReshapeHandleThingPeer(ReshapeHandleThing t) {
		super(t, ReshapeHandleThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		Point p = t.getAnchorPoint();
		Point lp = BNAUtils.worldToLocal(view.getCoordinateMapper(), p);
		Rectangle lbb = new Rectangle();
		Orientation o = t.getOrientation();
		BNAUtils.alignRectangle(lbb, lp, HANDLE_WIDTH, HANDLE_HEIGHT, o);

		//Rectangle bb = BNAUtils.localToWorld(view.getCoordinateMapper(), lbb);
		//t.setProperty(ReshapeHandleThing.HANDLE_BOUNDING_BOX_PROPERTY_NAME, bb);

		if (!clip.intersects(lbb)) {
			return;
		}

		g.setBackgroundColor(res.getColor(t.getColor(), SWT.COLOR_BLUE));
		g.fillRectangle(lbb);
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
