package edu.uci.isr.bna4.things.borders;

import java.util.concurrent.locks.Lock;

import org.eclipse.swt.graphics.RGB;

import edu.uci.isr.bna4.facets.IHasMutableColor;
import edu.uci.isr.bna4.facets.IHasMutableRotatingOffset;
import edu.uci.isr.bna4.things.essence.RectangleEssenceThing;

public class PulsingBorderThing extends RectangleEssenceThing implements IHasMutableColor, IHasMutableRotatingOffset {

	public PulsingBorderThing() {
		this(null);
	}

	public PulsingBorderThing(String id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setColor(new RGB(255, 0, 0));
		setProperty(ROTATING_OFFSET_PROPERTY_NAME, 0);
	}

	public void setColor(RGB c) {
		setProperty(COLOR_PROPERTY_NAME, c);
	}

	public RGB getColor() {
		return getProperty(COLOR_PROPERTY_NAME);
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
