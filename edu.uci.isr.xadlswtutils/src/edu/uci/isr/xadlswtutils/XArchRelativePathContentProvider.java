package edu.uci.isr.xadlswtutils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;

import edu.uci.isr.widgets.swt.SWTWidgetUtils;
import edu.uci.isr.xarchflat.ObjRef;
import edu.uci.isr.xarchflat.XArchFlatEvent;
import edu.uci.isr.xarchflat.XArchPath;
import edu.uci.isr.xarchflat.utils.IXArchRelativePathTrackerListener;
import edu.uci.isr.xarchflat.utils.XArchRelativePathTracker;

public class XArchRelativePathContentProvider
	implements IStructuredContentProvider, IXArchRelativePathTrackerListener{

	protected Viewer viewer = null;
	protected final XArchRelativePathTracker tracker;

	public XArchRelativePathContentProvider(XArchRelativePathTracker tracker){
		this.tracker = tracker;
		tracker.addTrackerListener(this);
	}

	public Object[] getElements(Object inputElement){
		return tracker.getAddedObjRefs();
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput){
		this.viewer = viewer;
	}

	public void dispose(){
		this.viewer = null;
		tracker.removeTrackerListener(this);
	}

	public void processAdd(ObjRef objRef, ObjRef[] relativeAncestorRefs){
		refreshOutsideSWTThread();
	}

	public void processRemove(ObjRef objRef, ObjRef[] relativeAncestorRefs){
		refreshOutsideSWTThread();
	}

	public void processUpdate(ObjRef objRef, ObjRef[] relativeAncestorRefs, XArchFlatEvent evt, XArchPath relativeSourceTargetPath){
		updateOutsideSWTThread(objRef);
	}

	private boolean needsRefresh = false;
	private Set<ObjRef> needsUpdate = Collections.synchronizedSet(new HashSet<ObjRef>());

	protected void refreshOutsideSWTThread(){
		needsRefresh = true;
		SWTWidgetUtils.async(viewer, new Runnable(){

			public void run(){
				if(needsRefresh){
					needsRefresh = false;
					needsUpdate.clear();
					if(viewer != null){
						viewer.refresh();
					}
				}
			}
		});
	}

	protected void updateOutsideSWTThread(ObjRef objRef){
		needsUpdate.add(objRef);
		SWTWidgetUtils.async(viewer, new Runnable(){

			public void run(){
				ObjRef[] elements = null;
				synchronized(needsUpdate){
					if(!needsUpdate.isEmpty()){
						elements = needsUpdate.toArray(new ObjRef[needsUpdate.size()]);
						needsUpdate.clear();
					}
				}
				if(elements != null){
					if(viewer instanceof StructuredViewer){
						((StructuredViewer)viewer).update(elements, null);
					}
					else if(viewer != null){
						needsRefresh = false;
						viewer.refresh();
					}
				}
			}
		});
	}
}
