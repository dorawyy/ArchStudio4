package edu.uci.isr.bna4;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Display;

public abstract class AbstractThingPeer<T extends IThing> implements IThingPeer {

	protected final T t;

	public AbstractThingPeer(T thing, Class<T> thingClass) {
		this.t = thing;
		if (!thingClass.isInstance(t)) {
			throw new IllegalArgumentException(this.getClass().getName() + " can only peer for " + thingClass.getName());
		}
	}

	public abstract void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res);

	public abstract boolean isInThing(IBNAView view, int worldX, int worldY);

	protected Display getDisplay() {
		Display d = Display.getCurrent();
		if (d != null) {
			return d;
		}
		return Display.getDefault();
	}
}
