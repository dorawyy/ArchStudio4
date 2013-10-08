package edu.uci.isr.bna4.things.swt;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;

import edu.uci.isr.bna4.IBNAView;
import edu.uci.isr.bna4.ResourceUtils;
import edu.uci.isr.bna4.constants.CompletionStatus;

public abstract class AbstractSWTTreeThingPeer<T extends AbstractSWTTreeThing> extends AbstractSWTViewerThingPeer<T, TreeViewer> {

	public AbstractSWTTreeThingPeer(T thing, Class<T> thingClass) {
		super(thing, thingClass);
	}

	protected abstract Object getInput();

	protected abstract ITreeContentProvider getContentProvider();

	protected abstract ILabelProvider getLabelProvider();

	@Override
	protected void createViewer(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res, Composite composite) {
		viewer = new TreeViewer(composite, SWT.BORDER | SWT.FLAT | SWT.SINGLE);

		ITreeContentProvider contentProvider = getContentProvider();
		if (contentProvider != null) {
			viewer.setContentProvider(contentProvider);
		}
		ILabelProvider labelProvider = getLabelProvider();
		if (labelProvider != null) {
			viewer.setLabelProvider(labelProvider);
		}

		viewer.setInput(getInput());
		viewer.expandAll();

		Object initialValue = t.getValue();
		if (initialValue != null) {
			viewer.setSelection(new StructuredSelection(initialValue));
		}

		if (composite.isFocusControl()) {
			viewer.getControl().forceFocus();
		}

		updateViewer(view, g, clip, res, composite);

		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					Object value = structuredSelection.getFirstElement();
					t.setValue(value);
					t.setCompletionStatus(CompletionStatus.OK);
					t.setEditing(false);
				}
			}
		});
		viewer.getControl().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.character == SWT.ESC) {
					t.setCompletionStatus(CompletionStatus.CANCEL);
					t.setEditing(false);
				}
			}
		});
	}

	@Override
	protected void updateViewer(IBNAView view, Graphics g, Rectangle clip, ResourceUtils res, Composite composite) {
		//Note: this is a good idea but it doesn't work because of an Eclipse bug.
		//Point computedSize = control.getTree().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		viewer.getTree().setSize(280, viewer.getTree().getItemHeight() * 5);
		ScrollBar hb = viewer.getTree().getHorizontalBar();
		if (hb != null) {
			hb.setSelection(hb.getMinimum());
		}
	}
}
