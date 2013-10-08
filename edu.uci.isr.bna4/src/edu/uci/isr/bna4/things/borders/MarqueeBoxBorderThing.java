package edu.uci.isr.bna4.things.borders;

import java.util.concurrent.locks.Lock;

import org.eclipse.draw2d.geometry.Point;

import edu.uci.isr.bna4.facets.IHasMutableRotatingOffset;
import edu.uci.isr.bna4.things.essence.RectangleEssenceThing;

public class MarqueeBoxBorderThing extends RectangleEssenceThing implements IHasMutableRotatingOffset {

	public MarqueeBoxBorderThing() {
		this(null);
	}

	public MarqueeBoxBorderThing(String id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setProperty(ROTATING_OFFSET_PROPERTY_NAME, 0);
		setMinBoundingBoxSize(new Point(0, 0));
	}

	public int getRotatingOffset() {
		return getProperty(ROTATING_OFFSET_PROPERTY_NAME);
	}

	public boolean shouldIncrementRotatingOffset() {
		return true;
	}
	
	public void incrementRotatingOffset() {
		Lock lock = getPropertyLock();
		lock.lock();
		try {
			setProperty(ROTATING_OFFSET_PROPERTY_NAME, getRotatingOffset() + 1);
		}
		finally {
			lock.unlock();
		}
	}
}
