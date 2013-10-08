package edu.uci.isr.bna4.things.swt;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.IThingListener;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.bna4.ThingEvent;

public abstract class AbstractSWTControlThingPeer<T extends AbstractSWTThing, C extends Control> extends AbstractThingPeer<T> {

	protected C control;

	public AbstractSWTControlThingPeer(T thing, Class<T> thingClass) {
		super(thing, thingClass);
		t.addThingListener(new IThingListener() {

			public void thingChanged(ThingEvent thingEvent) {
				if (!AbstractSWTControlThingPeer.this.t.isEditing() && control != null) {
					disposeControl();
				}
			}
		});
	}

	abstract protected void createControl(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res, Composite composite);

	abstract protected void updateControl(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res, Composite composite);

	protected void disposeControl() {
		if (!control.isDisposed()) {
			control.dispose();
		}
		control = null;
	}

	@Override
	public void draw(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res) {
		Composite composite = BNAUtils.getParentComposite(view);
		if (composite == null) {
			return;
		}

		if (t.isEditing() && control == null) {
			//It has been made visible but we have no corresponding control
			createControl(view, g, clip, res, composite);

			if (composite.isFocusControl()) {
				control.forceFocus();
			}
		}
		else if (!t.isEditing() && control != null) {
			//It has been made invisible but we are still showing the control
			disposeControl();
		}
		else if (control != null && !control.isDisposed()) {
			//Update the control
			updateControl(view, g, clip, res, composite);
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}
