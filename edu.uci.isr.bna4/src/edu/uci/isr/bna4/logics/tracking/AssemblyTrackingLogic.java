package edu.uci.isr.bna4.logics.tracking;

import java.util.Set;

import edu.uci.isr.bna4.AbstractThingLogic;
import edu.uci.isr.bna4.BNAModelEvent;
import edu.uci.isr.bna4.IBNASynchronousModelListener;
import edu.uci.isr.bna4.IThing;
import edu.uci.isr.bna4.ThingEvent;
import edu.uci.isr.bna4.assemblies.IAssembly;
import edu.uci.isr.bna4.facets.IHasAssemblyData;
import edu.uci.isr.sysutils.HashBag;

public class AssemblyTrackingLogic extends AbstractThingLogic implements IBNASynchronousModelListener {

	private HashBag<IAssembly> thingAssemblies = new HashBag<IAssembly>();

	public AssemblyTrackingLogic() {
	}

	@Override
	public void init() {
		for (IThing t : getBNAModel().getAllThings()) {
			update(t, null, t.getProperty(IHasAssemblyData.ASSEMBLY_PROPERTY_NAME));
		}
	}

	public void bnaModelChangedSync(BNAModelEvent evt) {
		switch (evt.getEventType()) {
		case THING_ADDED: {
			IThing thing = evt.getTargetThing();
			update(thing, null, thing.getProperty(IHasAssemblyData.ASSEMBLY_PROPERTY_NAME));
			break;
		}
		case THING_CHANGED: {
			IThing thing = evt.getTargetThing();
			ThingEvent te = evt.getThingEvent();
			if (IHasAssemblyData.ASSEMBLY_PROPERTY_NAME.equals(te.getPropertyName())) {
				switch (te.getEventType()) {
				case PROPERTY_SET:
				case PROPERTY_REMOVED:
					update(thing, te.getOldPropertyValue(), te.getNewPropertyValue());
				}
			}
			break;
		}
		case THING_REMOVED: {
			IThing thing = evt.getTargetThing();
			update(thing, thing.getProperty(IHasAssemblyData.ASSEMBLY_PROPERTY_NAME), null);
			break;
		}
		}
	}

	private void update(IThing thing, Object oldValue, Object newValue) {
		synchronized (thingAssemblies) {
			if (oldValue != null) {
				thingAssemblies.remove(oldValue);
			}
			if (newValue instanceof IAssembly) {
				thingAssemblies.add((IAssembly) newValue);
			}
		}
	}

	public IAssembly[] getAllAssemblies() {
		synchronized (thingAssemblies) {
			Set<IAssembly> uniqueValues = thingAssemblies.uniqueSet();
			return uniqueValues.toArray(new IAssembly[uniqueValues.size()]);
		}
	}
}
