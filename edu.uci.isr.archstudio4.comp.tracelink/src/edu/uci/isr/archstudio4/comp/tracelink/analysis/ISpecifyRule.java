package edu.uci.isr.archstudio4.comp.tracelink.analysis;

import org.eclipse.swt.widgets.Shell;

public interface ISpecifyRule {

	/**
	 * Prompts the user to specify a rule through a dialog box.
	 * 
	 * Returns the rule specifed by the user
	 * 
	 * @param parent  the parent shell to the dialog
	 */
	public void specifyRule(Shell parent);
}
