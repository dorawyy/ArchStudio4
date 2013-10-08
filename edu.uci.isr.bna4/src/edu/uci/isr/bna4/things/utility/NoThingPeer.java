package edu.uci.isr.bna4.things.utility;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.IThing;
import edu.uci.isr.bna4.IThingPeer;
import edu.uci.isr.bna4.ResourceUtils;

public final class NoThingPeer extends AbstractThingPeer<IThing> implements IThingPeer {

	public NoThingPeer(IThing t) {
		super(t, IThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
