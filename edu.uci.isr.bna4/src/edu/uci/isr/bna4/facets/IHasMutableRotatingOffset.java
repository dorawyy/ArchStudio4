package edu.uci.isr.bna4.facets;

import edu.uci.isr.bna4.logics.background.RotatingOffsetLogic;
import edu.uci.isr.bna4.things.borders.MarqueeBoxBorderThing;
import edu.uci.isr.bna4.things.glass.BoxGlassThing;

public interface IHasMutableRotatingOffset extends IHasRotatingOffset {

	/**
	 * This is an optimization to reduce unnecessary BNA event noise caused by
	 * the {@link RotatingOffsetLogic} calling
	 * {@link #incrementRotatingOffset()}. For example, it is unnecessary to
	 * call {@link #incrementRotatingOffset()} for glass things that are not
	 * selected, e.g., {@link BoxGlassThing}. Conversely,
	 * {@link #incrementRotatingOffset()} must always be called on
	 * {@link MarqueeBoxBorderThing}.
	 * 
	 * @see RotatingOffsetLogic
	 * @see MarqueeBoxBorderThing
	 * @return <code>false</code> if it is know that calling the
	 *         {@link #incrementRotatingOffset()} method is unnecessary,
	 *         <code>true</code> otherwise.
	 */
	public boolean shouldIncrementRotatingOffset();

	public void incrementRotatingOffset();
}
