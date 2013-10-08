package edu.uci.isr.bna4.things.glass;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;

public class EndpointGlassThingPeer extends AbstractThingPeer<EndpointGlassThing> {

	public EndpointGlassThingPeer(EndpointGlassThing t) {
		super(t, EndpointGlassThing.class);
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return BNAUtils.isWithin(t.getBoundingBox(), worldX, worldY);
	}
}
