package edu.uci.isr.bna4.facets;

import org.eclipse.draw2d.geometry.Point;

public interface IHasMutableInternalWorldEndpoint extends IHasInternalWorldEndpoint {

	public void setInternalEndpointWorldThingID(String worldThingID);

	public void setInternalEndpoint(Point internalWorldPoint);
}
