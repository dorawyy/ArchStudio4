package edu.uci.isr.bna4.things.glass;

import java.util.concurrent.locks.Lock;

import edu.uci.isr.bna4.facets.IHasMutableAnchorPoint;
import edu.uci.isr.bna4.facets.IHasMutablePathData;
import edu.uci.isr.bna4.facets.IHasMutableRotatingOffset;
import edu.uci.isr.bna4.facets.IHasMutableSelected;
import edu.uci.isr.bna4.facets.IRelativeMovable;
import edu.uci.isr.bna4.things.essence.PathEssenceThing;

public class PathGlassThing extends PathEssenceThing implements IHasMutablePathData, IHasMutableAnchorPoint, IRelativeMovable, IHasMutableSelected,
        IHasMutableRotatingOffset {

	public PathGlassThing() {
		this(null);
	}

	public PathGlassThing(String id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setSelected(false);
		setProperty(ROTATING_OFFSET_PROPERTY_NAME, 0);
	}

	public void setSelected(boolean selected) {
		setProperty(SELECTED_PROPERTY_NAME, selected);
	}

	public boolean isSelected() {
		return getProperty(SELECTED_PROPERTY_NAME);
	}

	public int getRotatingOffset() {
		return getProperty(ROTATING_OFFSET_PROPERTY_NAME);
	}

	public boolean shouldIncrementRotatingOffset() {
		return isSelected();
	}
	
	public void incrementRotatingOffset() {
		if (shouldIncrementRotatingOffset()) {
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
}
