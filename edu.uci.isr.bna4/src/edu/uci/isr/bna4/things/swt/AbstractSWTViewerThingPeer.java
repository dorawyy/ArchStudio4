package edu.uci.isr.bna4.things.swt;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import edu.uci.isr.bna4.AbstractThingPeer;
import edu.uci.isr.bna4.BNAUtils;
import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.IThingListener;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.bna4.ThingEvent;

public abstract class AbstractSWTViewerThingPeer<T extends AbstractSWTThing, V extends Viewer> extends AbstractThingPeer<T> {

	protected V viewer;

	public AbstractSWTViewerThingPeer(T thing, Class<T> thingClass) {
		super(thing, thingClass);
		t.addThingListener(new IThingListener() {

			public void thingChanged(ThingEvent thingEvent) {
				if (!AbstractSWTViewerThingPeer.this.t.isEditing() && viewer != null) {
					disposeViewer();
				}
			}
		});
	}

	abstract protected void createViewer(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res, Composite composite);

	abstract protected void updateViewer(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res, Composite composite);

	protected void disposeViewer() {
		Control control = viewer.getControl();
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

		if (t.isEditing() && viewer == null) {
			//It has been made visible but we have no corresponding viewer
			createViewer(view, g, clip, res, composite);

			if (composite.isFocusControl()) {
				viewer.getControl().forceFocus();
			}
		}
		else if (!t.isEditing() && viewer != null) {
			//It has been made invisible but we are still showing the viewer
			disposeViewer();
		}
		else if (viewer != null && !viewer.getControl().isDisposed()) {
			//Update the viewer
			updateViewer(view, g, clip, res, composite);
		}
	}

	@Override
	public boolean isInThing(IBNAView view, int worldX, int worldY) {
		return false;
	}
}