package edu.uci.isr.bna4.things.utility;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.DefaultBNAView;
import edu.uci.isr.bna4.DefaultCoordinateMapper;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.IBNAWorld;
import edu.uci.isr.bna4.ICoordinateMapper;
import edu.uci.isr.bna4.IMutableCoordinateMapper;
import edu.uci.isr.bna4.IThingPeer;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.bna4.logics.tracking.ModelBoundsTrackingLogic;

public class WorldThingPeer extends AbstractThingPeer<WorldThing> implements IThingPeer {

	protected IBNAView innerView = null;

	public WorldThingPeer(WorldThing t) {
		super(t, WorldThing.class);
	}

	public IBNAView getInnerView() {
		return innerView;
	}

	protected void updateInnerView(IBNAView view) {
		IBNAWorld innerWorld = t.getWorld();
		if (innerWorld == null || innerView != null && !innerWorld.equals(innerView.getWorld())) {
			innerView = null;
		}
		if (innerView == null && innerWorld != null) {
			innerView = new DefaultBNAView(view, innerWorld, new DefaultCoordinateMapper());
		}
	}

	protected Rectangle lastLocalBoundingBox = null;
	protected Rectangle lastModelBounds = null;

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		updateInnerView(view);
		if (innerView == null) {
			return;
		}

		Rectangle bb = t.getBoundingBox();
		Rectangle lbb = BNAUtils.worldToLocal(view.getCoordinateMapper(), bb);

		Rectangle modelBounds = ModelBoundsTrackingLogic.getModelBounds(innerView.getWorld());
		if (modelBounds == null || modelBounds.width <= 0 || modelBounds.height <= 0) {
			return;
		}

		if (!BNAUtils.nulleq(lastLocalBoundingBox, lbb) || !BNAUtils.nulleq(lastModelBounds, modelBounds)) {
			ICoordinateMapper icm = innerView.getCoordinateMapper();
			if (icm instanceof IMutableCoordinateMapper) {
				IMutableCoordinateMapper mcm = (IMutableCoordinateMapper) icm;

				double sx = (double) lbb.width / (modelBounds.width + 2);
				double sy = (double) lbb.height / (modelBounds.height + 2);
				double s = Math.min(Math.min(sx, sy), 1.0d);
				mcm.rescaleAbsolute(s);

				double ddx = s == sx ? 0.0d : (lbb.width / s - modelBounds.width) / 2.0d;
				double ddy = s == sy ? 0.0d : (lbb.height / s - modelBounds.height) / 2.0d;

				int dx = BNAUtils.round(ddx);
				int dy = BNAUtils.round(ddy);

				mcm.repositionAbsolute(modelBounds.x - BNAUtils.round(lbb.x / s) - dx, modelBounds.y - BNAUtils.round(lbb.y / s) - dy);
			}
		}
		lastLocalBoundingBox = lbb;
		lastModelBounds = modelBounds;

		if (!clip.intersects(lbb) || lbb.width < 5 || lbb.height < 5) {
			return;
		}

		Rectangle iclip = clip.getIntersection(lbb);
		g.setClip(iclip);
		BNAUtils.renderWorld(innerView, g, iclip, res);
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		updateInnerView(view);
		if (innerView == null) {
			return false;
		}

		return t.getBoundingBox().contains(worldX, worldY);
	}
}
