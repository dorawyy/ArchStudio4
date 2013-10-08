package edu.uci.isr.bna4;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PeerCache {

	protected Map<IThing, IThingPeer> peerMap;

	public PeerCache() {
		this.peerMap = Collections.synchronizedMap(new HashMap<IThing, IThingPeer>());
	}

	public IThingPeer createPeer(IThing th) {
		try {
			IThingPeer peer = null;
			Class<? extends IThingPeer> peerClass = th.getPeerClass();
			for (Constructor<?> c : peerClass.getConstructors()) {
				Class<?>[] parameterTypes = c.getParameterTypes();
				if (parameterTypes.length == 1) {
					if (parameterTypes[0].isInstance(th)) {
						peer = ((Constructor<? extends IThingPeer>)c).newInstance(new Object[] { th });
						break;
					}
				}
				if (parameterTypes.length == 2) {
					if (parameterTypes[0].isInstance(th)) {
						// TODO: check generics if possible
						if (parameterTypes[1].isInstance(th.getClass())) {
							peer = ((Constructor<? extends IThingPeer>)c).newInstance(new Object[] { th, th.getClass() });
							break;
						}
					}
				}
			}
			if (peer == null) {
				throw new RuntimeException("Could not instantiate peer.");
			}
			peerMap.put(th, peer);
			return peer;
		}
		catch (InvocationTargetException ite) {
			throw new RuntimeException("Could not instantiate peer.", ite);
		}
		catch (IllegalAccessException iae) {
			throw new RuntimeException("Could not instantiate peer.", iae);
		}
		catch (InstantiationException ie) {
			throw new RuntimeException("Could not instantiate peer.", ie);
		}
	}

	public IThingPeer getPeer(IThing th) {
		IThingPeer peer = peerMap.get(th);
		if (peer != null) {
			return peer;
		}
		else {
			return createPeer(th);
		}
	}

}
