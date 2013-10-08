package edu.uci.isr.widgets.swt;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;

public abstract class ActionWithProgress
    extends Action
    implements IRunnableWithProgress{

	public ActionWithProgress(){
		super();
	}

	public ActionWithProgress(String text, ImageDescriptor image){
		super(text, image);
	}

	public ActionWithProgress(String text, int style){
		super(text, style);
	}

	public ActionWithProgress(String text){
		super(text);
	}

	@Override
	public void run(){
		try{
			new ProgressMonitorDialog(null).run(true, true, this);
		}
		catch(Exception e){
			e.printStackTrace();
			MessageDialog.openError(null, "Error", e.getMessage());
		}
	}
}
