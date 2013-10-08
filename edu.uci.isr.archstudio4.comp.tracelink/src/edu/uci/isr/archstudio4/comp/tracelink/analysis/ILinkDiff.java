package edu.uci.isr.archstudio4.comp.tracelink.analysis;

import java.util.Collection;

import edu.uci.isr.archstudio4.comp.tracelink.models.ITracelinkModel;

public interface ILinkDiff {

	/**
	 * Returns the list of tracelinks in links2 that are absent in links1
	 * 
	 * @param links1  the current IArchTraceLinks
	 * @param links2  the IArchTraceLink to get ITraceLinks from
	 * @return list of with the missing tracelinks in links1
	 */
	public ITracelinkModel[] getDiff(Collection<ITracelinkModel> links1, Collection<ITracelinkModel> links2);
}
