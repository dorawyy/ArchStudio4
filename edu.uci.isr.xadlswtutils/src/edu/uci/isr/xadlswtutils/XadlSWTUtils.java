package edu.uci.isr.xadlswtutils;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.uci.isr.widgets.swt.SWTWidgetUtils;
import edu.uci.isr.xadlutils.XadlUtils;
import edu.uci.isr.xarchflat.ObjRef;
import edu.uci.isr.xarchflat.XArchFlatEvent;
import edu.uci.isr.xarchflat.XArchFlatEventProvider;
import edu.uci.isr.xarchflat.XArchFlatListener;
import edu.uci.isr.xarchflat.XArchFlatQueryInterface;

public class XadlSWTUtils{

	private static void updateLabelTextOutsideSWT(final Label label, final XArchFlatQueryInterface xarch, final ObjRef objRef){
		SWTWidgetUtils.async(label, new Runnable(){

			public void run(){
				String description = XadlUtils.getDescription(xarch, objRef, "Description", "[No Description]");
				label.setText(description);
				label.setToolTipText(description);
				SWTWidgetUtils.relayout(label);
			}
		});
	}

	public static Label createDescriptionLabel(Composite parent, int style, final XArchFlatQueryInterface xarch, final XArchFlatEventProvider flatEventProvider, final ObjRef objRef){
		final Label label = new Label(parent, style);
		final XArchFlatListener updateLabelListener = new XArchFlatListener(){

			public void handleXArchFlatEvent(XArchFlatEvent evt){
				ObjRef[] sourceAncestors = evt.getSourceAncestors();
				if(sourceAncestors.length > 1){
					if(objRef.equals(sourceAncestors[1])){
						updateLabelTextOutsideSWT(label, xarch, objRef);
					}
				}
			}
		};
		flatEventProvider.addXArchFlatListener(updateLabelListener);
		label.addDisposeListener(new DisposeListener(){

			public void widgetDisposed(DisposeEvent e){
				flatEventProvider.removeXArchFlatListener(updateLabelListener);
			}
		});
		updateLabelTextOutsideSWT(label, xarch, objRef);
		return label;
	}
}
