/**
 * 
 */
package edu.uci.isr.archstudio4.comp.tracelink.analysis;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Shell;

import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxRegistry;
import edu.uci.isr.myx.fw.MyxUtils;

/**
 * @author Hazel, dpurpura
 */
public class SpecifyRule {
	/*

	extends AbstractMyxSimpleBrick
	implements ISpecifyRule{

	public static final IMyxName INTERFACE_NAME_IN_SPECIFYRULE = MyxUtils.createName("specifyrule");

	protected MyxRegistry myxr;
	protected Vector<ITracelinkRule> rules;
	

	public SpecifyRule(){
		myxr = MyxRegistry.getSharedInstance();
		rules = new Vector<ITracelinkRule>();
	}

	
	public void begin(){
		myxr.register(this);

	}
	*/

	/**
	 * @see edu.uci.isr.myx.fw.IMyxProvidedServiceProvider#getServiceObject(edu.uci.isr.myx.fw.IMyxName)
	 */
	/*
	public Object getServiceObject(IMyxName interfaceName){
		
		if(interfaceName.equals(INTERFACE_NAME_IN_SPECIFYRULE)){
			return this;
		}
		else{
			return null;
		}
		
	}
	*/

	public void specifyRule(Shell parent){
		/*
		if(parent == null){
			System.err.println("SpecifyRule: parent shell is null!");
			return;
		}

		Shell shell = new Shell(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		shell.setText("Specify Rules");
		shell.setSize(600, 500);
		shell.setLayout(new FillLayout());

		ExpandBar bar = new ExpandBar(shell, SWT.NONE);
		Composite rule = new RuleDialog(bar, SWT.None);
		ExpandItem item = new ExpandItem(bar, SWT.NONE);

		item.setText("New Rule");
		item.setHeight(rule.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item.setControl(rule);
		item.setExpanded(true);

		shell.open();

		while(!shell.isDisposed()){
			if(!shell.getDisplay().readAndDispatch()){
				shell.getDisplay().sleep();
			}
		}
	*/
	}

}
