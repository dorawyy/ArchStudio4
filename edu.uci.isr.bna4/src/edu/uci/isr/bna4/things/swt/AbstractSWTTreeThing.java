package edu.uci.isr.bna4.things.swt;


public abstract class AbstractSWTTreeThing extends AbstractSWTThing {

	public static final String VALUE_PROPERTY_NAME = "value";

	public AbstractSWTTreeThing(String id) {
		super(id);
	}

	public void setValue(Object value) {
		setProperty(VALUE_PROPERTY_NAME, value);
	}

	public Object getValue() {
		return getProperty(VALUE_PROPERTY_NAME);
	}
}
