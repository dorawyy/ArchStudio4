package edu.uci.isr.bna4.things.glass;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.widgets.swt.constants.Orientation;

public class ReshapeHandleGlassThingPeer extends AbstractThingPeer<ReshapeHandleGlassThing> {

	public static final int HANDLE_WIDTH = 5;
	public static final int HANDLE_HEIGHT = 5;

	public ReshapeHandleGlassThingPeer(ReshapeHandleGlassThing t) {
		super(t, ReshapeHandleGlassThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		Point p = t.getAnchorPoint();
		Point lp = BNAUtils.worldToLocal(view.getCoordinateMapper(), p);
		Rectangle lbb = new Rectangle();
		Orientation o = t.getOrientation();
		BNAUtils.alignRectangle(lbb, lp, HANDLE_WIDTH, HANDLE_HEIGHT, o);

		int localX = view.getCoordinateMapper().worldXtoLocalX(worldX);
		int localY = view.getCoordinateMapper().worldYtoLocalY(worldY);

		return lbb.contains(localX, localY);
	}
}
